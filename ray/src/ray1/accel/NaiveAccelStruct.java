package ray1.accel;

import ray1.IntersectionRecord;
import ray1.Ray;
import ray1.surface.Surface;
/**
 * Provide a fake AABB that performs a linear search.
 * 
 * @author ss932, pramook
 */
public class NaiveAccelStruct implements AccelStruct {
	
	/** A shared surfaces array that will be used across every node in the tree */
	private Surface []surfaces;

	public NaiveAccelStruct() { }
	
	/**
	 * Set outRecord to the first intersection of ray with the scene. Return true
	 * if there was an intersection and false otherwise. If no intersection was
	 * found outRecord is unchanged.
	 *
	 * @param outRecord the output IntersectionRecord
	 * @param ray the ray to intersect
	 * @param anyIntersection if true, will immediately return when found an intersection
	 * @return true if and intersection is found.
	 */
	public boolean intersect(IntersectionRecord outRecord, Ray rayIn, boolean anyIntersection) {
		boolean ret = false;
		IntersectionRecord tmp = new IntersectionRecord();
		Ray ray = new Ray(rayIn.origin, rayIn.direction);
		ray.start = rayIn.start;
		ray.end = rayIn.end;
		for(int i = 0; i < surfaces.length; i++) {
			if(surfaces[i].intersect(tmp, ray) && tmp.t < ray.end ) {
				if(anyIntersection) return true;
				ret = true;
				ray.end = tmp.t;
				if(outRecord != null)
					outRecord.set(tmp);
			}
		}
		return ret;
	}
	
	@Override
	public void build(Surface[] surfaces) {
		this.surfaces = surfaces;
	}
}

