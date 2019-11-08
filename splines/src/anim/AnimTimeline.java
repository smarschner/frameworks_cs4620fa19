package anim;

import java.util.TreeSet;

import common.SceneObject;
import egl.math.Matrix4;

/**
 * A Container Of Keyframes For An Object
 * @author Cristian
 *
 */
public class AnimTimeline {
	/**
	 * A Sorted Set Of Keyframes
	 */
	public final TreeSet<AnimKeyframe> frames = new TreeSet<>(AnimKeyframe.COMPARATOR);
	/**
	 * The Object Found In This Timeline
	 */
	public final SceneObject object;
	
	/**
	 * An Animation Timeline For An Object
	 * @param o Object
	 */
	public AnimTimeline(SceneObject o) {
		object = o;
		
		// Create A Default Keyframe
		AnimKeyframe f = new AnimKeyframe(0);
		f.transformation.set(o.transformation);
		frames.add(f);
	}
	
	/**
	 * Add A Keyframe For This Object
	 * @param frame Frame Number
	 * @param t Transformation
	 */
	public void addKeyFrame(int frame, Matrix4 t) {
		AnimKeyframe f = new AnimKeyframe(frame);
		AnimKeyframe fTree = frames.floor(f);
		if(fTree != null && f.frame == fTree.frame) f = fTree;
		else frames.add(f);
		
		f.transformation.set(t);
	}
	/**
	 * Remove A Keyframe For This Object
	 * @param frame Frame Number
	 * @param t Transformation
	 */
	public void removeKeyFrame(int frame, Matrix4 t) {
		AnimKeyframe f = new AnimKeyframe(frame);
		frames.remove(f);
		if(frames.size() == 0) {
			f.transformation.set(t);
			frames.add(f);
		}
	}

	public void getSurroundingFrames(int frame, AnimKeyframe[] outPair) {
		// Search For Surrounding Frames
		AnimKeyframe tmp = new AnimKeyframe(frame);
		outPair[0] = frames.floor(tmp);
		outPair[1] = frames.ceiling(tmp);
		
		// At Least One Is Non-Null From Our Invariant, But One Might Be Null
		if(outPair[0] == null) outPair[0] = outPair[1];
		else if(outPair[1] == null) outPair[1] = outPair[0];
	}
}
