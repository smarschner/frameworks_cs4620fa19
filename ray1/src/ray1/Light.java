package ray1;

import egl.math.Color;
import egl.math.Colorf;
import egl.math.Vector3;

/**
 * This class represents a basic point light which is infinitely small and emits
 * a constant power in all directions. This is a useful idealization of a small
 * light emitter.
 *
 * @author ags
 */
public class Light {
	
	/** Where the light is located in space. */
	public final Vector3 position = new Vector3();
	public void setPosition(Vector3 position) { this.position.set(position); }
	
	/** How bright the light is. */
	public final Colorf intensity = new Colorf(Color.White);
	public void setIntensity(Colorf intensity) { this.intensity.set(intensity); }
	
	/**Initialize method*/
	public void init() {
		// do nothing
	}

	/**
	 * Default constructor.  Produces a unit intensity light at the origin.
	 */
	public Light() { }
	
	/**
	 * @see Object#toString()
	 */
	public String toString() {
		
		return "light: " + position + " " + intensity;
	}
}