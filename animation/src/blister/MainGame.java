package blister;

import java.io.OutputStreamWriter;
import java.util.Calendar;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;

import blister.input.KeyPressEventArgs;
import blister.input.KeyboardEventDispatcher;
import blister.input.KeyboardKeyEventArgs;
import blister.input.MouseButtonEventArgs;
import blister.input.MouseEventDispatcher;
import blister.input.MouseMoveEventArgs;
import blister.input.MouseState;
import blister.input.MouseWheelEventArgs;
import egl.GLDiagnostic;
import egl.GLError;
import egl.GLState;
import egl.IDisposable;
import ext.csharp.Event;
import ext.csharp.EventArgs;

/**
 * Basic Functionality Class To Get An Application Up And Running
 * @author Cristian
 *
 */
public abstract class MainGame implements IDisposable {
	/**
	 * Window Resize Event Struct
	 * @author Cristian
	 *
	 */
	public static class WindowResizeArgs extends EventArgs {
		public int x, y, width, height;

		public WindowResizeArgs(int _x, int _y, int _w, int _h) {
			x = _x;
			y = _y;
			width = _w;
			height = _h;
		}
	}

	// Event Instances
	private final KeyPressEventArgs eKP;
	private final KeyboardKeyEventArgs eKK;
	private final MouseState ms;
	private final MouseButtonEventArgs eMB;
	private final MouseWheelEventArgs eMW;
	private final MouseMoveEventArgs eMM;
	private final WindowResizeArgs eWR;

	/**
	 * List Of Screens That Must Be Built
	 */
	protected ScreenList screenList = null;
	/**
	 * Screen Currently Running In This Window
	 */
	protected IGameScreen screen;

	/**
	 * Flag For If This Window Should Still Continue Updating
	 */
	private boolean isRunning = false;
	/**
	 * Time Information
	 */
	private long lastNS = 0;
	/**
	 * Instances Of The Game's Current Time
	 */
	private GameTime lastTime, curTime;
	/**
	 * OpenGL Context
	 */
	private ContextAttribs glContext = new ContextAttribs(2, 1);
	/**
	 * OpenGL Backbuffer Pixel Format
	 */
	private org.lwjgl.opengl.PixelFormat glPixelFormat = new PixelFormat(8, 24, 8);
	
	/**
	 * Event Called When The Window Resizes
	 */
	public final Event<WindowResizeArgs> OnWindowResize = new Event<>(this);

	/**
	 * Constructor That Creates A Window For The Game
	 * @param title Window Title
	 * @param w Desired Window Width
	 * @param h Desired Window Height
	 */
	public MainGame(String title, int w, int h, ContextAttribs context, org.lwjgl.opengl.PixelFormat pixelFormat) {
		Display.setVSyncEnabled(true);
		// Display.setResizable(true);
		Display.setTitle(title);

		eKP = new KeyPressEventArgs();
		eKK = new KeyboardKeyEventArgs();
		ms = new MouseState();
		eMB = new MouseButtonEventArgs(ms);
		eMW = new MouseWheelEventArgs(ms);
		eMM = new MouseMoveEventArgs(ms);
		eWR = new WindowResizeArgs(0, 0, w, h);

		curTime = new GameTime();
		lastTime = new GameTime();
		
		if(context != null) glContext = context;
		if(pixelFormat != null) glPixelFormat = pixelFormat;
	}
	public MainGame(String title, int w, int h) {
		this(title, w, h, null, null);
	}
	/**
	 * Disposes Of All Screens And Exits The Application
	 */
	public void dispose() {
		if(screen != null) {
			screen.onExit(lastTime);
		}
		screenList.destroy(lastTime);
		exit();
	}
	/**
	 * Destroys The Window And Stops The Program
	 */
	public void exit() {
		Display.destroy();
		GLDiagnostic.dispose();
		System.exit(0);
	}

