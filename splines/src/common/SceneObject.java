package common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import egl.math.Matrix4;
import egl.math.Vector3;

public class SceneObject extends ACUniqueObject implements IXMLDocWriteable {
	public final Matrix4 transformation = new Matrix4();
	
	public String parent = null;
	public String material = null;
	public String mesh = null;
	
	public void addRotation(Vector3 rot) {
		rot = rot.clone().mul((float)(Math.PI/180.0));
		transformation.mulAfter(Matrix4.createRotationX(rot.x));
		transformation.mulAfter(Matrix4.createRotationY(rot.y));
		transformation.mulAfter(Matrix4.createRotationZ(rot.z));
	}
	public void addTranslation(Vector3 v) {
		transformation.mulAfter(Matrix4.createTranslation(v));
	}
	public void addScale(Vector3 v) {
		transformation.mulAfter(Matrix4.createScale(v));			
	}
	public void setMatrix(float[] m) {
		if(m.length != 16) throw new AssertionError("Matrix Must Have 16 Values");
		transformation.set(new Matrix4(m));
	}
	
	public void setParent(String s) {
		parent = s;
	}
	public void setMaterial(String s) {
		material = s;
	}
	public void setMesh(String s) {
		mesh = s;
	}
	
	public boolean hasMesh(String s) {
		if(mesh == null) return s == null;
		else return s != null && mesh.equals(s);
	}
	public boolean hasMaterial(String s) {
		if(material == null) return s == null;
		else return s != null && material.equals(s);
	}
	public boolean hasParent(String s) {
		if(parent == null) return s == null;
		else return s != null && parent.equals(s);
	}

	@Override
	public void saveData(Document d, Element e) {
		// Set Transformation
		Element eTrans = d.createElement("matrix");
		String sTrans = "" + transformation.m[0];
		for(int i = 1;i < transformation.m.length;i++) {
			sTrans += " " + transformation.m[i];
		}
		eTrans.appendChild(d.createTextNode(sTrans));
		e.appendChild(eTrans);
		
		// Save Bindings
		if(parent != null) {
			Element t = d.createElement("parent");
			t.appendChild(d.createTextNode(parent));
			e.appendChild(t);
		}
		if(mesh != null) {
			Element t = d.createElement("mesh");
			t.appendChild(d.createTextNode(mesh));
			e.appendChild(t);
		}
		if(material != null) {
			Element t = d.createElement("material");
			t.appendChild(d.createTextNode(material));
			e.appendChild(t);
		}
	}
}
