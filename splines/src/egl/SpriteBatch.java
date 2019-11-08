package egl;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

import egl.GL.BufferTarget;
import egl.GL.BufferUsageHint;
import egl.GL.GLType;
import egl.GL.GetProgramParameterName;
import egl.GL.PixelFormat;
import egl.GL.PixelType;
//import org.lwjgl.BufferUtils;
import egl.GL.PrimitiveType;
import egl.GL.ShaderParameter;
import egl.GL.ShaderType;
import egl.GL.TextureUnit;
import egl.math.Color;
import egl.math.Matrix4;
import egl.math.Vector2;
import egl.math.Vector4;

public class SpriteBatch implements IDisposable {
	/**
	 * Default Initial Size Of Sprite Buffer
	 */
	public static final int INITIAL_GLYPH_CAP = 32;

	/**
	 * Texture Sorting Method
	 * @param g1 Glyph 1
	 * @param g2 Glyph 2
	 * @return Sort Comparison
	 */
	private static int SSMTexture(SpriteGlyph g1, SpriteGlyph g2) {
		return Integer.compare(g1.texture.getID(), g2.texture.getID());
	}
	/**
	 * Depth Sorting Method For Front-To-Back
	 * @param g1 Glyph 1
	 * @param g2 Glyph 2
	 * @return Sort Comparison
	 */
	private static int SSMFrontToBack(SpriteGlyph g1, SpriteGlyph g2) {
		return Float.compare(g1.depth, g2.depth);
	}
	/**
	 * Depth Sorting Method For Back-To-Front
	 * @param g1 Glyph 1
	 * @param g2 Glyph 2
	 * @return Sort Comparison
	 */
	private static int SSMBackToFront(SpriteGlyph g1, SpriteGlyph g2) {
		return Float.compare(g2.depth, g1.depth);
	}

	/**
	 * Batched Draw Call
	 * @author Cristian
	 *
	 */
	private static class SpriteBatchCall {
		public GLTexture Texture;
		public int Indices;
		public int IndexOffset;

		public SpriteBatchCall(int iOff, GLTexture t, ArrayList<SpriteBatchCall> calls) {
			Texture = t;
			IndexOffset = iOff;
			Indices = 6;
			calls.add(this);
		}

		public SpriteBatchCall Append(SpriteGlyph g, ArrayList<SpriteBatchCall> calls) {
			if(g.texture != Texture) return new SpriteBatchCall(IndexOffset + Indices, g.texture, calls);
			else Indices += 6;
			return this;
		}
	}

	/**
	 * Vertex Shader Source Code
	 */
	private static final String VS_SRC = 
			"#version 120\n" +
			"uniform mat4 World;\n" + 
			"uniform mat4 VP;\n" + 
			"attribute vec4 vPosition;\n" + 
			"attribute vec2 vUV;\n" + 
			"attribute vec4 vUVRect;\n" + 
			"attribute vec4 vTint;\n" + 
			"varying vec2 fUV;\n" + 
			"varying vec4 fUVRect;\n" + 
			"varying vec4 fTint;\n" + 
			"void main() {\n" + 
			"    fTint = vTint;\n" + 
			"    fUV = vUV;\n" + 
			"    fUVRect = vUVRect;\n" + 
    		"    vec4 worldPos = World * vPosition;\n" + 
    		"    gl_Position = VP * worldPos;\n" + 
			"}";
	/**
	 * Fragment Shader Source Code
	 */
	private static final String FS_SRC = 
			"#version 120\n" +
			"uniform sampler2D SBTex;\n" + 
			"uniform vec4 ColorMult;\n" + 
			"uniform vec4 ColorAdd;\n" + 
			"varying vec2 fUV;\n" + 
			"varying vec4 fUVRect;\n" + 
			"varying vec4 fTint;\n" + 
			"void main() {\n" + 
			"    gl_FragColor = (ColorMult * texture2D(SBTex, (vec2(fract(fUV.x), fract(fUV.y)) * fUVRect.zw) + fUVRect.xy) * fTint) + ColorAdd;\n" + 
			"}";

	/**
	 * Default Texture Sampling Rectangle
	 */
	public static final Vector4 FULL_UV_RECT = new Vector4(0, 0, 1, 1);
	/**
	 * Default Texture Tiling
	 */
	public static final Vector2 UV_NO_TILE = new Vector2(1, 1);

