package blister;

import java.util.Arrays;

/**
 * Container That Holds A List Of Runnable Screens
 * @author Cristian
 *
 */
public class ScreenList {
	/**
	 * Default Screen Start Index
	 */
	public static final int NO_START_SELECTED = -1;
	/**
	 * An Appropriate Index For Absence Of A Screen
	 */
    public static final int NO_SCREEN = -2;

    /**
     * Parent Game Window
     */
    protected MainGame game;

    /**
     * Array Of Screens
     */
    protected IGameScreen[] screens;
    /**
     * Index Of Screen Currently Being Run
     */
    protected int current = NO_START_SELECTED;

    /**
     * 
     * @return The Current Screen (null If None)
     */
    public IGameScreen getCurrent() {
        try {
            return screens[current];
        }
        catch(Exception e) {
            return null;
        }
     }
    /**
     * 
     * @return The Next Screen (null If None)
     */
    public IGameScreen getNext() {
        try {
            current = getCurrent().getNext();
            return getCurrent();
        }
        catch(Exception e) {
            return null;
        }
    }
    /**
     * 
     * @return The Previous Screen (null If None)
     */
    public IGameScreen getPrevious() {
        try {
            current = getCurrent().getPrevious();
            return getCurrent();
        }
        catch(Exception e) {
            return null;
        }
    }

    /**
     * Create This As A Child Of A Window
     * @param game Parent
     */
    public ScreenList(MainGame game) {
    	screens = new IGameScreen[0];
        this.game = game;
    }
    /**
     * Create This As A Child Of A Window With Screen Initialization Information
     * @param game Parent
     * @param startScreen Index Of The Starting Screen
     * @param screens List Of Screens To Be Added (In Order Of Arguments)
     */
    public ScreenList(MainGame game, int startScreen, IGameScreen... screens) {
    	this(game);
        setStartScreen(startScreen);
        addScreens(screens);
    }

    /**
     * Set This List's Starting Screen Index
     * @param s Index Into Screen List
     */
    public void setStartScreen(int s) {
        if(current == NO_START_SELECTED) {
            current = s;
        }
    }
    /**
     * Append Screens Into This List
     * @param s List Of Screens
     */
    public void addScreens(IGameScreen... s) {
        //Copy Over The Screens
        int l;
        if(screens == null) {
            l = 0;
            screens = s;
        }
        else {
            l = screens.length;
            screens = Arrays.copyOf(screens, screens.length + s.length);
            System.arraycopy(s, 0, screens, l, s.length);
        }

        //Build The Added Screens
        for(int i = l; i < screens.length; i++) {
            screens[i].setParentGame(game, i);
            screens[i].build();
        }
    }

    /**
     * Destroy All The Screens In This List
     * @param gameTime Time Of Destruction
     */
    public void destroy(GameTime gameTime) {
        for(IGameScreen screen : screens) {
            screen.destroy(gameTime);
        }
    }
}
