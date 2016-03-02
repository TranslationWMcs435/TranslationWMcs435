package edu.wm.translationengine.trans;

import edu.wm.translationengine.classes.StepTestCase;

/**
 * SupportedCommands specifies all actions that
 * are supported by the translation engine.
 */
public interface Functions {

	/**
	 * <p>Tap on the element or screen at the precise location.</p>
	 * 
	 * @param testCase information on how to perform tap
	 * @return true if tap was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean tap( StepTestCase testCase ) throws Exception;
	
	/**
	 * <p>Tap on the element or screen at the precise location.</p>
	 * 
	 * @param testCase information on how to perform tap
	 * @return true if tap was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean longTap( StepTestCase testCase ) throws Exception;
	

	/**
	 * <p>Press a key.</p>
	 * 
	 * @param testCase information on how to perform the action
	 * @return true if action was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean pressKey( StepTestCase testCase ) throws Exception;
	

	/**
	 * <p>Perform a user swipe.</p>
	 * 
	 * @param testCase information on how to perform the action
	 * @return true if action was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean swipe( StepTestCase testCase ) throws Exception;
	
	/**
	 * <p>Flick the screen</p>
	 * 
	 * @param testCase information on how to perform the action
	 * @return true if action was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean flick( StepTestCase testCase ) throws Exception;
	

	/**
	 * <p>Pinch the screen.</p>
	 * 
	 * @param testCase information on how to perform the action
	 * @return true if action was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean pinch( StepTestCase testCase) throws Exception;
	

	/**
	 * <p>Scroll to a specific location.</p>
	 * 
	 * @param testCase information on how to perform the action
	 * @return true if action was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean scrollTo( StepTestCase testCase ) throws Exception;
	

	/**
	 * <p>Launch an app.</p>
	 * 
	 * @param testCase information on how to perform the action
	 * @return true if action was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean launchApp( StepTestCase testCase ) throws Exception;
	

	/**
	 * <p>Close an app.</p>
	 * 
	 * @param testCase information on how to perform the action
	 * @return true if action was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean closeApp( StepTestCase testCase ) throws Exception;
	

	/**
	 * <p>Press the phone's menu button.</p>
	 * 
	 * @param testCase information on how to perform the action
	 * @return true if action was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean pressMenuKey( StepTestCase testCase ) throws Exception;
	
	/**
	 * <p>Hide the keyboard.</p>
	 * 
	 * @param testCase information on how to perform the action
	 * @return true if action was successful, false otherwise
	 * @throws Exception if a problem arises during test case
	 */
	public boolean hideKeyboard( StepTestCase testCase ) throws Exception;	
}