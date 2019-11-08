package ext.csharp;

public abstract class ACEventFuncRef<L, T extends EventArgs> {
	public abstract void Receive(L o, Object sender, T args);
}
