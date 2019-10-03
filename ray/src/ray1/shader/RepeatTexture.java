package ray1.shader;

import ray1.shader.Texture;
import egl.math.Color;
import egl.math.Colorf;
import egl.math.Vector2;

/**
 * A Texture class that repeats the texture image as necessary for UV-coordinates
 * outside the [0.0, 1.0] range.
 * 
 * @author eschweic zz335
 *
 */
public class RepeatTexture extends Texture {

	public Colorf getTexColor(Vector2 texCoord) {
		if (image == null) {
			System.err.println("Warning: Texture uninitialized!");
			return new Colorf();
		}
		
		int x = (int) (texCoord.x * image.getWidth() + 0.5f);
		int y = (int) ((1.0 - texCoord.y) * image.getHeight() + 0.5f);
		
		x = x % image.getWidth();
		if (x < 0f) x += image.getWidth();
		y = y % image.getHeight();
		if (y < 0f) y += image.getHeight();
		
		Color c = Color.fromIntRGB(image.getRGB(x, y));
		return new Colorf(c);
	}

}
