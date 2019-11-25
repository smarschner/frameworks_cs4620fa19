package blister.input;

import ext.csharp.EventArgs;

public class KeyPressEventArgs extends EventArgs {
	public char KeyChar;
	
	public KeyPressEventArgs() {
		KeyChar = 0;
	}
}