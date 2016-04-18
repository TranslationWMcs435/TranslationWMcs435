package edu.wm.translationengine.uiautomator;



import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.trans.Functions;
import edu.wm.translationengine.trans.StepTestCaseDataPrinter;

public class UiAutomatorFunctions implements Functions{
	
	StepTestCaseDataPrinter p;
	String last_id = "";
	String last_text = "";
	
	public UiAutomatorFunctions(){
		p = new StepTestCaseDataPrinter();
	}
	
	public void uiautomator_switcher(String action, StepTestCase s) throws Exception{
		
		if(action.equals("CLICK")){
			tap(s);
		}
		if(action.equals("LONG_CLICK")){
			longTap(s);
		}
		if(action.equals("TYPE")){
			type(s);
		}
		if(action.contains("SWIPE")){
			System.out.println("Swiped");
			swipe(s);
		}
		
		last_id = s.getComponent().getId();
		last_text = s.getComponent().getText();
		/*
		if (s.getComponent().getId().equals("")){
			last_id = s.getComponent().getId();
		}else if(s.getComponent().getText() != null){
			last_id = null;
			last_text = s.getComponent().getText();
		}else{
			last_id = null;
			last_text = null;
		}*/
		
	}
	
	public boolean tap(StepTestCase testCase) throws Exception {
		String uiautomator_command = new String();	
		
		if(testCase.getComponent().getId().equals("")){
			System.out.println("Got in, but no ID: " + testCase.getComponent().getText());
			uiautomator_command += "\t\tnew UiObject(new UiSelector().text(\"" + testCase.getComponent().getText() + "\")).click();\n";
			UiAutomatorTranslator.toWrite.add(uiautomator_command);
		}else{
			if(testCase.getComponent().getType().equals("android.widget.CheckedTextView")){
				uiautomator_command += "\t\tnew UiObject(new UiSelector().className(\"android.widget.ListView\")).getChild(new UiSelector().text(\"" + testCase.getComponent().getText() + "\")).click();\n";
				UiAutomatorTranslator.toWrite.add(uiautomator_command);
				if(testCase.getComponent().getText() == null){
					System.out.println("Text field is null in the following stepTestCase:\n");
					p.printData(testCase);
				}
			}
			else{
				uiautomator_command += "\t\tnew UiObject(new UiSelector().resourceId(\"" + testCase.getComponent().getId() + "\")).click();\n";
				UiAutomatorTranslator.toWrite.add(uiautomator_command);
				if(testCase.getComponent().getId() == null){
					System.out.println("Id field is null in the following stepTestCase:\n");
					p.printData(testCase);
				}
			}
		}
		
		return true;
	}

	public boolean longTap(StepTestCase testCase) throws Exception {
		String uiautomator_command = new String();		
		if(testCase.getComponent().getType().equals("android.widget.CheckedTextView")){
			uiautomator_command += "\t\tnew UiObject(new UiSelector().className(\"android.widget.ListView\")).getChild(new UiSelector().text(\"" + testCase.getComponent().getText() + "\")).longClick();\n";
			UiAutomatorTranslator.toWrite.add(uiautomator_command);
			if(testCase.getComponent().getText() == null){
				System.out.println("Text field is null in the following stepTestCase:\n");
				p.printData(testCase);
			}
		}
		else{
		uiautomator_command += "\t\tnew UiObject(new UiSelector().resourceId(\"" + testCase.getComponent().getId() + "\")).longClick();\n";
		UiAutomatorTranslator.toWrite.add(uiautomator_command);
		if(testCase.getComponent().getId() == null){
			System.out.println("Id field is null in the following stepTestCase:\n");
			p.printData(testCase);
		}
		}
		return true;
	}

	public boolean type(StepTestCase testCase) throws Exception{
		String uiautomator_command = "";		
		if(testCase.getComponent().getId().equals("")){
			System.out.println("Id field is null in the following stepTestCase:\n");
			if (last_id.equals("")){
				System.out.println("No ID on current or last input. Basing from text on last input.");
				uiautomator_command += "\t\tnew UiObject(new UiSelector().text(\"" + last_text + "\")).setText(\"" + testCase.getComponent().getText() + "\");\n";
			}else{
				System.out.println("ID found from last input.");
				uiautomator_command += "\t\tnew UiObject(new UiSelector().resourceId(\"" + last_id + "\")).setText(\"" + testCase.getComponent().getText() + "\");\n";
				//p.printData(testCase);
			}
		}else if(testCase.getComponent().getId().equals("id/keyboard_view")){
			System.out.println("ID is keyboard, basing location on prior click.");
			if (last_id.equals("")){
				System.out.println("No ID on current or last input. Basing from text on last input.");
				uiautomator_command += "\t\tnew UiObject(new UiSelector().text(\"" + last_text + "\")).setText(\"" + testCase.getComponent().getText() + "\");\n";
			}else{
				System.out.println("ID found from last input.");
				uiautomator_command += "\t\tnew UiObject(new UiSelector().resourceId(\"" + last_id + "\")).setText(\"" + testCase.getComponent().getText() + "\");\n";
				//p.printData(testCase);
			}
		}
		if(testCase.getComponent().getText().equals("")){
			System.out.println("Text field is null in the following stepTestCase, so nothing will be typed in-app:\n");
			p.printData(testCase);
		}
		UiAutomatorTranslator.toWrite.add(uiautomator_command);
		System.out.println(testCase.getComponent().getId());
		return true;
	}
	public boolean pressKey(StepTestCase testCase) throws Exception {
		// Haven't seen as an input yet.
		return false;
	}

	public boolean swipe(StepTestCase testCase) throws Exception {
		// Requires: Stuff
		// public boolean swipe (int startX, int startY, int endX, int endY, int steps)
		// The trick is to figure out the end positions when you only know the start and the direction.
		String uiautomator_command = "";
		int startX = Integer.parseInt(testCase.getComponent().getPositionX());
		int startY = Integer.parseInt(testCase.getComponent().getPositionY());
		int endY;
		int endX;
		
		
		// Generate the end coordinates based on initial ones and direction.
		switch(testCase.getComponent().getType()){
		case "SWIPE-UP-RIGHT":
			break;
		case "SWIPE-UP-LEFT":
			break;
		case "SWIPE-DOWN-RIGHT":
			break;
		case "SWIPE-DOWN-LEFT":
			break;
		}
		
		// Actually add the string to uiautomator_command
		
		UiAutomatorTranslator.toWrite.add(uiautomator_command);
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
		// Stretch goal: Figure out a generic name for the app based on JSON. May be impossible.
		return false;
	}

	public boolean closeApp(StepTestCase testCase) throws Exception {
		// Exit the application at the end of the test.
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
