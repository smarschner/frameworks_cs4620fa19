package mesh.gen;

import common.BasicType;
import mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Sphere Mesh
 * @author Cristian
 *
 */
public class MeshGenSphere extends MeshGenerator {
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// TODO#A1 SOLUTION START

		// Calculate Vertex And Index Count
		int vertsPerSlice = opt.divisionsLatitude + 1;
		outData.vertexCount = (opt.divisionsLongitude + 1) * vertsPerSlice;
		int tris = opt.divisionsLongitude * opt.divisionsLatitude * 2;
		outData.indexCount = tris * 3;

		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);
		
		// Create The Vertices
		for(int i = 0;i <= opt.divisionsLongitude;i++) {
			// Calculate XZ-Plane Position For The Slice
			float pTheta = (float)i / (float)opt.divisionsLongitude;
			double theta = pTheta * Math.PI * 2.0;
			float tz = (float)-Math.cos(theta);
			float tx = (float)-Math.sin(theta);
			
			// Traverse Up The Slice
			for(int pi = 0;pi < vertsPerSlice;pi++) {
				float pPhi = (float)pi / (float)opt.divisionsLatitude;
				double phi = (1 - pPhi) * Math.PI;
				
				// Calculate The Actual Position
				float y = (float)Math.cos(phi);
				float x = tx * (float)Math.sin(phi);
				float z = tz * (float)Math.sin(phi);
				
				outData.positions.put(x); outData.positions.put(y); outData.positions.put(z);
				outData.normals.put(x); outData.normals.put(y); outData.normals.put(z);
				outData.uvs.put(pTheta); outData.uvs.put(pPhi);
			}
		}
		
		// Create The Indices
		for(int i = 0;i < opt.divisionsLongitude;i++) {
			int si = i * vertsPerSlice;
			for(int pi = 0;pi < opt.divisionsLatitude;pi++) {
				outData.indices.put(si);
				outData.indices.put(si + vertsPerSlice);
				outData.indices.put(si + 1);
				outData.indices.put(si + 1);
				outData.indices.put(si + vertsPerSlice);
				outData.indices.put(si + vertsPerSlice + 1);
				si++;
			}
		}
		
		// #SOLUTION END
	}
	
	@Override
	public BasicType getType() {
		return BasicType.Sphere;
	}
}
