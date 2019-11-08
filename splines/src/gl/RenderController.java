package gl;

import java.util.ArrayList;

import anim.AnimationEngine;
import common.Material;
import common.Mesh;
import common.Scene;
import common.SceneCamera;
import common.SceneObject;
import common.Texture;
import common.event.SceneCollectionModifiedEvent;
import common.event.SceneEvent;
import common.event.SceneEventQueue;
import common.event.SceneObjectResourceEvent;
import common.event.SceneReloadEvent;
import common.event.SceneTransformationEvent;
import egl.IDisposable;
import egl.math.Vector2;

public class RenderController implements IDisposable {
	public final SceneEventQueue queue;
	public final Scene scene;
	public RenderEnvironment env;
	public AnimationEngine animEngine;
	private boolean requestNewScene = false;
	
	public RenderController(Scene s, Vector2 viewSize) {
		scene = s;
		queue = new SceneEventQueue(scene);
		scene.addListener(queue);
		env = RenderTreeBuilder.build(scene, viewSize);
		animEngine = new AnimationEngine(scene);
		for(SceneObject o : scene.objects) {
			if(o instanceof SceneCamera) {
				System.out.println("Camera " + o.getID().name + " will not be animated");
			}
			else {
				animEngine.addObject(o.getID().name, o);
			}
		}
	}
	@Override
	public void dispose() {
		env.dispose();
	}

	public boolean isNewSceneRequested() {
		return requestNewScene;
	}
	
	public void update(Renderer r, CameraController camController) {
		ArrayList<SceneEvent> le = new ArrayList<>();
		queue.getEvents(le);
		if(le.size() == 0) return;
		
		boolean isTreeModified = false;
		boolean areTransformsModified = false;
		boolean areResourcesModified = false;
		
		for(SceneEvent e : le) {
			if(e instanceof SceneCollectionModifiedEvent) {
				SceneCollectionModifiedEvent cme = (SceneCollectionModifiedEvent)e;
				switch (cme.dataType) {
				case Texture:
					areResourcesModified = true;
					if(cme.isAdded) {
						Texture t = scene.textures.get(cme.name);
						if(t != null) env.addTexture(t);
					}
					else {
						env.removeTexture(cme.name);
					}
					break;
				case Mesh:
					areResourcesModified = true;
					if(cme.isAdded) {
						Mesh m = scene.meshes.get(cme.name);
						if(m != null) env.addMesh(m);
					}
					else {
						env.removeMesh(cme.name);
					}
					break;
				case Material:
					areResourcesModified = true;
					if(cme.isAdded) {
						Material m = scene.materials.get(cme.name);
						if(m != null) env.addMaterial(m);
					}
					else {
						env.removeMaterial(cme.name);
					}
					break;
				case Object:
					SceneObject o = scene.objects.get(cme.name);
					isTreeModified = !cme.isAdded || (o != null);
					if(o != null && o instanceof SceneCamera) {
						System.out.println("Camera " + cme.name + " will not be animated");
					}
					else {
						if(cme.isAdded) animEngine.addObject(cme.name, o);
						else animEngine.removeObject(cme.name);
					}
					break;
				default:
					break;
				}
			}
			else if(e instanceof SceneTransformationEvent) {
				areTransformsModified = true;
			}
			else if(e instanceof SceneObjectResourceEvent) {
				areResourcesModified = true;
			}
			else if(e instanceof SceneReloadEvent) {
				requestNewScene = true;
			}
		}
		
		if(isTreeModified) {
			RenderTreeBuilder.buildTree(scene, env);
			if(camController.camera != null) {
				SceneObject so = camController.camera.sceneObject;
				for(RenderCamera cam : env.cameras ) {
					if(cam.sceneObject == so) {
						camController.camera = cam;
						break;
					}
				}				
			}
			else if(env.cameras.size() > 0) {
				camController.camera = env.cameras.get(0);
			}
		}
		else if(areTransformsModified) {
			RenderTreeBuilder.rippleTransformations(env);
		}
		
		if(areResourcesModified || isTreeModified) {
			env.linkObjectResources();
			r.buildPasses(env.root);
		}
	}
}
