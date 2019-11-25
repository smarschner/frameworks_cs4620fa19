package util;

import java.util.LinkedList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public final class ContextEnumeration {
	static class ContextPair {
		public ContextAttribs asked;
		public ContextAttribs provided;
	}
	
	public static void main(String[] args) {
		// Ensure Displays Are Available
		try {
			DisplayMode[] dispModes = Display.getAvailableDisplayModes();
			if (dispModes == null || dispModes.length < 1) {
				throw new LWJGLException("Null amount of display modes");
			}
		} catch (LWJGLException e) {
			System.err.println("Could not enumerate display modes.\n"
					+ "This OpenGL test must be run on a display.\n"
					+ e.getMessage());
		}

		// Enumeration Of OpenGL Tests
		PixelFormat pixelFormat = new PixelFormat();
		ContextAttribs[] contextAttribs = {
			// Old-style OpenGL
			new ContextAttribs(1, 1),
			new ContextAttribs(1, 2),
			new ContextAttribs(1, 3),
			new ContextAttribs(1, 4),
			new ContextAttribs(1, 5),
			
			// Hybrid New/Old
			new ContextAttribs(2, 0),
			new ContextAttribs(2, 1),
			
			// Introduction To Modern (Hello To Compatibility Problems)
			new ContextAttribs(3, 0),
			new ContextAttribs(3, 1),
			new ContextAttribs(3, 2).withProfileCompatibility(false).withProfileCore(true),
			new ContextAttribs(3, 2).withProfileCompatibility(true).withProfileCore(false),
			new ContextAttribs(3, 2).withProfileCompatibility(false).withProfileCore(false),
			new ContextAttribs(3, 3).withProfileCompatibility(false).withProfileCore(true),
			new ContextAttribs(3, 3).withProfileCompatibility(true).withProfileCore(false),
			new ContextAttribs(3, 3).withProfileCompatibility(false).withProfileCore(false),
			
			// Modern OpenGL
			new ContextAttribs(4, 0).withProfileCompatibility(false).withProfileCore(true),
			new ContextAttribs(4, 0).withProfileCompatibility(true).withProfileCore(false),
			new ContextAttribs(4, 0).withProfileCompatibility(false).withProfileCore(false),
			new ContextAttribs(4, 1).withProfileCompatibility(false).withProfileCore(true),
			new ContextAttribs(4, 1).withProfileCompatibility(true).withProfileCore(false),
			new ContextAttribs(4, 1).withProfileCompatibility(false).withProfileCore(false),
			new ContextAttribs(4, 2).withProfileCompatibility(false).withProfileCore(true),
			new ContextAttribs(4, 2).withProfileCompatibility(true).withProfileCore(false),
			new ContextAttribs(4, 2).withProfileCompatibility(false).withProfileCore(false),
			new ContextAttribs(4, 3).withProfileCompatibility(false).withProfileCore(true),
			new ContextAttribs(4, 3).withProfileCompatibility(true).withProfileCore(false),
			new ContextAttribs(4, 3).withProfileCompatibility(false).withProfileCore(false),
			new ContextAttribs(4, 4).withProfileCompatibility(false).withProfileCore(true),
			new ContextAttribs(4, 4).withProfileCompatibility(true).withProfileCore(false),
			new ContextAttribs(4, 4).withProfileCompatibility(false).withProfileCore(false)
		};
		
		// Test all possibilities of OpenGL contexts
		LinkedList<ContextAttribs> queueSuccess = new LinkedList<ContextAttribs>();
		LinkedList<ContextPair> queueMisattributed = new LinkedList<ContextPair>();
		LinkedList<ContextAttribs> queueFail = new LinkedList<ContextAttribs>();
		boolean badDriverReport = false;
		for (ContextAttribs attrib : contextAttribs) {
			try {
				Display.create(pixelFormat, attrib);
				
				// Test to see the ACTUAL version created
				String version = GL11.glGetString(GL11.GL_VERSION);
				String[] splits = version.split("[.\\s]+");
				if(splits.length >= 2) {
					try {
						int major = Integer.parseInt(splits[0]);
						int minor = Integer.parseInt(splits[1]);
						if (major != attrib.getMajorVersion() || minor != attrib.getMinorVersion()) {
							ContextPair pair = new ContextPair();
							pair.asked = attrib;
							pair.provided = new ContextAttribs(major, minor);
							queueMisattributed.add(pair);
						}
						else {
							queueSuccess.add(attrib);
						}
					}
					catch (NumberFormatException e) {
						badDriverReport = true;
						queueSuccess.add(attrib);
					}
				}
				else {
					badDriverReport = true;	
					queueSuccess.add(attrib);
				}
				Display.destroy();
			} catch (LWJGLException e) {
				queueFail.add(attrib);
			}
		}
		
		// Display successes and failures
		if (badDriverReport) {
			System.err.println("Driver did not implement GL_VERSION correctly, results may be skewed.\n");
		}
		if (queueSuccess.size() > 0) {
			System.out.println("Successes:");
			for (ContextAttribs attrib : queueSuccess) {
				System.out.println("- " + toString(attrib));
			}
		}
		else {
			System.err.println("Successes: None");
		}
		if (queueMisattributed.size() > 0) {
			System.err.println("\nMisattributed:");
			for (ContextPair pair : queueMisattributed) {
				System.err.println("- Requested: " + toString(pair.asked));
				System.err.println(String.format("  Obtained:  Version %d.%d", pair.provided.getMajorVersion(), pair.provided.getMinorVersion()));
			}
		}
		if (queueFail.size() > 0) {
			System.err.println("\nFailures:");
			for (ContextAttribs attrib : queueFail) {
				System.err.println("- " + toString(attrib));
			}
		}
	}
	
	private static String toString(ContextAttribs attribs) {
		return String.format("Version %d.%d - Core=%s - Compatability=%s", 
			attribs.getMajorVersion(),
			attribs.getMinorVersion(),
			attribs.isProfileCore() ? "True " : "False",
			attribs.isProfileCompatibility() ? "True " : "False"
		);
	}
}
