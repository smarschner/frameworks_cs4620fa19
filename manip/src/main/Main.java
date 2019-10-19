package main;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import camera.*;
import egl.math.*;
import gl.*;
import interactive.InputHandler;
import interactive.Interactive;
import manip.*;
import mesh.MeshData;

public class Main {
	InputHandler inputHandler;
	CameraController camController;
	RenderContext renderController;
	PickingProgram pickingProgram;
	RenderObject manipulationTarget;
	ArrayList<Renderable> activeManipulators = new ArrayList<>();
	// The window handle
	private long window;

	private int windowWidth = 800;
	private int windowHeight = 600;


	/**
	 * Put a manipulator in the scene
	 * @param obj The referenced object
	 * @param cls the class of manipulator to add
	 */
	private <T extends Manipulator> void addManipulators(RenderObject obj, Class<T> cls) {
		for (Renderable r : activeManipulators)
			renderController.removeObj(r);
		activeManipulators.clear();

		Constructor<T> c;
		try {
			c = cls.getDeclaredConstructor(RenderObject.class, ManipulatorAxis.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return;
		}

		for (ManipulatorAxis a : ManipulatorAxis.values()) {
			Manipulator m;
			try {
				m = c.newInstance(obj, a);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				continue;
			}
			renderController.addObj(m);
			activeManipulators.add(m);
		}
	}

	/**
	 * load the referenced object
	 */
	private void setReferenceObj(String objName) {
		MeshData md = new MeshData();
		md.loadOBJ(objName);
		RenderMesh rm = new RenderMesh(md);
		manipulationTarget = new RenderObject(rm);
		renderController.addObj(manipulationTarget);
	}

	private String readShaderFile(String shaderFileName) {
		InputStream is;
		try {
			is = new FileInputStream(shaderFileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
		StringBuilder sb = new StringBuilder();
		try (BufferedReader buf = new BufferedReader(new InputStreamReader(is))) {
			String line = buf.readLine();
			while(line != null){
				sb.append(line).append("\n"); line = buf.readLine();
			}
		} catch (IOException e) {
			throw new RuntimeException("invalid file " + shaderFileName);
		}
		return sb.toString();
	}

	/**
	 * The entry point of the application
	 */
	public void run(String objName) throws Exception {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		init();
		registerCallbacks();
		loop(objName);
		cleanup();
		
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void registerCallbacks() {
		inputHandler = new InputHandler(window);

		inputHandler.addInteractive(new Interactive() {
			Manipulator underCursor = null;

			@Override
			public boolean mouseButtonEvent(Vector2i p, int button, boolean down, int modifiers) {
				if (down) {
					Renderable pickedObject = pickingProgram.getRenderableUnderCursor(activeManipulators, camController.getCamera(), p,
							windowWidth, windowHeight);
					System.out.print("\n pos:" + p);
					System.out.print("\n PickedObjected: first: " + (pickedObject == null) + " second: " + (pickedObject instanceof Manipulator));
					if (pickedObject == null || !(pickedObject instanceof Manipulator)) {
						System.out.print("\n PickedObjected: first: " + (pickedObject == null) + " second: " + (pickedObject instanceof Manipulator));
						System.out.print("\n returning false");
						return false;
					}
					System.out.print("\n Selected !");
					underCursor = (Manipulator)pickedObject;
					underCursor.setSelected(true);
					return true;
				} else {
					if (underCursor == null) return false;
					underCursor.setSelected(false);
					underCursor = null;
					return true;
				}
			}

			@Override
			public boolean mouseDragEvent(Vector2i p, Vector2i rel, int button, int modifiers) {
				if (underCursor == null) return false;
				Vector2 lastMousePos = new Vector2((p.x - rel.x) / (float)windowWidth, (windowHeight - 1 - p.y + rel.y) / (float)windowHeight);
				Vector2 curMousePos = new Vector2(p.x / (float)windowWidth, (windowHeight - 1 - p.y) / (float)windowHeight);
				lastMousePos.mul(2.0f).sub(1.0f);
				curMousePos.mul(2.0f).sub(1.0f);
				underCursor.applyTransformation(lastMousePos, curMousePos, camController.getCamera().getViewProjectionMatrix());
				return true;
			}
		});

		inputHandler.addInteractive(camController);

		// Default window event handling. Putting this last means other Interactives
		// take priority.
		inputHandler.addInteractive(new Interactive() {
			@Override
			public boolean keyboardEvent(int key, int scancode, int action,
                    int modifiers) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
					glfwSetWindowShouldClose(window, true);
					return true;
				} else if (key == GLFW_KEY_T && action == GLFW_PRESS) {
					addManipulators(manipulationTarget, TranslationManipulator.class);
					return true;
				} else if (key == GLFW_KEY_R && action == GLFW_PRESS) {
					addManipulators(manipulationTarget, RotationManipulator.class);
					return true;
				} else if (key == GLFW_KEY_S && action == GLFW_PRESS) {
					addManipulators(manipulationTarget, ScaleManipulator.class);
					return true;
				}
				return false;
			}
		});

		glfwSetWindowSizeCallback(window, (long window, int width, int height) -> {
			windowWidth = width;
			windowHeight = height;
			camController.getCamera().setAspectRatio(((float)width)/(float)height);
		});
	}

	private void init() throws Exception {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

		// Create the window
		window = glfwCreateWindow(windowWidth, windowHeight, "CS4620A3", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);

		// Set up camera and controller
		float aspectRatio = ((float) windowWidth) / ((float) windowHeight);
		Camera camera = new PerspectiveCamera(new Vector3(10.0f, 0.0f, 0.0f), new Vector3(0.0f, 0.0f, 0.0f), new Vector3(0.0f, 1.0f, 0.0f),
				aspectRatio, 0.1f, 100.0f, (float)Math.PI / 6.0f);
		camController = new MouseCC(camera);
	}

	private void handleCallback() {
		glfwWaitEvents();
	}

	private void render() throws Exception{
		glClearColor(0.43f, 0.43f, 0.5f, 1.0f); // Set the clear color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		glEnable(GL_DEPTH_TEST);
		
		renderController.render();
//		renderGrid.render();
		glfwSwapBuffers(window); // swap the color buffers
	}

	private void loop(String objName) throws Exception {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		glInit(objName);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			handleCallback();
			render();
		}
	}

	/**
	 * Initialize opengl related objects
	 */
	private void glInit(String objName) throws Exception {
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));

		String vertexShaderFname = "shaders/Diffuse.vert";
		String fragmentShaderFname = "shaders/Diffuse.frag";
		List<String> uniformNames = Arrays.asList(new String[] {"worldMat", "viewProjMat", "color", "worldMatIT", "mode"});
		ShaderProgram p = new ShaderProgram(uniformNames);
		p.createVertexShader(readShaderFile(vertexShaderFname));
		p.createFragmentShader(readShaderFile(fragmentShaderFname));
		renderController = new RenderContext(p, camController.getCamera());
		setReferenceObj(objName);
		renderController.init();

		int fbwidth[] = new int[1], fbheight[] = new int[1];
		glfwGetFramebufferSize(window, fbwidth, fbheight);
		pickingProgram = new PickingProgram(windowWidth, windowHeight, ((float)fbwidth[0])/((float)windowWidth));
	}

	private void cleanup() {
		renderController.cleanup();
		pickingProgram.cleanup();
	}

	public static void main(String[] args) {
		try {
			new Main().run(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Single command-line argument required: OBJ mesh to display.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}