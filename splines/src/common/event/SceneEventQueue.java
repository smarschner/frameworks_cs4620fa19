package common.event;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import common.Scene;

public class SceneEventQueue {
	public final Scene scene;
	public final LinkedBlockingQueue<SceneEvent> queue = new LinkedBlockingQueue<>();
	
	public SceneEventQueue(Scene s) {
		scene = s;
	}

	public void getEvents(ArrayList<SceneEvent> a) {
		queue.drainTo(a);
	}
	public void addEvent(SceneEvent e) {
		queue.offer(e);
	}
}
