package blister;

import egl.math.Vector2;

public abstract class GameScreen implements IGameScreen {
    private ScreenState state;
    public ScreenState getState() {
		return state;
	}
	protected void setState(ScreenState state) {
		this.state = state;
	}

	private int index; 
	public int getIndex() {
		return index;
	}
	private void setIndex(int index) {
		this.index = index;
	}
	
	public abstract int getNext();
	protected abstract void setNext(int next);

	public abstract int getPrevious();
	protected abstract void setPrevious(int previous);
	
	protected MainGame game;
    public MainGame getParentGame() {
        return game;
    }

    public Vector2 getViewSize() {
        return new Vector2(game.getWidth(), game.getHeight());
    }

    public GameScreen() {
    	state = ScreenState.ExitApplication;
    }
    
    public void setParentGame(MainGame pgame, int index) {
        game = pgame;
        setIndex(index);
    }

    public abstract void build();
    public abstract void destroy(GameTime gameTime);

    public void setRunning() {
        setState(ScreenState.Running);
    }
    public abstract void onEntry(GameTime gameTime);
    public abstract void onExit(GameTime gameTime);

    public abstract void update(GameTime gameTime);
    public abstract void draw(GameTime gameTime);
}