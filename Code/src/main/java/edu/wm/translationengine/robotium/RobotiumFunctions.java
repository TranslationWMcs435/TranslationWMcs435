package edu.wm.translationengine.robotium;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.trans.GenericFunctions;

/**
 * Specifies how different UI commands should be written
 * for test cases.
 *
 */
public class RobotiumFunctions extends GenericFunctions {
	
	public void writeStep(StepTestCase testCase) throws Exception {
		
		String action = testCase.getAction();
		
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
	
	/**
	 * Need: type, index
	 */
	@Override
	public boolean tap(StepTestCase testCase) throws Exception {

		String id = testCase.getComponent().getId();
		String type = testCase.getComponent().getType();
		String index = testCase.getComponent().getIndex();

		writeTestName(id);
		
		if(type.equals("com.android.systemui.statusbar.policy.KeyButtonView")) 
		{
			RobotiumTranslator.toWrite.add("\t\tsolo.goBack();\n");
		}
		else 
		{
			RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(" + type + ".class, " + index + ");\n");
			RobotiumTranslator.toWrite.add("\t\tsolo.clickOnView(curView);");
		}
	
		return true;
	}
	
	/**
	 * Need: type, index
	 */
	@Override
	public boolean longTap(StepTestCase testCase) throws Exception {

		String id = testCase.getComponent().getId();
		String type = testCase.getComponent().getType();
		String index = testCase.getComponent().getIndex();
		
		writeTestName(id);
		
		RobotiumTranslator.toWrite.add("\t\t// " + id + "\n");
		RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(" + type + ".class, " + index + ");\n");
		RobotiumTranslator.toWrite.add("\t\tsolo.clickLongOnView(curView, 800);");

		return true;
	}

	/**
	 * Need: type, index, text
	 */
	@Override
	public boolean pressKey(StepTestCase testCase) throws Exception {
		
		String id = testCase.getComponent().getId();
		String type = testCase.getComponent().getType();
		String index = testCase.getComponent().getIndex();
		String textToType = testCase.getComponent().getText();
		
		writeTestName(id);
		
		RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(" + type + ".class, " + index + ");\n");
		RobotiumTranslator.toWrite.add("\t\tsolo.enterText(" + index + ", \"" + textToType + "\");");
	
		return true;
	}

	@Override
	public boolean swipe(StepTestCase testCase) throws Exception {
		
		return false;
	}
	
	private void writeTestName(String id) {
		
		if(id != null && !id.equals("")) {
			RobotiumTranslator.toWrite.add("\t\t// " + id + "\n");
		}
		else {
			RobotiumTranslator.toWrite.add("\t\t// Unable to get test case name becase of missing component id\n");
		}
	}

}