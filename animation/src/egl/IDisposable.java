package egl;

/**
 * Represents Objects Whose Resource Are Not Automatically Freed By The GC
 * @author Cristian
 *
 */
public interface IDisposable {
	/**
	 * Frees Non-GC Resources
	 */
	void dispose();
}
