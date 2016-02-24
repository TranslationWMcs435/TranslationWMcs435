package Espresso;

import classes.Component;
import classes.StepTestCase;

public class EspressoFunctions {
	private boolean clicky;
	private boolean long_clicky;
	
	
	
	/**
	 * Constructor for the EspressoFunctions class
	 */
	public EspressoFunctions(){
		clicky = false;
		long_clicky = false;
	}
	
	
	
	
	
	/**
	 * Switching hub for taking different kinds of input, calls methods depending
	 * on what kind of user input is given.
	 * @param action String of the action to be performed
	 * @param s StepTestCase object being looked at 
	 */
	public void espresso_switcher(String action, StepTestCase s){
		
		if(action.equals("CLICK")){
			if(clicky == false){
				EspressoTranslator.toWrite.add(0, "import static android.support.test.espresso.action.ViewActions.click;\n");
				clicky = true;
			}
			make_click_command(s.getComponent());
		}
		if(action.equals("LONG_CLICK")){
			if(long_clicky == false){
				EspressoTranslator.toWrite.add(0, "import static android.support.test.espresso.action.ViewActions.longClick;\n"); 
				long_clicky = true;
			}
			make_long_click_command(s.getComponent());
		}
	}

	
	
	
	
	
	/**
	 * Generates the click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_click_command(Component c) {
		String espresso_command = new String();		
		espresso_command += "\t\t\tonView(withId(R.id." + c.getId().substring(3) + ")).perform(click());\n";
		EspressoTranslator.toWrite.add(espresso_command);
		System.out.println(espresso_command);
	}
	/**
	 * Generates the click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_click_command_with_text(Component c) {
		String espresso_command = new String();		
		espresso_command += "\t\t\tonView(withText(\"" + c.getText() + "\")).perform(click());\n";
		EspressoTranslator.toWrite.add(espresso_command);
		System.out.println(espresso_command);
	}
	
	/**
	 * Generates the long click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_long_click_command(Component c) {
		String espresso_command = new String();		
		espresso_command += "\t\t\tonView(withId(R.id." + c.getId().substring(3) + ")).perform(longClick());\n";
		EspressoTranslator.toWrite.add(espresso_command);
		System.out.println(espresso_command);
	}
	/**
	 * Generates the long click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_long_click_command_with_text(Component c) {
		String espresso_command = new String();	
		espresso_command += "\t\t\tonView(withText(\"" + c.getText() + "\")).perform(longClick());\n";
		EspressoTranslator.toWrite.add(espresso_command);
		System.out.println(espresso_command);
	}
	
	
	
	
	
	
	
}