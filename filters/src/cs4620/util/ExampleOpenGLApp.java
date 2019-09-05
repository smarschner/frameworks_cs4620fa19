package cs4620.util;

import static org.lwjgl.opengl.GL33.*;

/**
 * An example of how to use SimpleOpenGLApp
 * 
 * @author srm
 */
public class ExampleOpenGLApp extends SimpleOpenGLApp {

    @Override
    public void init() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println(String.format("Resize: %d %d", width, height));
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void keyEvent(int key, int scancode, int action, int mods) {
        System.out.println(String.format("key event: key %d scancode %d action %d mods %x", 
                    key, scancode, action, mods));
    }

    @Override
    public void mouseButtonEvent(int button, int action, int mods) {
        System.out.println(String.format("mouse button event: button %d action %d mods %x", 
                    button, action, mods));
    }

    @Override
    public void mouseMotionEvent(double xpos, double ypos) {
        System.out.println(String.format("mouse motion event: xpos %.2f ypos %.2f)", 
                    xpos, ypos));
    }

    @Override
    public void scrollEvent(double xoffset, double yoffset) {
        System.out.println(String.format("scroll event: xoffset %.2f yoffset %.2f", 
                    xoffset, yoffset));
    }

    public static void main(String[] args) {
        new ExampleOpenGLApp().run();
    }

}
