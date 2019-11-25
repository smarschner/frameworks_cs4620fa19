package egl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A Log For OpenGL Diagnostic Information
 * @author Cristian
 *
 */
public class GLDiagnostic {
	/**
	 * Log File
	 */
	private static File file;
	/**
	 * Log Writer
	 */
	private static FileWriter fw = null;
	
	/**
	 * Opens The File Log
	 */
	public static void init() {
		dispose();
		file = new File("GLDiag.txt");
		if(file.exists()) file.delete();
		try {
			file.createNewFile();
			fw = new FileWriter(file);
		} catch (IOException e) {
			fw = null;
		}
	}
	/**
	 * Flushes And Closes The File Log
	 */
	public static void dispose() {
		try {
			if(fw != null) {
				fw.flush();
				fw.close();
				fw = null;
			}
		} catch (IOException e) {
		}
	}
	
	/**
	 * Write A Message To The Log
	 * @param msg Message
	 */
	public static void writeln(String msg) {
		try {
			if(fw != null) {
				fw.write(msg);
				fw.write("\r\n");
				fw.flush();
			}
		} catch (IOException e) {
		}
	}
}
