package blister;

import org.lwjgl.opengl.GL11;

/**
 * A Screen That Waits A Frame To Change To A Different Screen
 * @author Cristian
 *
 */
public class FalseFirstScreen extends GameScreen {
	protected int nS;
    protected boolean doNext;
	@Override
	public int getNext() {
		if(doNext) { return getIndex(); }
        doNext = true;
        return nS;
	}
	@Override
	protected void setNext(int next) {
		nS = next;
	}

	private int previous;
	@Override
	public int getPrevious() {
		return previous;
	}
	@Override
	protected void setPrevious(int previous) {
		this.previous = previous;
	}

	/**
	 * Create A Screen That Will Move To A Different Screen
	 * @param nextScreen Index Of The Desired Screen To Travel
	 */
	public FalseFirstScreen(int nextScreen) {
        doNext = false;
        setNext(nextScreen);
        setPrevious(ScreenList.NO_SCREEN);
    }
	
	@Override
	public void build() {
		
	}
	@Override
	public void destroy(GameTime gameTime) {
		
	}

	@Override
	public void onEntry(GameTime gameTime) {
		
	}
	@Override
	public void onExit(GameTime gameTime) {
		
	}

	@Override
	public void update(GameTime gameTime) {
		
	}
	@Override
	public void draw(GameTime gameTime) {
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        setState(ScreenState.ChangeNext);
	}
}
