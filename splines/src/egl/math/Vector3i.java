package egl.math;
import java.util.AbstractList;

/**
 * A 3-Component Vector
 * <br>Integer Precision</br>
 * <pre>
 *  Argument Usage Identifiers:
 * [POS]  - Position
 * [OFF]  - Offset Between Positions
 * [ARR]  - Array Of Integers
 * [OUT]  - Non-Null Output
 * </pre>
 * @author Cristian
 *
 */
public class Vector3i extends AbstractList<Integer> implements Cloneable {
	public static final int NUM_COMPONENTS = 3;
	
	/** 
	 * Coordinate Component Of The Vector
	 */
	public int x, y, z;

	/**
	 * Inline Constructor
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 */
	public Vector3i(int _x, int _y, int _z) {
		x = _x;
		y = _y;
		z = _z;
	}
	/**
	 * Copy Constructor
	 * @param v Vector
	 */
	public Vector3i(Vector3i v) {
		this(v.x, v.y, v.z);
	}
	/**
	 * Uniform Value Constructor
	 * @param f Value
	 */
	public Vector3i(int f) {
		this(f, f, f);
	}
	/**
	 * Zero Vector Constructor
	 */
	public Vector3i() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+", "+z+"}i";
	}
	
	/**
	 * Inline Setter
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 * @return This
	 */
	public Vector3i set(int _x, int _y, int _z) {
		x = _x;
		y = _y;
		z = _z;
		return this;
	}
	/**
	 * Set This Vector To The Zero Vector
	 * @return This
	 */
	public Vector3i setZero() {
		return set(0);
	}
	/**
	 * Set This Vector To Uniform Values
	 * @param f Value
	 * @return This
	 */
	public Vector3i set(int f) {
		return set(f, f, f);
	}
	/**
	 * Copy Vector Into This Vector
	 * @param v Vector
	 * @return This
	 */
	public Vector3i set(Vector3i v) {
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
	public Vector3i setMultiple(int s, int _x, int _y, int _z) {
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
	public Vector3i setMultiple(int s, Vector3i v) {
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
	public Vector3i add(int _x, int _y, int _z) {
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
	public Vector3i add(Vector3i v) {
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
	public Vector3i add(int f) {
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
	public Vector3i addMultiple(int s, int _x, int _y, int _z) {
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
	public Vector3i addMultiple(int s, Vector3i v) {
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
	public Vector3i sub(int _x, int _y, int _z) {
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
	public Vector3i sub(Vector3i v) {
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
	public Vector3i sub(int f) {
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
	public Vector3i subMultiple(int s, int _x, int _y, int _z) {
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
	public Vector3i subMultiple(int s, Vector3i v) {
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
	public Vector3i mul(int _x, int _y, int _z) {
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
	public Vector3i mul(Vector3i v) {
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
	public Vector3i mul(int f) {
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
	public Vector3i div(int _x, int _y, int _z) {
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
	public Vector3i div(Vector3i v) {
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
	public Vector3i div(int f) {
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
	 * In-Place Power
	 * <pre>
	 * This = This ^ {_x, _y, _z}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Vector3i pow(int _x, int _y, int _z) {
		x = powi(x, _x);
		y = powi(y, _y);
		z = powi(z, _z);
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
	public Vector3i pow(Vector3i v) {
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
	public Vector3i pow(int f) {
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
	public Vector3i exp(int _x, int _y, int _z) {
		x = powi(_x, x);
		y = powi(_y, y);
		z = powi(_z, z);
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
	public Vector3i exp(Vector3i v) {
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
	public Vector3i exp(int f) {
		return exp(f, f, f);
	}
	
	/**
	 * In-Place Absolution
	 * @return This
	 */
	public Vector3i abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		if(z < 0) z = -z;
		return this;
	}
	/**
	 * In-Place Negation
	 * @return This
	 */
	public Vector3i negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	/**
	 * Calculates Dot Product Between This And Another
	 * @param v [{@link Vector3i ARR}] Vector
	 * @return Vector Dot Product
	 */
	public int dot(Vector3i v) {
		return x * v.x + y * v.y + z * v.z;
	}
	/**
	 * Calculates The Dot Product With Itself
	 * @return The Vector Length Squared
	 */
	public int lenSq() {
		return dot(this);
	}
	/**
	 * Calculates The Length
	 * @return The Vector Length
	 */
	public double len() {
		return Math.sqrt(lenSq());
	}

	/**
	 * Calculates Distance Squared Between This Vector And Another
	 * <pre>
	 * Let off = This - v
	 * Ret dot(off, off)
	 * </pre>
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
	 * Calculates Distance Between This Vector And Another
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
	 * Calculate Angle Between This Vector And Another
 	 * @param v [{@link Vector3i DIRN}] Vector
	 * @return Angle Between Vectors
	 */
	public double angle(Vector3i v) {
		return Math.acos(dot(v) / (len() * v.len()));
	}
	
	/**
	 * Checks Component Equality Between This Vector And Another
	 * @param v Vector
	 * @return True If All Components Equal
	 */
	public boolean equals(Vector3i v) {
		return x == v.x && y == v.y && z == v.z;
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

