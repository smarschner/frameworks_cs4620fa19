package ray1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import edu.cornell.graphics.exr.Channel;
import edu.cornell.graphics.exr.ChannelList;
import edu.cornell.graphics.exr.EXROutputFile;
import edu.cornell.graphics.exr.FrameBuffer;
import edu.cornell.graphics.exr.Header;
import edu.cornell.graphics.exr.PixelType;
import edu.cornell.graphics.exr.Slice;
import egl.math.Color;
import egl.math.Colorf;

/**
 * Basic image class, containing a 2D array of Colorfs. Simple functionality for
 * setting pixel colors and writing to a PNG/EXR file is provided.
 *
 * @author eschweic
 */
public class Image {
	
	/** Image width * */
	protected int width;
	
	/** Image height * */
	protected int height;
	
	/** Data array* */
	protected Colorf[][] data;
	
	/**
	 * Create an empty image
	 *
	 * @param inW input width
	 * @param inH input height
	 */
	public Image(int inW, int inH) {
		setSize(inW, inH);
	}
	
	/**
	 * Copy constructor
	 *
	 * @param oldImage oldImage
	 */
	public Image(Image oldImage) {
		setSize(oldImage.getWidth(), oldImage.getHeight());
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				data[i][j].set(oldImage.data[i][j]);
			}
		}
	}
	
	/**
	 * Set the image to black
	 */
	public void clear() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				data[i][j] = new Colorf();
			}
		}
	}
	
	/**
	 * @return the width of the image
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return the height of the image
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Set the size of the image by recreating it.  Destroys all current image data.
	 * @param newWidth width
	 * @param newHeight height
	 */
	public void setSize(int newWidth, int newHeight) {
		width = newWidth;
		height = newHeight;
		data = new Colorf[width][height];
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				data[i][j] = new Colorf();
			}
		}
	}
	
	/**
	 * Get the color of a pixel.
	 *
	 * @param outPixel Color value of pixel (inX,inY)
	 * @param inX inX coordinate
	 * @param inY inY Coordinate
	 */
	public void getPixelColor(Color outPixel, int inX, int inY) {
		if (inX < 0 || inY < 0 || inX >= width || inY >= height)
			throw new IndexOutOfBoundsException();
		outPixel.set(data[inX][inY]);
	}
	
	/**
	 * Get the color of a pixel.
	 *
	 * @param outPixel Colorf value of pixel (inX,inY)
	 * @param inX inX coordinate
	 * @param inY inY Coordinate
	 */
	public void getPixelColor(Colorf outPixel, int inX, int inY) {
		if (inX < 0 || inY < 0 || inX >= width || inY >= height)
			throw new IndexOutOfBoundsException();
		outPixel.set(data[inX][inY]);
	}
	
	/**
	 * Set the color of a pixel.
	 * @param inPixel Color value of pixel (inX,inY)
	 * @param inX inX coordinate
	 * @param inY inY Coordinate
	 */
	public void setPixelColor(Color inPixel, int inX, int inY) {
		if (inX < 0 || inY < 0 || inX >= width || inY >= height)
			throw new IndexOutOfBoundsException();
		data[inX][inY].set(inPixel);
	}
	
	/**
	 * Set the color of a pixel.
	 * @param inPixel Colorf value of pixel (inX, inY)
	 * @param inX inX coordinate
	 * @param inY inY coordinate
	 */
	public void setPixelColor(Colorf inPixel, int inX, int inY) {
		if (inX < 0 || inY < 0 || inX >= width || inY >= height)
			throw new IndexOutOfBoundsException();
		data[inX][inY].set(inPixel);
	}
	
	/**
	 * Write this image to the filename.  The output is always written as a PNG regardless
	 * of the extension on the filename given.
	 * @param fileName the output filename
	 */
	public void write(String fileName) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Colorf pixelColor= new Colorf();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				this.getPixelColor(pixelColor, x, y);
				pixelColor.gammaCorrect(2.2f);
				Color c = pixelColor.toColor();
				bufferedImage.setRGB(x, (height - 1 - y), c.toIntRGB());
			}
		}
		
		try {
			ImageIO.write(bufferedImage, "PNG", new File(fileName));
		}
		catch (Exception e) {
			System.out.println("Error occured while attempting to write file: "+fileName);
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * A quick openEXR-JNI write test
	 * Write this image to the filename.  The output is always written as HDR regardless
	 * of the extension on the filename given.
	 * @param fileName the output filename
	 */
	public void writeHDR(String fileName) {
		
		File outFile = new File(fileName);

        final PixelType pixelType = PixelType.FLOAT;
        final int numChannels = 3;
        final int elemSize    = pixelType.byteSize();
        final int pixelSize   = elemSize * numChannels;
        final int numPixels   = width * height;
    
		Header hdrHeader = new Header(width, height);
		
		ChannelList channels = hdrHeader.getChannels();
        channels.insert("R", new Channel(pixelType));
        channels.insert("G", new Channel(pixelType));
        channels.insert("B", new Channel(pixelType));
        
		Colorf pixelColor= new Colorf();
        ByteBuffer pixels = ByteBuffer.allocateDirect(pixelSize * numPixels);
        pixels.order(ByteOrder.LITTLE_ENDIAN);
        for(int h = height-1; h >= 0; --h) {
            for (int w = 0; w < width; ++w) {
            	this.getPixelColor(pixelColor, w, h);
                pixels.putFloat((float) pixelColor.x);
                pixels.putFloat((float) pixelColor.y);
                pixels.putFloat((float) pixelColor.z);
            }
        }
        pixels.flip();
        
     // Build the frame buffer
        FrameBuffer frameBuffer = new FrameBuffer();
        final int yStride = width * pixelSize;
        frameBuffer.insert("R", Slice.build()
                .baseOffset(0).buffer(pixels).pixelType(pixelType)
                .xStride(pixelSize).yStride(yStride).get());
        frameBuffer.insert("G", Slice.build()
                .baseOffset(elemSize).buffer(pixels).pixelType(pixelType)
                .xStride(pixelSize).yStride(yStride).get());
        frameBuffer.insert("B", Slice.build()
                .baseOffset(2*elemSize).buffer(pixels).pixelType(pixelType)
                .xStride(pixelSize).yStride(yStride).get());
        
        try (EXROutputFile out = new EXROutputFile(outFile.toPath(), hdrHeader)){
             out.setFrameBuffer(frameBuffer);
             out.writePixels(height);
             System.out.printf("  Wrote RGB32F file %s%n", outFile);
             
             //System.out.printf("  File size: %d%n", Files.size(outFile.toPath()));
             pixels.rewind();
         }
		catch (Exception e) {
			System.out.println("Error occured while attempting to write file: "+fileName);
			System.err.println(e);
			e.printStackTrace();
		}
	}

}
