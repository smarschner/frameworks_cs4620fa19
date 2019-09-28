package egl.math;


/**
 * 3x3 Matrix With Column Major Ordering
 * <br>Single Precision</br>
 * @author Cristian
 * 
 */
public class Matrix3 implements Cloneable {
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
	public final float[] m = new float[ELEMENTS];

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
	public Matrix3(
			float m00, float m01, float m02,
			float m10, float m11, float m12,
			float m20, float m21, float m22
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
	public Matrix3(float[] _m) {
		for(int i = 0;i < ELEMENTS;i++) m[i] = _m[i];
	}
	/**
	 * Copy Constructor
	 * @param _m Matrix
	 */
	public Matrix3(Matrix3 _m) {
		this(_m.m);
	}
	/**
	 * Minor Constructor Removes Right Column And Bottom Row
	 * @param _m Matrix
	 */
	public Matrix3(Matrix4 _m) {
		this(
				_m.m[0], _m.m[4], _m.m[8],
				_m.m[1], _m.m[5], _m.m[9],
				_m.m[2], _m.m[6], _m.m[10]
				);
	}
	/**
	 * Row Constructor
	 * @param r0 [{@link Vector3 ARR}] Row 1
	 * @param r1 [{@link Vector3 ARR}] Row 2
	 * @param r2 [{@link Vector3 ARR}] Row 3
	 */
	public Matrix3(Vector3 r0, Vector3 r1, Vector3 r2) {
		this(
				r0.x, r0.y, r0.z,
				r1.x, r1.y, r1.z,
				r2.x, r2.y, r2.z
				);
	}
	/**
	 * Frame (Column) Constructor
	 * @param x [{@link Vector2 DIRN}] X Axis Direction
	 * @param y [{@link Vector2 DIRN}] Y Axis Direction
	 * @param t [{@link Vector2 POS}] Translation
	 */
	public Matrix3(Vector2 x, Vector2 y, Vector2 t) {
		this(
				x.x, y.x, t.x,
				x.y, y.y, t.y,
				0, 0, 1
				);
	}
	/**
	 * Identity Matrix Constructor
	 */
	public Matrix3() {
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
	 * Read One Entry Of The Matrix
	 * @param r Row    In Range [0,SIZE)
	 * @param c Column In Range [0,SIZE)
	 * @return Entry at Row r and Column c.
	 */
	public float get(int r, int c) {
		return m[index(r,c)];
	}
	
	/**
	 * Write One Entry Of The Matrix
	 * @param r Row    In Range [0,SIZE)
	 * @param c Column In Range [0,SIZE)
	 * @param v Value To Write
	 */
	public void set(int r, int c, float v) {
		m[index(r,c)] = v;
	}

	/**
	 * Helper To Calculate An Inner Product
	 * @param left Left Side Matrix
	 * @param right Right Side Matrix
	 * @param r Row
	 * @param c Column
	 * @return Dot Product Of Row r Of Left and Column c of Right
	 */
	private static float innerProduct(Matrix3 left, Matrix3 right, int r, int c) {
		return
				left.m[index(r, 0)] * right.m[index(0, c)] +
				left.m[index(r, 1)] * right.m[index(1, c)] +
				left.m[index(r, 2)] * right.m[index(2, c)]
						;
	}

	/**
	 * Set A Vector To The X-Axis Of This Matrix
	 * @param out [{@link Vector2 OUT}] Vector
	 * @return Out
	 */
	public Vector2 getX(Vector2 out) {
		return out.set(m[0], m[1]);
	}
	/**
	 * Obtains The X-Axis Of This Matrix
	 * @return New Vector
	 */
	public Vector2 getX() {
		return new Vector2(m[0], m[1]);
	}
	/**
	 * Set A Vector To The Y-Axis Of This Matrix
	 * @param out [{@link Vector2 OUT}] Vector
	 * @return Out
	 */
	public Vector2 getY(Vector2 out) {
		return out.set(m[3], m[4]);
	}
	/**
	 * Obtains The Y-Axis Of This Matrix
	 * @return New Vector
	 */
	public Vector2 getY() {
		return new Vector2(m[3], m[4]);
	}
	/**
	 * Set A Vector To The Translation Of This Matrix
	 * @param out [{@link Vector2 OUT}] Vector
	 * @return Out
	 */
	public Vector2 getTrans(Vector2 out) {
		return out.set(m[6], m[7]);
	}
	/**
	 * Obtains The Translation Of This Matrix
	 * @return New Vector
	 */
	public Vector2 getTrans() {
		return new Vector2(m[6], m[7]);
	}
	/**
	 * Set A Vector To Row r Of This Matrix
	 * @param r Row
	 * @param out [{@link Vector3 OUT}] Vector
	 * @return Out
	 */
	public Vector3 getRow(int r, Vector3 out) {
		return out.set(m[r], m[r + 3], m[r + 6]);
	}
	/**
	 * Obtains Row r Of This Matrix
	 * @param r Row
	 * @return New Vector
	 */
	public Vector3 getRow(int r) {
		return new Vector3(m[r], m[r + 3], m[r + 6]);
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
	public Matrix3 set(
			float m00, float m01, float m02,
			float m10, float m11, float m12,
			float m20, float m21, float m22
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
	public Matrix3 setIdentity() {
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
	public Matrix3 set(Matrix3 mat) {
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
	public Matrix3 mulAfter(Matrix3 mat) {
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
	public Matrix3 mulBefore(Matrix3 mat) {
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
	public Matrix3 mulAfter(Matrix3 mat, Matrix3 out) {
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
	public Matrix3 mulBefore(Matrix3 mat, Matrix3 out) {
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
	public Vector3 mul(Vector3 v) {
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
	 * @param v [{@link Vector2 POS}] Vector
	 * @return Transformed Vector
	 */
	public Vector2 mul(Vector2 v) {
		float w = m[2] * v.x + m[5] * v.y + m[8];
		return v.set(
				m[0] * v.x + m[3] * v.y + m[6],
				m[1] * v.x + m[4] * v.y + m[7]
				).div(w);
	}

	/**
	 * Transpose In Place
	 * @return This
	 */
	public Matrix3 transpose() {
		float t;
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
	private float coFactor(int c, int r) {
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
	 * Add Matrix m1 Into This
	 * @param m1
	 * @return This
	 */
	public Matrix3 add(Matrix3 m1) {
		for (int i = 0; i < ELEMENTS; i++)
			m[i] += m1.m[i];
		return this;
	}
	
	/**
	 * Subtract Matrix m1 From This
	 * @param m1
	 * @return This
	 */
	public Matrix3 sub(Matrix3 m1) {
		for (int i = 0; i < ELEMENTS; i++)
			m[i] -= m1.m[i];
		return this;
	}
	
	/**
	 * Calculates The Determinant Of This Matrix
	 * @return Determinant
	 */
	public float determinant() {
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
	public Matrix3 invert() throws AssertionError {
		float cof00 = coFactor(0, 0);
		float cof01 = coFactor(0, 1);
		float cof02 = coFactor(0, 2);
		float det = 
				m[0] * cof00 +
				m[1] * cof01 +
				m[2] * cof02
				;
		if(det == 0) throw new AssertionError("Determinant Of 0");
		float f = 1 / det;
		return set(
				f * cof00, f * cof01, f * cof02,
				f * coFactor(1, 0), f * coFactor(1, 1), f * coFactor(1, 2),
				f * coFactor(2, 0), f * coFactor(2, 1), f * coFactor(2, 2)
				);
	}
	
	/**
	 * Set this to a linear interpolation of two other matrices
	 * @return this
	 */
	public Matrix3 interpolate(Matrix3 m1, Matrix3 m2, float r) {
		for(int i = 0; i < ELEMENTS; i++) {
			m[i] = (m2.m[i] - m1.m[i]) * r + m1.m[i];
		}
		return this;
	}
	
	/**
	 * The 1-norm of this matrix (the maximum column sum)
	 * @return the norm
	 */
	public float norm1() {
		float norm = 0;
		for (int c = 0; c < SIZE; c++) {
			float sum = 0;
			for (int r = 0; r < SIZE; r++)
				sum += Math.abs(m[index(r,c)]);
			norm = Math.max(norm, sum);
		}
		return norm;
	}
	
	/**
	 * Compute polar decomposition of this.
	 * Algorithm from:
	 *    N. Higham, Computing the Polar Decomposition---with Applications
	 *    SIAM J. Sci. Stat. Comput. 7:4 (Oct 1986) 
	 */
	public void polar_decomp(Matrix3 outQ, Matrix3 outP) {
	    final float TOL = 1e-6f;
	    Matrix3 Xprev = new Matrix3();
	    Matrix3 X = new Matrix3(this);
	    Matrix3 Y = new Matrix3();
	    do {
	    	Y.set(X).invert();
	    	Xprev.set(X);
	    	X.interpolate(X, Y.transpose(), 0.5f);
	    } while (X.clone().sub(Xprev).norm1() > TOL * Xprev.norm1());
	    outQ.set(X);
	    outP.set(X).transpose().mulBefore(this);
	}

	/**
	 * @return A Copy Of This
	 */
	@Override
	public Matrix3 clone() {
		return new Matrix3(this);
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
	public static Matrix3 createScale(float x, float y, Matrix3 out) {
		return out.set(
				x, 0, 0,
				0, y, 0,
				0, 0, 1
				);
	}
	/**
	 * @see {@link #createScale(float, float, Matrix3) Mat3.createScale(v.x, v.y, out)}
	 * @param v [{@link Vector2 ARR}] Scaling Vector
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3 createScale(Vector2 v, Matrix3 out) {
		return createScale(v.x, v.y, out);
	}
	/**
	 * @see {@link #createScale(float, float, Matrix3) Mat3.createScale(s, s, out)}
	 * @param s Uniform Scaling
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3 createScale(float s, Matrix3 out) {
		return createScale(s, s, out);
	}
	/**
	 * @see {@link #createScale(float, float, Matrix3) Mat3.createScale(x, y, new Mat3())}
	 * @param x X Scale
	 * @param y Y Scale
	 * @param z Z Scale
	 * @return New Matrix
	 */
	public static Matrix3 createScale(float x, float y) {
		return createScale(x, y, new Matrix3());
	}
	/**
	 * @see {@link #createScale(Vector2, Matrix3) Mat3.createScale(v, new Mat3())}
	 * @param v [{@link Vector2 ARR}] Scaling Vector
	 * @return New Matrix
	 */
	public static Matrix3 createScale(Vector2 v) {
		return createScale(v, new Matrix3());
	}
	/**
	 * @see {@link #createScale(float, Matrix3) Mat3.createScale(s, new Mat3())}
	 * @param s Uniform Scaling
	 * @return New Matrix
	 */
	public static Matrix3 createScale(float s) {
		return createScale(s, new Matrix3());
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
	public static Matrix3 createTranslation(float x, float y, Matrix3 out) {
		return out.set(
				1, 0, x,
				0, 1, y,
				0, 0, 1
				);
	}
	/**
	 * @see {@link #createTranslation(float, float, Matrix3) Mat3.createTranslation(v.x, v.y, out)}
	 * @param v [{@link Vector2 POS}] Translation Vector
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix3 createTranslation(Vector2 v, Matrix3 out) {
		return createTranslation(v.x, v.y, out);
	}
	/**
	 * @see {@link #createTranslation(float, float, Matrix3) Mat3.createTranslation(x, y, new Mat3())}
	 * @param x X Translation
	 * @param y Y Translation
	 * @return New Matrix
	 */
	public static Matrix3 createTranslation(float x, float y) {
		return createTranslation(x, y, new Matrix3());
	}
	/**
	 * @see {@link #createTranslation(Vector2, Matrix3) Mat3.createTranslation(v, new Mat3())}
	 * @param v [{@link Vector2 POS}] Translation Vector
	 * @return New Matrix
	 */
	public static Matrix3 createTranslation(Vector2 v) {
		return createTranslation(v, new Matrix3());
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
	public static Matrix3 createRotation(float t, Matrix3 out) {
		float cosT = (float)Math.cos(t), sinT = (float)Math.sin(t);
		return out.set(
				cosT, -sinT, 0,
				sinT, cosT, 0,
				0, 0, 1
				);
	}
	/**
	 * @see {@link #createRotation(float, Matrix3) Mat3.createRotation(t, new Mat3())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix3 createRotation(float t) {
		return createRotation(t, new Matrix3());
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
	public static Matrix3 createRotationX(float t, Matrix3 out) {
		float cosT = (float)Math.cos(t), sinT = (float)Math.sin(t);
		return out.set(
				1, 0, 0,
				0, cosT, -sinT,
				0, sinT, cosT
				);
	}
	/**
	 * @see {@link #createRotationX(float, Matrix3) Mat3.createRotationX(t, new Mat3())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix3 createRotationX(float t) {
		return createRotationX(t, new Matrix3());
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
	public static Matrix3 createRotationY(float t, Matrix3 out) {
		float cosT = (float)Math.cos(t), sinT = (float)Math.sin(t);
		return out.set(
				cosT, 0, sinT,
				0, 1, 0,
				-sinT, 0, cosT
				);
	}
	/**
	 * @see {@link #createRotationY(float, Matrix3) Mat3.createRotationY(t, new Mat3())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix3 createRotationY(float t) {
		return createRotationY(t, new Matrix3());
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
	public static Matrix3 createRotationZ(float t, Matrix3 out) {
		float cosT = (float)Math.cos(t), sinT = (float)Math.sin(t);
		return out.set(
				cosT, -sinT, 0,
				sinT, cosT, 0,
				0, 0, 1
				);
	}
	/**
	 * @see {@link #createRotationZ(float, Matrix3) Mat3.createRotationZ(t, new Mat3())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix3 createRotationZ(float t) {
		return createRotationZ(t, new Matrix3());
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
	public static Matrix3 createOrthographic(float l, float r, float b, float t, Matrix3 out) {
		float w = r - l, h = t - b;
		return out.set(
				2 / w, 0, -(l + r) / w,
				0, 2 / h, -(b + t) / h,
				0, 0, 1);
	}
	/**
	 * @see {@link #createOrthographic(float, float, float, float, Matrix3) Mat3.createOrthographic(l, r, b, t, new Mat3())}
	 * @param l Left Side Of Rect
	 * @param r Right Side Of Rect
	 * @param b Bottom Side Of Rect
	 * @param t Top Side Of Rect
	 * @return New Matrix
	 */
	public static Matrix3 createOrthographic(float l, float r, float b, float t) {
		return createOrthographic(l, r, b, t, new Matrix3());
	}

}
