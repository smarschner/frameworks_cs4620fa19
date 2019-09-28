package ray1;

import egl.math.Vector3d;

/**
 * A ray is simply an origin point and a direction vector.
 *
 * @author ags
 */
public class Ray {

  /**
   * This quantity represents a "small amount" to handle comparisons in
   * floating point computations.  It is often useful to have one global
   * value across the ray tracer that stands for a "small amount".
   */
  public static final double EPSILON = 1e-3f;

  /** The starting point of the ray. */
  public final Vector3d origin = new Vector3d();

  /** The normalized direction in which the ray travels. */
  public final Vector3d direction = new Vector3d();
  

  /**
  * It is convenient to have a ray have a start and end t values.
  * The start value lets the ray be offset slightly from surfaces
  * avoiding self intersection through numerical inaccuracies, and
  * the end value lets rays be cut off at certain points (such
  * as for shadow rays).
  */

  /** Starting t value of the ray **/
  public double start;

  /** Ending t value of the ray **/
  public double end;

  /**
   * Default constructor generates a trivial ray.
   */
  public Ray() {}

  /**
   * The explicit constructor. Values should be set here, and any
   * variables that need to be calculated should be done here.
   * @param newOrigin The origin of the new ray.
   * @param newDirection The direction of the new ray.
   */
  public Ray(Vector3d newOrigin, Vector3d newDirection) {

    origin.set(newOrigin);
    direction.set(newDirection);
  }

  public Ray(Ray r) {
	  origin.set(r.origin);
	  direction.set(r.direction);
	  start = r.start;
	  end = r.end;
  }

  /**
   * Sets this ray with the given direction and origin.
   * @param newOrigin the new origin point
   * @param newDirection the new direction vector
   */
  public void set(Vector3d newOrigin, Vector3d newDirection) {

	  origin.set(newOrigin);
	  direction.set(newDirection);
  }

  /**
   * Sets outPoint to the point on this ray t units from the origin.  Note that t can
   * be considered as distance along this ray only if the ray direction is normalized.
   * @param outPoint the output point
   * @param t The distance along the ray.
   */
  public void evaluate(Vector3d outPoint, float t) {

    outPoint.set(origin).addMultiple(t, direction);
  }
  
  /**
   * Sets outPoint to the point on this ray t units from the origin.  Note that t can
   * be considered as distance along this ray only if the ray direction is normalized.
   * @param outPoint the output point
   * @param t The distance along the ray.
   */
  public void evaluate(Vector3d outPoint, double t) {

    outPoint.set(origin).addMultiple((float) t, new Vector3d(direction));
  }
  
  
  /**
   * Moves the origin of the ray EPISILON units along ray.  Avoids self intersection
   * when casting rays from surfaces.
   */
  public void makeOffsetRay() {

    start = EPSILON;
    end = Float.POSITIVE_INFINITY;

  }

  /**
   * Offsets the ray origin by EPSILON and sets the end point of the ray to t.
   * @param newEnd the endpoint of the ray.
   */
  public void makeOffsetSegment(double newEnd) {

    start = EPSILON;
    end = newEnd;


  }
}