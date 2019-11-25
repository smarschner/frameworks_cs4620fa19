package common.texture;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import egl.math.Color;
import egl.math.Vector2i;

public class TexGenColor extends ACTextureGenerator {
	public final Color color = new Color();

	public TexGenColor() {
		setSize(new Vector2i(1, 1));
		setColor(Color.White);
	}
	
	public void setColor(Color c) {
		color.set(c);
	}
	
	@Override
	public void getColor(float u, float v, Color outColor) {
		outColor.set(color);
	}
	
	@Override
	public void saveData(Document doc, Element eData) {
		super.saveData(doc, eData);
		
		Element e = doc.createElement("color");
		e.appendChild(doc.createTextNode(
				color.r() + " " +
				color.g() + " " +
				color.b() + " " +
				color.a() + " "
				));
		eData.appendChild(e);
	}
}
