package interactive;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import org.lwjgl.system.MemoryStack;

import egl.math.Vector2;
import egl.math.Vector2i;

/**
 * An object that dispatches user input, including keystrokes
 * and mouse events, to objects that implement the Interactive
 * interface.
 *
 * @author eschweickart
 *
 */
public class InputHandler {
	private Vector2i lastCursorPos = new Vector2i();

	/**
	 * The list of active Iteractives.
	 * When an event is received, this list is traversed in order, calling the
	 * appropriate event handling methods, until the first list member responds
	 * to the event.
	 */
	public ArrayList<Interactive> interactives = new ArrayList<>();

	/**
	 * Add an Interactive to the back of the interactive list.
	 * @param i The Interactive to be added.
	 */
	public void addInteractive(Interactive i) {
		interactives.add(i);
	}

	/**
	 * Creates an empty input handler.
	 * This method can be called before GLFW has been initialized.
	 * Call {@link registerCallbacks} before using.
	 */
	public InputHandler() { }

	/**
	 * Creates an input handler. GLFW must be initialized before calling this method!
	 * @param window The GLFW window id.
	 */
	public InputHandler(long window) {
		registerCallbacks(window);
	}

	/**
	 * Registers GLFW callback events. GLFW must be initialized before calling this method!
	 * @param window The GLFW window id.
	 */
	public void registerCallbacks(long window) {
		// Register GLFW callbacks
		glfwSetScrollCallback(window, (long lWindow, double xoffset, double yoffset) -> {
			double xpos = 0.0; double ypos = 0.0;
			try (MemoryStack stack = stackPush()) {
				DoubleBuffer pXPos = stack.mallocDouble(1);
				DoubleBuffer pYPos = stack.mallocDouble(1);

				glfwGetCursorPos(lWindow, pXPos, pYPos);
				xpos = pXPos.get(0);
				ypos = pYPos.get(0);
			}
			Vector2i p = new Vector2i((int)xpos, (int)ypos);
			Vector2 rel = new Vector2((float)xoffset, (float)yoffset);
			int modifiers = getModifiers(lWindow);
			for (Interactive i : interactives) {
				if (i.scrollEvent(p, rel, modifiers)) break;
			}
		});

		glfwSetKeyCallback(window, (long lWindow, int key, int scancode, int action, int modifiers) -> {
			for (Interactive i : interactives) {
				if (i.keyboardEvent(key, scancode, action, modifiers)) break;
			}
		});

		glfwSetMouseButtonCallback(window, (long lWindow, int button, int action, int modifiers) -> {
			double xpos = 0.0; double ypos = 0.0;
			try (MemoryStack stack = stackPush()) {
				DoubleBuffer pXPos = stack.mallocDouble(1);
				DoubleBuffer pYPos = stack.mallocDouble(1);

				glfwGetCursorPos(lWindow, pXPos, pYPos);
				xpos = pXPos.get(0);
				ypos = pYPos.get(0);
			}
			Vector2i p = new Vector2i((int)xpos, (int)ypos);

			boolean down = action == GLFW_PRESS;

			for (Interactive i : interactives) {
				if (i.mouseButtonEvent(p, button, down, modifiers)) break;
			}
		});

		glfwSetCursorPosCallback(window, (long lWindow, double xPos, double yPos) -> {
			boolean leftPressed = glfwGetMouseButton(lWindow, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;
			boolean rightPressed = glfwGetMouseButton(lWindow, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS;
			if (leftPressed || rightPressed) {
				Vector2i p = new Vector2i((int)xPos, (int)yPos);
				Vector2i rel = p.clone().sub(lastCursorPos);
				int button = leftPressed ? GLFW_MOUSE_BUTTON_LEFT : GLFW_MOUSE_BUTTON_RIGHT;
				int modifiers = getModifiers(lWindow);
				for (Interactive i : interactives) {
					if (i.mouseDragEvent(p, rel, button, modifiers)) break;
				}
			}

			lastCursorPos.set((int)xPos, (int)yPos);
		});
	}

	private static int getModifiers(long window) {
		int modifiers = 0;
		if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS ||
				glfwGetKey(window, GLFW_KEY_RIGHT_SHIFT) == GLFW_PRESS) {
			modifiers |= GLFW_MOD_SHIFT;
		}
		if (glfwGetKey(window, GLFW_KEY_LEFT_ALT) == GLFW_PRESS ||
				glfwGetKey(window, GLFW_KEY_RIGHT_ALT) == GLFW_PRESS) {
			modifiers |= GLFW_MOD_ALT;
		}
		if (glfwGetKey(window, GLFW_KEY_LEFT_SUPER) == GLFW_PRESS ||
				glfwGetKey(window, GLFW_KEY_RIGHT_SUPER) == GLFW_PRESS) {
			modifiers |= GLFW_MOD_SUPER;
		}
		if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS ||
				glfwGetKey(window, GLFW_KEY_RIGHT_CONTROL) == GLFW_PRESS) {
			modifiers |= GLFW_MOD_CONTROL;
		}
		return modifiers;
	}
}
