package camera;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

import egl.math.Vector2;
import egl.math.Vector2i;

/**
 * A CameraController that is useful with mouse input.
 * @author eschweickart
 *
 */
public class MouseCC extends CameraController {
	/**
	 * Creates a MouseCC.
	 * @param camera The Camera to be controlled.
	 */
	public MouseCC(Camera camera) { super(camera); }

	/**
	 * Zooms the camera using the y-magnitude of the scroll event.
	 */
	@Override
	public boolean scrollEvent(Vector2i p, Vector2 rel, int modifiers) {
		camera.zoom(rel.y * 0.01f);
		return true;
	}

	/**
	 * If the left button is held down, orbits the Camera. If the right button
	 * is held down, tracks the Camera.
	 */
	@Override
	public boolean mouseDragEvent(Vector2i p, Vector2i rel, int button,
			int modifiers) {
		if (button == GLFW_MOUSE_BUTTON_LEFT) {
			camera.orbit(new Vector2(rel.x * -0.01f, rel.y * -0.01f));
			return true;
		}
		if (button == GLFW_MOUSE_BUTTON_RIGHT) {
			camera.track(new Vector2(-rel.x * 0.02f, rel.y * 0.02f));
			return true;
		}
		return false;
	}
}
