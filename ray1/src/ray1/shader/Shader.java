package ray1.shader;

import ray1.IntersectionRecord;
import ray1.Light;
import ray1.Ray;
import ray1.Scene;
import egl.math.Colorf;

/**
 * This interface specifies what is necessary for an object to be a shader.
 * @author ags, pramook, zechenz
 */
public abstract class Shader {
	
	/**
	 * The material given to all surfaces unless another is specified.
	 */
	public static final Shader DEFAULT_SHADER = new Lambertian();
	
	protected Texture texture = null;
	public void setTexture(Texture t) { texture = t; }
	public Texture getTexture() { return texture; }
	
	/**	
	 * Calculate the intensity (color) for this material at the intersection described in
	 * the record contained in workspace.
	 * 	 
	 * @param outIntensity The color returned towards the source of the incoming ray.
	 * @param scene The scene in which the surface exists.
	 * @param ray The ray which intersected the surface.
	 * @param record The intersection record of where the ray intersected the surface.
	 * @param depth The recursion depth.
	 */
	public abstract void shade(Colorf outIntensity, Scene scene, Ray ray, IntersectionRecord record, int depth);
	
	/**
	* Initialize method
	*/
	public void init() {
		// do nothing
	};

	/**
	 * A utility method to check if there is any surface between the given intersection
	 * point and the given light. shadowRay is set to point from the intersection point
	 * towards the light.
	 * 
	 * @param scene The scene in which the surface exists.
	 * @param light A light in the scene.
	 * @param record The intersection point on a surface.
	 * @return true if there is any surface between the intersection point and the light;
	 * false otherwise.
	 */
	protected boolean isShadowed(Scene scene, Light light, IntersectionRecord record) {	
		
		Ray shadowRay = new Ray();
		
		// Setup the shadow ray to start at surface and end at light
		shadowRay.origin.set(record.location);
		shadowRay.direction.set(light.position).sub(record.location);
		
		double end = shadowRay.direction.len();
		shadowRay.direction.normalize();
		
		// Set the ray to end at the light
		shadowRay.makeOffsetSegment(end);
		
		return scene.getAnyIntersection(shadowRay);
	}
	
}