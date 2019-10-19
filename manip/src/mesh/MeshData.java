package mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import egl.math.Vector2;
import egl.math.Vector3;
import egl.math.Vector3i;

public class MeshData {
	/**
	 * Vector3 Components Representing Vertex Positions (Mandatory)
	 */
	public FloatBuffer positions;
	/**
	 * Normalized Vector3 Components Representing Vertex Normals (Optional)
	 */
	public FloatBuffer normals;
	/**
	 * Vector2 Components With Values In The Domain [0, 1]
	 */
	public FloatBuffer uvs;
	/**
	 * Integer Indices In The Domain [0, Vertex Count)
	 */
	public IntBuffer indices;

	/**
	 * Number Of Vertices Used In The Mesh
	 */
	public int vertexCount;
	/**
	 * Number Of Indices Used In The Mesh (Should Be A Multiple Of 3)
	 */
	public int indexCount;

	public MeshData() {
		positions = null;
		normals = null;
		uvs = null;
		indexCount = vertexCount = 0;
	}

	public boolean loadOBJ(String fName) {
		OBJMesh om = OBJParser.parse(fName);
		if(om == null || !om.hasData()) return false;
		if(om.hasUVs()) {
			if(!om.hasNormals()) {
				System.err.println("Input Mesh Must Contain Normals If UVs are specified");
				return false;
			}

			// Mesh Description
			indexCount = om.triangles.size() * 3;
			vertexCount = om.vertices.size();
			positions = BufferUtils.createFloatBuffer(vertexCount * 3);
			normals = BufferUtils.createFloatBuffer(vertexCount * 3);
			uvs = BufferUtils.createFloatBuffer(vertexCount * 2);
			indices = BufferUtils.createIntBuffer(indexCount);

			// Flatten Data
			for(Vector3i vert : om.vertices) {
				Vector3 v = om.positions.get(vert.x);
				positions.put(v.x);
				positions.put(v.y);
				positions.put(v.z);
				v = om.normals.get(vert.z);
				normals.put(v.x);
				normals.put(v.y);
				normals.put(v.z);
				Vector2 v2 = om.uvs.get(vert.y);
				uvs.put(v2.x);
				uvs.put(v2.y);
			}
			for(Vector3i t : om.triangles) {
				indices.put(t.x);
				indices.put(t.y);
				indices.put(t.z);
			}
		}
		else {
			// Mesh Description
			indexCount = om.triangles.size() * 3;
			vertexCount = om.vertices.size();
			positions = BufferUtils.createFloatBuffer(vertexCount * 3);
			indices = BufferUtils.createIntBuffer(indexCount);

			// Flatten Data
			for(Vector3i vert : om.vertices) {
				Vector3 v = om.positions.get(vert.x);
				positions.put(v.x);
				positions.put(v.y);
				positions.put(v.z);
			}
			for(Vector3i t : om.triangles) {
				indices.put(t.x);
				indices.put(t.y);
				indices.put(t.z);
			}
		}

		return true;

	}

	/**
	 * Checks For Position And Triangulation Information
	 * @return True If This Contains The Mandatory Data For Visualizing A Mesh
	 */
	public boolean hasData() {
		return vertexCount >= 3 && indexCount >= 3 && 
				positions != null && indices != null &&
				positions.capacity() >= (vertexCount * 3) && indices.capacity() >= indexCount;
	}
	/**
	 * Checks For Normals
	 * @return True If This Contains Normals
	 */
	public boolean hasNormals() {
		return normals != null && normals.capacity() >= (vertexCount * 3);
	}
	/**
	 * Checks For Texture Coordinates
	 * @return True If This Contains Texture Coordinates
	 */
	public boolean hasUVs() {
		return uvs != null && uvs.capacity() >= (vertexCount * 2);
	}
}
