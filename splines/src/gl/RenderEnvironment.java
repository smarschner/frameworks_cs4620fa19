package gl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.BufferUtils;

import common.Cubemap;
import common.Material;
import common.Mesh;
import common.SceneObject;
import common.Texture;
import common.texture.TexCubeMap;
import mesh.MeshConverter;
import mesh.MeshData;
import mesh.OBJMesh_Archive;
import mesh.OBJParser;
import egl.GL.PixelFormat;
import egl.GL.PixelType;
import egl.GL.TextureTarget;
import egl.GLTexture;
import egl.IDisposable;
import egl.NativeMem;
import egl.math.Color;
import egl.math.Vector2;
import egl.math.Vector2i;
import egl.math.Vector3;
import egl.math.Vector3i;

public class RenderEnvironment implements IDisposable {
	/**
	 * OpenGL Mesh Resources
	 */
	public final HashMap<String, RenderMesh> meshes = new HashMap<>();
	/**
	 * OpenGL Texture Resources
	 */
	public final HashMap<String, GLTexture> textures = new HashMap<>();
	/**
	 * OpenGL Material (Programs/Shaders) Resources
	 */
	public final HashMap<String, RenderMaterial> materials = new HashMap<>();

	/**
	 * Scene Root Node
	 */
	public RenderObject root;
	/**
	 * List Of Cameras In The Scene
	 */
	public final ArrayList<RenderCamera> cameras = new ArrayList<>();
	/**
	 * List Of Lights In The Scene
	 */
	public final ArrayList<RenderLight> lights = new ArrayList<>();
	/**
	 * The Size Of The Window Displaying The Scene
	 */
	public final Vector2 viewportSize = new Vector2();

	public TexCubeMap cubemap = new TexCubeMap();
	
	public RenderEnvironment(Vector2 viewSize) {
		viewportSize.set(viewSize);
	}
	@Override
	public void dispose() {
		for(Entry<String, RenderMesh> e : meshes.entrySet()) e.getValue().dispose();
		meshes.clear();
		
		for(Entry<String, GLTexture> e : textures.entrySet()) e.getValue().dispose();
		textures.clear();
		
		for(Entry<String, RenderMaterial> e : materials.entrySet()) e.getValue().dispose();
		materials.clear();
	}
	
	public void addTexture(Texture t) {
		GLTexture rt = new GLTexture(TextureTarget.Texture2D, true);
		switch (t.type) {
		case FILE:
			// Load From Image File
			try {
				rt.setImage2D(t.file, false);
			} catch (Exception e) {
				System.err.println("Problem when loading texture from file: " + e.getMessage());
				rt.dispose();
				return;
			}
			break;
		case GENERATOR:
			// Generate An Image
			Vector2i size = t.generator.getSize();
			ByteBuffer bb = NativeMem.createByteBuffer(size.x * size.y * 4);
			Color c = new Color();
			for(int y = 0;y < size.y;y++) {
				float texY = (y + 0.5f) / (size.y);
				for(int x = 0;x < size.x;x++) {
					float texX = (x + 0.5f) / (size.x);
					t.generator.getColor(texX, texY, c);
					bb.put(c.R);
					bb.put(c.G);
					bb.put(c.B);
					bb.put(c.A);
				}
			}
			bb.flip();
			rt.setImage(size.x, size.y, PixelFormat.Rgba, PixelType.UnsignedByte, bb, false);
			break;
		default:
			rt.dispose();
			return;
		}

		// Add To Dictionary
		textures.put(t.getID().name, rt);
	}
	public boolean removeTexture(String name) {
		GLTexture t = textures.get(name);
		if(t == null) return false;
		textures.remove(name);
		t.dispose();
		return true;
	}

