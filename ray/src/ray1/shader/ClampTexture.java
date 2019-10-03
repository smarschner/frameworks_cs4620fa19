package ray1.shader;

import ray1.shader.Texture;
import egl.math.Color;
import egl.math.Colorf;
import egl.math.Vector2;

/**
 * A Texture class that treats UV-coordinates outside the [0.0, 1.0] range as if they
 * were at the nearest image boundary.
 * @author eschweic zz335
 *
 */
public class ClampTexture extends Texture {

	public Colorf getTexColor(Vector2 texCoord) {
		if (image == null) {
			System.err.println("Warning: Texture uninitialized!");
			return new Colorf();
		}
			
		int x = (int) (texCoord.x * image.getWidth() + 0.5f);
		int y = (int) ((1.0f - texCoord.y) * image.getHeight() + 0.5f);
		
		x = Math.max(0, Math.min(image.getWidth()-1, x));
		y = Math.max(0, Math.min(image.getHeight()-1, y));
			
		Color c = Color.fromIntRGB(image.getRGB(x, y));
		return new Colorf(c);
	}

}
