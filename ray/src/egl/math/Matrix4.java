package egl.math;

/**
 * 4x4 Matrix With Column Major Ordering
 * <br>Single Precision</br>
 * @author Cristian
 * 
 */
public class Matrix4 implements Cloneable {
	/**
	 * Dimension Of A Row Or Column
	 */
	public static final int SIZE = 4;
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
	 * @param m03 Row 1 Col 4
	 * @param m10 Row 2 Col 1
	 * @param m11 Row 2 Col 2
	 * @param m12 Row 2 Col 3
	 * @param m13 Row 2 Col 4
	 * @param m20 Row 3 Col 1
	 * @param m21 Row 3 Col 2
	 * @param m22 Row 3 Col 3
	 * @param m23 Row 3 Col 4
	 * @param m30 Row 4 Col 1
	 * @param m31 Row 4 Col 2
	 * @param m32 Row 4 Col 3
	 * @param m33 Row 4 Col 4
	 */
	public Matrix4(
			float m00, float m01, float m02, float m03,
			float m10, float m11, float m12, float m13,
			float m20, float m21, float m22, float m23,
			float m30, float m31, float m32, float m33
			) {
		m[0]  = m00;
		m[1]  = m10;
		m[2]  = m20;
		m[3]  = m30;
		m[4]  = m01;
		m[5]  = m11;
		m[6]  = m21;
		m[7]  = m31;
		m[8]  = m02;
		m[9]  = m12;
		m[10] = m22;
		m[11] = m32;
		m[12] = m03;
		m[13] = m13;
		m[14] = m23;
		m[15] = m33;
	}
	/**
	 * Array Constructor With Values Starting At Index 0
	 * @param _m Array Of At Least ELEMENTS Length
	 */
	public Matrix4(float[] _m) {
		for(int i = 0;i < ELEMENTS;i++) m[i] = _m[i];
	}
	/**
	 * Copy Constructor
	 * @param _m Matrix
	 */
	public Matrix4(Matrix4 _m) {
		this(_m.m);
	}
	/**
	 * Minor Constructor extends with zeros and a one
	 * @param _m Matrix
	 */
	public Matrix4(Matrix3 _m) {
		this(
				_m.m[0], _m.m[3], _m.m[6], 0,
				_m.m[1], _m.m[4], _m.m[7], 0,
				_m.m[2], _m.m[5], _m.m[8], 0,
				   0   ,    0   ,    0   , 1
				);
	}
	/**
	 * Row Constructor
	 * @param r0 [{@link Vector4 ARR}] Row 1
	 * @param r1 [{@link Vector4 ARR}] Row 2
	 * @param r2 [{@link Vector4 ARR}] Row 3
	 * @param r3 [{@link Vector4 ARR}] Row 4
	 */
	public Matrix4(Vector4 r0, Vector4 r1, Vector4 r2, Vector4 r3) {
		this(
				r0.x, r0.y, r0.z, r0.w,
				r1.x, r1.y, r1.z, r1.w,
				r2.x, r2.y, r2.z, r2.w,
				r3.x, r3.y, r3.z, r3.w
				);
	}
	/**
	 * Frame (Column) Constructor
	 * @param x [{@link Vector3 DIRN}] X Axis Direction
	 * @param y [{@link Vector3 DIRN}] Y Axis Direction
	 * @param z [{@link Vector3 DIRN}] Z Axis Direction
	 * @param t [{@link Vector3 POS}] Translation
	 */
	public Matrix4(Vector3 x, Vector3 y, Vector3 z, Vector3 t) {
		this(
				x.x, y.x, z.x, t.x,
				x.y, y.y, z.y, t.y,
				x.z, y.z, z.z, t.z,
				0, 0, 0, 1
				);
	}
	/**
	 * Identity Matrix Constructor
	 */
	public Matrix4() {
		this(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
				);
	}

	@Override
	public String toString() {
		return 
				"["+m[0]+", "+m[4]+", "+m[8]+", "+m[12]+"]\n"+ 
				"["+m[1]+", "+m[5]+", "+m[9]+", "+m[13]+"]\n"+ 
				"["+m[2]+", "+m[6]+", "+m[10]+", "+m[14]+"]\n"+ 
				"["+m[3]+", "+m[7]+", "+m[11]+", "+m[15]+"]";
	}

