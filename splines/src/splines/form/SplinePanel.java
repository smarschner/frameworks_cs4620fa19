package splines.form;

/**
 * Provides functionality for drawing splines on the current gl window
 * 
 * @author deedy
 *
 */
public abstract class SplinePanel {
	/**
	 * Width, height of the panel, and the side of the box containing the spline 
	 */
	public static int panelWidth, panelHeight, panelSide;
	
	/**
	 * Half of the length of panelSide
	 */
	public static float halfSide;
	
	/**
	 * The index of the SplinePanel in the SplineScreen, 0 being left most. [Only one row]
	 */
	public int index;
	
	/**
     * Resets the static variables of the panel - to be called on resize event.
     * @param panelWidth The new width of the panel
     * @param panelHeight The new height of the panel
     */
	public static void resize(int panelWidth, int panelHeight) {
		SplinePanel.panelWidth = panelWidth;
		SplinePanel.panelHeight = panelHeight;
		SplinePanel.panelSide = Math.min(SplinePanel.panelWidth, SplinePanel.panelHeight);
		SplinePanel.halfSide = (float) SplinePanel.panelSide / 2.0f;
	}
	
	/**
     * Draws all the spline elements.
     */
	public abstract void draw();
}
