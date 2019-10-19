package interactive;
import egl.math.Vector2;
import egl.math.Vector2i;

/**
 * An interface for objects that respond to user input, including
 * keystrokes and mouse movements.
 * @author eschweickart
 *
 */
public interface Interactive {

	/**
	 * Respond to a scroll event.
	 * The default implementation ignores the event.
	 * @param p The pixel position of the mouse in the window.
	 * @param rel The subpixel relative motion of the scrolling event.
	 * @param modifiers
	 * See <a href="http://www.glfw.org/docs/latest/group__mods.html">GLFW Modifier Key Flags</a>.
	 * @return <code>false</code> if the event is ignored; <code>true</code> otherwise.
	 */
	default boolean scrollEvent(Vector2i p, Vector2 rel, int modifiers) {
		return false;
	}

	/**
	 * Respond to a keyboard event.
	 * The default implementation ignores the event.
	 * @param key
	 * See <a href="http://www.glfw.org/docs/latest/group__keys.html">GLFW Keyboard Keys</a>.
	 * @param scancode The scancode for the pressed key.
	 * @param action One of {@code GLFW_PRESS}, {@code GLFW_REPEAT} or {@code GLFW_RELEASE}.
	 * @param modifiers
	 * See <a href="http://www.glfw.org/docs/latest/group__mods.html">GLFW Modifier Key Flags</a>.
	 * @return <code>false</code> if the event is ignored; <code>true</code> otherwise.
	 */
	default boolean keyboardEvent(int key, int scancode, int action,
			int modifiers) {
		return false;
	}

	/**
	 * Respond to a mouse button event.
	 * The default implementation ignores the event.
	 * @param p The pixel position of the mouse in the window.
	 * @param button
	 * See <a href="http://www.glfw.org/docs/latest/group__buttons.html">GLFW Mouse Buttons</a>.
	 * @param down <code>true</code> for a mouse button press event, <code>false</code> for
	 * a mouse button release event.
	 * @param modifiers
	 * See <a href="http://www.glfw.org/docs/latest/group__mods.html">GLFW Modifier Key Flags</a>.
	 * @return <code>false</code> if the event is ignored; <code>true</code> otherwise.
	 */
	default boolean mouseButtonEvent(Vector2i p, int button, boolean down,
			int modifiers) {
		return false;
	}

	/**
	 * Respond to a mouse drag event.
	 * This fires when the cursor moves and either {@code GLFW_MOUSE_BUTTON_RIGHT}
	 * or {@code GLFW_MOUSE_BUTTON_LEFT} is held down.
	 * The default implementation ignores the event.
	 * @param p The pixel position of the mouse in the window.
	 * @param rel The relative movement of the mouse since the last event in pixels.
	 * @param button One of {@code GLFW_MOUSE_BUTTON_RIGHT}
	 * or {@code GLFW_MOUSE_BUTTON_LEFT}.
	 * @param modifiers
	 * See <a href="http://www.glfw.org/docs/latest/group__mods.html">GLFW Modifier Key Flags</a>.
	 * @return <code>false</code> if the event is ignored; <code>true</code> otherwise.
	 */
	default boolean mouseDragEvent(Vector2i p, Vector2i rel, int button,
			int modifiers) {
		return false;
	}
}
