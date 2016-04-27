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

	public void setupTestMethodHeader(String packageName, String mainActivity) {
		// Makes the header if we don't have an app name.
//		UiAutomatorTranslator.toWrite.add(0, "package " + packageName + ".test\n\n");
		UiAutomatorTranslator.toWrite.add("public class TestFile extends UiAutomatorTestCase {\n");
		UiAutomatorTranslator.toWrite.add("\tpublic void test_uiautomator() throws UiObjectNotFoundException{\n");
	}

	public void closeTestMethod() {
		// TODO Auto-generated method stub
		UiAutomatorTranslator.toWrite.add("\t}\n}");
	}

}
