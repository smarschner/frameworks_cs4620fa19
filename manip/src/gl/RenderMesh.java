package gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import mesh.MeshData;

/**
 * Mesh data. Contains openGL calls to draw this mesh.
 */
public class RenderMesh {

	private int vaoId;

	private int vboId;
	private int idxVboId;
	private int normalVboId;

	private final MeshData mesh;
	private final int vertexCount;
	private final int indexCount;

	private final FloatBuffer verticesBuffer;
	private final IntBuffer indicesBuffer;
	private final FloatBuffer normalBuffer;

	public RenderMesh(MeshData data) {
		this.mesh = data;
		verticesBuffer = data.positions;
		indicesBuffer = data.indices;
		vertexCount = mesh.vertexCount;
		indexCount = mesh.indexCount;
		if (data.hasNormals()) {
			normalBuffer = data.normals;	
		} else {
			//degenerate normals
			normalBuffer = MemoryUtil.memAllocFloat(data.vertexCount*3);
			for(int i = 0; i < data.vertexCount * 3; i++) {
				normalBuffer.put(0);
			}
		}
	}

	public void init () {
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		verticesBuffer.flip();
		vboId = glGenBuffers();
		setVertexAttribV3f(vboId, verticesBuffer, 0);
		indicesBuffer.flip();
		idxVboId = glGenBuffers();
		bindElementArray(idxVboId, indicesBuffer);
		//attribute normal
		normalBuffer.flip();
		normalVboId = glGenBuffers();
		setVertexAttribV3f(normalVboId, normalBuffer, 1);
		glBindVertexArray(0);         
	}

	/**
	 * Set vertex attribute of vec3
	 */
	private void setVertexAttribV3f(int bufferObjectId, FloatBuffer buffer, int attribIndex) {
		glBindBuffer(GL_ARRAY_BUFFER, bufferObjectId);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glEnableVertexAttribArray(bufferObjectId);
		glVertexAttribPointer(attribIndex, 3, GL_FLOAT, false, 0, 0);
		glDisableVertexAttribArray(bufferObjectId);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private void bindElementArray(int bufferObjectId, IntBuffer buffer) {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferObjectId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public int getVaoId() {
		return vaoId;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public int getIndicesCount() {
		return indexCount;
	}

	public void render () {
		glBindVertexArray(getVaoId());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
		glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		// Restore state
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}

	public void cleanUp() {
		glDisableVertexAttribArray(0);

		// Delete the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboId);
		if (mesh.hasNormals()) {
			glDeleteBuffers(normalVboId);
		}
		glDeleteBuffers(idxVboId);

		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}
}