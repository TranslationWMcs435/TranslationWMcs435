package edu.wm.translationengine.uiautomator;

import java.io.IOException;

import edu.wm.translationengine.trans.FileModifier;

public class UiAutomatorFileModifier extends FileModifier{

	public void setupFileImports() throws IOException {
		// TODO Auto-generated method stub
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.core.UiDevice;\n");
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.core.UiObject;\n");
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.core.UiObjectNotFoundException;\n");
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.core.UiSelector;\n");
		UiAutomatorTranslator.toWrite.add("import com.android.uiautomator.testrunner.UiAutomatorTestCase;\n\n");
	}

	public void setupTestMethodHeader(String packageName, String mainActivity) {
		// TODO Auto-generated method stub
		UiAutomatorTranslator.toWrite.add("public class TestBackPress extends UiAutomatorTestCase {\n");
		UiAutomatorTranslator.toWrite.add("\tpublic void uiautomator_test() throws UiObjectNotFoundException{\n");
	}

	public void closeTestMethod() {
		// TODO Auto-generated method stub
		UiAutomatorTranslator.toWrite.add("\t}\n");
		UiAutomatorTranslator.toWrite.add(0, "package [Test_package_name_here]\n\n");
	}

}
