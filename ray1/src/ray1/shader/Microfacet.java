package ray1.shader;

import egl.math.Color;
import egl.math.Colorf;
import egl.math.Vector2;

/**
 * Microfacet-based shader
 *
 * @author zechenz
 */
public class Microfacet extends ReflectionShader {
	
	/** refractive index of material, used in Fresnel factor*/
	protected float nt;
	public void setNt(float nt) {this.nt = nt;}
	public float getNt() {return nt;}	
	
	/**
	 * The roughness parameter of the microfacet model
	 */
	protected float alpha;
	public void setAlpha(float t) { alpha = t; }
	public float getAlpha() { return alpha; }

	/** A color that scales the microfacet reflection. */
	protected final Colorf microfacetColor = new Colorf(1, 1, 1);
	public void setMicrofacetColor(Colorf microfacetColor) { this.microfacetColor.set(microfacetColor); }
	public Colorf getMicrofacetColor() {return new Colorf(microfacetColor);}
	
	/** The color of the diffuse reflection. */
	protected final Colorf diffuseColor = new Colorf(0, 0, 0);
	public void setDiffuseColor(Colorf diffuseColor) { this.diffuseColor.set(diffuseColor); }
	public Colorf getDiffuseColor() {return new Colorf(diffuseColor);}
	
	protected Microfacet() { }

	/**
	 * @see Object#toString()
	 */
	public String toString() {    
		return "Microfacet shader, microfacet color " + microfacetColor + " diffuseColor " + diffuseColor + brdf.toString();
	}
	
}
