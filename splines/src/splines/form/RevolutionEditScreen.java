package splines.form;

import splines.RevolutionApp;
import blister.GameScreen;
import blister.GameTime;

public class RevolutionEditScreen extends GameScreen {

	RevolutionSplineScreen scr;
	
	@Override
	public int getNext() {
		return 0;
	}
	@Override
	protected void setNext(int next) {
	}

	@Override
	public int getPrevious() {
		return 0;
	}
	@Override
	protected void setPrevious(int previous) {
	}

	@Override
	public void build() {
		scr = ((RevolutionApp)game).scrView;
	}
	@Override
	public void destroy(GameTime gameTime) {
	}

	@Override
	public void onEntry(GameTime gameTime) {
		scr.onEntry(gameTime);
	}
	@Override
	public void onExit(GameTime gameTime) {
		scr.onExit(gameTime);
	}

	@Override
	public void update(GameTime gameTime) {
		scr.update(gameTime);
	}
	@Override
	public void draw(GameTime gameTime) {
		scr.draw(gameTime);
	}

}
