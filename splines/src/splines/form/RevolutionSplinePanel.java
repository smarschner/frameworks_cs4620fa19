package splines.form;

import org.lwjgl.opengl.GL11;

import splines.SplineCurve;
import egl.GLBuffer;
import egl.GLError;
import egl.RasterizerState;

public class RevolutionSplinePanel extends SplinePanel {
	
	public SplineCurve toRevolve;
	
	public RevolutionSplineScreen owner;
	
	public boolean clickStartedHere= false;
	
	public float scale= 1.0f;
	
	GLBuffer vertexPositions, vertexNormals, triangleIndices;
	
	public RevolutionSplinePanel(int index, SplineCurve toRevolve, RevolutionSplineScreen owner) {
		this.toRevolve= toRevolve;
		this.owner= owner;
		this.index= index;
	}
	
	@Override
	public void draw() {
		GL11.glViewport(this.index * SplinePanel.panelWidth, 0, SplinePanel.panelWidth, SplinePanel.panelHeight);
		owner.rController.update(owner.renderer, owner.camController);
		
		if(owner.camController.camera != null){
			owner.renderer.draw(owner.camController.camera, owner.rController.env.lights, RasterizerState.CULL_NONE);
			
			if (owner.showGrid)
				owner.gridRenderer.draw(owner.camController.camera);
		}
        GLError.get("draw");
	}
}