	/**
	 * Initializes Window State And Prepares It For Running
	 */
	protected void init() {
		fullInitialize();

		createDisplay();
		initDiagnostics();

		fullLoad();
		buildScreenList();
		screen = screenList.getCurrent();
		if(screen == null) {
			exit();
			return;
		}
		else {
			screen.setRunning();
			screen.onEntry(lastTime);
		}

		lastNS = System.nanoTime();
	}
	/**
	 * Creates The Display And OpenGL Context
	 */
	private void createDisplay() {
		try{
			Display.setDisplayMode(new DisplayMode(eWR.width, eWR.height));
			Display.create(glPixelFormat, glContext);
			GLState.enableAll();
			
			GL11.glViewport(0, 0, eWR.width, eWR.height);
			GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
			
			ms.Refresh();
			eKK.Refresh();
		}
		catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}
	/**
	 * Writes General Diagnostic Information
	 */
	private void initDiagnostics(){
		GLError.setErrorLog(new OutputStreamWriter(System.err), false);
		GLDiagnostic.init();
		GLDiagnostic.writeln("TIME:                         " + Calendar.getInstance().getTime());
		GLDiagnostic.writeln("");
		GLDiagnostic.writeln("=== Java System Properties ===");
		GLDiagnostic.writeln("java.version:                 " + System.getProperty("java.version"));
		GLDiagnostic.writeln("java.runtime.name:            " + System.getProperty("java.runtime.name"));
		GLDiagnostic.writeln("java.vm.name:                 " + System.getProperty("java.vm.name"));
		GLDiagnostic.writeln("java.vm.version:              " + System.getProperty("java.vm.version"));
		GLDiagnostic.writeln("os.name:                      " + System.getProperty("os.name"));
		GLDiagnostic.writeln("os.version:                   " + System.getProperty("os.version"));
		GLDiagnostic.writeln("");
		GLDiagnostic.writeln("====== OpenGL Properties =====");
		GLDiagnostic.writeln("GL_VENDOR:                    " + GL11.glGetString(GL11.GL_VENDOR));
		GLDiagnostic.writeln("GL_VERSION:                   " + GL11.glGetString(GL11.GL_VERSION));
		GLDiagnostic.writeln("GL_RENDERER:                  " + GL11.glGetString(GL11.GL_RENDERER));
		GLDiagnostic.writeln("GL_SHADING_LANGUAGE_VERSION:  " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
		GLDiagnostic.writeln("");
	}

	/**
	 * Updates Input From The Keyboard/Mouse And Sends Events
	 */
	protected void checkInput() {
		// Check Display Input
		if(Display.wasResized()) {
			eWR.x = Display.getX();
			eWR.y = Display.getY();
			eWR.width = Display.getWidth();
			eWR.height = Display.getHeight();
			OnWindowResize.Invoke(eWR);
		}
		if(Display.isCloseRequested())
			isRunning = false;

		// Keyboard Input Queue
		while(Keyboard.next()) {
			// Key Events
			eKK.key = Keyboard.getEventKey();
			eKK.isRepeat = Keyboard.isRepeatEvent();
			if(Keyboard.getEventKeyState()) {
				eKK.keyStates[eKK.key] = true;
				KeyboardEventDispatcher.eventInput_KeyDown(eKK);
			}
			else {
				eKK.keyStates[eKK.key] = false;
				KeyboardEventDispatcher.eventInput_KeyUp(eKK);
			}

			// Text Events
			eKP.KeyChar = Keyboard.getEventCharacter();
			if(eKP.KeyChar >= 32 && eKP.KeyChar <= 126)
				KeyboardEventDispatcher.eventInput_CharEntered(eKP);
			else
				eKP.KeyChar = 0;
		}

		// Mouse Input Queue
		while(Mouse.next()) {
			ms.x = Mouse.getEventX();
			ms.y = Mouse.getEventY();

			// Mouse Button
			eMB.button = 1 << Mouse.getEventButton();
			if(Mouse.getEventButtonState()) {
				if(!ms.IsButtonDown(eMB.button)) {
					ms.Buttons |= eMB.button;
					MouseEventDispatcher.EventInput_MouseButton(eMB, true);
					continue;
				}
			}
			else {
				if(ms.IsButtonDown(eMB.button)) {
					ms.Buttons &= ~eMB.button;
					MouseEventDispatcher.EventInput_MouseButton(eMB, false);
					continue;
				}
			}

			// Mouse Wheel
			eMW.ScrollChange = Mouse.getEventDWheel();
			if(eMW.ScrollChange != 0) {
				ms.Scroll += eMW.ScrollChange;
				MouseEventDispatcher.EventInput_MouseWheel(eMW);
				continue;
			}

			// Mouse Motion
			eMM.dx = Mouse.getEventDX();
			eMM.dy = Mouse.getEventDY();
			if(eMM.dx != 0 || eMM.dy != 0) {
				MouseEventDispatcher.EventInput_MouseMotion(eMM);
				continue;
			}
		}
	}
	/**
	 * Updates The Game Time
	 */
	private void refreshElapsedTime() {
		long ct = System.nanoTime();
		double et = (ct - lastNS) / 1000000000.0;
		lastNS = ct;

		curTime.elapsed = et;
		curTime.total += et;
		lastTime = curTime;
	}
	/**
	 * Frame Update Logic
	 */
	protected void onUpdateFrame() {
		if(screen != null) {
			switch(screen.getState()) {
			case Running:
				screen.update(curTime);
				break;
			case ChangeNext:
				screen.onExit(curTime);
				screen = screenList.getNext();
				if(screen != null) {
					screen.setRunning();
					screen.onEntry(curTime);
				}
				break;
			case ChangePrevious:
				screen.onExit(curTime);
				screen = screenList.getPrevious();
				if(screen != null) {
					screen.setRunning();
					screen.onEntry(curTime);
				}
				break;
			case ExitApplication:
				exit();
				return;
			}
		}
		else {
			exit();
			return;
		}
	}
	/**
	 * Frame Rendering Logic
	 */
	protected void onRenderFrame() {
		if(screen != null && screen.getState() == ScreenState.Running) {
			screen.draw(curTime);
		}
	}

	/**
	 * Initializes And Runs The Window In The Current Thread (Blocking Operation)
	 */
	public void run() {
		init();

		isRunning = true;
		while(isRunning) {
			Display.sync(60);

			checkInput();
			refreshElapsedTime();
			onUpdateFrame();
			onRenderFrame();

			Display.update();
		}
	}

	/**
	 * 
	 * @return The Width Of The Display
	 */
	public int getWidth() {
		return Display.getWidth();
	}
	/**
	 * 
	 * @return The Height Of The Display
	 */
	public int getHeight() {
		return Display.getHeight();
	}

	/**
	 * Create The ScreenList Object And Set It's Screens
	 */
	protected abstract void buildScreenList();
	/**
	 * Code Executed Before Window Is Created
	 */
	protected abstract void fullInitialize();
	/**
	 * Code Executed With An Active OpenGL Context
	 */
	protected abstract void fullLoad();
}