package egl;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL20;

import egl.math.Matrix3;
import egl.math.Matrix4;
import egl.math.Vector2;
import egl.math.Vector3;
import egl.math.Vector4;

/**
 * Aids In Eliminating Code From Setting Certain OpenGL Uniform Data
 * @author Cristian
 *
 */
public class GLUniform {
	/**
	 * Object Instance For A Main Rendering Thread
	 */
	private static final GLUniform stInstance = new GLUniform();
	/**
	 * Static Helper For A Single Thread To Access (Not Thread-Safe)
	 * @param unID Uniform Location
	 * @param m Matrix
	 * @param transpose Specifies The Matrix Be Sent In A Transposed Fashion
	 */
	public static void setST(int unID, Matrix4 m, boolean transpose) {
		stInstance.set(unID, m, transpose);
	}
	/**
	 * Static Helper For A Single Thread To Access (Not Thread-Safe)
	 * @param unID Uniform Location
	 * @param m Matrix
	 * @param transpose Specifies The Matrix Be Sent In A Transposed Fashion
	 */
	public static void setST(int unID, Matrix3 m, boolean transpose) {
		stInstance.set(unID, m, transpose);
	}
	/**
	 * Sends 2 Floats To GPU As A Program Uniform
	 * @param unID Uniform Location
	 * @param v Vector
	 */
	public static void set(int unID, Vector2 v) {
		GL20.glUniform2f(unID, v.x, v.y);
	}
	/**
	 * Sends 3 Floats To GPU As A Program Uniform
	 * @param unID Uniform Location
	 * @param v Vector
	 */
	public static void set(int unID, Vector3 v) {
		GL20.glUniform3f(unID, v.x, v.y, v.z);
	}
	/**
	 * Sends 4 Floats To GPU As A Program Uniform
	 * @param unID Uniform Location
	 * @param v Vector
	 */
	public static void set(int unID, Vector4 v) {
		GL20.glUniform4f(unID, v.x, v.y, v.z, v.w);
	}
	
	/**
	 * Single Native Memory Buffer
	 */
	private FloatBuffer fbMat = NativeMem.createFloatBuffer(16);
	
	/**
	 * Sends Matrix To GPU As A Program Uniform (Not Thread-Safe)
	 * @param unID Uniform Location
	 * @param m Matrix
	 * @param transpose Specifies The Matrix Be Sent In A Transposed Fashion
	 */
	public void set(int unID, Matrix4 m, boolean transpose) {
		fbMat.clear();
		fbMat.put(m.m);
		fbMat.flip();
		GL20.glUniformMatrix4(unID, transpose, fbMat);
	}
	/**
	 * Sends Matrix To GPU As A Program Uniform (Not Thread-Safe)
	 * @param unID Uniform Location
	 * @param m Matrix
	 * @param transpose Specifies The Matrix Be Sent In A Transposed Fashion
	 */
	public void set(int unID, Matrix3 m, boolean transpose) {
		fbMat.clear();
		fbMat.put(m.m);
		fbMat.flip();
		GL20.glUniformMatrix3(unID, transpose, fbMat);
	}
}
