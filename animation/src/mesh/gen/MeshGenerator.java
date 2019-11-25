package mesh.gen;

import common.BasicType;
import mesh.MeshData;


public abstract class MeshGenerator {
	/**
	 * Generate Mesh Information Into A Data Buffer
	 * @param outData Array Into Which Data Will Be Sent
	 * @param opt {@link MeshGenOptions}
	 */
	public abstract void generate(MeshData outData, MeshGenOptions opt);
	
	/**
	 * What Kind Of Shape Is Generated
	 * @return A Shape Enum
	 */
	public abstract BasicType getType();
}
