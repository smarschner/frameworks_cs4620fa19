package egl;

import java.util.HashMap;

/**
 * Interface For Communicating Semantic Information Between Programs And Vertex Buffer
 * @author Cristian
 *
 */
public class ShaderInterface {
	/**
	 * Copy Of Vertex Semantic Information Filled With Program Attribute Locations
	 */
	public final ArrayBind[] binds;

	/**
	 * Construct An Interface With A Vertex Declaration That Will Be Copied
	 * @param _binds Vertex Attribute Semantics
	 */
	public ShaderInterface(ArrayBind[] _binds) {
		binds = new ArrayBind[_binds.length];
		for(int i = 0;i < binds.length;i++) {
			binds[i] = new ArrayBind(
					_binds[i].semantic,
					_binds[i].compType,
					_binds[i].compCount,
					_binds[i].offset,
					_binds[i].isNormalized
					);
		}
	}

	/**
	 * Link The Interface To A Attribute Semantic Map
	 * @param dSemBinds Map Of Semantics To Attribute Locations
	 * @return Number Of Semantics Properly Linked
	 */
	public int build(HashMap<Integer, Integer> dSemBinds) {
		int bound = 0;
		for(int i = 0; i < binds.length; i++) {
			Integer v = dSemBinds.get(binds[i].semantic);
			if(v != null) {
				binds[i].location = v;
				bound++;
			}
			else {
				binds[i].location = GL.BadAttributeLocation;        		
			}
		}
		return bound;
	}
	/**
	 * Link The Interface To A Program
	 * @param program Program
	 * @return Number Of Semantics Properly Linked
	 */
	public int build(GLProgram program) {
		return build(program.semanticLinks);
	}
}
