package blister.input;

import ext.csharp.EventArgs;

public class MouseMoveEventArgs extends EventArgs {
	public final MouseState state;
	public int dx;
	public int dy;
	
	public MouseMoveEventArgs(MouseState s) {
		state = s;
	}
}