	public void addCubemap(Cubemap c) {
		try {
			cubemap.createCubeMap(c.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addMesh(Mesh m) {
		MeshData md = new MeshData();
		switch (m.type) {
		case FILE:
			// Load From OBJ
			OBJMesh_Archive om = OBJParser.parse(m.file);
			if(om == null || !om.hasData()) return;
			if(om.hasUVs()) {
				if(!om.hasNormals()) {
					System.err.println("Input Mesh Must Contain Normals If UVs are specified");
					return;
				}
				
				// Mesh Description
				md.indexCount = om.triangles.size() * 3;
				md.vertexCount = om.vertices.size();
				md.positions = BufferUtils.createFloatBuffer(md.vertexCount * 3);
				md.normals = BufferUtils.createFloatBuffer(md.vertexCount * 3);
				md.uvs = BufferUtils.createFloatBuffer(md.vertexCount * 2);
				md.indices = BufferUtils.createIntBuffer(md.indexCount);
				
				// Flatten Data
				for(Vector3i vert : om.vertices) {
					Vector3 v = om.positions.get(vert.x);
					md.positions.put(v.x);
					md.positions.put(v.y);
					md.positions.put(v.z);
					v = om.normals.get(vert.z);
					md.normals.put(v.x);
					md.normals.put(v.y);
					md.normals.put(v.z);
					Vector2 v2 = om.uvs.get(vert.y);
					md.uvs.put(v2.x);
					md.uvs.put(v2.y);
				}
				for(Vector3i t : om.triangles) {
					md.indices.put(t.x);
					md.indices.put(t.y);
					md.indices.put(t.z);
				}
			}
			else {
				// Set The Triangle Indices To The Vertex's Position Index
				for(Vector3i t : om.triangles) {
					t.x = om.vertices.get(t.x).x;
					t.y = om.vertices.get(t.y).x;
					t.z = om.vertices.get(t.z).x;
				}

				// Convert The Data
				md = MeshConverter.convertToFaceNormals(om.positions, om.triangles);
				if(!md.hasData()) {
					System.err.println("Mesh Conversion Error - No Output Data");
					return;
				}

				// Add Some Interesting UVs
				md.uvs = BufferUtils.createFloatBuffer(md.vertexCount * 2);
				for(int i = 0;i < md.indexCount;) {
					int v = md.indices.get(i++) * 2;
					md.uvs.put(v++, 0); md.uvs.put(v, 0);
					v = md.indices.get(i++) * 2;
					md.uvs.put(v++, 0); md.uvs.put(v, 1);
					v = md.indices.get(i++) * 2;
					md.uvs.put(v++, 1); md.uvs.put(v, 1);
				}
			}
			break;
		case GENERATOR:
			// Generate Mesh
			m.generator.generate(md, m.genOptions);
			if(!md.hasData()) {
				System.err.println("Mesh Conversion Error - No Output Data");
				return;
			}
			break;
		default:
			return;
		}

		// Create OpenGL Resource
		RenderMesh rm = new RenderMesh(m);
		rm.build(md);

		// Add To Dictionary
		meshes.put(m.getID().name, rm);
	}
	public boolean removeMesh(String name) {
		RenderMesh m = meshes.get(name);
		if(m == null) return false;
		meshes.remove(name);
		m.dispose();
		return true;
	}

	public void addMaterial(Material m) {
		// OpenGL Resource
		RenderMaterial rm = new RenderMaterial(m);

		// Link Up Everything
		rm.loadShaders(this);

		// Add To Dictionary
		materials.put(m.getID().name, rm);
	}
	public boolean removeMaterial(String name) {
		RenderMaterial m = materials.get(name);
		if(m == null) return false;
		materials.remove(name);
		m.dispose();
		return true;
	}

	/**
	 * Find The RenderObject Containing A Specific SceneObject
	 * @param o Scene Node
	 * @return Null If No Node Can Be Found
	 */
	public RenderObject findObject(SceneObject o) {
		return findObject(root, o);
	}
	private RenderObject findObject(RenderObject ro, SceneObject o) {
		if(ro.sceneObject == o) return ro;
		for(RenderObject cro : ro.children) {
			if((ro = findObject(cro, o)) != null) return ro;
		}
		return null;
	}

	public void linkMaterials() {
		for(RenderMaterial rm : materials.values()) {
			rm.createInputProviders(this);
		}
	}

	public void linkObjectMaterials() {
		linkObjectMaterials(root);
	}
	private void linkObjectMaterials(RenderObject ro) {
		ro.material = materials.get(ro.sceneObject.material);
		for(RenderObject cro : ro.children) {
			linkObjectMaterials(cro);
		}
	}
	public void linkObjectMeshes() {
		linkObjectMeshes(root);
	}
	private void linkObjectMeshes(RenderObject ro) {
		ro.mesh = meshes.get(ro.sceneObject.mesh);
		for(RenderObject cro : ro.children) {
			linkObjectMeshes(cro);
		}
	}
	public void linkObjectResources() {
		linkObjectResources(root);
	}
	private void linkObjectResources(RenderObject ro) {
		ro.mesh = meshes.get(ro.sceneObject.mesh);
		ro.material = materials.get(ro.sceneObject.material);
		for(RenderObject cro : ro.children) {
			linkObjectResources(cro);
		}
	}

	public void linkResources() {
		linkMaterials();
		linkObjectResources();
	}
}
