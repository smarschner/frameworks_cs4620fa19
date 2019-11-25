package common.event;

import common.SceneObject;

public class SceneTransformationEvent extends SceneEvent {
	public final SceneObject object;
	
	public SceneTransformationEvent(SceneObject o) {
		super(SceneDataType.Object);
		object = o;
	}
}
