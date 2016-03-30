package edu.wm.translationengine.uiautomator;



import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.espresso.EspressoTranslator;
import edu.wm.translationengine.trans.Functions;
import edu.wm.translationengine.trans.GenericFunctions;

public class UiAutomatorFunctions implements Functions{

	
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
	}
	
	public boolean tap(StepTestCase testCase) throws Exception {
		String uiautomator_command = new String();	
		if(testCase.getComponent().getType().equals("android.widget.CheckedTextView")){
			uiautomator_command += "\t\tnew UiObject(new UiSelector().className(\"android.widget.ListView\")).getChild(new UiSelector().text(\"" + testCase.getComponent().getText() + "\")).click();\n";
			UiAutomatorTranslator.toWrite.add(uiautomator_command);
		}
		else{
			uiautomator_command += "\t\tnew UiObject(new UiSelector().resourceId(\"" + testCase.getComponent().getId() + "\")).click();\n";
			UiAutomatorTranslator.toWrite.add(uiautomator_command);
		}
		
		return true;
	}

	public boolean longTap(StepTestCase testCase) throws Exception {
		String uiautomator_command = new String();		
		if(testCase.getComponent().getType().equals("android.widget.CheckedTextView")){
			uiautomator_command += "\t\tnew UiObject(new UiSelector().className(\"android.widget.ListView\")).getChild(new UiSelector().text(\"" + testCase.getComponent().getText() + "\")).longClick();\n";
			UiAutomatorTranslator.toWrite.add(uiautomator_command);
		}
		else{
		uiautomator_command += "\t\tnew UiObject(new UiSelector().resourceId(\"" + testCase.getComponent().getId() + "\")).longClick();\n";
		UiAutomatorTranslator.toWrite.add(uiautomator_command);
		}
		return true;
	}

	public boolean type(StepTestCase testCase) throws Exception{
		String uiautomator_command = new String();		
		uiautomator_command += "\t\tnew UiObject(new UiSelector().resourceId(\"" + testCase.getComponent().getId() + "\")).setText(\"" + testCase.getComponent().getText() + "\");\n";
		UiAutomatorTranslator.toWrite.add(uiautomator_command);
		return true;
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
