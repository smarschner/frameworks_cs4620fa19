package common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Cubemap extends ACUniqueObject implements IXMLDocWriteable {
	public static enum Type {
		FILE
	}
	
	public Type type;
	public String file;
	
	public Cubemap() {
	}
	
	public void setFile(String f) {
		type = Type.FILE;
		file = f;
	}

	@Override
	public void saveData(Document doc, Element eData) {
		switch (type) {
		case FILE:
			Element eFile = doc.createElement("file");
			eFile.appendChild(doc.createTextNode(file));
			eData.appendChild(eFile);
			break;
		default:
			break;
		}
	}
}
