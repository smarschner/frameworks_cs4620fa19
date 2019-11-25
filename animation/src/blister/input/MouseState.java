package blister.input;

import org.lwjgl.input.Mouse;

public class MouseState {
	public int x;
	public int y;
	public int Buttons;
	public int Scroll;

	public MouseState() {
	}
	
	public void Refresh() {
		Mouse.poll();
		x = Mouse.getX();
		y = Mouse.getY();
		for(int i = 0;i < 16;i++) Buttons |= Mouse.isButtonDown(i) ? 0 : (1 << i);
		Scroll=0;
	}

	public boolean IsButtonDown(int b) {
		return (Buttons & b) != 0;
	}
	
	public String toString() {
		return "[MouseState (" + x + "," + y + ") 0x" + Integer.toHexString(Buttons) + " " + Scroll + "]";
	}
}
