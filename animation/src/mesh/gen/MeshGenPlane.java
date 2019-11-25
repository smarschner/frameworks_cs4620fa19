package mesh.gen;

import common.BasicType;
import mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Plane Mesh
 * @author Balazs
 *
 */
public class MeshGenPlane extends MeshGenerator {
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// Calculate Vertex And Index Count
		outData.vertexCount = (opt.divisionsLongitude + 1) * (opt.divisionsLatitude + 1);
		int tris = opt.divisionsLongitude * opt.divisionsLatitude * 2;
		outData.indexCount = tris * 3;

		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);
		
		// Set vertex positions
		for (int i = 0; i <= opt.divisionsLongitude; i++) {
			for (int j = 0; j <= opt.divisionsLatitude; j++) {
				float u = (float)i/opt.divisionsLongitude;
				float v = (float)j/opt.divisionsLatitude;
				// From -1 to 1
				float x = u*2 + (-1);
				float y = 0;
				// From -1 to 1
				float z = v*2 + (-1);
				
				outData.positions.put(x); outData.positions.put(y); outData.positions.put(z);
				// All normals [0, 1, 0]
				outData.normals.put(0); outData.normals.put(1); outData.normals.put(0);
				outData.uvs.put(u); outData.uvs.put(v);
			}
		}
		
		int vertsPerSlice = opt.divisionsLatitude + 1;
		// Set triangle indices
		for (int i = 0; i < opt.divisionsLongitude; i++) {
			int si = i * vertsPerSlice;
			for (int j = 0; j < opt.divisionsLatitude; j++) {
				outData.indices.put(si);
				outData.indices.put(si + 1);
				outData.indices.put(si + vertsPerSlice);
				
				outData.indices.put(si + vertsPerSlice);
				outData.indices.put(si + 1);
				outData.indices.put(si + vertsPerSlice + 1);
				si++;
			}
		}
	}

	@Override
	public BasicType getType() {
		return BasicType.Cube;
	}
}
