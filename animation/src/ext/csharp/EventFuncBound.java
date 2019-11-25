package ext.csharp;

public class EventFuncBound<L, T extends EventArgs> extends ACEventFunc<T> {
	private final L refObj;
	private final ACEventFuncRef<L, T> function;
	public EventFuncBound(L bound, ACEventFuncRef<L, T> f) {
		refObj = bound;
		function = f;
	}
	@Override
	public void receive(Object sender, T args) {
		function.Receive(refObj, sender, args);
	}
}
