package camera;

import egl.math.Vector3;
import egl.math.Matrix4;

/// An orthographic Camera.
public class OrthographicCamera extends Camera {
  protected float scale = 10.0f;

  /// Initalizes a OrthographicCamera.
  /// @param eye The starting location of the Camera.
  /// @param target The point in the center of the Camera's focus.
  /// @param vertical The direction opposite of gravity.
  /// @param aspectRatio The aspect ratio of the Camera (width/height).
  /// @param near The distance to the Camera's near plane.
  /// @param far The distance to the Camera's far plane.
  /// @param scale The scale of the OrthographicCamera's viewport.
  public OrthographicCamera(Vector3 eye,  Vector3 target, Vector3 vertical,
    float aspectRatio, float near, float far, float scale) {
    super(eye, target, vertical, aspectRatio, near, far);
    this.scale = scale;
  }

  /// @return The current scale OrthographicCamera's viewport.
  public float getScale() { return scale; }

  /// @param scale The new scale of the OrthographicCamera's viewport.
  public void setScale(float scale) {
    this.scale = scale;
  }

  public void getProjectionMatrix(Matrix4 out) {
    Matrix4.createOrthographic(getScale(), getScale() * getAspectRatio(),
      getNear(), getFar(), out);
  }

  public Matrix4 getProjectionMatrix() {
    return Matrix4.createOrthographic(getScale(), getScale() * getAspectRatio(),
      getNear(), getFar());
  }

  /// "Zooms" the OrthographicCamera by changing the scale of the viewport, rather than
  /// moving the Camera itself (which would have no visual effect).
  /// @param delta The viewport scale will be scaled by <tt>1.0 - delta</tt>.
  public void zoom(float delta) {
    setScale(getScale() * (1.0f - delta));
  }
}
