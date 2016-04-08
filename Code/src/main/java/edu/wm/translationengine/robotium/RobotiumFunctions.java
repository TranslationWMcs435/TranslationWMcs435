package edu.wm.translationengine.robotium;

import java.util.Arrays;
import java.util.List;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.trans.GenericFunctions;

/**
 * Specifies how different UI commands should be written
 * for test cases.
 *
 */
public class RobotiumFunctions extends GenericFunctions {
	
	public void writeStep(StepTestCase testCase) throws Exception {
		
		if(testCase == null) {
			System.out.println("Test case is null");
			RobotiumTranslator.toWrite.add("\t\t //Error: Test case is null");
			return;
		}
		
		String action = testCase.getAction();
		if(action == null) {
			System.out.println("Action is null");
			RobotiumTranslator.toWrite.add("\t\t //Error: Test case action is null");
			return;
		}
		
		if(testCase.getComponent() == null) {
			System.out.println("Component is null");
			RobotiumTranslator.toWrite.add("\t\t //Error: Component is null");
		}
		
		RobotiumTranslator.toWrite.add("\n");
		if(action.equalsIgnoreCase("CLICK")) {
			tap(testCase);
		}
		else if(action.equalsIgnoreCase("LONG_CLICK")) {
			longTap(testCase);
		}
		else if(action.equalsIgnoreCase("TYPE")) {
			pressKey(testCase);
		}
		
		RobotiumTranslator.toWrite.add("\n");
	}

	@Override
	public boolean tap(StepTestCase testCase) throws Exception {
		
		try 
		{
			String id = testCase.getComponent().getId();
			String type = testCase.getComponent().getType();
			String index = testCase.getComponent().getIndex();
			
			throwEmptyInfo(Arrays.asList(id,type,index));
			
			RobotiumTranslator.toWrite.add("\t\t// " + id + "\n");
			
			if(type.equalsIgnoreCase("com.android.systemui.statusbar.policy.KeyButtonView")) 
			{
				RobotiumTranslator.toWrite.add("\t\tsolo.goBack();\n");
			}
			else 
			{
				RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(" + type + ".class, " + index + ");\n");
				RobotiumTranslator.toWrite.add("\t\tsolo.clickOnView(curView);");
			}
			
		} catch(Exception e) {
			System.out.println("Information needed for tap action is missing. Error: " + e.toString());
			System.out.println("Error has been written out to the output file...");
			RobotiumTranslator.toWrite.add("\t\t//Error: Information missing for tap action. " + e.toString());
		}
		
		return true;
	}

	@Override
	public boolean longTap(StepTestCase testCase) throws Exception {

		try 
		{
			String id = testCase.getComponent().getId();
			String type = testCase.getComponent().getType();
			String index = testCase.getComponent().getIndex();
			
			throwEmptyInfo(Arrays.asList(id,type,index));
			
			RobotiumTranslator.toWrite.add("\t\t// " + id + "\n");
			RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(" + type + ".class, " + index + ");\n");
			RobotiumTranslator.toWrite.add("\t\tsolo.clickLongOnView(curView, 800);");
			
		} catch(Exception e) 
		{
			System.out.println("Information needed for tap action is missing. Error: " + e.toString());
			System.out.println("Error has been written out to the output file...");
			RobotiumTranslator.toWrite.add("\t\t//Error: Information missing for tap action. " + e.toString());
		}
	
		return true;
	}

	@Override
	public boolean pressKey(StepTestCase testCase) throws Exception {
		
		try
		{
			String id = testCase.getComponent().getId();
			String type = testCase.getComponent().getType();
			String index = testCase.getComponent().getIndex();
			String textToType = testCase.getComponent().getText();

			throwEmptyInfo(Arrays.asList(id,type,index,textToType));
			RobotiumTranslator.toWrite.add("\t\t// " + id + "\n");
			RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(" + type + ".class, " + index + ");\n");
			RobotiumTranslator.toWrite.add("\t\tsolo.enterText(" + index + ", \"" + textToType + "\");");
			
		} catch(Exception e) 
		{
			System.out.println("Information needed for tap action is missing. Error: " + e.toString());
			System.out.println("Error has been written out to the output file...");
			RobotiumTranslator.toWrite.add("\t\t//Error: Information missing for tap action. " + e.toString());
		}
	
		return true;
	}

	@Override
	public boolean swipe(StepTestCase testCase) throws Exception {
		
		return false;
	}

	@Override
	public boolean flick(StepTestCase testCase) throws Exception {
	
		return false;
	}

	@Override
	public boolean pinch(StepTestCase testCase) throws Exception {
		return false;
	}

	@Override
	public boolean scrollTo(StepTestCase testCase) throws Exception {
		return false;
	}

	@Override
	public boolean launchApp(StepTestCase testCase) throws Exception {
		return false;
	}

	@Override
	public boolean closeApp(StepTestCase testCase) throws Exception {
		return false;
	}

	@Override
	public boolean pressMenuKey(StepTestCase testCase) throws Exception {
		return false;
	}

	@Override
	public boolean hideKeyboard(StepTestCase testCase) throws Exception {
		return false;
	}
	
	private void throwEmptyInfo(List<String> a) throws Exception {
		
		for(String s: a) {
			if(s.equals("")) {
				throw new Exception("invalid JSON FILE key-value pair...");
			}
		}
	}
	
}