package common.texture;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import egl.math.Color;
import egl.math.MathHelper;
import egl.math.Vector2i;

public class TexGenCheckerBoard extends ACTextureGeneratorTwoColor {
	public final Vector2i tiles = new Vector2i();
	
	public TexGenCheckerBoard() {
		setTiles(8, 8);
		setColor1(Color.Black);
		setColor2(Color.White);
	}
	
	public void setTiles(int x, int y) {
		tiles.set(x, y);
	}
	public void setTiles(Vector2i t) {
		setTiles(t.x, t.y);
	}

	
	@Override
	public void getColor(float u, float v, Color outColor) {
		// Find Tile Positions
		int ui = (int)Math.floor(u * tiles.x);
		ui = MathHelper.clamp(ui, 0, tiles.x - 1);
		int vi = (int)Math.floor(v * tiles.y);
		vi = MathHelper.clamp(vi, 0, tiles.y - 1);
		
		// Tile Index
		int ti = vi * tiles.x + ui;
		
		// Even Checker Board Pretend We Have A Ghost Tile
		if(tiles.x % 2 == 0) ti += vi;

		outColor.set(ti % 2 == 0 ? color1 : color2);
	}
	
	@Override
	public void saveData(Document doc, Element eData) {
		super.saveData(doc, eData);
		
		Element e = doc.createElement("tiles");
		e.appendChild(doc.createTextNode(tiles.x + " " + tiles.y));
		eData.appendChild(e);
	}
}
