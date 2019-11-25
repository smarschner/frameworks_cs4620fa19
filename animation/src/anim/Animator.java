package anim;

/**
 * Framerate Controller
 * @author Cristian
 *
 */
public class Animator {
	/**
	 * Desired FPS
	 */
	private float fps = 24f;
	/**
	 * Value Used In Calculations
	 */
	private float spf = 1f / 24f;
	
	/**
	 * Time Left Over From Last Frame
	 */
	private float t = 0f;
	/**
	 * True If Frames Must Still Be Popped Out
	 */
	private boolean isPlaying = false;
	
	/**
	 * Change The Framerate Of Animation
	 * @param _fps Desired FPS
	 */
	public void setFPS(float _fps) {
		fps = _fps;
		spf = 1f / fps;
	}
	
	public void togglePlaying() {
		isPlaying = !isPlaying;
	}

	/**
	 * Calculates Number Of Frames That Have Passed Within A Period Of Time
	 * @param dt Elapsed Time In Seconds
	 * @return Number Of Frames That Have Passed
	 */
	public int update(float dt) {
		if(!isPlaying) return 0;
		
		t += dt;
		int newFrames = (int)(Math.floor(t / spf) + 0.5f);
		if(newFrames > 0) t -= newFrames * spf;
		return newFrames;
	}
}
