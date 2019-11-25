package gl;

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import egl.ArrayBind;
import egl.BlendState;
import egl.DepthState;
import egl.GL.BufferTarget;
import egl.GL.BufferUsageHint;
import egl.GL.GLType;
import egl.GL.PrimitiveType;
import egl.GLBuffer;
import egl.GLProgram;
import egl.GLUniform;
import egl.IDisposable;
import egl.NativeMem;
import egl.RasterizerState;
import egl.Semantic;
import egl.math.Vector4;

public class GridRenderer implements IDisposable {
	
	private static final int GRID_SIZE = 10;
	private static final Vector4 gridColor = new Vector4(0.5f, 0.5f, 0.5f, 0.5f);
	
	private static final float AXES_LINEWIDTH = 3.0f;
	private static final float AXES_LENGTH = 1.0f;
	private static final Vector4 gridColorX = new Vector4(0.8f, 0.0f, 0.0f, 0.5f);
	private static final Vector4 gridColorY = new Vector4(0.0f, 0.6f, 0.0f, 0.5f);
	private static final Vector4 gridColorZ = new Vector4(0.0f, 0.0f, 0.8f, 0.5f);
	
	private static final int VERTEX_SIZE = 3 * 4;
	public static final ArrayBind[] VERTEX_DECLARATION = {
		new ArrayBind(Semantic.Position, GLType.Float, 3, 0),
	};
	
	final GLBuffer vBuffer = new GLBuffer(BufferTarget.ArrayBuffer, BufferUsageHint.StaticDraw, false);
	final GLBuffer vBuffer_axes = new GLBuffer(BufferTarget.ArrayBuffer, BufferUsageHint.StaticDraw, false);
	final GLBuffer iBuffer = new GLBuffer(BufferTarget.ElementArrayBuffer, BufferUsageHint.StaticDraw, false);
	final GLBuffer iBuffer_x = new GLBuffer(BufferTarget.ElementArrayBuffer, BufferUsageHint.StaticDraw, false);
	final GLBuffer iBuffer_y = new GLBuffer(BufferTarget.ElementArrayBuffer, BufferUsageHint.StaticDraw, false);
	final GLBuffer iBuffer_z = new GLBuffer(BufferTarget.ElementArrayBuffer, BufferUsageHint.StaticDraw, false);

	GLProgram program = new GLProgram(true);

