package common;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import egl.math.Vector3;
import common.event.SceneCollectionModifiedEvent;
import common.event.SceneDataType;
import common.event.SceneEvent;
import common.event.SceneEventQueue;
import common.texture.TexGenCheckerBoard;
import common.texture.TexGenSphereNormalMap;
import common.texture.TexGenUVGrid;
import mesh.gen.MeshGenCube;
import mesh.gen.MeshGenCylinder;
import mesh.gen.MeshGenPlane;
import mesh.gen.MeshGenSphere;



public class Scene {
	/**
	 * The Name Of The Root Node
	 */
	public static final String ROOT_NODE_NAME = "World";
	public static final HashSet<String> DEFAULT_MESHES = new HashSet<>(Arrays.asList("Sphere", "Cube", "Cylinder"));
	public static final HashSet<String> DEFAULT_MATERIALS = new HashSet<>(Arrays.asList("Generic"));
	public static final HashSet<String> DEFAULT_TEXTURES = new HashSet<>(Arrays.asList("Checker Board", "UV"));

	/**
	 * Container Of Unique Meshes
	 */
	public final UniqueContainer<Mesh> meshes = new UniqueContainer<Mesh>(new UniqueContainer.IAllocator<Mesh>() {
		@Override
		public Mesh generate() {
			return new Mesh();
		}
	}, "__Mesh__");
	/**
	 * Container Of Unique Textures
	 */
	public final UniqueContainer<Texture> textures = new UniqueContainer<Texture>(new UniqueContainer.IAllocator<Texture>() {
		@Override
		public Texture generate() {
			return new Texture();
		}
	}, "__Texture__");
	/**
	 * Container Of Unique Cube Map	
	 */
	public final UniqueContainer<Cubemap> cubemaps = new UniqueContainer<Cubemap>(new UniqueContainer.IAllocator<Cubemap>() {
		@Override
		public Cubemap generate() {
			return new Cubemap();
		}
	}, "__Cubemap__");
	/**
	 * Container Of Unique Materials
	 */
	public final UniqueContainer<Material> materials = new UniqueContainer<Material>(new UniqueContainer.IAllocator<Material>() {
		@Override
		public Material generate() {
			return new Material();
		}
	}, "__Material__");
	/**
	 * Container Of Unique Objects
	 */
	public final UniqueContainer<SceneObject> objects = new UniqueContainer<SceneObject>(new UniqueContainer.IAllocator<SceneObject>() {
		@Override
		public SceneObject generate() {
			return new SceneObject();
		}
	}, "__Object__");

	public Vector3 background = new Vector3();
	
	/**
	 * A list of queues that listen to updates in the scene
	 */
	private final ArrayList<SceneEventQueue> changeListeners = new ArrayList<>();

	public Scene() {
		// Add Primitive Shapes
		Mesh m = new Mesh();
		m.setGenerator(new MeshGenSphere());
		addMesh(new NameBindMesh("Sphere", m));
		m = new Mesh();
		m.setGenerator(new MeshGenCylinder());
		addMesh(new NameBindMesh("Cylinder", m));
		m = new Mesh();
		m.setGenerator(new MeshGenCube());
		addMesh(new NameBindMesh("Cube", m));
		m = new Mesh();
		m.setGenerator(new MeshGenPlane());
		addMesh(new NameBindMesh("Plane", m));

		// Add Simple Generated Textures
		Texture t = new Texture();
		t.setGenerator(new TexGenCheckerBoard());
		addTexture(new NameBindTexture("Checker Board", t));
		t = new Texture();
		t.setGenerator(new TexGenUVGrid());
		addTexture(new NameBindTexture("UV", t));
		t = new Texture();
		t.setGenerator(new TexGenSphereNormalMap());
		addTexture(new NameBindTexture("NormalMapped", t));
		
		// Add Generic Material
		Material mat = new Material();
		mat.setType(Material.T_AMBIENT);
		addMaterial(new NameBindMaterial("Generic", mat));

		// Add The Root Node
		SceneObject so = new SceneObject();
		addObject(new NameBindSceneObject(ROOT_NODE_NAME, so));
	}

	public void addListener(SceneEventQueue q){
		changeListeners.add(q);
	}
	public void sendEvent(SceneEvent e) {
		for(SceneEventQueue q : changeListeners) q.addEvent(e);		
	}

	public static class NameBindMesh { 
		String name = null;
		Mesh data = null;

		public NameBindMesh() {
		}
		public NameBindMesh(String s, Mesh d) {
			setName(s);
			setData(d);
		}

