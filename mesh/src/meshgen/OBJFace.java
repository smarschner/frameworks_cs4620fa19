package meshgen;

/**
 * Represents a single face of an OBJMesh
 * An OBJFace specifies a number of indices into the positions array to represent the positions of each vertex of the face. The
 * face may optionally contain an equal number of indices into uvs and normals to specify other data at each vertex.
 * By convention, vertices should be listed in counter-clockwise order.
 */
public class OBJFace {
	/**
	 * Lists a number of indices into the positions array.
	 * Must not be null, and must have at least 3 entries.
	 */
	public int[] positions;
	/**
	 * Lists a number of indices into the uvs array.
	 * Must either be null or have as many entries as this.positions.
	 */
	public int[] uvs;
	/**
	 * Lists a number of indices into the normals array.
	 * Must either be null or have as many entries as this.positions.
	 */
	public int[] normals;

	/**
	 * The starting index used in this.positions, this.uvs, and this.normals.
	 * Must either be 0 or 1.
	 */
	public static int indexBase = 0;

	/**
	 * Returns the number of vertices associated with this face.
	 */
	public int numVerts() {
		return positions.length;
	}

	/**
	 * Returns true if this face has texture coordinate information.
	 */
	public boolean hasUVs() {
		return (uvs != null);
	}

	/**
	 * Returns true if this face has normal information.
	 */
	public boolean hasNormals() {
		return (normals != null);
	}

	/**
	 * Sets the position, uv, and normal indices of one vertex of this face simultaneously.
	 * @warning hasUVs() and hasNormals() must both be true.
	 */
	public void setVertex(int vertexIndex, int positionIndex, int uvIndex, int normalIndex) {
		positions[vertexIndex] = positionIndex;
		uvs[vertexIndex] = uvIndex;
		normals[vertexIndex] = normalIndex;
	}

	/**
	 * Constructs an OBJFace with numVerts vertices.
	 * Instantiates this.positions to the correct size.
	 * If hasUVs is true, instantiates this.uvs to the correct size. Otherwise leaves it null.
	 * If hasNormals is true, instantiates this.normals to the correct size. Otherwise leaves it null.
	 */
	public OBJFace(int numVerts, boolean hasUVs, boolean hasNormals) {
		positions = new int[numVerts];
		if (hasUVs) {
			uvs = new int[numVerts];
		} else {
			uvs = null;
		}
		if (hasNormals) {
			normals = new int[numVerts];
		} else {
			normals = null;
		}
	}
}
