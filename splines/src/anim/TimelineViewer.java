package anim;

import egl.BlendState;
import egl.DepthState;
import egl.SpriteBatch;
import egl.SpriteSortMode;
import egl.math.Color;
import egl.math.MathHelper;
import egl.math.Matrix4;
import egl.math.Vector2;
import egl.math.Vector4;

/**
 * Visualizes An Animation Timeline
 * @author Cristian
 *
 */
public class TimelineViewer {
	private static final Color COLOR_KEYFRAME = Color.HotPink;
	private static final Color COLOR_TIMELINE = Color.DarkOrchid;
	private static final Color COLOR_TIMELINE_BORDER = Color.BlanchedAlmond;
	private static final Color COLOR_CURSOR = new Color(200, 200, 200, 200);
	private static final float BAR_OFFSET = 10f;
	private static final float BAR_HEIGHT = 20f;
	private static final float BAR_BORDER = 3f;
	private static final float FADE_RATE = 1.2f;
	
	private SpriteBatch sb = null;
	private final Vector4 currentColorMultiplier = new Vector4(1, 1, 1, 0);

	public void init() {
		sb = new SpriteBatch(true);
	}
	public void dispose() {
		sb.dispose();
		sb = null;
	}
	
	public void draw(float screenWidth, float screenHeight, AnimationEngine eng, String name, boolean isHovering, float dt) {
		sb.begin();
		float barWidth = screenWidth - BAR_OFFSET * 2;
		
		// Draw Border
		Vector2 pos = new Vector2(BAR_OFFSET, screenHeight - BAR_OFFSET - BAR_HEIGHT).sub(BAR_BORDER);
		Vector2 size = new Vector2(barWidth, BAR_HEIGHT).add(2 * BAR_BORDER);
		sb.draw(null, pos, size, COLOR_TIMELINE_BORDER, 1.0f);
		
		// Draw Inner Bar
		pos.add(BAR_BORDER);
		size.sub(2 * BAR_BORDER);
		sb.draw(null, pos, size, COLOR_TIMELINE, 0.9f);
		
		// Draw Keyframes
		size.x /= eng.getNumFrames();
		AnimTimeline tl = eng.timelines.get(name);
		if(tl != null) {
			for(AnimKeyframe f : tl.frames) {
				if(f.frame < eng.getFirstFrame() || f.frame > eng.getLastFrame()) {
					continue;
				}
				
				float t = f.frame - eng.getFirstFrame();
				t /= eng.getNumFrames();
				pos.x = BAR_OFFSET + t * barWidth;
				sb.draw(null, pos, size, COLOR_KEYFRAME, 0.8f);
			}
		}

		// Draw Cursor
		float tCursor = eng.getCurrentFrame() - eng.getFirstFrame();
		tCursor /= eng.getNumFrames();
		pos.x = BAR_OFFSET + tCursor * barWidth;
		sb.draw(null, pos, size, COLOR_CURSOR, 0.7f);
		
		sb.end(SpriteSortMode.BackToFront);
		
		if(isHovering) currentColorMultiplier.w = 1.0f;
		else currentColorMultiplier.w = MathHelper.clamp(currentColorMultiplier.w - FADE_RATE * dt, 0, 1);
		
		sb.renderBatch(
				new Matrix4(), SpriteBatch.createCameraFromWindow(screenWidth, screenHeight),
				BlendState.ALPHA_BLEND, null, DepthState.NONE, null,
				currentColorMultiplier, null);
	}
}
