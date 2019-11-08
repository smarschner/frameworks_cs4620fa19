package common.texture;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import egl.math.Color;

public abstract class ACTextureGeneratorTwoColor extends ACTextureGenerator {
	public final Color color1 = new Color(Color.Red);
	public final Color color2 = new Color(Color.Blue);
	
	public void setColor1(Color c) {
		color1.set(c);
	}
	public void setColor2(Color c) {
		color2.set(c);
	}
	
	@Override
	public void saveData(Document doc, Element eData) {
		super.saveData(doc, eData);
		
		Element e = doc.createElement("color1");
		e.appendChild(doc.createTextNode(
				color1.r() + " " +
				color1.g() + " " +
				color1.b() + " " +
				color1.a() + " "
				));
		eData.appendChild(e);
		
		e = doc.createElement("color2");
		e.appendChild(doc.createTextNode(
				color2.r() + " " +
				color2.g() + " " +
				color2.b() + " " +
				color2.a() + " "
				));
		eData.appendChild(e);
	}
}
