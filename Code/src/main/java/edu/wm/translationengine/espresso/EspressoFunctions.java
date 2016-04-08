package edu.wm.translationengine.espresso;


//import espresso.EspressoTranslator;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.trans.GenericFunctions;
import edu.wm.translationengine.trans.StepTestCaseDataPrinter;

public class EspressoFunctions extends GenericFunctions{
	private boolean clicky;
	private boolean long_clicky;
	private boolean typey;
	StepTestCaseDataPrinter p;

	/**
	 * Constructor for the EspressoFunctions class
	 */
	public EspressoFunctions(){
		clicky = false;
		long_clicky = false;
		typey = false;
		p = new StepTestCaseDataPrinter();
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
			make_click_command(s);
		}
		if(action.equals("LONG_CLICK")){
			if(long_clicky == false){
				EspressoTranslator.toWrite.add(0, "import static android.support.test.espresso.action.ViewActions.longClick;\n"); 
				long_clicky = true;
			}
			make_long_click_command(s);
		}
		if(action.equals("TYPE")){
			if(typey == false){
				EspressoTranslator.toWrite.add(0, "import static android.support.test.espresso.action.ViewActions.typeText;\n"); 
				typey = true;
			}
			type(s);
		}
	}

	/**
	 * Generates the click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_click_command(StepTestCase s) {
		String espresso_command = new String();
		try{
			if(s.getComponent().getType().equals("android.widget.CheckedTextView")){
				espresso_command += "\t\t\tonData(allOf(is(\"" + s.getComponent().getText() + "\"))).perform(click());\n";
				EspressoTranslator.toWrite.add(espresso_command);
			}
			else if(s.getComponent().getId().length() > 11 && s.getComponent().getId().substring(0, 11).equals("android:id/")){
				make_click_command_with_text(s);
			}
			else{
				String id = s.getComponent().getId().substring((EspressoTranslator.packageName.length() + 4));
				espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(click());\n";
				EspressoTranslator.toWrite.add(espresso_command);
			}
		}catch(Exception e){
			System.out.println("\nERROR: Error with following action:\n");
			p.printData(s);
			EspressoTranslator.toWrite.add("\t\t\t\\\\This StepTestCase had an error, its code was not written to file.\n");
		}
		
	}
	
	/**
	 * Generates the click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_click_command_with_text(StepTestCase s) {
		String espresso_command = new String();		
		try{
			espresso_command += "\t\t\tonView(withText(\"" + s.getComponent().getText() + "\")).perform(click());\n";
			EspressoTranslator.toWrite.add(espresso_command);
		}
		catch(Exception e){
			System.out.println("\nERROR: Error with following action:\n");
			p.printData(s);
			EspressoTranslator.toWrite.add("\t\t\t\\\\This StepTestCase had an error, its code was not written to file.\n");
		}
		

	}
	
	/**
	 * Generates the long click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_long_click_command(StepTestCase s) {
		String espresso_command = new String();
		try{
			if(s.getComponent().getType().equals("android.widget.CheckedTextView")){
				espresso_command += "\t\t\tonData(allOf(is(\"" + s.getComponent().getText() + "\"))).perform(longClick());\n";
				EspressoTranslator.toWrite.add(espresso_command);
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
		catch(Exception e){
			System.out.println("\nERROR: Error with following action:\n");
			p.printData(s);
			EspressoTranslator.toWrite.add("\t\t\t\\\\This StepTestCase had an error, its code was not written to file.\n");

		}
		
		
	}
	/**
	 * Generates the long click command formatted in the Espresso framework
	 * @param c Component object of the current stepTestCase being analyzed
	 */
	private void make_long_click_command_with_text(StepTestCase s) {
		
		try{
			String espresso_command = new String();	
			espresso_command += "\t\t\tonView(withText(\"" + s.getComponent().getText() + "\")).perform(longClick());\n";
			EspressoTranslator.toWrite.add(espresso_command);
		}
		catch(Exception e){
			System.out.println("\nERROR: Error with following action:\n");
			p.printData(s);
			EspressoTranslator.toWrite.add("\t\t\t\\\\This StepTestCase had an error, its code was not written to file.\n");

		}
	}

	public void type(StepTestCase s){
		try{
			String espresso_command = new String();
			String id = s.getComponent().getId().substring((EspressoTranslator.packageName.length() + 4));
			espresso_command += "\t\t\tonView(withId(R.id." + id + ")).perform(typeText(\"" + s.getComponent().getText() + "\"));\n";
			EspressoTranslator.toWrite.add(espresso_command);
		}
		catch(Exception e){
			System.out.println("\nERROR: Error with following action:\n");
			p.printData(s);
			EspressoTranslator.toWrite.add("\t\t\t\\\\This StepTestCase had an error, its code was not written to file.\n");

			
		}

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
