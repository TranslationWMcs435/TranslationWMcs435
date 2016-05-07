package edu.wm.translationengine.robotium;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.trans.GenericFunctions;

import java.util.List;

/**
 * Specifies how different UI commands should be written
 * for test cases.
 *
 */
public class RobotiumFunctions extends GenericFunctions {
	
	List<String> extras;
	
	public RobotiumFunctions() {
		
	}
	
	public RobotiumFunctions(List<String> extras) {
		this.extras = extras;
	}
	
	public void writeStep(StepTestCase testCase) throws Exception {
		
		String action = testCase.getAction();
		
		RobotiumTranslator.toWrite.add("\n");
		if(action.equals("CLICK")) {
			tap(testCase);
		}
		else if(action.equals("LONG_CLICK")) {
			longTap(testCase);
		}
		else if(action.equals("TYPE")) {
			pressKey(testCase);
		}
		else if(action.toLowerCase().contains("swipe")) {
			swipe(testCase);
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
		String text = testCase.getComponent().getText();

		writeTestName(id);
		
		if(type.equals("com.android.systemui.statusbar.policy.KeyButtonView")) {
			RobotiumTranslator.toWrite.add("\t\tsolo.goBack();");
		}
		else if(type.equals("android.widget.CheckBox") ||
				type.equals("android.widget.CheckedTextView") ||
				type.equals("android.widget.TextView")) {

			RobotiumTranslator.toWrite.add("\t\tsolo.clickOnText(\"" + text + "\");" );
		}
		else if(type.equals("android.widget.ImageView")) {
			
			if(RobotiumTranslator.packageName.equals("com.evancharlton.mileage")) {
				// this is a very bad quick fix to get the test cases running
				
				// This occurs when Robotium cannot click an image view that is nested
				// within an outer layout. 
				
				// this is a limitation of the translator
				RobotiumTranslator.toWrite.add("\t\tsolo.clickOnText(\"Statistics\");" );
			}
			else if(text.equals("")) {
				RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(\"" + id + "\");\n");
				RobotiumTranslator.toWrite.add("\t\tsolo.clickOnView(curView);");
			}
			else {
				RobotiumTranslator.toWrite.add("\t\tsolo.clickOnText(\"" + text + "\");" );
			}
		}
		else if(type.equals("android.widget.Button")) {
			RobotiumTranslator.toWrite.add("\t\tcurView = solo.getView(\"" + id + "\");\n");
			RobotiumTranslator.toWrite.add("\t\tsolo.clickOnView(curView);");
		}
		else if(type.equals("android.widget.EditText")) {
			RobotiumTranslator.toWrite.add("\t\tcurView = solo.getEditText(\"" + text + "\");\n");
			RobotiumTranslator.toWrite.add("\t\tsolo.clickOnView(solo.getView(\"" + id + "\"));");
		}
		else {
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
		String textToType = testCase.getComponent().getText();
		
		writeTestName(id);
		
		// if EditText contains an identifying text
		RobotiumTranslator.toWrite.add("\t\tsolo.enterText( (EditText)curView, \"" + textToType + "\");");
		
		return true;
	}

	@Override
	public boolean swipe(StepTestCase testCase) throws Exception {
		
		String action = testCase.getAction();
		String id = testCase.getComponent().getId();
		String x = testCase.getComponent().getPositionX();
		String y = testCase.getComponent().getPositionY();
				
		writeTestName(id);
		if(action.equals("SWIPE-DOWN-LEFT")) {
			RobotiumTranslator.toWrite.add("\t\tswipeUpRight(" + x + 
					", " + y + ");");
			extras.add(getSwipeUpRight());
		}
		else if(action.equals("SWIPE-DOWN-RIGHT")) {
			RobotiumTranslator.toWrite.add("\t\tswipeUpLeft(" + x + 
					", " + y + ");");
			extras.add(getSwipeUpLeft());
		}
		else if(action.equals("SWIPE-UP-RIGHT")) {
			RobotiumTranslator.toWrite.add("\t\tswipeDownLeft(" + x + 
					", " + y + ");");
			extras.add(getSwipeDownLeft());
		}
		else if(action.contains("SWIPE-UP-LEFT")) {
			RobotiumTranslator.toWrite.add("\t\tswipeDownRight(" + x + 
					", " + y + ");");
			extras.add(getSwipeDownRight());
		}
		else if(action.equals("SWIPE-UP")) {
			RobotiumTranslator.toWrite.add("\t\tswipeUp();");
			extras.add(getSwipeUp());
		}
		else if(action.equals("SWIPE-DOWN")) {
			RobotiumTranslator.toWrite.add("\t\tswipeDown();");
			extras.add(getSwipeDown());
		}
		else if(action.equals("SWIPE-LEFT")) {
			RobotiumTranslator.toWrite.add("\t\tswipeLeft();");
			extras.add(getSwipeLeft());
		}
		else if(action.equals("SWIPE-RIGHT")) {
			RobotiumTranslator.toWrite.add("\t\tswipeRight();");
			extras.add(getSwipeRight());
		}
		
		return true;
	}
	
	private String getSwipeDownRight() {
		
		return
				"	private void swipeDownRight(float endX, float endY) {\r\n" + 
				"		\r\n" + 
				"		Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();\r\n" + 
				"		int width = 0;\r\n" + 
				"		int height = 0;\r\n" + 
				"		\r\n" + 
				"		if(android.os.Build.VERSION.SDK_INT <= 13) {\r\n" + 
				"			width = display.getWidth();\r\n" + 
				"			height = display.getHeight();\r\n" + 
				"		} \r\n" + 
				"		else {\r\n" + 
				"			Point size = new Point();\r\n" + 
				"			display.getSize(size);\r\n" + 
				"			width = size.x;\r\n" + 
				"			height = size.y;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		// down right\r\n" + 
				"		\r\n" + 
				"		float yStart = 50;\r\n" + 
				"		float yEnd = endY;\r\n" + 
				"		if(yEnd < height * 0.75) {\r\n" + 
				"			yEnd = (float)(height * 0.85);\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		float xStart = 50;\r\n" + 
				"		float xEnd = endX;\r\n" + 
				"		if(xEnd < width * 0.75) {\r\n" + 
				"			xEnd = (float) (width * 0.85);\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		solo.drag(xStart, xEnd, yStart, yEnd, 40);\r\n" + 
				"	}\n";
	}
	
	private String getSwipeDownLeft() {
		
		return
				"	private void swipeDownLeft(float endX, float endY) {\r\n" + 
				"		\r\n" + 
				"		// swipe down left\r\n" + 
				"		Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();\r\n" + 
				"		int width = 0;\r\n" + 
				"		int height = 0;\r\n" + 
				"		\r\n" + 
				"		if(android.os.Build.VERSION.SDK_INT <= 13) {\r\n" + 
				"			width = display.getWidth();\r\n" + 
				"			height = display.getHeight();\r\n" + 
				"		} \r\n" + 
				"		else {\r\n" + 
				"			Point size = new Point();\r\n" + 
				"			display.getSize(size);\r\n" + 
				"			width = size.x;\r\n" + 
				"			height = size.y;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		// down left\r\n" + 
				"		float yStart = (float)(height * 0.20);\r\n" + 
				"		float yEnd = endY;\r\n" + 
				"		if(yEnd < height * 0.75) {\r\n" + 
				"			yEnd = (float) (height * 0.85);\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		float xStart = (float) (width * 0.85);;\r\n" + 
				"		float xEnd = endX;\r\n" + 
				"		if(xEnd > width * 0.75) {\r\n" + 
				"			xEnd = 90;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		solo.drag(xStart, xEnd, yStart, yEnd, 40);\r\n" + 
				"	}\n";
	}
	
	private String getSwipeUpRight() {
		
		return 
				"	private void swipeUpRight(float endX, float endY) {\r\n" + 
				"		\r\n" + 
				"		Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();\r\n" + 
				"		int width = 0;\r\n" + 
				"		int height = 0;\r\n" + 
				"		\r\n" + 
				"		if(android.os.Build.VERSION.SDK_INT <= 13) {\r\n" + 
				"			width = display.getWidth();\r\n" + 
				"			height = display.getHeight();\r\n" + 
				"		} \r\n" + 
				"		else {\r\n" + 
				"			Point size = new Point();\r\n" + 
				"			display.getSize(size);\r\n" + 
				"			width = size.x;\r\n" + 
				"			height = size.y;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		float yStart = (float) (height * 0.85);\r\n" + 
				"		float yEnd = endY;\r\n" + 
				"		if(yEnd > height*0.75 )\r\n" + 
				"			yEnd = (float)(height * 0.25);\r\n" + 
				"		\r\n" + 
				"		float xStart = 50;\r\n" + 
				"		float xEnd = endX;\r\n" + 
				"		if(xEnd < width*0.75) {\r\n" + 
				"			xEnd = (float)(width * 0.85);\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		solo.drag(xStart, xEnd, yStart, yEnd, 40);\r\n" + 
				"	}\n";
	}
	
	private String getSwipeUpLeft() {
		
		return
				"	private void swipeUpLeft(float endX, float endY) {\r\n" + 
				"		\r\n" + 
				"		Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();\r\n" + 
				"		int width = 0;\r\n" + 
				"		int height = 0;\r\n" + 
				"		\r\n" + 
				"		if(android.os.Build.VERSION.SDK_INT <= 13) {\r\n" + 
				"			width = display.getWidth();\r\n" + 
				"			height = display.getHeight();\r\n" + 
				"		} \r\n" + 
				"		else {\r\n" + 
				"			Point size = new Point();\r\n" + 
				"			display.getSize(size);\r\n" + 
				"			width = size.x;\r\n" + 
				"			height = size.y;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		// up left\r\n" + 
				"		float yStart = (float) (height * 0.85);;\r\n" + 
				"		float yEnd = endY;\r\n" + 
				"		if(yEnd > height*0.75) {\r\n" + 
				"			yEnd = (float)(height * 0.25);\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		float xStart = (float) (width * 0.85);;\r\n" + 
				"		float xEnd = endX;\r\n" + 
				"		if(xEnd > width * 0.25) {\r\n" + 
				"			xEnd = (float)(width * 0.25);\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		solo.drag(xStart, xEnd, yStart, yEnd, 40);\r\n" + 
				"	}\n";
	}
	
	private String getSwipeUp() {
		
		return 
				"    private void swipeUp() {\r\n" + 
				"		\r\n" + 
				"		// swipe down left\r\n" + 
				"		Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();\r\n" + 
				"		int width = 0;\r\n" + 
				"		int height = 0;\r\n" + 
				"		\r\n" + 
				"		if(android.os.Build.VERSION.SDK_INT <= 13) {\r\n" + 
				"			width = display.getWidth();\r\n" + 
				"			height = display.getHeight();\r\n" + 
				"		} \r\n" + 
				"		else {\r\n" + 
				"			Point size = new Point();\r\n" + 
				"			display.getSize(size);\r\n" + 
				"			width = size.x;\r\n" + 
				"			height = size.y;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		// down left\r\n" + 
				"		float yStart = (float)(height * 0.90);\r\n" + 
				"		float yEnd = 100;\r\n" + 
				"\r\n" + 
				"		float xStart = (float) (width * 0.50);\r\n" + 
				"		float xEnd = xStart;\r\n" + 
				"		\r\n" + 
				"		solo.drag(xStart, xEnd, yStart, yEnd, 40);\r\n" + 
				"	}\n";
	}
	
	private String getSwipeDown() {
		
		return 
				"    private void swipeDown() {\r\n" + 
				"		\r\n" + 
				"		// swipe down left\r\n" + 
				"		Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();\r\n" + 
				"		int width = 0;\r\n" + 
				"		int height = 0;\r\n" + 
				"		\r\n" + 
				"		if(android.os.Build.VERSION.SDK_INT <= 13) {\r\n" + 
				"			width = display.getWidth();\r\n" + 
				"			height = display.getHeight();\r\n" + 
				"		} \r\n" + 
				"		else {\r\n" + 
				"			Point size = new Point();\r\n" + 
				"			display.getSize(size);\r\n" + 
				"			width = size.x;\r\n" + 
				"			height = size.y;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		// down left\r\n" + 
				"		float yEnd = (float)(height * 0.90);\r\n" + 
				"		float yStart = 200;\r\n" + 
				"\r\n" + 
				"		float xStart = (float) (width * 0.50);\r\n" + 
				"		float xEnd = xStart;\r\n" + 
				"		\r\n" + 
				"		solo.drag(xStart, xEnd, yStart, yEnd, 40);\r\n" + 
				"	}\n";
	}
	
	private String getSwipeLeft() {
		
		return 
				"    private void swipeLeft() {\r\n" + 
				"		\r\n" + 
				"		// swipe down left\r\n" + 
				"		Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();\r\n" + 
				"		int width = 0;\r\n" + 
				"		int height = 0;\r\n" + 
				"		\r\n" + 
				"		if(android.os.Build.VERSION.SDK_INT <= 13) {\r\n" + 
				"			width = display.getWidth();\r\n" + 
				"			height = display.getHeight();\r\n" + 
				"		} \r\n" + 
				"		else {\r\n" + 
				"			Point size = new Point();\r\n" + 
				"			display.getSize(size);\r\n" + 
				"			width = size.x;\r\n" + 
				"			height = size.y;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		// down left\r\n" + 
				"		float yEnd = (float)(height * 0.90);\r\n" + 
				"		float yStart = yEnd;\r\n" + 
				"\r\n" + 
				"		float xStart = (float) (width * 0.90);\r\n" + 
				"		float xEnd = 100;\r\n" + 
				"		\r\n" + 
				"		solo.drag(xStart, xEnd, yStart, yEnd, 40);\r\n" + 
				"	}\n";
	}
	
	private String getSwipeRight() {
		
		return 
				"    private void swipeRight() {\r\n" + 
				"		\r\n" + 
				"		// swipe down left\r\n" + 
				"		Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();\r\n" + 
				"		int width = 0;\r\n" + 
				"		int height = 0;\r\n" + 
				"		\r\n" + 
				"		if(android.os.Build.VERSION.SDK_INT <= 13) {\r\n" + 
				"			width = display.getWidth();\r\n" + 
				"			height = display.getHeight();\r\n" + 
				"		} \r\n" + 
				"		else {\r\n" + 
				"			Point size = new Point();\r\n" + 
				"			display.getSize(size);\r\n" + 
				"			width = size.x;\r\n" + 
				"			height = size.y;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		// down left\r\n" + 
				"		float yEnd = (float)(height * 0.90);\r\n" + 
				"		float yStart = yEnd;\r\n" + 
				"\r\n" + 
				"		float xEnd = (float) (width * 0.90);\r\n" + 
				"		float xStart = 100;\r\n" + 
				"		\r\n" + 
				"		solo.drag(xStart, xEnd, yStart, yEnd, 40);\r\n" + 
				"	}\n";
	}
	
	private void writeTestName(String id) {
		
		if(id != null && !id.equals("")) {
			RobotiumTranslator.toWrite.add("\t\t// " + id + "\n");
		}
		else {
			RobotiumTranslator.toWrite.add("\t\t// Unable to get test case name because of missing component id\n");
		}
	}

}