	/**
	 * Element Index Calculation
	 * @param r Row    In Range [0,SIZE)
	 * @param c Column In Range [0,SIZE)
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
	private static float innerProduct(Matrix4 left, Matrix4 right, int r, int c) {
		return
				left.m[index(r, 0)] * right.m[index(0, c)] +
				left.m[index(r, 1)] * right.m[index(1, c)] +
				left.m[index(r, 2)] * right.m[index(2, c)] +
				left.m[index(r, 3)] * right.m[index(3, c)]
						;
	}

	/**
	 * Set A Vector To The X-Axis Of This Matrix
	 * @param out [{@link Vector3 OUT}] Vector
	 * @return Out
	 */
	public Vector3 getX(Vector3 out) {
		return out.set(m[0], m[1], m[2]);
	}
	/**
	 * Obtains The X-Axis Of This Matrix
	 * @return New Vector
	 */
	public Vector3 getX() {
		return new Vector3(m[0], m[1], m[2]);
	}
	/**
	 * Set A Vector To The Y-Axis Of This Matrix
	 * @param out [{@link Vector3 OUT}] Vector
	 * @return Out
	 */
	public Vector3 getY(Vector3 out) {
		return out.set(m[4], m[5], m[6]);
	}
	/**
	 * Obtains The Y-Axis Of This Matrix
	 * @return New Vector
	 */
	public Vector3 getY() {
		return new Vector3(m[4], m[5], m[6]);
	}
	/**
	 * Set A Vector To The Z-Axis Of This Matrix
	 * @param out [{@link Vector3 OUT}] Vector
	 * @return Out
	 */
	public Vector3 getZ(Vector3 out) {
		return out.set(m[8], m[9], m[10]);
	}
	/**
	 * Obtains The Z-Axis Of This Matrix
	 * @return New Vector
	 */
	public Vector3 getZ() {
		return new Vector3(m[8], m[9], m[10]);
	}
	/**
	 * Set A Vector To The Translation Of This Matrix
	 * @param out [{@link Vector3 OUT}] Vector
	 * @return Out
	 */
	public Vector3 getTrans(Vector3 out) {
		return out.set(m[12], m[13], m[14]);
	}
	/**
	 * Obtains The Translation Of This Matrix
	 * @return New Vector
	 */
	public Vector3 getTrans() {
		return new Vector3(m[12], m[13], m[14]);
	}
	/**
	 * Set A Vector To Row r Of This Matrix
	 * @param r Row
	 * @param out [{@link Vector4 OUT}] Vector
	 * @return Out
	 */
	public Vector4 getRow(int r, Vector4 out) {
		return out.set(m[r], m[r + 4], m[r + 8], m[r + 12]);
	}
	/**
	 * Obtains Row r Of This Matrix
	 * @param r Row
	 * @return New Vector
	 */
	public Vector4 getRow(int r) {
		return new Vector4(m[r], m[r + 4], m[r + 8], m[r + 12]);
	}
	/**
	 * Set A Matrix To The XYZ-Axes Of This Matrix
	 * @param out [{@link Matrix3 OUT}] Matrix
	 * @return Out
	 */
	public Matrix3 getAxes(Matrix3 out) {
		return out.set(
				m[0], m[4], m[8],
				m[1], m[5], m[9],
				m[2], m[6], m[10]
				);
	}
	/**
	 * Obtains The XYZ-Axes Of This Matrix
	 * @return New Matrix
	 */
	public Matrix3 getAxes() {
		return new Matrix3(
				m[0], m[4], m[8],
				m[1], m[5], m[9],
				m[2], m[6], m[10]
				);
	}
	
