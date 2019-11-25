package blister.input;

import org.lwjgl.input.Keyboard;

import ext.csharp.EventArgs;

public class KeyboardKeyEventArgs extends EventArgs {
	public int key;
	public boolean isRepeat;
	public final boolean[] keyStates;

	public boolean getAlt() { return keyStates[Keyboard.KEY_LMENU] | keyStates[Keyboard.KEY_RMENU]; 	}
	public boolean getShift() { return keyStates[Keyboard.KEY_LSHIFT] | keyStates[Keyboard.KEY_RSHIFT]; 	}
	public boolean getControl() { return keyStates[Keyboard.KEY_LCONTROL] | keyStates[Keyboard.KEY_RCONTROL]; 	}

	public KeyboardKeyEventArgs() {
		keyStates = new boolean[Keyboard.KEYBOARD_SIZE];
	}
	public void Refresh() {
		key = 0;
		Keyboard.poll();
		keyStates[Keyboard.KEY_LMENU] = Keyboard.isKeyDown(Keyboard.KEY_LMENU);
		keyStates[Keyboard.KEY_RMENU] = Keyboard.isKeyDown(Keyboard.KEY_RMENU);
		keyStates[Keyboard.KEY_LSHIFT] = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		keyStates[Keyboard.KEY_RSHIFT] = Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
		keyStates[Keyboard.KEY_LCONTROL] = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		keyStates[Keyboard.KEY_RCONTROL] = Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
		isRepeat = false;
	}
}
