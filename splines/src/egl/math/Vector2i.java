package egl.math;
import java.util.AbstractList;

/**
 * A 2-Component Vector
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
public class Vector2i extends AbstractList<Integer> implements Cloneable {
	public static final int NUM_COMPONENTS = 2;
	
	/** 
	 * Coordinate Component Of The Vector
	 */
	public int x, y;

	/**
	 * Inline Constructor
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 */
	public Vector2i(int _x, int _y) {
		x = _x;
		y = _y;
	}
	/**
	 * Copy Constructor
	 * @param v Vector
	 */
	public Vector2i(Vector2i v) {
		this(v.x, v.y);
	}
	/**
	 * Uniform Value Constructor
	 * @param f Value
	 */
	public Vector2i(int f) {
		this(f, f);
	}
	/**
	 * Zero Vector Constructor
	 */
	public Vector2i() {
		this(0);
	}

	@Override
	public String toString() {
		return "{"+x+", "+y+"}i";
	}
	
	/**
	 * Inline Setter
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @return This
	 */
	public Vector2i set(int _x, int _y) {
		x = _x;
		y = _y;
		return this;
	}
	/**
	 * Set This Vector To The Zero Vector
	 * @return This
	 */
	public Vector2i setZero() {
		return set(0);
	}
	/**
	 * Set This Vector To Uniform Values
	 * @param f Value
	 * @return This
	 */
	public Vector2i set(int f) {
		return set(f, f);
	}
	/**
	 * Copy Vector Into This Vector
	 * @param v Vector
	 * @return This
	 */
	public Vector2i set(Vector2i v) {
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
	public Vector2i setMultiple(int s, int _x, int _y) {
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
	public Vector2i setMultiple(int s, Vector2i v) {
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
	public Vector2i add(int _x, int _y) {
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
	public Vector2i add(Vector2i v) {
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
	public Vector2i add(int f) {
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
	public Vector2i addMultiple(int s, int _x, int _y) {
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
	public Vector2i addMultiple(int s, Vector2i v) {
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
	public Vector2i sub(int _x, int _y) {
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
	public Vector2i sub(Vector2i v) {
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
	public Vector2i sub(int f) {
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
	public Vector2i subMultiple(int s, int _x, int _y) {
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
	public Vector2i subMultiple(int s, Vector2i v) {
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
	public Vector2i mul(int _x, int _y) {
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
	public Vector2i mul(Vector2i v) {
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
	public Vector2i mul(int f) {
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
	public Vector2i div(int _x, int _y) {
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
	public Vector2i div(Vector2i v) {
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
	public Vector2i div(int f) {
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
	 * This = This ^ {_x, _y}
	 * </pre>
	 * @param _x X Component
	 * @param _y Y Component
	 * @return This
	 */
	public Vector2i pow(int _x, int _y) {
		x = powi(x, _x);
		y = powi(y, _y);
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
	public Vector2i pow(Vector2i v) {
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
	public Vector2i pow(int f) {
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
	public Vector2i exp(int _x, int _y) {
		x = powi(_x, x);
		y = powi(_y, y);
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
	public Vector2i exp(Vector2i v) {
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
	public Vector2i exp(int f) {
		return exp(f, f);
	}
	
	/**
	 * In-Place Absolution
	 * @return This
	 */
	public Vector2i abs() {
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		return this;
	}
	/**
	 * In-Place Negation
	 * @return This
	 */
	public Vector2i negate() {
		x = -x;
		y = -y;
		return this;
	}
	
	/**
	 * Calculates Dot Product Between This And Another
	 * @param v [{@link Vector2i ARR}] Vector
	 * @return Vector Dot Product
	 */
	public int dot(Vector2i v) {
		return x * v.x + y * v.y;
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
	 * @param v [{@link Vector2i POS}] Vector
	 * @return Length Squared Of Offset Vector
	 */
	public int distSq(Vector2i v) {
		int ox = x - v.x;
		int oy = y - v.y;
		return ox * ox + oy * oy;
	}
	/**
	 * Calculates Distance Between This Vector And Another
	 * <pre>
	 * Let off = This - v
	 * Ret len(off)
	 * </pre>
	 * @param v [{@link Vector2i POS}] Vector
	 * @return Length Of Offset Vector
	 */
	public double dist(Vector2i v) {
		return Math.sqrt(distSq(v));
	}
	/**
	 * Calculate Angle Between This Vector And Another
 	 * @param v [{@link Vector2i DIRN}] Vector
	 * @return Angle Between Vectors
	 */
	public double angle(Vector2i v) {
		return Math.acos(dot(v) / (len() * v.len()));
	}

	/**
	 * Checks Component Equality Between This Vector And Another
	 * @param v Vector
	 * @return True If All Components Equal
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

