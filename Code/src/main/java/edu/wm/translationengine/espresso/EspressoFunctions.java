package edu.wm.translationengine.espresso;


//import espresso.EspressoTranslator;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.trans.GenericFunctions;
import edu.wm.translationengine.trans.StepTestCaseDataPrinter;

public class EspressoFunctions extends GenericFunctions{
	private boolean clicky;
	private boolean long_clicky;
	private boolean typey;
	private boolean swipe_upy;
	private boolean swipe_downy;
	private boolean swipe_lefty;
	private boolean swipe_righty;
	StepTestCaseDataPrinter p;
	String storedId;
	String storedText;
	boolean useId; //if true use id in type else use the text of the component
	
	/**
	 * Constructor for the EspressoFunctions class
	 */
	public EspressoFunctions(){
		clicky = false;
		long_clicky = false;
		typey = false;
		swipe_upy = false;
		swipe_downy = false;
		swipe_lefty = false;
		swipe_righty = false;
		p = new StepTestCaseDataPrinter();
		storedId = "";
		storedText = "";
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
				EspressoTranslator.toWrite.add(1, "import static android.support.test.espresso.action.ViewActions.click;\n");
				clicky = true;
			}
			make_click_command(s);
		}
		if(action.equals("LONG_CLICK")){
			if(long_clicky == false){
				EspressoTranslator.toWrite.add(1, "import static android.support.test.espresso.action.ViewActions.longClick;\n"); 
				long_clicky = true;
			}
			make_long_click_command(s);
		}
		if(action.equals("TYPE")){
			if(typey == false){
				EspressoTranslator.toWrite.add(1, "import static android.support.test.espresso.action.ViewActions.typeText;\n"); 
				EspressoTranslator.toWrite.add(1, "import static android.support.test.espresso.matcher.ViewMatchers.withHint;\n"); 
				typey = true;
			}
			type(s);
		}
		if(action.equals("SWIPE-UP")){
			if(swipe_upy == false){
				EspressoTranslator.toWrite.add(1, "import static android.support.test.espresso.action.ViewActions.swipeUp;\n");
				swipe_upy = true;
			}
			swipeUp(s);
		}
		if(action.equals("SWIPE-DOWN")){
			if(swipe_downy == false){
				EspressoTranslator.toWrite.add(1, "import static android.support.test.espresso.action.ViewActions.swipeDown;\n");
				swipe_downy = true;
			}
			swipeDown(s);
		}
		if(action.equals("SWIPE-LEFT")){
			if(swipe_lefty == false){
				EspressoTranslator.toWrite.add(1, "import static android.support.test.espresso.action.ViewActions.swipeLeft;\n");
				swipe_lefty = true;
			}
			swipeLeft(s);
		}
		if(action.equals("SWIPE-RIGHT")){
			if(swipe_righty == false){
				EspressoTranslator.toWrite.add(1, "import static android.support.test.espresso.action.ViewActions.swipeRight;\n");
				swipe_righty = true;
			}
			swipeRight(s);
		}
	}

	/**
	 * Generates the click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_click_command(StepTestCase s) {
		String espresso_command = new String();

		
		if(s.getComponent().getType().equals("android.widget.EditText")){
			if(s.getComponent().getId().equals("")){
				storedText = s.getComponent().getText();
				useId = false;
			}
			else{
				storedId = s.getComponent().getId();
				useId = true;
			}
		}

		if(s.getComponent().getType().equals("android.widget.CheckedTextView")){
			espresso_command += "\t\t\tonData(allOf(is(\"" + s.getComponent().getText() + "\"))).perform(click());\n";
			EspressoTranslator.toWrite.add(espresso_command);
		}
		else if(s.getComponent().getType().equals("android.widget.ImageButton")){
			espresso_command += "\t\t\tonView(withContentDescription(\"" + s.getComponent().getDescription() + "\")).perform(click());\n";
			EspressoTranslator.toWrite.add(espresso_command);
		}
		else if(s.getComponent().getId().equals("")){
			make_click_command_with_text(s);
		}
		else if(s.getComponent().getId().length() > 11 && s.getComponent().getId().substring(0, 11).equals("android:id/")){
			make_click_command_with_text(s);
		}
		else{
			String id = s.getComponent().getId().substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(click());\n";
			EspressoTranslator.toWrite.add(espresso_command);
		}
	}
	
	/**
	 * Generates the click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_click_command_with_text(StepTestCase s) {
		String espresso_command = new String();
		if(s.getComponent().getType().equals("android.widget.EditText")){
			espresso_command += "\t\t\tonView(withHint(\"" + s.getComponent().getText() + "\")).perform(click());\n";
		}
		else{
			espresso_command += "\t\t\tonView(withText(\"" + s.getComponent().getText() + "\")).perform(click());\n";
		}
		EspressoTranslator.toWrite.add(espresso_command);
	}
	
	/**
	 * Generates the long click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_long_click_command(StepTestCase s) {
		String espresso_command = new String();

		if(s.getComponent().getType().equals("android.widget.CheckedTextView")){
			espresso_command += "\t\t\tonData(allOf(is(\"" + s.getComponent().getText() + "\"))).perform(longClick());\n";
			EspressoTranslator.toWrite.add(espresso_command);
		}
		else if(s.getComponent().getType().equals("android.widget.ImageButton")){
			espresso_command += "\t\t\tonView(withContentDescription(\"" + s.getComponent().getDescription() + "\")).perform(longClick());\n";
			EspressoTranslator.toWrite.add(espresso_command);
		}
		else if(s.getComponent().getId().equals("")){
			make_long_click_command_with_text(s);
		}
		else if(s.getComponent().getId().length() > 11 && s.getComponent().getId().substring(0, 11).equals("android:id/")){
			make_long_click_command_with_text(s);
		}
		else{
			String id = s.getComponent().getId().substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(longClick());\n";
			EspressoTranslator.toWrite.add(espresso_command);
		}

	
		
	}
	/**
	 * Generates the long click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_long_click_command_with_text(StepTestCase s) {
		String espresso_command = new String();	
		if(s.getComponent().getType().equals("android.widget.EditText")){
			espresso_command += "\t\t\tonView(withHint(\"" + s.getComponent().getText() + "\")).perform(longClick());\n";
		}
		else{
			espresso_command += "\t\t\tonView(withText(\"" + s.getComponent().getText() + "\")).perform(longClick());\n";
		}
		EspressoTranslator.toWrite.add(espresso_command);
	}

	public void type(StepTestCase s){
		String espresso_command = new String();
		if(useId == true){
			String id = storedId.substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(typeText(\"" + s.getComponent().getText() + "\"));\n";
		}
		else{
			espresso_command += "\t\t\tonView(withHint(\"" + storedText + "\")).perform(typeText(\"" + s.getComponent().getText() + "\"));\n";
		}
		EspressoTranslator.toWrite.add(espresso_command);
	}

	public void swipeUp(StepTestCase s){
		String espresso_command = new String();
		if(s.getComponent().getType().equals("android.widget.CheckedTextView")){
			espresso_command += "\t\t\tonData(allOf(is(\"" + s.getComponent().getText() + "\"))).perform(swipeUp());\n";
		}
		else{
			String id = s.getComponent().getId().substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(swipeUp())\n";
		}
		EspressoTranslator.toWrite.add(espresso_command);
	}
	public void swipeDown(StepTestCase s){
		String espresso_command = new String();
		if(s.getComponent().getType().equals("android.widget.CheckedTextView")){
			espresso_command += "\t\t\tonData(allOf(is(\"" + s.getComponent().getText() + "\"))).perform(swipeDown());\n";
		}
		else{
			String id = s.getComponent().getId().substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(swipeDown())\n";
		}
		EspressoTranslator.toWrite.add(espresso_command);
	}
	public void swipeLeft(StepTestCase s){
		String espresso_command = new String();
		if(s.getComponent().getType().equals("android.widget.CheckedTextView")){
			espresso_command += "\t\t\tonData(allOf(is(\"" + s.getComponent().getText() + "\"))).perform(swipeLeft());\n";
		}
		else{
			String id = s.getComponent().getId().substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(swipeLeft())\n";
		}
		EspressoTranslator.toWrite.add(espresso_command);
	}
	public void swipeRight(StepTestCase s){
		String espresso_command = new String();
		if(s.getComponent().getType().equals("android.widget.CheckedTextView")){
			espresso_command += "\t\t\tonData(allOf(is(\"" + s.getComponent().getText() + "\"))).perform(swipeRight());\n";
		}
		else{
			String id = s.getComponent().getId().substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(swipeRight())\n";
		}
		EspressoTranslator.toWrite.add(espresso_command);
	}

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
