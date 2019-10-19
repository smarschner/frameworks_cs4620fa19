package gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.BufferUtils;

import camera.Camera;
import egl.math.Vector2i;

/**
 * Uses a shader to draw the current pixel under the mouse cursor and
 * find the currently picked object
 */
public class PickingProgram {
	private final ShaderProgram program;
	private final ByteBuffer idBuffer = BufferUtils.createByteBuffer(4);

	private static final String VERTEX_SHADER_SRC = "#version 150\n"
			+ "uniform mat4 worldMat;\n"
			+ "uniform mat4 viewProjMat;\n"
			+ "in vec3 vPos;\n"
			+ "void main() {\n"
			+ "  gl_Position = viewProjMat * worldMat * vec4(vPos, 1.0);\n"
			+ "}";
	private static final String FRAG_SHADER_SRC = "#version 150\n"
			+ "uniform float id;\n"
			+ "out vec4 color;\n"
			+ "void main() {\n"
			+ "  color = vec4(id, 0.0, 0.0, 1.0);\n"
			+ "}";

	private static final String WORLD_MAT_NAME = "worldMat";
	private static final String VIEWPROJ_MAT_NAME = "viewProjMat";
	private static final String ID_UNIFORM_NAME = "id";

	private int fbo;
	private int renderTexture;
	private int depthTexture;
	private int texWidth;
	private int texHeight;
	private float pixelRatio;

	public PickingProgram(int windowWidth, int windowHeight, float pixelRatio) throws Exception {
		List<String> uniformNames = Arrays.asList(WORLD_MAT_NAME, VIEWPROJ_MAT_NAME, ID_UNIFORM_NAME);
		program = new ShaderProgram(uniformNames);
		program.createVertexShader(VERTEX_SHADER_SRC);
		program.createFragmentShader(FRAG_SHADER_SRC);
		program.link();
		program.init();

		this.pixelRatio = pixelRatio;
		texWidth = (int)(windowWidth * pixelRatio);
		texHeight = (int)(windowHeight * pixelRatio);

		fbo = glGenFramebuffers();
		renderTexture = glGenTextures();
		depthTexture = glGenTextures();

		glBindFramebuffer(GL_FRAMEBUFFER, fbo);

		resizeTextures();

		int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
		if (status != GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("FBO incomplete: " + status);
			System.exit(-1);
		}

		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public Renderable getRenderableUnderCursor(List<? extends Renderable> objects, Camera camera, Vector2i cursorPosition,
			int windowWidth, int windowHeight) {
		// Use off-screen buffer
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		
		if (texWidth != (int)(windowWidth * pixelRatio) || texHeight != (int)(windowHeight * pixelRatio)) {
			texWidth = (int)(windowWidth * pixelRatio);
			texHeight = (int)(windowHeight * pixelRatio);
			resizeTextures();
		}

		glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glEnable(GL_DEPTH_TEST);

		program.bind();
		// Set camera
		try {
			program.setUniform(VIEWPROJ_MAT_NAME, camera.getViewProjectionMatrix());
		} catch(Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		// Draw manipulators
		int id = 0;
		for (Renderable ro : objects) {
			try {
				program.setUniform(WORLD_MAT_NAME, ro.getWorldTransform());
				program.setUniform(ID_UNIFORM_NAME, id / 255.0f);
			} catch(Exception e) {
				System.err.println(e.getMessage());
				System.exit(-1);
			}
			ro.getMesh().render();
			id++;
		}
		program.unbind();

		// Get color under cursor
		idBuffer.clear();
		glReadBuffer(GL_COLOR_ATTACHMENT0);
		System.out.print("gl_color_attachment0" + GL_COLOR_ATTACHMENT0);
		System.out.print("\n x: " + ((int)(pixelRatio * cursorPosition.x)));
		System.out.print("\n y: " + (texHeight - 1 - (int)(pixelRatio * cursorPosition.y)));
		glReadPixels((int)(pixelRatio * cursorPosition.x), texHeight - 1 - (int)(pixelRatio * cursorPosition.y),
				1, 1, GL_RGBA, GL_UNSIGNED_BYTE, idBuffer);
		Renderable result = null;
		System.out.print("\nidBuffer0: " + (idBuffer.get(0)));
		System.out.print("\nidBuffer1: " + (idBuffer.get(1)));
		if (idBuffer.get(1) == 0) {
			int objectID = idBuffer.get(0);
			result = objects.get(objectID);
		}

		// Cleanup
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		return result;
	}

	public void cleanup() {
		program.cleanup();
		glDeleteFramebuffers(fbo);
		glDeleteTextures(renderTexture);
		glDeleteTextures(depthTexture);
	}

	// Change the texture size on the GPU
	// Bind the FBO before calling this!
	private void resizeTextures() {
		glBindTexture(GL_TEXTURE_2D, renderTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texWidth, texHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer)null);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, renderTexture, 0);

		glBindTexture(GL_TEXTURE_2D, depthTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, texWidth, texHeight, 0, GL_DEPTH_COMPONENT, GL_UNSIGNED_BYTE, (ByteBuffer)null);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthTexture, 0);
	}
}
