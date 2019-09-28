package egl.math;


/**
 * 3x3 Matrix With Column Major Ordering
 * <br>Double Precision</br>
 * @author Cristian
 * 
 */
public class Matrix3d implements Cloneable {
	/**
	 * Dimension Of A Row Or Column
	 */
	public static final int SIZE = 3;
	/**
	 * Number Of Elements (SIZE * SIZE)
	 */
	public static final int ELEMENTS = SIZE * SIZE;
	/**
	 * Array Containing The Elements
	 */
	public final double[] m = new double[ELEMENTS];

	/**
	 * Inline Constructor
	 * @param m00 Row 1 Col 1
	 * @param m01 Row 1 Col 2
	 * @param m02 Row 1 Col 3
	 * @param m10 Row 2 Col 1
	 * @param m11 Row 2 Col 2
	 * @param m12 Row 2 Col 3
	 * @param m20 Row 3 Col 1
	 * @param m21 Row 3 Col 2
	 * @param m22 Row 3 Col 3
	 */
	public Matrix3d(
			double m00, double m01, double m02,
			double m10, double m11, double m12,
			double m20, double m21, double m22
			) {
		m[0] = m00;
		m[1] = m10;
		m[2] = m20;
		m[3] = m01;
		m[4] = m11;
		m[5] = m21;
		m[6] = m02;
		m[7] = m12;
		m[8] = m22;
	}
	/**
	 * Array Constructor With Values Starting At Index 0
	 * @param _m Array Of At Least ELEMENTS Length
	 */
	public Matrix3d(double[] _m) {
		for(int i = 0;i < ELEMENTS;i++) m[i] = _m[i];
	}
	/**
	 * Copy Constructor
	 * @param _m Matrix
	 */
	public Matrix3d(Matrix3d _m) {
		this(_m.m);
	}
	/**
	 * Minor Constructor Removes Right Column And Bottom Row
	 * @param _m Matrix
	 */
	public Matrix3d(Matrix4d _m) {
		this(
				_m.m[0], _m.m[4], _m.m[8],
				_m.m[1], _m.m[5], _m.m[9],
				_m.m[2], _m.m[6], _m.m[10]
				);
	}
	/**
	 * Row Constructor
	 * @param r0 [{@link Vector3d ARR}] Row 1
	 * @param r1 [{@link Vector3d ARR}] Row 2
	 * @param r2 [{@link Vector3d ARR}] Row 3
	 */
	public Matrix3d(Vector3d r0, Vector3d r1, Vector3d r2) {
		this(
				r0.x, r0.y, r0.z,
				r1.x, r1.y, r1.z,
				r2.x, r2.y, r2.z
				);
	}
	/**
	 * Frame (Column) Constructor
	 * @param x [{@link Vector2d DIRN}] X Axis Direction
	 * @param y [{@link Vector2d DIRN}] Y Axis Direction
	 * @param t [{@link Vector2d POS}] Translation
	 */
	public Matrix3d(Vector2d x, Vector2d y, Vector2d t) {
		this(
				x.x, y.x, t.x,
				x.y, y.y, t.y,
				0, 0, 1
				);
	}
	/**
	 * Identity Matrix Constructor
	 */
	public Matrix3d() {
		this(
				1, 0, 0,
				0, 1, 0,
				0, 0, 1
				);
	}

	@Override
	public String toString() {
		return 
				"["+m[0]+", "+m[3]+", "+m[6]+"]\n"+ 
				"["+m[1]+", "+m[4]+", "+m[7]+"]\n"+ 
				"["+m[2]+", "+m[5]+", "+m[8]+"]";
	}

	/**
	 * Element Index Calculation
	 * @param r Column In Range [0,SIZE)
	 * @param c Row    In Range [0,SIZE)
	 * @return Index In Element Array (c * SIZE + r)
	 */
	public static int index(int r, int c) {
		return c * SIZE + r;
	}
	/**
	 * Helper To Calculate An Inner Product
	 * @param left Left Side Matrix
	 * @param right Right Side Matrix
	 * @param r Row
	 * @param c Column
	 * @return Dot Product Of Row r Of Left and Column c of Right
	 */
	private static double innerProduct(Matrix3d left, Matrix3d right, int r, int c) {
		return
				left.m[index(r, 0)] * right.m[index(0, c)] +
				left.m[index(r, 1)] * right.m[index(1, c)] +
				left.m[index(r, 2)] * right.m[index(2, c)]
						;
	}

