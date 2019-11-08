package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.LWJGLException;

/**
 * README
 * 
 * Now That You've Pulled A Major Framework Change,
 * There Are A Few More Steps To Follow Before You Are Able
 * To Run This Test. One Of The New Things Available In The
 * Framework Is LWJGL (A Java Port Of OpenGL), But It Requires
 * Some Configuration Specific To Your Machine To Use It Properly:
 * 
 * 1) Right-click On Your Project And Select Properties
 * 2) In The Window That Pops Up, Select The Tab On The Left
 *    Labeled "Java Build Path"
 * 3) Select The Tab Called "Libraries" And Expand "lwjgl.jar"
 * 4) Select The Option "Native library location:" And Press The "Edit" 
 *    Button
 * 5) Enter In The Correct Path To Your Native Library Location. It Should
 *    Be One Of The Following (Pick Your OS):
 *      CS4620/deps/native/windows
 *      CS4620/deps/native/linux
 *      CS4620/deps/native/freebsd
 *      CS4620/deps/native/solaris
 *      CS4620/deps/native/macosx
 * 6) You Should Now Be Fully Configured To Use LWJGL On Your Machine
 * 
 * On Another Note, If You Wish To Export Your Code Using LWJGL, You Need To Include
 * All The Files Located Inside Of The Native Library Location Alongside Your Runnable
 * JAR File. Additionally, The Current Working Directory Must Be The Same As The Executable
 * Directory, Or Else JRE Will Not Be Able To Locate The Natives Properly.
 */

public class LightweightDiagnosticApp {
	
	private static final String diagnosticFileName = "LWGLDiag.txt";
	private static final PixelFormat glPixelFormat = new PixelFormat(8, 24, 8);
		
	public static void performDiagnostic(PrintStream log) {
		log.println("TIME:                             " + Calendar.getInstance().getTime());
		log.println();
		log.println("=== Java System Properties ===");
		log.println("java.version:                     " + System.getProperty("java.version"));
		log.println("java.runtime.name:                " + System.getProperty("java.runtime.name"));
		log.println("java.vm.name:                     " + System.getProperty("java.vm.name"));
		log.println("java.vm.version:                  " + System.getProperty("java.vm.version"));
		log.println("os.name:                          " + System.getProperty("os.name"));
		log.println("os.version:                       " + System.getProperty("os.version"));
		log.println();
		
		
		log.println("=== Legacy OpenGL Properties ===");
		ContextAttribs glContextAttribs = new ContextAttribs(2,1);
		
		try {
			Display.create(glPixelFormat, glContextAttribs);
			
			log.println("GL_VENDOR:                          " + GL11.glGetString(GL11.GL_VENDOR));
			log.println("GL_VERSION:                         " + GL11.glGetString(GL11.GL_VERSION));
			log.println("GL_RENDERER:                        " + GL11.glGetString(GL11.GL_RENDERER));
			log.println("GL_SHADING_LANGUAGE_VERSION:        " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
			log.println("GL_MAX_VERTEX_UNIFORM_COMPONENTS:   " + GL11.glGetInteger(GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS));
			log.println("GL_MAX_FRAGMENT_UNIFORM_COMPONENTS: " + GL11.glGetInteger(GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS));
			log.println();
			
			Display.destroy();
		} catch (LWJGLException e) {
			log.println("Encountered LWJGL Exception:\n");
			log.append(e.getMessage());
			log.println();
		}
		
		log.println("=== Modern OpenGL Properties ===");
		glContextAttribs = new ContextAttribs(3,2).withProfileCore(true);
		
		try {
			Display.create(glPixelFormat, glContextAttribs);
			
			log.println("GL_VENDOR:                          " + GL11.glGetString(GL11.GL_VENDOR));
			log.println("GL_VERSION:                         " + GL11.glGetString(GL11.GL_VERSION));
			log.println("GL_RENDERER:                        " + GL11.glGetString(GL11.GL_RENDERER));
			log.println("GL_SHADING_LANGUAGE_VERSION:        " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
			log.println("GL_MAX_VERTEX_UNIFORM_COMPONENTS:   " + GL11.glGetInteger(GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS));
			log.println("GL_MAX_FRAGMENT_UNIFORM_COMPONENTS: " + GL11.glGetInteger(GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS));
			log.println();
			
			Display.destroy();
		} catch (LWJGLException e) {
			log.println("Encountered LWJGL Exception:\n");
			log.append(e.getMessage());
			log.println();
		}
	}
	
	public static void main(String[] args) {
		File diagnosticFile = new File(diagnosticFileName);	
		try {
			PrintStream stream = new PrintStream(diagnosticFile);
			performDiagnostic(stream);
			stream.close();
			System.out.println("Wrote file: " + diagnosticFile.getAbsolutePath());
			System.out.println("Diagnostic completed successfully.");
		} catch (FileNotFoundException e) {
			System.err.println("Error: Could not create diagnostic file. Writing to stdout instead.");
			System.err.println("Please copy the output below into a file manually and submit it.");
			System.err.println();
			performDiagnostic(System.out);
		}
	}
}
