package egl.math;

import java.util.AbstractList;

/**
 * A Quaternion With WXYZ Ordering
 * <br>Single Precision</br>
 * @author Cristian
 *
 */
public class Quat extends AbstractList<Float> implements Cloneable {
	/** 
	 * Components Of The Quaternion
	 */
	public float w, x, y, z;

	/**
	 * Inline Constructor
	 * @param _w W Coordinate
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 */
	public Quat(float _w, float _x, float _y, float _z) {
		w = _w;
		x = _x;
		y = _y;
		z = _z;
	}
	/**
	 * Copy Constructor
	 * @param q Quaternion
	 */
	public Quat(Quat q) {
		this(q.w, q.x, q.y, q.z);
	}
	/**
	 * Uniform Value Constructor
	 * @param f Value
	 */
	public Quat(float f) {
		this(f, f, f, f);
	}
	/**
	 * Rotation Matrix Constructor
	 * @param m Rotation Matrix
	 */
	public Quat(Matrix3 m) {
		set(m);
	}
	/**
	 * Rotation Matrix Constructor
	 * @param m Rotation Matrix
	 */
	public Quat(Matrix4 m) {
		set(m);
	}
	/**
	 * Identity Quaternion Constructor
	 */
	public Quat() {
		this(1, 0, 0, 0);
	}
	
	@Override
	public String toString() {
		return "{"+w+", "+x+", "+y+", "+z+"}f";
	}
	
