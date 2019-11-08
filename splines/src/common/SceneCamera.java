package common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import egl.math.Vector2d;

public class SceneCamera extends SceneObject {
	public final Vector2d zPlanes = new Vector2d(0.01, 1000.0);
	public final Vector2d imageSize = new Vector2d(0.02, 0.02);
	public boolean isPerspective = true;
	public float exposure = 2.0f;
	
	public void setZPlanes(Vector2d v) {
		zPlanes.set(v);
	}
	public void setOrthographic(Vector2d size) {
		isPerspective = false;
		imageSize.set(size);
	}
	public void setPerspective(Vector2d size) {
		isPerspective = true;
		imageSize.set(size);
	}
	public void setExposure(float exposure) {
		this.exposure = exposure;
	}
	
	@Override
	public void saveData(Document d, Element e) {
		super.saveData(d, e);
		e.setAttribute("type", SceneCamera.class.getName());
		
		Element eZP = d.createElement("zPlanes");
		eZP.appendChild(d.createTextNode(zPlanes.x + " " + zPlanes.y));
		e.appendChild(eZP);
		
		Element eIS = d.createElement(isPerspective ? "perspective" : "orthographic");
		eIS.appendChild(d.createTextNode(imageSize.x + " " + imageSize.y));
		e.appendChild(eIS);
		
		Element eExp = d.createElement("exposure");
		eExp.appendChild(d.createTextNode(Float.toString(exposure)));
		e.appendChild(eExp);
	}
}