	/**
	 * Set A Vector To The X-Axis Of This Matrix
	 * @param out [{@link Vector2d OUT}] Vector
	 * @return Out
	 */
	public Vector2d getX(Vector2d out) {
		return out.set(m[0], m[1]);
	}
	/**
	 * Obtains The X-Axis Of This Matrix
	 * @return New Vector
	 */
	public Vector2d getX() {
		return new Vector2d(m[0], m[1]);
	}
	/**
	 * Set A Vector To The Y-Axis Of This Matrix
	 * @param out [{@link Vector2d OUT}] Vector
	 * @return Out
	 */
	public Vector2d getY(Vector2d out) {
		return out.set(m[3], m[4]);
	}
	/**
	 * Obtains The Y-Axis Of This Matrix
	 * @return New Vector
	 */
	public Vector2d getY() {
		return new Vector2d(m[3], m[4]);
	}
	/**
	 * Set A Vector To The Translation Of This Matrix
	 * @param out [{@link Vector2d OUT}] Vector
	 * @return Out
	 */
	public Vector2d getTrans(Vector2d out) {
		return out.set(m[6], m[7]);
	}
	/**
	 * Obtains The Translation Of This Matrix
	 * @return New Vector
	 */
	public Vector2d getTrans() {
		return new Vector2d(m[6], m[7]);
	}
	/**
	 * Set A Vector To Row r Of This Matrix
	 * @param r Row
	 * @param out [{@link Vector3d OUT}] Vector
	 * @return Out
	 */
	public Vector3d getRow(int r, Vector3d out) {
		return out.set(m[r], m[r + 3], m[r + 6]);
	}
	/**
	 * Obtains Row r Of This Matrix
	 * @param r Row
	 * @return New Vector
	 */
	public Vector3d getRow(int r) {
		return new Vector3d(m[r], m[r + 3], m[r + 6]);
	}


	/**
	 * Inline Setter
	 * @param m00 Row 1 Col 1
	 * @param m01 Row 1 Col 2
	 * @param m02 Row 1 Col 3
	 * @param m10 Row 2 Col 1
	 * @param m11 Row 2 Col 2
	 * @param m12 Row 2 Col 3
	 * @param m20 Row 3 Col 1
	 * @param m21 Row 3 Col 2
	 * @param m22 Row 3 Col 3
	 * @return Self
	 */
	public Matrix3d set(
			double m00, double m01, double m02,
			double m10, double m11, double m12,
			double m20, double m21, double m22
			) {
		m[0] = m00;
		m[1] = m10;
		m[2] = m20;
		m[3] = m01;
		m[4] = m11;
		m[5] = m21;
		m[6] = m02;
		m[7] = m12;
		m[8] = m22;
		return this;
	}
	/**
	 * Set This Matrix To The Identity Matrix
	 * @return Self
	 */
	public Matrix3d setIdentity() {
		return set(
				1, 0, 0,
				0, 1, 0,
				0, 0, 1
				);
	}
	/**
	 * Copy Matrix Into This Matrix
	 * @param mat Matrix
	 * @return Self
	 */
	public Matrix3d set(Matrix3d mat) {
		for(int i = 0;i < ELEMENTS;i++) m[i] = mat.m[i];
		return this;
	}

	/**
	 * Composes A Matrix M Onto This So That M Applies After This
	 * <pre>
	 * This = Mat * This
	 * </pre>
	 * @param mat Matrix M
	 * @return This
	 */
	public Matrix3d mulAfter(Matrix3d mat) {
		return set(
				innerProduct(mat, this, 0, 0),
				innerProduct(mat, this, 0, 1),
				innerProduct(mat, this, 0, 2),
				innerProduct(mat, this, 1, 0),
				innerProduct(mat, this, 1, 1),
				innerProduct(mat, this, 1, 2),
				innerProduct(mat, this, 2, 0),
				innerProduct(mat, this, 2, 1),
				innerProduct(mat, this, 2, 2)
				);
	}

