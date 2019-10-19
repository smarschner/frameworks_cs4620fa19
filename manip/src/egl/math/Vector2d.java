package egl.math;
import java.util.AbstractList;

/**
 * A 2-component double-precision vector
 * @author Cristian, srm
 */
public class Vector2d extends AbstractList<Double> implements Cloneable {
	public static final int NUM_COMPONENTS = 2;
	
	/** 
	 * Coordinates of the vector
	 */
	public double x, y;

	/**
	 * Inline constructor
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 */
	public Vector2d(double _x, double _y) {
		x = _x;
		y = _y;
	}

	/**
	 * Copy constructor
	 * @param v Vector
	 */
	public Vector2d(Vector2d v) {
		this(v.x, v.y);
	}

	/**
	 * Copy Constructor
	 * @param v Vector
	 */
	public Vector2d(Vector2 v) {
		this(v.x, v.y);
	}
	/**
	 * Uniform Value Constructor
	 * @param f Value
	 */
	public Vector2d(double f) {
		this(f, f);
	}

	/**
	 * Zero Vector constructor
	 */
	public Vector2d() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+"}d";
	}
	
	/**
	 * Inline setter
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 * @return this
	 */
	public Vector2d set(double _x, double _y) {
		x = _x;
		y = _y;
		return this;
	}

	/**
	 * Set this vector to the zero vector
	 * @return this
	 */
	public Vector2d setZero() {
		return set(0);
	}

	/**
	 * Set this vector to uniform values
	 * @param f Value
	 * @return this
	 */
	public Vector2d set(double f) {
		return set(f, f);
	}

	/**
	 * Copy a vector into this vector
	 * @param v Vector
	 * @return this
	 */
	public Vector2d set(Vector2d v) {
		return set(v.x, v.y);
	}
	public Vector2d set(Vector2 v) {
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
	public Vector2d setMultiple(double s, double _x, double _y) {
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
	public Vector2d setMultiple(double s, Vector2d v) {
		return setMultiple(s, v.x, v.y);
	}
	public Vector2d setMultiple(double s, Vector2 v) {
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
	public Vector2d add(double _x, double _y) {
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
	public Vector2d add(Vector2d v) {
		return add(v.x, v.y);
	}
	public Vector2d add(Vector2 v) {
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
	public Vector2d add(double f) {
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
	public Vector2d addMultiple(double s, double _x, double _y) {
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
	public Vector2d addMultiple(double s, Vector2d v) {
		return addMultiple(s, v.x, v.y);
	}
	public Vector2d addMultiple(double s, Vector2 v) {
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
	public Vector2d sub(double _x, double _y) {
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
	public Vector2d sub(Vector2d v) {
		return sub(v.x, v.y);
	}
	public Vector2d sub(Vector2 v) {
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
	public Vector2d sub(double f) {
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
	public Vector2d subMultiple(double s, double _x, double _y) {
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
	public Vector2d subMultiple(double s, Vector2d v) {
		return subMultiple(s, v.x, v.y);
	}
	public Vector2d subMultiple(double s, Vector2 v) {
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
	public Vector2d mul(double _x, double _y) {
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
	public Vector2d mul(Vector2d v) {
		return mul(v.x, v.y);
	}
	public Vector2d mul(Vector2 v) {
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
	public Vector2d mul(double f) {
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
	public Vector2d div(double _x, double _y) {
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
	public Vector2d div(Vector2d v) {
		return div(v.x, v.y);
	}
	public Vector2d div(Vector2 v) {
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
	public Vector2d div(double f) {
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
	public Vector2d pow(double _x, double _y) {
		x = (double)Math.pow(x, _x);
		y = (double)Math.pow(y, _y);
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
	public Vector2d pow(Vector2d v) {
		return pow(v.x, v.y);
	}
	public Vector2d pow(Vector2 v) {
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
	public Vector2d pow(double f) {
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
	public Vector2d exp(double _x, double _y) {
		x = (double)Math.pow(_x, x);
		y = (double)Math.pow(_y, y);
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
	public Vector2d exp(Vector2d v) {
		return exp(v.x, v.y);
	}
	public Vector2d exp(Vector2 v) {
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
	public Vector2d exp(double f) {
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
	public Vector2d log(double _x, double _y) {
		x = (double)(Math.log(x) / Math.log(_x));
		y = (double)(Math.log(y) / Math.log(_y));
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
	public Vector2d log(Vector2d v) {
		return log(v.x, v.y);
	}
	public Vector2d log(Vector2 v) {
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
	public Vector2d log(double f) {
		return log(f, f);
	}
	
	/**
	 * In-place absolute value
	 * @return this
	 */
	public Vector2d abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		return this;
	}

	/**
	 * In-place negation
	 * @return this
	 */
	public Vector2d negate() {
		x = -x;
		y = -y;
		return this;
	}
	
	/**
	 * Dot product between this and another vector
	 * @param v [{@link Vector2d ARR}] Vector
	 * @return Vector Dot Product
	 */
	public double dot(Vector2d v) {
		return x * v.x + y * v.y;
	}
	public double dot(Vector2 v) {
		return x * v.x + y * v.y;
	}
	/**
	 * This vector's dot product with itself
	 * @return This vector's length squared
	 */
	public double lenSq() {
		return dot(this);
	}

	/**
	 * Calculates the length
	 * @return This vector's length
	 */
	public double len() {
		return (double)Math.sqrt(lenSq());
	}

	/**
	 * Calculates squared distance between this vector and another
	 * @param v [{@link Vector2d POS}] Vector
	 * @return Squared distance
	 */
	public double distSq(Vector2d v) {
		double ox = x - v.x;
		double oy = y - v.y;
		return ox * ox + oy * oy;
	}
	public double distSq(Vector2 v) {
		double ox = x - v.x;
		double oy = y - v.y;
		return ox * ox + oy * oy;
	}
	/**
	 * Calculates distance between this vector and another
	 * @param v [{@link Vector2d POS}] Vector
	 * @return Distance
	 */
	public double dist(Vector2d v) {
		return (double)Math.sqrt(distSq(v));
	}
	public double dist(Vector2 v) {
		return Math.sqrt(distSq(v));
	}
	/**
	 * Calculate angle between this vector and another
 	 * @param v [{@link Vector2d DIRN}] Vector
	 * @return Angle between vectors (radians)
	 */
	public double angle(Vector2d v) {
		return (double)Math.acos(dot(v) / (len() * v.len()));
	}
	public double angle(Vector2 v) {
		return Math.acos(dot(v) / (len() * v.len()));
	}
	
	/**
	 * In-place CCW rotation
	 * @return this
	 */
	public Vector2d rot90() {
		return set(-y, x);
	}
	
	/**
	 * In-place normalization to unit length
	 * @return this
	 */
	public Vector2d normalize() {
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
	public Vector2d lerp(Vector2d v, double r) {
		return mul(1 - r).addMultiple(r, v);
	}
	public Vector2d lerp(Vector2 v, double r) {
		return mul(1 - r).addMultiple(r, v);
	}
	
	/**
	 * Checks component equality between this vector and another
	 * @param v Vector
	 * @return True if all components equal
	 */
	public boolean equals(Vector2d v) {
		return x == v.x && y == v.y;
	}
	
	/**
	 * Checks approximate component equality between this vector and another
	 * @param v Vector
	 * @param epsilon Approximation Factor
	 * @return True if maximum difference between components less than epsilon in magnitude
	 */
	public boolean equalsApprox(Vector2d v, double epsilon) {
		return Math.abs(x - v.x) < epsilon && Math.abs(y - v.y) < epsilon;
	}
	
	/**
	 * Checks approximate component equality between this vector and another
	 * @param v Vector
	 * @return True if maximum difference between components less than 1e-8f in magnitude
	 */
	public boolean equalsApprox(Vector2d v) {
		return equalsApprox(v, 1e-8);
	}
	
	@Override
	public Double set(int index, Double element) {
		double oldVal;
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
	public Double get(int index) {
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
	public Vector2d clone() {
		return new Vector2d(this);
	}
}

