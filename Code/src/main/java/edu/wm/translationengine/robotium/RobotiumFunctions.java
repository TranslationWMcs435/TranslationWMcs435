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
		if(action.equals("CLICK")) {
			tap(testCase);
		}
		else if(action.equals("LONG_CLICK")) {
			longTap(testCase);
		}
		RobotiumTranslator.toWrite.add("\n");
		
	}

	@Override
	public boolean tap(StepTestCase testCase) throws Exception {
		
		if(testCase != null) {
			
			RobotiumTranslator.toWrite.add("\t\t// " + testCase.getComponent().getId() + "\n");
			RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(" + testCase.getComponent().getType() + ".class, " + testCase.getComponent().getIndex() + ");\n");
			RobotiumTranslator.toWrite.add("\t\tsolo.clickOnView(curView);");			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean longTap(StepTestCase testCase) throws Exception {
		
		if(testCase != null) {
			
			RobotiumTranslator.toWrite.add("\t\t// " + testCase.getComponent().getId() + "\n");
			RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(" + testCase.getComponent().getType() + ".class, " + testCase.getComponent().getIndex() + ");\n");
			RobotiumTranslator.toWrite.add("\t\tsolo.clickLongOnView(curView, 800);");
			return true;
		}
		
		return false;
	}

	@Override
	public boolean pressKey(StepTestCase testCase) throws Exception {
		return false;
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
	
}
