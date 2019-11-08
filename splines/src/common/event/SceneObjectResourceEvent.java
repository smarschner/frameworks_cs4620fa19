package common.event;

import common.SceneObject;

public class SceneObjectResourceEvent extends SceneEvent {
	public static enum Type {
		Material,
		Mesh
	}
	
	public final Type type;
	public final SceneObject object;
	
	public SceneObjectResourceEvent(SceneObject o, Type t) {
		super(SceneDataType.Object);
		type = t;
		object = o;
	}
}
