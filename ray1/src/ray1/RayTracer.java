package ray1;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import egl.math.Colorf;
import ray1.camera.Camera;
import ray1.shader.Shader;

public class RayTracer {
	public static class ScenePath {
		
		/**
		 * The Scene's File
		 */
		public Path file;
		/**
		 * The Folder Containing The Scene
		 */
		public Path sceneRoot;
		/**
		 * The Root Workspace Path
		 */
		public Path root;
		
		public ScenePath(String r, String f) {
			if(r == null) {
				root = null;
				file = Paths.get(f);
			}
			else  {
				root = Paths.get(r);
				file = root.resolve(f);
			}
			sceneRoot = file.getParent();
		}
		
		public String getRoot() {
			return root == null ? null : root.toAbsolutePath().toString();
		}
		public String getFile() {
			return file.toAbsolutePath().toString();
		}

		/**
		 * Attempt To Search The Scene And Program Workspace For A File
		 * @param f The File To Search For
		 * @return The Absolute File Path That Is Resolved (Or null)
		 */
		public String resolve(String f) {
			Path p = root != null ? root.resolve(f) : null;
			if(p == null) p = sceneRoot.resolve(f);
			if(p == null) p = Paths.get(f);
			return p == null ? null : p.toAbsolutePath().toString();
		}
	}
	
	/**
	 * The Workspace For The Scene
	 */
	public static ScenePath sceneWorkspace = null;
	
	/**
	 * This directory precedes the arguments passed in via the command line.
	 */
	public static final String directory = "data/scenes/ray1";
	
	/**
	 * Output HDR image (using openEXR)
	 */
	public static final boolean writeHDR = true;
	
	/**
	 * The maximum number of recursive tracing calls allowed
	 */
	public static final int MAX_DEPTH = 12;
	
	/**
	 * The main method takes all the parameters and assumes they are input files
	 * for the ray tracer. It tries to render each one and write it out to a PNG
	 * file named <input_file>.png. A '-p' option may be passed in to change the
	 * path that is prepended to each file that is included.
	 *
	 * @param args
	 */
	public static final void main(String[] args) {
		ArrayList<ScenePath> pathArgs = new ArrayList<>();
		ArrayList<ScenePath> scenesToRender = new ArrayList<>();
		String currentRoot = directory;
				
		// Use All The Arguments
		for(int i = 0;i < args.length;i++) {
			switch(args[i].toLowerCase()) {
			case "-p":
				// Use A Different Root Path
				i++;
				if(i < args.length) currentRoot = args[i];
				break;
			case "-pnull":
				// Use The CWD
				currentRoot = null;
				break;
			default:
				// This Must Be A File
				pathArgs.add(new ScenePath(currentRoot, args[i]));
				break;
			}
		}
		
		if(pathArgs.size() < 1) {
			// Attempt To Render All The Scenes
			pathArgs.add(new ScenePath(currentRoot, "."));
			
			// Display What's Going To Go Down
			printUsage();
			System.out.println("\nAttempting To Render All Scenes");
		}
		
		// Expand All The Possible Scenes
		for(ScenePath p : pathArgs) {
			// Add All The Files In The 
			File f = p.file.toFile();
			if(f.isDirectory()) {
				for(File _f : f.listFiles()) {
					// We Only Want XML Files
					if(!_f.getPath().endsWith(".xml")) continue;
					
					scenesToRender.add(new ScenePath(p.getRoot(), _f.toPath().toAbsolutePath().toString()));
				}
			}
			else {
				// We Only Want XML Files
				if(!f.getPath().endsWith(".xml")) continue;
				
				// Just A Single Scene
				scenesToRender.add(p);
			}
		}
				
		System.out.println("Attempting To Render " + scenesToRender.size() + " Scene(s)");
		RayTracer rayTracer = new RayTracer();
		rayTracer.run(scenesToRender);
	}
	
	public static void printUsage() {
		System.out.println("Usage: java RayTracer [-p path] [directory1 directory2 ... | file1 file2 ...]");
		System.out.println("List each scene file you would like to render on the command line separated by spaces.");
		System.out.println("You may also specify a directory, and all scene files in that directory will be rendered.");
		System.out.println("By default, all files specified are prepended with a given path. Use the -p option to");
		System.out.println("override this path. The path may be overriden multiple times or -pnull may be provided to set");
		System.out.println("the path to the program's working directory. With no -p argument given, this path is: " + directory);
		System.out.println("NB: the path is relative to the working directory of the application, which is normally the root of the CS4620 project.");
	}
	
	/**
	 * The run method takes all the parameters and assumes they are input files
	 * for the ray tracer. It tries to render each one and write it out to a PNG
	 * file named <input_file>.png.
	 *
	 * @param args
	 */
	public void run(ArrayList<ScenePath> args) {
		Parser parser = new Parser();
		for (ScenePath p : args) {
			// Set The Current Workspace For The Scene
			sceneWorkspace = p;

			// Parse the input file
			String inputFilename = sceneWorkspace.getFile();
			System.out.println("Rendering " + p.file.getFileName() + "...");
			Scene scene = (Scene) parser.parse(inputFilename, Scene.class);
			
			// Initialize the scene
			scene.init();

			// Render the scene
			renderImage(scene);

			// Write the image out
			if (writeHDR)
			{
				scene.getImage().writeHDR(sceneWorkspace.getFile() + ".exr");
			}
			else
			{
				scene.getImage().write(sceneWorkspace.getFile() + ".png");
			}
		}
	}
	
	/**
	 * The renderImage method renders the entire scene.
	 *
	 * @param scene The scene to be rendered
	 */
	public void renderImage(Scene scene) {

		// Get the output image
		Image image = scene.getImage();
		Camera cam = scene.getCamera();

		// Set the camera aspect ratio to match output image
		int width = image.getWidth();
		int height = image.getHeight();

		// Timing counters
		long startTime = System.currentTimeMillis();

		// Do some basic setup
		Ray ray = new Ray();
		Colorf rayColor = new Colorf();

		// Progress reporting
		final int PROGRESS_INTERVAL_MS = 5000;
		long lastShownTime = startTime;
		
		float exposure = scene.getExposure();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				rayColor.setZero();

				cam.getRay(ray, (float) (x + 0.5) / width, (float) (y + 0.5) / height);		
				
				shadeRay(rayColor, scene, ray, 1);
				
				rayColor.mul(exposure);
				image.setPixelColor(rayColor, x, y);
			}
			
			if (System.currentTimeMillis() - lastShownTime > PROGRESS_INTERVAL_MS) {
				lastShownTime = System.currentTimeMillis();
				System.out.println((int)(100.0*y / height) + "%");
			}
		}

		// Output time
		long totalTime = (System.currentTimeMillis() - startTime);
		System.out.println("Done.  Rendering time: "
				+ (totalTime / 1000.0) + " seconds");
	}

	/**
	 * This method returns the color along a single ray in outColor.
	 *
	 * @param outColor output space
	 * @param scene the scene
	 * @param ray the ray to shade
	 * @param depth
	 */
	public static void shadeRay(Colorf outColor, Scene scene, Ray ray, int depth) {
		// TODO#Ray Task 4: Compute the color of the intersection point.
		// 1) If depth is greater than MAX_DEPTH, return immediately.
		// 2) Find the first intersection of "ray" with the scene.
		//    Record intersection in intersectionRecord. If it doesn't hit anything,
		//    just return the scene's background color.
		// 3) Get the shader from the intersection record.
		// 4) Call the shader's shade() method to set the color for this ray.
		
		// Reset the output color
		outColor.setZero();
		
				
	}
}
