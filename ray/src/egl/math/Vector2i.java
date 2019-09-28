package egl.math;
import java.util.AbstractList;

/**
 * A 2-component single-precision vector
 * @author Cristian, srm
 */
public class Vector2i extends AbstractList<Integer> implements Cloneable {
	public static final int NUM_COMPONENTS = 2;
	
	/** 
	 * Coordinates of the vector
	 */
	public int x, y;

	/**
	 * Inline constructor
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 */
	public Vector2i(int _x, int _y) {
		x = _x;
		y = _y;
	}

	/**
	 * Copy constructor
	 * @param v Vector
	 */
	public Vector2i(Vector2i v) {
		this(v.x, v.y);
	}

	/**
	 * Uniform Value constructor
	 * @param f Value
	 */
	public Vector2i(int f) {
		this(f, f);
	}

	/**
	 * Zero Vector constructor
	 */
	public Vector2i() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+"}i";
	}
	
	/**
	 * Inline setter
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 * @return this
	 */
	public Vector2i set(int _x, int _y) {
		x = _x;
		y = _y;
		return this;
	}

	/**
	 * Set this vector to the zero vector
	 * @return this
	 */
	public Vector2i setZero() {
		return set(0);
	}

	/**
	 * Set this vector to uniform values
	 * @param f Value
	 * @return this
	 */
	public Vector2i set(int f) {
		return set(f, f);
	}

	/**
	 * Copy a vector into this vector
	 * @param v Vector
	 * @return this
	 */
	public Vector2i set(Vector2i v) {
		return set(v.x, v.y);
	}

	/**
	 * Sets this vector to a multiple of XYZ components
	 * <pre>
	 * This = s * {_x, _y}
	 * </pre>
	 * @param s Scalar
	 * @param _x X component
	 * @param _y Y component
	 * @return this
	 */
	public Vector2i setMultiple(int s, int _x, int _y) {
		x = s * _x;
		y = s * _y;
		return this;
	}

	/**
	 * Sets this vector to its componentwise prodict with another
	 * <pre>
	 * This = s * v
	 * </pre>
	 * @param s Scalar
	 * @param v Vector
	 * @return this
	 */
	public Vector2i setMultiple(int s, Vector2i v) {
		return setMultiple(s, v.x, v.y);
	}
	
	/**
	 * In-place addition
	 * <pre>
	 * This = this + {_x, _y}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @return this
	 */
	public Vector2i add(int _x, int _y) {
		x += _x;
		y += _y;
		return this;
	}

	/**
	 * In-place addition
	 * <pre>
	 * This = this + v
	 * </pre>
	 * @param v Vector
	 * @return this
	 */
	public Vector2i add(Vector2i v) {
		return add(v.x, v.y);
	}

	/**
	 * In-place uniform addition
	 * <pre>
	 * This = this + {f, f}
	 * </pre>
	 * @param f Value
	 * @return this
	 */
	public Vector2i add(int f) {
		return add(f, f);
	}

	/**
	 * In-place scaled addition
	 * <pre>
	 * This = this + s * {_x, _y}
	 * </pre>
	 * @param s Scalar
	 * @param _x X component
	 * @param _y Y component
	 * @return this
	 */
	public Vector2i addMultiple(int s, int _x, int _y) {
		x += s * _x;
		y += s * _y;
		return this;
	}

	/**
	 * In-place scaled addition
	 * <pre>
	 * This = this + s * v
	 * </pre>
	 * @param s Scalar
	 * @param v Vector
	 * @return this
	 */
	public Vector2i addMultiple(int s, Vector2i v) {
		return addMultiple(s, v.x, v.y);
	}
	
	/**
	 * In-place subtraction
	 * <pre>
	 * This = this - {_x, _y}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @return this
	 */
	public Vector2i sub(int _x, int _y) {
		x -= _x;
		y -= _y;
		return this;
	}

	/**
	 * In-place subtraction
	 * <pre>
	 * This = this - v
	 * </pre>
	 * @param v Vector
	 * @return this
	 */
	public Vector2i sub(Vector2i v) {
		return sub(v.x, v.y);
	}

	/**
	 * In-place uniform subtraction
	 * <pre>
	 * This = this - {f, f}
	 * </pre>
	 * @param f Value
	 * @return this
	 */
	public Vector2i sub(int f) {
		return sub(f, f);
	}

	/**
	 * In-place scaled subtraction
	 * <pre>
	 * This = this - s * {_x, _y}
	 * </pre>
	 * @param s Scalar
	 * @param _x X component
	 * @param _y Y component
	 * @return this
	 */
	public Vector2i subMultiple(int s, int _x, int _y) {
		x -= s * _x;
		y -= s * _y;
		return this;
	}

	/**
	 * In-place scaled subtraction
	 * <pre>
	 * This = this - s * v
	 * </pre>
	 * @param s Scalar
	 * @param v Vector
	 * @return this
	 */
	public Vector2i subMultiple(int s, Vector2i v) {
		return subMultiple(s, v.x, v.y);
	}
	
	/**
	 * In-place componentwise multiplication
	 * <pre>
	 * This = this * {_x, _y}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z Component
	 * @return this
	 */
	public Vector2i mul(int _x, int _y) {
		x *= _x;
		y *= _y;
		return this;
	}

	/**
	 * In-place componentwise multiplication
	 * <pre>
	 * This = this * v
	 * </pre>
	 * @param v Vector
	 * @return this
	 */
	public Vector2i mul(Vector2i v) {
		return mul(v.x, v.y);
	}

	/**
	 * In-place uniform multiplication
	 * <pre>
	 * This = this * {f, f}
	 * </pre>
	 * @param f Value
	 * @return this
	 */
	public Vector2i mul(int f) {
		return mul(f, f);
	}

	/**
	 * In-place componentwise division
	 * <pre>
	 * This = this / {_x, _y}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @return this
	 */
	public Vector2i div(int _x, int _y) {
		x /= _x;
		y /= _y;
		return this;
	}

	/**
	 * In-place division
	 * <pre>
	 * This = this / v
	 * </pre>
	 * @param v Vector
	 * @return this
	 */
	public Vector2i div(Vector2i v) {
		return div(v.x, v.y);
	}

	/**
	 * In-place uniform division
	 * <pre>
	 * This = this * {1 / f, 1 / f}
	 * </pre>
	 * @param f Value
	 * @return this
	 */
	public Vector2i div(int f) {
		return mul(1 / f);
	}

	// Integer Power Function
	private static int powi(int base, int exp)
	{
	    int result = 1;
	    while (exp != 0)
	    {
	        if ((exp & 1) == 1)
	            result *= base;
	        exp >>= 1;
	        base *= base;
	    }

	    return result;
	}

	/**
	 * In-Place componentwise power
	 * <pre>
	 * This = this ^ {_x, _y}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @return this
	 */
	public Vector2i pow(int _x, int _y) {
		x = powi(x, _x);
		y = powi(y, _y);
		return this;
	}

	/**
	 * In-Place componentwise power
	 * <pre>
	 * This = this ^ v
	 * </pre>
	 * @param v Vector
	 * @return this
	 */
	public Vector2i pow(Vector2i v) {
		return pow(v.x, v.y);
	}

	/**
	 * In-place uniform componentwise power
	 * <pre>
	 * This = this ^ {f, f}
	 * </pre>
	 * @param f Value
	 * @return this
	 */
	public Vector2i pow(int f) {
		return pow(f, f);
	}
	
	/**
	 * In-place componentwise exponentiation
	 * <pre>
	 * This = {_x, _y} ^ This
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @return this
	 */
	public Vector2i exp(int _x, int _y) {
		x = powi(_x, x);
		y = powi(_y, y);
		return this;
	}

	/**
	 * In-place componentwise exponentiation
	 * <pre>
	 * This = v ^ This
	 * </pre>
	 * @param v Vector
	 * @return this
	 */
	public Vector2i exp(Vector2i v) {
		return exp(v.x, v.y);
	}

	/**
	 * In-place uniform componentwise exponentiation
	 * <pre>
	 * This = {f, f} ^ This
	 * </pre>
	 * @param f Value
	 * @return this
	 */
	public Vector2i exp(int f) {
		return exp(f, f);
	}

	/**
	 * In-place absolute value
	 * @return this
	 */
	public Vector2i abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		return this;
	}

	/**
	 * In-place negation
	 * @return this
	 */
	public Vector2i negate() {
		x = -x;
		y = -y;
		return this;
	}
	
	/**
	 * Dot product between this and another vector
	 * @param v [{@link Vector2i ARR}] Vector
	 * @return Vector Dot Product
	 */
	public int dot(Vector2i v) {
		return x * v.x + y * v.y;
	}

	/**
	 * This vector's dot product with itself
	 * @return This vector's length squared
	 */
	public int lenSq() {
		return dot(this);
	}

	/**
	 * Calculates the length
	 * @return This vector's length
	 */
	public double len() {
		return Math.sqrt(lenSq());
	}

	/**
	 * Calculates squared distance between this vector and another
	 * @param v [{@link Vector2i POS}] Vector
	 * @return Squared distance
	 */
	public int distSq(Vector2i v) {
		int ox = x - v.x;
		int oy = y - v.y;
		return ox * ox + oy * oy;
	}

	/**
	 * Calculates distance between this vector and another
	 * @param v [{@link Vector2i POS}] Vector
	 * @return Distance
	 */
	public double dist(Vector2i v) {
		return Math.sqrt(distSq(v));
	}
	
    /**
    * Calculate angle between this vector and another
    * @param v [{@link Vector2i DIRN}] Vector
    * @return Angle between vectors
    */
    public double angle(Vector2i v) {
    	return Math.acos(dot(v) / (len() * v.len()));
    }

	/**
	 * Checks component equality between this vector and another
	 * @param v Vector
	 * @return True if all components equal
	 */
	public boolean equals(Vector2i v) {
		return x == v.x && y == v.y;
	}

	@Override
	public Integer set(int index, Integer element) {
		int oldVal;
		switch (index) {
		case 0:
			oldVal = x;
			x = element;
			return oldVal;
		case 1:
			oldVal = y;
			y = element;
			return oldVal;
		default: throw new IndexOutOfBoundsException();
		}
	}
	
	@Override
	public Integer get(int index) {
		switch(index) {
		case 0: return x;
		case 1: return y;
		default: throw new IndexOutOfBoundsException();
		}
	}
	@Override
	public int size() {
		return NUM_COMPONENTS;
	}

	@Override
	public Vector2i clone() {
		return new Vector2i(this);
	}
}

