package common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import common.texture.ACTextureGenerator;
import common.texture.TexGenCheckerBoard;

public class Texture extends ACUniqueObject implements IXMLDocWriteable {
	public static enum Type {
		FILE,
		GENERATOR
	}
	
	public Type type;
	public String file;
	public ACTextureGenerator generator;
	
	public Texture() {
		setGenerator(new TexGenCheckerBoard());
	}
	
	public void setGenerator(ACTextureGenerator gen) {
		type = Type.GENERATOR;
		file = null;
		generator = gen;
	}
	public void setFile(String f) {
		type = Type.FILE;
		file = f;
		generator = null;
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
			generator.saveData(doc, eGen);
			eData.appendChild(eGen);
			break;
		default:
			break;
		}
	}
}
