package egl;

import java.nio.ByteBuffer;

/**
 * Common Interleaved Vertex Functionality
 * @author Cristian
 *
 */
public interface IVertexType {
	/**
	 * Vertex Size Description
	 * @return Vertex Stride/Size In Bytes
	 */
	int getByteSize();
	/**
	 * Put This Vertex's Data Into A Buffer
	 * @param bb Memory Buffer
	 */
	void appendToBuffer(ByteBuffer bb);
	/**
	 * Get This Vertex's Declaration
	 * @return Vertex Attributes Semantics
	 */
	ArrayBind[] getDeclaration();
}
