package egl.math;

import java.util.AbstractList;

/**
 * A Quaternion With WXYZ Ordering
 * <br>Double Precision</br>
 * @author Cristian
 *
 */
public class Quatd extends AbstractList<Double> implements Cloneable {
	/** 
	 * Components Of The Quaternion
	 */
	public double w, x, y, z;

	/**
	 * Inline Constructor
	 * @param _w W Coordinate
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 */
	public Quatd(double _w, double _x, double _y, double _z) {
		w = _w;
		x = _x;
		y = _y;
		z = _z;
	}
	/**
	 * Copy Constructor
	 * @param q Quaternion
	 */
	public Quatd(Quatd q) {
		this(q.w, q.x, q.y, q.z);
	}
	/**
	 * Uniform Value Constructor
	 * @param f Value
	 */
	public Quatd(double f) {
		this(f, f, f, f);
	}
	/**
	 * Identity Quaternion Constructor
	 */
	public Quatd() {
		this(1, 0, 0, 0);
	}
	/**
	 * Rotation Matrix Constructor
	 * @param m Rotation Matrix
	 */
	public Quatd(Matrix3d m) {
		set(m);
	}
	/**
	 * Rotation Matrix Constructor
	 * @param m Rotation Matrix
	 */
	public Quatd(Matrix4d m) {
		set(m);
	}
	
	@Override
	public String toString() {
		return "{"+w+", "+x+", "+y+", "+z+"}d";
	}
	
	/**
	 * Inline Setter
	 * @param _w W Coordinate
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 * @return This
	 */
	public Quatd set(double _w, double _x, double _y, double _z) {
		w = _w;
		x = _x;
		y = _y;
		z = _z;
		return this;
	}
	/**
	 * Set This Quaternion To The Identity Quaternion
	 * @return This
	 */
	public Quatd setIdentity() {
		return set(1, 0, 0, 0);
	}
	/**
	 * Set This Quaternion To Uniform Values
	 * @param f Value
	 * @return This
	 */
	public Quatd set(double f) {
		return set(f, f, f, f);
	}
	/**
	 * Copy Quaternion Into This Quaternion
	 * @param q Quaternion
	 * @return This
	 */
	public Quatd set(Quatd q) {
		return set(q.w, q.x, q.y, q.z);
	}
	/**
	 * Sets This Quaternion To A Multiple Of WXYZ Components
	 * <pre>
	 * This = s * {_w, _x, _y, _z}
	 * </pre>
	 * @param s Scaling
	 * @param _w W Component
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Quatd setScaled(double s, double _w, double _x, double _y, double _z) {
		w = s * _w;
		x = s * _x;
		y = s * _y;
		z = s * _z;
		return this;
	}
	/**
	 * Sets This Quaternion To A Multiple Of Another
	 * <pre>
	 * This = s * q
	 * </pre>
	 * @param s Scaling
	 * @param q Quaternion
	 * @return This
	 */
	public Quatd setScaled(double s, Quatd q) {
		return setScaled(s, q.w, q.x, q.y, q.z);
	}
	/**
	 * Set This Quaternion To The Rotation Matrix
	 * @param m Rotation Matrix
	 * @return This
	 */
	public Quatd set(Matrix3d m) {
		w =  m.m[0] + m.m[4] + m.m[8] + 1.0f;
		if(w < 0.0f) w = 0.0f; else { w = Math.sqrt(w / 4.0); }
		
		x =  m.m[0] - m.m[4] - m.m[8] + 1.0f;
		if(x < 0.0f) x = 0.0f; else { x = Math.sqrt(x / 4.0); }
		
		y = -m.m[0] + m.m[4] - m.m[8] + 1.0f;
		if(y < 0.0f) y = 0.0f; else { y = Math.sqrt(y / 4.0); }
		
		z = -m.m[0] - m.m[4] + m.m[8] + 1.0f;
		if(z < 0.0f) z = 0.0f; else { z = Math.sqrt(z / 4.0); }
		
		if(w >= x && w >= y && w >= z) {
		    if(m.m[7] - m.m[5] < 0) x = -x;
		    if(m.m[2] - m.m[3] < 0) y = -y;
		    if(m.m[3] - m.m[1] < 0) z = -z;
		} 
		else if(x >= y && x >= z) {
			if(m.m[7] - m.m[5] < 0) w = -w;
		    if(m.m[2] + m.m[1] < 0) y = -y;
		    if(m.m[3] + m.m[6] < 0) z = -z;
		} 
		else if(y >= z) {
			if(m.m[2] - m.m[6] < 0) w = -w;
		    if(m.m[3] + m.m[1] < 0) x = -x;
		    if(m.m[7] + m.m[5] < 0) z = -z;
		}
		else {
			if(m.m[3] - m.m[1] < 0) w = -w;
		    if(m.m[6] + m.m[2] < 0) x = -x;
		    if(m.m[7] + m.m[5] < 0) y = -y;
		}
		return normalize();
	}
	/**
	 * Set This Quaternion To The Rotation Matrix
	 * @param m Rotation Matrix
	 * @return This
	 */
	public Quatd set(Matrix4d m) {
		w =  m.m[0] + m.m[5] + m.m[10] + 1.0f;
		if(w < 0.0f) w = 0.0f; else { w = Math.sqrt(w / 4.0); }
		
		x =  m.m[0] - m.m[5] - m.m[10] + 1.0f;
		if(x < 0.0f) x = 0.0f; else { x = Math.sqrt(x / 4.0); }
		
		y = -m.m[0] + m.m[5] - m.m[10] + 1.0f;
		if(y < 0.0f) y = 0.0f; else { y = Math.sqrt(y / 4.0); }
		
		z = -m.m[0] - m.m[5] + m.m[10] + 1.0f;
		if(z < 0.0f) z = 0.0f; else { z = Math.sqrt(z / 4.0); }
		
		if(w >= x && w >= y && w >= z) {
		    if(m.m[9] - m.m[6] < 0) x = -x;
		    if(m.m[2] - m.m[4] < 0) y = -y;
		    if(m.m[4] - m.m[1] < 0) z = -z;
		} 
		else if(x >= y && x >= z) {
			if(m.m[9] - m.m[6] < 0) w = -w;
		    if(m.m[2] + m.m[1] < 0) y = -y;
		    if(m.m[4] + m.m[8] < 0) z = -z;
		} 
		else if(y >= z) {
			if(m.m[2] - m.m[8] < 0) w = -w;
		    if(m.m[4] + m.m[1] < 0) x = -x;
		    if(m.m[9] + m.m[6] < 0) z = -z;
		}
		else {
			if(m.m[4] - m.m[1] < 0) w = -w;
		    if(m.m[8] + m.m[2] < 0) x = -x;
		    if(m.m[9] + m.m[6] < 0) y = -y;
		}
		return normalize();
	}

