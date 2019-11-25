package mesh;

import java.util.ArrayList;

import egl.NativeMem;
import egl.math.Vector2;
import egl.math.Vector3;
import egl.math.Vector3i;

/**
 * Mesh Data Represented In An OBJ Format
 * 
 * Note That "Unique" Is Used With Quotation Marks Because It Is
 * Preferred That All Data Found Within The Lists Is Unique
 * @author Cristian
 *
 */
public class OBJMesh_Archive {
	/**
	 * List Of "Unique" Positions
	 */
	public final ArrayList<Vector3> positions;
	/**
	 * List Of "Unique" Texture Coordinates
	 */
	public final ArrayList<Vector2> uvs;
	/**
	 * List Of "Unique" Normals
	 */
	public final ArrayList<Vector3> normals;
	
	/**
	 * List Of "Unique" Vertices
	 * X Component Indexes Into Positions List
	 * Y Component Indexes Into Texture Coordinates List
	 * Z Component Indexes Into Normals List
	 */
	public final ArrayList<Vector3i> vertices;
	/**
	 * List Of "Unique" Vertices
	 * Each Component Indexes Into Vertex List With Triangle Orientation Being X->Y->Z
	 */
	public final ArrayList<Vector3i> triangles;
	
	public OBJMesh_Archive() {
		positions = new ArrayList<>();
		uvs = new ArrayList<>();
		normals = new ArrayList<>();
		vertices = new ArrayList<>();
		triangles = new ArrayList<>();
	}

	/**
	 * Checks For Position And Triangulation Information
	 * @return True If This Contains The Mandatory Data For Visualizing A Mesh
	 */
	public boolean hasData() {
		return positions.size() >= 3 && triangles.size() > 0 && vertices.size() >= 3;
	}
	/**
	 * Checks For Texture Coordinates
	 * @return True If This Contains Texture Coordinates
	 */
	public boolean hasUVs() {
		return uvs.size() > 0;
	}
	/**
	 * Checks For Normals
	 * @return True If This Contains Normals
	 */
	public boolean hasNormals() {
		return normals.size() > 0;
	}
	
	public MeshData flatten() {
		if (!hasData()) return null;
		
		MeshData meshdata = new MeshData();
		meshdata.vertexCount = vertices.size();
		meshdata.indexCount = triangles.size() * 3;
		
		meshdata.positions = NativeMem.createFloatBuffer(meshdata.vertexCount * 3);
		for (Vector3i vert : vertices) {
			meshdata.positions.put(positions.get(vert.x).x);
			meshdata.positions.put(positions.get(vert.x).y);
			meshdata.positions.put(positions.get(vert.x).z);
		}
		
		meshdata.indices = NativeMem.createIntBuffer(meshdata.indexCount);
		for (Vector3i tri : triangles) {
			meshdata.indices.put(tri.x);
			meshdata.indices.put(tri.y);
			meshdata.indices.put(tri.z);
		}
		
		if (hasUVs()) {
			meshdata.uvs = NativeMem.createFloatBuffer(meshdata.vertexCount * 2);
			for (Vector3i vert : vertices) {
				meshdata.uvs.put(uvs.get(vert.y).x);
				meshdata.uvs.put(uvs.get(vert.y).y);
			}
		}
		
		if (hasNormals()) {
			meshdata.normals = NativeMem.createFloatBuffer(meshdata.vertexCount * 3);
			for (Vector3i vert : vertices) {
				meshdata.normals.put(normals.get(vert.z).x);
				meshdata.normals.put(normals.get(vert.z).y);
				meshdata.normals.put(normals.get(vert.z).z);
			}
		}
		
		return meshdata;
	}

}
