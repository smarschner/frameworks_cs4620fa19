package mesh.gen;

import common.BasicType;
import mesh.MeshData;
import egl.NativeMem;
import egl.math.Matrix4;
import egl.math.Vector3;

/**
 * Generates A Torus Mesh
 * @author Cristian (Original)
 * @author Tongcheng (Revised 8/26/2015)
 */
public class MeshGenTorus extends MeshGenerator {
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// TODO#A1 SOLUTION START
		
		// #SOLUTION END
	}
	
	@Override
	public BasicType getType() {
		return BasicType.TriangleMesh; // Ray-casting Slightly More Difficult On A Torus 
	}
}
