package egl;

/**
 * A Sprite Instance
 * @author Cristian
 *
 */
public class SpriteGlyph {
	/**
	 * Texture Used By The Sprite
	 */
	public GLTexture texture;
	/**
	 * Draw Depth
	 */
    public float depth;

    /**
     * Top Left Corner
     */
    public final VertexSpriteBatch vtl;
    /**
     * Top Right Corner
     */
    public final VertexSpriteBatch vtr;
    /**
     * Bottom Left Corner
     */
    public final VertexSpriteBatch vbl;
    /**
     * Bottom Right Corner
     */
    public final VertexSpriteBatch vbr;

    /**
     * Construct An Empty Sprite Glyph
     * @param t Texture
     * @param d Depth
     */
    public SpriteGlyph(GLTexture t, float d) {
        texture = t;
        depth = d;
        vtl = new VertexSpriteBatch();
        vtr = new VertexSpriteBatch();
        vbl = new VertexSpriteBatch();
        vbr = new VertexSpriteBatch();
    }
}
