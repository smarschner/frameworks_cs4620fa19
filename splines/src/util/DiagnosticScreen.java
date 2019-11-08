package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import blister.GameScreen;
import blister.GameTime;
import egl.ArrayBind;
import egl.BlendState;
import egl.DepthState;
import egl.GL.BufferTarget;
import egl.GL.BufferUsageHint;
import egl.GL.GLType;
import egl.GL.PixelFormat;
import egl.GL.PixelInternalFormat;
import egl.GL.PrimitiveType;
import egl.GL.TextureTarget;
import egl.GL.TextureUnit;
import egl.GLBuffer;
import egl.GLProgram;
import egl.GLTexture;
import egl.NativeMem;
import egl.RasterizerState;
import egl.Semantic;
import egl.ShaderInterface;

public class DiagnosticScreen extends GameScreen {
	@Override
	public int getNext() {
		return 0;
	}
	@Override
	protected void setNext(int next) {
	}

	@Override
	public int getPrevious() {
		return 0;
	}
	@Override
	protected void setPrevious(int previous) {
	}

	GLProgram program;
	GLBuffer vb, ib;
	GLTexture texture;
	ShaderInterface fxsi;
	
	boolean savedImage;
	
	@Override
	public void build() {
		program = new GLProgram();
		program.quickCreateResource("Diag", "util/Diag.vert", "util/Diag.frag", null);
		
		fxsi = new ShaderInterface(new ArrayBind[]{
				new ArrayBind(Semantic.Position, GLType.Float, 3, 0),
				new ArrayBind(Semantic.TexCoord, GLType.Float, 2, 3 * 4)
		});
		fxsi.build(program.semanticLinks);
		
		vb = new GLBuffer(BufferTarget.ArrayBuffer, BufferUsageHint.StaticDraw, true);
		vb.setAsVertex(5 * 4);
		FloatBuffer vBuf = NativeMem.createFloatBuffer(4 * 5);
		vBuf.put(new float[]{
				-0.5f, 0.5f, 0, 0, 1,
				0.5f, 0.5f, 0, 1, 1,
				-0.5f, -0.5f, 0, 0, 0,
				0.5f, -0.5f, 0, 1, 0
		});
		vBuf.flip();
		vb.setDataInitial(vBuf);
		
		ib = new GLBuffer(BufferTarget.ElementArrayBuffer, BufferUsageHint.StaticDraw, true);
		ib.setAsIndexInt();
		IntBuffer iBuf = NativeMem.createIntBuffer(6);
		iBuf.put(new int[]{ 0, 2, 1, 1, 2, 3 });
		iBuf.flip();
		ib.setDataInitial(iBuf);
		
		texture = new GLTexture(TextureTarget.Texture2D, true);
		texture.internalFormat = PixelInternalFormat.Rgba;
		try {
			texture.setImage2DResource("util/Diag.png", false);
		} catch (Exception e) {
			System.out.println("NO IMAGE\r\n" + e.getMessage());
		}
	}
	@Override
	public void destroy(GameTime gameTime) {
	}

	@Override
	public void onEntry(GameTime gameTime) {
		savedImage = false;
	}
	@Override
	public void onExit(GameTime gameTime) {
	}

	@Override
	public void update(GameTime gameTime) {
	}
	@Override
	public void draw(GameTime gameTime) {
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glClearDepth(1.0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		RasterizerState.CULL_CLOCKWISE.set();
		BlendState.OPAQUE.set();
		DepthState.DEFAULT.set();
		
		program.use();
		texture.use(TextureUnit.Texture0, program.getUniform("Texture"));
		vb.useAsAttrib(fxsi);
		ib.bind();
		GL11.glDrawElements(PrimitiveType.Triangles, 6, GLType.UnsignedInt, 0);
		ib.unbind();
		texture.unuse();
		GLProgram.unuse();
		
		if(!savedImage) {
			int sw = game.getWidth();
			int sh = game.getHeight();
			ByteBuffer pb = NativeMem.createByteBuffer(sw * sh * 4);
			GL11.glReadPixels(0, 0, sw, sh, PixelFormat.Rgba, GLType.UnsignedByte, pb);
			pb.position(0); pb.limit(sw * sh * 4);
			
			byte[] cb = new byte[4];
			BufferedImage im = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
			for(int y = sh - 1;y >= 0;y--) {
				for(int x = 0;x < sw;x++) {
					pb.get(cb);
					im.setRGB(x, y, (cb[2] & 0xFF) | ((cb[1] & 0xFF) << 8) | ((cb[0] & 0xFF) << 16) | 0xFF000000);
				}
			}
			
			try {
				ImageIO.write(im, "png", new File("Diag.png"));
				System.out.println("Image Has Been Saved");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			savedImage = true;
		}
	}

}
