package blister;

/**
 * Set Of States A Window Can Occupy
 * @author Cristian
 *
 */
public enum ScreenState {
	/**
	 * Screen Is Running
	 */
	Running,
	/**
	 * Request To Exit The Application
	 */
    ExitApplication,
    /**
     * Request To Change To A Next Screen
     */
    ChangeNext,
    /**
     * Request To Change To A Previous Screen
     */
    ChangePrevious
}
