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
	
	public RobotiumChecker() {
		super();
	}
	
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
	
	public boolean checkClick(Component c) {
				
		if(!hasBasicComponentInfo(c, "click"))
			return false;

		return true;
	}
	
	public boolean checkLongClick(Component c) {
		
		if(!hasBasicComponentInfo(c, "long click"))
			return false;

		return true;
	}
	
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
	 * Need positionX, positionY
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
		
		return true;
	}
	
	private String checkID(Component c, String action) {
		
		if(c.getId() == null || c.equals("")) {
			return "*** Information needed for " + action + " action is missing: id\n" +
					"Input test case file should include the component id as a value for the key \'id\' in stepTestCases";
		}
		
		return null;
	}
	
	private String checkType(Component c, String action) {
		
		if(c.getType() == null || c.getType().equals("")) {
			return "*** Information needed for " + action + " action is missing: type\n" +
					"Input test case file should include the component id as a value for the key \'type\' in stepTestCases";
		}
		
		return null;
	}
	
	private String checkIndex(Component c, String action) {
		
		if(c.getIndex() == null || c.getIndex().equals("")) {
			return "Information needed for " + action + " action is missing: index\n" +
					"Input test case file should include the component id as a value for the key \'index\' in stepTestCases";
		}
		
		return null;
	}
	
}
