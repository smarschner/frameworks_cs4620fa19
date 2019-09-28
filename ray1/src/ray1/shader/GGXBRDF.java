package ray1.shader;

import egl.math.Colorf;
import egl.math.Vector3;
import egl.math.Vector3d;

/**
 * Evaluate microfacet BRDF function with GGX distribution
 * @author zechen
 */
public class GGXBRDF extends MicrofacetBRDF 
{
	
	GGXBRDF(Colorf diffuseReflectance, Texture diffuseReflectanceTexture, Colorf microfacetColor, float nt, float alpha) {
		super(diffuseReflectance, diffuseReflectanceTexture, microfacetColor, nt, alpha);
	}

	public String toString() {    
		return "GGX " + super.toString();
	}

	/**
	 * Evaluate the specular part of BRDF function value in microfacet model with GGX distribution
	 *
	 * @param IncomingVec Direction vector of the incoming ray.
	 * @param OutgoingVec Direction vector of the outgoing ray.
	 * @param SurfaceNormal Normal vector of the surface at the shaded point.
	 * @return evaluated BRDF function value
	 */
	public float EvalSpecular(Vector3d IncomingVec, Vector3d OutgoingVec, Vector3d SurfaceNormal)
	{
		// TODO#Ray Extra Credit: Evaluate the specular part of BRDF function of microfacet-based model with GGX distribution
		// Walter, Bruce, et al. 
		// "Microfacet models for refraction through rough surfaces." 
		// Proceedings of the 18th Eurographics conference on Rendering Techniques. Eurographics Association, 2007.


		
		return 1f;
	}
	
	public final float EvalGGXDist(Vector3d HalfVec, Vector3d SurfaceNormal)
	{		
		// TODO#Ray Extra Credit
		
		float D = 0;

		return D;
	}
	
	public final float G1_GGX(Vector3d v, Vector3d m, Vector3d n)
	{
		// TODO#Ray Extra Credit
		
		float ret = 0;

		return ret;
	}	
}