	/**
	 * Composes A Matrix M Onto This So That M Applies Before This
	 * <pre>
	 * This = This * Mat
	 * </pre>
	 * @param mat Matrix M
	 * @return This
	 */
	public Matrix3d mulBefore(Matrix3d mat) {
		return set(
				innerProduct(this, mat, 0, 0),
				innerProduct(this, mat, 0, 1),
				innerProduct(this, mat, 0, 2),
				innerProduct(this, mat, 1, 0),
				innerProduct(this, mat, 1, 1),
				innerProduct(this, mat, 1, 2),
				innerProduct(this, mat, 2, 0),
				innerProduct(this, mat, 2, 1),
				innerProduct(this, mat, 2, 2)
				);
	}

	/**
	 * Multiplies This Matrix And Another Into Out So That M Applies After This
	 * <pre>
	 * Out = Mat * This
	 * </pre>
	 * @param mat Matrix M
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public Matrix3d mulAfter(Matrix3d mat, Matrix3d out) {
		return out.set(
				innerProduct(mat, this, 0, 0),
				innerProduct(mat, this, 0, 1),
				innerProduct(mat, this, 0, 2),
				innerProduct(mat, this, 1, 0),
				innerProduct(mat, this, 1, 1),
				innerProduct(mat, this, 1, 2),
				innerProduct(mat, this, 2, 0),
				innerProduct(mat, this, 2, 1),
				innerProduct(mat, this, 2, 2)
				);
	}

	/**
	 * Multiplies This Matrix And Another Into Out So That M Applies Before This
	 * <pre>
	 * Out = This * Mat
	 * </pre>
	 * @param mat Matrix M
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public Matrix3d mulBefore(Matrix3d mat, Matrix3d out) {
		return out.set(
				innerProduct(this, mat, 0, 0),
				innerProduct(this, mat, 0, 1),
				innerProduct(this, mat, 0, 2),
				innerProduct(this, mat, 1, 0),
				innerProduct(this, mat, 1, 1),
				innerProduct(this, mat, 1, 2),
				innerProduct(this, mat, 2, 0),
				innerProduct(this, mat, 2, 1),
				innerProduct(this, mat, 2, 2)
				);
	}

	/**
	 * Transforms A Vector In Place
	 * <pre>
	 * V = This * V
	 * <pre>
	 * @param v [{@link #Vec3 POS}] Vector
	 * @return Transformed Vector
	 */
	public Vector3d mul(Vector3d v) {
		return v.set(
				m[0] * v.x + m[3] * v.y + m[6] * v.z,
				m[1] * v.x + m[4] * v.y + m[7] * v.z,
				m[2] * v.x + m[5] * v.y + m[8] * v.z
				);
	}
	/**
	 * Transforms And Homogenizes A Position Vector In Place
	 * <pre>
	 * {V, w} = This * {V, 1}
	 * V = V / w
	 * </pre>
	 * @param v [{@link Vector2d POS}] Vector
	 * @return Transformed Vector
	 */
	public Vector2d mul(Vector2d v) {
		double w = m[2] * v.x + m[5] * v.y + m[8];
		return v.set(
				m[0] * v.x + m[3] * v.y + m[6],
				m[1] * v.x + m[4] * v.y + m[7]
				).div(w);
	}

	/**
	 * Transpose In Place
	 * @return This
	 */
	public Matrix3d transpose() {
		double t;
		t = m[1];  m[1]  = m[3];  m[3] = t;
		t = m[2];  m[2]  = m[6];  m[6] = t;
		t = m[5];  m[5]  = m[7]; m[7] = t;
		return this;
	}
	/**
	 * Helper To Calculate A Cofactor
	 * @param c Excluded Column
	 * @param r Excluded Row
	 * @return Cofactor
	 */
	private double coFactor(int c, int r) {
		switch(c) {
		case 0:
			switch(r) {
			case 0:
				return m[4] * m[8] - m[5] * m[7];
			case 1:
				return m[5] * m[6] - m[3] * m[8];
			case 2:
				return m[3] * m[7] - m[4] * m[6];
			}
		case 1:
			switch(r) {
			case 0:
				return m[2] * m[7] - m[1] * m[8];
			case 1:
				return m[0] * m[8] - m[2] * m[6];
			case 2:
				return m[1] * m[6] - m[0] * m[7];
			}
		case 2:
			switch(r) {
			case 0:
				return m[1] * m[5] - m[2] * m[4];
			case 1:
				return m[2] * m[3] - m[0] * m[5];
			case 2:
				return m[0] * m[4] - m[1] * m[3];
			}
		}
		return 0;
	}
	/**
	 * Calculates The Determinant Of This Matrix
	 * @return Determinant
	 */
	public double determinant() {
		return 
				m[0] * coFactor(0, 0) +
				m[1] * coFactor(0, 1) +
				m[2] * coFactor(0, 2)
				;
	}
	/**
	 * Inverts In Place If Inverse Exists
	 * @return This
	 * @throws AssertionError When Determinant Is Zero
	 */
	public Matrix3d invert() throws AssertionError {
		double cof00 = coFactor(0, 0);
		double cof01 = coFactor(0, 1);
		double cof02 = coFactor(0, 2);
		double det = 
				m[0] * cof00 +
				m[1] * cof01 +
				m[2] * cof02
				;
		if(det == 0) throw new AssertionError("Determinant Of 0");
		double f = 1 / det;
		return set(
				f * cof00, f * cof01, f * cof02,
				f * coFactor(1, 0), f * coFactor(1, 1), f * coFactor(1, 2),
				f * coFactor(2, 0), f * coFactor(2, 1), f * coFactor(2, 2)
				);
	}

