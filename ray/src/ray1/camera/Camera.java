package ray1.camera;

import ray1.Ray;
import egl.math.Vector3;
import egl.math.Vector3d;

/**
 * Represents a camera object. This class is responsible for generating rays that are intersected
 * with the scene.
 */

public abstract class Camera {
	/*
	 * Fields that are read in from the input file to describe the camera.
	 * You'll probably want to store some derived values to make ray generation easy.
	 */
	
	/**
	 * The position of the eye.
	 */
	protected final Vector3 viewPoint = new Vector3();
	public Vector3 getViewPoint() {return viewPoint.clone();}
	public void setViewPoint(Vector3 viewPoint) { this.viewPoint.set(viewPoint); }
	
	/**
	 * The direction the eye is looking.
	 */
	protected final Vector3 viewDir = new Vector3(0, 0, -1);
	public Vector3 getViewDir() { return viewDir.clone(); }	
	public void setViewDir(Vector3 viewDir) { this.viewDir.set(viewDir); }
	
	/**
	 * The upwards direction from the viewer's perspective.
	 */
	protected final Vector3 viewUp = new Vector3(0, 1, 0);
	public Vector3 getViewUp() { return viewUp.clone(); }
	public void setViewUp(Vector3 viewUp) { this.viewUp.set(viewUp); }
	
	/**
	 * The width of the viewing window.
	 */
	protected float viewWidth = 1.0f;
	public float getViewWidth() { return viewWidth; }
	public void setViewWidth(float viewWidth) { this.viewWidth = viewWidth; }
	
	/**
	 * The height of the viewing window.
	 */
	protected float viewHeight = 1.0f;
	public float getViewHeight() { return viewHeight; }
	public void setViewHeight(float viewHeight) { this.viewHeight = viewHeight; }
	
	/**
	 * Generate a ray that points out into the scene for the given (u,v) coordinate.
	 * This coordinate corresponds to a point on the viewing window, where (0,0) is the
	 * lower left corner and (1,1) is the upper right.
	 * @param outRay A space to return the output ray
	 * @param u The horizontal coordinate (0 is left, 1 is right)
	 * @param v The vertical coordinate (0 is bottom, 1 is top)
	 */
	public abstract void getRay(Ray outRay, float u, float v);
	
	/**
	* Initialize method: initialize the orthonormal basis vectors
	*/
	public abstract void init();

	/**
	 * Code for unit testing of cameras.
	 */
	public void testGetRay(Ray correctRay, float u, float v) {
		Ray testRay = new Ray();
		getRay(testRay, u, v);
		if (!raysEquivalent(testRay, correctRay)) {
			 System.err.println("test failed");
			 System.err.println("testRay: " + testRay.origin + " + t * " + testRay.direction);
			 System.err.println("correctRay: " + correctRay.origin + " + t * " + correctRay.direction);
			 System.exit(-1);
		}
		System.out.println("Test successful.");
	}
	
	private static boolean raysEquivalent(Ray ray1, Ray ray2) {
		Vector3 dir1 = new Vector3(ray1.direction);
		Vector3 dir2 = new Vector3(ray2.direction);
		dir1.normalize();
		dir2.normalize();
		dir1.sub(dir2);
		return ray1.origin.dist(ray2.origin) < 1e-6 && dir1.len() < 1e-6; 
	}

	public static void main(String args[]) {
		
	    Vector3 viewPoint = new Vector3(1f, 0.5f, 2f);
	    Vector3 viewDir = new Vector3(15.23f, -1.854f, 65.221f);
	    viewDir.normalize();
	    Vector3 viewUp = new Vector3(1f, 0f, 0f);
	    
	    OrthographicCamera orthoCam = new OrthographicCamera();
	    orthoCam.setViewPoint(viewPoint);
	    orthoCam.setViewDir(viewDir);
	    orthoCam.setViewUp(viewUp);
	    orthoCam.init();
	    
	    PerspectiveCamera perspectiveCam = new PerspectiveCamera();
	    perspectiveCam.setViewPoint(viewPoint);
	    perspectiveCam.setViewDir(viewDir);
	    perspectiveCam.setViewUp(viewUp);
	    perspectiveCam.init();
	    
	    float u = 0.37123f;
	    float v = 0.11343f;
	    Ray correctRay0 = new Ray(new Vector3d(0.6235493799051484f, 0.36878515466141304f, 2.084176425089877f), 
	                              new Vector3d(0.22730915261287413f, -0.027671120744863338f, 0.9734294315537928f));
	    orthoCam.testGetRay(correctRay0, u, v);
	    
	    Ray correctRay1 = new Ray(new Vector3d(1.0f, 0.5f, 2.0f),
	                              new Vector3d(-0.13811656557506483f, -0.14714072701594028f, 0.9794250460177998f));
	    perspectiveCam.testGetRay(correctRay1, u, v);
	    

	    u = 0.00234f;
	    v = 0.9832f;
	    Ray correctRay2 = new Ray(new Vector3d(1.4705511166404994f, 0.005661926354425948f, 1.8760675810709173f),
	                              new Vector3d(0.22730915261287413f, -0.027671120744863338f, 0.9734294315537928f));
	    orthoCam.testGetRay(correctRay2, u, v);
	    
	    Ray correctRay3 = new Ray(new Vector3d(1.0f, 0.5f, 2.0f),
	                              new Vector3d(0.5734153111840217f, -0.4289226336993767f, 0.698011644029039f));
	    perspectiveCam.testGetRay(correctRay3, u, v);
	    
	    
	    u = 0.2345f;
	    v = 0.78201f;
	    Ray correctRay4 = new Ray(new Vector3d(1.2746277432055346f, 0.2364287075632546f, 1.928378256923414f),
                                  new Vector3d(0.22730915261287413f, -0.027671120744863338f, 0.9734294315537928f));
	    orthoCam.testGetRay(correctRay4, u, v);
	    
	    Ray correctRay5 = new Ray(new Vector3d(1.0f, 0.5f, 2.0f),
	                              new Vector3d(0.46805451959738786f, -0.27158260116709737f, 0.8409327306198586f));
	    perspectiveCam.testGetRay(correctRay5, u, v);
	    

	    u = 0.55523f;
	    v = 0.12555f;
	    Ray correctRay6 = new Ray(new Vector3d(0.635352111386452f, 0.552789156075998f, 2.0866509013806787f),
                                  new Vector3d(0.22730915261287413f, -0.027671120744863338f, 0.9734294315537928f));
	    orthoCam.testGetRay(correctRay6, u, v);
	    
	    Ray correctRay7 = new Ray(new Vector3d(1.0f, 0.5f, 2.0f),
	                              new Vector3d(-0.12844581009091482f, 0.02349159814594461f, 0.991438257627089f));
	    perspectiveCam.testGetRay(correctRay7, u, v);
	}
	
}
