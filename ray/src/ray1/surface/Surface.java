package ray1.surface;

import java.util.ArrayList;

import ray1.IntersectionRecord;
import ray1.Ray;
import ray1.shader.Shader;
import ray1.OBJFace;
import ray1.OBJMesh;
import ray1.OBJMesh.OBJFileFormatException;
import ray1.accel.BboxUtils;
import egl.math.Vector3d;
import egl.math.Vector2;
import egl.math.Vector2d;
import egl.math.Vector3;

/**
 * Abstract base class for all surfaces.  Provides access for shader and
 * intersection uniformly for all surfaces.
 *
 * @author ags, ss932
 */
public abstract class Surface {
	/** The average position of the surface. Usually calculated by taking the average of 
	 * all the vertices. This point will be used in AABB tree construction. */
	public Vector3d averagePosition;
	
	/** The smaller coordinate (x, y, z) of the bounding box of this surface */
	public Vector3d minBound;
	
	/** The larger coordinate (x, y, z) of the bounding box of this surface */
	public Vector3d maxBound; 
	
	/** Shader to be used to shade this surface. */
	protected Shader shader = Shader.DEFAULT_SHADER;
	public void setShader(Shader shader) { this.shader = shader; }
	public Shader getShader() { return shader; }
	
	public Vector3d getAveragePosition() { return averagePosition; } 
	public Vector3d getMinBound() { return minBound; }
	public Vector3d getMaxBound() { return maxBound; }	

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
	
	/**
	 * Compute the bounding box and store the result in
	 * averagePosition, minBound, and maxBound.
	 */
	public abstract void computeBoundingBox();

}