	private static final Vector4 DEFAULT_COL_MULT = new Vector4(1, 1, 1, 1);
	private static final Vector4 DEFAULT_COL_ADD = new Vector4(0, 0, 0, 0);
	
	/**
	 * Construct A Camera Matrix From A View Size
	 * @param w View Width
	 * @param h View Height
	 * @return New Camera Matrix
	 */
	public static Matrix4 createCameraFromWindow(float w, float h) {
		w *= 0.5f;
		h *= 0.5f;
		return Matrix4.createScale(1 / w, -1 / h, 1).mulAfter(Matrix4.createTranslation(-1, 1, 0));
	}

	// Glyph Information
	private ArrayList<SpriteGlyph> glyphs;
	private Queue<SpriteGlyph> emptyGlyphs;

	// Render Batches
	private int bufUsage;
	private int vbo, glyphCapacity;
	private ArrayList<SpriteBatchCall> batches;

	// White Pixel Texture
	private GLTexture texture;
	
	// Custom Shader
	private int idProg, idVS, idFS;
	private int unWorld, unVP, unTexture, unColMult, unColAdd;

	/**
	 * Construct A SpriteBatch With Static/Dynamic Qualifiers
	 * @param isDynamic Will This SpriteBatch Change Itself Many Times
	 */
	public SpriteBatch(boolean isDynamic) {
		bufUsage = isDynamic ? BufferUsageHint.DynamicDraw : BufferUsageHint.StaticDraw;

		init();

		emptyGlyphs = new ArrayDeque<SpriteGlyph>();
	}
	private void init() {
		createProgram();
		searchUniforms();
		createVertexArray();
		
		texture = new GLTexture().init();
		ByteBuffer buf = NativeMem.createByteBuffer(4);
		buf.put((byte)0xff);
		buf.put((byte)0xff);
		buf.put((byte)0xff);
		buf.put((byte)0xff);
		buf.flip();
		texture.setImage(1, 1, PixelFormat.Rgba, PixelType.UnsignedByte, buf, false);
		
		glUseProgram(idProg);
		GLUniform.set(unColMult, DEFAULT_COL_MULT);
		GLUniform.set(unColAdd, DEFAULT_COL_ADD);
	}
	/**
	 * Destroy OpenGL Resources This SpriteBatch Allocated
	 */
	@Override	
	public void dispose() {
		glDeleteBuffers(vbo);
		vbo = 0;
		glDetachShader(idProg, idVS);
		glDeleteShader(idVS);
		glDetachShader(idProg, idFS);
		glDeleteShader(idFS);
		glDeleteProgram(idProg);
		texture.dispose();
	}

	private void createProgram() {
		// Create The Program
		idProg = glCreateProgram();

		// Make Vertex Shader
		idVS = glCreateShader(ShaderType.VertexShader);
		glShaderSource(idVS, VS_SRC);
		glCompileShader(idVS);
		if(glGetShaderi(idVS, ShaderParameter.CompileStatus) != 1)
			throw new RuntimeException("Vert Shader Had Compilation Errors");
		glAttachShader(idProg, idVS);

		// Make Fragment Shader
		idFS = glCreateShader(ShaderType.FragmentShader);
		glShaderSource(idFS, FS_SRC);
		glCompileShader(idFS);
		if(glGetShaderi(idFS, ShaderParameter.CompileStatus) != 1)
			throw new RuntimeException("Frag Shader Had Compilation Errors");
		glAttachShader(idProg, idFS);

		// Setup Vertex Attribute Locations
		glBindAttribLocation(idProg, 0, "vPosition");
		glBindAttribLocation(idProg, 1, "vTint");
		glBindAttribLocation(idProg, 2, "vUV");
		glBindAttribLocation(idProg, 3, "vUVRect");

		glLinkProgram(idProg);
		glValidateProgram(idProg);
		if(glGetProgrami(idProg, GetProgramParameterName.LinkStatus) != 1)
			throw new RuntimeException("Program Had Compilation Errors");
	}
	private void searchUniforms() {
		unWorld = glGetUniformLocation(idProg, "World");
		unVP = glGetUniformLocation(idProg, "VP");
		unTexture = glGetUniformLocation(idProg, "SBTex");
		unColMult = glGetUniformLocation(idProg, "ColorMult");
		unColAdd = glGetUniformLocation(idProg, "ColorAdd");
	}
	private void createVertexArray() {
		vbo = glGenBuffers();
		glyphCapacity = INITIAL_GLYPH_CAP;
		glBindBuffer(BufferTarget.ArrayBuffer, vbo);
		glBufferData(BufferTarget.ArrayBuffer, (glyphCapacity * 6) * VertexSpriteBatch.Size, bufUsage);

		GLBuffer.unbind(BufferTarget.ArrayBuffer);    	
	}

