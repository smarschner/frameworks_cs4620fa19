package ray1;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import ray1.camera.Camera;
import egl.math.Colorf;
import ray1.shader.Shader;
import ray1.surface.Surface;
import ray1.shader.Texture;
import ray1.shader.BRDF;

/**
 * The scene is just a collection of objects that compose a scene. The camera,
 * the list of lights, and the list of surfaces.
 *
 * @author ags, pramook
 */
public class Scene {
	
	/** The camera for this scene. */
	protected Camera camera;
	public void setCamera(Camera camera) { this.camera = camera; }
	public Camera getCamera() { return this.camera; }
	
	/** The background color for this scene. Any rays that don't hit a surface
	 *  return this color.
	 */
	protected Colorf backColor = new Colorf();
	public void setBackColor(Colorf color) { this.backColor.set(color); }
	public Colorf getBackColor() { return this.backColor; }
	
	/** The amount of exposure to be used for this Scene. */
	protected float exposure= 1.0f;
	/**
	 * Sets the exposure of this Scene.
	 * @param exposure
	 * 		The exposure to be used; must be greater than 0.
	 */
	public void setExposure(float exposure) {
		if(exposure > 0.0) this.exposure= exposure;
	}
	public float getExposure() { return this.exposure; }
	
	/** The list of lights for the scene. */
	protected ArrayList<Light> lights = new ArrayList<Light>();
	public void addLight(Light toAdd) { lights.add(toAdd); }
	public List<Light> getLights() { return this.lights; }
	
	/** The list of surfaces for the scene. */
	protected ArrayList<Surface> surfaces = new ArrayList<Surface>();
	public void addSurface(Surface toAdd) { surfaces.add(toAdd); }
	public List<Surface> getSurfaces() { return this.surfaces; }
	public void setSurfaces(ArrayList<Surface> s) { surfaces = s; }
	
	/** The list of textures for the scene. */
	protected ArrayList<Texture> textures = new ArrayList<Texture>();
	public void addTexture(Texture toAdd) { textures.add(toAdd); }
	public List<Texture> getTextures() { return this.textures; }
	
	/** The list of shaders in the scene. */
	protected ArrayList<Shader> shaders = new ArrayList<Shader>();
	public void addShader(Shader toAdd) { shaders.add(toAdd); }
	public List<Shader> getShaders() { return this.shaders; }
	
	/** The list of brdf in the scene. */
	protected ArrayList<BRDF> brdfs = new ArrayList<BRDF>();
	public void addBRDF(BRDF toAdd) { brdfs.add(toAdd); }
	public List<BRDF> getBrdfs() { return this.brdfs; }
	
	/** Image to be produced by the renderer **/
	protected Image outputImage;
	public Image getImage() { return this.outputImage; }
	public void setImage(Image outputImage) { this.outputImage = outputImage; }
	
	/**
	* Initialize method
	*/
	public void init() {
		// Create the acceleration structure.
		ArrayList<Surface> renderableSurfaces = new ArrayList<Surface>();
		List<Surface> surfaces = getSurfaces();
		for (Iterator<Surface> iter = surfaces.iterator(); iter.hasNext();) {
			iter.next().appendRenderableSurfaces(renderableSurfaces);
		}
		setSurfaces(renderableSurfaces);
		
		// initialize camera
		getCamera().init();
		
		//initialize lights
		for ( Light light : getLights() ) {
			light.init();
		}
		
		//initialize surfaces
		for ( Surface surface : getSurfaces()) {
			surface.init();
		}
		
		//initialize shaders
		for (Shader shader : getShaders()) {
			shader.init();
		}
		
		//initialize brdfs
		for (BRDF brdf : getBrdfs())
		{
			brdf.init();
		}
	}

	/**
	 * Set outRecord to the first intersection of ray with the scene. Return true
	 * if there was an intersection and false otherwise. If no intersection was
	 * found outRecord is unchanged.
	 *
	 * @param outRecord the output IntersectionRecord
	 * @param ray the ray to intersect
	 * @return true if and intersection is found.
	 */
	public boolean getFirstIntersection(IntersectionRecord outRecord, Ray ray) {
		return intersect(outRecord, ray, false);
		
	}
	
	/**
	 * Shadow ray calculations can be considerably accelerated by not bothering to find the
	 * first intersection.  This record returns any intersection of the ray and the surfaces
	 * and returns true if one is found.
	 * @param ray the ray to intersect
	 * @return true if any intersection is found
	 */
	public boolean getAnyIntersection(Ray ray) {
		return intersect(new IntersectionRecord(), ray, true);	
	}
	
	private boolean intersect(IntersectionRecord outRecord, Ray rayIn, boolean anyIntersection) {
		// TODO#Ray Task 3:
		//			1) Loop through all surfaces in the scene.
		//		    2) Intersect each with a copy of the given ray.
		//		    3) If there was an intersection, check the modified IntersectionRecord to see
		//		  	   if the object was hit by the ray sooner than any previous object.
		//			   Hint: modifying the end field of your local copy of ray might be useful here.
		//          4) If anyIntersection is true, return immediately.
		//		    5) Set outRecord to the IntersectionRecord of the first object hit.
		//		    6) If there was an intersection, return true; otherwise return false.

		boolean ret = false;
	
		return ret;
	}
}