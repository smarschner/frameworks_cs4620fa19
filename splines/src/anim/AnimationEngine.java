package anim;

import java.util.HashMap;
import common.Scene;
import common.SceneObject;
import common.event.SceneTransformationEvent;
import egl.math.Matrix4;
import egl.math.Vector3;
import egl.math.Matrix3;
import egl.math.Quat;

/**
 * A Component Resting Upon Scene That Gives
 * Animation Capabilities
 * @author Cristian
 *
 */
public class AnimationEngine {
	/**
	 * The First Frame In The Global Timeline
	 */
	private int frameStart = 0;
	/**
	 * The Last Frame In The Global Timeline
	 */
	private int frameEnd = 100;
	/**
	 * The Current Frame In The Global Timeline
	 */
	private int curFrame = 0;
	/**
	 * Scene Reference
	 */
	private final Scene scene;
	/**
	 * Animation Timelines That Map To Object Names
	 */
	public final HashMap<String, AnimTimeline> timelines = new HashMap<>();

	/**
	 * An Animation Engine That Works Only On A Certain Scene
	 * @param s The Working Scene
	 */
	public AnimationEngine(Scene s) {
		scene = s;
	}
	
	/**
	 * Set The First And Last Frame Of The Global Timeline
	 * @param start First Frame
	 * @param end Last Frame (Must Be Greater Than The First
	 */
	public void setTimelineBounds(int start, int end) {
		// Make Sure Our End Is Greater Than Our Start
		if(end < start) {
			int buf = end;
			end = start;
			start = buf;
		}
		
		frameStart = start;
		frameEnd = end;
		moveToFrame(curFrame);
	}
	/**
	 * Add An Animating Object
	 * @param oName Object Name
	 * @param o Object
	 */
	public void addObject(String oName, SceneObject o) {
		timelines.put(oName, new AnimTimeline(o));
	}
	/**
	 * Remove An Animating Object
	 * @param oName Object Name
	 */
	public void removeObject(String oName) {
		timelines.remove(oName);
	}

	/**
	 * Set The Frame Pointer To A Desired Frame (Will Be Bounded By The Global Timeline)
	 * @param f Desired Frame
	 */
	public void moveToFrame(int f) {
		if(f < frameStart) f = frameStart;
		else if(f > frameEnd) f = frameEnd;
		curFrame = f;
	}
	/**
	 * Looping Forwards Play
	 * @param n Number Of Frames To Move Forwards
	 */
	public void advance(int n) {
		curFrame += n;
		if(curFrame > frameEnd) curFrame = frameStart + (curFrame - frameEnd - 1);
	}
	/**
	 * Looping Backwards Play
	 * @param n Number Of Frames To Move Backwards
	 */
	public void rewind(int n) {
		curFrame -= n;
		if(curFrame < frameStart) curFrame = frameEnd - (frameStart - curFrame - 1);
	}

	public int getCurrentFrame() {
		return curFrame;
	}
	public int getFirstFrame() {
		return frameStart;
	}
	public int getLastFrame() {
		return frameEnd;
	}
	public int getNumFrames() {
		return frameEnd - frameStart + 1;
	}

	/**
	 * Adds A Keyframe For An Object At The Current Frame
	 * Using The Object's Transformation - (CONVENIENCE METHOD)
	 * @param oName Object Name
	 */
	public void addKeyframe(String oName) {
		AnimTimeline tl = timelines.get(oName);
		if(tl == null) return;
		tl.addKeyFrame(getCurrentFrame(), tl.object.transformation);
	}
	/**
	 * Removes A Keyframe For An Object At The Current Frame
	 * Using The Object's Transformation - (CONVENIENCE METHOD)
	 * @param oName Object Name
	 */
	public void removeKeyframe(String oName) {
		AnimTimeline tl = timelines.get(oName);
		if(tl == null) return;
		tl.removeKeyFrame(getCurrentFrame(), tl.object.transformation);
	}
	
	/**
	 * Loops Through All The Animating Objects And Updates Their Transformations To
	 * The Current Frame - For Each Updated Transformation, An Event Has To Be 
	 * Sent Through The Scene Notifying Everyone Of The Change
	 */
	
	public void updateTransformations() {
		AnimKeyframe[] pair = new AnimKeyframe[2];
	    for(AnimTimeline tl : timelines.values()) {
	        tl.getSurroundingFrames(curFrame, pair);
	           
	        float r = getRatio(pair[0].frame, pair[1].frame, curFrame); 
	           
	        // Interpolate translations linearly
            Vector3 trans1 = pair[0].transformation.getTrans();
	        Vector3 trans2 = pair[1].transformation.getTrans();
	        Vector3 transInterp = (new Vector3(trans1)).mul(1.0f - r).add(trans2.mul(r));
	            
	        // Polar decompose axis matrices
	        Matrix3 rot1 = new Matrix3();
	        Matrix3 scale1 = new Matrix3();
	        Matrix3 rot2 = new Matrix3();
	        Matrix3 scale2 = new Matrix3();
	        pair[0].transformation.getAxes().polar_decomp(rot1, scale1);
	        pair[1].transformation.getAxes().polar_decomp(rot2, scale2);
	            
	        // Slerp rotation matrix and linear interpolate scales
	        Quat rot1Q = new Quat(rot1);
	        Quat rot2Q = new Quat(rot2);
	        Matrix3 rotInterp = new Matrix3();
	        Quat.slerp(rot1Q, rot2Q, r).toRotationMatrix(rotInterp);
	        Matrix3 scaleInterp = new Matrix3();
	        scaleInterp.interpolate(scale1, scale2, r);
	            
	        // Combine interpolated R, S and T 
	        Matrix3 axisInterp = new Matrix3();
	        rotInterp.mulBefore(scaleInterp, axisInterp);
	        Matrix4 res = new Matrix4(axisInterp);
	        res.mulAfter(Matrix4.createTranslation(transInterp, new Matrix4()));
	        
	        tl.object.transformation.set(res);

	        scene.sendEvent(new SceneTransformationEvent(tl.object));
	    }
		
	}

	public static float getRatio(int min, int max, int cur) {
		if(min == max) return 0f;
		float total = max - min;
		float diff = cur - min;
		return diff / total;
	}
	
}
