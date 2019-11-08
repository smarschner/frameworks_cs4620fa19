package blister.input;

import ext.csharp.EventArgs;

public class MouseWheelEventArgs extends EventArgs {
	public final MouseState State;
	public int ScrollChange;
	
	public MouseWheelEventArgs(MouseState s) {
		State = s;
	}
}
