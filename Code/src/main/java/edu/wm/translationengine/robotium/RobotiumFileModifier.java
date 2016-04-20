package edu.wm.translationengine.robotium;

import java.io.IOException;
import java.util.List;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.trans.FileModifier;

/**
 *
 * RobotiumFileModifier specifies the content
 * of a test case file.
 *
 */
public class RobotiumFileModifier extends FileModifier {
	
	private String packageName;
	private String mainActivityName;
	
	public RobotiumFileModifier(String mainPackageName, String mainActivityName, List<StepTestCase> testCase) {
		this.packageName = mainPackageName;
		this.mainActivityName = mainActivityName;
	}
	
	public void setupFileImports() throws IOException {
		
		// package declaration
		RobotiumTranslator.toWrite.add("package " + packageName + ".test;\n");
		RobotiumTranslator.toWrite.add("\n");
		
		// imports
		RobotiumTranslator.toWrite.add("import android.util.Log;\n" + 
					"import junit.framework.AssertionFailedError;\n" +
					"import com.robotium.solo.Solo;\n\n" +
					"import android.annotation.TargetApi;\n" +
					"import android.test.ActivityInstrumentationTestCase2;\n" +
					"import android.os.Build;\n" + 
					"import android.view.View;\n" +
					"import android.view.Display;\n" + 
					"import android.graphics.Point;\n" + 
					"import android.widget.EditText;\n\n");
		RobotiumTranslator.toWrite.add("\n");
	}

	public void setupTestMethodHeader() {
		
		setupTestMethodHeader(packageName, mainActivityName);
	}

	public void setupTestMethodHeader(String a, String b) {
				
		// Class name
		RobotiumTranslator.toWrite.add("public class TestFile extends " + 
				"ActivityInstrumentationTestCase2 {\n");
		RobotiumTranslator.toWrite.add("\n");
		
		// Instance variables
		RobotiumTranslator.toWrite.add("\tprivate Solo solo;\n");
		RobotiumTranslator.toWrite.add("\n");
		
		RobotiumTranslator.toWrite.add(
			"\tprivate static final String TARGET_PACKAGE_ID = \"" + packageName + "\";\r\n" + 
			"\tprivate static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = \"" + mainActivityName + "\";\n" +
			"\tprivate static Class<?> launcherActivityClass;\n"
		);
		
		RobotiumTranslator.toWrite.add(
			"	static {\r\n" + 
			"		try{\r\n" + 
			"			launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);\r\n" + 
			"		}\r\n" + 
			"		catch(ClassNotFoundException e) {\r\n" + 
			"			throw new RuntimeException(e);\r\n" + 
			"		}\r\n" + 
			"	}\n"
		);
		
		// Constructor
		RobotiumTranslator.toWrite.add(
				"    @SuppressWarnings({ \"unchecked\", \"deprecation\" })\r\n" + 
				"    public TestFile() throws ClassNotFoundException {\r\n" + 
				"        super(TARGET_PACKAGE_ID, launcherActivityClass);\r\n" + 
				"    }"
		);
		
		// setUp()
		RobotiumTranslator.toWrite.add( "\t@Override\n" + 
				"\tpublic void setUp() throws Exception {\n" + 
					"\t\tsolo = new Solo(getInstrumentation(), getActivity());\n" + 
				"\t}\n");
		RobotiumTranslator.toWrite.add("\n");
		
		// @Test
		RobotiumTranslator.toWrite.add("\tpublic void testActions() {\n");
		RobotiumTranslator.toWrite.add("\t\tView curView = null;\n");
	}

	@Override
	public void closeTestMethod() {
		
		// tearDown()
		RobotiumTranslator.toWrite.add("\t@Override\n" + 
		"\tpublic void tearDown() throws Exception {\n" + 
			"\t\tsolo.finishOpenedActivities();\n" + 
		"\t}\n");
		RobotiumTranslator.toWrite.add("\n\n");
	}
	
	public void closeClass(List<String> extras) {
		
		for(String method: extras) {
			RobotiumTranslator.toWrite.add(method + "\n");
		}
		
		RobotiumTranslator.toWrite.add("}");
	}

}
