package edu.wm.translationengine.espresso;

<<<<<<< HEAD
//import espresso.EspressoTranslator;
=======
>>>>>>> origin/master
import edu.wm.translationengine.classes.Component;
import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.trans.*;

public class EspressoFunctions extends GenericFunctions{
	private boolean clicky;
	private boolean long_clicky;
	private boolean typey;
	
	
	
	/**
	 * Constructor for the EspressoFunctions class
	 */
	public EspressoFunctions(){
		clicky = false;
		long_clicky = false;
		typey = false;
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
		if(action.equals("TYPE")){
			if(typey == false){
				EspressoTranslator.toWrite.add(0, "import static android.support.test.espresso.action.ViewActions.typeText;\n"); 
				typey = true;
			}
		}
	}

	
	
	
	
	
	/**
	 * Generates the click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_click_command(Component c) {
		String espresso_command = new String();
		if(c.getType().equals("android.widget.CheckedTextView")){
			espresso_command += "\t\t\tonData(allOf(is(\"" + c.getText() + "\"))).perform(click());\n";
			EspressoTranslator.toWrite.add(espresso_command);
			System.out.println(espresso_command);
		}
		else if(c.getId().length() > 11 && c.getId().substring(0, 11).equals("android:id/")){
			make_click_command_with_text(c);
		}
		else{
			String id = c.getId().substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(click());\n";
			EspressoTranslator.toWrite.add(espresso_command);
			System.out.println(espresso_command);
		}
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
		if(c.getType().equals("android.widget.CheckedTextView")){
			espresso_command += "\t\t\tonData(allOf(is(\"" + c.getText() + "\"))).perform(longClick());\n";
			EspressoTranslator.toWrite.add(espresso_command);
			System.out.println(espresso_command);
		}
		else if(c.getId().length() > 11 && c.getId().substring(0, 11).equals("android:id/")){
			make_long_click_command_with_text(c);
		}
		else{
			String id = c.getId().substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(longClick());\n";
			EspressoTranslator.toWrite.add(espresso_command);
			System.out.println(espresso_command);
		}
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

<<<<<<< HEAD
	public void type(Component c){
		String espresso_command = new String();
		String id = c.getId().substring((EspressoTranslator.packageName.length() + 4));
		espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(typeText(\"" + c.getText() + "\"));\n";
		EspressoTranslator.toWrite.add(espresso_command);
		System.out.println(espresso_command);
	}
=======

>>>>>>> origin/master



	public boolean tap(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean longTap(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean pressKey(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean swipe(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean flick(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean pinch(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean scrollTo(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean launchApp(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean closeApp(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean pressMenuKey(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}





	public boolean hideKeyboard(StepTestCase testCase) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
	
	
}
