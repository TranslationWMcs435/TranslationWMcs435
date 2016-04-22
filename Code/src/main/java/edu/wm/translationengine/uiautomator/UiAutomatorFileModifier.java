package edu.wm.translationengine.uiautomator;

import java.io.IOException;

import edu.wm.translationengine.trans.FileModifierInterface;

public class UiAutomatorFileModifier implements FileModifierInterface{

	public void setupFileImports() throws IOException {
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.core.UiDevice;\n");
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.core.UiObject;\n");
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.core.UiObjectNotFoundException;\n");
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.core.UiSelector;\n");
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.testrunner.UiAutomatorTestCase;\n\n");
	}

	public void setupTestMethodHeader(String appName, String mainActivity) {
		// Makes the header if we don't have an app name.
		UiAutomatorTranslator.toWrite.add("public class TestBackPress extends UiAutomatorTestCase {\n");
		UiAutomatorTranslator.toWrite.add("\tpublic void test_uiautomator() throws UiObjectNotFoundException{\n");
		
		// Kind of lying with the name mainActivity here.
		if(mainActivity.equals("")){
			this.openApp(appName);
		}
	}
	
	public void openApp(String appName) {
		// Makes a complicated Rube-Goldberg contraption to navigate to the home page and then the app.
		// Output looks like:
		/**
			getUiDevice().pressHome();
	        UiObject Applications = new UiObject(new UiSelector().description("Apps"));
	        Applications.clickAndWaitForNewWindow();
	        int i = 0;
	        while(i < 10){
	            try{
	                new UiObject(new UiSelector().text("Mileage")).click(); //Only real change here.
	                i = 10;
	            } catch(Exception e){
	                i++;
	                getUiDevice().swipe(500, 500, 10, 500, 5); //Assumed workable for most screen sizes.
	            }
	        }
		 */
		UiAutomatorTranslator.toWrite.add("\t\tgetUiDevice().pressHome();\n");
		UiAutomatorTranslator.toWrite.add("\t\tUiObject Applications = new UiObject(new UiSelector().description(\"Apps\"));\n");
		UiAutomatorTranslator.toWrite.add("\t\tApplications.clickAndWaitForNewWindow();\n");
		UiAutomatorTranslator.toWrite.add("\t\tint i = 0;\n");
		UiAutomatorTranslator.toWrite.add("\t\twhile(i < 10){\n");
		UiAutomatorTranslator.toWrite.add("\t\t\ttry{\n");
		UiAutomatorTranslator.toWrite.add("\t\t\t\tnew UiObject(new UiSelector().text(\"" + appName + "\")).click();\n");
		UiAutomatorTranslator.toWrite.add("\t\t\t\ti = 10;\n");
		UiAutomatorTranslator.toWrite.add("\t\t\t} catch(Exception e){\n");
		UiAutomatorTranslator.toWrite.add("\t\t\t\ti++;\n");
		UiAutomatorTranslator.toWrite.add("\t\t\t\tgetUiDevice().swipe(500, 500, 10, 500, 5);\n");
		UiAutomatorTranslator.toWrite.add("\t\t\t}\n\t\t}\n");
	}

	public void closeTestMethod() {
		// TODO Auto-generated method stub
		UiAutomatorTranslator.toWrite.add("\t}\n}");
		UiAutomatorTranslator.toWrite.add(0, "package [Test_package_name_here]\n\n");
	}

}
