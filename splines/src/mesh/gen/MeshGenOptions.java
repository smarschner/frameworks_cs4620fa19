package mesh.gen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Struct Holding Options That Govern Mesh Tesselation
 * @author Cristian
 *
 */
public class MeshGenOptions {
	/**
	 * Number Of Times To Cut A Mesh Along The Latitude Lines
	 */
	public int divisionsLatitude;
	/**
	 * Number Of Times To Cut A Mesh Along The Longitude Lines
	 */
	public int divisionsLongitude;
	/**
	 * Extra Value (Used For Torus)
	 */
	public float innerRadius;
	
	public void setDivLatitude(int d) {
		divisionsLatitude = d;
	}
	public void setDivLongitude(int d) {
		divisionsLongitude = d;
	}
	public void setInnerRadius(float r) {
		innerRadius = r;
	}

	public void saveData(Document doc, Element eData) {
		Element e = doc.createElement("divLatitude");
		e.appendChild(doc.createTextNode(Integer.toString(divisionsLatitude)));
		eData.appendChild(e);
		
		e = doc.createElement("divLongitude");
		e.appendChild(doc.createTextNode(Integer.toString(divisionsLongitude)));
		eData.appendChild(e);
		
		e = doc.createElement("innerRadius");
		e.appendChild(doc.createTextNode(Float.toString(innerRadius)));
		eData.appendChild(e);
	}
}
