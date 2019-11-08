package egl;

import static org.lwjgl.opengl.GL11.glTexParameteri;
import egl.GL.TextureMagFilter;
import egl.GL.TextureMinFilter;
import egl.GL.TextureParameterName;
import egl.GL.TextureWrapMode;

/**
 * OpenGL State For Texture Sampling Methods
 * @author Cristian
 *
 */
public class SamplerState {
	/**
	 * Linear Texture Interpolation With Coordinate Clamping
	 */
    public static final SamplerState LINEAR_CLAMP = new SamplerState(
        TextureMinFilter.Linear,
        TextureMagFilter.Linear,
        TextureWrapMode.ClampToEdge,
        TextureWrapMode.ClampToEdge,
        TextureWrapMode.ClampToEdge
    );
    /**
     * Linear Texture Interpolation With Coordinate Wrapping
     */
    public static final SamplerState LINEAR_WRAP = new SamplerState(
        TextureMinFilter.Linear,
        TextureMagFilter.Linear,
        TextureWrapMode.Repeat,
        TextureWrapMode.Repeat,
        TextureWrapMode.Repeat
    );
    /**
     * Closest Texel Sampling With Coordinate Clamping
     */
    public static final SamplerState POINT_CLAMP = new SamplerState(
        TextureMinFilter.Nearest,
        TextureMagFilter.Nearest,
        TextureWrapMode.ClampToEdge,
        TextureWrapMode.ClampToEdge,
        TextureWrapMode.ClampToEdge
    );
    /**
     * Closest Texel Sampling With Coordinate Wrapping
     */
    public static final SamplerState POINT_WRAP = new SamplerState(
        TextureMinFilter.Nearest,
        TextureMagFilter.Nearest,
        TextureWrapMode.Repeat,
        TextureWrapMode.Repeat,
        TextureWrapMode.Repeat
    );
    /**
	 * Linear Texture Interpolation With Coordinate Clamping And Linear Mipmapping
	 */
    public static final SamplerState LINEAR_CLAMP_MM = new SamplerState(
        TextureMinFilter.LinearMipmapLinear,
        TextureMagFilter.Linear,
        TextureWrapMode.ClampToEdge,
        TextureWrapMode.ClampToEdge,
        TextureWrapMode.ClampToEdge
    );
    /**
     * Linear Texture Interpolation With Coordinate Wrapping And Linear Mipmapping
     */
    public static final SamplerState LINEAR_WRAP_MM = new SamplerState(
        TextureMinFilter.LinearMipmapLinear,
        TextureMagFilter.Linear,
        TextureWrapMode.Repeat,
        TextureWrapMode.Repeat,
        TextureWrapMode.Repeat
    );
    /**
     * Closest Texel Sampling With Coordinate Clamping And Linear Mipmapping
     */
    public static final SamplerState POINT_CLAMP_MM = new SamplerState(
        TextureMinFilter.NearestMipmapLinear,
        TextureMagFilter.Nearest,
        TextureWrapMode.ClampToEdge,
        TextureWrapMode.ClampToEdge,
        TextureWrapMode.ClampToEdge
    );
    /**
     * Closest Texel Sampling With Coordinate Wrapping And Linear Mipmapping
     */
    public static final SamplerState POINT_WRAP_MM = new SamplerState(
        TextureMinFilter.NearestMipmapLinear,
        TextureMagFilter.Nearest,
        TextureWrapMode.Repeat,
        TextureWrapMode.Repeat,
        TextureWrapMode.Repeat
    );

    /**
     * Texture Sampling Method When Minified
     */
    public int minFilter;
    /**
     * Texture Sampling Method When Magnified
     */
    public int magFilter;
    /**
     * Wrapping In U Direction
     */
    public int wrapS;
    /**
     * Wrapping In V Direction
     */
    public int wrapT;
    /**
     * Wrapping In W Direction (3D)
     */
    public int wrapR;

    /**
     * State Constructor
     * @param textureMinFilter {@link TextureMinFilter}
     * @param textureMagFilter {@link TextureMagFilter}
     * @param textureWrapModeS {@link TextureWrapMode U Wrapping}
     * @param textureWrapModeT {@link TextureWrapMode V Wrapping}
     * @param textureWrapModeR {@link TextureWrapMode W Wrapping (3D)}
     */
    public SamplerState(
		int textureMinFilter,
		int textureMagFilter,
		int textureWrapModeS,
		int textureWrapModeT,
		int textureWrapModeR
		) {
    	minFilter = textureMinFilter;
    	magFilter = textureMagFilter;
    	wrapS = textureWrapModeS;
    	wrapT = textureWrapModeT;
    	wrapR = textureWrapModeR;
	}

    /**
     * Apply This State To A Bound Texture
     * @param target Texture's Binding Target
     */
	public void set(int target) {
        glTexParameteri(target, TextureParameterName.TextureMagFilter, magFilter);
        glTexParameteri(target, TextureParameterName.TextureMinFilter, minFilter);
        glTexParameteri(target, TextureParameterName.TextureWrapS, wrapS);
        glTexParameteri(target, TextureParameterName.TextureWrapT, wrapT);
        glTexParameteri(target, TextureParameterName.TextureWrapR, wrapR);
    }
}
