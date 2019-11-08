package common.event;

public class SceneCollectionModifiedEvent extends SceneEvent {
	public final String name;
	public final boolean isAdded;
	
	public SceneCollectionModifiedEvent(SceneDataType t, String n, boolean added) {
		super(t);
		name = n;
		isAdded = added;
	}
}
