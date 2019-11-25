package egl;

/**
 * Specifies Sprite Ordering When Drawing Them Via SpriteBatch
 * @author Cristian
 *
 */
public class SpriteSortMode {
	/**
	 * Sprites Drawn In Order They Were Given
	 */
	public static final int None = 0;
	/**
	 * Sprites Drawn From Front To Back (Z-Buffer Optimization For Opaque Objects)
	 */
	public static final int FrontToBack = 1;
	/**
	 * Sprites Drawn From Back To Front (Painter's Algorithm)
	 */
	public static final int BackToFront = 2;
	/**
	 * Sprites Are Batched By Texture
	 */
	public static final int Texture = 3;
}
