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

    public float[] rotateManip = new float[]{ 0, 0, 0, 0, 0, 0 };
    public boolean[] scaleManip = new boolean[]{ false, false, false };
    public boolean[] translateManip = new boolean[]{ false, false, false };

    public float[] curScale = new float[]{ 0, 0, 0 };
    public float[] curRotate = new float[]{ 0, 0, 0 };
    public float[] prevRotate = new float[]{ 0, 0, 0 };
    public float[] curTranslate = new float[]{ 0, 0, 0 };

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

    public void setRotateX(float[] m) {
        if(m.length != 2) throw new AssertionError("Rotation X Must Have 2 Values");
        if(m[0] < -180 || m[1] < -180 || m[0] > 180 || m[1] > 180)
            throw new AssertionError("Rotation cannot exceed 180 degrees");
        rotateManip[0] = m[0];
        rotateManip[1] = m[1];
    }

    public void setRotateY(float[] m) {
        if(m.length != 2) throw new AssertionError("Rotation Y Must Have 2 Values");
        if(m[0] < -180 || m[1] < -180 || m[0] > 180 || m[1] > 180)
            throw new AssertionError("Rotation cannot exceed 180 degrees");
        rotateManip[2] = m[0];
        rotateManip[3] = m[1];
    }

    public void setRotateZ(float[] m) {
        if(m.length != 2) throw new AssertionError("Rotation Z Must Have 2 Values");
        if(m[0] < -180 || m[1] < -180 || m[0] > 180 || m[1] > 180)
            throw new AssertionError("Rotation cannot exceed 180 degrees");
        rotateManip[4] = m[0];
        rotateManip[5] = m[1];
    }

    public void setEnableScale(int[] m) {
        if(m.length != 3) throw new AssertionError("Scale Must Have 3 Values (for X, Y and Z)");
        for (int i = 0; i < 3; i++)
            if (m[i] == 1) scaleManip[i] = true;
    }

    public void setEnableTranslate(int[] m) {
        if(m.length != 3) throw new AssertionError("Translate Must Have 3 Values (for X, Y and Z)");
        for (int i = 0; i < 3; i++)
            if (m[i] == 1) translateManip[i] = true;
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
