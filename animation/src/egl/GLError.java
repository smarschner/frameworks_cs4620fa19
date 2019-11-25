package egl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.lwjgl.opengl.GL11;

import egl.GL.ErrorCode;

/**
 * OpenGL Error Checker
 * @author Cristian
 *
 */
public class GLError {
	/**
	 * If The Error Log Should Be Closed By This
	 */
	private static boolean shouldClose = false;
	/**
	 * Output Location
	 */
	private static OutputStreamWriter ErrorLog = null;
	
	/**
	 * Resets Error Stream To A New One
	 * @param osw Error Destination
	 * @param close Should GLError Close This Stream
	 */
	public static void setErrorLog(OutputStreamWriter osw, boolean close) {
		close();
		ErrorLog = osw;
		shouldClose = close;
	}
	/**
	 * Use A File To Record Errors
	 * @param file File Name
	 */
	public static void setErrorLogFile(String file) {
		File f = new File(file);
		if(f.exists()) f.delete();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			setErrorLog(osw, true);
			return;
		} catch (FileNotFoundException e) {
			System.err.println("Could Not Create A File For The OpenGL Error Log");
			return;
		}
	}

	/**
	 * Write An Error If An Error Is Found
	 * @param desc Tag That Prepends Error
	 */
	public static void get(String desc) {
		int error;
		while((error = GL11.glGetError()) != ErrorCode.NoError) {
			write("ERROR - " + desc + " - " + error);
		}
	}
	
	/**
	 * Write A String To The GLError Output Stream
	 * @param err Message
	 */
	public static void write(String err) {
		if(ErrorLog != null) {
			try { ErrorLog.write(err + System.lineSeparator()); } 
			catch (IOException e) { }
		}
	}

	/**
	 * Flushes And Removes Current Stream, Closing It If Necessary
	 */
	public static void close() {
		if(ErrorLog == null) return;
		try {
			ErrorLog.flush();
			if(shouldClose) ErrorLog.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ErrorLog = null;
		return;
	}
}
