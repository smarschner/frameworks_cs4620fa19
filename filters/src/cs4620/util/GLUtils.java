package cs4620.util;

import static org.lwjgl.opengl.GL33.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;

/**
 * A container for static methods that encapsulate simple and common OpenGL
 * operations.
 * 
 * @author srm
 * @author Pramook Khungurn via CS5625 frameworks
 */
public class GLUtils {

    /**
     * Provide a user-readable name for an OpenGL error code.
     * 
     * @param err The error code
     * @return The name of the error
     */
    public static String glErrorString(int err) {
        switch (err) {
            case GL_NO_ERROR:
                return "GL_NO_ERROR";
            case GL_INVALID_ENUM:
                return "GL_INVALID_ENUM";
            case GL_INVALID_VALUE:
                return "GL_INVALID_VALUE";
            case GL_INVALID_OPERATION:
                return "GL_INVALID_OPERATION";
            case GL_INVALID_FRAMEBUFFER_OPERATION:
                return "GL_INVALID_FRAMEBUFFER_OPERATION";
            case GL_OUT_OF_MEMORY:
                return "GL_OUT_OF_MEMORY";
            case GL_STACK_UNDERFLOW:
                return "GL_STACK_UNDERFLOW";
            case GL_STACK_OVERFLOW:
                return "GL_STACK_OVERFLOW";
        }
        return "Unknown GL error";
    }


    /**
     * Check for queued OpenGL errors, and write messages to standard error
     * to report them.
     * 
     * @param msg A string to identify where the error came from, which is
     *            included in the output message.
     */
    public static void checkError(String msg) {
        int err;
        while ((err = glGetError()) != GL_NO_ERROR) {
            System.err.println(String.format("GL Error %s: 0x%x %s", msg, err, glErrorString(err)));
        }
    }

    /**
     * Compile a GLSL shader from source code stored in a file.  If there are compile
     * errors, throw an Error with the output from the compiler.
     * 
     * @param filename   The pathname of the file where the shader source is located.
     * @param shaderType The OpenGL constant for the shader type, for instance
     *                   GL_VERTEX_SHADER or GL_FRAGMENT_SHADER.
     * @return           The numeric handle to the new shader.
     */
    public static int compileShader(String filename, int shaderType) {
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, readSource(filename));
        glCompileShader(shader);
        if (0 == glGetShaderi(shader, GL_COMPILE_STATUS)) {
            String infoLog = glGetShaderInfoLog(shader);
            glDeleteShader(shader);
            throw new Error(String.format("Error in compiling shader '%s':\n%s", filename, infoLog));
        } else {
            return shader;
        }
    }

    /**
     * Link a simple GLSL program that has only a vertex and a fragment shader.
     * 
     * @param vtxShaderID  The numeric handle of the vertex shader.
     * @param fragShaderID The numeric handle of the fragment shader.
     * @return             The numeric handle to the new program.
     */
    public static int linkSimpleProgram(int vtxShaderID, int fragShaderID) {
        int program = glCreateProgram();
        glAttachShader(program, vtxShaderID);
        glAttachShader(program, fragShaderID);
        glLinkProgram(program);
        if (0 == glGetProgrami(program, GL_LINK_STATUS)) {
            String infoLog = glGetProgramInfoLog(program);
            glDeleteProgram(program);
            throw new Error("Error in linking a GLSL program:\n" + infoLog);
        } else {
            return program;
        }
    }

    /**
     * Read the entire contents of a shader source file into a String. Exit with
     * a useful error message if there is a problem.
     * 
     * @param filename The pathname of the file to be read.
     * @return         The shader program text
     */
    public static String readSource(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Cannot find shader source file '%s'; is the working directory set to the project root?", filename));
            System.exit(1);
        } catch (IOException e) {
            System.err.println(String.format("Unexpected exception reading shader source file '%s'", filename));
            System.exit(1);
        }
        return null; // Not reached
    }

    /**
     * Create and set up an OpenGL Vertex Array Object with the given vertex and index data.
     * @param vertexData  The vertex data.  It must contain positions and texture coordinates 
     *                    interleaved in that order, which are set up as attribute arrays 0 
     *                    and 1 respectively
     *                    
     * @param indexData   The index data
     * @return The ID of the newly created VAO
     */
    public static int buildVAO(float[] vertexData, int[] indexData) {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        FloatBuffer vertexArray = BufferUtils.createFloatBuffer(vertexData.length);
        vertexArray.put(vertexData).flip();
        int vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 4*5, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4*5, 4*3);
        glBindBuffer(GL_ARRAY_BUFFER, GL_NONE);

        IntBuffer indexArray = BufferUtils.createIntBuffer(indexData.length);
        indexArray.put(indexData).flip();
        int indexBuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexArray, GL_STATIC_DRAW);

        glBindVertexArray(GL_NONE);

        return vao;
    }

    // Vertex and index data that define a full-screen quad in TRIANGLES mode.
    // The format is suitable for buildVAO above.
    public static final float fsqVertices[] = new float[] {
        -1.0f, -1.0f, 0.0f,  // Lower left vertex position
            0.0f,  0.0f,        //    and texture coordinate
            1.0f, -1.0f, 0.0f,  // Lower right
            1.0f,  0.0f,
            1.0f,  1.0f, 0.0f,  // Top right
            1.0f,  1.0f,
            -1.0f,  1.0f, 0.0f,  // Top left
            0.0f,  1.0f
    };
    public static final int fsqIndices[] = new int[] { 0, 1, 2, 0, 2, 3 };

}