	/**
	 * Clear The Current Batch State For A New Set Of Batched Draw Calls
	 */
	public void begin() {
		// Only Clear The Glyphs
		glyphs = new ArrayList<SpriteGlyph>();
		batches = new ArrayList<SpriteBatchCall>();
	}

	private SpriteGlyph createGlyph(GLTexture t, float d) {
		if(t == null) t = texture;
		if(emptyGlyphs.size() > 0) {
			SpriteGlyph g = emptyGlyphs.remove();
			g.texture = t;
			g.depth = d;
			return g;
		}
		else {
			return new SpriteGlyph(t, d);
		}
	}
	public void draw(GLTexture t, Vector4 uvRect, Vector2 uvTiling, Matrix4 mTransform, Color tint, float depth) {
		Vector4 uvr = uvRect != null ? uvRect : FULL_UV_RECT;
		Vector2 uvt = uvTiling != null ? uvTiling : UV_NO_TILE;
		SpriteGlyph g = createGlyph(t, depth);

		g.vtl.Position.x = 0;
		g.vtl.Position.y = 0;
		g.vtl.Position.z = depth;
		mTransform.mulPos(g.vtl.Position);
		g.vtl.UV.x = 0;
		g.vtl.UV.y = 0;
		g.vtl.UVRect.set(uvr);
		g.vtl.Color.set(tint);

		g.vtr.Position.x = 1;
		g.vtr.Position.y = 0;
		g.vtr.Position.z = depth;
		mTransform.mulPos(g.vtr.Position);
		g.vtr.UV.x = uvt.x;
		g.vtr.UV.y = 0;
		g.vtr.UVRect.set(uvr);
		g.vtr.Color.set(tint);

		g.vbl.Position.x = 0;
		g.vbl.Position.y = 1;
		g.vbl.Position.z = depth;
		mTransform.mulPos(g.vbl.Position);
		g.vbl.UV.x = 0;
		g.vbl.UV.y = uvt.y;
		g.vbl.UVRect.set(uvr);
		g.vbl.Color.set(tint);

		g.vbr.Position.x = 1;
		g.vbr.Position.y = 1;
		g.vbr.Position.z = depth;
		mTransform.mulPos(g.vbr.Position);
		g.vbr.UV.x = uvt.x;
		g.vbr.UV.y = uvt.y;
		g.vbr.UVRect.set(uvr);
		g.vbr.Color.set(tint);

		glyphs.add(g);
	}
	public void draw(GLTexture t, Vector4 uvRect, Vector2 uvTiling, Vector2 position, Vector2 offset, Vector2 size, float rotation, Color tint, float depth) {
		Vector4 uvr = uvRect != null ? uvRect : FULL_UV_RECT;
		Vector2 uvt = uvTiling != null ? uvTiling : UV_NO_TILE;
		SpriteGlyph g = createGlyph(t, depth);

		float rxx = (float)Math.cos(-rotation);
		float rxy = (float)Math.sin(-rotation);
		float cl = size.x * (-offset.x);
		float cr = size.x * (1 - offset.x);
		float ct = size.y * (-offset.y);
		float cb = size.y * (1 - offset.y);

		g.vtl.Position.x = (cl * rxx) + (ct * rxy) + position.x;
		g.vtl.Position.y = (cl * -rxy) + (ct * rxx) + position.y;
		g.vtl.Position.z = depth;
		g.vtl.UV.x = 0;
		g.vtl.UV.y = 0;
		g.vtl.UVRect.set(uvr);
		g.vtl.Color.set(tint);

		g.vtr.Position.x = (cr * rxx) + (ct * rxy) + position.x;
		g.vtr.Position.y = (cr * -rxy) + (ct * rxx) + position.y;
		g.vtr.Position.z = depth;
		g.vtr.UV.x = uvt.x;
		g.vtr.UV.y = 0;
		g.vtr.UVRect.set(uvr);
		g.vtr.Color.set(tint);

		g.vbl.Position.x = (cl * rxx) + (cb * rxy) + position.x;
		g.vbl.Position.y = (cl * -rxy) + (cb * rxx) + position.y;
		g.vbl.Position.z = depth;
		g.vbl.UV.x = 0;
		g.vbl.UV.y = uvt.y;
		g.vbl.UVRect.set(uvr);
		g.vbl.Color.set(tint);

		g.vbr.Position.x = (cr * rxx) + (cb * rxy) + position.x;
		g.vbr.Position.y = (cr * -rxy) + (cb * rxx) + position.y;
		g.vbr.Position.z = depth;
		g.vbr.UV.x = uvt.x;
		g.vbr.UV.y = uvt.y;
		g.vbr.UVRect.set(uvr);
		g.vbr.Color.set(tint);

		glyphs.add(g);
	}
	public void draw(GLTexture t, Vector4 uvRect, Vector2 uvTiling, Vector2 position, Vector2 offset, Vector2 size, Color tint, float depth) {
		Vector4 uvr = uvRect != null ? uvRect : FULL_UV_RECT;
		Vector2 uvt = uvTiling != null ? uvTiling : UV_NO_TILE;
		SpriteGlyph g = createGlyph(t, depth);

		float cl = size.x * (-offset.x);
		float cr = size.x * (1 - offset.x);
		float ct = size.y * (-offset.y);
		float cb = size.y * (1 - offset.y);

		g.vtl.Position.x = cl + position.x;
		g.vtl.Position.y = ct + position.y;
		g.vtl.Position.z = depth;
		g.vtl.UV.x = 0;
		g.vtl.UV.y = 0;
		g.vtl.UVRect.set(uvr);
		g.vtl.Color.set(tint);

		g.vtr.Position.x = cr + position.x;
		g.vtr.Position.y = ct + position.y;
		g.vtr.Position.z = depth;
		g.vtr.UV.x = uvt.x;
		g.vtr.UV.y = 0;
		g.vtr.UVRect.set(uvr);
		g.vtr.Color.set(tint);

		g.vbl.Position.x = cl + position.x;
		g.vbl.Position.y = cb + position.y;
		g.vbl.Position.z = depth;
		g.vbl.UV.x = 0;
		g.vbl.UV.y = uvt.y;
		g.vbl.UVRect.set(uvr);
		g.vbl.Color.set(tint);

		g.vbr.Position.x = cr + position.x;
		g.vbr.Position.y = cb + position.y;
		g.vbr.Position.z = depth;
		g.vbr.UV.x = uvt.x;
		g.vbr.UV.y = uvt.y;
		g.vbr.UVRect.set(uvr);
		g.vbr.Color.set(tint);

		glyphs.add(g);
	}
	public void draw(GLTexture t, Vector4 uvRect, Vector2 uvTiling, Vector2 position, Vector2 size, Color tint, float depth) {
		Vector4 uvr = uvRect != null ? uvRect : FULL_UV_RECT;
		Vector2 uvt = uvTiling != null ? uvTiling : UV_NO_TILE;
		SpriteGlyph g = createGlyph(t, depth);

		g.vtl.Position.x = position.x;
		g.vtl.Position.y = position.y;
		g.vtl.Position.z = depth;
		g.vtl.UV.x = 0;
		g.vtl.UV.y = 0;
		g.vtl.UVRect.set(uvr);
		g.vtl.Color.set(tint);

		g.vtr.Position.x = size.x + position.x;
		g.vtr.Position.y = position.y;
		g.vtr.Position.z = depth;
		g.vtr.UV.x = uvt.x;
		g.vtr.UV.y = 0;
		g.vtr.UVRect.set(uvr);
		g.vtr.Color.set(tint);

		g.vbl.Position.x = position.x;
		g.vbl.Position.y = size.y + position.y;
		g.vbl.Position.z = depth;
		g.vbl.UV.x = 0;
		g.vbl.UV.y = uvt.y;
		g.vbl.UVRect.set(uvr);
		g.vbl.Color.set(tint);

		g.vbr.Position.x = size.x + position.x;
		g.vbr.Position.y = size.y + position.y;
		g.vbr.Position.z = depth;
		g.vbr.UV.x = uvt.x;
		g.vbr.UV.y = uvt.y;
		g.vbr.UVRect.set(uvr);
		g.vbr.Color.set(tint);

		glyphs.add(g);
	}
	public void draw(GLTexture t, Vector4 uvRect, Vector2 position, Vector2 size, Color tint, float depth) {
		Vector4 uvr = uvRect != null ? uvRect : FULL_UV_RECT;
		SpriteGlyph g = createGlyph(t, depth);

		g.vtl.Position.x = position.x;
		g.vtl.Position.y = position.y;
		g.vtl.Position.z = depth;
		g.vtl.UV.x = 0;
		g.vtl.UV.y = 0;
		g.vtl.UVRect.set(uvr);
		g.vtl.Color.set(tint);

		g.vtr.Position.x = size.x + position.x;
		g.vtr.Position.y = position.y;
		g.vtr.Position.z = depth;
		g.vtr.UV.x = 1;
		g.vtr.UV.y = 0;
		g.vtr.UVRect.set(uvr);
		g.vtr.Color.set(tint);

		g.vbl.Position.x = position.x;
		g.vbl.Position.y = size.y + position.y;
		g.vbl.Position.z = depth;
		g.vbl.UV.x = 0;
		g.vbl.UV.y = 1;
		g.vbl.UVRect.set(uvr);
		g.vbl.Color.set(tint);

		g.vbr.Position.x = size.x + position.x;
		g.vbr.Position.y = size.y + position.y;
		g.vbr.Position.z = depth;
		g.vbr.UV.x = 1;
		g.vbr.UV.y = 1;
		g.vbr.UVRect.set(uvr);
		g.vbr.Color.set(tint);

		glyphs.add(g);
	}
	public void draw(GLTexture t, Vector2 position, Vector2 size, Color tint, float depth) {
		SpriteGlyph g = createGlyph(t, depth);

		g.vtl.Position.x = position.x;
		g.vtl.Position.y = position.y;
		g.vtl.Position.z = depth;
		g.vtl.UV.x = 0;
		g.vtl.UV.y = 0;
		g.vtl.UVRect.set(FULL_UV_RECT);
		g.vtl.Color.set(tint);

		g.vtr.Position.x = size.x + position.x;
		g.vtr.Position.y = position.y;
		g.vtr.Position.z = depth;
		g.vtr.UV.x = 1;
		g.vtr.UV.y = 0;
		g.vtr.UVRect.set(FULL_UV_RECT);
		g.vtr.Color.set(tint);

		g.vbl.Position.x = position.x;
		g.vbl.Position.y = size.y + position.y;
		g.vbl.Position.z = depth;
		g.vbl.UV.x = 0;
		g.vbl.UV.y = 1;
		g.vbl.UVRect.set(FULL_UV_RECT);
		g.vbl.Color.set(tint);

		g.vbr.Position.x = size.x + position.x;
		g.vbr.Position.y = size.y + position.y;
		g.vbr.Position.z = depth;
		g.vbr.UV.x = 1;
		g.vbr.UV.y = 1;
		g.vbr.UVRect.set(FULL_UV_RECT);
		g.vbr.Color.set(tint);

		glyphs.add(g);
	}

