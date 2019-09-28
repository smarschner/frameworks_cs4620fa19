package ray1;

import egl.math.Vector2d;
import egl.math.Vector3d;
import ray1.surface.Surface;

/**
 * This class is really just a struct, holding necessary information about a
 * particular intersection point.
 *
 * @author ags
 */
public class IntersectionRecord {

  /** The location where the intersection occurred. */
  public final Vector3d location = new Vector3d();

  /** The normal of the surface at the intersection location. */
  public final Vector3d normal = new Vector3d();

  /** The texture coordinates of the intersection point */
  public final Vector2d texCoords = new Vector2d();
  
  /** A reference to the actual surface. */
  public Surface surface = null;

  /** The t value along the ray at which the intersection occurred. */
  public double t = 0;  
  
  /**
   * Set this intersection record to the value of inRecord
   *
   * @param inRecord the input record
   */
  public void set(IntersectionRecord inRecord) {
    location.set(inRecord.location);
    normal.set(inRecord.normal);
    texCoords.set(inRecord.texCoords);
    surface = inRecord.surface;   
    t = inRecord.t;
  }
}