package splines.form;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import blister.GameScreen;
import blister.GameTime;
import blister.MainGame;
import blister.ScreenState;
import blister.MainGame.WindowResizeArgs;
import blister.input.KeyboardEventDispatcher;
import blister.input.KeyboardKeyEventArgs;
import blister.input.MouseButtonEventArgs;
import blister.input.MouseEventDispatcher;
import blister.input.MouseMoveEventArgs;
import common.Mesh;
import common.Scene;
import common.Scene.NameBindMesh;
import common.SceneObject;
import common.event.SceneTransformationEvent;
import gl.GridRenderer;
import gl.RenderCamera;
import gl.RenderController;
import gl.Renderer;
import scene.form.RPMaterialData;
import scene.form.RPMeshData;
import scene.form.RPTextureData;
import splines.CatmullRom;
import splines.RevolutionApp;
import egl.math.Vector2;
import ext.csharp.ACEventFunc;

public class RevolutionSplineScreen extends GameScreen {	
	public static final int NUM_PANELS = 2;
	
	public static float tol1 = .1f;
	
	static SplinePanel[] panels;
	static SplinePanel selectedPanel;
	
	public MeshGenRevolution generator;
	
	Renderer renderer = new Renderer();
	int cameraIndex = 0;
	boolean pick;
	int prevCamScroll = 0;
	boolean wasPickPressedLast = false;
	boolean showGrid = true;

	RevolutionApp app;
	RPMeshData dataMesh;
	RPMaterialData dataMaterial;
	RPTextureData dataTexture;

	RenderController rController;
	RevolutionCameraController camController;
	GridRenderer gridRenderer;
	

	@Override
	public int getNext() {
		return getIndex();
	}
	@Override
	protected void setNext(int next) {
	}

	@Override
	public int getPrevious() {
		return -1;
	}
	@Override
	protected void setPrevious(int previous) {
	}

	@Override
	public void build() {
		app = (RevolutionApp)game;

		renderer = new Renderer();
		panels = new SplinePanel[RevolutionSplineScreen.NUM_PANELS];
		CatmullRom closed = new CatmullRom(app.leftPoints, true, tol1);
		panels[0] = new RevolutionTwoDimSplinePanel(0, closed);
		panels[1] = new RevolutionSplinePanel(1, closed, this);
		
		generator = new MeshGenRevolution();
		generator.setSplineToRevolve(((RevolutionTwoDimSplinePanel) panels[0]).spline);
		generator.setScale(app.options.getScale());
		generator.setSliceTolerance(app.options.getSliceTolerance());
		
		selectedPanel = null;
	}
	@Override
	public void destroy(GameTime gameTime) {
	}
	
	public void newRevolution() {
		Mesh m= new Mesh();
		m.setGenerator(generator);
		app.revolutionScene.addMesh(new NameBindMesh("SWEEP", m));// overwrites previous...

		SceneObject root= app.revolutionScene.objects.get(Scene.ROOT_NODE_NAME);
		root.setMesh("SWEEP");
		root.setMaterial("Sweep");
		app.revolutionScene.sendEvent(new SceneTransformationEvent(root));
	}

	/**
	 * Add Spline Data Hotkeys
	 */
	private final ACEventFunc<KeyboardKeyEventArgs> onKeyPress = new ACEventFunc<KeyboardKeyEventArgs>() {
		@Override
		public void receive(Object sender, KeyboardKeyEventArgs args) {
			switch (args.key) {
			case Keyboard.KEY_G:
				showGrid = !showGrid;
				break;
			case Keyboard.KEY_P:
				newRevolution();
				break;
			default:
				break;
			}
		}
	};
	
	private final ACEventFunc<MouseButtonEventArgs> onMousePress = new ACEventFunc<MouseButtonEventArgs>() {
		@Override
		public void receive(Object sender, MouseButtonEventArgs args) {
			int index = (int) Math.floor(Mouse.getX() / SplinePanel.panelWidth);
			if (index >= 0 && index < panels.length && panels[index] != null) {
				if(panels[index] instanceof RevolutionTwoDimSplinePanel) {
					RevolutionTwoDimSplinePanel p= (RevolutionTwoDimSplinePanel)panels[index];
					if (args.button == 1) {
						p.selectWithMouse(Mouse.getX(), Mouse.getY());
						selectedPanel = panels[index];
					} else if (args.button == 2) {
						int selectedIndex = p.getSelectedWithMouseClick(Mouse.getX(), Mouse.getY());
						if (selectedIndex == -1) {
							p.spline.addControlPoint(p.mouseClickToWorldTransform(Mouse.getX(), Mouse.getY()));
						} else {
							p.spline.removeControlPoint(selectedIndex);
						}
					}
					if(RevolutionControlFrame.REAL_TIME)
						newRevolution();
				} else if(panels[index] instanceof RevolutionSplinePanel) {
					selectedPanel= panels[index];
					((RevolutionSplinePanel) selectedPanel).clickStartedHere= true;
				}
				
				
			}
		}
	};
	