	/**
	 * Inline Setter
	 * @param _w W Coordinate
	 * @param _x X Coordinate
	 * @param _y Y Coordinate
	 * @param _z Z Coordinate
	 * @return This
	 */
	public Quat set(float _w, float _x, float _y, float _z) {
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
	public Quat setIdentity() {
		return set(1, 0, 0, 0);
	}
	/**
	 * Set This Quaternion To Uniform Values
	 * @param f Value
	 * @return This
	 */
	public Quat set(float f) {
		return set(f, f, f, f);
	}
	/**
	 * Copy Quaternion Into This Quaternion
	 * @param q Quaternion
	 * @return This
	 */
	public Quat set(Quat q) {
		return set(q.w, q.x, q.y, q.z);
	}
	/**
	 * Set This Quaternion To The Rotation Matrix
	 * @param m Rotation Matrix
	 * @return This
	 */
	public Quat set(Matrix3 m) {
		w =  m.m[0] + m.m[4] + m.m[8] + 1.0f;
		if(w < 0.0f) w = 0.0f; else { w = (float)Math.sqrt(w / 4.0); }
		
		x =  m.m[0] - m.m[4] - m.m[8] + 1.0f;
		if(x < 0.0f) x = 0.0f; else { x = (float)Math.sqrt(x / 4.0); }
		
		y = -m.m[0] + m.m[4] - m.m[8] + 1.0f;
		if(y < 0.0f) y = 0.0f; else { y = (float)Math.sqrt(y / 4.0); }
		
		z = -m.m[0] - m.m[4] + m.m[8] + 1.0f;
		if(z < 0.0f) z = 0.0f; else { z = (float)Math.sqrt(z / 4.0); }
		
		if(w >= x && w >= y && w >= z) {
		    if(m.m[5] - m.m[7] < 0) x = -x;
		    if(m.m[6] - m.m[2] < 0) y = -y;
		    if(m.m[1] - m.m[3] < 0) z = -z;
		}
		else if(x >= y && x >= z) {
		    if(m.m[5] - m.m[7] < 0) w = -w;
		    if(m.m[1] + m.m[3] < 0) y = -y;
		    if(m.m[6] + m.m[2] < 0) z = -z;
		}
		else if(y >= z) {
			if(m.m[6] - m.m[2] < 0) w = -w;
			if(m.m[1] + m.m[3] < 0) x = -x;
			if(m.m[5] + m.m[7] < 0) z = -z;
		}
		else {
			if(m.m[1] - m.m[3] < 0) w = -w;
			if(m.m[2] + m.m[6] < 0) x = -x;
			if(m.m[5] + m.m[7] < 0) y = -y;
		}
		return normalize();
	}
	/**
	 * Set This Quaternion To The Rotation Matrix
	 * @param m Rotation Matrix
	 * @return This
	 */
	public Quat set(Matrix4 m) {
		w =  m.m[0] + m.m[5] + m.m[10] + 1.0f;
		if(w < 0.0f) w = 0.0f; else { w = (float)Math.sqrt(w / 4.0); }
		
		x =  m.m[0] - m.m[5] - m.m[10] + 1.0f;
		if(x < 0.0f) x = 0.0f; else { x = (float)Math.sqrt(x / 4.0); }
		
		y = -m.m[0] + m.m[5] - m.m[10] + 1.0f;
		if(y < 0.0f) y = 0.0f; else { y = (float)Math.sqrt(y / 4.0); }
		
		z = -m.m[0] - m.m[5] + m.m[10] + 1.0f;
		if(z < 0.0f) z = 0.0f; else { z = (float)Math.sqrt(z / 4.0); }
		
		if(w >= x && w >= y && w >= z) {
		    if(m.m[6] - m.m[9] < 0) x = -x;
		    if(m.m[8] - m.m[2] < 0) y = -y;
		    if(m.m[1] - m.m[4] < 0) z = -z;
		}
		else if(x >= y && x >= z) {
		    if(m.m[6] - m.m[9] < 0) w = -w;
		    if(m.m[1] + m.m[4] < 0) y = -y;
		    if(m.m[8] + m.m[2] < 0) z = -z;
		}
		else if(y >= z) {
			if(m.m[8] - m.m[2] < 0) w = -w;
			if(m.m[1] + m.m[4] < 0) x = -x;
			if(m.m[6] + m.m[9] < 0) z = -z;
		}
		else {
			if(m.m[1] - m.m[4] < 0) w = -w;
			if(m.m[2] + m.m[8] < 0) x = -x;
			if(m.m[6] + m.m[9] < 0) y = -y;
		}
		return normalize();
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
	public Quat setScaled(float s, float _w, float _x, float _y, float _z) {
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
	public Quat setScaled(float s, Quat q) {
		return setScaled(s, q.w, q.x, q.y, q.z);
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
	public Quat mul(float _w, float _x, float _y, float _z) {
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
	public Quat mul(Quat q) {
		return mul(q.w, q.x, q.y, q.z);
	}

	/**
	 * In-Place Scaling
	 * @param s Scaling Factor
	 * @return This
	 */
	public Quat scale(float s) {
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
	public Quat negate() {
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
	public Quat conjugate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	/**
	 * Calculates The Component-wise (Vector) Length Squared
	 * @return Length Squared
	 */
	public float lenSq() {
		return w * w + x * x + y * y + z * z;
	}
	/**
	 * Calculates The Component-wise (Vector) Length
	 * @return Length
	 */
	public float len() {
		return (float)Math.sqrt(lenSq());
	}
	/**
	 * In-Place Normalization
	 * @return This
	 */
	public Quat normalize() {
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
	public Quat add(float _w, float _x, float _y, float _z) {
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
	public Quat add(Quat q) {
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
	public Quat addScaled(float s, float _w, float _x, float _y, float _z) {
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
	public Quat addScaled(float s, Quat q) {
		return addScaled(s, q.w, q.x, q.y, q.z);
	}
	
        public static Quat slerp(Quat i1, Quat i2, float t)
        {
            //TODO#A7: Calculate interpolated quaternion q using the provided formula
            // To find the angle between q1 and q2  (i1 and i2), you need to first calculate
            // calculate its cosine and clamp to [0,1], then call Math.acos(...) to get the angle.
            // If theta < 0.01f, apply linear interpolation. Otherwise, apply slerp.
        }

	/**
	 * Return The Normalized Axis And The Angle Of This Rotation
	 * @param aa [{@link Vector4 OUT}] Unit Axis Then Angle (Radians)
	 * @return Out
	 */
	public Vector4 toAxisAngle(Vector4 aa) {
		double l = Math.sqrt(x * x + y * y + z * z);
		double u = Math.atan2(l, w);
		aa.w = (float)(2 * u);
		if(l < 0.001) {
			aa.x = 0f;
			aa.y = 1f;
			aa.z = 0f;
		}
		else {
			aa.x = (float)(x / l);
			aa.y = (float)(y / l);
			aa.z = (float)(z / l);
		}
		return aa;
	}
	/**
	 * Create A Rotation Matrix From This Quaternion
	 * @param m Non-null Output Matrix
	 * @return Out
	 */
	public Matrix3 toRotationMatrix(Matrix3 m) {
		float sq = w * w + x * x + y * y + z * z;
		float s = sq > 0 ? (2 / sq) : 0;

		float xs = x*s; float ys = y*s; float zs = z*s;
		float wx = w*xs; float wy = w*ys; float wz = w*zs;
		float xx = x*xs; float xy = x*ys; float xz = x*zs;
		float yy = y*ys; float yz = y*zs; float zz = z*zs;

		// Diagonal
		m.m[0] = 1 - (yy + zz);
		m.m[4] = 1 - (xx + zz);
		m.m[8] = 1 - (xx + yy);
		// Corners
		m.m[1] = xy + wz;
	    m.m[3] = xy - wz;
	    m.m[2] = xz - wy;
	    m.m[6] = xz + wy;
	    m.m[5] = yz + wx;
	    m.m[7] = yz - wx;
		return m;
	}
	/**
	 * Create A Rotation Matrix [Upper 3x3] From This Quaternion
	 * @param m Non-null Output Matrix
	 * @return Out
	 */
	public Matrix4 toRotationMatrix(Matrix4 m) {
		float sq = w * w + x * x + y * y + z * z;
		float s = sq > 0 ? (2 / sq) : 0;

		float xs = x*s; float ys = y*s; float zs = z*s;
		float wx = w*xs; float wy = w*ys; float wz = w*zs;
		float xx = x*xs; float xy = x*ys; float xz = x*zs;
		float yy = y*ys; float yz = y*zs; float zz = z*zs;

		// Diagonal
		m.m[0] =  1 - (yy + zz);
		m.m[5] =  1 - (xx + zz);
		m.m[10] = 1 - (xx + yy);
		// Corners
	    m.m[1] = xy + wz;
	    m.m[4] = xy - wz;
	    m.m[2] = xz - wy;
	    m.m[8] = xz + wy;
	    m.m[6] = yz + wx;
	    m.m[9] = yz - wx;
		return m;
	}

	/**
	 * Rotate A Vector Using This Quaternion
	 * @param v [{@link Vector3 DIRN}] Vector
	 * @return Vector
	 */
	public Vector3 mul(Vector3 v) {
		float sq = w * w + x * x + y * y + z * z;
		float s = sq > 0 ? (2 / sq) : 0;

		float xs = x*s; float ys = y*s; float zs = z*s;
		float wx = w*xs; float wy = w*ys; float wz = w*zs;
		float xx = x*xs; float xy = x*ys; float xz = x*zs;
		float yy = y*ys; float yz = y*zs; float zz = z*zs;

		return v.set(
				v.x * (1 - (yy + zz)) +
				v.y * (xy - wz) +
				v.z * (xz + wy),

				v.x * (xy + wz) +
				v.y * (1 - (xx + zz)) +
				v.z * (yz - wx),

				v.x * (xz - wy) +
				v.y * (yz + wx) +
				v.z * (1 - (xx + yy))
				);
	}

	@Override
	public Float get(int i) {
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
	public Quat clone() {
		return new Quat(this);
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
	public static Quat createRotationX(float t, Quat out) {
		return out.set((float)Math.cos(t / 2f), (float)Math.sin(t / 2f), 0, 0);
	}
	/**
	 * @see {@link #createRotationX(float, Quat) Quat.createRotationX(t, new Quat())}
	 * @param t Angle (Radians)
	 * @return New Quaternion
	 */
	public static Quat createRotationX(float t) {
		return createRotationX(t, new Quat());
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
	public static Quat createRotationY(float t, Quat out) {
		return out.set((float)Math.cos(t / 2f), 0, (float)Math.sin(t / 2f), 0);
	}
	/**
	 * @see {@link #createRotationY(float, Quat) Quat.createRotationY(t, new Quat())}
	 * @param t Angle (Radians)
	 * @return New Quaternion
	 */
	public static Quat createRotationY(float t) {
		return createRotationY(t, new Quat());
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
	public static Quat createRotationZ(float t, Quat out) {
		return out.set((float)Math.cos(t / 2f), 0, 0, (float)Math.sin(t / 2f));
	}
	/**
	 * @see {@link #createRotationZ(float, Quat) Quat.createRotationZ(t, new Quat())}
	 * @param t Angle (Radians)
	 * @return New Quaternion
	 */
	public static Quat createRotationZ(float t) {
		return createRotationZ(t, new Quat());
	}

	/**
	 * Test For Correctness Of Rotation And Conjugation
	 * @param args Ummm... No
	 */
	public static void main(String[] args) {
		Quat qAA = createRotationX(0.4f);
		Vector4 aa = qAA.toAxisAngle(new Vector4());
		System.out.println(aa);
		
		Quat q = createRotationX(Util.PIf / 2);
		Matrix3 r = q.toRotationMatrix(new Matrix3());
		Vector3 v = new Vector3(0, 0, -1);

		System.out.println(r);
		System.out.println(Matrix3.createRotationX(Util.PIf / 2));

		
		Quat q2 = new Quat(q).conjugate().mul(q);
		System.out.println(q2.mul(v.clone()));

		System.out.println(q.mul(v.clone()));
		System.out.println(new Quat(r).mul(v.clone()));
		System.out.println(r.mul(v.clone()));
		System.out.println(q);
		System.out.println(new Quat(r));
	}
}

