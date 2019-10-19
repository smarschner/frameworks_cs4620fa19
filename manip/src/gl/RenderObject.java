package gl;

import static org.lwjgl.opengl.GL11.*;
import egl.math.*;

/**
 * An object in the scene that needs to be rendered.
 */
public class RenderObject implements Renderable {
	/**
	 * The full world transformation matrix of this object
	 */
	private final Matrix4 mWorldTransform = new Matrix4();
	/**
	 * The normal matrix of this object
	 */
	private final Matrix3 mWorldTransformIT = new Matrix3();

	public final Matrix4 translation = new Matrix4();
	public final Matrix4 rotationX = new Matrix4();
	public final Matrix4 rotationY = new Matrix4();
	public final Matrix4 rotationZ = new Matrix4();
	public final Matrix4 scale = new Matrix4();
	private Colorf color = new Colorf();

	/* (non-Javadoc)
	 * @see gl.Renderable#getWorldTransform()
	 */
	@Override
	public Matrix4 getWorldTransform() {
		mWorldTransform.set(translation)
				.mulBefore(rotationX)
				.mulBefore(rotationY)
				.mulBefore(rotationZ)
				.mulBefore(scale);
		return mWorldTransform;
	}

	/* (non-Javadoc)
	 * @see gl.Renderable#getWorldTransformIT()
	 */
	@Override
	public Matrix3 getWorldTransformIT() {
		getWorldTransform();
		mWorldTransformIT.set(mWorldTransform.getAxes()).invert().transpose();
		return mWorldTransformIT;
	}

	/**
	 * Mesh
	 */
	private RenderMesh mesh = null;

	public RenderObject(RenderMesh mesh) {
		this.mesh = mesh;
	}

	/* (non-Javadoc)
	 * @see gl.Renderable#getMesh()
	 */
	@Override
	public RenderMesh getMesh() {
		return mesh;
	}

	/**
	 * @param mesh the mesh to set
	 */
	public void setMesh(RenderMesh mesh) {
		this.mesh = mesh;
	}

	public void setColor(Colorf color) {
		this.color.set(color);
	}

	@Override
	public Colorf getColor() {
		return new Colorf(Color.AliceBlue);
	}

	@Override
	public float getMode() {
		return 1f;
	}
	
	public void render () {
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ZERO);
		mesh.render();
	}
}