	private void sortGlyphs(int spriteSortMode) {
		if(glyphs.size() < 1) return;
		switch(spriteSortMode) {
		case SpriteSortMode.Texture:
			Collections.sort(glyphs, new Comparator<SpriteGlyph>() {
				@Override
				public int compare(SpriteGlyph o1, SpriteGlyph o2) {
					return SSMTexture(o1, o2);
				}
			});
			break;
		case SpriteSortMode.FrontToBack:
			Collections.sort(glyphs, new Comparator<SpriteGlyph>() {
				@Override
				public int compare(SpriteGlyph o1, SpriteGlyph o2) {
					return SSMFrontToBack(o1, o2);
				}
			});
			break;
		case SpriteSortMode.BackToFront:
			Collections.sort(glyphs, new Comparator<SpriteGlyph>() {
				@Override
				public int compare(SpriteGlyph o1, SpriteGlyph o2) {
					return SSMBackToFront(o1, o2);
				}
			});
			break;
		default:
			break;
		}
	}
	private void generateBatches() {
		if(glyphs.size() < 1) return;

		// Create Arrays
		ByteBuffer bb = NativeMem.createByteBuffer(6 * glyphs.size() * VertexSpriteBatch.Size);
		SpriteBatchCall call = new SpriteBatchCall(0, glyphs.get(0).texture, batches);
		glyphs.get(0).vtl.appendToBuffer(bb);
		glyphs.get(0).vtr.appendToBuffer(bb);
		glyphs.get(0).vbl.appendToBuffer(bb);
		glyphs.get(0).vbl.appendToBuffer(bb);
		glyphs.get(0).vtr.appendToBuffer(bb);
		glyphs.get(0).vbr.appendToBuffer(bb);
		emptyGlyphs.add(glyphs.get(0));
		
		int gc = glyphs.size();
		for(int i = 1; i < gc; i++) {
			SpriteGlyph glyph = glyphs.get(i);
			call = call.Append(glyph, batches);
			glyph.vtl.appendToBuffer(bb);
			glyph.vtr.appendToBuffer(bb);
			glyph.vbl.appendToBuffer(bb);
			glyph.vbl.appendToBuffer(bb);
			glyph.vtr.appendToBuffer(bb);
			glyph.vbr.appendToBuffer(bb);
			emptyGlyphs.add(glyphs.get(i));
		}
		bb.flip();
		glyphs = null;

		// Set The Buffer Data
		glBindBuffer(BufferTarget.ArrayBuffer, vbo);
		if(gc > glyphCapacity) {
			glyphCapacity = gc * 2;
			glBufferData(
					BufferTarget.ArrayBuffer,
					(glyphCapacity * 6) * VertexSpriteBatch.Size,
					bufUsage
					);
		}
		glBufferSubData(BufferTarget.ArrayBuffer, 0, bb);
		GLBuffer.unbind(BufferTarget.ArrayBuffer);
	}
	/**
	 * Batches Draw Calls Appropriately
	 * @param spriteSortMode {@link SpriteSortMode}
	 */
	public void end(int spriteSortMode) {
		sortGlyphs(spriteSortMode);
		generateBatches();
	}

