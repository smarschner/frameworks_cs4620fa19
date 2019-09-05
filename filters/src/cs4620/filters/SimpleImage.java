package cs4620.filters;

/**
 * A very simple image class, providing only for 8-bit three-channel (RGB) images.
 * 
 * @author srm
 * @author <student(s)...>
 */
public class SimpleImage {

    private int width;
    private int height;
    private byte[] data;

    /**
     * A black (all zero) image.
     * 
     * @param width  The image width
     * @param height The image height
     */
    public SimpleImage(int width, int height) {
        this.width = width;
        this.height = height;
        data = new byte[width * height * 3];
    }

    /**
     * @return The number of pixels in the horizontal direction
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The number of pixels in the vertical direction
     */
    public int getHeight() {
        return height;
    }

    /**
     * Access the image content, which is organized from left to right and bottom to top.
     * The channel is the fastest-varying index and the row is the slowest-varying.
     * 
     * @return The data array, which can be used for reading or writing.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Read a single component of a single pixel.
     * 
     * @param ix  column index
     * @param iy  row index
     * @param c   color channel in {0, 1, 2}
     * @return the pixel value
     */    
    public byte getPixel(int ix, int iy, int c) {
    	//TODO getPixel
        return 0;
    }

    /**
     * Write a single component of a single pixel.
     * 
     * @param ix  column index
     * @param iy  row index
     * @param c   color channel in {0, 1, 2}
     * @param val the new pixel value
     */
    public void setPixel(int ix, int iy, int c, byte val) {
    	//TODO setPixel
    }


}
