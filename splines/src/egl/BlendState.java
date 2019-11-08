package egl;

import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.opengl.GL20.glBlendEquationSeparate;
import egl.GL.BlendEquationMode;
import egl.GL.BlendingFactorDest;
import egl.GL.BlendingFactorSrc;

/**
 * OpenGL State For Pixel Blending Functions
 * @author Cristian
 *
 */
public class BlendState {
	/**
	 * Fully Opaque Blending With No Usage Of Alpha Value:
	 * Pixel = Src
	 */
	public static final BlendState OPAQUE = new BlendState(
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.One,
        BlendingFactorDest.Zero,
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.One,
        BlendingFactorDest.Zero
    );
    /**
     * Usage Of Alpha For Both Pixels:
     * Pixel = Src * Alpha + Dest * (1 - Alpha)
     */
	public static final BlendState ALPHA_BLEND = new BlendState(
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.SrcAlpha,
        BlendingFactorDest.OneMinusSrcAlpha,
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.One,
        BlendingFactorDest.Zero
    );
    /**
     * Usage Of Alpha On The Destination Pixel:
     * Pixel = Src + Dest * (1 - Alpha)
     */
	public static final BlendState PREMULTIPLIED_ALPHA_BLEND = new BlendState(
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.One,
        BlendingFactorDest.OneMinusSrcAlpha,
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.One,
        BlendingFactorDest.Zero
    );
	/**
     * Usage Of Alpha On The Source Pixel:
     * Pixel = Src * Alpha + Dest
     */
    public static final BlendState ADDITIVE = new BlendState(
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.SrcAlpha,
        BlendingFactorDest.One,
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.One,
        BlendingFactorDest.Zero
    );
    /**
     * Pure Color Addition:
     * Pixel = Src + Dest
     */
    public static final BlendState PREMULTIPLIED_ADDITIVE = new BlendState(
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.One,
        BlendingFactorDest.One,
        BlendEquationMode.FuncAdd,
        BlendingFactorSrc.One,
        BlendingFactorDest.Zero
    );

    /**
     * RGB Blending Function
     */
    public int blendMode;
    /**
     * RGB Source Scaling Factor
     */
    public int blendSrc;
    /**
     * RGB Destination Scaling Factor
     */
    public int blendDest;
    /**
     * Alpha Blending Function
     */
    public int blendModeAlpha;
    /**
     * Alpha Source Scaling Factor
     */
    public int blendSrcAlpha;
    /**
     * Alpha Destination Scaling Factor
     */
    public int blendDestAlpha;

    /**
     * State Constructor
     * @param blendEquationMode {@link BlendEquationMode RGB Function}
     * @param blendingFactorSrc {@link BlendingFactorSrc RGB Source Factor}
     * @param blendingFactorDest {@link BlendingFactorDest RGB Destination Factor }
     * @param blendEquationModeAlpha {@link BlendEquationMode Alpha Function}
     * @param blendingFactorSrcAlpha {@link BlendingFactorSrc Alpha Source Factor}
     * @param blendingFactorDestAlpha {@link BlendingFactorDest Alpha Destination Factor }
     */
    public BlendState(
		int blendEquationMode,
		int blendingFactorSrc,
		int blendingFactorDest,
		int blendEquationModeAlpha,
		int blendingFactorSrcAlpha,
		int blendingFactorDestAlpha
		) {
    	blendMode = blendEquationMode;
    	blendSrc = blendingFactorSrc;
    	blendDest = blendingFactorDest;
    	blendModeAlpha = blendEquationModeAlpha;
    	blendSrcAlpha = blendingFactorSrcAlpha;
    	blendDestAlpha = blendingFactorDestAlpha;
	}

    /**
     * Apply This State
     */
	public void set() {
        glBlendEquationSeparate(blendMode, blendModeAlpha);
        glBlendFuncSeparate(blendSrc, blendDest, blendSrcAlpha, blendDestAlpha);
    }
}
