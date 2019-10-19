package egl.math;
import java.util.AbstractList;

/**
 * A 3-component single-precision vector
 * @author Cristian, srm
 *
 */
public class Vector3 extends AbstractList<Float> implements Cloneable {
	public static final int NUM_COMPONENTS = 3;
	
	/** 
	 * Coordinates of the vector
	 */
	public float x, y, z;

	/**
	 * Inline constructor
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 * @param _z Z coordinate
	 */
	public Vector3(float _x, float _y, float _z) {
		x = _x;
		y = _y;
		z = _z;
	}

	/**
	 * Copy constructor
	 * @param v Vector
	 */
	public Vector3(Vector3 v) {
		this(v.x, v.y, v.z);
	}

	/**
	 * Copy constructor
	 * @param v Vector
	 */
	public Vector3(Vector3d v) {
		this((float)v.x, (float)v.y, (float)v.z);
	}
	
	/**
	 * Uniform value constructor
	 * @param f Value
	 */
	public Vector3(float f) {
		this(f, f, f);
	}

	/**
	 * Zero vector constructor
	 */
	public Vector3() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+", "+z+"}f";
	}
	
	/**
	 * Inline setter
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 * @param _z Z coordinate
	 * @return This
	 */
	public Vector3 set(float _x, float _y, float _z) {
		x = _x;
		y = _y;
		z = _z;
		return this;
	}

	/**
	 * Set this vector to the zero vector
	 * @return This
	 */
	public Vector3 setZero() {
		return set(0);
	}

	/**
	 * Set This vector to uniform values
	 * @param f Value
	 * @return This
	 */
	public Vector3 set(float f) {
		return set(f, f, f);
	}

	/**
	 * Copy vector into this vector
	 * @param v Vector
	 * @return This
	 */
	public Vector3 set(Vector3 v) {
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
	public Vector3 setMultiple(float s, float _x, float _y, float _z) {
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
	public Vector3 setMultiple(float s, Vector3 v) {
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
	public Vector3 add(float _x, float _y, float _z) {
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
	public Vector3 add(Vector3 v) {
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
	public Vector3 add(float f) {
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
	public Vector3 addMultiple(float s, float _x, float _y, float _z) {
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
	public Vector3 addMultiple(float s, Vector3 v) {
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
	public Vector3 sub(float _x, float _y, float _z) {
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
	public Vector3 sub(Vector3 v) {
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
	public Vector3 sub(float f) {
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
	public Vector3 subMultiple(float s, float _x, float _y, float _z) {
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
	public Vector3 subMultiple(float s, Vector3 v) {
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
	public Vector3 mul(float _x, float _y, float _z) {
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
	public Vector3 mul(Vector3 v) {
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
	public Vector3 mul(float f) {
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
	public Vector3 div(float _x, float _y, float _z) {
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
	public Vector3 div(Vector3 v) {
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
	public Vector3 div(float f) {
		return mul(1 / f);
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
	public Vector3 pow(float _x, float _y, float _z) {
		x = (float)Math.pow(x, _x);
		y = (float)Math.pow(y, _y);
		z = (float)Math.pow(z, _z);
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
	public Vector3 pow(Vector3 v) {
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
	public Vector3 pow(float f) {
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
	public Vector3 exp(float _x, float _y, float _z) {
		x = (float)Math.pow(_x, x);
		y = (float)Math.pow(_y, y);
		z = (float)Math.pow(_z, z);
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
	public Vector3 exp(Vector3 v) {
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
	public Vector3 exp(float f) {
		return exp(f, f, f);
	}
	
	/**
	 * In-place logarithm
	 * <pre>
	 * This = log[base = This]({_x, _y, _z})
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3 log(float _x, float _y, float _z) {
		x = (float)(Math.log(x) / Math.log(_x));
		y = (float)(Math.log(y) / Math.log(_y));
		z = (float)(Math.log(z) / Math.log(_z));
		return this;
	}

	/**
	 * In-place logarithm
	 * <pre>
	 * This = log[base = This](v)
	 * </pre>
	 * @param v Vector
	 * @return This
	 */
	public Vector3 log(Vector3 v) {
		return log(v.x, v.y, v.z);
	}

	/**
	 * In-place uniform logarithm
	 * <pre>
	 * This = log[base = This]({f, f, f})
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector3 log(float f) {
		return log(f, f, f);
	}
	
	/**
	 * In-place absolute value
	 * @return This
	 */
	public Vector3 abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		if(z < 0) z = -z;
		return this;
	}

	/**
	 * In-Place negation
	 * @return This
	 */
	public Vector3 negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	/**
	 * Dot product between this and another vector
	 * @param v [{@link Vector3 ARR}] Vector
	 * @return Vector Dot Product
	 */
	public float dot(Vector3 v) {
		return x * v.x + y * v.y + z * v.z;
	}

	/**
	 * This vector's dot product with itself
	 * @return This vector's length squared
	 */
	public float lenSq() {
		return dot(this);
	}

	/**
	 * Calculates the length
	 * @return This vector's length
	 */
	public float len() {
		return (float)Math.sqrt(lenSq());
	}

	/**
	 * Calculates squared distance between this vector and another
	 * @param v [{@link Vector3 POS}] Vector
	 * @return Length Squared Of Offset Vector
	 */
	public float distSq(Vector3 v) {
		float ox = x - v.x;
		float oy = y - v.y;
		float oz = z - v.z;
		return ox * ox + oy * oy + oz * oz;
	}

	/**
	 * Calculates distance between this vector and another
	 * <pre>
	 * Let off = This - v
	 * Ret len(off)
	 * </pre>
	 * @param v [{@link Vector3 POS}] Vector
	 * @return Length Of Offset Vector
	 */
	public float dist(Vector3 v) {
		return (float)Math.sqrt(distSq(v));
	}

	/**
	 * Calculate angle between this vector and another
 	 * @param v [{@link Vector3 DIRN}] Vector
	 * @return Angle Between Vectors (Radians)
	 */
	public float angle(Vector3 v) {
		return (float)Math.acos(dot(v) / (len() * v.len()));
	}
	
	/**
	 * In-place cross product
	 * <pre>
	 * This = cross(This,v)
	 * </pre>
	 * @param v Right Side Vector
	 * @return This
	 */
	public Vector3 cross(Vector3 v) {
		return set(
			y * v.z - z * v.y,
			z * v.x - x * v.z,
			x * v.y - y * v.x
			);
	}
	
	/**
	 * In-place normalization to unit length
	 * @return This
	 */
	public Vector3 normalize() {
		return mul(1 / len());
	}

	/**
	 * In-place linear interpolation between this vector and another 
	 * <pre>
	 * This = (1 - r) * This + r * v
	 * </pre>
	 * @param v Vector
	 * @param r Ratio
	 * @return This
	 */
	public Vector3 lerp(Vector3 v, float r) {
		return mul(1 - r).addMultiple(r, v);
	}
	
	/**
	 * Checks component equality between this vector and another
	 * @param v Vector
	 * @return True if all components equal
	 */
	public boolean equals(Vector3 v) {
		return x == v.x && y == v.y && z == v.z;
	}
	
	/**
	 * Checks approximate component equality between this vector and another
	 * @param v Vector
	 * @param epsilon Approximation Factor
	 * @return True if maximum difference between components less than epsilon in magnitude
	 */
	public boolean equalsApprox(Vector3 v, float epsilon) {
		return Math.abs(x - v.x) < epsilon && Math.abs(y - v.y) < epsilon && Math.abs(z - v.z) < epsilon;
	}
	
	/**
	 * Checks approximate component equality between this vector and another
	 * @param v Vector
	 * @return True if maximum difference between components less than 1e-5f in magnitude
	 */
	public boolean equalsApprox(Vector3 v) {
		return equalsApprox(v, 1e-5f);
	}
	
	@Override
	public Float set(int index, Float element) {
		float oldVal;
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
	public Float get(int index) {
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
	public Vector3 clone() {
		return new Vector3(this);
	}
}

