package egl.math;

/**
 * An RGBA color specified using floating point accuracy. This is most useful
 * for operations common in ray-tracing applications, as well as
 * HDR and similar features.
 * @author ags, eschweic
 */
public class Colorf extends Vector3 {
	/**
	 * The explicit constructor. Values are typically between 0.0 and 1.0.
	 * @param r The red value.
	 * @param g The green value.
	 * @param b The blue value.
	 */
	public Colorf(float r, float g, float b) {
		super(r, g, b);
	}


	/**
	 * Copy constructor.
	 * @param cf The color to copy.
	 */
	public Colorf(Colorf cf) {
		super(cf.r(), cf.g(), cf.b());
	}
	
	/**
	 * Construct a Colorf from a Color.
	 * @param c The Color to copy.
	 */
	public Colorf(Color c) {
		set(c);
	}
	
	/**
	 * Default constructor. Returns black.
	 */
	public Colorf() {
		super();
	}

	/**
	 * @return The red component.
	 */
	public float r() {
		return x;
	}

	/**
	 * @return The green component.
	 */
	public float g() {
		return y;
	}

	/**
	 * @return The blue component.
	 */
	public float b() {
		return z;
	}

	/**
	 * Sets this Colorf to be equivalent to a given Color.
	 * @param c The Color to be copied.
	 * @return this.
	 */
	public Colorf set(Color c) {
		this.x = c.r() / 255.0f;
		this.y = c.g() / 255.0f;
		this.z = c.b() / 255.0f;
		return this;
	}

	/**
	 * Clamps the each component of this color to between [min,max]
	 * @param min the minimum value
	 * @param max the maximum value
	 */
	public void clamp(float min, float max) {
		x = Math.max(Math.min(x, max), min);
		y = Math.max(Math.min(y, max), min);
		z = Math.max(Math.min(z, max), min);
	}

	/**
	 * Gamma corrects this color.
	 * @param gamma the gamma value to use (2.2 is generally used).
	 */
	public void gammaCorrect(float gamma) {
		float inverseGamma = 1.0f / gamma;
		super.pow(inverseGamma);
	}
	
	/**
	 * Returns a Color object. Values are clamped to [0.0, 1.0] before being mapped to
	 * [0, 255]. This Colorf object is not modified.
	 * @return A Color that corresponds to this Colorf.
	 */
	public Color toColor() {
		float cr = Math.max(Math.min(x, 1.0f), 0.0f);
		float cg = Math.max(Math.min(y, 1.0f), 0.0f);
		float cb = Math.max(Math.min(z, 1.0f), 0.0f);
		// Adding 0.5 to each component rounds them instead of truncating.
		return new Color((byte) (cr * 255f + 0.5f),
				   		 (byte) (cg * 255f + 0.5f),
				   		 (byte) (cb * 255f + 0.5f),
				   		 255);
	}
}
