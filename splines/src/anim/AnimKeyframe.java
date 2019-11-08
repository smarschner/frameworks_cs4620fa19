package anim;

import java.util.Comparator;

import egl.math.Matrix4;

/**
 * An Immutable Struct Holding A Frame Number And Transformation
 * @author Cristian
 *
 */
public class AnimKeyframe {
	/**
	 * Compares Two Keyframes Only By Their Time
	 */
	public static final Comparator<AnimKeyframe> COMPARATOR = new Comparator<AnimKeyframe>() {
		@Override
		public int compare(AnimKeyframe o1, AnimKeyframe o2) {
			return Integer.compare(o1.frame, o2.frame);
		}
	};
	
	/**
	 * The Frame In The Timeline
	 */
	public final int frame;
	/**
	 * Object's Transformation Matrix At This Moment In Time
	 */
	public final Matrix4 transformation = new Matrix4();
	
	/**
	 * Keyframes Are Immutable
	 * @param f Frame Of The Keyframe
	 */
	public AnimKeyframe(int f) {
		frame = f;
	}
}
