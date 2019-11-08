package egl;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage1D;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.glTexImage3D;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUniform1i;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;
//import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

//import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import egl.GL.PixelFormat;
import egl.GL.PixelInternalFormat;
import egl.GL.PixelType;
import egl.GL.TextureParameterName;
import egl.GL.TextureTarget;
import egl.GL.TextureUnit;
import egl.math.Color;
import ext.java.IOUtils;

public class GLTexture implements IDisposable {
	private static final int[] TEXTURE_TARGETS = {
		TextureTarget.ProxyTexture1D,
		TextureTarget.ProxyTexture1DArray,
		TextureTarget.ProxyTexture2D,
		TextureTarget.ProxyTexture2DArray,
		TextureTarget.ProxyTexture2DMultisample,
		TextureTarget.ProxyTexture2DMultisampleArray,
		TextureTarget.ProxyTexture3D,
		TextureTarget.ProxyTextureCubeMap,
		TextureTarget.ProxyTextureCubeMapArray,
		TextureTarget.ProxyTextureRectangle,
		TextureTarget.Texture1D,
		TextureTarget.Texture1DArray,
		TextureTarget.Texture2D,
		TextureTarget.Texture2DArray,
		TextureTarget.Texture2DMultisample,
		TextureTarget.Texture2DMultisampleArray,
		TextureTarget.Texture3D,
		TextureTarget.TextureBaseLevel,
		TextureTarget.TextureBindingCubeMap,
		TextureTarget.TextureBuffer,
		TextureTarget.TextureCubeMap,
		TextureTarget.TextureCubeMapArray,
		TextureTarget.TextureCubeMapNegativeX,
		TextureTarget.TextureCubeMapNegativeY,
		TextureTarget.TextureCubeMapNegativeZ,
		TextureTarget.TextureCubeMapPositiveX,
		TextureTarget.TextureCubeMapPositiveY,
		TextureTarget.TextureCubeMapPositiveZ,
		TextureTarget.TextureMaxLevel,
		TextureTarget.TextureMaxLod,
		TextureTarget.TextureMinLod,
		TextureTarget.TextureRectangle,
	};
	static class Binding {
        private int Target;
        public GLTexture Current;

        public Binding(int t) {
            Target = t;
            Current = null;
        }
        public void Unbind() {
            Current = null;
            glBindTexture(Target, 0);
        }
    }
	
	private static final HashMap<Integer, Binding> currentBindings = new HashMap<>();
    static {
        for(int bt : TEXTURE_TARGETS) {
            currentBindings.put(bt, new Binding(bt));
        }
    }
    public static void unbind(int t) {
        currentBindings.get(t).Unbind();
    }
	
    private int id;
    private int target;
    private Binding refBind;
    private int[] dimensions;
    public int internalFormat = PixelInternalFormat.Rgba;
	
    public GLTexture(int target, boolean init) {
        id = 0;
        dimensions = new int[] { 0, 0, 0 };

        setTarget(target);
        internalFormat = PixelInternalFormat.Rgba8;
        if(init) init();
    }
    public GLTexture(int target) {
    	this(target, false);
    }
    public GLTexture() {
    	this(TextureTarget.Texture2D);
    }
    @Override
    public void dispose() {
        if(getIsCreated()) {
            glDeleteTextures(id);
            id = 0;
        }
    }

    public int getID() {
    	return id;
    }
    public boolean getIsCreated() {
        return id != 0;
    }
    public int getWidth() {
        return dimensions[0];
    }
    public int getHeight() {
        return dimensions[1];
    }
    public int getDepth() {
        return dimensions[2];
    }
    public int getTarget() {
        return target;
    }
    public boolean getIsBound() {
        return refBind.Current == this;
    }
    
    public GLTexture init() {
        if(getIsCreated()) return this;
        id = glGenTextures();
        return this;
    }

    private void setTarget(int value) {
        target = value;
        refBind = currentBindings.get(target);
    }

    public void bind() {
        if(!getIsBound()) {
            refBind.Current = this;
            glBindTexture(target, id);
            GLError.get("Texture Bind");
        }
    }
    public void unbind() {
        if(getIsBound()) refBind.Unbind();
    }

