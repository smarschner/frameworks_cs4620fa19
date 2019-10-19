package gl;

import egl.math.Colorf;
import egl.math.Matrix3;
import egl.math.Matrix4;

public interface Renderable {

	/**
	 * @return The world transform of the renderable
	 */
	Matrix4 getWorldTransform();

	/**
	 * @return The inverse transpose of world transform of the renderable
	 */
	Matrix3 getWorldTransformIT();

	/**
	 * @return the mesh of the renderable
	 */
	RenderMesh getMesh();
	
	/**
	 * @return The color of the renderable
	 */
	Colorf getColor();
	
	/**
	 * For shader usage. 0 if a constant color should be used
	 * 1 if lambertian shading should be used.
	 */
	float getMode();
	
	/**
	 * draw the renderable using openGL.
	 */
	void render();

}