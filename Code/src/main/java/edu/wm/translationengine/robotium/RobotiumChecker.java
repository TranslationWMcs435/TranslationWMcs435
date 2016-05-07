package edu.wm.translationengine.robotium;

import edu.wm.translationengine.classes.Component;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.AbstractChecker;

/**
 *	Checks if test file contains all necessary info for Robotium
 *	to run translate.
 *
 */
public class RobotiumChecker extends AbstractChecker {
	
	/**
	 * Instantiates a new Robotium checker.
	 */
	public RobotiumChecker() {
		super();
	}
	
	/** 
	 * Check if file contains all essential app metadata
	 * needed to translate.
	 * 
	 * @param tc a testcase
	 * @return true if the file contains all essential metadata, false otherwise
	 */
	public boolean checkAppData(TestCase tc) {
		
		if(tc.getAppName() == null || tc.getAppName().equals("")) {
			System.err.println("*** Info needed for test case missing: app name.");
			System.err.println("Input test case file should include app name as a value for the key \'appName\'");
			return false;
		}
		if(tc.getMainActivity() == null || tc.getMainActivity().equals("")) {
			System.err.println("*** Info needed for test case missing: main activity");
			System.err.println("Input test case file should include main activity as a value for the key \'mainActivity\'");
			return false;
		}
		if(tc.getPackageName() == null || tc.getPackageName().equals("")) {
			System.err.println("*** Info needed for test case missing: package name");
			System.err.println("Input test case file should include main activity as a value for the key \'packageName\'");
			return false;
		}
	
		return true;	
	}
	
	/**
	 * Check if component has a valid values to perform click action.
	 *
	 * @param c the component
	 * @return a message to indicate whether the component has valid values needed to perform click
	 */
	public boolean checkClick(Component c) {
				
		if(!hasBasicComponentInfo(c, "click"))
			return false;

		return true;
	}
	
	/**
	 * Check if component has a valid values to perform long click action.
	 *
	 * @param c the component
	 * @return a message to indicate whether the component has valid values needed to perform long click
	 */
	public boolean checkLongClick(Component c) {
		
		if(!hasBasicComponentInfo(c, "long click"))
			return false;

		return true;
	}
	
	/**
	 * Check if component has a valid values to perform type action.
	 *
	 * @param c the component
	 * @return a message to indicate whether the component has valid values needed to perform type
	 */
	public boolean checkType(Component c) {
		
		if(c.getText() == null) {
			// don't check c.getText().equals("") because 
			// empty strings can be inputted for type action
			
			System.err.println("*** Information needed for long click action is missing: id\n" +
					"Input test case file should include the component id as a value for the key \'text\' in stepTestCases");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check if the component has necessary values needed for 
	 * swipe action. Needs positionX, positionY.
	 *
	 * @param c the component
	 * @return true, if successful
	 */
	public boolean checkSwipe(Component c) {

		if(c.getPositionX().equals("") || c.getPositionX() == null) {
			System.err.println("*** Information needed for swipe action is missing: positionX\n"+ 
					"Input test case file should include the component id as a value for the key 'positionX' in stepTestCases");
			return false;
		}
		if(c.getPositionY().equals("") || c.getPositionY() == null) {
			System.err.println("*** Information needed for swipe action is missing: positionY\n"+ 
					"Input test case file should include the component id as a value for the key 'positionY' in stepTestCases");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks for basic component info.
	 *
	 * @param c the c
	 * @param action the action
	 * @return true, if successful
	 */
	private boolean hasBasicComponentInfo(Component c, String action) {
		
		String typeErr = checkType(c, action);
		if(typeErr != null) {
			System.err.println(typeErr);
			return false;
		}
		
		String indexErr = checkIndex(c, action);
		if(indexErr != null) {
			System.err.println(indexErr);
			return false;
		}
		
		String idErr = checkID(c, action);
		if(idErr != null) {
			System.err.println(idErr);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check if component has valid values needed for id.
	 *
	 * @param c the c
	 * @param action the action
	 * @return a message to indicate whether the component has valid id values
	 */
	private String checkID(Component c, String action) {
		
		if(c.getId() == null || c.equals("")) {
			return "*** Information needed for " + action + " action is missing: id\n" +
					"Input test case file should include the component id as a value for the key \'id\' in stepTestCases";
		}
		
		return null;
	}
	
	/**
	 * Check if component has valid values needed for type.
	 *
	 * @param c the c
	 * @param action the action
	 * @return a message to indicate whether the component has all values needed to perform type
	 */
	private String checkType(Component c, String action) {
		
		if(c.getType() == null || c.getType().equals("")) {
			return "*** Information needed for " + action + " action is missing: type\n" +
					"Input test case file should include the component id as a value for the key \'type\' in stepTestCases";
		}
		
		return null;
	}
	
	/**
	 * Check if component has a valid index.
	 *
	 * @param c the component
	 * @param action the action
	 * @return a message to indicate whether the component has valid index values
	 */
	private String checkIndex(Component c, String action) {
		
		if(c.getIndex() == null || c.getIndex().equals("")) {
			return "Information needed for " + action + " action is missing: index\n" +
					"Input test case file should include the component id as a value for the key \'index\' in stepTestCases";
		}
		
		return null;
	}
}