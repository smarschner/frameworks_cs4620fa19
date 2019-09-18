package math;
import java.util.AbstractList;

/**
 * A 2-component single-precision vector
 * @author Cristian, srm
 */
public class Vector2 extends AbstractList<Float> implements Cloneable {
	public static final int NUM_COMPONENTS = 2;
	
	/** 
	 * Coordinates of the vector
	 */
	public float x, y;

	/**
	 * Inline constructor
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 */
	public Vector2(float _x, float _y) {
		x = _x;
		y = _y;
	}

	/**
	 * Copy constructor
	 * @param v Vector
	 */
	public Vector2(Vector2 v) {
		this(v.x, v.y);
	}

	/**
	 * Uniform Value constructor
	 * @param f Value
	 */
	public Vector2(float f) {
		this(f, f);
	}

	/**
	 * Zero Vector constructor
	 */
	public Vector2() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+"}f";
	}
	
	/**
	 * Inline setter
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 * @return this
	 */
	public Vector2 set(float _x, float _y) {
		x = _x;
		y = _y;
		return this;
	}

	/**
	 * Set this vector to the zero vector
	 * @return this
	 */
	public Vector2 setZero() {
		return set(0);
	}

	/**
	 * Set this vector to uniform values
	 * @param f Value
	 * @return this
	 */
	public Vector2 set(float f) {
		return set(f, f);
	}

	/**
	 * Copy a vector into this vector
	 * @param v Vector
	 * @return this
	 */
	public Vector2 set(Vector2 v) {
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
	public Vector2 setMultiple(float s, float _x, float _y) {
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
	public Vector2 setMultiple(float s, Vector2 v) {
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
	public Vector2 add(float _x, float _y) {
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
	public Vector2 add(Vector2 v) {
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
	public Vector2 add(float f) {
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
	public Vector2 addMultiple(float s, float _x, float _y) {
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
	public Vector2 addMultiple(float s, Vector2 v) {
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
	public Vector2 sub(float _x, float _y) {
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
	public Vector2 sub(Vector2 v) {
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
	public Vector2 sub(float f) {
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
	public Vector2 subMultiple(float s, float _x, float _y) {
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
	public Vector2 subMultiple(float s, Vector2 v) {
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
	public Vector2 mul(float _x, float _y) {
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
	public Vector2 mul(Vector2 v) {
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
	public Vector2 mul(float f) {
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
	public Vector2 div(float _x, float _y) {
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
	public Vector2 div(Vector2 v) {
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
	public Vector2 div(float f) {
		return mul(1 / f);
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
	public Vector2 pow(float _x, float _y) {
		x = (float)Math.pow(x, _x);
		y = (float)Math.pow(y, _y);
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
	public Vector2 pow(Vector2 v) {
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
	public Vector2 pow(float f) {
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
	public Vector2 exp(float _x, float _y) {
		x = (float)Math.pow(_x, x);
		y = (float)Math.pow(_y, y);
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
	public Vector2 exp(Vector2 v) {
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
	public Vector2 exp(float f) {
		return exp(f, f);
	}
	
	/**
	 * In-place logarithm
	 * <pre>
	 * This = log[base = This]({_x, _y})
	 * </pre>
	 * @param _x X component
	 * @param _y Y component
	 * @return this
	 */
	public Vector2 log(float _x, float _y) {
		x = (float)(Math.log(x) / Math.log(_x));
		y = (float)(Math.log(y) / Math.log(_y));
		return this;
	}

	/**
	 * In-place logarithm
	 * <pre>
	 * This = log[base = This](v)
	 * </pre>
	 * @param v Vector
	 * @return this
	 */
	public Vector2 log(Vector2 v) {
		return log(v.x, v.y);
	}

	/**
	 * In-place uniform logarithm
	 * <pre>
	 * This = log[base = This]({f, f})
	 * </pre>
	 * @param f Value
	 * @return this
	 */
	public Vector2 log(float f) {
		return log(f, f);
	}
	
	/**
	 * In-place absolute value
	 * @return this
	 */
	public Vector2 abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		return this;
	}

	/**
	 * In-place negation
	 * @return this
	 */
	public Vector2 negate() {
		x = -x;
		y = -y;
		return this;
	}
	
	/**
	 * Dot product between this and another vector
	 * @param v [{@link Vector2 ARR}] Vector
	 * @return Vector Dot Product
	 */
	public float dot(Vector2 v) {
		return x * v.x + y * v.y;
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
	 * @param v [{@link Vector2 POS}] Vector
	 * @return Squared distance
	 */
	public float distSq(Vector2 v) {
		float ox = x - v.x;
		float oy = y - v.y;
		return ox * ox + oy * oy;
	}

	/**
	 * Calculates distance between this vector and another
	 * @param v [{@link Vector2 POS}] Vector
	 * @return Distance
	 */
	public float dist(Vector2 v) {
		return (float)Math.sqrt(distSq(v));
	}

	/**
	 * Calculate angle between this vector and another
 	 * @param v [{@link Vector2 DIRN}] Vector
	 * @return Angle between vectors (radians)
	 */
	public float angle(Vector2 v) {
		return (float)Math.acos(dot(v) / (len() * v.len()));
	}
	
	/**
	 * In-place CCW rotation
	 * @return this
	 */
	public Vector2 rot90() {
		return set(-y, x);
	}
	
	/**
	 * In-place normalization to unit length
	 * @return this
	 */
	public Vector2 normalize() {
		return mul(1 / len());
	}

	/**
	 * In-place linear interpolation between this vector and another 
	 * <pre>
	 * This = (1 - r) * this + r * v
	 * </pre>
	 * @param v Vector
	 * @param r Ratio
	 * @return this
	 */
	public Vector2 lerp(Vector2 v, float r) {
		return mul(1 - r).addMultiple(r, v);
	}
	
	/**
	 * Checks component equality between this vector and another
	 * @param v Vector
	 * @return True if all components equal
	 */
	public boolean equals(Vector2 v) {
		return x == v.x && y == v.y;
	}
	
	/**
	 * Checks approximate component equality between this vector and another
	 * @param v Vector
	 * @param epsilon Approximation Factor
	 * @return True if maximum difference between components less than epsilon in magnitude
	 */
	public boolean equalsApprox(Vector2 v, float epsilon) {
		return Math.abs(x - v.x) < epsilon && Math.abs(y - v.y) < epsilon;
	}
	
	/**
	 * Checks approximate component equality between this vector and another
	 * @param v Vector
	 * @return True if maximum difference between components less than 1e-5f in magnitude
	 */
	public boolean equalsApprox(Vector2 v) {
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
		default: throw new IndexOutOfBoundsException();
		}
	}
	
	@Override
	public Float get(int index) {
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
	public Vector2 clone() {
		return new Vector2(this);
	}
}

