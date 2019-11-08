package gl.manip;

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import common.UUIDGenerator;
import mesh.OBJMesh_Archive;
import mesh.OBJParser;
import egl.GL.BufferTarget;
import egl.GL.BufferUsageHint;
import egl.GL.GLType;
import egl.GL.PrimitiveType;
import egl.GLBuffer;
import egl.GLProgram;
import egl.GLUniform;
import egl.IDisposable;
import egl.NativeMem;
import egl.math.Matrix4;
import egl.math.Vector3;
import egl.math.Vector3i;
import egl.math.Vector4;

public class ManipRenderer implements IDisposable {
	private static final Vector4[] AxisColors = new Vector4[3];
	static {
		AxisColors[Manipulator.Axis.X] = new Vector4(1, 0, 0, .3f);
		AxisColors[Manipulator.Axis.Y] = new Vector4(0, 1, 0, .3f);
		AxisColors[Manipulator.Axis.Z] = new Vector4(0, 0, 1, .3f);
	}
	
	GLProgram program = new GLProgram(true);
	
	public final GLBuffer vb = new GLBuffer(BufferTarget.ArrayBuffer, BufferUsageHint.StaticDraw, true);
	public final GLBuffer ib = new GLBuffer(BufferTarget.ElementArrayBuffer, BufferUsageHint.StaticDraw, true);
	
	int[] ind = new int[3];
	int[] count = new int[3];
	private final HashMap<Manipulator, UUIDGenerator.ID> manipIDs = new HashMap<>();
	
	public ManipRenderer() {
		OBJMesh_Archive omT = OBJParser.parse("data/meshes/Translate.obj");
		OBJMesh_Archive omR = OBJParser.parse("data/meshes/Rotate.obj");
		OBJMesh_Archive omS = OBJParser.parse("data/meshes/Scale.obj");
		
		ind[Manipulator.Type.SCALE] = 0;
		count[Manipulator.Type.SCALE] = omS.triangles.size() * 3;
		ind[Manipulator.Type.ROTATE] = count[Manipulator.Type.SCALE];
		count[Manipulator.Type.ROTATE] = omR.triangles.size() * 3;
		ind[Manipulator.Type.TRANSLATE] = count[Manipulator.Type.SCALE] + count[Manipulator.Type.ROTATE];
		count[Manipulator.Type.TRANSLATE] = omT.triangles.size() * 3;
		
		ByteBuffer bbVerts = NativeMem.createByteBuffer((omT.vertices.size()  + omR.vertices.size() + omS.vertices.size()) * 3 * 4);
		ByteBuffer bbInds = NativeMem.createByteBuffer((omT.triangles.size()  + omR.triangles.size() + omS.triangles.size()) * 3 * 4);

		int vi = addMesh(omS, bbVerts, bbInds, 0);
		vi = addMesh(omR, bbVerts, bbInds, vi);
		vi = addMesh(omT, bbVerts, bbInds, vi);
		
		bbVerts.flip();
		vb.setAsVertexVec3();
		vb.setDataInitial(bbVerts);
		
		bbInds.flip();
		ib.setAsIndexInt();
		ib.setDataInitial(bbInds);
		
		HashMap<String, Integer> attrMap = new HashMap<>();
		attrMap.put("vPos", 0);
		program.quickCreateResource("Manip", "gl/manip/Manip.vert", "gl/manip/Manip.frag", attrMap);
	}
	@Override
	public void dispose() {
		vb.dispose();
		ib.dispose();
		program.dispose();
	}

	private int addMesh(OBJMesh_Archive om, ByteBuffer vb, ByteBuffer ib, int vi) {
		for(Vector3i v : om.vertices) {
			Vector3 pos = om.positions.get(v.x);
			vb.putFloat(pos.x);
			vb.putFloat(pos.y);
			vb.putFloat(pos.z);
		}
		for(Vector3i t : om.triangles) {
			ib.putInt(vi + t.x);
			ib.putInt(vi + t.y);
			ib.putInt(vi + t.z);
		}
		
		return vi + om.vertices.size();
	}
	
	public void drawCall(int type, int vertexPositionAttrib) {
		vb.useAsAttrib(vertexPositionAttrib);
		ib.bind();
		GL11.glDrawElements(PrimitiveType.Triangles, count[type], GLType.UnsignedInt, ind[type] * 4);
		ib.unbind();
	}

	public void setIDs(HashMap<Manipulator, UUIDGenerator.ID> ids) {
		manipIDs.clear();
		manipIDs.putAll(ids);
	}
	
	public void render(Matrix4 mWorld, Matrix4 mCamera, int type, int axis) {
		program.use();
		
		GLUniform.set(program.getUniform("AxisColor"), AxisColors[axis]);
		GLUniform.setST(program.getUniform("World"), mWorld, false);
		GLUniform.setST(program.getUniform("VP"), mCamera, false);
		
		drawCall(type, 0);
		
		GLProgram.unuse();
	}
}
