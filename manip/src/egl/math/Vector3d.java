package egl.math;
import java.util.AbstractList;

/**
 * A 3-component double-precision vector
 * @author Cristian, srm
 *
 */
public class Vector3d extends AbstractList<Double> implements Cloneable {
	public static final int NUM_COMPONENTS = 3;
	
	/** 
	 * Coordinates of the vector
	 */
	public double x, y, z;

	/**
	 * Inline constructor
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 * @param _z Z coordinate
	 */
	public Vector3d(double _x, double _y, double _z) {
		x = _x;
		y = _y;
		z = _z;
	}

	/**
	 * Copy constructor
	 * @param v Vector
	 */
	public Vector3d(Vector3d v) {
		this(v.x, v.y, v.z);
	}
	
	/**
	 * Copy Constructor
	 * @param v Vector
	 */
	public Vector3d(Vector3 v) {
		this(v.x, v.y, v.z);
	}
	/**
	 * Uniform Value Constructor
	 * @param f Value
	 */
	public Vector3d(double f) {
		this(f, f, f);
	}

	/**
	 * Zero vector constructor
	 */
	public Vector3d() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+", "+z+"}d";
	}
	
	/**
	 * Inline setter
	 * @param _x X coordinate
	 * @param _y Y coordinate
	 * @param _z Z coordinate
	 * @return This
	 */
	public Vector3d set(double _x, double _y, double _z) {
		x = _x;
		y = _y;
		z = _z;
		return this;
	}

	/**
	 * Set this vector to the zero vector
	 * @return This
	 */
	public Vector3d setZero() {
		return set(0);
	}

	/**
	 * Set This vector to uniform values
	 * @param f Value
	 * @return This
	 */
	public Vector3d set(double f) {
		return set(f, f, f);
	}

	/**
	 * Copy vector into this vector
	 * @param v Vector
	 * @return This
	 */
	public Vector3d set(Vector3d v) {
		return set(v.x, v.y, v.z);
	}

	/**
	 * Copy Vector Into This Vector
	 * @param v Vector
	 * @return This
	 */
	public Vector3d set(Vector3 v) {
		return set(v.x, v.y, v.z);
	}
	/**
	 * Sets This Vector To A Multiple Of XYZ Components
	 * <pre>
	 * This = s * {_x, _y, _z}
	 * </pre>
	 * @param s Scalar
	 * @param _x X component
	 * @param _y Y component
	 * @param _z Z component
	 * @return This
	 */
	public Vector3d setMultiple(double s, double _x, double _y, double _z) {
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
	public Vector3d setMultiple(double s, Vector3d v) {
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
	public Vector3d add(double _x, double _y, double _z) {
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
	public Vector3d add(Vector3d v) {
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
	public Vector3d add(double f) {
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
	public Vector3d addMultiple(double s, double _x, double _y, double _z) {
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
	public Vector3d addMultiple(double s, Vector3d v) {
		return addMultiple(s, v.x, v.y, v.z);
	}
	public Vector3d addMultiple(double s, Vector3 v) {
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
	public Vector3d sub(double _x, double _y, double _z) {
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
	public Vector3d sub(Vector3d v) {
		return sub(v.x, v.y, v.z);
	}
	public Vector3d sub(Vector3 v) {
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
	public Vector3d sub(double f) {
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
	public Vector3d subMultiple(double s, double _x, double _y, double _z) {
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
	public Vector3d subMultiple(double s, Vector3d v) {
		return subMultiple(s, v.x, v.y, v.z);
	}
	public Vector3d subMultiple(double s, Vector3 v) {
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
	public Vector3d mul(double _x, double _y, double _z) {
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
	public Vector3d mul(Vector3d v) {
		return mul(v.x, v.y, v.z);
	}
	public Vector3d mul(Vector3 v) {
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
	public Vector3d mul(double f) {
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
	public Vector3d div(double _x, double _y, double _z) {
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
	public Vector3d div(Vector3d v) {
		return div(v.x, v.y, v.z);
	}
	public Vector3d div(Vector3 v) {
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
	public Vector3d div(double f) {
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
	public Vector3d pow(double _x, double _y, double _z) {
		x = (double)Math.pow(x, _x);
		y = (double)Math.pow(y, _y);
		z = (double)Math.pow(z, _z);
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
	public Vector3d pow(Vector3d v) {
		return pow(v.x, v.y, v.z);
	}
	public Vector3d pow(Vector3 v) {
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
	public Vector3d pow(double f) {
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
	public Vector3d exp(double _x, double _y, double _z) {
		x = (double)Math.pow(_x, x);
		y = (double)Math.pow(_y, y);
		z = (double)Math.pow(_z, z);
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
	public Vector3d exp(Vector3d v) {
		return exp(v.x, v.y, v.z);
	}
	public Vector3d exp(Vector3 v) {
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
	public Vector3d exp(double f) {
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
	public Vector3d log(double _x, double _y, double _z) {
		x = (double)(Math.log(x) / Math.log(_x));
		y = (double)(Math.log(y) / Math.log(_y));
		z = (double)(Math.log(z) / Math.log(_z));
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
	public Vector3d log(Vector3d v) {
		return log(v.x, v.y, v.z);
	}
	public Vector3d log(Vector3 v) {
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
	public Vector3d log(double f) {
		return log(f, f, f);
	}
	
	/**
	 * In-place absolute value
	 * @return This
	 */
	public Vector3d abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		if(z < 0) z = -z;
		return this;
	}

	/**
	 * In-Place negation
	 * @return This
	 */
	public Vector3d negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	/**
	 * Dot product between this and another vector
	 * @param v [{@link Vector3d ARR}] Vector
	 * @return Vector Dot Product
	 */
	public double dot(Vector3d v) {
		return x * v.x + y * v.y + z * v.z;
	}
	public double dot(Vector3 v) {
		return x * v.x + y * v.y + z * v.z;
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
	 * @param v [{@link Vector3d POS}] Vector
	 * @return Length Squared Of Offset Vector
	 */
	public double distSq(Vector3d v) {
		double ox = x - v.x;
		double oy = y - v.y;
		double oz = z - v.z;
		return ox * ox + oy * oy + oz * oz;
	}
	public double distSq(Vector3 v) {
		double ox = x - v.x;
		double oy = y - v.y;
		double oz = z - v.z;
		return ox * ox + oy * oy + oz * oz;
	}
	/**
	 * Calculates distance between this vector and another
	 * <pre>
	 * Let off = This - v
	 * Ret len(off)
	 * </pre>
	 * @param v [{@link Vector3d POS}] Vector
	 * @return Length Of Offset Vector
	 */
	public double dist(Vector3d v) {
		return (double)Math.sqrt(distSq(v));
	}
	public double dist(Vector3 v) {
		return Math.sqrt(distSq(v));
	}
	/**
	 * Calculate angle between this vector and another
 	 * @param v [{@link Vector3d DIRN}] Vector
	 * @return Angle Between Vectors (Radians)
	 */
	public double angle(Vector3d v) {
		return (double)Math.acos(dot(v) / (len() * v.len()));
	}
	public double angle(Vector3 v) {
		return Math.acos(dot(v) / (len() * v.len()));
	}
	
	/**
	 * In-place cross product
	 * <pre>
	 * This = cross(This,v)
	 * </pre>
	 * @param v Right Side Vector
	 * @return This
	 */
	public Vector3d cross(Vector3d v) {
		return set(
			y * v.z - z * v.y,
			z * v.x - x * v.z,
			x * v.y - y * v.x
			);
	}
	public Vector3d cross(Vector3 v) {
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
	public Vector3d normalize() {
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
	public Vector3d lerp(Vector3d v, double r) {
		return mul(1 - r).addMultiple(r, v);
	}
	public Vector3d lerp(Vector3 v, double r) {
		return mul(1 - r).addMultiple(r, v);
	}
	
	/**
	 * Checks component equality between this vector and another
	 * @param v Vector
	 * @return True if all components equal
	 */
	public boolean equals(Vector3d v) {
		return x == v.x && y == v.y && z == v.z;
	}
	
	/**
	 * Checks approximate component equality between this vector and another
	 * @param v Vector
	 * @param epsilon Approximation Factor
	 * @return True if maximum difference between components less than epsilon in magnitude
	 */
	public boolean equalsApprox(Vector3d v, double epsilon) {
		return Math.abs(x - v.x) < epsilon && Math.abs(y - v.y) < epsilon && Math.abs(z - v.z) < epsilon;
	}
	
	/**
	 * Checks approximate component equality between this vector and another
	 * @param v Vector
	 * @return True if maximum difference between components less than 1e-8f in magnitude
	 */
	public boolean equalsApprox(Vector3d v) {
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
		case 2:
			oldVal = z;
			z = element;
			return oldVal;
		default: throw new IndexOutOfBoundsException();
		}
	}
	
	@Override
	public Double get(int index) {
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
	public Vector3d clone() {
		return new Vector3d(this);
	}
}

