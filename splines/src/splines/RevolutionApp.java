package splines;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import common.Scene;
import splines.form.RevolutionControlFrame;
import splines.form.RevolutionEditScreen;
import splines.form.RevolutionSplineScreen;
import egl.math.Vector2;
import ext.java.Parser;
import blister.FalseFirstScreen;
import blister.MainGame;
import blister.ScreenList;

public class RevolutionApp extends MainGame {
	/* +-------+-------+
	 * |       |       |
	 * |   A   |   B   |
	 * |       |       |
	 * +-------+-------+
	 * 
	 * A: The left spline
	 * B: A revolved around an axis
	 * 
	 * Valid control points live in the range x,y in [-1,1]
	 * 
	 *    (-1,1)         (1,1)
	 *       +------+------+
	 *       |      |      |
	 *       |      |      |
	 *       +----(0,0)----+
	 *       |      |      |
	 *       |      |      |
	 *       +------+------+
	 *     (-1,1)        (1,-1)
	 *     
	 * This makes drawing with OpenGL very simple.
	 */
	public ArrayList<Vector2> leftPoints;
	
	// Main spline display
	public Scene revolutionScene;
	public RevolutionSplineScreen scrView;
	
	// Options frame
	public RevolutionControlFrame options;
	// Dimensions for ControlFrame stored here so we can construct the display size correctly
	private static int min_width= 340;
	public static Dimension tolSlideDim= new Dimension(100,300),
	                        tolPanelDim= new Dimension(min_width, 325),
	                        modeDim= new Dimension(min_width, 150),
	                        lwsbDim= new Dimension(min_width, 75),
	                        configDim= new Dimension(min_width, 150),
	                        optionsDim= new Dimension(min_width,
	        		                                  tolPanelDim.height +
	        		                                      modeDim.height +
	        		                                      lwsbDim.height +
	        		                                      configDim.height),
	        		        screenDim= Toolkit.getDefaultToolkit().getScreenSize();
	
	public RevolutionApp() {
		super("CS 4620: Splines", screenDim.width-optionsDim.width, optionsDim.height);
		init_splines();
		init_display();
	}
	
	private void init_splines() {
		leftPoints= new ArrayList<Vector2>();
		
		int numInitialControlPoints = 5;
		double incr= (2*Math.PI) / numInitialControlPoints;
		double center_x = 0.5, center_y = 0.5, scale = 0.5;
		// add initial control points to A
		for (int i=0; i<numInitialControlPoints; i++) {
			// A starts closed, even spread along circle
			double x= Math.cos(incr * i + Math.PI) * scale + center_x;
			double y= Math.sin(incr * i + Math.PI) * scale + center_y;
			leftPoints.add(new Vector2((float)x, (float)y));
		}
		
		File f= new File("data/scenes/SplineScene.xml");
		if(f.exists()) {
			Object o= (new Parser()).parse(f.getAbsolutePath(), Scene.class);
			revolutionScene= (Scene)o;
		} else {
			System.err.println("Could not find \"data/scenes/SplineScene.xml\"!!! Please find it and put it back!");
			revolutionScene= new Scene();	
		}
	}
	
	private void init_display() {
		options= new RevolutionControlFrame("Options...", this);
	}

	@Override
	protected void buildScreenList() {
		scrView = new RevolutionSplineScreen();
		screenList = new ScreenList(this, 0,
			new FalseFirstScreen(1),
			scrView,
			new RevolutionEditScreen()
			);
	}

	@Override
	protected void fullInitialize() {}

	@Override
	protected void fullLoad() {}
	
	public static void main(String[] args) {
		RevolutionApp app= new RevolutionApp();
		app.run();
		app.dispose();
	}
}
