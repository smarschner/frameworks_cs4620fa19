package cs4620.filters;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import cs4620.util.GLUtils;
import cs4620.util.IOUtils;
import cs4620.util.SimpleOpenGLApp;

/**
 * @author srm
 * @author rc844
 * @author xz649
 */
public class ResampleApp extends SimpleOpenGLApp {

    private int fsqVAO;
    private int copyProgram;
    private int tex;
    private SimpleImage srcImg, dstImg;
    private ResampleEngine resampler = new PixelCopier();
    private double top, bottom, left, right = 0;
    private	double zoom = 0; //zoom in/out rate

    // For mouse dragging
    private double xPrevPos, yPrevPos = 0;
    private boolean leftMouseDown, leftReleased, leftPrev, leftDrag = false;

    // Write to image for testing
    // For now we use the cactus image
    private String finalImgType = "png";
    private String finalImgName = "cactus";
    private String currentFilterName = "_PixelCopy";

    @Override
    public void init() {

        // Set up screen-resolution texture
        // (currently it is actually just the resolution of the input image)
        tex = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, tex);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glBindTexture(GL_TEXTURE_2D, 0);

        srcImg = IOUtils.readImage("data/cactus.jpg");

        // Set image frame
        left = 0;
        bottom = 0;
        right = srcImg.getWidth();
        top = srcImg.getHeight();

        // Compile shader program
        int vtxShader = GLUtils.compileShader("src/shaders/direct.vert", GL_VERTEX_SHADER);
        int fragShader = GLUtils.compileShader("src/shaders/direct.frag", GL_FRAGMENT_SHADER);
        copyProgram = GLUtils.linkSimpleProgram(vtxShader, fragShader);

        // Set up full-screen quad
        fsqVAO = GLUtils.buildVAO(GLUtils.fsqVertices, GLUtils.fsqIndices);
    }

    @Override
    public void resize(int width, int height) {

        // Allocate screen-resolution texture
        glBindTexture(GL_TEXTURE_2D, tex);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glBindTexture(GL_TEXTURE_2D, 0);

        // Allocate destination image
        dstImg = new SimpleImage(width, height);

        // Set viewport to full framebuffer
        glViewport(0, 0, width, height);
    }

    @Override
    public void draw() {

        // Clear
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        // Resample
        resampler.resample(srcImg, dstImg, left, bottom, right, top);

        // Update texture
        ByteBuffer buf = BufferUtils.createByteBuffer(dstImg.getWidth() * dstImg.getHeight() * 3);
        buf.put(dstImg.getData()).flip();
        glBindTexture(GL_TEXTURE_2D, tex);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, dstImg.getWidth(), dstImg.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, buf);

        // Draw full-screen quad
        glBindVertexArray(fsqVAO);
        glUseProgram(copyProgram);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, tex);
        int loc = glGetUniformLocation(copyProgram, "tex");
        glUniform1i(loc, 0);
        glDrawElements(GL_TRIANGLES, GLUtils.fsqIndices.length, GL_UNSIGNED_INT, 0);
        glUseProgram(0);
        glBindVertexArray(GL_NONE);

        GLUtils.checkError("after draw");

    }

    public void keyEvent(int key, int scancode, int action, int mods) {
        super.keyEvent(key, scancode, action, mods);
        if (action == GLFW_RELEASE) {
            switch (key) {
                case GLFW_KEY_N://Nearest Neighbor
                    currentFilterName = "_NearestNeighborSp";
                    resampler = new NearestNeighborResampler();
                    break;
                case GLFW_KEY_L://Linear with direct implement
                    currentFilterName = "_LinearSampler";
                    resampler = new LinearResampler();
                    break;
                case GLFW_KEY_F://Linear filter
                    currentFilterName = "_LinearFilter";
                    resampler = new FilterResampler(new LinearFilter());
                    break;
                case GLFW_KEY_M://Mitchell-Netravali Cubic filter
                    currentFilterName = "_Mitchell-Netravali";
                    resampler = new FilterResampler(new CubicFilter(1.f/3,1.f/3));
                    break;
                case GLFW_KEY_B://B-spline Cubic filter
                    currentFilterName = "_B-spline";
                    resampler = new FilterResampler(new CubicFilter(1.f,0.f));
                    break;
                case GLFW_KEY_C://Catmull-Rom spline Cubic filter
                    currentFilterName = "_Catmull-Rom";
                    resampler = new FilterResampler(new CubicFilter(0.f,0.5f));
                    break;
                case GLFW_KEY_P:
                    resampler = new PixelCopier();
                    break;
                case GLFW_KEY_R://reset window to show srcImg
                    left = 0;
                    right = srcImg.getWidth();
                    top = srcImg.getHeight();
                    bottom = 0;
                    // Use nearest neighbor by default
                    resampler = new PixelCopier();
                    break;
                case GLFW_KEY_W://save image
                    IOUtils.writeImage(dstImg, finalImgType, finalImgName + currentFilterName);
                    break;
                case GLFW_KEY_T://save image for test
                    double width = srcImg.getWidth();
                    double height = srcImg.getHeight();
                    // Use zoomed image
                    left = width * 0.65f;
                    right = width * 0.75f;
                    top = height * 0.5f;
                    bottom = height * 0.4f;
                    draw();
                    IOUtils.writeImage(dstImg, finalImgType, finalImgName + currentFilterName);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mouseButtonEvent(int button, int action, int mods) {
        if (button == GLFW_MOUSE_BUTTON_LEFT) {
            if (action == GLFW_PRESS) leftMouseDown = true;
            if (action == GLFW_RELEASE) leftMouseDown = false;
        }
    }

    @Override
    public void mouseMotionEvent(double xPos, double yPos) {
        // Check if dragging
        leftReleased = leftPrev && !leftMouseDown;

        if (leftReleased) leftDrag = false;

        leftPrev = leftMouseDown;

        if (!leftDrag && leftMouseDown) {
            xPrevPos = xPos;
            yPrevPos = yPos;
            leftDrag = true;
        }

        // Update image pos when dragging the mouse
        if (leftDrag) {
            double xmotion = -srcImg.getWidth()*(xPos - xPrevPos)/(double)dstImg.getWidth();
            double ymotion = srcImg.getHeight()*(yPos - yPrevPos)/(double)dstImg.getHeight();
            left += xmotion;
            right += xmotion;
            top += ymotion;
            bottom += ymotion;
        }

        xPrevPos = xPos;
        yPrevPos = yPos;
    }

    @Override
    public void scrollEvent(double xOffset, double yOffset) {
        zoom += yOffset * 0.1f;
        double width, height = 0;
        double minzoom = -1;
        double maxzoom = 0.9;
        if (zoom < maxzoom && zoom > minzoom) {
            width = (1 - zoom) * srcImg.getWidth();
            height = (1 - zoom) * srcImg.getHeight();
        }
        else {
            zoom = Math.max(zoom,minzoom);
            zoom = Math.min(zoom,maxzoom);
            width = (1 - zoom) * srcImg.getWidth();
            height = (1 - zoom) * srcImg.getHeight();
        }
        //set the display region of srcImg
        double xmid = left + right;
        double ymid = top + bottom;
        left = (xmid - width)/2;
        bottom = (ymid - height)/2;
        right =  (xmid + width)/2;
        top =  (ymid + height)/2;
    }

    public static void main(String[] args) {
        new ResampleApp().run();
    }

}
