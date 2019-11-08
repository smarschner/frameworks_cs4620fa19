package egl.math;
import java.util.AbstractList;

/**
 * A 4-Component Vector
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
public class Vector4i extends AbstractList<Integer> implements Cloneable {
	public static final int NUM_COMPONENTS = 4;
	
	/** 
	 * Coordinate Component Of The Vector
	 */
	public int x, y, z, w;

	/**
	 * Inline Constructor
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 * @param _w W Coordinate
	 */
	public Vector4i(int _x, int _y, int _z, int _w) {
		x = _x;
		y = _y;
		z = _z;
		w = _w;
	}
	/**
	 * Copy Constructor
	 * @param v Vector
	 */
	public Vector4i(Vector4i v) {
		this(v.x, v.y, v.z, v.w);
	}
	/**
	 * Uniform Value Constructor
	 * @param f Value
	 */
	public Vector4i(int f) {
		this(f, f, f, f);
	}
	/**
	 * Zero Vector Constructor
	 */
	public Vector4i() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+", "+z+", "+w+"}i";
	}
	
	/**
	 * Inline Setter
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 * @param _w W Coordinate
	 * @return This
	 */
	public Vector4i set(int _x, int _y, int _z, int _w) {
		x = _x;
		y = _y;
		z = _z;
		w = _w;
		return this;
	}
	/**
	 * Set This Vector To The Zero Vector
	 * @return This
	 */
	public Vector4i setZero() {
		return set(0);
	}
	/**
	 * Set This Vector To Uniform Values
	 * @param f Value
	 * @return This
	 */
	public Vector4i set(int f) {
		return set(f, f, f, f);
	}
	/**
	 * Copy Vector Into This Vector
	 * @param v Vector
	 * @return This
	 */
	public Vector4i set(Vector4i v) {
		return set(v.x, v.y, v.z, v.w);
	}
	/**
	 * Sets This Vector To A Multiple Of XYZ Components
	 * <pre>
	 * This = s * {_x, _y, _z, _w}
	 * </pre>
	 * @param s Scalar
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @param _w W Component
	 * @return This
	 */
	public Vector4i setMultiple(int s, int _x, int _y, int _z, int _w) {
		x = s * _x;
		y = s * _y;
		z = s * _z;
		w = s * _w;
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
	public Vector4i setMultiple(int s, Vector4i v) {
		return setMultiple(s, v.x, v.y, v.z, v.w);
	}

	/**
	 * In-Place Addition
	 * <pre>
	 * This = This + {_x, _y, _z, _w}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @param _w W Component
	 * @return This
	 */
	public Vector4i add(int _x, int _y, int _z, int _w) {
		x += _x;
		y += _y;
		z += _z;
		w += _w;
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
	public Vector4i add(Vector4i v) {
		return add(v.x, v.y, v.z, v.w);
	}
	/**
	 * In-Place Uniform Addition
	 * <pre>
	 * This = This + {f, f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector4i add(int f) {
		return add(f, f, f, f);
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
	 * This = This + s * {_x, _y, _z, _w}
	 * </pre>
	 * @param s Scalar
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @param _w W Component
	 * @return This
	 */
	public Vector4i addMultiple(int s, int _x, int _y, int _z, int _w) {
		x += s * _x;
		y += s * _y;
		z += s * _z;
		w += s * _w;
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
	public Vector4i addMultiple(int s, Vector4i v) {
		return addMultiple(s, v.x, v.y, v.z, v.w);
	}
	
	/**
	 * In-Place Subtraction
	 * <pre>
	 * This = This - {_x, _y, _z, _w}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @param _w W Component
	 * @return This
	 */
	public Vector4i sub(int _x, int _y, int _z, int _w) {
		x -= _x;
		y -= _y;
		z -= _z;
		w -= _w;
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
	public Vector4i sub(Vector4i v) {
		return sub(v.x, v.y, v.z, v.w);
	}
	/**
	 * In-Place Uniform Subtraction
	 * <pre>
	 * This = This - {f, f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector4i sub(int f) {
		return sub(f, f, f, f);
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
	 * This = This - s * {_x, _y, _z, _w}
	 * </pre>
	 * @param s Scalar
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @param _w W Component
	 * @return This
	 */
	public Vector4i subMultiple(int s, int _x, int _y, int _z, int _w) {
		x -= s * _x;
		y -= s * _y;
		z -= s * _z;
		w -= s * _w;
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
	public Vector4i subMultiple(int s, Vector4i v) {
		return subMultiple(s, v.x, v.y, v.z, v.w);
	}
	
	/**
	 * In-Place Multiplication
	 * <pre>
	 * This = This * {_x, _y, _z, _w}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @param _w W Component
	 * @return This
	 */
	public Vector4i mul(int _x, int _y, int _z, int _w) {
		x *= _x;
		y *= _y;
		z *= _z;
		w *= _w;
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
	public Vector4i mul(Vector4i v) {
		return mul(v.x, v.y, v.z, v.w);
	}
	/**
	 * In-Place Uniform Multiplication
	 * <pre>
	 * This = This * {f, f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector4i mul(int f) {
		return mul(f, f, f, f);
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
	 * This = This / {_x, _y, _z, _w}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @param _w W Component
	 * @return This
	 */
	public Vector4i div(int _x, int _y, int _z, int _w) {
		x /= _x;
		y /= _y;
		z /= _z;
		w /= _w;
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
	public Vector4i div(Vector4i v) {
		return div(v.x, v.y, v.z, v.w);
	}
	/**
	 * In-Place Uniform Division
	 * <pre>
	 * This = This * {1 / f, 1 / f, 1 / f, 1 / f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector4i div(int f) {
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
	 * This = This ^ {_x, _y, _z, _w}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @param _w W Component
	 * @return This
	 */
	public Vector4i pow(int _x, int _y, int _z, int _w) {
		x = powi(x, _x);
		y = powi(y, _y);
		z = powi(z, _z);
		w = powi(w, _w);
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
	public Vector4i pow(Vector4i v) {
		return pow(v.x, v.y, v.z, v.w);
	}
	/**
	 * In-Place Uniform Power
	 * <pre>
	 * This = This ^ {f, f, f, f}
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector4i pow(int f) {
		return pow(f, f, f, f);
	}
	
	/**
	 * In-Place Exponentiation
	 * <pre>
	 * This = {_x, _y, _z, _w} ^ This
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @param _w W Component
	 * @return This
	 */
	public Vector4i exp(int _x, int _y, int _z, int _w) {
		x = powi(_x, x);
		y = powi(_y, y);
		z = powi(_z, z);
		w = powi(_w, w);
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
	public Vector4i exp(Vector4i v) {
		return exp(v.x, v.y, v.z, v.w);
	}
	/**
	 * In-Place Uniform Exponentiation
	 * <pre>
	 * This = {f, f, f, f} ^ This
	 * </pre>
	 * @param f Value
	 * @return This
	 */
	public Vector4i exp(int f) {
		return exp(f, f, f, f);
	}
	
	/**
	 * In-Place Absolution
	 * @return This
	 */
	public Vector4i abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		if(z < 0) z = -z;
		if(w < 0) w = -w;
		return this;
	}
	/**
	 * In-Place Negation
	 * @return This
	 */
	public Vector4i negate() {
		x = -x;
		y = -y;
		z = -z;
		w = -w;
		return this;
	}
	
	/**
	 * Calculates Dot Product Between This And Another
	 * @param v [{@link Vector4i ARR}] Vector
	 * @return Vector Dot Product
	 */
	public int dot(Vector4i v) {
		return x * v.x + y * v.y + z * v.z + w * v.w;
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
	 * @param v [{@link Vector4i POS}] Vector
	 * @return Length Squared Of Offset Vector
	 */
	public int distSq(Vector4i v) {
		int ox = x - v.x;
		int oy = y - v.y;
		int oz = z - v.z;
		int ow = w - v.w;
		return ox * ox + oy * oy + oz * oz + ow * ow;
	}
	/**
	 * Calculates Distance Between This Vector And Another
	 * <pre>
	 * Let off = This - v
	 * Ret len(off)
	 * </pre>
	 * @param v [{@link Vector4i POS}] Vector
	 * @return Length Of Offset Vector
	 */
	public double dist(Vector4i v) {
		return Math.sqrt(distSq(v));
	}
	/**
	 * Calculate Angle Between This Vector And Another
 	 * @param v [{@link Vector4i DIRN}] Vector
	 * @return Angle Between Vectors
	 */
	public double angle(Vector4i v) {
		return Math.acos(dot(v) / (len() * v.len()));
	}
	
	/**
	 * Checks Component Equality Between This Vector And Another
	 * @param v Vector
	 * @return True If All Components Equal
	 */
	public boolean equals(Vector4i v) {
		return x == v.x && y == v.y && z == v.z && w == v.w;
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
		case 3:
			oldVal = w;
			w = element;
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
		case 3: return w;
		default: throw new IndexOutOfBoundsException();
		}
	}
	@Override
	public int size() {
		return NUM_COMPONENTS;
	}

	@Override
	public Vector4i clone() {
		return new Vector4i(this);
	}
}

