package egl.math;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Util {
	/**
	 * 3.14159265359
	 */
	public static final double PI = 3.14159265359;
	/**
	 * 3.14159265359
	 */
	public static final float PIf = 3.14159265359f;
	
	public static Vector3 getVec3fromYamlObject(Object yamlObject)
	{
		if (!(yamlObject instanceof List))
			throw new RuntimeException("yamlObject not a List");
		List<?> yamlList = (List<?>)yamlObject;
		return new Vector3(
				Float.valueOf(yamlList.get(0).toString()),
				Float.valueOf(yamlList.get(1).toString()),
				Float.valueOf(yamlList.get(2).toString()));
	}
	
	public static void assign3ElementArrayFromYamlObject(float[] output, Object yamlObject)
	{
		if (!(yamlObject instanceof List))
			throw new RuntimeException("yamlObject not a List");
		List<?> yamlList = (List<?>)yamlObject;

		output[0] = Float.valueOf(yamlList.get(0).toString());
		output[1] = Float.valueOf(yamlList.get(1).toString());
		output[2] = Float.valueOf(yamlList.get(2).toString());
	}

	public static void assign4ElementArrayFromYamlObject(float[] output, Object yamlObject)
	{
		if (!(yamlObject instanceof List))
			throw new RuntimeException("yamlObject not a List");
		List<?> yamlList = (List<?>)yamlObject;

		output[0] = Float.valueOf(yamlList.get(0).toString());
		output[1] = Float.valueOf(yamlList.get(1).toString());
		output[2] = Float.valueOf(yamlList.get(2).toString());
		output[3] = Float.valueOf(yamlList.get(3).toString());
	}
	
	/**
	 * Stolen from http://snippets.dzone.com/posts/show/1335
	 */
	public static String readFileAsString(String filePath) throws IOException{
	    byte[] buffer = new byte[(int) new File(filePath).length()];
	    BufferedInputStream f = null;
	    try {
	        f = new BufferedInputStream(new FileInputStream(filePath));
	        f.read(buffer);
	    } finally {
	        if (f != null) try { f.close(); } catch (IOException ignored) { }
	    }
	    return new String(buffer);
	}

	// TODOX: Move To GLProgram, Op on current
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, float f) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		gl.glUniform1f(loc, f); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Vec2 v) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		gl.glUniform2f(loc, v.x, v.y); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Vec3 v) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		gl.glUniform3f(loc, v.x, v.y, v.z); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Vec4 v) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		gl.glUniform4f(loc, v.x, v.y, v.z, v.w); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Mat3 m) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		gl.glUniformMatrix3fv(loc, 1, true, m.m, 0); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Mat4 m) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		gl.glUniformMatrix4fv(loc, 1, true, m.m, 0); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, float[] f) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		gl.glUniform1fv(loc, f.length, f, 0); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Vec2[] v) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		float[] buf = new float[2 * v.length];
//		int bi = 0;
//		for(int ei = 0; ei < v.length;ei++) {
//			for(float f : v[ei]) {
//				buf[bi++] = f;
//			}
//		}
//		gl.glUniform2fv(loc, v.length, buf, 0); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Vec3[] v) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		float[] buf = new float[3 * v.length];
//		int bi = 0;
//		for(int ei = 0; ei < v.length;ei++) {
//			for(float f : v[ei]) {
//				buf[bi++] = f;
//			}
//		}
//		gl.glUniform3fv(loc, v.length, buf, 0); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Vec4[] v) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		float[] buf = new float[4 * v.length];
//		int bi = 0;
//		for(int ei = 0; ei < v.length;ei++) {
//			for(float f : v[ei]) {
//				buf[bi++] = f;
//			}
//		}
//		gl.glUniform4fv(loc, v.length, buf, 0); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Mat3[] m) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		float[] buf = new float[Mat3.ELEMENTS * m.length];
//		int bi = 0;
//		for(int ei = 0; ei < m.length;ei++) {
//			for(int i = 0;i < Mat3.ELEMENTS;i++) {
//				buf[bi++] = m[ei].m[i];
//			}
//		}
//		gl.glUniformMatrix3fv(loc, m.length, true, buf, 0); GLError.get(gl, "Util.unSet");
//		return true;
//	}
//	public static boolean setUniform(GL2 gl, GLProgram program, String name, Mat4[] m) {
//		int loc = program.getUniformLocation(name);
//		if(loc < 0) return false;
//		float[] buf = new float[Mat4.ELEMENTS * m.length];
//		int bi = 0;
//		for(int ei = 0; ei < m.length;ei++) {
//			for(int i = 0;i < Mat4.ELEMENTS;i++) {
//				buf[bi++] = m[ei].m[i];
//			}
//		}
//		gl.glUniformMatrix4fv(loc, m.length, true, buf, 0); GLError.get(gl, "Util.unSet");
//		return true;
//	}
}
