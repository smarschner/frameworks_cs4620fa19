package egl.math;
import java.util.AbstractList;

/**
 * A 3-component single-precision vector
 * @author Cristian, srm
 *
 */
public class Vector3i extends AbstractList<Integer> implements Cloneable {
	public static final int NUM_COMPONENTS = 3;
	
	/** 
	 * Coordinates of the vector
	 */
	public int x, y, z;

	/**
	 * Inline constructor
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 * @param _z Z coordinate
	 */
	public Vector3i(int _x, int _y, int _z) {
		x = _x;
		y = _y;
		z = _z;
	}

	/**
	 * Copy constructor
	 * @param v Vector
	 */
	public Vector3i(Vector3i v) {
		this(v.x, v.y, v.z);
	}
	
	/**
	 * Uniform value constructor
	 * @param f Value
	 */
	public Vector3i(int f) {
		this(f, f, f);
	}

	/**
	 * Zero vector constructor
	 */
	public Vector3i() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+", "+z+"}i";
	}
	
	/**
	 * Inline setter
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 * @param _z Z coordinate
	 * @return This
	 */
	public Vector3i set(int _x, int _y, int _z) {
		x = _x;
		y = _y;
		z = _z;
		return this;
	}

	/**
	 * Set this vector to the zero vector
	 * @return This
	 */
	public Vector3i setZero() {
		return set(0);
	}

	/**
	 * Set This vector to uniform values
	 * @param f Value
	 * @return This
	 */
	public Vector3i set(int f) {
		return set(f, f, f);
	}

	/**
	 * Copy vector into this vector
	 * @param v Vector
	 * @return This
	 */
	public Vector3i set(Vector3i v) {
		return set(v.x, v.y, v.z);
	}

	/**
	 * Sets this vector to a multiple of XYZ components
	 * <pre>
	 * This = s * {_x, _y, _z}
	 * </pre>
	 * @param s Scalar
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3i setMultiple(int s, int _x, int _y, int _z) {
		x = s * _x;
		y = s * _y;
		z = s * _z;
		return this;
	}

	/**
	 * Sets this vector to its componentwise prodict with another
	 * <pre>
	 * This = s * v
	 * </pre>
	 * @param s Scalar
	 * @param v Vector
	 * @return This
	 */
	public Vector3i setMultiple(int s, Vector3i v) {
		return setMultiple(s, v.x, v.y, v.z);
	}

	/**
	 * In-place addition
	 * <pre>
	 * This = This + {_x, _y, _z}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3i add(int _x, int _y, int _z) {
		x += _x;
		y += _y;
		z += _z;
		return this;
	}

	/**
	 * In-place addition
	 * <pre>
	 * This = This + v
	 * </pre>
	 * @param v Vector
	 * @return This
	 */
	public Vector3i add(Vector3i v) {
		return add(v.x, v.y, v.z);
	}

	/**
	 * In-place uniform addition
	 * <pre>
	 * This = This + {f, f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector3i add(int f) {
		return add(f, f, f);
	}

	/**
	 * In-place scaled addition
	 * <pre>
	 * This = This + s * {_x, _y, _z}
	 * </pre>
	 * @param s Scalar
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3i addMultiple(int s, int _x, int _y, int _z) {
		x += s * _x;
		y += s * _y;
		z += s * _z;
		return this;
	}

	/**
	 * In-place scaled addition
	 * <pre>
	 * This = This + s * v
	 * </pre>
	 * @param s Scalar
	 * @param v Vector
	 * @return This
	 */
	public Vector3i addMultiple(int s, Vector3i v) {
		return addMultiple(s, v.x, v.y, v.z);
	}
	
	/**
	 * In-place subtraction
	 * <pre>
	 * This = This - {_x, _y, _z}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3i sub(int _x, int _y, int _z) {
		x -= _x;
		y -= _y;
		z -= _z;
		return this;
	}

	/**
	 * In-place subtraction
	 * <pre>
	 * This = This - v
	 * </pre>
	 * @param v Vector
	 * @return This
	 */
	public Vector3i sub(Vector3i v) {
		return sub(v.x, v.y, v.z);
	}

	/**
	 * In-place uniform subtraction
	 * <pre>
	 * This = This - {f, f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector3i sub(int f) {
		return sub(f, f, f);
	}

	/**
	 * In-place scaled subtraction
	 * <pre>
	 * This = This - s * {_x, _y, _z}
	 * </pre>
	 * @param s Scalar
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3i subMultiple(int s, int _x, int _y, int _z) {
		x -= s * _x;
		y -= s * _y;
		z -= s * _z;
		return this;
	}

	/**
	 * In-place scaled subtraction
	 * <pre>
	 * This = This - s * v
	 * </pre>
	 * @param s Scalar
	 * @param v Vector
	 * @return This
	 */
	public Vector3i subMultiple(int s, Vector3i v) {
		return subMultiple(s, v.x, v.y, v.z);
	}
	
	/**
	 * In-place componentwise multiplication
	 * <pre>
	 * This = This * {_x, _y, _z}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3i mul(int _x, int _y, int _z) {
		x *= _x;
		y *= _y;
		z *= _z;
		return this;
	}

	/**
	 * In-place componentwise multiplication
	 * <pre>
	 * This = This * v
	 * </pre>
	 * @param v Vector
	 * @return This
	 */
	public Vector3i mul(Vector3i v) {
		return mul(v.x, v.y, v.z);
	}

	/**
	 * In-place uniform multiplication
	 * <pre>
	 * This = This * {f, f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector3i mul(int f) {
		return mul(f, f, f);
	}

	/**
	 * In-place componentwise division
	 * <pre>
	 * This = This / {_x, _y, _z}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3i div(int _x, int _y, int _z) {
		x /= _x;
		y /= _y;
		z /= _z;
		return this;
	}

	/**
	 * In-place componentwise division
	 * <pre>
	 * This = This / v
	 * </pre>
	 * @param v Vector
	 * @return This
	 */
	public Vector3i div(Vector3i v) {
		return div(v.x, v.y, v.z);
	}

	/**
	 * In-place uniform division
	 * <pre>
	 * This = This * {1 / f, 1 / f, 1 / f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector3i div(int f) {
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
	 * This = This ^ {_x, _y, _z}
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3i pow(int _x, int _y, int _z) {
		x = powi(x, _x);
		y = powi(y, _y);
		z = powi(z, _z);
		return this;
	}

	/**
	 * In-Place componentwise power
	 * <pre>
	 * This = This ^ v
	 * </pre>
	 * @param v Vector
	 * @return This
	 */
	public Vector3i pow(Vector3i v) {
		return pow(v.x, v.y, v.z);
	}

	/**
	 * In-place uniform componentwise power
	 * <pre>
	 * This = This ^ {f, f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector3i pow(int f) {
		return pow(f, f, f);
	}
	
	/**
	 * In-place componentwise exponentiation
	 * <pre>
	 * This = {_x, _y, _z} ^ This
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3i exp(int _x, int _y, int _z) {
		x = powi(_x, x);
		y = powi(_y, y);
		z = powi(_z, z);
		return this;
	}

	/**
	 * In-place componentwise exponentiation
	 * <pre>
	 * This = v ^ This
	 * </pre>
	 * @param v Vector
	 * @return This
	 */
	public Vector3i exp(Vector3i v) {
		return exp(v.x, v.y, v.z);
	}

	/**
	 * In-place uniform componentwise exponentiation
	 * <pre>
	 * This = {f, f, f} ^ This
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector3i exp(int f) {
		return exp(f, f, f);
	}
	
	/**
	 * In-place absolute value
	 * @return This
	 */
	public Vector3i abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		if(z < 0) z = -z;
		return this;
	}

	/**
	 * In-Place negation
	 * @return This
	 */
	public Vector3i negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	/**
	 * Dot product between this and another vector
	 * @param v [{@link Vector3i ARR}] Vector
	 * @return Vector Dot Product
	 */
	public int dot(Vector3i v) {
		return x * v.x + y * v.y + z * v.z;
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
	 * @param v [{@link Vector3i POS}] Vector
	 * @return Length Squared Of Offset Vector
	 */
	public int distSq(Vector3i v) {
		int ox = x - v.x;
		int oy = y - v.y;
		int oz = z - v.z;
		return ox * ox + oy * oy + oz * oz;
	}

	/**
	 * Calculates distance between this vector and another
	 * <pre>
	 * Let off = This - v
	 * Ret len(off)
	 * </pre>
	 * @param v [{@link Vector3i POS}] Vector
	 * @return Length Of Offset Vector
	 */
	public double dist(Vector3i v) {
		return Math.sqrt(distSq(v));
	}
		
    /**
    * Calculate angle between this vector and another
    * @param v [{@link Vector3i DIRN}] Vector
    * @return Angle between vectors
    */
    public double angle(Vector3i v) {
    	return Math.acos(dot(v) / (len() * v.len()));
    }

	/**
	 * Checks component equality between this vector and another
	 * @param v Vector
	 * @return True if all components equal
	 */
	public boolean equals(Vector3i v) {
		return x == v.x && y == v.y && z == v.z;
	}
	
	/**
	 * Checks whether this vector is zero.
	 * @return True if this vector is exactly zero.
	 */
	public boolean isZero() {
		return x == 0 && y == 0 && z == 0;
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
		case 2:
			oldVal = z;
			z = element;
			return oldVal;
		default: throw new IndexOutOfBoundsException();
		}
	}
	
	@Override
	public Integer get(int index) {
		switch(index) {
		case 0: return x;
		case 1: return y;
		case 2: return z;
		default: throw new IndexOutOfBoundsException();
		}
	}
	@Override
	public int size() {
		return NUM_COMPONENTS;
	}

	@Override
	public Vector3i clone() {
		return new Vector3i(this);
	}
}

