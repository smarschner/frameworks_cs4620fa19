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
				
		// TODO#A2 Fill in this function.
		// 1) Convert the input texture coordinates to integer pixel coordinates. Adding 0.5
		//    before casting a double to an int gives better nearest-pixel rounding.
		// 2) Clamp the resulting coordinates to the image boundary.
		// 3) Create a Color object based on the pixel coordinate (use Color.fromIntRGB
		//    and the image object from the Texture class), convert it to a Colord, and return it.
		// NOTE: By convention, UV coordinates specify the lower-left corner of the image as the
		//    origin, but the ImageBuffer class specifies the upper-left corner as the origin.
			
		int x = (int) (texCoord.x * image.getWidth() + 0.5f);
		int y = (int) ((1.0f - texCoord.y) * image.getHeight() + 0.5f);
		
		x = Math.max(0, Math.min(image.getWidth()-1, x));
		y = Math.max(0, Math.min(image.getHeight()-1, y));
			
		Color c = Color.fromIntRGB(image.getRGB(x, y));
		return new Colorf(c);
	}

}
