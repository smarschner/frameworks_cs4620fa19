package camera;

import egl.math.Vector3;
import egl.math.Matrix4;

/// A pinhole perspective Camera.
public class PerspectiveCamera extends Camera {
  protected float fov = (float)(Math.PI / 4.0);

  /// Initalizes a PerspectiveCamera.
  /// @param eye The starting location of the Camera.
  /// @param target The point in the center of the Camera's focus.
  /// @param vertical The direction opposite of gravity.
  /// @param aspectRatio The aspect ratio of the Camera (width/height).
  /// @param near The distance to the Camera's near plane.
  /// @param far The distance to the Camera's far plane.
  /// @param fov The field of view angle in radians, measured from the Camera's
  ///            gaze vector to the edge of the Camera's frustum on the y-axis.
  public PerspectiveCamera(Vector3 eye, Vector3 target, Vector3 vertical,
    float aspectRatio, float near, float far, float fov) {
    super(eye, target, vertical, aspectRatio, near, far);
    this.fov = fov;
  }

  /// Gets the PerspectiveCamera's current field of view.
  /// @return The field of view angle in degrees, measured from the Camera's
  ///         gaze vector to the edge of the Camera's frustum on the y-axis.
  public float getFOV() { return fov; }

  /// Sets the PerspectiveCamera's current field of view.
  /// @param fov The field of view angle in radians, measured from the Camera's
  ///             gaze vector to the edge of the Camera's frustum on the y-axis.
  public void setFOV(float fov) { 
    this.fov = fov;
  }

  public void getProjectionMatrix(Matrix4 out) {
    Matrix4.createPerspectiveFOV(getFOV(), getAspectRatio(), getNear(), getFar(), out);
  }

  public Matrix4 getProjectionMatrix() {
    return Matrix4.createPerspectiveFOV(getFOV(), getAspectRatio(), getNear(), getFar());
  }
}
