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
     *	Enum for the mode of rotation
     */
    private enum RotationMode {
        EULER, QUAT_LERP, QUAT_SLERP;
    };

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

    /*
     * Rotation Mode
     */
    private RotationMode rotationMode = RotationMode.EULER;
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
        for (int i = 0; i < 3; i++)
            tl.object.prevRotate[i] = tl.object.curRotate[i];
    }

    /**
     * Reset current rotations if no keyframe is set at the current frame
     */
    public void resetFrameTransformation(String oName) {
        AnimTimeline tl = timelines.get(oName);
        if (!tl.hasKeyFrame(getCurrentFrame()) || tl.frames.size() == 1) {
            for (int i = 0; i < 3; i++)
                tl.object.curRotate[i] = tl.object.prevRotate[i];
        }
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
     * Toggles rotation mode that will be applied to all animated objects.
     */
    /* Commented out - only use SLERP for this assignment (Fall 2019)
    public void toggleRotationMode() {
        switch(this.rotationMode) {
            case EULER:
                this.rotationMode = RotationMode.QUAT_LERP;
                break;
            case QUAT_LERP:
                this.rotationMode = RotationMode.QUAT_SLERP;
                break;
            case QUAT_SLERP:
                this.rotationMode = RotationMode.EULER;
                break;
            default:
                break;
        }
        System.out.println("Now in rotation mode " + this.rotationMode.name());
    }*/

    /**
     * Loops Through All The Animating Objects And Updates Their Transformations To
     * The Current Frame - For Each Updated Transformation, An Event Has To Be 
     * Sent Through The Scene Notifying Everyone Of The Change
     */

    public void updateTransformations() {
        for(AnimTimeline tl : timelines.values()) {
            tl.updateTransformation(curFrame);
            scene.sendEvent(new SceneTransformationEvent(tl.object));
        }

    }

}

