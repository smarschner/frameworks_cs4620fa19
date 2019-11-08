package common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import egl.math.Vector3d;

public class SceneLight extends SceneObject {
	/**
	 * RGB intensity of the light
	 */
	public final Vector3d intensity = new Vector3d(1.0);
	public boolean isAmbient = false;
	
	public void setIntensity(Vector3d v) {
		intensity.set(v);
	}
	public void setIsAmbient(boolean isAmbient) {
		this.isAmbient = isAmbient;
	}
	
	@Override
	public void saveData(Document d, Element e) {
		super.saveData(d, e);
		e.setAttribute("type", SceneLight.class.getName());
		
		Element ed = d.createElement("intensity");
		ed.appendChild(d.createTextNode(intensity.x + " " + intensity.y + " " + intensity.z));
		e.appendChild(ed);
		
		ed = d.createElement("isAmbient");
		ed.appendChild(d.createTextNode(Boolean.toString(isAmbient)));
		e.appendChild(ed);
	}
}
