package cs4620.util;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.stb.STBImageWrite.stbi_write_png;
import static org.lwjgl.stb.STBImageWrite.stbi_write_jpg;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SeekableByteChannel;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import cs4620.filters.SimpleImage;

/**
 * A container for static methods that are useful for I/O operations in the LWJGL context.
 * 
 * @author srm
 */
public class IOUtils {

    /**
     * Read the entire contents of a file into an LWJGL-friendly ByteBuffer.
     * <p>Implementation owed to lwjgl.demo.util.IOUtil
     * 
     * @param resource The filename to read from.
     * @return A newly allocated ByteBuffer with the entire file contents.
     * @throws IOException
     */
    public static ByteBuffer readWholeFile(String resource) throws IOException {
        Path path = Paths.get(resource);
        ByteBuffer buffer;

        try (SeekableByteChannel fc = Files.newByteChannel(path)) {
            buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
            while (fc.read(buffer) != -1) {
                ;
            }
        }

        buffer.flip();
        return buffer;
    }


    /**
     * Read an image from the filesystem, without using the AWT library.  Supports
     * file formats supported by the stb library.
     * <p>Implementation mainly owed to org.lwjgl.demo.stb.Image.
     * 
     * @param filename The input filename, relative the the process working directory
     * @return A newly created SimpleImage
     */
    public static SimpleImage readImage(String filename) {
        System.out.println("Loading image " + filename);

        ByteBuffer imageBuffer;
        try {
            imageBuffer = IOUtils.readWholeFile(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ByteBuffer image;
        int width, height, components;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w    = stack.mallocInt(1);
            IntBuffer h    = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            // Decode the image
            image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }

            System.out.println(String.format("raw image is %dx%d, %d components", w.get(0), h.get(0), comp.get(0)));

            width = w.get(0);
            height = h.get(0);
            components = comp.get(0);
        }

        SimpleImage simg = new SimpleImage(width, height);
        for (int iy = height - 1; iy >= 0; iy--) {
            for (int ix = 0; ix < width; ix++) {
                image.get(simg.getData(), 3 * (ix + width * iy), 3);
                for (int i = 3; i < components; i++)
                    image.get();
            }
        }
        return simg;
    }

    /**
     * Write an image to the filesystem, without using the AWT library.  Supports
     * a subset of the file formats supported by the stb library.
     * 
     * @param img      The image to write
     * @param imgType  File type, "png" or "jpg"
     * @param filename Name of the output file
     */
    public static void writeImage(SimpleImage img, String imgType, String filename) {
        String filePath = "data/" + filename + "." + imgType;

        ByteBuffer buf = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 3);
        for (int iy = img.getHeight() - 1; iy >= 0; iy--)
            buf.put(img.getData(), 3 * img.getWidth() * iy, 3 * img.getWidth());
        buf.flip();

        if (imgType.toLowerCase() == "png")
            stbi_write_png(filePath, img.getWidth(), img.getHeight(), 3, buf, img.getWidth() * 3);
        else if (imgType.toLowerCase() == "jpg")
            stbi_write_jpg(filePath, img.getWidth(), img.getHeight(), 3, buf, 90);
        else
            throw new RuntimeException("Unsupported file type: " + imgType);
    }
}
