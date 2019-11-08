package egl.math;

public class MathHelper {
	public static byte clamp(byte v, byte min, byte max) {
		return v < min ? min : (v > max ? max : v);
	}
	public static short clamp(short v, short min, short max) {
		return v < min ? min : (v > max ? max : v);
	}
	public static int clamp(int v, int min, int max) {
		return v < min ? min : (v > max ? max : v);
	}
	public static long clamp(long v, long min, long max) {
		return v < min ? min : (v > max ? max : v);
	}
	public static float clamp(float v, float min, float max) {
		return v < min ? min : (v > max ? max : v);
	}
	public static double clamp(double v, double min, double max) {
		return v < min ? min : (v > max ? max : v);
	}
}