	/**
	 * In-Place Hamilton Product
	 * <pre>
	 * This = This * {_w, _x, _y, _z}
	 * </pre>
	 * @param _w W Component
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Quatd mul(double _w, double _x, double _y, double _z) {
		return set(
				w * _w - x * _x - y * _y - z * _z,
				w * _x + x * _w + y * _z - z * _y,
				w * _y - x * _z + y * _w + z * _x,
				w * _z + x * _y - y * _x + z * _w
				);
	}
	/**
	 * In-Place Hamilton Product
	 * <pre>
	 * This = This * q
	 * </pre>
	 * @param q Quaternion
	 * @return This
	 */
	public Quatd mul(Quatd q) {
		return mul(q.w, q.x, q.y, q.z);
	}

	/**
	 * In-Place Scaling
	 * @param s Scaling Factor
	 * @return This
	 */
	public Quatd scale(double s) {
		w *= s;
		x *= s;
		y *= s;
		z *= s;
		return this;
	}
	/**
	 * In-Place Negation
	 * @return This
	 */
	public Quatd negate() {
		w = -w;
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	/**
	 * In-Place Conjugation
	 * @return This
	 */
	public Quatd conjugate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	/**
	 * Calculates The Component-wise (Vector) Length Squared
	 * @return Length Squared
	 */
	public double lenSq() {
		return w * w + x * x + y * y + z * z;
	}
	/**
	 * Calculates The Component-wise (Vector) Length
	 * @return Length
	 */
	public double len() {
		return Math.sqrt(lenSq());
	}
	/**
	 * In-Place Normalization
	 * @return This
	 */
	public Quatd normalize() {
		return scale(1 / len());
	}
	
	/**
	 * In-Place Componentwise Addition
	 * <pre>
	 * This = This + {_w, _x, _y, _z}
	 * </pre>
	 * @param _w W Component
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Quatd add(double _w, double _x, double _y, double _z) {
		w += _w;
		x += _x;
		y += _y;
		z += _z;
		return this;
	}
	/**
	 * In-Place Componentwise Addition
	 * <pre>
	 * This = This + q
	 * </pre>
	 * @param q Quaternion
	 * @return This
	 */
	public Quatd add(Quatd q) {
		return add(q.w, q.x, q.y, q.z);
	}
	/**
	 * In-Place Componentwise Scaled Addition
	 * <pre>
	 * This = This + s * {_w, _x, _y, _z}
	 * </pre>
	 * @param s Scaling
	 * @param _w W Component
	 * @param _x X Component
	 * @param _y Y Component
	 * @param _z Z Component
	 * @return This
	 */
	public Quatd addScaled(double s, double _w, double _x, double _y, double _z) {
		w += s * _w;
		x += s * _x;
		y += s * _y;
		z += s * _z;
		return this;
	}
	/**
	 * In-Place Componentwise Scaled Addition
	 * <pre>
	 * This = This + s * q
	 * </pre>
	 * @param s Scaling
	 * @param q Quaternion
	 * @return This
	 */
	public Quatd addScaled(double s, Quatd q) {
		return addScaled(s, q.w, q.x, q.y, q.z);
	}

	/**
	 * Return The Normalized Axis And The Angle Of This Rotation
	 * @param aa [{@link Vector4 OUT}] Unit Axis Then Angle (Radians)
	 * @return Out
	 */
	public Vector4d toAxisAngle(Vector4d aa) {
		double l = Math.sqrt(x * x + y * y + z * z);
		double u = Math.atan2(l, w);
		aa.w = 2 * u;
		if(l < 0.001) {
			aa.x = 0;
			aa.y = 1;
			aa.z = 0;
		}
		else {
			aa.x = (x / l);
			aa.y = (y / l);
			aa.z = (z / l);
		}
		return aa;
	}
	/**
	 * Create A Rotation Matrix From This Quaternion
	 * @param m Non-null Output Matrix
	 * @return Out
	 */
	public Matrix3d toRotationMatrix(Matrix3d m) {
		// Diagonal
		m.m[0] = w * w + x * x - y * y - z * z;
		m.m[4] = w * w - x * x + y * y - z * z;
		m.m[8] = w * w - x * x - y * y + z * z;
		// Corners
		m.m[1] = 2 * x * y - 2 * w * z;
		m.m[2] = 2 * x * z + 2 * w * y;
		m.m[3] = 2 * x * y + 2 * w * z;
		m.m[5] = 2 * y * z - 2 * w * x;
		m.m[6] = 2 * x * z - 2 * w * y;
		m.m[7] = 2 * y * z + 2 * w * x;
		return m;
	}
	/**
	 * Create A Rotation Matrix [Upper 3x3] From This Quaternion
	 * @param m Non-null Output Matrix
	 * @return Out
	 */
	public Matrix4d toRotationMatrix(Matrix4d m) {
		// Diagonal
		m.m[0] = w * w + x * x - y * y - z * z;
		m.m[5] = w * w - x * x + y * y - z * z;
		m.m[10] = w * w - x * x - y * y + z * z;
		// Corners
		m.m[1] = 2 * x * y - 2 * w * z;
		m.m[2] = 2 * x * z + 2 * w * y;
		m.m[4] = 2 * x * y + 2 * w * z;
		m.m[6] = 2 * y * z - 2 * w * x;
		m.m[8] = 2 * x * z - 2 * w * y;
		m.m[9] = 2 * y * z + 2 * w * x;
		return m;
	}

	@Override
	public Double get(int i) {
		switch (i) {
		case 0: return w;
		case 1: return x;
		case 2: return y;
		case 3: return z;
		default: throw new IndexOutOfBoundsException();
		}
	}
	@Override
	public int size() {
		return 4;
	}

	@Override
	public Quatd clone() {
		return new Quatd(this);
	}

	/**
	 * Create A X-Axis Rotation Quaternion Into Out
	 * <pre>
	 * {cos(t / 2), sin(t / 2), 0, 0}
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-null Output Quaternion
	 * @return Out
	 */
	public static Quatd createRotationX(double t, Quatd out) {
		return out.set(Math.cos(t / 2f), Math.sin(t / 2f), 0, 0);
	}
	/**
	 * @see {@link #createRotationX(double, Quatd) Quatd.createRotationX(t, new Quatd())}
	 * @param t Angle (Radians)
	 * @return New Quaternion
	 */
	public static Quatd createRotationX(double t) {
		return createRotationX(t, new Quatd());
	}
	/**
	 * Create A Y-Axis Rotation Quaternion Into Out
	 * <pre>
	 * {cos(t / 2), 0, sin(t / 2), 0}
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-null Output Quaternion
	 * @return Out
	 */
	public static Quatd createRotationY(double t, Quatd out) {
		return out.set(Math.cos(t / 2f), 0, Math.sin(t / 2f), 0);
	}
	/**
	 * @see {@link #createRotationY(double, Quatd) Quatd.createRotationY(t, new Quatd())}
	 * @param t Angle (Radians)
	 * @return New Quaternion
	 */
	public static Quatd createRotationY(double t) {
		return createRotationY(t, new Quatd());
	}
	/**
	 * Create A Z-Axis Rotation Quaternion Into Out
	 * <pre>
	 * {cos(t / 2), 0, 0, sin(t / 2)}
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-null Output Quaternion
	 * @return Out
	 */
	public static Quatd createRotationZ(double t, Quatd out) {
		return out.set(Math.cos(t / 2f), 0, 0, Math.sin(t / 2f));
	}
	/**
	 * @see {@link #createRotationZ(double, Quatd) Quatd.createRotationZ(t, new Quatd())}
	 * @param t Angle (Radians)
	 * @return New Quaternion
	 */
	public static Quatd createRotationZ(double t) {
		return createRotationZ(t, new Quatd());
	}
}
