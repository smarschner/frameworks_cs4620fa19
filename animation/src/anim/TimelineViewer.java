package anim;

import java.awt.event.MouseEvent;

import egl.BlendState;
import egl.DepthState;
import egl.GLTexture;
import egl.SpriteBatch;
import egl.SpriteSortMode;
import egl.GL.PixelInternalFormat;
import egl.GL.TextureTarget;
import egl.math.Color;
import egl.math.MathHelper;
import egl.math.Matrix4;
import egl.math.Vector2;
import egl.math.Vector4;

/**
 * Visualizes An Animation Timeline
 * @author Cristian Zaloj
 * @author Steve Marschner
 *
 */
public class TimelineViewer {
	private static final Color COLOR_KEYFRAME = Color.Yellow;
	private static final Color COLOR_TIMELINE = Color.Black;
	private static final Color COLOR_TIMELINE_BORDER = Color.DarkGray;
	private static final Color COLOR_GRAPH = Color.White;
	private static final Color COLOR_CURSOR = new Color(200, 200, 200, 255);
	private static final float BAR_OFFSET = 10f;
	private static final float BAR_HEIGHT = 40f;
	private static final float BAR_BORDER = 3f;
	private static final float KEY_WIDTH = 9f;
	private static final float KEY_HEIGHT = 9f;
	private static final float FADE_RATE = 1.2f;
	
	private SpriteBatch sb = null;
	private final Vector4 currentColorMultiplier = new Vector4(1, 1, 1, 0);
	
	// whether mouse is down in timeline
	public boolean LeftDownTimeline = false;
	
	GLTexture texDiamond;

	public void init() {
		sb = new SpriteBatch(true);
		texDiamond = new GLTexture(TextureTarget.Texture2D, true);
		texDiamond.internalFormat = PixelInternalFormat.Rgba;
		try {
			texDiamond.setImage2D("data/textures/diamond.png", false);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}
	public void dispose() {
		sb.dispose();
		sb = null;
	}
	
	public int onTimeline(float screenWidth, float screenHeight, AnimationEngine eng, float mouseX, float mouseY, int frame) {
		float barWidth = screenWidth - BAR_OFFSET * 2;
		Vector2 pos = new Vector2(BAR_OFFSET, screenHeight - BAR_OFFSET - BAR_HEIGHT).sub(BAR_BORDER);
		pos.add(BAR_BORDER);
		if (mouseX < BAR_OFFSET + barWidth && mouseX > BAR_OFFSET && screenHeight - mouseY < pos.y + BAR_HEIGHT && screenHeight - mouseY > pos.y ){
			frame = (int) Math.floor((mouseX - BAR_OFFSET) / barWidth * eng.getNumFrames());
		}
		return frame;
	}
	
	public void draw(float screenWidth, float screenHeight, AnimationEngine eng, String name, boolean isHovering, float dt) {
		sb.begin();
		float barWidth = screenWidth - BAR_OFFSET * 2;
		
		// Draw Border
		Vector2 pos = new Vector2(BAR_OFFSET, screenHeight - BAR_OFFSET - BAR_HEIGHT).sub(BAR_BORDER);
		Vector2 size = new Vector2(barWidth, BAR_HEIGHT).add(2 * BAR_BORDER);
		sb.draw(null, pos, size, COLOR_TIMELINE_BORDER, 1.0f);
		
		// Draw Inner Bar
		Vector2 barPos = pos.clone().add(BAR_BORDER);
		Vector2 barSize = size.clone().sub(2 * BAR_BORDER);
		sb.draw(null, barPos, barSize, COLOR_TIMELINE, 0.9f);
		
		// Draw thin line
		Vector2 graphPos = barPos.clone().add(0, BAR_HEIGHT / 2);
		Vector2 graphSize = barSize.clone();
		graphSize.y = 1;
		sb.draw(null, graphPos, graphSize, COLOR_GRAPH, 0.85f);
		
		// Draw Keyframes
		Vector2 keySize = new Vector2(KEY_HEIGHT, KEY_HEIGHT);
		Vector2 keyPos = graphPos.clone().sub(KEY_HEIGHT / 2);
		AnimTimeline tl = eng.timelines.get(name);
		if(tl != null) {
			for(AnimKeyframe f : tl.frames) {
				if(f.frame < eng.getFirstFrame() || f.frame > eng.getLastFrame()) {
					continue;
				}
				float t = (f.frame - eng.getFirstFrame()) / (float) eng.getNumFrames();
				keyPos.x = barPos.x + t * barSize.x - KEY_WIDTH / 2;
				sb.draw(texDiamond, keyPos, keySize, COLOR_KEYFRAME, 0.8f);
			}
		}

		// Draw Cursor
		float tCursor = (eng.getCurrentFrame() - eng.getFirstFrame()) / (float) eng.getNumFrames();
		Vector2 cursorpos = new Vector2(barPos.x + tCursor * barSize.x, barPos.y);
		Vector2 cursorsize = new Vector2(1, barSize.y);
		sb.draw(null, cursorpos, cursorsize, COLOR_CURSOR, 0.87f);
		
		sb.end(SpriteSortMode.BackToFront);
		
		if(isHovering) currentColorMultiplier.w = 1.0f;
		else currentColorMultiplier.w = MathHelper.clamp(currentColorMultiplier.w - FADE_RATE * dt, 0, 1);
		
		sb.renderBatch(
				new Matrix4(), SpriteBatch.createCameraFromWindow(screenWidth, screenHeight),
				BlendState.ALPHA_BLEND, null, DepthState.NONE, null,
				currentColorMultiplier, null);
	}
}
