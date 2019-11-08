package egl;

import java.nio.ByteBuffer;

import egl.math.Color;
import egl.math.Vector2;
import egl.math.Vector2d;
import egl.math.Vector3;
import egl.math.Vector3d;
import egl.math.Vector4;
import egl.math.Vector4d;

/**
 * Provides Common Method To Append Elements Into A ByteBuffer
 * @author Cristian
 *
 */
public class VertexUtils {
	/**
	 * Place A Byte In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, byte v) {
		b.put(v);
	}
	/**
	 * Place A Short In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, short v) {
		b.putShort(v);
	}
	/**
	 * Place An Integer In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, int v) {
		b.putInt(v);
	}
	/**
	 * Place A Long In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, long v) {
		b.putLong(v);
	}
	/**
	 * Place A Float In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, float v) {
		b.putFloat(v);
	}
	/**
	 * Place A Double In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, double v) {
		b.putDouble(v);
	}

	/**
	 * Place 4 Bytes RGBA In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, Color v) {
		b.put(v.R);
		b.put(v.G);
		b.put(v.B);
		b.put(v.A);
	}
	
	/**
	 * Place 2 Floats XY In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, Vector2 v) {
		b.putFloat(v.x);
		b.putFloat(v.y);
	}
	/**
	 * Place 3 Floats XYZ In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, Vector3 v) {
		b.putFloat(v.x);
		b.putFloat(v.y);
		b.putFloat(v.z);
	}
	/**
	 * Place 4 Floats XYZW In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, Vector4 v) {
		b.putFloat(v.x);
		b.putFloat(v.y);
		b.putFloat(v.z);
		b.putFloat(v.w);
	}
	
	/**
	 * Place 2 Doubles XY In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, Vector2d v) {
		b.putDouble(v.x);
		b.putDouble(v.y);
	}
	/**
	 * Place 3 Doubles XYZ In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, Vector3d v) {
		b.putDouble(v.x);
		b.putDouble(v.y);
		b.putDouble(v.z);
	}
	/**
	 * Place 4 Doubles XYZW In The Vertex Buffer Memory
	 * @param b Vertex Memory
	 * @param v Value
	 */
	public static void appendToBuffer(ByteBuffer b, Vector4d v) {
		b.putDouble(v.x);
		b.putDouble(v.y);
		b.putDouble(v.z);
		b.putDouble(v.w);
	}
}
