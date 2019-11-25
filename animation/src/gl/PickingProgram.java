package gl;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import egl.BlendState;
import egl.GL.PixelFormat;
import egl.GL.PixelType;
import egl.ArrayBind;
import egl.GL;
import egl.GLProgram;
import egl.GLUniform;
import egl.IDisposable;
import egl.NativeMem;
import egl.Semantic;
import egl.ShaderInterface;
import egl.math.Matrix4;

public class PickingProgram implements IDisposable {
	private final GLProgram program = new GLProgram(false);
	public final ShaderInterface fxsi = new ShaderInterface(RenderMesh.VERTEX_DECLARATION);
	
	private final ByteBuffer ibID = NativeMem.createByteBuffer(4);
	
	public PickingProgram() {
		program.quickCreateResource("Pick", "gl/Pick.vert", "gl/Pick.frag", null);
		fxsi.build(program.semanticLinks);
	}
	@Override
	public void dispose() {
		program.dispose();
	}
	
	public int getPositionAttributeLocation() {
		for(ArrayBind b : fxsi.binds) {
			if(b.semantic == Semantic.Position) return b.location;
		}
		return GL.BadAttributeLocation;
	}
	
	public void use(Matrix4 mCamera) {
		program.use();
		
		BlendState.OPAQUE.set();
		
		GLUniform.setST(program.getUniform("VP"), mCamera, false);
	}
	public void setObject(Matrix4 mWorld, int id) {
		GLUniform.setST(program.getUniform("World"), mWorld, false);

		int r = id & 0x000000FF;
		int g = id >> 8 & 0x000000FF;
		int b = id >> 16 & 0x000000FF;
		int a = id >> 24 & 0x000000FF;
		GL20.glUniform4f(program.getUniform("ID"), r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}
	
	public int getID(int x, int y) {
		ibID.clear();
		GL11.glReadPixels(x, y, 1, 1, PixelFormat.Rgba, PixelType.UnsignedByte, ibID);
		return ibID.asIntBuffer().get(0);
	}
}