    public void setImage(int[] dim, int pixelFormat, int pixelType, ByteBuffer buf, boolean mipMap) throws Exception {
        if(dim == null || dim.length < 1 || dim.length > 3)
            throw new Exception("Dimensions For The Texture Must Be Given (Must Be 1 - 3)");
        System.arraycopy(dim, 0, dimensions, 0, dim.length);

        int dims = 0;
        boolean found = false;
        if(getWidth() > 0) dims++;
        else found = true;
        if(!found && getHeight() > 0) dims++;
        else found = true;
        if(!found && getDepth() > 0) dims++;
        
        bind();
        switch(dims) {
            case 1:
                glTexImage1D(target, 0, internalFormat, getWidth(), 0, pixelFormat, pixelType, buf);
                break;
            case 2:
                if(mipMap)
                    glTexParameteri(target, TextureParameterName.GenerateMipmap, 1);
                glTexImage2D(target, 0, internalFormat, getWidth(), getHeight(), 0, pixelFormat, pixelType, buf);
                if(mipMap && getTarget() == TextureTarget.Texture2D)
                	GL30.glGenerateMipmap(TextureTarget.Texture2D);
                break;
            case 3:
        		glTexImage3D(target, 0, internalFormat, getWidth(), getHeight(), getDepth(), 0, pixelFormat, pixelType, buf);
                break;
            default:
                throw new Exception("Invalid Dimensions For The Texture (Must Be > 0)");
        }
		SamplerState.POINT_WRAP.set(target);
        unbind();
    }
    public void setImage(int w, int h, int d, int pixelFormat, int pixelType, ByteBuffer buf, boolean mipMap) {
    	try {
			setImage(new int[] {w, h, d}, pixelFormat, pixelType, buf, mipMap);
		} 
    	catch (Exception e) {
    		// The Apocalypse Has Begun. Quick... Hide In The Garage!
    		// Update: This Error Has Occurred. We Are All FUCKED!!!
		}
    }
    public void setImage(int w, int h, int pixelFormat, int pixelType, ByteBuffer buf, boolean mipMap) {
    	setImage(w, h, 0, pixelFormat, pixelType, buf, mipMap);
    }
    public void setImage(int w, int pixelFormat, int pixelType, ByteBuffer buf, boolean mipMap) {
    	setImage(w, 0, 0, pixelFormat, pixelType, buf, mipMap);
    }

    public void setImage2D(BufferedImage data, boolean mipMap) throws Exception {
    	
    	int w = data.getWidth();
    	int h = data.getHeight();
    	
//    	byte[] pixels = ((DataBufferByte)data.getRaster().getDataBuffer()).getData();
    	ByteBuffer bb = NativeMem.createByteBuffer(w * h * 4);
    	Color c = new Color();
    	for(int y = h - 1;y >= 0;y--) {
    		for(int x = 0; x < w; x++) {
    			int argb = data.getRGB(x, y);
    			c.setIntARGB(argb);
    			bb.put(c.R);
    			bb.put(c.G);
    			bb.put(c.B);
    			bb.put(c.A);
    		}
    	}
    	bb.flip();
        setImage(new int[] { w, h, 0 }, PixelFormat.Rgba, PixelType.UnsignedByte, bb, mipMap);
    }
    public void setImage2D(String file, boolean mipMap) throws Exception {
    	InputStream s = IOUtils.openFile(file);
    	if(s == null) throw new Exception("Could Not Open Image File: " + file);
    	BufferedImage image = ImageIO.read(s);
    	setImage2D(image, mipMap);
    }
    public void setImage2DResource(String name, boolean mipMap) throws Exception {
    	InputStream s = IOUtils.openResource(name);
    	if(s == null) throw new Exception("Could Not Open Image Resource: " + name);
    	BufferedImage image = ImageIO.read(s);
    	setImage2D(image, mipMap);
    }
    
    public void bindToUnit(int unit) {
        glActiveTexture(unit);
        bind();
    }
    public void setUniformSampler(int unit, int unSampler) {
        glUniform1i(unSampler, unit - TextureUnit.Texture0);
    }

    /**
     * Uses This Texture In A Program
     * @param unit {@link TextureUnit}0 - Maximum Texture Units
     * @param unSampler Sampler Uniform ID
     */
    public void use(int unit, int unSampler) {
        bindToUnit(unit);
        setUniformSampler(unit, unSampler);
    }
    public void unuse() {
        unbind();
    }
}