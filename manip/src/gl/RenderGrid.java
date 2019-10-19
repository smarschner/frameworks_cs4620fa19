package gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import camera.Camera;
import egl.math.Matrix3;
import egl.math.Matrix4;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import mesh.MeshData;

/**
 * Mesh data. Contains openGL calls to draw this mesh.
 */
public class RenderGrid {

	private int vaoId;
	private int vboId;
	private int idxVboId;


	private final int vertexCount;
	private final int indexCount;
	private FloatBuffer verticesBuffer;
	private IntBuffer indicesBuffer;


	public RenderGrid(int res) {
		vertexCount = (res+1) * (res+1);
		indexCount = res * (res + 1) * 4;
		System.out.print(indexCount);
		verticesBuffer = BufferUtils.createFloatBuffer(vertexCount * 3);
		indicesBuffer = BufferUtils.createIntBuffer(indexCount);
				
        // generate a grid with input res  size range (-25 to 25)
		float step = 50.f / (float) res;
		float start = -25.f;
		for (int i = 0 ; i <= res ; i++) {
			float x = start + i * step;
			for (int j = 0 ; j <= res ; j++) {
				float z = start + j * step;
				verticesBuffer.put(x);
				verticesBuffer.put(0);
				verticesBuffer.put(z);
			}
		}

        // horizontal lines
		for (int i = 0 ; i <= res ; i++) {
			for(int j = 0 ; j < res ; j++ ){
                indicesBuffer.put(i * (res + 1) + j);
                indicesBuffer.put(i * (res + 1) + j + 1);
            }
        }

        // vertical lines
        for (int i = 0 ; i < res ; i++) {
			for(int j = 0 ; j <= res ; j++ ) {
                indicesBuffer.put(i * (res+1) + j);
                indicesBuffer.put((i + 1) * (res + 1) + j);
            }
        }
	}

	public void init () throws Exception {
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		verticesBuffer.flip();
		vboId = glGenBuffers();
		setVertexAttribV3f(vboId, verticesBuffer, 0);
		indicesBuffer.flip();
		idxVboId = glGenBuffers();
		bindElementArray(idxVboId, indicesBuffer);
		//attribute normal
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

	public void render () throws Exception {
		glBindVertexArray(getVaoId());
		glEnableVertexAttribArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
		glDrawElements(GL_LINES, indexCount, GL_UNSIGNED_INT, 0);
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
		glDeleteBuffers(idxVboId);

		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}
}