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
	
	/** The package name. */
	private String packageName;
	
	/** The main activity name. */
	private String mainActivityName;
	
	/**
	 * Instantiates a new robotium file modifier.
	 *
	 * @param mainPackageName the main package name
	 * @param mainActivityName the main activity name
	 * @param testCase the test case
	 */
	public RobotiumFileModifier(String mainPackageName, String mainActivityName, List<StepTestCase> testCase) {
		this.packageName = mainPackageName;
		this.mainActivityName = mainActivityName;
	}
	
	/**
	 * Write file import statements on top of the output file.
	 * 
	 * @throws IOException if the program has trouble reading the data
	 */
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

	/**
	 * Write test method.
	 */
	public void setupTestMethodHeader() {
		
		setupTestMethodHeader(packageName, mainActivityName);
	}

	/**
	 * Write test method, which contains the translated UI commands.
	 * 
	 * @param a the first string
	 * @param b the second input string
	 */
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

	/**
	 * Write the close down method for the JUnit test case.
	 */
	@Override
	public void closeTestMethod() {
		
		// tearDown()
		RobotiumTranslator.toWrite.add("\t@Override\n" + 
		"\tpublic void tearDown() throws Exception {\n" + 
			"\t\tsolo.finishOpenedActivities();\n" + 
		"\t}\n");
		RobotiumTranslator.toWrite.add("\n\n");
	}
	
	/**
	 * Close the output file by writing additional helper methods
	 * and also closing the class with a bracket.
	 *
	 * @param extras a list of helper methods to additionally write to the file
	 */
	public void closeClass(List<String> extras) {
		
		for(String method: extras) {
			RobotiumTranslator.toWrite.add(method + "\n");
		}
		
		RobotiumTranslator.toWrite.add("}");
	}
}