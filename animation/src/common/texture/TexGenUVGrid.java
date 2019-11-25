package common.texture;

import egl.math.Color;
import egl.math.MathHelper;
import egl.math.Vector2i;

public class TexGenUVGrid extends ACTextureGeneratorTwoColor {
	public TexGenUVGrid() {
		setSize(new Vector2i(128, 128));
		setColor1(Color.Red);
		setColor2(Color.Green);
	}
	
	@Override
	public void getColor(float u, float v, Color outColor) {
		outColor.set(
			MathHelper.clamp((int)Math.round(u * color1.r() + v * color2.r()), 0, 255),
			MathHelper.clamp((int)Math.round(u * color1.g() + v * color2.g()), 0, 255),	
			MathHelper.clamp((int)Math.round(u * color1.b() + v * color2.b()), 0, 255),	
			MathHelper.clamp((int)Math.round(u * color1.a() + v * color2.a()), 0, 255)
			);
	}
}
