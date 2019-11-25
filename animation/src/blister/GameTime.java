package blister;

/**
 * Struct Holding Accumulated And Elapsed Time
 * @author Cristian
 *
 */
public class GameTime {
	/**
	 * Accumulated Time
	 */
	public double total;
	/**
	 * Elapsed Time Slice (Between A Frame)
	 */
    public double elapsed;
    
    /**
     * Zero-Fill Constructor
     */
    public GameTime() {
    	total = 0;
    	elapsed = 0;
    }
}
