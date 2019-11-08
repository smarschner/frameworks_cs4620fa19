package gl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import egl.BlendState;
import egl.DepthState;
import egl.GL;
import egl.GLProgram;
import egl.IDisposable;
import egl.GL.GLType;
import egl.GL.PrimitiveType;
import egl.GLError;
import egl.RasterizerState;

public class Renderer implements IDisposable {
	private static final Comparator<RenderObject> cmpMesh = new Comparator<RenderObject>() {
		@Override
		public int compare(RenderObject arg0, RenderObject arg1) {
			return Integer.compare(arg0.mesh.vBuffer.getID(), arg1.mesh.vBuffer.getID());
		}
	};
	private static final Comparator<RenderObject> cmpMaterial = new Comparator<RenderObject>(){
		@Override
		public int compare(RenderObject arg0, RenderObject arg1) {
			return Integer.compare(arg0.mesh.vBuffer.getID(), arg1.mesh.vBuffer.getID());
		}
	};

	public final PickingProgram pickProgram = new PickingProgram();
	
	private static class RenderPass {
		public RenderMaterial material;
		public RenderMesh mesh;
		public final ArrayList<RenderObject> objects = new ArrayList<>();
	}
	private ArrayList<RenderPass> passes = null;
	
	@Override
	public void dispose() {
		pickProgram.dispose();
	}
	
	public void buildPasses(RenderObject root) {
		// Create An Array Of Objects To Draw From The Root
		ArrayList<RenderObject> objs = new ArrayList<>();
		addToDrawList(root, objs);

		// Sort To Allow For Following Ordering: For Each Material, For Each Mesh
		Collections.sort(objs, cmpMesh);
		Collections.sort(objs, cmpMaterial);

		// Add Objects To Render Passes
		passes = new ArrayList<>();
		RenderPass curPass = new RenderPass();
		for(RenderObject ro : objs) {
			if(ro.mesh != curPass.mesh || ro.material != curPass.material) {
				curPass = new RenderPass();
				curPass.material = ro.material;
				curPass.mesh = ro.mesh;
				passes.add(curPass);
			}
			curPass.objects.add(ro);
		}
	}
	private void addToDrawList(RenderObject ro, ArrayList<RenderObject> objs) {
		if(ro.mesh != null && ro.material != null) objs.add(ro);
		for(RenderObject c : ro.children) {
			addToDrawList(c, objs);
		}
	}

	public void draw(RenderCamera camera, ArrayList<RenderLight> lights) {
		draw(camera, lights, 0.0f);
	}
	
	public void draw(RenderCamera camera, ArrayList<RenderLight> lights, float time) {
		DepthState.DEFAULT.set();
		BlendState.OPAQUE.set();
		RasterizerState.CULL_CLOCKWISE.set();
		
		// Draw Up To 16 Lights
		int cc = lights.size() > 16 ? 16 : lights.size();

		RenderMaterial material = null;
		RenderMesh mesh = null;
		for(RenderPass p : passes) {
			if(material != p.material) {
				material = p.material;
				material.program.use();
				material.useMaterialProperties();
				material.useCameraAndLights(camera, lights, 0, cc);
				material.useTime(time);
			}
			if(mesh != p.mesh) {
				if(mesh != null) mesh.iBuffer.unbind();
				mesh = p.mesh;
				mesh.iBuffer.bind();
			}

			mesh.vBuffer.useAsAttrib(material.shaderInterface);
			mesh.vBufferTangentSpace.useAsAttrib(material.shaderInterfaceTangentSpace);
			if(mesh.vBufferSkinned.getIsCreated()) {
				mesh.vBufferSkinned.useAsAttrib(material.shaderInterfaceSkinned);
			}
			for(RenderObject ro : p.objects) {
				material.useObject(ro);
				GL11.glDrawElements(PrimitiveType.Triangles, mesh.indexCount, GLType.UnsignedInt, 0);
				GLError.get("Draw");
			}
		}
	}
	
	public void draw(RenderCamera camera, ArrayList<RenderLight> lights, RasterizerState rs) {
		DepthState.DEFAULT.set();
		BlendState.OPAQUE.set();
		if(rs != null)
			rs.set();
		else
			RasterizerState.CULL_CLOCKWISE.set();
		
		// Draw Up To 16 Lights
		int cc = lights.size() > 16 ? 16 : lights.size();

		RenderMaterial material = null;
		RenderMesh mesh = null;
		for(RenderPass p : passes) {
			if(material != p.material) {
				material = p.material;
				material.program.use();
				material.useMaterialProperties();
				material.useCameraAndLights(camera, lights, 0, cc);
			}
			if(mesh != p.mesh) {
				if(mesh != null) mesh.iBuffer.unbind();
				mesh = p.mesh;
				mesh.iBuffer.bind();
			}

			mesh.vBuffer.useAsAttrib(material.shaderInterface);
			for(RenderObject ro : p.objects) {
				material.useObject(ro);
				GL11.glDrawElements(PrimitiveType.Triangles, mesh.indexCount, GLType.UnsignedInt, 0);
				GLError.get("Draw");
			}
		}
		GLProgram.unuse();
	}

	public void beginPickingPass(RenderCamera camera) {
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glClearDepth(1.0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		DepthState.DEFAULT.set();
		BlendState.OPAQUE.set();
		RasterizerState.CULL_CLOCKWISE.set();
		
		pickProgram.use(camera.mViewProjection);		
	}
	public void drawPassesPick() {
		RenderMesh mesh = null;
		for(RenderPass p : passes) {
			if(mesh != p.mesh) {
				if(mesh != null) mesh.iBuffer.unbind();
				mesh = p.mesh;
				mesh.iBuffer.bind();
			}

			mesh.vBuffer.useAsAttrib(pickProgram.fxsi);
			for(RenderObject ro : p.objects) {
				pickProgram.setObject(ro.mWorldTransform, ro.sceneObject.getID().id);
				GL11.glDrawElements(PrimitiveType.Triangles, mesh.indexCount, GLType.UnsignedInt, 0);
			}
		}		
	}
	public int getPickID(int x, int y) {
		int id = pickProgram.getID(x, y);
		return id;
	}
}
