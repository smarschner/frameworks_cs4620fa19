package gl;

import common.SceneLight;
import common.SceneObject;

public class RenderLight extends RenderObject {
	/**
	 * Reference to scene counterpart (specialized)
	 */
	public final SceneLight sceneLight;
	
	public RenderLight(SceneObject o) {
		super(o);
		sceneLight = (SceneLight)sceneObject;
	}
}
