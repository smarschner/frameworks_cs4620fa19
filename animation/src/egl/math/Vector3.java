package egl.math;
import java.util.AbstractList;

/**
 * A 3-Component Vector
 * <br>Single Precision</br>
 * <pre>
 *  Argument Usage Identifiers:
 * [POS]  - Position
 * [OFF]  - Offset Between Positions
 * [DIR]  - Normalized Direction
 * [DIRN] - Direction (Scale Allowable)
 * [ARR]  - Array Of Floats
 * [OUT]  - Non-Null Output
 * </pre>
 * @author Cristian
 *
 */
public class Vector3 extends AbstractList<Float> implements Cloneable {
	public static final int NUM_COMPONENTS = 3;
	
	/** 
	 * Coordinate Component Of The Vector
	 */
	public float x, y, z;

	/**
	 * Inline Constructor
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 */
	public Vector3(float _x, float _y, float _z) {
		x = _x;
		y = _y;
		z = _z;
	}
	/**
	 * Copy Constructor
	 * @param v Vector
	 */
	public Vector3(Vector3 v) {
		this(v.x, v.y, v.z);
	}
	/**
	 * Uniform Value Constructor
	 * @param f Value
	 */
	public Vector3(float f) {
		this(f, f, f);
	}
	/**
	 * Zero Vector Constructor
	 */
	public Vector3() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+", "+z+"}f";
	}
	
	/**
	 * Inline Setter
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 * @return This
	 */
	public Vector3 set(float _x, float _y, float _z) {
		x = _x;
		y = _y;
		z = _z;
		return this;
	}
	/**
	 * Set This Vector To The Zero Vector
	 * @return This
	 */
	public Vector3 setZero() {
		return set(0);
	}
	/**
	 * Set This Vector To Uniform Values
	 * @param f Value
	 * @return This
	 */
	public Vector3 set(float f) {
		return set(f, f, f);
	}
	/**
	 * Copy Vector Into This Vector
	 * @param v Vector
	 * @return This
	 */
	public Vector3 set(Vector3 v) {
		return set(v.x, v.y, v.z);
	}
	/**
	 * Sets This Vector To A Multiple Of XYZ Components
	 * <pre>
	 * This = s * {_x, _y, _z}
	 * </pre>
	 * @param s Scalar
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 setMultiple(float s, float _x, float _y, float _z) {
		x = s * _x;
		y = s * _y;
		z = s * _z;
		return this;
	}
	/**
	 * Sets This Vector To A Multiple Of Another
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
	 * In-Place Addition
	 * <pre>
	 * This = This + {_x, _y, _z}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 add(float _x, float _y, float _z) {
		x += _x;
		y += _y;
		z += _z;
		return this;
	}
	/**
	 * In-Place Addition
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
	 * In-Place Uniform Addition
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
	 * Sets This Vector To The Sum Of Two Vectors
	 * <pre>
	 * This = v1 + v2
	 * </pre>
	 * @param v1 Vector
	 * @param v2 Vector
	 * @return This
	 * /
	public Vec3 add(Vec3 v1, Vec3 v2) {
		return set(v1).add(v2);
	}*/
	/**
	 * In-Place Scaled Addition
	 * <pre>
	 * This = This + s * {_x, _y, _z}
	 * </pre>
	 * @param s Scalar
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 addMultiple(float s, float _x, float _y, float _z) {
		x += s * _x;
		y += s * _y;
		z += s * _z;
		return this;
	}
	/**
	 * In-Place Scaled Addition
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
	 * In-Place Subtraction
	 * <pre>
	 * This = This - {_x, _y, _z}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 sub(float _x, float _y, float _z) {
		x -= _x;
		y -= _y;
		z -= _z;
		return this;
	}
	/**
	 * In-Place Subtraction
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
	 * In-Place Uniform Subtraction
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
	 * Sets This Vector To The Difference Of Two Vectors
	 * <pre>
	 * This = v1 - v2
	 * </pre>
	 * @param v1 Vector
	 * @param v2 Vector
	 * @return This
	 * /
	public Vec3 sub(Vec3 v1, Vec3 v2) {
		return set(v1).sub(v2);
	}*/
	/**
	 * In-Place Scaled Subtraction
	 * <pre>
	 * This = This - s * {_x, _y, _z}
	 * </pre>
	 * @param s Scalar
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 subMultiple(float s, float _x, float _y, float _z) {
		x -= s * _x;
		y -= s * _y;
		z -= s * _z;
		return this;
	}
	/**
	 * In-Place Scaled Subtraction
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
	 * In-Place Multiplication
	 * <pre>
	 * This = This * {_x, _y, _z}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 mul(float _x, float _y, float _z) {
		x *= _x;
		y *= _y;
		z *= _z;
		return this;
	}
	/**
	 * In-Place Multiplication
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
	 * In-Place Uniform Multiplication
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
	 * Sets This Vector To The Product Of Two Vectors
	 * <pre>
	 * This = v1 * v2
	 * </pre>
	 * @param v1 Vector
	 * @param v2 Vector
	 * @return This
	 * /
	public Vec3 mul(Vec3 v1, Vec3 v2) {
		return set(v1).mul(v2);
	}*/

	/**
	 * In-Place Division
	 * <pre>
	 * This = This / {_x, _y, _z}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 div(float _x, float _y, float _z) {
		x /= _x;
		y /= _y;
		z /= _z;
		return this;
	}
	/**
	 * In-Place Division
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
	 * In-Place Uniform Division
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
	 * Sets This Vector To The Quotient Of Two Vectors
	 * <pre>
	 * This = v1 / v2
	 * </pre>
	 * @param v1 Vector
	 * @param v2 Vector
	 * @return This
	 * /
	public Vec3 div(Vec3 v1, Vec3 v2) {
		return set(v1).div(v2);
	}*/

	/**
	 * In-Place Power
	 * <pre>
	 * This = This ^ {_x, _y, _z}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 pow(float _x, float _y, float _z) {
		x = (float)Math.pow(x, _x);
		y = (float)Math.pow(y, _y);
		z = (float)Math.pow(z, _z);
		return this;
	}
	/**
	 * In-Place Power
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
	 * In-Place Uniform Power
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
	 * In-Place Exponentiation
	 * <pre>
	 * This = {_x, _y, _z} ^ This
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 exp(float _x, float _y, float _z) {
		x = (float)Math.pow(_x, x);
		y = (float)Math.pow(_y, y);
		z = (float)Math.pow(_z, z);
		return this;
	}
	/**
	 * In-Place Exponentiation
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
	 * In-Place Uniform Exponentiation
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
	 * In-Place Logarithm
	 * <pre>
	 * This = log[base = This]({_x, _y, _z})
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3 log(float _x, float _y, float _z) {
		x = (float)(Math.log(x) / Math.log(_x));
		y = (float)(Math.log(y) / Math.log(_y));
		z = (float)(Math.log(z) / Math.log(_z));
		return this;
	}
	/**
	 * In-Place Logarithm
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
	 * In-Place Uniform Logarithm
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
	 * In-Place Absolution
	 * @return This
	 */
	public Vector3 abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		if(z < 0) z = -z;
		return this;
	}
	/**
	 * In-Place Negation
	 * @return This
	 */
	public Vector3 negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	/**
	 * Calculates Dot Product Between This And Another
	 * @param v [{@link Vector3 ARR}] Vector
	 * @return Vector Dot Product
	 */
	public float dot(Vector3 v) {
		return x * v.x + y * v.y + z * v.z;
	}
	/**
	 * Calculates The Dot Product With Itself
	 * @return The Vector Length Squared
	 */
	public float lenSq() {
		return dot(this);
	}
	/**
	 * Calculates The Length
	 * @return The Vector Length
	 */
	public float len() {
		return (float)Math.sqrt(lenSq());
	}

	/**
	 * Calculates Distance Squared Between This Vector And Another
	 * <pre>
	 * Let off = This - v
	 * Ret dot(off, off)
	 * </pre>
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
	 * Calculates Distance Between This Vector And Another
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
	 * Calculate Angle Between This Vector And Another
 	 * @param v [{@link Vector3 DIRN}] Vector
	 * @return Angle Between Vectors (Radians)
	 */
	public float angle(Vector3 v) {
		return (float)Math.acos(dot(v) / (len() * v.len()));
	}
	
	/**
	 * In-Place Cross Product
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
	 * In-Place Normalization To Unit Length
	 * @return This
	 */
	public Vector3 normalize() {
		return mul(1 / len());
	}

	/**
	 * In-Place Linear Interpolation Between This Vector And Another 
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
	 * Checks Component Equality Between This Vector And Another
	 * @param v Vector
	 * @return True If All Components Equal
	 */
	public boolean equals(Vector3 v) {
		return x == v.x && y == v.y && z == v.z;
	}
	
	/**
	 * Checks Approximate Component Equality Between This Vector And Another
	 * @param v Vector
	 * @param epsilon Approximation Factor
	 * @return True If Maximum Difference Between Components Less Than epsilon In Magnitude
	 */
	public boolean equalsApprox(Vector3 v, float epsilon) {
		return Math.abs(x - v.x) < epsilon && Math.abs(y - v.y) < epsilon && Math.abs(z - v.z) < epsilon;
	}
	
	/**
	 * Checks Approximate Component Equality Between This Vector And Another
	 * @param v Vector
	 * @return True If Maximum Difference Between Components Less Than 1e-5f In Magnitude
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