	/**
	 * Inline Setter
	 * @param m00 Row 1 Col 1
	 * @param m01 Row 1 Col 2
	 * @param m02 Row 1 Col 3
	 * @param m03 Row 1 Col 4
	 * @param m10 Row 2 Col 1
	 * @param m11 Row 2 Col 2
	 * @param m12 Row 2 Col 3
	 * @param m13 Row 2 Col 4
	 * @param m20 Row 3 Col 1
	 * @param m21 Row 3 Col 2
	 * @param m22 Row 3 Col 3
	 * @param m23 Row 3 Col 4
	 * @param m30 Row 4 Col 1
	 * @param m31 Row 4 Col 2
	 * @param m32 Row 4 Col 3
	 * @param m33 Row 4 Col 4
	 * @return Self
	 */
	public Matrix4 set(
			float m00, float m01, float m02, float m03,
			float m10, float m11, float m12, float m13,
			float m20, float m21, float m22, float m23,
			float m30, float m31, float m32, float m33
			) {
		m[0]  = m00;
		m[1]  = m10;
		m[2]  = m20;
		m[3]  = m30;
		m[4]  = m01;
		m[5]  = m11;
		m[6]  = m21;
		m[7]  = m31;
		m[8]  = m02;
		m[9]  = m12;
		m[10] = m22;
		m[11] = m32;
		m[12] = m03;
		m[13] = m13;
		m[14] = m23;
		m[15] = m33;
		return this;
	}
	/**
	 * Set This Matrix To The Identity Matrix
	 * @return Self
	 */
	public Matrix4 setIdentity() {
		return set(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
				);
	}
	/**
	 * Copy Matrix Into This Matrix
	 * @param mat Matrix
	 * @return Self
	 */
	public Matrix4 set(Matrix4 mat) {
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
	public Matrix4 mulAfter(Matrix4 mat) {
		return set(
				innerProduct(mat, this, 0, 0),
				innerProduct(mat, this, 0, 1),
				innerProduct(mat, this, 0, 2),
				innerProduct(mat, this, 0, 3),
				innerProduct(mat, this, 1, 0),
				innerProduct(mat, this, 1, 1),
				innerProduct(mat, this, 1, 2),
				innerProduct(mat, this, 1, 3),
				innerProduct(mat, this, 2, 0),
				innerProduct(mat, this, 2, 1),
				innerProduct(mat, this, 2, 2),
				innerProduct(mat, this, 2, 3),
				innerProduct(mat, this, 3, 0),
				innerProduct(mat, this, 3, 1),
				innerProduct(mat, this, 3, 2),
				innerProduct(mat, this, 3, 3)
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
	public Matrix4 mulBefore(Matrix4 mat) {
		return set(
				innerProduct(this, mat, 0, 0),
				innerProduct(this, mat, 0, 1),
				innerProduct(this, mat, 0, 2),
				innerProduct(this, mat, 0, 3),
				innerProduct(this, mat, 1, 0),
				innerProduct(this, mat, 1, 1),
				innerProduct(this, mat, 1, 2),
				innerProduct(this, mat, 1, 3),
				innerProduct(this, mat, 2, 0),
				innerProduct(this, mat, 2, 1),
				innerProduct(this, mat, 2, 2),
				innerProduct(this, mat, 2, 3),
				innerProduct(this, mat, 3, 0),
				innerProduct(this, mat, 3, 1),
				innerProduct(this, mat, 3, 2),
				innerProduct(this, mat, 3, 3)
				);
	}

	/**
	 * Multiplies This Matrix And A Matrix M Into Out So That M Applies After This
	 * <pre>
	 * Out = Mat * This
	 * </pre>
	 * @param mat Matrix M
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public Matrix4 mulAfter(Matrix4 mat, Matrix4 out) {
		return out.set(
				innerProduct(mat, this, 0, 0),
				innerProduct(mat, this, 0, 1),
				innerProduct(mat, this, 0, 2),
				innerProduct(mat, this, 0, 3),
				innerProduct(mat, this, 1, 0),
				innerProduct(mat, this, 1, 1),
				innerProduct(mat, this, 1, 2),
				innerProduct(mat, this, 1, 3),
				innerProduct(mat, this, 2, 0),
				innerProduct(mat, this, 2, 1),
				innerProduct(mat, this, 2, 2),
				innerProduct(mat, this, 2, 3),
				innerProduct(mat, this, 3, 0),
				innerProduct(mat, this, 3, 1),
				innerProduct(mat, this, 3, 2),
				innerProduct(mat, this, 3, 3)
				);
	}

	/**
	 * Multiplies This Matrix And A Matrix M Into Out So That M Applies Before This
	 * <pre>
	 * Out = This * Mat
	 * </pre>
	 * @param mat Matrix M
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public Matrix4 mulBefore(Matrix4 mat, Matrix4 out) {
		return out.set(
				innerProduct(this, mat, 0, 0),
				innerProduct(this, mat, 0, 1),
				innerProduct(this, mat, 0, 2),
				innerProduct(this, mat, 0, 3),
				innerProduct(this, mat, 1, 0),
				innerProduct(this, mat, 1, 1),
				innerProduct(this, mat, 1, 2),
				innerProduct(this, mat, 1, 3),
				innerProduct(this, mat, 2, 0),
				innerProduct(this, mat, 2, 1),
				innerProduct(this, mat, 2, 2),
				innerProduct(this, mat, 2, 3),
				innerProduct(this, mat, 3, 0),
				innerProduct(this, mat, 3, 1),
				innerProduct(this, mat, 3, 2),
				innerProduct(this, mat, 3, 3)
				);
	}

	/**
	 * Transforms A Vector In Place
	 * <pre>
	 * V = This * V
	 * <pre>
	 * @param v [{@link #Vec4 POS}] Vector
	 * @return Transformed Vector
	 */
	public Vector4 mul(Vector4 v) {
		return v.set(
				m[0] * v.x + m[4] * v.y + m[8]  * v.z + m[12] * v.w,
				m[1] * v.x + m[5] * v.y + m[9]  * v.z + m[13] * v.w,
				m[2] * v.x + m[6] * v.y + m[10] * v.z + m[14] * v.w,
				m[3] * v.x + m[7] * v.y + m[11] * v.z + m[15] * v.w
				);
	}
	/**
	 * Transforms And Homogenizes A Position Vector In Place
	 * <pre>
	 * {V, w} = This * {V, 1}
	 * V = V / w
	 * </pre>
	 * @param v [{@link Vector3 POS}] Vector
	 * @return Transformed Vector
	 */
	public Vector3 mulPos(Vector3 v) {
		float w = m[3] * v.x + m[7] * v.y + m[11] * v.z + m[15];
		return v.set(
				m[0] * v.x + m[4] * v.y + m[8]  * v.z + m[12],
				m[1] * v.x + m[5] * v.y + m[9]  * v.z + m[13],
				m[2] * v.x + m[6] * v.y + m[10] * v.z + m[14]
				).div(w);
	}
	
	/**
	 * Transforms A Direction Vector In Place (Does Not Homogenize)
	 * <pre>
	 * {V, _} = This * {V, 0}
	 * </pre>
	 * @param v [{@link Vector3d DIRN}] Vector
	 * @return Transformed Vector
	 */
	public Vector3 mulDir(Vector3 v) {
		return v.set(
				m[0] * v.x + m[4] * v.y + m[8]  * v.z,
				m[1] * v.x + m[5] * v.y + m[9]  * v.z,
				m[2] * v.x + m[6] * v.y + m[10] * v.z);
	}

	/**
	 * Transpose In Place
	 * @return This
	 */
	public Matrix4 transpose() {
		float t;
		t = m[1];  m[1]  = m[4];  m[4] = t;
		t = m[2];  m[2]  = m[8];  m[8] = t;
		t = m[3];  m[3]  = m[12]; m[12] = t;
		t = m[6];  m[6]  = m[9];  m[9] = t;
		t = m[7];  m[7]  = m[13]; m[13] = t;
		t = m[11]; m[11] = m[14]; m[14] = t;
		return this;
	}
	/**
	 * Helper To Calculate A Cofactor
	 * @param c Excluded Column
	 * @param r Excluded Row
	 * @return Cofactor
	 */
	private float coFactor(int c, int r) {
		Matrix3 minor = new Matrix3();
		int i = 0;
		for(int ri = 0;ri < SIZE;ri++) {
			if(ri == c) continue;
			for(int ci = 0;ci < SIZE;ci++) {
				if(ci == r) continue;
				minor.m[i++] = m[ri * 4 + ci];
			}
		}
		if((r + c) % 2 == 0) return minor.determinant();
		else return -minor.determinant();
	}
	/**
	 * Calculates The Determinant Of This Matrix
	 * @return Determinant
	 */
	public float determinant() {
		return 
				m[0] * coFactor(0, 0) +
				m[1] * coFactor(0, 1) +
				m[2] * coFactor(0, 2) +
				m[3] * coFactor(0, 3)
				;
	}
	/**
	 * Inverts In Place If Inverse Exists
	 * @return This
	 * @throws AssertionError When Determinant Is Zero
	 */
	public Matrix4 invert() throws AssertionError {
		float cof00 = coFactor(0, 0);
		float cof01 = coFactor(0, 1);
		float cof02 = coFactor(0, 2);
		float cof03 = coFactor(0, 3);
		float det = 
				m[0] * cof00 +
				m[1] * cof01 +
				m[2] * cof02 +
				m[3] * cof03;
		if(det == 0) throw new AssertionError("Determinant Of 0");
		float f = 1 / det;
		return set(
				f * cof00, f * cof01, f * cof02, f * cof03,
				f * coFactor(1, 0), f * coFactor(1, 1), f * coFactor(1, 2), f * coFactor(1, 3),
				f * coFactor(2, 0), f * coFactor(2, 1), f * coFactor(2, 2), f * coFactor(2, 3),
				f * coFactor(3, 0), f * coFactor(3, 1), f * coFactor(3, 2), f * coFactor(3, 3)
				);
	}
	
	/**
	 * @return A Copy Of This
	 */
	@Override
	public Matrix4 clone() {
		return new Matrix4(this);
	}

	/**
	 * Create A Scaling Matrix Into Out
	 * <pre>
	 * | x  0  0  0 |
	 * | 0  y  0  0 |
	 * | 0  0  z  0 |
	 * | 0  0  0  1 |
	 * </pre>
	 * @param x X Scale
	 * @param y Y Scale
	 * @param z Z Scale
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createScale(float x, float y, float z, Matrix4 out) {
		return out.set(
				x, 0, 0, 0,
				0, y, 0, 0,
				0, 0, z, 0,
				0, 0, 0, 1
				);
	}
	/**
	 * @see {@link #createScale(float, float, float, Matrix4) Mat4.createScale(v.x, v.y, v.z, out)}
	 * @param v [{@link Vector3 ARR}] Scaling Vector
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createScale(Vector3 v, Matrix4 out) {
		return createScale(v.x, v.y, v.z, out);
	}
	/**
	 * @see {@link #createScale(float, float, float, Matrix4) Mat4.createScale(s, s, s, out)}
	 * @param s Uniform Scaling
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createScale(float s, Matrix4 out) {
		return createScale(s, s, s, out);
	}
	/**
	 * @see {@link #createScale(float, float, float, Matrix4) Mat4.createScale(x, y, z, new Mat4())}
	 * @param x X Scale
	 * @param y Y Scale
	 * @param z Z Scale
	 * @return New Matrix
	 */
	public static Matrix4 createScale(float x, float y, float z) {
		return createScale(x, y, z, new Matrix4());
	}
	/**
	 * @see {@link #createScale(Vector3, Matrix4) Mat4.createScale(v, new Mat4())}
	 * @param v [{@link Vector3 ARR}] Scaling Vector
	 * @return New Matrix
	 */
	public static Matrix4 createScale(Vector3 v) {
		return createScale(v, new Matrix4());
	}
	/**
	 * @see {@link #createScale(float, Matrix4) Mat4.createScale(s, new Mat4())}
	 * @param s Uniform Scaling
	 * @return New Matrix
	 */
	public static Matrix4 createScale(float s) {
		return createScale(s, new Matrix4());
	}

	/**
	 * Create A Translation Matrix Into Out
	 * <pre>
	 * | 1  0  0  x |
	 * | 0  1  0  y |
	 * | 0  0  1  z |
	 * | 0  0  0  1 |
	 * <pre>
	 * @param x X Translation
	 * @param y Y Translation
	 * @param z Z Translation
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createTranslation(float x, float y, float z, Matrix4 out) {
		return out.set(
				1, 0, 0, x,
				0, 1, 0, y,
				0, 0, 1, z,
				0, 0, 0, 1
				);
	}
	/**
	 * @see {@link #createTranslation(float, float, float, Matrix4) Mat4.createTranslation(v.x, v.y, v.z, out)}
	 * @param v [{@link Vector3 POS}] Translation Vector
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createTranslation(Vector3 v, Matrix4 out) {
		return createTranslation(v.x, v.y, v.z, out);
	}
	/**
	 * @see {@link #createTranslation(float, float, float, Matrix4) Mat4.createTranslation(x, y, z, new Mat4())}
	 * @param x X Translation
	 * @param y Y Translation
	 * @param z Z Translation
	 * @return New Matrix
	 */
	public static Matrix4 createTranslation(float x, float y, float z) {
		return createTranslation(x, y, z, new Matrix4());
	}
	/**
	 * @see {@link #createTranslation(Vector3, Matrix4) Mat4.createTranslation(v, new Mat4())}
	 * @param v [{@link Vector3 POS}] Translation Vector
	 * @return New Matrix
	 */
	public static Matrix4 createTranslation(Vector3 v) {
		return createTranslation(v, new Matrix4());
	}

	/**
	 * Create A X-Axis Rotation Matrix Into Out
	 * <pre>
	 * | 1  0        0       0 |
	 * | 0  cos(t)  -sin(t)  0 |
	 * | 0  sin(t)   cos(t)  0 |
	 * | 0  0        0       1 |
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createRotationX(float t, Matrix4 out) {
		float cosT = (float)Math.cos(t), sinT = (float)Math.sin(t);
		return out.set(
				1, 0, 0, 0,
				0, cosT, -sinT, 0,
				0, sinT, cosT, 0,
				0, 0, 0, 1
				);
	}
	/**
	 * Create A Y-Axis Rotation Matrix Into Out
	 * <pre>
	 * |  cos(t)  0  sin(t)  0 |
	 * |  0       1  0       0 |
	 * | -sin(t)  0  cos(t)  0 |
	 * |  0       0  0       1 |
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createRotationY(float t, Matrix4 out) {
		float cosT = (float)Math.cos(t), sinT = (float)Math.sin(t);
		return out.set(
				cosT, 0, sinT, 0,
				0, 1, 0, 0,
				-sinT, 0, cosT, 0,
				0, 0, 0, 1
				);
	}
	/**
	 * Create A Z-Axis Rotation Matrix Into Out
	 * <pre>
	 * |  cos(t) -sin(t)  0  0 |
	 * |  sin(t)  cos(t)  0  0 |
	 * |  0       0       1  0 |
	 * |  0       0       0  1 |
	 * </pre>
	 * @param t Angle (Radians)
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createRotationZ(float t, Matrix4 out) {
		float cosT = (float)Math.cos(t), sinT = (float)Math.sin(t);
		return out.set(
				cosT, -sinT, 0, 0,
				sinT, cosT, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
				);
	}
	/**
	 * @see {@link #createRotationX(float, Matrix4) Mat4.createRotationX(t, new Mat4())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix4 createRotationX(float t) {
		return createRotationX(t, new Matrix4());
	}
	/**
	 * @see {@link #createRotationY(float, Matrix4) Mat4.createRotationY(t, new Mat4())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix4 createRotationY(float t) {
		return createRotationY(t, new Matrix4());
	}
	/**
	 * @see {@link #createRotationZ(float, Matrix4) Mat4.createRotationZ(t, new Mat4())}
	 * @param t Angle (Radians)
	 * @return New Matrix
	 */
	public static Matrix4 createRotationZ(float t) {
		return createRotationZ(t, new Matrix4());
	}

	/**
	 * Create A Perspective Matrix Into Out
	 * <pre>
	 * Let ys = cot(fov)
	 * Let xs = ys / aspect
	 * Let a = zfar / (znear - zfar)
	 * | xs  0   0      0     |
	 * | 0   ys  0      0     |
	 * | 0   0   a  znear * a |
	 * | 0   0  -1      0     |
	 * </pre>
	 * @param fov Field Of View Y Direction In Radians From The Center Plane (0,PI/2) <pre>
	 *        /
	 *       /_
	 *      /  \  <-  FOV 
	 * EYE /____|_____
	 * </pre>
	 * @param aspect Width / Height
	 * @param znear Near Clip Distance
	 * @param zfar Far Clip Distance
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createPerspectiveFOV(float fov, float aspect, float znear, float zfar, Matrix4 out) {
		float ys = (float)(1.0 / Math.tan(fov));
		float xs = ys / aspect;
		float a = zfar / (znear - zfar);
		return out.set(
				xs, 0, 0, 0,
				0, ys, 0, 0,
				0, 0, a, znear * a,
				0, 0, -1.f, 0
				);
	}
	/**
	 * @see {@link #createPerspectiveFOV(float, float, float, float, Matrix4) Mat4.createPerspectiveFOV(fov, aspect, znear, zfar, new Mat4())}
	 * @param fov Field Of View Y Direction In Radians From The Center Plane (0,PI/2) <pre>
	 *        /
	 *       /_
	 *      /  \  <-  FOV 
	 * EYE /____|_____
	 * </pre>
	 * @param aspect Width / Height
	 * @param znear Near Clip Distance
	 * @param zfar Far Clip Distance
	 * @return New Matrix
	 */
	public static Matrix4 createPerspectiveFOV(float fov, float aspect, float znear, float zfar) {
		return createPerspectiveFOV(fov, aspect, znear, zfar, new Matrix4());
	}
	/**
	 * Create A Perspective Matrix Into Out
	 * <pre>
	 * Let a = zfar / (znear - zfar)
	 * | 2 * znear / w  0              0      0     |
	 * | 0              2 * znear / h  0      0     |
	 * | 0              0              a  znear * a |
	 * | 0              0             -1      0     |
	 * </pre>
	 * @param w Width Of Near View (Image) Plane
	 * @param h Height Of Near View (Image) Plane
	 * @param znear Near Clip Distance
	 * @param zfar Far Clip Distance
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createPerspective(float w, float h, float znear, float zfar, Matrix4 out) {
		float a = zfar / (znear - zfar);
		return out.set(
				2.f * znear / w, 0, 0, 0,
				0, 2.f * znear / h, 0, 0,
				0, 0, a, znear * a,
				0, 0, -1.f, 0
				);
	}
	/**
	 * @see {@link #createPerspective(float, float, float, float, Matrix4) Mat4.createPerspective(w, h, znear, zfar, new Mat4())}
	 * @param w Width Of Near View (Image) Plane
	 * @param h Height Of Near View (Image) Plane
	 * @param znear Near Clip Distance
	 * @param zfar Far Clip Distance
	 * @return New Matrix
	 */
	public static Matrix4 createPerspective(float w, float h, float znear, float zfar) {
		return createPerspective(w, h, znear, zfar, new Matrix4());
	}
	/**
	 * Create An Orthographic Matrix Into Out
	 * <pre>
	 * Let a = 1 / (znear - zfar)
	 * | 2 / w  0      0      0     |
	 * | 0      2 / h  0      0     |
	 * | 0      0      a  znear * a |
	 * | 0      0      0      1     |
	 * </pre>
	 * @param w Width Of View Volume
	 * @param h Height Of View Volume
	 * @param znear Near Clip Distance
	 * @param zfar Far Clip Distance
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createOrthographic(float w, float h, float znear, float zfar, Matrix4 out) {
		float a = 1 / (znear - zfar);
		return out.set(
				2.f / w, 0, 0, 0,
				0, 2.f / h, 0, 0,
				0, 0, a, znear * a,
				0, 0, 0, 1
				);
	}
	/**
	 * @see {@link #createOrthographic(float, float, float, float, Matrix4) Mat4.createOrthographic(w, h, znear, zfar, new Mat4())}
	 * @param w Width Of Near View Volume
	 * @param h Height Of Near View Volume
	 * @param znear Near Clip Distance
	 * @param zfar Far Clip Distance
	 * @return New Matrix
	 */
	public static Matrix4 createOrthographic(float w, float h, float znear, float zfar) {
		return createOrthographic(w, h, znear, zfar, new Matrix4());
	}
	
	/**
	 * Create An Orthographic Matrix Into Out
	 * <pre>
	 * Let w = r - l
	 * Let h = t - b
	 * |  2 / w  0      0     -(l + r) / w |
	 * |  0      2 / h  0     -(b + t) / h |
	 * |  0      0      0     1            |
	 * </pre>
	 * @param l Left Side Of Rect
	 * @param r Right Side Of Rect
	 * @param b Bottom Side Of Rect
	 * @param t Top Side Of Rect
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createOrthographic2D(float l, float r, float b, float t, Matrix4 out) {
		float w = r - l, h = t - b;
		return out.set(
				2 / w, 0, 0, -(l + r) / w,
				0, 2 / h, 0, -(b + t) / h,
				0, 0, 1, 0, 
				0, 0, 0, 1);
	}
	
	/**
	 * @see {@link #createOrthographic(float, float, float, float, Matrix3) Mat3.createOrthographic(l, r, b, t, new Mat3())}
	 * @param l Left Side Of Rect
	 * @param r Right Side Of Rect
	 * @param b Bottom Side Of Rect
	 * @param t Top Side Of Rect
	 * @return New Matrix
	 */
	public static Matrix4 createOrthographic2D(float l, float r, float b, float t) {
		return createOrthographic2D(l, r, b, t, new Matrix4());
	}

	/**
	 * Create A View Matrix Into Out
	 * <pre>
	 * Let z = normalize(eye - target)
	 * Let x = normalize(cross(up, z))
	 * Let y = cross(z, x)
	 * |  x.x  y.x  z.x  -dot(x, eye) |
	 * |  x.y  y.y  z.y  -dot(y, eye) |
	 * |  x.z  y.z  z.z  -dot(z, eye) |
	 * |   0    0    0        1       |
	 * </pre>
	 * @param eye [{@link Vector3 POS}] Camera Origin
	 * @param target [{@link Vector3 POS}] Camera Viewing Target
	 * @param up [{@link Vector3 DIRN}] Camera Up Direction
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createLookAt(Vector3 eye, Vector3 target, Vector3 up, Matrix4 out) {
		Vector3 z = new Vector3(eye).sub(target).normalize();
		Vector3 x = new Vector3(up).cross(z).normalize();
		Vector3 y = new Vector3(z).cross(x);
		return out.set(
				x.x, x.y, x.z, -x.dot(eye),
				y.x, y.y, y.z, -y.dot(eye),
				z.x, z.y, z.z, -z.dot(eye),
				0, 0, 0, 1
				);
	}
	/**
	 * @see {@link #createLookAt(Vector3, Vector3, Vector3, Matrix4) Mat4.createLookAt(eye, target, up, new Mat4())}
	 * @param eye [{@link Vector3 POS}] Camera Origin
	 * @param target [{@link Vector3 POS}] Camera Viewing Target
	 * @param up [{@link Vector3 DIRN}] Camera Up Direction
	 * @return New Matrix
	 */
	public static Matrix4 createLookAt(Vector3 eye, Vector3 target, Vector3 up) {
		return createLookAt(eye, target, up, new Matrix4());
	}
	/**
	 * Create A View Matrix Into Out
	 * <pre>
	 * Let z = normalize(-viewDir)
	 * Let x = normalize(cross(up, z))
	 * Let y = cross(z, x)
	 * |  x.x  y.x  z.x  -dot(x, eye) |
	 * |  x.y  y.y  z.y  -dot(y, eye) |
	 * |  x.z  y.z  z.z  -dot(z, eye) |
	 * |   0    0    0        1       |
	 * </pre>
	 * @param eye [{@link Vector3 POS}] Camera Origin
	 * @param viewDir [{@link Vector3 DIRN}] Camera Viewing Direction
	 * @param up [{@link Vector3 DIRN}] Camera Up Direction
	 * @param out Non-Null Output Matrix
	 * @return Out
	 */
	public static Matrix4 createView(Vector3 eye, Vector3 viewDir, Vector3 up, Matrix4 out) {
		Vector3 z = new Vector3(viewDir).negate().normalize();
		Vector3 x = new Vector3(up).cross(z).normalize();
		Vector3 y = new Vector3(z).cross(x);
		return out.set(
				x.x, x.y, x.z, -x.dot(eye),
				y.x, y.y, y.z, -y.dot(eye),
				z.x, z.y, z.z, -z.dot(eye),
				0, 0, 0, 1
				);
	}
	/**
	 * @see {@link #createView(Vector3, Vector3, Vector3, Matrix4) Mat4.createView(eye, viewDir, up, new Mat4())}
	 * @param eye [{@link Vector3 POS}] Camera Origin
	 * @param viewDir [{@link Vector3 DIRN}] Camera Viewing Direction
	 * @param up [{@link Vector3 DIRN}] Camera Up Direction
	 * @return New Matrix
	 */
	public static Matrix4 createView(Vector3 eye, Vector3 viewDir, Vector3 up) {
		return createView(eye, viewDir, up, new Matrix4());
	}
}
