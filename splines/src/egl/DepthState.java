package egl;

import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import egl.GL.DepthFunction;
import egl.GL.EnableCap;

/**
 * OpenGL State For Depth-Buffer Reading, Writing, And Testing
 * @author Cristian
 *
 */
public class DepthState {
	/**
	 * Do Not Write Depth Or Read Depth To Draw
	 */
	public static final DepthState NONE = new DepthState(
        false,
        DepthFunction.Always,
        false
    );
	/**
	 * Draw Only When Depth Is Less Than Or Equal To Depth Buffer, Without Overwriting Depth
	 */
	public static final DepthState DEPTH_READ = new DepthState(
        true,
        DepthFunction.Lequal,
        false
    );
	/**
	 * Overwrite The Depth Buffer With Current Depth
	 */
    public static final DepthState DEPTH_WRITE = new DepthState(
        false,
        DepthFunction.Always,
        true
    );
    /**
     * Draw Only When Depth Is Less Than Or Equal To Depth Buffer, Overwriting Depth
     */
    public static final DepthState DEFAULT = new DepthState(
        true,
        DepthFunction.Lequal,
        true
    );

    /**
     * Should Depth Comparisons Take Place
     */
    public boolean shouldRead;
    /**
     * Depth Comparison Function
     */
    public int depthFunc;
    /**
     * Should Pixel Depth Be Written
     */
    public boolean shouldWrite;

    /**
     * State Constructor
     * @param read Enable Depth Reading
     * @param depthFunction {@link DepthFunction}
     * @param write Enable Depth Writing
     */
    public DepthState(boolean read, int depthFunction, boolean write) {
    	shouldRead = read;
    	depthFunc = depthFunction;
    	shouldWrite = write;
    }

    /**
     * Apply This State
     */
    public void set() {
        if(shouldRead || shouldWrite) {
            glEnable(EnableCap.DepthTest);
            glDepthMask(shouldWrite);
            glDepthFunc(depthFunc);
        }
        else {
            glDisable(EnableCap.DepthTest);
        }
    }
}