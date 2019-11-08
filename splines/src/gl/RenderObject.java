package gl;

import java.util.ArrayList;

import common.SceneObject;
import egl.math.Matrix3;
import egl.math.Matrix4;

public class RenderObject {
	/**
	 * The full transformation matrix of this object (composed using parent as well)
	 */
	public final Matrix4 mWorldTransform = new Matrix4();
	/**
	 * The normal matrix of this object
	 */
	public final Matrix3 mWorldTransformIT = new Matrix3();
	
	/**
	 * The parent object
	 */
	public RenderObject parent = null;
	/**
	 * A list of children
	 */
	public final ArrayList<RenderObject> children = new ArrayList<>();

	/**
	 * Reference to scene counterpart
	 */
	public final SceneObject sceneObject;

	/**
	 * Mesh
	 */
	public RenderMesh mesh = null;
	/**
	 * Material
	 */
	public RenderMaterial material = null;
	
	public RenderObject(SceneObject o) {
		sceneObject = o;
	}
}
