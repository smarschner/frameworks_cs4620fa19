package blister.input;

import ext.csharp.Event;

public class MouseEventDispatcher {
	public static final Event<MouseButtonEventArgs> OnMouseRelease = new Event<>(null);
    public static final Event<MouseButtonEventArgs> OnMousePress = new Event<>(null);
    public static final Event<MouseWheelEventArgs> OnMouseScroll = new Event<>(null);
    public static final Event<MouseMoveEventArgs> OnMouseMotion = new Event<>(null);
    
    public static void EventInput_MouseMotion(MouseMoveEventArgs e) {
    	OnMouseMotion.Invoke(e);
    }
    public static void EventInput_MouseButton(MouseButtonEventArgs e, boolean pressed) {
        if(pressed) OnMousePress.Invoke(e);
        else OnMouseRelease.Invoke(e);
    }
    public static void EventInput_MouseWheel(MouseWheelEventArgs e) {
    	OnMouseScroll.Invoke(e);
    }
}