	public void renderBatch(Matrix4 mWorld, Matrix4 mCamera, BlendState bs, SamplerState ss, DepthState ds, RasterizerState rs, Vector4 cMul, Vector4 cAdd) {
		// Set Up Render State
		if(bs == null) bs = BlendState.PREMULTIPLIED_ALPHA_BLEND;
		if(ds == null) ds = DepthState.NONE;
		if(rs == null) rs = RasterizerState.CULL_NONE;
		if(ss == null) ss = SamplerState.LINEAR_WRAP;
		if(cMul == null) cMul = DEFAULT_COL_MULT;
		if(cAdd == null) cAdd = DEFAULT_COL_ADD;
		bs.set();
		ds.set();
		rs.set();

		// Setup The Program
		glUseProgram(idProg);

		// Set Up The Matrices
		GLUniform.setST(unWorld, mWorld, false);
		GLUniform.setST(unVP, mCamera, false);
		GLUniform.set(unColMult, cMul);
		GLUniform.set(unColAdd, cAdd);

		glBindBuffer(BufferTarget.ArrayBuffer, vbo);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glVertexAttribPointer(0, 3, GLType.Float, false, VertexSpriteBatch.Size, 0);
		glVertexAttribPointer(1, 4, GLType.UnsignedByte, true, VertexSpriteBatch.Size, 36);
		glVertexAttribPointer(2, 2, GLType.Float, false, VertexSpriteBatch.Size, 12);
		glVertexAttribPointer(3, 4, GLType.Float, false, VertexSpriteBatch.Size, 20);
		
		// Draw All The Batches
		int bc = batches == null ? 0 : batches.size();
		for(int i = 0; i < bc; i++) {
			SpriteBatchCall batch = batches.get(i);
			batch.Texture.use(TextureUnit.Texture0, unTexture);
			ss.set(batch.Texture.getTarget());
			glDrawArrays(PrimitiveType.Triangles, batch.IndexOffset, batch.Indices);
			batch.Texture.unuse();
		}

		GLProgram.unuse();
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glBindBuffer(BufferTarget.ArrayBuffer, 0);
	}
	public void renderBatch(Matrix4 mWorld, Matrix4 mCamera, BlendState bs, SamplerState ss, DepthState ds, RasterizerState rs) {
		renderBatch(mWorld, mCamera, bs, ss, ds, rs, null, null);
	}
}