	/**
	 * @return A Copy Of This
	 */
	@Override
	public Matrix3d clone() {
		return new Matrix3d(this);
	}

	/**
	 * Create A Scaling Matrix Into Out
	 * <pre>
	 * | x  0  0 |
	 * | 0  y  0 |
	 * | 0  0  1 |
	 * </pre>
	 * @param x X Scale
	 * @param y Y Scale
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createScale(double x, double y, Matrix3d out) {
		return out.set(
				x, 0, 0,
				0, y, 0,
				0, 0, 1
				);
	}
	/**
	 * @see {@link #createScale(double, double, Matrix3d) Mat3.createScale(v.x, v.y, out)}
	 * @param v [{@link Vector2d ARR}] Scaling Vector
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createScale(Vector2d v, Matrix3d out) {
		return createScale(v.x, v.y, out);
	}
	/**
	 * @see {@link #createScale(double, double, Matrix3d) Mat3.createScale(s, s, out)}
	 * @param s Uniform Scaling
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createScale(double s, Matrix3d out) {
		return createScale(s, s, out);
	}
	/**
	 * @see {@link #createScale(double, double, Matrix3d) Mat3.createScale(x, y, new Mat3())}
	 * @param x X Scale
	 * @param y Y Scale
	 * @param z Z Scale
	 * @return New Matrix
	 */
	public static Matrix3d createScale(double x, double y) {
		return createScale(x, y, new Matrix3d());
	}
	/**
	 * @see {@link #createScale(Vector2d, Matrix3d) Mat3.createScale(v, new Mat3())}
	 * @param v [{@link Vector2d ARR}] Scaling Vector
	 * @return New Matrix
	 */
	public static Matrix3d createScale(Vector2d v) {
		return createScale(v, new Matrix3d());
	}
	/**
	 * @see {@link #createScale(double, Matrix3d) Mat3.createScale(s, new Mat3())}
	 * @param s Uniform Scaling
	 * @return New Matrix
	 */
	public static Matrix3d createScale(double s) {
		return createScale(s, new Matrix3d());
	}

	/**
	 * Create A Translation Matrix Into Out
	 * <pre>
	 * | 1  0  x |
	 * | 0  1  y |
	 * | 0  0  1 |
	 * <pre>
	 * @param x X Translation
	 * @param y Y Translation
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createTranslation(double x, double y, Matrix3d out) {
		return out.set(
				1, 0, x,
				0, 1, y,
				0, 0, 1
				);
	}
	/**
	 * @see {@link #createTranslation(double, double, Matrix3d) Mat3.createTranslation(v.x, v.y, out)}
	 * @param v [{@link Vector2d POS}] Translation Vector
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createTranslation(Vector2d v, Matrix3d out) {
		return createTranslation(v.x, v.y, out);
	}
	/**
	 * @see {@link #createTranslation(double, double, Matrix3d) Mat3.createTranslation(x, y, new Mat3())}
	 * @param x X Translation
	 * @param y Y Translation
	 * @return New Matrix
	 */
	public static Matrix3d createTranslation(double x, double y) {
		return createTranslation(x, y, new Matrix3d());
	}
	/**
	 * @see {@link #createTranslation(Vector2d, Matrix3d) Mat3.createTranslation(v, new Mat3())}
	 * @param v [{@link Vector2d POS}] Translation Vector
	 * @return New Matrix
	 */
	public static Matrix3d createTranslation(Vector2d v) {
		return createTranslation(v, new Matrix3d());
	}

