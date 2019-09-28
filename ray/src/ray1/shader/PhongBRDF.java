package ray1.shader;

import egl.math.Color;
import egl.math.Colorf;
import egl.math.Vector2;
import egl.math.Vector3;
import egl.math.Vector3d;

public class PhongBRDF extends BRDF {

	/** The color of the specular reflection. */
	protected final Colorf specularColor = new Colorf(Color.White);

	/** The exponent controlling the sharpness of the specular reflection. */
	protected float exponent = 1.0f;

	public String toString() {    
		return "Phong BRDF" + 
				", specularColor = " + specularColor + 
				", exponent = " + exponent + super.toString();
	}
	
	PhongBRDF(Colorf diffuseReflectance, Texture diffuseReflectanceTexture, Colorf specularColor, float exponent) 
	{
		super(diffuseReflectance, diffuseReflectanceTexture);
		this.specularColor.set(specularColor);
		this.exponent = exponent;
	}

	@Override
	public void EvalBRDF(Vector3d incoming, Vector3d outgoing, Vector3d surfaceNormal, Vector2 texCoords, Colorf BRDFValue) 
	{	
		// TODO#Ray Task 5: Evaluate the BRDF value of Phong reflection model

	}

}
