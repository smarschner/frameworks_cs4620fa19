package egl;

import static egl.GL.*;
import static org.lwjgl.opengl.GL11.glEnable;

public class GLState {
	public static void enableTextures() {
        glEnable(EnableCap.Texture1D);
        glEnable(EnableCap.Texture2D);
        glEnable(EnableCap.TextureRectangle);
    }
    public static void enableBlending() {
    	glEnable(EnableCap.Blend);
    }
    public static void enableAll() {
        enableTextures();
        enableBlending();
        DepthState.DEFAULT.set();
        RasterizerState.CULL_COUNTER_CLOCKWISE.set();
        BlendState.ADDITIVE.set();
    }
}
