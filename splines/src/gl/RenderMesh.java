package gl;


import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import common.Mesh;
import mesh.MeshData;
import egl.ArrayBind;
import egl.GL.BufferTarget;
import egl.GL.BufferUsageHint;
import egl.GL.GLType;
import egl.GLBuffer;
import egl.GLError;
import egl.IDisposable;
import egl.NativeMem;
import egl.Semantic;
import egl.math.Vector2;
import egl.math.Vector3;
import egl.math.Vector3i;

public class RenderMesh implements IDisposable {
	private static final int VERTEX_SIZE = 8 * 4;
	public static final ArrayBind[] VERTEX_DECLARATION = {
		new ArrayBind(Semantic.Position, GLType.Float, 3, 0),
		new ArrayBind(Semantic.Normal, GLType.Float, 3, 3 * 4),
		new ArrayBind(Semantic.TexCoord, GLType.Float, 2, 6 * 4)
	};
	private static final int VERTEX_SIZE_TANGENT_SPACE = 6 * 4;
	public static final ArrayBind[] VERTEX_DECLARATION_TANGENT_SPACE = {
		new ArrayBind(Semantic.Tangent, GLType.Float, 3, 0),
		new ArrayBind(Semantic.Bitangent, GLType.Float, 3, 3 * 4)
	};
	private static final int VERTEX_SIZE_SKINNED = 8 * 4;
	public static final ArrayBind[] VERTEX_DECLARATION_SKINNED = {
		new ArrayBind(Semantic.BlendIndices, GLType.Float, 4, 0),
		new ArrayBind(Semantic.BlendWeight, GLType.Float, 4, 4 * 4)
	};

	public final GLBuffer vBuffer = new GLBuffer(BufferTarget.ArrayBuffer, BufferUsageHint.StaticDraw, false);
	public final GLBuffer vBufferTangentSpace = new GLBuffer(BufferTarget.ArrayBuffer, BufferUsageHint.StaticDraw, false);
	public final GLBuffer vBufferSkinned = new GLBuffer(BufferTarget.ArrayBuffer, BufferUsageHint.StaticDraw, false);
	public final GLBuffer iBuffer = new GLBuffer(BufferTarget.ElementArrayBuffer, BufferUsageHint.StaticDraw, false);
	public int vertexCount;
	public int indexCount;

	public final Mesh sceneMesh;

	public RenderMesh(Mesh m) {
		sceneMesh = m;
	}
	@Override
	public void dispose() {
		vBuffer.dispose();
		vBufferTangentSpace.dispose();
		vBufferSkinned.dispose();
		iBuffer.dispose();
	}

	public void build(MeshData data) {
		vertexCount = data.vertexCount;
		indexCount = data.indexCount;

		// Interlace The Data
		ByteBuffer bb = NativeMem.createByteBuffer(vertexCount * VERTEX_SIZE);
		data.positions.position(0);
		data.positions.limit(vertexCount * 3);
		data.normals.position(0);
		data.normals.limit(vertexCount * 3);
		data.uvs.position(0);
		data.uvs.limit(vertexCount * 2);
		for(int i = 0;i < vertexCount;i++) {
			bb.putFloat(data.positions.get());
			bb.putFloat(data.positions.get());
			bb.putFloat(data.positions.get());
			bb.putFloat(data.normals.get());
			bb.putFloat(data.normals.get());
			bb.putFloat(data.normals.get());
			bb.putFloat(data.uvs.get());
			bb.putFloat(data.uvs.get());
		}
		bb.flip();

		// Send Data To GPU
		vBuffer.init();
		vBuffer.setAsVertex(VERTEX_SIZE);
		vBuffer.setDataInitial(bb);
		GLError.get("RenderMesh init: vertex setDataInitial");
		
		iBuffer.init();
		iBuffer.setAsIndexInt();
		data.indices.position(0);
		data.indices.limit(indexCount);
		iBuffer.setDataInitial(data.indices);

		// Calculate Tangent Space Information
		FloatBuffer fb = NativeMem.createFloatBuffer(vertexCount * VERTEX_SIZE_TANGENT_SPACE / 4);
		int ii = 0;
		Vector3[] pos = {
				new Vector3(),
				new Vector3(),
				new Vector3()
		};
		Vector2[] uv = {
				new Vector2(),
				new Vector2(),
				new Vector2()
		};
		Vector3i vertIndices = new Vector3i();
		while(ii < indexCount) {
			vertIndices.set(
					data.indices.get(ii),
					data.indices.get(ii + 1),
					data.indices.get(ii + 2)
					);
			ii += 3;

			// Get All The Positions And UVs On The Triangle
			for(int vi = 0;vi < 3;vi++) {
				int vpo = vertIndices.get(vi) * 3;
				pos[vi].set(
						data.positions.get(vpo),
						data.positions.get(vpo + 1),
						data.positions.get(vpo + 2)
						);
				int vto = vertIndices.get(vi) * 2;
				uv[vi].set(
						data.uvs.get(vto),
						data.uvs.get(vto + 1)
						);
			}

			pos[1].sub(pos[0]);
			pos[2].sub(pos[0]);
			uv[1].sub(uv[0]);
			uv[2].sub(uv[0]).negate();
			
			float r = 1.0f / (uv[1].x * uv[2].y - uv[1].y * uv[2].x);
			Vector3 tangent = (pos[1].clone().mul(uv[2].y).sub(pos[2].clone().mul(uv[1].y))).mul(r).normalize();
			Vector3 bitangent = (pos[2].mul(uv[1].x).sub(pos[1].mul(uv[2].x))).mul(r).normalize();
			
			for(int vi = 0;vi < 3;vi++) {
				int vo = vertIndices.get(vi) * (VERTEX_SIZE_TANGENT_SPACE / 4);
				fb.put(vo, tangent.x);
				fb.put(vo + 1, tangent.y);
				fb.put(vo + 2, tangent.z);
				fb.put(vo + 3, bitangent.x);
				fb.put(vo + 4, bitangent.y);
				fb.put(vo + 5, bitangent.z);
			}
		}
		fb.position(0);
		fb.limit(vertexCount * VERTEX_SIZE_TANGENT_SPACE / 4);
		GLError.get("RenderMesh init: index setDataInitial");
		
		vBufferTangentSpace.init();
		vBufferTangentSpace.setAsVertex(VERTEX_SIZE_TANGENT_SPACE);
		vBufferTangentSpace.setDataInitial(fb);
	}
	public void addSkinningInformation(FloatBuffer indices, FloatBuffer weights) {
		ByteBuffer bb = NativeMem.createByteBuffer(vertexCount * VERTEX_SIZE_SKINNED);
		indices.position(0);
		indices.limit(vertexCount * 4);
		weights.position(0);
		weights.limit(vertexCount * 4);
		for(int i = 0;i < vertexCount;i++) {
			bb.putFloat(indices.get());
			bb.putFloat(indices.get());
			bb.putFloat(indices.get());
			bb.putFloat(indices.get());
			bb.putFloat(weights.get());
			bb.putFloat(weights.get());
			bb.putFloat(weights.get());
			bb.putFloat(weights.get());
		}
		bb.flip();

		vBufferSkinned.init();
		vBufferSkinned.setAsVertex(VERTEX_SIZE_SKINNED);
		vBufferSkinned.setDataInitial(bb);
	}
}
