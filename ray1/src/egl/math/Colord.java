package egl.math;

/**
 * An RGB color specified using floating point accuracy. This is most useful
 * for operations common in ray-tracing applications, as well as
 * HDR and similar features.
 * @author eschweic
 */
public class Colord extends Vector3d {
	/**
	 * The explicit constructor. Values are typically between 0.0 and 1.0.
	 * @param r The red value.
	 * @param g The green value.
	 * @param b The blue value.
	 */
	public Colord(double r, double g, double b) {
		super(r, g, b);
	}


	/**
	 * Copy constructor.
	 * @param cf The color to copy.
	 */
	public Colord(Colord cd) {
		super(cd.r(), cd.g(), cd.b());
	}
	
	/**
	 * Construct a Colord from a Color.
	 * @param c The Color to copy.
	 */
	public Colord(Color c) {
		set(c);
	}
	
	/**
	 * Construct a Colord from a Vector3d, it should have values between 0 and 1!
	 * @param v The Vector3d to copy.
	 */
	public Colord(Vector3d v) {
		super(v);
	}
	
	/**
	 * Default constructor. Returns black.
	 */
	public Colord() {
		super();
	}

	/**
	 * @return The red component.
	 */
	public double r() {
		return x;
	}

	/**
	 * @return The green component.
	 */
	public double g() {
		return y;
	}

	/**
	 * @return The blue component.
	 */
	public double b() {
		return z;
	}

	/**
	 * Sets this Colord to be equivalent to a given Color.
	 * @param c The Color to be copied.
	 * @return this.
	 */
	public Colord set(Color c) {
		this.x = c.r() / 255.0;
		this.y = c.g() / 255.0;
		this.z = c.b() / 255.0;
		return this;
	}

	/**
	 * Clamps the each component of this color to between [min,max]
	 * @param min the minimum value
	 * @param max the maximum value
	 */
	public void clamp(double min, double max) {
		x = Math.max(Math.min(x, max), min);
		y = Math.max(Math.min(y, max), min);
		z = Math.max(Math.min(z, max), min);
	}

	/**
	 * Gamma corrects this color.
	 * @param gamma the gamma value to use (2.2 is generally used).
	 */
	public void gammaCorrect(double gamma) {
		double inverseGamma = 1.0 / gamma;
		super.pow(inverseGamma);
	}
	
	/**
	 * Returns a Color object. Values are clamped to [0.0, 1.0] before being mapped to
	 * [0, 255]. This Colord object is not modified.
	 * @return A Color that corresponds to this Colord.
	 */
	public Color toColor() {
		double cr = Math.max(Math.min(x, 1.0), 0.0);
		double cg = Math.max(Math.min(y, 1.0), 0.0);
		double cb = Math.max(Math.min(z, 1.0), 0.0);
		// Adding 0.5 to each component rounds them instead of truncating.
		return new Color((byte) (cr * 255d + 0.5),
				   		 (byte) (cg * 255d + 0.5),
				   		 (byte) (cb * 255d + 0.5),
				   		 255);
	}
}
