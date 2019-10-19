package camera;

import static org.lwjgl.glfw.GLFW.GLFW_MOD_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT;

import egl.math.Vector2;
import egl.math.Vector2i;

/**
 * A CameraController that is useful with a touchpad interface.
 * @author eschweickart
 *
 */
public class TouchpadCC extends CameraController {
	/**
	 * Creates a TouchpadCC.
	 * @param camera The Camera to be controlled.
	 */
	public TouchpadCC(Camera camera) { super(camera); }

	/**
	 * With no modifiers, this orbits the Camera.
	 * With the Shift modifier, this tracks the Camera.
	 * With the Alt modifier, this zooms the Camera.
	 */
	@Override
	public boolean scrollEvent(Vector2i p, Vector2 rel, int modifiers) {
		if (modifiers == GLFW_MOD_SHIFT) {
			camera.track(new Vector2(-rel.x * 0.01f, rel.y * 0.01f));
		} else if (modifiers == GLFW_MOD_ALT) {
			camera.zoom(rel.y * 0.01f);
		} else {
			camera.orbit(new Vector2(rel).mul(0.1f));
		}
		return true;
	}
}
