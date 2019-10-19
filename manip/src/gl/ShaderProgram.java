package gl;
import static org.lwjgl.opengl.GL20.*;

import java.util.*;

import egl.math.*;

/**
 * The java binding of a glsl shader program
 */
public class ShaderProgram {

	private final int programId;

	private int vertexShaderId;

	private int fragmentShaderId;

	private Map<String, Integer> uniforms = new HashMap<>();

	private List<String> uniformNames = new ArrayList<>();

	public ShaderProgram() throws Exception {
		programId = glCreateProgram();
		if (programId == 0) {
			throw new Exception("Could not create Shader");
		}
	}

	public ShaderProgram(List<String> uniformNames) throws Exception {
		this();
		this.uniformNames.addAll(uniformNames);
	}

	public void createVertexShader(String shaderCode) throws Exception {
		vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
	}

	public void createFragmentShader(String shaderCode) throws Exception {
		fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
	}

	protected int createShader(String shaderCode, int shaderType) throws Exception {
		int shaderId = glCreateShader(shaderType);
		if (shaderId == 0) {
			throw new Exception("Error creating shader. Type: " + shaderType);
		}

		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);

		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
		}

		glAttachShader(programId, shaderId);

		return shaderId;
	}

	public void link() throws Exception {
		glBindAttribLocation(programId, 0, "position");
		glBindAttribLocation(programId, 1, "normal");

		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
		}

		if (vertexShaderId != 0) {
			glDetachShader(programId, vertexShaderId);
		}
		if (fragmentShaderId != 0) {
			glDetachShader(programId, fragmentShaderId);
		}
	}

	public void init() throws Exception {
		for (String uniformName : uniformNames) {
			int uniformLocation = glGetUniformLocation(programId, uniformName);
		    if (uniformLocation < 0) {
		        throw new Exception("Could not find uniform:"+ uniformName);
		    }
		    uniforms.put(uniformName, uniformLocation);
		}
	}

	public void setUniform(String uniformName, Matrix4 value) throws Exception {
		if (!uniforms.containsKey(uniformName)) {
			throw new Exception("Uniform name not found: " + uniformName);
		}
		glUniformMatrix4fv(uniforms.get(uniformName), false, value.m);
	}

	public void setUniform(String uniformName, Matrix3 value) throws Exception {
		if (!uniforms.containsKey(uniformName)) {
			throw new Exception("Uniform name not found: " + uniformName);
		}
		glUniformMatrix3fv(uniforms.get(uniformName), false, value.m);
	}

	public void setUniform(String uniformName, Vector3 value) throws Exception {
		if (!uniforms.containsKey(uniformName)) {
			throw new Exception("Uniform name not found: " + uniformName);
		}
		glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
	}

	public void setUniform(String uniformName, float value) throws Exception {
		if (!uniforms.containsKey(uniformName)) {
			throw new Exception("Uniform name not found: " + uniformName);
		}
		glUniform1f(uniforms.get(uniformName), value);
	}

	public void bind() {
		glUseProgram(programId);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public void cleanup() {
		unbind();
		if (programId != 0) {
			glDeleteProgram(programId);
		}
	}
}
