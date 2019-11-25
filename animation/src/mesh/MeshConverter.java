package mesh;

import java.util.ArrayList;

import egl.NativeMem;
import egl.math.Vector3;
import egl.math.Vector3i;

/**
 * Performs Normals Reconstruction Upon A Mesh Of Positions
 * @author Cristian
 *
 */
public class MeshConverter {
	/**
	 * Reconstruct A Mesh's Normals So That It Appears To Have Sharp Creases
	 * @param positions List Of Positions
	 * @param tris List Of Triangles (A Group Of 3 Values That Index Into The Positions List)
	 * @return A Mesh With Normals That Lie Normal To Faces
	 */
	public static MeshData convertToFaceNormals(ArrayList<Vector3> positions, ArrayList<Vector3i> tris) {
		MeshData data = new MeshData();

		// Notice
		System.out.println("This Feature Has Been Removed For The Sake Of Assignment Consistency");
		System.out.println("This Feature Will Be Added In A Later Assignment");
				
		// Allocate Mesh Data
		data.vertexCount = tris.size() * 3;
		data.indexCount = tris.size() * 3;
		data.positions = NativeMem.createFloatBuffer(data.vertexCount * 3);
		data.normals = NativeMem.createFloatBuffer(data.vertexCount * 3);
		data.indices = NativeMem.createIntBuffer(data.indexCount);
		
		// Loop Through Triangles
		int vertIndex = 0;
		for(Vector3i t : tris) {
			// Compute The Normal
			Vector3 n = new Vector3(positions.get(t.z));
			n.sub(positions.get(t.y));
			n.cross(positions.get(t.x).clone().sub(positions.get(t.y)));
			n.normalize();
			
			// Check For Degenerate Triangle
			if(Float.isNaN(n.x) || Float.isNaN(n.y) || Float.isNaN(n.z)) {
				data.vertexCount -= 3;
				data.indexCount -= 3;
				continue;
			}
			
			// Add A Vertex
			for(int vi = 0;vi < 3;vi++) {
				Vector3 v = positions.get(t.get(vi));
				data.positions.put(v.x); data.positions.put(v.y); data.positions.put(v.z);
				data.normals.put(n.x); data.normals.put(n.y); data.normals.put(n.z);
				data.indices.put(vertIndex++);
			}
		}
		
		return data;
	}
}
