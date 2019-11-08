package scene.form;

import java.util.ArrayList;
import java.util.Random;

import common.Material;
import common.Mesh;
import common.Scene;
import common.Texture;
import common.event.SceneCollectionModifiedEvent;
import common.event.SceneEvent;
import common.event.SceneEventQueue;
import common.event.SceneReloadEvent;
import scene.SceneApp;

public class ControlWindowUpdater implements Runnable {
	public final ControlWindow window;
	public SceneEventQueue queue;

	static Random r = new Random();
	
	RPMaterialData materials;
	RPMeshData meshes;
	RPTextureData textures;
	ScenePanel objects;
	
	private final SceneApp app;
	ArrayList<ValueUpdatable> updatePanels;

	public ControlWindowUpdater(SceneApp a, ControlWindow w) {
		window = w;
		app = a;
		
		textures = (RPTextureData)window.tabs.get("Texture");
		meshes = (RPMeshData)window.tabs.get("Mesh");
		materials = (RPMaterialData)window.tabs.get("Material");
		objects = (ScenePanel)window.tabs.get("Object");
		
		onNewScene(app.scene);
	}
	
	public void onNewScene(Scene s) {
		queue = new SceneEventQueue(s);
		s.addListener(queue);

		if(textures != null) {
			textures.setScene(s);
			for(Texture t : s.textures) {
				textures.addData(t.getID().name);
			}
		}
		if(meshes != null) {
			meshes.setScene(s);
			for(Mesh m : s.meshes) {
				meshes.addData(m.getID().name);
			}
		}
		if(materials != null) {
			materials.setScene(s);
			for(Material m : s.materials) {
				materials.addData(m.getID().name);
			}
		}
		if(objects != null) {
			objects.setScene();
		}
	}

	@Override
	public void run() {
		while(true) {
			objects.updateValues();
			ArrayList<SceneEvent> a = new ArrayList<>();
			queue.getEvents(a);
			boolean newSceneFound = false;
			if(a.size() != 0 && !newSceneFound) {
				boolean rebuildScene = false;
				for(SceneEvent e : a) {
					if(e instanceof SceneCollectionModifiedEvent) {
						SceneCollectionModifiedEvent cme = (SceneCollectionModifiedEvent)e;
						switch (cme.dataType) {
						case Material:
							if(materials == null) continue;
							if(cme.isAdded) materials.addData(cme.name);
							else materials.removeData(cme.name);
							break;
						case Mesh:
							if(meshes == null) continue;
							if(cme.isAdded) meshes.addData(cme.name);
							else meshes.removeData(cme.name);
							break;
						case None:
							break;
						case Object:
							if(objects == null) continue;
							rebuildScene = true;
							break;
						case Texture:
							if(textures == null) continue;
							if(cme.isAdded) textures.addData(cme.name);
							else textures.removeData(cme.name);
							break;
						default:
							break;
						}
					}
					else if(e instanceof SceneReloadEvent) {
						onNewScene(app.scene);
						rebuildScene = false;
						newSceneFound = true;
						break;
					}
				}
				
				if(rebuildScene) objects.rebuildSceneStructure();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
