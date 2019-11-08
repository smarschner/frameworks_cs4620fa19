package common.texture;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import common.IXMLDocWriteable;
import egl.math.Color;
import egl.math.Vector2i;

public abstract class ACTextureGenerator implements IXMLDocWriteable {
	public final Vector2i size = new Vector2i(64, 64);
	
	public Vector2i getSize() {
		return size;
	}
	public void setSize(Vector2i s) {
		size.set(s);
	}
	
	public abstract void getColor(float u, float v, Color outColor);
	
	@Override
	public void saveData(Document doc, Element eData) {
		Element e = doc.createElement("size");
		e.appendChild(doc.createTextNode(size.x + " " + size.y));
		eData.appendChild(e);
	}
}
