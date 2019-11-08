package common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import egl.math.Color;

public class Material extends ACUniqueObject implements IXMLDocWriteable {
	public static final String T_AMBIENT = "Ambient";
	public static final String T_NORMAL = "Normal";
	public static final String T_GOURAUD = "Gouraud";
	public static final String T_LAMBERTIAN = "Lambertian";
	public static final String T_PHONG = "Phong";
	public static final String T_GLASS = "Glass";
	public static final String T_GLAZED = "Glazed";
	public static final String T_COOKTORRANCE = "CookTorrance";
	public static final String T_NORMAL_MAPPED = "NormalMapped";
	public static final String T_SPLINE = "Spline";
	public static final String T_DISPMAPPED = "DispMapped";

	public static class InputProvider implements IXMLDocWriteable {
		public static enum Type {
			TEXTURE,
			COLOR
		}

		public Type type;
		public String texture;
		public Color color;

		public InputProvider() {
			setColor(Color.Transparent);
		}

		public void setTexture(String t) { 
			type = Type.TEXTURE;
			texture = t;
			color = null;
		}
		public void setColor(Color c) { 
			type = Type.COLOR;
			texture = null;
			color = c;
		}
	
		@Override
		public void saveData(Document doc, Element eData) {
			switch (type) {
			case COLOR:
				Element ec = doc.createElement("color");
				ec.appendChild(doc.createTextNode(
						color.r() + " " +
						color.g() + " " +
						color.b() + " " +
						color.a() + " "
						));
				eData.appendChild(ec);
				break;
			case TEXTURE:
				Element et = doc.createElement("texture");
				et.appendChild(doc.createTextNode(texture));
				eData.appendChild(et);
				break;
			default:
				break;
			}
		}
	}

	public String materialType = T_AMBIENT;
	public InputProvider inputDiffuse = new InputProvider();
	public InputProvider inputNormal = new InputProvider();
	public InputProvider inputSpecular = new InputProvider();
	public InputProvider inputFiberColor = new InputProvider();
	public InputProvider inputFiberDirection = new InputProvider();
	
	// Shininess of the surface, used with Blinn-Phong materials
	public float shininess;
	// Roughness of the surface, used with Cook-Torrance materials
	public float roughness;
	// Displacement magnitude, used with displacement mapped materials
	public float dispMagnitude;

	public Material() {
		inputDiffuse.setColor(Color.LightGray);
		inputNormal.setColor(new Color(128, 128, 255, 255));
		inputSpecular.setColor(Color.White);
		inputFiberColor.setColor(Color.Brown);
		inputFiberDirection.setColor(new Color(255, 0, 0));
		shininess = 50f;
		roughness = 0.3f;
		dispMagnitude = 0.3f;
	}

	public void setType(String t) {
		materialType = t;
	}
	public void setDiffuse(InputProvider p) {
		inputDiffuse = p;
	}
	public void setNormal(InputProvider p) {
		inputNormal = p;
	}
	public void setSpecular(InputProvider p) {
		inputSpecular = p;
	}
	public void setFiberColor(InputProvider p) {
		inputFiberColor = p;
	}
	public void setFiberDirection(InputProvider p) {
		inputFiberDirection = p;
	}
	public void setShininess(float shininess) {
		this.shininess = shininess;
	}
	public void setRoughness(float roughness) {
		this.roughness = roughness;
	}
	public void setDispMagnitude(float dispMagnitude) {
		this.dispMagnitude = dispMagnitude;
	}

	@Override
	public void saveData(Document doc, Element eData) {
		Element e = doc.createElement("type");
		e.appendChild(doc.createTextNode(materialType));
		eData.appendChild(e);
		
		e = doc.createElement("diffuse");
		inputDiffuse.saveData(doc, e);
		eData.appendChild(e);
		
		e = doc.createElement("fibercolor");
		inputFiberColor.saveData(doc, e);
		eData.appendChild(e);
		
		e = doc.createElement("fiberdirection");
		inputFiberDirection.saveData(doc, e);
		eData.appendChild(e);
		
		e = doc.createElement("normal");
		inputNormal.saveData(doc, e);
		eData.appendChild(e);
		
		e = doc.createElement("specular");
		inputSpecular.saveData(doc, e);
		eData.appendChild(e);
		
		e = doc.createElement("shininess");
		e.appendChild(doc.createTextNode(Float.toString(shininess)));
		eData.appendChild(e);
		
		e = doc.createElement("roughness");
		e.appendChild(doc.createTextNode(Float.toString(roughness)));
		eData.appendChild(e);
		
		e = doc.createElement("dispMagnitude");
		e.appendChild(doc.createTextNode(Float.toString(dispMagnitude)));
		eData.appendChild(e);
	}
}
