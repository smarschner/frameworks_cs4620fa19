package common.texture;

import egl.math.Color;
import egl.math.Colord;
import egl.math.Matrix3d;
import egl.math.Vector2i;
import egl.math.Vector3d;

public class TexGenSphereNormalMap extends ACTextureGenerator {
	// 0.5f means that the discs are tangent to each other
	// For greater values discs intersect
	// For smaller values there is a "planar" area between the discs
	private float bumpRadius;
	// The number of rows and columns
	// There should be one disc in each row and column
	private int resolution;
	
	// The top of the sphere is [0.0, 1.0, 0.0] when theta = don't care, phi = PI/2
	private Vector3d getNormalFromSphCoords(float theta, float phi) {
		Vector3d normal = new Vector3d();
		normal.x = -Math.sin(theta) * Math.sin(phi);
		normal.y = Math.cos(phi);
		normal.z = -Math.cos(theta) * Math.sin(phi);
		
		return normal;
	}
	
	public TexGenSphereNormalMap() {
		this.bumpRadius = 0.5f;
		this.resolution = 10;
		this.setSize(new Vector2i(256));
	}
	
	public void setBumpRadius(float bumpRadius) {
		this.bumpRadius = bumpRadius;
	}
	
	public void setResolution(int resolution) {
		this.resolution = resolution;
	}
	
	@Override
	public void getColor(float u, float v, Color outColor) {
		// We assume we're at a no-bump zone
		Vector3d normal = new Vector3d(0, 0, 1);
		
		// Determine the normalized distance between the closest disc center
		float uPos = u * resolution;
		float vPos = v * resolution;
		float bumpCenterU = Math.round(uPos);
		float bumpCenterV = Math.round(vPos);
		float du = uPos - bumpCenterU;
		float dv = vPos - bumpCenterV;
		
		// du, dv ranges from -0.5 -> 0.5
		float dist = (float) Math.sqrt(du*du + dv*dv);
		
		// If we are NOT at the corner of the square, so we are on a flat part
		if (dist < bumpRadius) {
			// Find the normal on the regular sphere in object space
			float theta = (float) (u * Math.PI * 2.0);
			float phi = (float) ((1 - v) * Math.PI);
			Vector3d normPoint = this.getNormalFromSphCoords(theta, phi);

			// Find the normal of the bump in object space
			float bumpCenterTheta = (float) (bumpCenterU / resolution * Math.PI * 2.0);
			float bumpCenterPhi = (float) ((1 - bumpCenterV / resolution) * Math.PI);
			normal.set(this.getNormalFromSphCoords(bumpCenterTheta, bumpCenterPhi));

			// Convert normal from object space to tangent space
			Vector3d tangent = new Vector3d(normPoint.z, 0, -normPoint.x).normalize();
			Vector3d bitangent = new Vector3d(tangent).cross(normPoint);
			new Matrix3d(tangent, bitangent, normPoint).mul(normal).normalize();
		}
		
		// Transform [-1, 1] -> [0, 1], because we will encode normals as colors
		normal.add(1.0f).mul(0.5f);
		outColor.set(new Colord(normal));
	}
}
