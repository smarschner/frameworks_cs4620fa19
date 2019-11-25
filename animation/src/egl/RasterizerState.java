package egl;

import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import egl.GL.CullFaceMode;
import egl.GL.EnableCap;
import egl.GL.FrontFaceDirection;

/**
 * OpenGL State For Specifying Triangle Culling And Orientation
 * @author Cristian
 *
 */
public class RasterizerState {
	/**
	 * Draw All Triangles
	 */
	public static final RasterizerState CULL_NONE = new RasterizerState(
        false,
        CullFaceMode.Back,
        FrontFaceDirection.Ccw
    );
    /**
     * Draw Counter-Clockwise Oriented Triangles
     */
	public static final RasterizerState CULL_CLOCKWISE = new RasterizerState(
        true,
        CullFaceMode.Back,
        FrontFaceDirection.Ccw
    );
	/**
	 * Draw Clockwise Oriented Triangles
	 */
    public static final RasterizerState CULL_COUNTER_CLOCKWISE = new RasterizerState(
        true,
        CullFaceMode.Back,
        FrontFaceDirection.Cw
    );

    /**
     * Should Triangles Be Culled
     */
    public boolean useCulling;
    /**
     * Triangle Face Directions To Be Removed
     */
    public int cullMode;
    /**
     * Vertex Winding Designating A Triangle That Faces Forward
     */
    public int faceOrientation;

    /**
     * State Constructor
     * @param use Should Culling Be Added To The Pipeline
     * @param cullFaceMode {@link CullFaceMode}
     * @param frontFaceDirection {@link FrontFaceDirection}
     */
    public RasterizerState(boolean use, int cullFaceMode, int frontFaceDirection) {
    	useCulling = use;
    	cullMode = cullFaceMode;
    	faceOrientation = frontFaceDirection;
	}

    /**
     * Apply This State
     */
	public void set() {
        if(useCulling) {
            glEnable(EnableCap.CullFace);
            glFrontFace(faceOrientation);
            glCullFace(cullMode);
        }
        else {
            glDisable(EnableCap.CullFace);
        }
    }
}
