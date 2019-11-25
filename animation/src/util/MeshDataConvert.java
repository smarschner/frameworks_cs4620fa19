package util;

import mesh.OBJMesh;
import java.util.ArrayList;
import egl.math.Vector2;
import egl.math.Vector3;
import egl.NativeMem;
import mesh.MeshData;
import mesh.OBJFace;

public class MeshDataConvert {
	public static void convertToMeshData(MeshData outData, OBJMesh mesh) {
		
		// List of vertex positions
		ArrayList<Vector3> positions = mesh.positions;
		// List of vertex texture coordinates
		ArrayList<Vector2> uvs = mesh.uvs;
		// List of vertex normals
		ArrayList<Vector3> normals = mesh.normals;
		// List of faces that comprise the mesh
		ArrayList<OBJFace> faces = mesh.faces;
		
		// Initialize buffers and counts
		outData.vertexCount = positions.size();
		outData.indexCount = faces.size() * 3;
		
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);
		
		
		// Vertices
		for (int i = 0; i < positions.size(); i++) {
			Vector3 position = positions.get(i);
			outData.positions.put(position.x);
			outData.positions.put(position.y);
			outData.positions.put(position.z);
		}
		
		// Normals
		for (int i = 0; i < normals.size(); i++) {
			Vector3 normal = normals.get(i);
			outData.normals.put(normal.x);
			outData.normals.put(normal.y);
			outData.normals.put(normal.z);
		}
		
		// UVs
		for (int i = 0; i < uvs.size(); i++) {
			Vector2 UV = uvs.get(i);
			outData.uvs.put(UV.x);
			outData.uvs.put(UV.y);
		}
		
		// Indices
		for (int i = 0; i < faces.size(); i++) {
			OBJFace face = faces.get(i);
			outData.indices.put(face.positions);
		}
		
	}
}
