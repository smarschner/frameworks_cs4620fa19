package scene.form;

public abstract class OnRPModification {
	public abstract void onAddition(String name);
	public abstract void onDeletion(String name);
	public abstract void onFileLoad(String name, String fullPath);
}


