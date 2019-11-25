package common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import mesh.gen.MeshGenOptions;
import mesh.gen.MeshGenerator;

public class Mesh extends ACUniqueObject implements IXMLDocWriteable {
	public static enum Type {
		FILE,
		GENERATOR
	}
	
	public Type type;
	public String file;
	public MeshGenerator generator;
	public final MeshGenOptions genOptions = new MeshGenOptions();
	
	public Mesh() {
		genOptions.divisionsLatitude = 80;
		genOptions.divisionsLongitude = 120;
		genOptions.innerRadius = 0.25f;
	}
	
	public void setGenerator(MeshGenerator gen) {
		type = Type.GENERATOR;
		file = null;
		generator = gen;
	}
	public void setFile(String f) {
		type = Type.FILE;
		file = f;
		generator = null;
	}
	public void setGenOptions(MeshGenOptions o) {
		genOptions.divisionsLatitude = o.divisionsLatitude;
		genOptions.divisionsLongitude = o.divisionsLongitude;
		genOptions.innerRadius = o.innerRadius;
	}

	@Override
	public void saveData(Document doc, Element eData) {
		switch (type) {
		case FILE:
			Element eFile = doc.createElement("file");
			eFile.appendChild(doc.createTextNode(file));
			eData.appendChild(eFile);
			break;
		case GENERATOR:
			Element eGen = doc.createElement("generator");
			eGen.setAttribute("type", generator.getClass().getName());
			eData.appendChild(eGen);
			
			Element eOpt = doc.createElement("genOptions");
			genOptions.saveData(doc, eOpt);
			eData.appendChild(eOpt);
			break;
		default:
			break;
		}
	}
}
