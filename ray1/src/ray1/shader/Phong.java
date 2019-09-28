package ray1.shader;

import ray1.IntersectionRecord;
import ray1.Light;
import ray1.Ray;
import ray1.RayTracer;
import ray1.Scene;
import egl.math.Color;
import egl.math.Colorf;
import egl.math.Vector2;
import egl.math.Vector3;
import egl.math.Vector3d;

/**
 * A Phong material.
 *
 * @author ags, pramook, zechenz
 */
public class Phong extends ReflectionShader {

	/** The color of the diffuse reflection. */
	protected final Colorf diffuseColor = new Colorf(Color.White);
	public void setDiffuseColor(Colorf diffuseColor) { this.diffuseColor.set(diffuseColor); }
	public Colorf getDiffuseColor() {return new Colorf(diffuseColor);}

	/** The color of the specular reflection. */
	protected final Colorf specularColor = new Colorf(Color.White);
	public void setSpecularColor(Colorf specularColor) { this.specularColor.set(specularColor); }
	public Colorf getSpecularColor() {return new Colorf(specularColor);}

	/** The exponent controlling the sharpness of the specular reflection. */
	protected float exponent = 1.0f;
	public void setExponent(float exponent) { this.exponent = exponent; }
	public float getExponent() {return exponent;}

	public Phong() { }

	/**
	 * @see Object#toString()
	 */
	public String toString() {    
		return "Phong shader " + diffuseColor + " " + specularColor + " " + exponent + " end";
	}

	@Override
	public
	void init() {
		this.brdf = new PhongBRDF(diffuseColor, texture, specularColor, exponent);
	}
}
