package ray1.surface;

import java.util.ArrayList;

import ray1.IntersectionRecord;
import ray1.Ray;
import ray1.shader.Shader;

/**
 * Abstract base class for all surfaces.  Provides access for shader and
 * intersection uniformly for all surfaces.
 *
 * @author ags, ss932
 */
public abstract class Surface {

	/** Shader to be used to shade this surface. */
	protected Shader shader = Shader.DEFAULT_SHADER;
	public void setShader(Shader shader) { this.shader = shader; }
	public Shader getShader() { return shader; }

	/**Initialize method*/
	public void init() {
		// do nothing
	}
	
	/**
	 * Tests this surface for intersection with ray. If an intersection is found
	 * record is filled out with the information about the intersection and the
	 * method returns true. It returns false otherwise and the information in
	 * outRecord is not modified.
	 *
	 * @param outRecord the output IntersectionRecord
	 * @param ray the ray to intersect
	 * @return true if the surface intersects the ray
	 */
	public abstract boolean intersect(IntersectionRecord outRecord, Ray ray);
	
	/**
	 * Add this surface (and any child surfaces) to the array list in.
	 */
	public void appendRenderableSurfaces(ArrayList<Surface> in) {
		in.add(this);
	}

}
