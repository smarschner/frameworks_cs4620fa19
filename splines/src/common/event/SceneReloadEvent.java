package common.event;

public class SceneReloadEvent extends SceneEvent {
	public final String file;
	
	public SceneReloadEvent(String f) {
		super(SceneDataType.None);
		file = f;
	}
}
