package gl;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ZERO;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;
import java.util.List;

import camera.Camera;
import egl.math.Matrix3;
import egl.math.Matrix4;

public class RenderContext {
	private final ShaderProgram program;
	private final List<Renderable> objs = new ArrayList<>();
	private final Camera camera;
	private static RenderGrid grid;

	private static String WORLD_MAT_NAME = "worldMat";
	private static String WORLDIT_MAT_NAME = "worldMatIT";
	private static String VIEWPROJ_MAT_NAME = "viewProjMat";
	private static String COLOR_VEC_NAME = "color";
	private static String MODE_FLOAT_NAME = "mode";


	public RenderContext (ShaderProgram p, Camera camera) throws Exception {
		this.program = p;
		this.camera = camera;
		this.grid = new RenderGrid(20);
		grid.init();
	}

	public void addObj (Renderable obj) {
		objs.add(obj);
		if(obj != null && obj.getMesh() != null) {
			obj.getMesh().init();
		}
	}

	public void removeObj (Renderable obj) {
		if (obj != null && obj.getMesh() != null) {
			obj.getMesh().cleanUp();
		}
		objs.remove(obj);
	}

	private void setUniforms(Renderable obj) throws Exception {
		Matrix4 m = new Matrix4();
		camera.getViewProjectionMatrix(m);
		program.setUniform(VIEWPROJ_MAT_NAME, m);
		m.set(obj.getWorldTransform());
		program.setUniform(WORLD_MAT_NAME, m);
		program.setUniform(COLOR_VEC_NAME, obj.getColor());
		Matrix3 n = new Matrix3();
		n.set(obj.getWorldTransformIT());
		program.setUniform(WORLDIT_MAT_NAME, n);
		program.setUniform(MODE_FLOAT_NAME, obj.getMode());
	}
	
	private void setGridUniforms() throws Exception {
		Matrix4 m = new Matrix4();
		camera.getViewProjectionMatrix(m);
		program.setUniform(VIEWPROJ_MAT_NAME, m);
		m.setIdentity();
		program.setUniform(WORLD_MAT_NAME, m);
		program.setUniform(MODE_FLOAT_NAME, 2);
	}

	public void init() throws Exception {
		program.link();
		program.init();

	}

	public void cleanup () {
		program.cleanup();
	}

	public void render() throws Exception{
		program.bind();
		
		// render grid 
		glEnable(GL_DEPTH_TEST);
		setGridUniforms();
		grid.render();
		
		for (Renderable obj : objs) {
			setUniforms(obj);
			obj.render();
		}
		
		
	    program.unbind();
	}
}
