package ray1.accel;

import ray1.IntersectionRecord;
import ray1.Ray;
import ray1.surface.Surface;

/**
 * A class representing an arbitrary data structure for accessing surfaces and intersecting
 * them with rays. 
 *
 */
public interface AccelStruct {
	/**
	 * Given a set of surfaces, perform the necessary initialization steps to prepare for future
	 * intersect() calls.
	 * @param surfaces The surfaces of the scene.
	 */
	void build(Surface[] surfaces);
	
	/**
	 * Given a ray, intersect it surfaces in the scene. This will be called many times by the ray tracer,
	 * and should be optimized for performance.
	 * 
	 * @param outRecord Record the relevant intersection information here if the ray intersects
	 * a surface; otherwise, do not modify this.
	 * @param rayIn The ray that is intersected with the scene.
	 * @param anyIntersection A boolean that is true if the caller is only concerned with finding any
	 * ray-surface intersection rather than the first; otherwise, the first intersection must be recorded.
	 * @return true if the ray intersects a surface in the scene; false otherwise.
	 */
	public boolean intersect(IntersectionRecord outRecord, Ray rayIn, boolean anyIntersection);
}
