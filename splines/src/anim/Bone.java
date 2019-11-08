package anim;

import java.util.ArrayList;

import egl.math.Matrix4;
import egl.math.Quat;
import egl.math.Vector3;

/**
 * A Piece Of The Skeleton, With The Identity Pointing In The X-Axis
 * @author Cristian
 *
 */
public class Bone {
	/**
	 * The Translation Of The Bone Away From It's Parent
	 */
	public final Vector3 offset = new Vector3();
	/**
	 * The Rotation Of The Bone
	 */
	public final Quat rotation = new Quat();
	/**
	 * The Envelope Length Of The Bone
	 */
	public float envelopeLength = 1.0f;
	/**
	 * The Envelope Radius Of The Bone
	 */
	public float envelopeRadius = 1.0f;
	
	/**
	 * Parent
	 */
	public Bone parent = null;
	/**
	 * A List Of Children Bones
	 */
	public final ArrayList<Bone> children = new ArrayList<>();
	
	public final String name;
	public final int index;
	
	public Bone(String n, int i) {
		name = n;
		index = i;
	}
	
	public void addChild(Bone b) {
		b.parent = this;
		children.add(b);
	}

	public Matrix4 createTransformation() {
		// Rotate First
		Matrix4 m = rotation.toRotationMatrix(new Matrix4());
		// Then Translate
		return m.mulAfter(Matrix4.createTranslation(offset));
	}
}