	/**
	 * Create A Rotation Matrix Into Out
	 * <pre>
	 * |  cos(t) -sin(t)  0 |
	 * |  sin(t)  cos(t)  0 |
	 * |  0       0       1 |
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createRotation(double t, Matrix3d out) {
		double cosT = Math.cos(t), sinT = Math.sin(t);
		return out.set(
				cosT, -sinT, 0,
				sinT, cosT, 0,
				0, 0, 1
				);
	}
	/**
	 * @see {@link #createRotation(double, Matrix3d) Mat3.createRotation(t, new Mat3())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix3d createRotation(double t) {
		return createRotation(t, new Matrix3d());
	}
	/**
	 * Create A X Rotation Matrix Into Out
	 * <pre>
	 * | 1   0       0      |
	 * | 0   cos(t) -sin(t) |
	 * | 0   sin(t)  cos(t) |
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createRotationX(double t, Matrix3d out) {
		double cosT = Math.cos(t), sinT = Math.sin(t);
		return out.set(
				1, 0, 0,
				0, cosT, -sinT,
				0, sinT, cosT
				);
	}
	/**
	 * @see {@link #createRotationX(double, Matrix3d) Mat3.createRotationX(t, new Mat3())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix3d createRotationX(double t) {
		return createRotationX(t, new Matrix3d());
	}
	/**
	 * Create A Y Rotation Matrix Into Out
	 * <pre>
	 * |  cos(t)  0   sin(t) |
	 * |  0       1   0      |
	 * | -sin(t)  0   cos(t) |
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createRotationY(double t, Matrix3d out) {
		double cosT = Math.cos(t), sinT = Math.sin(t);
		return out.set(
				cosT, 0, sinT,
				0, 1, 0,
				-sinT, 0, cosT
				);
	}
	/**
	 * @see {@link #createRotationY(double, Matrix3d) Mat3.createRotationY(t, new Mat3())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix3d createRotationY(double t) {
		return createRotationY(t, new Matrix3d());
	}
	/**
	 * Create A Z Rotation Matrix Into Out
	 * <pre>
	 * | cos(t)  -sin(t)  0 |
	 * | sin(t)   cos(t)  0 |
	 * | 0        0       1 |
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createRotationZ(double t, Matrix3d out) {
		double cosT = Math.cos(t), sinT = Math.sin(t);
		return out.set(
				cosT, -sinT, 0,
				sinT, cosT, 0,
				0, 0, 1
				);
	}
	/**
	 * @see {@link #createRotationZ(double, Matrix3d) Mat3.createRotationZ(t, new Mat3())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix3d createRotationZ(double t) {
		return createRotationZ(t, new Matrix3d());
	}

	/**
	 * Create An Orthographic Matrix Into Out
	 * <pre>
	 * Let w = r - l
	 * Let h = t - b
	 * |  2 / w  0      -(l + r) / w |
	 * |  0      2 / h  -(b + t) / h |
	 * |  0      0      1            |
	 * </pre>
	 * @param l Left Side Of Rect
	 * @param r Right Side Of Rect
	 * @param b Bottom Side Of Rect
	 * @param t Top Side Of Rect
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3d createOrthographic(double l, double r, double b, double t, Matrix3d out) {
		double w = r - l, h = t - b;
		return out.set(
				2 / w, 0, -(l + r) / w,
				0, 2 / h, -(b + t) / h,
				0, 0, 1);
	}
	/**
	 * @see {@link #createOrthographic(double, double, double, double, Matrix3d) Mat3.createOrthographic(l, r, b, t, new Mat3())}
	 * @param l Left Side Of Rect
	 * @param r Right Side Of Rect
	 * @param b Bottom Side Of Rect
	 * @param t Top Side Of Rect
	 * @return New Matrix
	 */
	public static Matrix3d createOrthographic(double l, double r, double b, double t) {
		return createOrthographic(l, r, b, t, new Matrix3d());
	}

}
