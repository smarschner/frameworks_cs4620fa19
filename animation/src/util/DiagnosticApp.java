package util;

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
 *    Labelled "Java Build Path"
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

import blister.FalseFirstScreen;
import blister.MainGame;
import blister.ScreenList;

public class DiagnosticApp extends MainGame {
	public DiagnosticApp(boolean withContext32Core) {
		super("Diagnostic", 800, 600, withContext32Core ?
				new org.lwjgl.opengl.ContextAttribs(3,2).withProfileCore(true) :
				null, null);
	}

	public DiagnosticApp() {
		this(false);
	}

	@Override
	protected void buildScreenList() {
		screenList = new ScreenList(this, 0, 
				new FalseFirstScreen(1),
				new DiagnosticScreen()
				);
	}

	@Override
	protected void fullInitialize() {
	}

	@Override
	protected void fullLoad() {
	}

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("2")) {
			System.out.println("Diagnostics App Version 2");
			DiagnosticApp app = new DiagnosticApp(true);
			app.run();
			app.dispose();
		} else {
			System.out.println("Diagnostics App Version 1");
			DiagnosticApp app = new DiagnosticApp();
			app.run();
			app.dispose();
		}
	}
}
