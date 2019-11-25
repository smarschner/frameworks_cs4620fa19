package blister;

import egl.math.Vector2;

/**
 * Interface For A Window Screen's Set Of Logic
 * @author Cristian
 *
 */
public interface IGameScreen {
	/**
	 * Get This Screen's Desired State For Post-Frame Operations
	 * @return This Screen's Desired State
	 */
	ScreenState getState();
	
	/**
	 * 
	 * @return This Screen's Index Into List Of Screens
	 */
    int getIndex();
    /**
     * Get Index Of A Screen After This One
     * @return Index Of Desired Next Screen
     */
    int getNext();
    /**
     * Get Index Of A Screen Before This
     * @return Index Of Desired Previous Screen
     */
    int getPrevious();
    
    /**
     * 
     * @return This Screen's Root Window
     */
    MainGame getParentGame();
    /**
     * 
     * @return The Size Of The Window
     */
    Vector2 getViewSize();

    /**
     * Screen Is Given Important Information
     * @param pgame Parent Application
     * @param index Location In The List Of Screens
     */
    void setParentGame(MainGame pgame, int index);

    /**
     * Called On The Initialization Of The Screen
     */
    void build();
    /**
     * Called When Window Is To Be Destroyed
     * @param gameTime Current Time From Start Of Program
     */
    void destroy(GameTime gameTime);

    /**
     * This Should Set The Window To A Runnable State
     */
    void setRunning();

    /**
     * Called When Screen Becomes The Window's Focus
     * @param gameTime Current Time From Start Of Program
     */
    void onEntry(GameTime gameTime);
    /**
     * Called When Screen Has Its Focus Lost
     * @param gameTime Current Time From Start Of Program
     */
    void onExit(GameTime gameTime);

    /**
     * Called When Screen Is Meant To Be Updating (Every Frame)
     * @param gameTime Current Time From Start Of Program
     */
    void update(GameTime gameTime);
    /**
     * Called When Screen Is Meant To Be Drawing (Every Frame)
     * @param gameTime Current Time From Start Of Program
     */
    void draw(GameTime gameTime);
}