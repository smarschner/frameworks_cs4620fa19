package cs4620.util;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

/**
 * A base class for cross-platform OpenGL applications that only require a single window.  To create
 * an app, extend this class, override at least the <code>init</code> and <code>draw</code> methods,
 * and provide a <code>main</code> method like this: 
 * <p>
 * <code>
 * public static void main(String[] args) {
 *		new MyOpenGLApp().run();
 * }
 * </code>
 * <p>
 * This class is intended to hide many of the details of LWJGL, a package that provides Java bindings 
 * for several graphics libraries, and GLFW, one of those libraries, which provides a cross-platform way to 
 * create windows with OpenGL contexts, so that the user of this class only needs to worry about making
 * OpenGL calls.
 * <p>
 * The implementation is directly based on the HelloWorld example from the LWJGL documentation 
 * (https://www.lwjgl.org/guide).
 * 
 * @author srm
 * @author LWJGL project
 */
/**
 * @author srm
 *
 */
public abstract class SimpleOpenGLApp {

    /**
     * Perform all one-time initialization of the OpenGL state.  Typical examples include
     * enabling or disabling features, allocating reusable textures and buffers, and 
     * compiling shader programs.
     */
    public abstract void init();


    /**
     * Make any adjustments needed to accommodate a change in the window size.  Typical
     * examples include reallocating textures to match the new window size, resetting the
     * viewport matrix to match the new window size, and adjusting camera aspect ratios 
     * to match the new window aspect ratio.
     * 
     * @param width   New window width in pixels
     * @param height  New window height in pixels
     */
    public abstract void resize(int width, int height);


    /**
     * Draw the window contents into the default framebuffer, making no assumptions about
     * the current framebuffer contents.
     */
    public abstract void draw();


    /**
     * Handle a keyboard event.  This method is intended to be overridden by subclasses, but
     * provides a default behavior of exiting when the ESC key is pressed. 
     * <p>The constants are defined in org.lwjgl.glfw.GLFW.  The underlying API is documented
     * <a href="https://www.glfw.org/docs/latest/input_guide.html#input_keyboard">here</a>.
     *
     * @param key      An integer indicating which key was pressed, in a system-independent way.
     *                 Key codes are documented 
     *                 <a href="https://www.glfw.org/docs/latest/group__keys.html">here</a>.
     * @param scancode A system-dependent integer indicating more precisely which key was 
     *                 pressed, useful for getting input from nonstandard keys.
     * @param action   One of the values GLFW_PRESS, GLFW_RELEASE, or GLFW_REPEAT
     * @param mods     A bit-mask of the values GLFW_MOD_SHIFT, GLFW_MOD_CONTROL, GLFW_MOD_ALT, 
     *                 and/or GLFW_MOD_SUPER, indicating which of these modifiers was down at the
     *                 time of the event.  On MacOS keyboards, Option is reported as ALT, and 
     *                 Command is reported as SUPER.  On Windows keyboards, the Windows key is 
     *                 reported as SUPER.
     */
    public void keyEvent(int key, int scancode, int action, int mods) {
        if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        if ( key == GLFW_KEY_R && action == GLFW_RELEASE )
            glfwSetWindowSize(window, 640,480); // reset window to default size
        if ( key == GLFW_KEY_T && action == GLFW_RELEASE )
            glfwSetWindowSize(window, 640,480); // reset window to default size
    }


    /**
     * Handle a mouse button event.  Button events only describe what happened with the button;
     * if you need to know where the mouse cursor was at the time, you need to keep track of
     * the information coming from mouse motion events.
     * <p>The underlying API is documented 
     * <a href="https://www.glfw.org/docs/latest/input_guide.html#input_mouse_button">here</a>.
     * 
     * @param button Which button, generally GLFW_MOUSE_BUTTON_LEFT or ..._RIGHT.
     * @param action Which way the transition was, GLFW_PRESS or GLFW_RELEASE
     * @param mods   A bit-mask of modifier keys; same as <code>keyEvent</code>.
     */

    public void mouseButtonEvent(int button, int action, int mods) {
        /*
           int state = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT);
           if (state == GLFW_PRESS)
           mouseMotionFlag = 1;
           if (state == GLFW_RELEASE)
           mouseMotionFlag = 0;
           */
    }	

    /**
     * Handle a mouse motion event.  Motion events are generated rapidly whenever the mouse
     * cursor is changing position.  
     * <p>The underlying API is documented
     * <a href="https://www.glfw.org/docs/latest/input_guide.html#cursor_pos">here</a>.
     * 
     * @param xpos The horizontal position, measured in screen coordinates relative to 
     *             the left edge of the window content area.
     * @param ypos The vertical position, measured in screen coordinates relative to 
     *             the top edge of the window content area.
     */
    public void mouseMotionEvent(double xpos, double ypos) {

    }	

    /**
     * Handle a scroll event.  Scroll events are generated by mouse wheels, trackpad 
     * gestures, and some other input devices.  
     * <p>The underlying API is documented
     * <a href="https://www.glfw.org/docs/latest/input_guide.html#scrolling">here</a>.
     * 
     * @param xoffset The horizontal scroll offset.
     * @param yoffset The vertical scroll offset.
     */
    public void scrollEvent(double xoffset, double yoffset) {

    }

    /**
     * The main entry point.  Call this method on a new instance to start the app.
     * This method does not return until the app exits.
     */
    public void run() {
        System.out.println("Using LWJGL " + Version.getVersion() + "!");

        initGLFW();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    ///
    // Implementation
    ///

    // The GLFW window handle
    private long window;

    /**
     * Create a window using GLFW.
     */
    private void initGLFW() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Request the right kind of OpenGL context
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        // Create the window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            keyEvent(key, scancode, action, mods);
        });

        // Set up a mouse button callback.
        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            mouseButtonEvent(button, action, mods);
        });

        // Set up a mouse button callback.
        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            mouseMotionEvent(xpos, ypos);
        });

        // Set up a scroll callback.
        glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
            scrollEvent(xoffset, yoffset);
        });

        // Set up a callback for framebuffer size changes.
        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            resize(width, height);
            draw();			
            glfwSwapBuffers(window); // swap the color buffers
        });

    }

    /**
     * The main event loop of the app.
     */
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        init();

        // Cause an initial resize
        try ( MemoryStack stack = MemoryStack.stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            glfwGetFramebufferSize(window, pWidth, pHeight);

            resize(pWidth.get(0), pHeight.get(0));
        }

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            draw();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

}
