package camera;

import interactive.Interactive;

/**
 * A class that controls camera movement in reponse to user input.
 *
 * @author eschweickart
 *
 */
public class CameraController implements Interactive {
	protected Camera camera;

	/**
	 * Creates a CameraController.
	 * @param camera The Camera to be controlled.
	 */
	public CameraController(Camera camera) { this.camera = camera; }

	/**
	 * @return The Camera being controlled.
	 */
	public Camera getCamera() {
		return camera;
	}
}
