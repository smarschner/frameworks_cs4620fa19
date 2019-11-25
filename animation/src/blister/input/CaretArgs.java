package blister.input;

import ext.csharp.EventArgs;

/**
 * Event Container For When A Caret Changes Position
 * @author Cristian
 *
 */
public class CaretArgs extends EventArgs {
	/**
	 * Current Position Of The Caret In A String
	 */
	public int position;
}