	public GridRenderer() {
		
		// Grid goes from -GRID_SIZE to GRID_SIZE in x and z.
		// First 10 points are -x, then +x, -z, +z.
		ByteBuffer vbb = NativeMem.createByteBuffer(VERTEX_SIZE * 4 * (2*GRID_SIZE + 1));
		for (int i = 0; i < 2*GRID_SIZE + 1; i++) {
			vbb.putFloat(-GRID_SIZE);
			vbb.putFloat(0);
			vbb.putFloat(i - GRID_SIZE);
		}
		for (int i = 0; i < 2*GRID_SIZE + 1; i++) {
			vbb.putFloat(GRID_SIZE);
			vbb.putFloat(0);
			vbb.putFloat(i - GRID_SIZE);
		}
		for (int i = 0; i < 2*GRID_SIZE + 1; i++) {
			vbb.putFloat(i - GRID_SIZE);
			vbb.putFloat(0);
			vbb.putFloat(-GRID_SIZE);
		}
		for (int i = 0; i < 2*GRID_SIZE + 1; i++) {
			vbb.putFloat(i - GRID_SIZE);
			vbb.putFloat(0);
			vbb.putFloat(GRID_SIZE);
		}
		vbb.flip();
		
		ByteBuffer vbb_axes = NativeMem.createByteBuffer(VERTEX_SIZE * 4);
		vbb_axes.putFloat(0); vbb_axes.putFloat(0); vbb_axes.putFloat(0);
		vbb_axes.putFloat(AXES_LENGTH); vbb_axes.putFloat(0); vbb_axes.putFloat(0);
		vbb_axes.putFloat(0); vbb_axes.putFloat(AXES_LENGTH); vbb_axes.putFloat(0);
		vbb_axes.putFloat(0); vbb_axes.putFloat(0); vbb_axes.putFloat(AXES_LENGTH);
		vbb_axes.flip();

		ByteBuffer ibb = NativeMem.createByteBuffer(4 * 4 * (2*GRID_SIZE + 1));
		for (int i = 0; i < 2*GRID_SIZE + 1; i++) {
			ibb.putInt(i);
			ibb.putInt(i + (2*GRID_SIZE + 1));
			ibb.putInt(i + 2 * (2*GRID_SIZE + 1));
			ibb.putInt(i + 3 * (2*GRID_SIZE + 1));
		}
		ibb.flip();
		
		ByteBuffer ibb_x = NativeMem.createByteBuffer(4 * 2);
		ibb_x.putInt(0); ibb_x.putInt(1);
		ibb_x.flip();
		
		ByteBuffer ibb_y = NativeMem.createByteBuffer(4 * 2);
		ibb_y.putInt(0); ibb_y.putInt(2);
		ibb_y.flip();
		
		ByteBuffer ibb_z = NativeMem.createByteBuffer(4 * 2);
		ibb_z.putInt(0); ibb_z.putInt(3);
		ibb_z.flip();

		// Send Data To GPU
		vBuffer.init();
		vBuffer.setAsVertexVec3();
		vBuffer.setDataInitial(vbb);
		
		vBuffer_axes.init();
		vBuffer_axes.setAsVertexVec3();
		vBuffer_axes.setDataInitial(vbb_axes);
		
		iBuffer.init();
		iBuffer.setAsIndexInt();
		iBuffer.setDataInitial(ibb);
		
		iBuffer_x.init();
		iBuffer_x.setAsIndexInt();
		iBuffer_x.setDataInitial(ibb_x);
		
		iBuffer_y.init();
		iBuffer_y.setAsIndexInt();
		iBuffer_y.setDataInitial(ibb_y);
		
		iBuffer_z.init();
		iBuffer_z.setAsIndexInt();
		iBuffer_z.setDataInitial(ibb_z);
		
		HashMap<String, Integer> attrMap = new HashMap<>();
		attrMap.put("vPos", 0);
		program.quickCreateResource("Grid", "gl/Grid.vert", "gl/Grid.frag", attrMap);
	}
	
	@Override
	public void dispose() {
		vBuffer.dispose();
		iBuffer.dispose();
		program.dispose();
	}
	
	public void draw(RenderCamera camera) {
		program.use();
		
		GLUniform.setST(program.getUniform("VP"), camera.mViewProjection, false);
		GLUniform.set(program.getUniform("uGridColor"), gridColor);
				
		DepthState.DEFAULT.set();
		BlendState.OPAQUE.set();
		RasterizerState.CULL_CLOCKWISE.set();

		vBuffer.useAsAttrib(0);
		iBuffer.bind();
		GL11.glDrawElements(PrimitiveType.Lines, 4 * (2*GRID_SIZE + 1), GLType.UnsignedInt, 0);
		iBuffer.unbind();

		GL11.glLineWidth(AXES_LINEWIDTH);
		
		GLUniform.set(program.getUniform("uGridColor"), gridColorX);
		vBuffer_axes.useAsAttrib(0);
		iBuffer_x.bind();
		GL11.glDrawElements(PrimitiveType.Lines, 2, GLType.UnsignedInt, 0);
		iBuffer_x.unbind();

		GLUniform.set(program.getUniform("uGridColor"), gridColorY);
		vBuffer_axes.useAsAttrib(0);
		iBuffer_y.bind();
		GL11.glDrawElements(PrimitiveType.Lines, 2, GLType.UnsignedInt, 0);
		iBuffer_y.unbind();

		GLUniform.set(program.getUniform("uGridColor"), gridColorZ);
		vBuffer_axes.useAsAttrib(0);
		iBuffer_z.bind();
		GL11.glDrawElements(PrimitiveType.Lines, 2, GLType.UnsignedInt, 0);
		iBuffer_z.unbind();
		
		GL11.glLineWidth(1.0f);

		GLProgram.unuse();
	}
}
