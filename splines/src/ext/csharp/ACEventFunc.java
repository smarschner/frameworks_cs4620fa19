package ext.csharp;

public abstract class ACEventFunc<T extends EventArgs>  {
	public abstract void receive(Object sender, T args);
}
