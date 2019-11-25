package egl.math;
import java.util.AbstractList;

/**
 * A 2-Component Vector
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
public class Vector2 extends AbstractList<Float> implements Cloneable {
	public static final int NUM_COMPONENTS = 2;
	
	/** 
	 * Coordinate Component Of The Vector
	 */
	public float x, y;

	/**
	 * Inline Constructor
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 */
	public Vector2(float _x, float _y) {
		x = _x;
		y = _y;
	}
	/**
	 * Copy Constructor
	 * @param v Vector
	 */
	public Vector2(Vector2 v) {
		this(v.x, v.y);
	}
	/**
	 * Uniform Value Constructor
	 * @param f Value
	 */
	public Vector2(float f) {
		this(f, f);
	}
	/**
	 * Zero Vector Constructor
	 */
	public Vector2() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+"}f";
	}
	
	/**
	 * Inline Setter
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @return This
	 */
	public Vector2 set(float _x, float _y) {
		x = _x;
		y = _y;
		return this;
	}
	/**
	 * Set This Vector To The Zero Vector
	 * @return This
	 */
	public Vector2 setZero() {
		return set(0);
	}
	/**
	 * Set This Vector To Uniform Values
	 * @param f Value
	 * @return This
	 */
	public Vector2 set(float f) {
		return set(f, f);
	}
	/**
	 * Copy Vector Into This Vector
	 * @param v Vector
	 * @return This
	 */
	public Vector2 set(Vector2 v) {
		return set(v.x, v.y);
	}
	/**
	 * Sets This Vector To A Multiple Of XYZ Components
	 * <pre>
	 * This = s * {_x, _y}
	 * </pre>
	 * @param s Scalar
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2 setMultiple(float s, float _x, float _y) {
		x = s * _x;
		y = s * _y;
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
	public Vector2 setMultiple(float s, Vector2 v) {
		return setMultiple(s, v.x, v.y);
	}
	
	/**
	 * In-Place Addition
	 * <pre>
	 * This = This + {_x, _y}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2 add(float _x, float _y) {
		x += _x;
		y += _y;
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
	public Vector2 add(Vector2 v) {
		return add(v.x, v.y);
	}
	/**
	 * In-Place Uniform Addition
	 * <pre>
	 * This = This + {f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector2 add(float f) {
		return add(f, f);
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
	 * This = This + s * {_x, _y}
	 * </pre>
	 * @param s Scalar
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2 addMultiple(float s, float _x, float _y) {
		x += s * _x;
		y += s * _y;
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
	public Vector2 addMultiple(float s, Vector2 v) {
		return addMultiple(s, v.x, v.y);
	}
	
	/**
	 * In-Place Subtraction
	 * <pre>
	 * This = This - {_x, _y}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2 sub(float _x, float _y) {
		x -= _x;
		y -= _y;
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
	public Vector2 sub(Vector2 v) {
		return sub(v.x, v.y);
	}
	/**
	 * In-Place Uniform Subtraction
	 * <pre>
	 * This = This - {f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector2 sub(float f) {
		return sub(f, f);
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
	 * This = This - s * {_x, _y}
	 * </pre>
	 * @param s Scalar
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2 subMultiple(float s, float _x, float _y) {
		x -= s * _x;
		y -= s * _y;
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
	public Vector2 subMultiple(float s, Vector2 v) {
		return subMultiple(s, v.x, v.y);
	}
	
	/**
	 * In-Place Multiplication
	 * <pre>
	 * This = This * {_x, _y}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector2 mul(float _x, float _y) {
		x *= _x;
		y *= _y;
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
	public Vector2 mul(Vector2 v) {
		return mul(v.x, v.y);
	}
	/**
	 * In-Place Uniform Multiplication
	 * <pre>
	 * This = This * {f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector2 mul(float f) {
		return mul(f, f);
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
	 * This = This / {_x, _y}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2 div(float _x, float _y) {
		x /= _x;
		y /= _y;
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
	public Vector2 div(Vector2 v) {
		return div(v.x, v.y);
	}
	/**
	 * In-Place Uniform Division
	 * <pre>
	 * This = This * {1 / f, 1 / f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector2 div(float f) {
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
	 * This = This ^ {_x, _y}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2 pow(float _x, float _y) {
		x = (float)Math.pow(x, _x);
		y = (float)Math.pow(y, _y);
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
	public Vector2 pow(Vector2 v) {
		return pow(v.x, v.y);
	}
	/**
	 * In-Place Uniform Power
	 * <pre>
	 * This = This ^ {f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector2 pow(float f) {
		return pow(f, f);
	}
	
	/**
	 * In-Place Exponentiation
	 * <pre>
	 * This = {_x, _y} ^ This
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2 exp(float _x, float _y) {
		x = (float)Math.pow(_x, x);
		y = (float)Math.pow(_y, y);
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
	public Vector2 exp(Vector2 v) {
		return exp(v.x, v.y);
	}
	/**
	 * In-Place Uniform Exponentiation
	 * <pre>
	 * This = {f, f} ^ This
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector2 exp(float f) {
		return exp(f, f);
	}
	
	/**
	 * In-Place Logarithm
	 * <pre>
	 * This = log[base = This]({_x, _y})
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2 log(float _x, float _y) {
		x = (float)(Math.log(x) / Math.log(_x));
		y = (float)(Math.log(y) / Math.log(_y));
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
	public Vector2 log(Vector2 v) {
		return log(v.x, v.y);
	}
	/**
	 * In-Place Uniform Logarithm
	 * <pre>
	 * This = log[base = This]({f, f})
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector2 log(float f) {
		return log(f, f);
	}
	
	/**
	 * In-Place Absolution
	 * @return This
	 */
	public Vector2 abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		return this;
	}
	/**
	 * In-Place Negation
	 * @return This
	 */
	public Vector2 negate() {
		x = -x;
		y = -y;
		return this;
	}
	
	/**
	 * Calculates Dot Product Between This And Another
	 * @param v [{@link Vector2 ARR}] Vector
	 * @return Vector Dot Product
	 */
	public float dot(Vector2 v) {
		return x * v.x + y * v.y;
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
	 * @param v [{@link Vector2 POS}] Vector
	 * @return Length Squared Of Offset Vector
	 */
	public float distSq(Vector2 v) {
		float ox = x - v.x;
		float oy = y - v.y;
		return ox * ox + oy * oy;
	}
	/**
	 * Calculates Distance Between This Vector And Another
	 * <pre>
	 * Let off = This - v
	 * Ret len(off)
	 * </pre>
	 * @param v [{@link Vector2 POS}] Vector
	 * @return Length Of Offset Vector
	 */
	public float dist(Vector2 v) {
		return (float)Math.sqrt(distSq(v));
	}
	/**
	 * Calculate Angle Between This Vector And Another
 	 * @param v [{@link Vector2 DIRN}] Vector
	 * @return Angle Between Vectors (Radians)
	 */
	public float angle(Vector2 v) {
		return (float)Math.acos(dot(v) / (len() * v.len()));
	}
	
	/**
	 * In-Place Cross Product (CCW Rotation)
	 * <pre>
	 * This = cross(This)
	 * </pre>
	 * @return This
	 */
	public Vector2 cross() {
		return set(-y, x);
	}
	
	/**
	 * In-Place Normalization To Unit Length
	 * @return This
	 */
	public Vector2 normalize() {
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
	public Vector2 lerp(Vector2 v, float r) {
		return mul(1 - r).addMultiple(r, v);
	}
	
	/**
	 * Checks Component Equality Between This Vector And Another
	 * @param v Vector
	 * @return True If All Components Equal
	 */
	public boolean equals(Vector2 v) {
		return x == v.x && y == v.y;
	}
	
	/**
	 * Checks Approximate Component Equality Between This Vector And Another
	 * @param v Vector
	 * @param epsilon Approximation Factor
	 * @return True If Maximum Difference Between Components Less Than epsilon In Magnitude
	 */
	public boolean equalsApprox(Vector2 v, float epsilon) {
		return Math.abs(x - v.x) < epsilon && Math.abs(y - v.y) < epsilon;
	}
	
	/**
	 * Checks Approximate Component Equality Between This Vector And Another
	 * @param v Vector
	 * @return True If Maximum Difference Between Components Less Than 1e-5f In Magnitude
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

