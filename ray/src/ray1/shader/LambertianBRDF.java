package ray1.shader;

import egl.math.Colorf;
import egl.math.Vector2;
import egl.math.Vector3;
import egl.math.Vector3d;

/**
 * A Lambertian material scatters light equally in all directions; its BRDF value is
 * a constant
 *
 * @author srm, zechenz
 */
public class LambertianBRDF extends BRDF {

	LambertianBRDF(Colorf diffuseReflectance, Texture diffuseReflectanceTexture) {
		super(diffuseReflectance, diffuseReflectanceTexture);
	}

	public String toString() {    
		return "Lambertian BRDF" + super.toString();
	}

	@Override
	public void EvalBRDF(Vector3d incoming, Vector3d outgoing, Vector3d surfaceNormal, Vector2 texCoords, Colorf BRDFValue) {
		// TODO#Ray: Evaluate the BRDF value of Lambertian reflectance and set it to variable BRDFValue
		// Hint: getDiffuseReflectance() method can be helpful.
	}

}
