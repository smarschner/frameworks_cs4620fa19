package mesh.gen;

import common.BasicType;
import mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Cube Mesh
 * @author Cristian
 *
 */
public class MeshGenCube extends MeshGenerator {
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// Calculate Vertex And Index Count
		outData.vertexCount = 6 * 4;
		outData.indexCount = 6 * 2 * 3;

		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);
		
		// Add Positions For 6 Faces
		outData.positions.put(new float[]{
				// Face XN
				-1, 1, -1,
				-1, 1, 1,
				-1, -1, -1,
				-1, -1, 1,

				// Face XP
				1, 1, 1,
				1, 1, -1,
				1, -1, 1,
				1, -1, -1,

				// Face YN
				-1, -1, 1,
				1, -1, 1,
				-1, -1, -1,
				1, -1, -1,

				// Face YP
				-1, 1, -1,
				1, 1, -1,
				-1, 1, 1,
				1, 1, 1,

				// Face ZN
				1, 1, -1,
				-1, 1, -1,
				1, -1, -1,
				-1, -1, -1,

				// Face ZP
				-1, 1, 1,
				1, 1, 1,
				-1, -1, 1,
				1, -1, 1
		});
		
		// Add Normals For 6 Faces
		for(int i = 0;i < 4;i++) { outData.normals.put(-1); outData.normals.put(0); outData.normals.put(0); }
		for(int i = 0;i < 4;i++) { outData.normals.put(1); outData.normals.put(0); outData.normals.put(0); }
		for(int i = 0;i < 4;i++) { outData.normals.put(0); outData.normals.put(-1); outData.normals.put(0); }
		for(int i = 0;i < 4;i++) { outData.normals.put(0); outData.normals.put(1); outData.normals.put(0); }
		for(int i = 0;i < 4;i++) { outData.normals.put(0); outData.normals.put(0); outData.normals.put(-1); }
		for(int i = 0;i < 4;i++) { outData.normals.put(0); outData.normals.put(0); outData.normals.put(1); }
		
		// Add UV Coordinates
		float[] uvs = { 0, 0, 1, 0, 0, 1, 1, 1 };
		for(int i = 0; i < 6; i++) outData.uvs.put(uvs);

		// Add Indices
		for(int f = 0;f < 6;f++) {
			f <<= 2;
			outData.indices.put(f);
			outData.indices.put(f + 2);
			outData.indices.put(f + 1);
			outData.indices.put(f + 1);
			outData.indices.put(f + 2);
			outData.indices.put(f + 3);
			f >>= 2;
		}
	}

	@Override
	public BasicType getType() {
		return BasicType.Cube;
	}
}