	private final ACEventFunc<MouseMoveEventArgs> onMouseMotion = new ACEventFunc<MouseMoveEventArgs>() {
		@Override
		public void receive(Object sender, MouseMoveEventArgs args) {
			if (selectedPanel instanceof RevolutionTwoDimSplinePanel) {
				((RevolutionTwoDimSplinePanel)selectedPanel).updateSelectedWithMouse(Mouse.getX(), Mouse.getY());
				if(RevolutionControlFrame.REAL_TIME)
					newRevolution();
			}
		}
	};

	private final ACEventFunc<MouseButtonEventArgs> onMouseRelease = new ACEventFunc<MouseButtonEventArgs>() {
		@Override
		public void receive(Object sender, MouseButtonEventArgs args) {
			if(selectedPanel instanceof RevolutionTwoDimSplinePanel) {
				((RevolutionTwoDimSplinePanel) selectedPanel).unselect();
				selectedPanel = null;
				if(RevolutionControlFrame.REAL_TIME)
					newRevolution();
			} else if(selectedPanel instanceof RevolutionSplinePanel) {
				((RevolutionSplinePanel) selectedPanel).clickStartedHere= false;
				selectedPanel= null;
			}
		}
	};

	@Override
	public void onEntry(GameTime gameTime) {		
		cameraIndex = 0;
		app.OnWindowResize.add(new ACEventFunc<MainGame.WindowResizeArgs>() {
			@Override
			public void receive(Object sender, WindowResizeArgs args) {
				// change spline panels
				int width= Display.getWidth();
				int height= Display.getHeight();
				SplinePanel.resize(width / NUM_PANELS, height);
				
				// change rController env viewport to keep aspect ratio
				rController.env.viewportSize.set(width / NUM_PANELS, height);
				
			}
		});
		
		rController = new RenderController(app.revolutionScene, new Vector2(app.getWidth()/NUM_PANELS, app.getHeight()));
		
		renderer.buildPasses(rController.env.root);
		camController = new RevolutionCameraController(app.revolutionScene, rController.env, null, (RevolutionSplinePanel)panels[1]);
		createCamController();
		gridRenderer = new GridRenderer();

		KeyboardEventDispatcher.OnKeyPressed.add(onKeyPress);
		MouseEventDispatcher.OnMousePress.add(onMousePress);
		MouseEventDispatcher.OnMouseMotion.add(onMouseMotion);
		MouseEventDispatcher.OnMouseRelease.add(onMouseRelease);
		
		wasPickPressedLast = false;
		prevCamScroll = 0;
		
		newRevolution();
	}
	
	@Override
	public void onExit(GameTime gameTime) {
		KeyboardEventDispatcher.OnKeyPressed.remove(onKeyPress);
		MouseEventDispatcher.OnMousePress.remove(onMousePress);
		MouseEventDispatcher.OnMouseMotion.remove(onMouseMotion);
		MouseEventDispatcher.OnMouseRelease.remove(onMouseRelease);
		rController.dispose();
	}

	private void createCamController() {
		if(rController.env.cameras.size() > 0) {
			RenderCamera cam = rController.env.cameras.get(cameraIndex);
			camController.camera = cam;
		}
		else {
			camController.camera = null;
		}
	}

	@Override
	public void update(GameTime gameTime) {
		pick = false;
		int curCamScroll = 0;

		if(Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) curCamScroll++;
		if(Keyboard.isKeyDown(Keyboard.KEY_MINUS)) curCamScroll--;
		if(rController.env.cameras.size() != 0 && curCamScroll != 0 && prevCamScroll != curCamScroll) {
			if(curCamScroll < 0) curCamScroll = rController.env.cameras.size() - 1;
			cameraIndex += curCamScroll;
			cameraIndex %= rController.env.cameras.size();
			createCamController();
		}
		prevCamScroll = curCamScroll;

		if(camController.camera != null) {
			camController.update(gameTime.elapsed);
		}

		if(Mouse.isButtonDown(1) || Mouse.isButtonDown(0) && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))) {
			if(!wasPickPressedLast) pick = true;
			wasPickPressedLast = true;
		}
		else wasPickPressedLast = false;

		// View A Different Scene
		if(rController.isNewSceneRequested()) {
			setState(ScreenState.ChangeNext);
		}
	}
	

	@Override
	public void draw(GameTime gameTime) {
		int panelWidth = game.getWidth() / RevolutionSplineScreen.NUM_PANELS;
		int panelHeight = game.getHeight();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		
		GL11.glOrtho(-panelWidth/2,
				      panelWidth/2,
				     -panelHeight/2,
				      panelHeight/2,
				     -1.0, +1.0);
		
		SplinePanel.resize(panelWidth, panelHeight);
		for (SplinePanel sp : RevolutionSplineScreen.panels) {
			if (sp != null) {
				sp.draw();
			}
		}
	}
}
