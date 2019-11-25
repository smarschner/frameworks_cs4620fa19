package mesh.gen;

import common.BasicType;
import mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Cylinder Mesh
 * @author Cristian (Original)
 * @author Jimmy (Revised 8/25/2015)
 */
public class MeshGenCylinder extends MeshGenerator {
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {

		// Calculate Vertex And Index Count
		outData.vertexCount = 4*(opt.divisionsLongitude) + 4;
		int tris = 4 * (opt.divisionsLongitude);
		outData.indexCount = tris * 3;

		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);
		
		// Create The Vertices
		// Y-Axis Vertices for Top and Bottom
		outData.positions.put(0); outData.positions.put(1); outData.positions.put(0);
		outData.normals.put(0); outData.normals.put(1); outData.normals.put(0);
		outData.uvs.put(.75f); outData.uvs.put(.75f);
		outData.positions.put(0); outData.positions.put(-1); outData.positions.put(0);
		outData.normals.put(0); outData.normals.put(-1); outData.normals.put(0);
		outData.uvs.put(.25f); outData.uvs.put(.75f);
		
		// Vertices Around Circumference
		for(int i = 0;i < opt.divisionsLongitude;i++) {
			// Calculate XZ-Plane Position
			float p = (float)i / (float)opt.divisionsLongitude;
			double theta = p * Math.PI * 2.0;
			float z = (float)-Math.cos(theta);
			float x = (float)-Math.sin(theta);
			
			// Middle Tube Top
			outData.positions.put(x); outData.positions.put(1); outData.positions.put(z);
			outData.normals.put(x); outData.normals.put(0); outData.normals.put(z);
			outData.uvs.put(p); outData.uvs.put(0.5f);
			
			// Middle Tube Bottom
			outData.positions.put(x); outData.positions.put(-1); outData.positions.put(z);
			outData.normals.put(x); outData.normals.put(0); outData.normals.put(z);
			outData.uvs.put(p); outData.uvs.put(0);

			// Top Cap
			outData.positions.put(x); outData.positions.put(1); outData.positions.put(z);
			outData.normals.put(0); outData.normals.put(1); outData.normals.put(0);
			outData.uvs.put((x + 1) * 0.25f + 0.5f); outData.uvs.put((1 - z) * 0.25f + 0.5f);

			// Bottom Cap
			outData.positions.put(x); outData.positions.put(-1); outData.positions.put(z);
			outData.normals.put(0); outData.normals.put(-1); outData.normals.put(0);
			outData.uvs.put((x + 1) * 0.25f); outData.uvs.put((z + 1) * 0.25f + 0.5f);
		}
		// Extra Vertices
		outData.positions.put(0); outData.positions.put(1); outData.positions.put(-1);
		outData.normals.put(0); outData.normals.put(0); outData.normals.put(-1);
		outData.uvs.put(1); outData.uvs.put(.5f);
		outData.positions.put(0); outData.positions.put(-1); outData.positions.put(-1);
		outData.normals.put(0); outData.normals.put(0); outData.normals.put(-1);
		outData.uvs.put(1); outData.uvs.put(0);
		
		// Create The Indices For The Tube
		
		for(int i = 0; i < opt.divisionsLongitude;i++) { // Skip first two (axial) vertices
			int si = 4*i + 2; // Skip first two (axial) vertices
			outData.indices.put(si);
			outData.indices.put(si + 1);
			outData.indices.put(si + 4);
			outData.indices.put(si + 4);
			outData.indices.put(si + 1);
			outData.indices.put(si + 5);
		}
			
		// Create The Indices For The Caps Except Final Trangles Which loop back
		int i;
		int si = 0;
		for(i = 0;i < opt.divisionsLongitude-1;i++) {
			si = 4*(i) + 2;
			// Top Triangle
			outData.indices.put(0);
			outData.indices.put(si+2);
			outData.indices.put(si+6);

			// Bottom Triangle
			outData.indices.put(1);
			outData.indices.put(si + 7);
			outData.indices.put(si + 3);
		}
		
		// Add Final Triangles for last two vertices
        // Assumes that we have more than 1 division of longitude
        outData.indices.put(0);
        outData.indices.put(si+6);
		outData.indices.put(4);
		outData.indices.put(1);
		outData.indices.put(5);
		outData.indices.put(si+7);
	}

	@Override
	public BasicType getType() {
		return BasicType.Cylinder;
	}
}
