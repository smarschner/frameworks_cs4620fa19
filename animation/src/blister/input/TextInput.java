package blister.input;

import org.lwjgl.input.Keyboard;

import ext.csharp.ACEventFuncRef;
import ext.csharp.Event;
import ext.csharp.EventFuncBound;

public class TextInput {
	private final TextArgs tArgs = new TextArgs();
	private final CaretArgs cArgs = new CaretArgs();

	private boolean isActive;
	public boolean getIsActive() {
		return isActive;
	}

	private StringBuilder text;
	public String getText() {
		return text.toString();
	}
	public void setText(String value) {
		text.delete(0, text.length());
		text.append(value);
		setCaret(Math.min(getCaret(), getLength()));
		tArgs.Text = getText();
		OnTextChanged.Invoke(tArgs);
	}
	private int caret;
	public int getCaret() {
		return caret;
	}
	private void setCaret(int value) {
		caret = value;
		cArgs.position = getCaret();
		OnCaretMoved.Invoke(cArgs);
	}
	public int getLength() {
		return text.length();
	}

	public final Event<TextArgs> OnTextEntered = new Event<>(this);
	public final Event<TextArgs> OnTextChanged = new Event<>(this);
	public final Event<CaretArgs> OnCaretMoved = new Event<>(this);

	public TextInput() {
		text = new StringBuilder();
		setCaret(0);
		isActive = false;
	}
	public void Dispose() {
		Deactivate();
		text.delete(0, text.length());
		text = null;
	}

	public void Activate() {
		if(getIsActive()) return;
		isActive = true;

		KeyboardEventDispatcher.OnKeyPressed.add(OnKP);
		KeyboardEventDispatcher.OnReceiveChar.add(OnRC);
	}
	public void Deactivate() {
		if(!getIsActive()) return;
		isActive = false;

		KeyboardEventDispatcher.OnKeyPressed.remove(OnKP);
		KeyboardEventDispatcher.OnReceiveChar.remove(OnRC);
	}

	public void Insert(char c) {
		text.insert(getCaret(), c);
		setCaret(getCaret() + 1);
		tArgs.Text = getText();
		OnTextChanged.Invoke(tArgs);
	}
	public void Insert(String s) {
		if(s == null || s.isEmpty())
			return;
		text.insert(getCaret(), s);
		setCaret(getCaret() + s.length());
		tArgs.Text = getText();
		OnTextChanged.Invoke(tArgs);
	}
	public void Delete() {
		if(getCaret() == getLength())
			return;
		text.delete(getCaret(), getCaret() + 1);
		tArgs.Text = getText();
		OnTextChanged.Invoke(tArgs);
	}
	public void BackSpace() {
		if(getCaret() == 0)
			return;
		setCaret(getCaret() - 1);
		Delete();
	}

	// V This Is To Hack Around The Fact That Java Can't Use Functions As Values (Java Sucks)
	
	private static class OnRC_F extends ACEventFuncRef<TextInput, KeyPressEventArgs> {
		@Override
		public void Receive(TextInput o, Object sender, KeyPressEventArgs args) {
			o.OnChar(sender, args);
		}
	}
	private static final OnRC_F OnRC_FS = new OnRC_F();
	public final EventFuncBound<TextInput, KeyPressEventArgs> OnRC = new EventFuncBound<>(this, OnRC_FS);
	public void OnChar(Object s, KeyPressEventArgs args) {
		Insert(args.KeyChar);
	}

	private static class OnKP_F extends ACEventFuncRef<TextInput, KeyboardKeyEventArgs> {
		@Override
		public void Receive(TextInput o, Object sender, KeyboardKeyEventArgs args) {
			o.OnKeyPress(sender, args);
		}
	}
	private static final OnKP_F OnKP_FS = new OnKP_F();
	public final EventFuncBound<TextInput, KeyboardKeyEventArgs> OnKP = new EventFuncBound<>(this, OnKP_FS);
	public void OnKeyPress(Object s, KeyboardKeyEventArgs args) {
		switch(args.key) {
		case Keyboard.KEY_RETURN:
			if(text.length() < 1)
				return;
			tArgs.Text = getText();
			OnTextEntered.Invoke(tArgs);
			return;
		case Keyboard.KEY_BACK:
			BackSpace();
			return;
		case Keyboard.KEY_DELETE:
			Delete();
			return;
		case Keyboard.KEY_LEFT:
			if(getCaret() > 0)
				setCaret(getCaret() - 1);
			return;
		case Keyboard.KEY_RIGHT:
			if(getCaret() < getLength())
				setCaret(getCaret() + 1);
			return;
		case Keyboard.KEY_V:
			if(args.getAlt() || args.getShift() || ! args.getControl()) return;
			String c = KeyboardEventDispatcher.getNewClipboard();
			Insert(c);
			return;
		case Keyboard.KEY_C:
			if(args.getAlt() || args.getShift() || ! args.getControl()) return;
			if(text.length() > 0)
				KeyboardEventDispatcher.setToClipboard(getText());
			return;
		}
	}
}
