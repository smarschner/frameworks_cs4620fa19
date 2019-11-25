package gl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import anim.AnimationEngine;
import anim.TimelineViewer;
import common.Scene;
import common.event.SceneTransformationEvent;
import egl.math.Matrix4;
import egl.math.Vector2;
import egl.math.Vector3;

public class CameraController {
	protected final Scene scene;
	public RenderCamera camera;
	protected final RenderEnvironment rEnv;
	
	protected TimelineViewer tlv = new TimelineViewer();
	protected AnimationEngine eng; 
	
	protected boolean prevFrameButtonDown = false;
	protected int prevMouseX, prevMouseY;
	
	protected boolean orbitMode = true;
	
	static final float KEY_MOVE_RATE = 5f;
	static final float KEY_ROT_RATE = 100f;
	static final float MOUSE_ROT_RATE = 0.3f;
	
	static final float THETA_MIN = -80 * (float)Math.PI / 180;
	static final float THETA_MAX = 80 * (float)Math.PI / 180;
	protected float theta = 0.0f, phi = 0.0f;
	protected Vector3 eye = new Vector3();
	protected Vector3 target = new Vector3();
	protected Vector3 up = new Vector3();
	
	boolean initialized = false;
	
	public CameraController(Scene s, RenderEnvironment re, RenderCamera c, TimelineViewer t, AnimationEngine e) {
		scene = s;
		rEnv = re;
		camera = c;
		tlv = t;
		eng = e;
		}
	
	/**
	 * Initialize the camera parameters used by the controller from the current state of the camera.
	 * Also set initialized to true, so that we only do this once before the first update.
	 */
	private void init() {
		initialized = true;
		
		Matrix4 cameraTrans = camera.sceneObject.transformation;
		eye.set(cameraTrans.mulPos(new Vector3(0,0,0)));
		Vector3 view = cameraTrans.mulDir(new Vector3(0,0,-1));
		up.set(0,1,0);
		// Put target at the closest point to origin along the view vector
		// eye - (eye dot view) view / view^2
		target.set(eye).sub(view.clone().mul(eye.dot(view) / view.dot(view)));
		
		System.out.println("initial eye " + eye);
		System.out.println("initial target " + target);
		System.out.println("initial up " + up);
		
		Vector3 eyeDir = eye.clone().sub(target).normalize();
		theta = Math.min(THETA_MAX, Math.max(THETA_MIN, (float) Math.asin(eyeDir.y)));
		phi = (float) Math.atan2(eyeDir.x, eyeDir.z);
	}
	
	/**
	 * Update the camera's transformation matrix in response to user input.
	 * 
	 * Pairs of keys are available to translate the camera in three direction oriented to the camera,
	 * and to rotate around three axes oriented to the camera.  Mouse input can also be used to rotate 
	 * the camera around the horizontal and vertical axes.  All effects of these controls are achieved
	 * by altering the transformation stored in the SceneCamera that is referenced by the RenderCamera
	 * this controller is associated with.
	 * 
	 * @param et  time elapsed since previous frame
	 */
	public void update(double et) {
		
		if (!initialized) init();
		
		Vector3 motion = new Vector3();
		Vector3 rotation = new Vector3();
		
		// disable camera key control
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) { motion.add(0, 0, -1); }
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) { motion.add(0, 0, 1); }
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) { motion.add(-1, 0, 0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) { motion.add(1, 0, 0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) { motion.add(0, -1, 0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) { motion.add(0, 1, 0); }
		motion.mul(KEY_MOVE_RATE * (float) et);

//		if(Keyboard.isKeyDown(Keyboard.KEY_E)) { rotation.add(0, 0, -1); }
//		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) { rotation.add(0, 0, 1); }
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) { rotation.add(-1, 0, 0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) { rotation.add(1, 0, 0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) { rotation.add(0, -1, 0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) { rotation.add(0, 1, 0); }
		rotation.mul(KEY_ROT_RATE * (float) et);
		
//		if(Keyboard.isKeyDown(Keyboard.KEY_O)) { orbitMode = true; } 
//		if(Keyboard.isKeyDown(Keyboard.KEY_F)) { orbitMode = false; } 
		
		// camera mouse control
		// don't rotate camera when mouse drag on timeline
		int frame = -1;
		frame = tlv.onTimeline(800,600,eng, Mouse.getX(), Mouse.getY(), frame);
		boolean onTimeline = (frame != -1);
		boolean thisFrameButtonDown = !onTimeline && Mouse.isButtonDown(0) && !(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));

		// rotate in spherical coordinates mapping x and y mouse motion to phi and theta
		int thisMouseX = Mouse.getX(), thisMouseY = Mouse.getY();
		if (thisFrameButtonDown && prevFrameButtonDown) {
			rotation.add(0, -MOUSE_ROT_RATE * (thisMouseX - prevMouseX), 0);
			rotation.add(-MOUSE_ROT_RATE * (thisMouseY - prevMouseY), 0, 0);
		}
		prevFrameButtonDown = thisFrameButtonDown;
		prevMouseX = thisMouseX;
		prevMouseY = thisMouseY;
		
		// Update target according to translation inputs
		Matrix4 cameraTransform = camera.sceneObject.transformation;
		target.add(cameraTransform.mulDir(motion));
		
		// Update eye according to rotation inputs
		theta += rotation.x * Math.PI / 180.0f;
		theta = Math.max(Math.min(theta, THETA_MAX), THETA_MIN);
		phi += rotation.y * Math.PI / 180.0f;
				eye.set(target).add(Matrix4.createRotationY(phi).mulDir(Matrix4.createRotationX(-theta).mulDir(new Vector3(0,0,10))));

		// Rebuild camera matrix from eye and target
		Matrix4.createLookAt(eye, target, up, cameraTransform);
		cameraTransform.invert();
		
		scene.sendEvent(new SceneTransformationEvent(camera.sceneObject));
	}
}
