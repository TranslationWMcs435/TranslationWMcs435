package espresso;

import java.util.List;
import classes.Component;
import classes.StepTestCase;
import classes.TestCase;

/**
 * This is the EspressoTranslator class, it takes Action objects and creates Espresso
 * commands that correspond to those Action objects.
 * 
 * @author Nathan Chen
 *
 */
public class EspressoTranslator {
	
	/**
	 * Constructor for the EspressoTranslator class.
	 */
	public EspressoTranslator(){
	}
	
	/**
	 * Iterates through all the StepTestCase objects in the stepTestCases List.
	 * @param testCase The current TestCase object being looked at
	 */
	public void steps_iterator(TestCase testCase){
		String appName = testCase.getAppName();
		String packageName = testCase.getPackageName();
		String mainActivity = testCase.getMainActivity();
		List<StepTestCase> stepTestCases = testCase.getSteps();
		
		for(int i = 0; i < stepTestCases.size(); i++){
			StepTestCase cur = stepTestCases.get(i);
			espresso_switcher(cur.getAction(), cur);
		}
	}
	
	/**
	 * Switching hub for taking different kinds of input, calls methods depending
	 * on what kind of user input is given.
	 * @param action String of the action to be performed
	 * @param s StepTestCase object being looked at 
	 */
	public void espresso_switcher(String action, StepTestCase s){
		
		if(action.equals("CLICK")){
			make_click_command(s.getComponent());
		}
		if(action.equals("LONG_CLICK")){
			make_long_click_command(s.getComponent());
		}
	}

	/**
	 * Generates the click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_click_command(Component c) {
		String espresso_command = new String();		
		espresso_command += "onView(withId(R.id." + c.getId().substring(3) + ")).perform(click());";
		System.out.println(espresso_command);
	}
	
	/**
	 * Generates the long click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_long_click_command(Component c) {
		String espresso_command = new String();		
		espresso_command += "onView(withId(R.id." + c.getId().substring(3) + ")).perform(longClick());";
		System.out.println(espresso_command);
	}
}
