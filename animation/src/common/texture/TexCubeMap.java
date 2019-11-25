package common.texture;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import egl.GL;
import egl.NativeMem;
import egl.GL.TextureTarget;
import egl.GL.TextureUnit;
import egl.GL.TextureMagFilter;
import egl.GL.TextureMinFilter;
import ext.java.IOUtils;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUniform1i;

import java.nio.ByteBuffer;
//import java.nio.ByteBuffer;






//import org.lwjgl.BufferUtils;
import egl.GL.PixelInternalFormat;
import egl.GL.PixelType;
import egl.GL.TextureParameterName;
import egl.math.Color;


public class TexCubeMap {
	private int uintTexCube;
	boolean created;
	
	public TexCubeMap() {
		created = false;
	}
	
	public int getUint() {
		return uintTexCube;
	}
	
	public void createCubeMap(String dir) throws Exception {
		glActiveTexture(TextureUnit.Texture0);
		uintTexCube = glGenTextures();
		
		loadCubeMapSide(uintTexCube, TextureTarget.TextureCubeMapNegativeZ, dir + "negz.jpg");
		loadCubeMapSide(uintTexCube, TextureTarget.TextureCubeMapPositiveZ, dir + "posz.jpg");
		loadCubeMapSide(uintTexCube, TextureTarget.TextureCubeMapPositiveY, dir + "posy.jpg");
		loadCubeMapSide(uintTexCube, TextureTarget.TextureCubeMapNegativeY, dir + "negy.jpg");
		loadCubeMapSide(uintTexCube, TextureTarget.TextureCubeMapNegativeX, dir + "negx.jpg");
		loadCubeMapSide(uintTexCube, TextureTarget.TextureCubeMapPositiveX, dir + "posx.jpg");
		
		glTexParameteri(TextureTarget.TextureCubeMap, TextureParameterName.TextureMagFilter, TextureMagFilter.Linear);
		glTexParameteri(TextureTarget.TextureCubeMap, TextureParameterName.TextureMinFilter, TextureMinFilter.Linear);
		glTexParameteri(TextureTarget.TextureCubeMap, TextureParameterName.TextureWrapR, TextureParameterName.ClampToEdge);
		glTexParameteri(TextureTarget.TextureCubeMap, TextureParameterName.TextureWrapS, TextureParameterName.ClampToEdge);
		glTexParameteri(TextureTarget.TextureCubeMap, TextureParameterName.TextureWrapT, TextureParameterName.ClampToEdge);
		created = true;
	}
	
	public boolean use(int textureUnit, int unCubeMap) {
		if(unCubeMap != GL.BadUniformLocation && created) {
			glActiveTexture(textureUnit);
			glBindTexture(TextureTarget.TextureCubeMap, uintTexCube);
			glUniform1i(unCubeMap, textureUnit - TextureUnit.Texture0);
			return true;
		}
		return false;
	}
	

	private Boolean loadCubeMapSide(int uintTexture, int sideTarget, String name) throws Exception{
		glBindTexture(TextureTarget.TextureCubeMap, uintTexture);
		
    	InputStream s = IOUtils.openFile(name);
    	if(s == null) throw new Exception("Could Not Open Image Resource: " + name);
    	BufferedImage image = ImageIO.read(s);
		
    	int w = image.getWidth();
    	int h = image.getHeight();
    	
    	ByteBuffer bb = NativeMem.createByteBuffer(w * h * 4);
    	Color c = new Color();
    	for(int y = 0; y < h;y++) {
    		for(int x = 0; x < w; x++) {
    			int argb = image.getRGB(x, y);
    			c.setIntARGB(argb);
    			bb.put(c.R);
    			bb.put(c.G);
    			bb.put(c.B);
    			bb.put(c.A);
    		}
    	}
    	bb.flip();
    	
        glTexImage2D(sideTarget,
				0,
				PixelInternalFormat.Rgba,
				image.getWidth(),
				image.getHeight(),
				0,
				PixelInternalFormat.Rgba,
				PixelType.UnsignedByte,
				bb);
    	
    	return true;
	}
	
}