		public void setName(String s) {
			name = s;
		}
		public void setData(Mesh d) {
			data = d;
		}
	}
	public void addMesh(NameBindMesh o) {
		meshes.add(o.data);
		meshes.setName(o.data, o.name);

		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Mesh, o.name, true));
	}
	public void removeMesh(String name) {
		meshes.remove(name);

		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Mesh, name, false));
	}

	public static class NameBindTexture { 
		String name = null;
		Texture data = null;

		public NameBindTexture() {
		}
		public NameBindTexture(String s, Texture d) {
			setName(s);
			setData(d);
		}

		public void setName(String s) {
			name = s;
		}
		public void setData(Texture d) {
			data = d;
		}
	}
	
	public static class NameBindCubemap {
		String name = null;
		Cubemap data = null;
		public NameBindCubemap() {
		}
		
		public NameBindCubemap(String s, Cubemap d) {
			setName(s);
			setData(d);
		}

		public void setName(String s) {
			name = s;
		}
		public void setData(Cubemap d) {
			data = d;
		}
	}
	
	public void addTexture(NameBindTexture o) {
		textures.add(o.data);
		textures.setName(o.data, o.name);

		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Texture, o.name, true));
	}
	
	public void addCubemap(NameBindCubemap o) {
		cubemaps.add(o.data);
		cubemaps.setName(o.data, o.name);
		
		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Cubemap, o.name, true));
	}
	
	public void removeTexture(String name) {
		textures.remove(name);

		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Texture, name, false));
	}

	public void removeCubemap(String name) {
		cubemaps.remove(name);

		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Cubemap, name, false));
	}
	
	
	public static class NameBindMaterial { 
		String name = null;
		Material data = null;

		public NameBindMaterial() {
		}
		public NameBindMaterial(String s, Material d) {
			setName(s);
			setData(d);
		}

		public void setName(String s) {
			name = s;
		}
		public void setData(Material d) {
			data = d;
		}
	}
	public void addMaterial(NameBindMaterial o) {
		materials.add(o.data);
		materials.setName(o.data, o.name);

		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Material, o.name, true));
	}
	public void removeMaterial(String name) {
		materials.remove(name);

		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Material, name, false));
	}

	public static class NameBindSceneObject { 
		String name = null;
		SceneObject data = null;

		public NameBindSceneObject() {
		}
		public NameBindSceneObject(String s, SceneObject d) {
			setName(s);
			setData(d);
		}

		public void setName(String s) {
			name = s;
		}
		public void setData(SceneObject d) {
			data = d;
		}
	}
	public void addObject(NameBindSceneObject o) {
		objects.add(o.data);
		objects.setName(o.data, o.name);
		if(o.data.parent == null && !o.name.equals(ROOT_NODE_NAME)) o.data.parent = ROOT_NODE_NAME;
		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Object, o.name, true));
	}
	public void removeObject(String name) {
		// Can't Delete The Root
		if(name.equals(ROOT_NODE_NAME)) return;
		objects.remove(name);
		for(SceneObject o : objects) {
			if(o.parent != null && o.parent.equals(name)) o.parent = ROOT_NODE_NAME;
		}
		
		sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Object, name, false));
	}	
	
	public void setBackground(Vector3 background) {
		this.background.set(background);
	}

	public void saveData(String file) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// Scene Root
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("scene");
		doc.appendChild(rootElement);

		// Textures
		for(Texture o : textures) {
			if(DEFAULT_TEXTURES.contains(o.getID().name)) continue;

			Element e = doc.createElement("texture");
			Element tName = doc.createElement("name");
			tName.appendChild(doc.createTextNode(o.getID().name));
			e.appendChild(tName);

			Element eData = doc.createElement("data");
			o.saveData(doc, eData);
			e.appendChild(eData);
			
			rootElement.appendChild(e);
		}
		
		// Cube maps
		for(Cubemap o : cubemaps) {
			if(DEFAULT_TEXTURES.contains(o.getID().name)) continue;

			Element e = doc.createElement("cubemap");
			Element tName = doc.createElement("name");
			tName.appendChild(doc.createTextNode(o.getID().name));
			e.appendChild(tName);

			Element eData = doc.createElement("data");
			o.saveData(doc, eData);
			e.appendChild(eData);
			
			rootElement.appendChild(e);
		}
		
		// Meshes
		for(Mesh o : meshes) {
			if(DEFAULT_MESHES.contains(o.getID().name)) continue;

			Element e = doc.createElement("mesh");
			Element tName = doc.createElement("name");
			tName.appendChild(doc.createTextNode(o.getID().name));
			e.appendChild(tName);

			Element eData = doc.createElement("data");
			o.saveData(doc, eData);
			e.appendChild(eData);
			
			rootElement.appendChild(e);
		}
		
		// Materials
		for(Material o : materials) {
			if(DEFAULT_MATERIALS.contains(o.getID().name)) continue;

			Element e = doc.createElement("material");
			Element tName = doc.createElement("name");
			tName.appendChild(doc.createTextNode(o.getID().name));
			e.appendChild(tName);

			Element eData = doc.createElement("data");
			o.saveData(doc, eData);
			e.appendChild(eData);
			
			rootElement.appendChild(e);
		}
		
		// Objects
		for(SceneObject o : objects) {
			if(o.getID().name.equals(ROOT_NODE_NAME)) continue;

			Element e = doc.createElement("object");
			Element tName = doc.createElement("name");
			tName.appendChild(doc.createTextNode(o.getID().name));
			e.appendChild(tName);

			Element eData = doc.createElement("data");
			o.saveData(doc, eData);
			e.appendChild(eData);
			
			rootElement.appendChild(e);
		}

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(file));
		transformer.transform(source, result);
	}
}
