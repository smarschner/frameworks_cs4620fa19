package anim;

import java.util.TreeSet;

import common.SceneObject;
import egl.math.Matrix3;
import egl.math.Matrix4;
import egl.math.Quat;
import egl.math.Vector3;

/**
 * A timeline for a particular object in the scene.  The timeline holds
 * a sequence of keyframes and a reference to the object that they
 * pertain to.  Via linear interpolation between keyframes, the timeline
 * can compute the object's transformation at any point in time.
 * 
 * @author Cristian
 */
public class AnimTimeline {

    /**
     * A sorted set of keyframes.  Invariant: there is at least one keyframe.
     */
    public final TreeSet<AnimKeyframe> frames = new TreeSet<>(AnimKeyframe.COMPARATOR);

    /**
     * The object that this timeline animates
     */
    public final SceneObject object;

    /**
     * Create a new timeline for an object.  The new timeline initially has the object
     * stationary, with the same transformation it currently has at all times.  This is
     * achieve by createing a timeline with a single keyframe at time zero.
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
     * Add A keyframe to the timeline.
     * @param frame Frame number
     * @param t Transformation
     */
    public void addKeyFrame(int frame, Matrix4 t) {
        // TODO#A7: Add an AnimKeyframe to frames and set its transformation
    }

    /**
     * Checks if there is a keyframe at a given frame
     */
    public boolean hasKeyFrame(int frame) {
        AnimKeyframe f = new AnimKeyframe(frame);
        AnimKeyframe fTree = frames.floor(f);
        return fTree != null && fTree.frame == f.frame;
    }

    /**
     * Remove a keyframe from the timeline.  If the timeline is empty,
     * maintain the invariant by adding a single keyframe with the given
     * transformation.
     * @param frame Frame number
     * @param t Transformation
     */
    public void removeKeyFrame(int frame, Matrix4 t) {
        // TODO#A7: Delete a frame, you might want to use Treeset.remove
        // If there is no frame after deletion, add back this frame.
    }

    /**
     * Update the transformation for the object connected to this timeline to the current frame
     * @curFrame Current frame number
     */
    public void updateTransformation(int curFrame) {
        //TODO#A7: You need to get pair of surrounding frames,
        // calculate interpolation ratio,
        // calculate Translation, Scale and Rotation Interpolation,
        // and combine them.
        // Argument curFrame is current frame number
    }
}
