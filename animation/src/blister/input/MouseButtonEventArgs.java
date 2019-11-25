package blister.input;

import ext.csharp.EventArgs;

public class MouseButtonEventArgs extends EventArgs {
	public final MouseState State;
	public int button;
	
	public MouseButtonEventArgs(MouseState s) {
		State = s;
	}
	
	public String toString() {
		return "[MouseButtonEventArgs state: " + State + ", button: " + button + "]";
	}
}
