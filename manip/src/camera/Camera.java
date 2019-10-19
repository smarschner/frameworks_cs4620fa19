package camera;

import egl.math.*;

/**
 *  A representation of a Camera in a 3D space.
 * @author eschweickart
 *
 */
public abstract class Camera {
  private final Vector3 eye = new Vector3();
  private final Vector3 target = new Vector3();
  private final Vector3 vertical = new Vector3();

  private float aspectRatio;
  private float near;
  private float far;

  private float minPolarAngle = 0.1f;
  private float maxPolarAngle = (float)(Math.PI - minPolarAngle);

  /**
   * @return The current location of the Camera.
   */
  public Vector3 getEye() { return eye; }

  /**
   * @return The current location of the Camera's focus.
   */
  public Vector3 getTarget() { return target; }

  /**
   * @return The axis that represents the opposite of gravity.
   */
  public Vector3 getVertical() { return vertical; }

  /**
   * @return The aspect ratio of the Camera.
   */
  public float getAspectRatio() { return aspectRatio; }

  /**
   * @param aspectRatio The new aspect ratio of the Camera.
   */
  public void setAspectRatio(float aspectRatio) {
    this.aspectRatio = aspectRatio;
  }

  /**
   * @return The distance to the near plane of the Camera's frustum.
   */
  public float getNear() { return near; }

  /**
   * @param near The new distance to the near plane of the Camera's frustum.
   */
  public void setNear(float near) {
    this.near = near;
  }

  /**
   * @return The distance to the far plane of the Camera's frustum.
   */
  public float getFar() { return far; }

  /**
   * @param far The new distance to the far plane of the Camera's frustum.
   */
  public void setFar(float far) {
    this.far = far;
  }

  /**
   *  Initializes a Camera.
   * @param eye The starting location of the Camera.
   * @param target The point in the center of the Camera's focus.
   * @param vertical The direction opposite of gravity.
   * @param aspectRatio The aspect ratio of the Camera (width/height).
   * @param near The distance to the Camera's near plane.
   * @param far The distance to the Camera's far plane.
   */
  public Camera(Vector3 eye,  Vector3 target, Vector3 vertical,
    float aspectRatio, float near, float far) {
    getEye().set(eye);
    getTarget().set(target);
    getVertical().set(vertical).normalize();
    setAspectRatio(aspectRatio);
    setNear(near);
    setFar(far);
  }

  /**
   * Gets the current view (or camera) matrix.
   * @param out The matrix to be set. Must be non-null.
   */
  public void getViewMatrix(Matrix4 out) {
    Matrix4.createLookAt(getEye(), getTarget(), getVertical(), out);
  }

  /**
   * @return The current view (or camera) matrix.
   */
  public Matrix4 getViewMatrix() {
    return Matrix4.createLookAt(getEye(), getTarget(), getVertical());
  }

  /**
   * Gets the current projection matrix.
   * @param out The matrix to be set. Must be non-null.
   */
  public abstract void getProjectionMatrix(Matrix4 out);

  /**
   * @return The current projection matrix.
   */
  public abstract Matrix4 getProjectionMatrix();

  /**
   * Gets the current view-projection matrix.
   * @param out The matrix to be set. Must be non-null.
   */
  public void getViewProjectionMatrix(Matrix4 out) {
    getProjectionMatrix(out);
    out.mulBefore(getViewMatrix());
  }

  /**
   * @return The current view-projection matrix.
   */
  public Matrix4 getViewProjectionMatrix() {
    return getProjectionMatrix().mulBefore(getViewMatrix());
  }

  /**
   * Moves the Camera closer to or further from its target.
   * This distance is clamped to be between getNear() and getFar().
   * @param delta The distance between the Camera and its target is scaled by
   *   1.0 minus this parameter.
   */
  public void zoom(float delta) {
    Vector3 gaze = getTarget().clone().sub(getEye());
    float length = gaze.len() * (1.0f - delta);
    if (length < getNear()) {
    	getEye().set(getTarget()).addMultiple(-getNear(), gaze.normalize());
    } else if (length > getFar()) {
    	getEye().set(getTarget()).addMultiple(-getFar(), gaze.normalize());
    } else {
    	getEye().addMultiple(delta, gaze);
    }
  }

  /**
   * Moves the Camera and its target in a given direction.
   * @param delta The vector to add to the Camera's position and target.
   */
  public void translate(Vector3 delta) {
    getEye().add(delta);
    getTarget().add(delta);
  }

  /**
   * Moves the Camera and its target in the direction of the Camera's gaze.
   * @param distance The distance to move the Camera and its target.
   */
  public void dolly(float distance) {
    translate(getTarget().clone().sub(getEye()).normalize().mul(distance));
  }

  /**
   * Move the Camera and its target within the view plane.
   * @param delta The distance to move the camera within the view plane.
   */
  public void track(Vector2 delta) {
    Vector3 gaze = getTarget().clone().sub(getEye()).normalize();
    Vector3 right = gaze.clone().cross(getVertical()).normalize();
    Vector3 up = right.clone().cross(gaze).normalize();
    translate(new Vector3().addMultiple(delta.x, right).addMultiple(delta.y, up));
  }

  /**
   * Orbit the Camera around its target.
   * The Camera is first rotated around the left camera axis by delta.y radians,
   * clamped to minPolarAngle and maxPolarAngle, centered at the target.
   * Then the Camera is rotated by delta.x radians around getVertical() centered
   * at the target.
   * @param delta The amount (in radians) to rotate the camera around the
   *   vertical and left camera axes.
   */
  public void orbit(Vector2 delta) {
    Vector3 toCamera = getEye().clone().sub(getTarget());
    float distance = toCamera.len();

    float projection = toCamera.dot(getVertical());
    double vertAngle = Math.acos(projection / distance);

    if (vertAngle + delta.y > maxPolarAngle) {
    	delta.y = maxPolarAngle - (float)vertAngle;
    } else if (vertAngle + delta.y < minPolarAngle) {
    	delta.y = minPolarAngle - (float)vertAngle;
    }
    Vector3 rejection = toCamera.clone().subMultiple(projection, getVertical()).normalize();
    Vector3 right = getVertical().clone().cross(rejection).normalize();

    getEye().set(getTarget())
      .addMultiple((float)Math.cos(vertAngle + delta.y) * distance, getVertical())
      .addMultiple((float)(Math.sin(vertAngle + delta.y) * Math.cos(delta.x)) * distance, rejection)
      .addMultiple((float)Math.sin(delta.x) * distance, right);
  }
}
