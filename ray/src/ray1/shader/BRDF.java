package ray1.shader;

import egl.math.Colorf;
import egl.math.Vector2;
import egl.math.Vector3d;

/**
 * Abstract class to represent a BRDF.  Has the ability to compute an RGB BRDF value for a particular
 * surface reflection geometry.  All BRDFs have a texturable diffuse component available.
 * 
 * @author srm
 */
public abstract class BRDF {

	/**
	 * The diffuse reflectance controlling the diffuse component.
	 */
	private final Colorf diffuseReflectance = new Colorf();
	
	/**
	 * If non-null, a texture that defines the diffuse reflectance, overriding the value
	 * of diffuseReflectance.
	 */
	private Texture diffuseReflectanceTexture;

	public BRDF(Colorf diffuseReflectance, Texture diffuseReflectanceTexture) {
		super();
		this.diffuseReflectance.set(diffuseReflectance);
		this.diffuseReflectanceTexture = diffuseReflectanceTexture;
	}
	
	protected Colorf getDiffuseReflectance(Vector2 texCoords) {
		return (diffuseReflectanceTexture == null) ? diffuseReflectance : diffuseReflectanceTexture.getTexColor(texCoords);
	}

	/**
	 * Compute the BRDF value.
	 *
	 * @param incoming Direction vector of the incoming ray.
	 * @param outgoing Direction vector of the outgoing ray.
	 * @param surfaceNormal Normal vector of the surface at the shaded point.
	 * @param texCoords The texture coordinates of the shaded point.
	 * @param BRDFValue (output) The computed BRDF value.
	 */
	public abstract void EvalBRDF(Vector3d incoming, Vector3d outgoing, Vector3d surfaceNormal, Vector2 texCoords, Colorf BRDFValue);

	/**
	* Initialization method -- overridden in any subclasses that need to do initialization.
	*/
	public void init() {
		// do nothing
	}
	
	public String toString() {
		return ", diffuseReflectance = " + (diffuseReflectanceTexture == null ? diffuseReflectance : "(textured)");
	}

}