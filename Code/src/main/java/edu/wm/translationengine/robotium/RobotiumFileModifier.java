package edu.wm.translationengine.robotium;

import java.io.IOException;
import java.util.List;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.trans.FileModifierInterface;

/**
 *
 * RobotiumFileModifier specifies the content
 * of a test case file.
 *
 */
public class RobotiumFileModifier implements FileModifierInterface {
	
	private String packageName;
	private String mainActivityName;
	private List<StepTestCase> stepTestCases;
	private RobotiumFunctions functions;
	
	public RobotiumFileModifier(String mainPackageName, String mainActivityName, List<StepTestCase> testCase) {
		this.packageName = mainPackageName;
		this.mainActivityName = mainActivityName;
		this.stepTestCases = testCase;
		functions = new RobotiumFunctions();
	}
	
	@Override
	public void setupFileImports() throws IOException {
		
		// package declaration
		RobotiumTranslator.toWrite.add("package " + packageName + ".test;\n");
		RobotiumTranslator.toWrite.add("\n");
		
		// imports
		RobotiumTranslator.toWrite.add("import android.util.Log;\n" + 
					"import junit.framework.AssertionFailedError;\n" +
					"import com.robotium.solo.Solo;\n\n" +
					"import android.annotation.TargetApi;\n" + 
					"import android.os.Build;\n" + 
					"import android.view.View;\n\n");
		RobotiumTranslator.toWrite.add("import " + mainActivityName + ";\n");
		RobotiumTranslator.toWrite.add("\n");
	}

	public void setupTestMethodHeader() {
		
		setupTestMethodHeader(packageName, mainActivityName);
	}

	@Override
	public void setupTestMethodHeader(String a, String b) {
				
		// Class name
		RobotiumTranslator.toWrite.add("public class TestBasic extends " + 
				"ActivityInstrumentationTestCase2<" + mainActivityName + "> {\n");
		RobotiumTranslator.toWrite.add("\n");
		
		// Instance variables
		RobotiumTranslator.toWrite.add("\tprivate Solo solo;\n");
		RobotiumTranslator.toWrite.add("\n");
		
		// Constructor
		RobotiumTranslator.toWrite.add("\t@TargetApi(Build.VERSION_CODES.FROYO)\n");
		RobotiumTranslator.toWrite.add("\tpublic TestBasic() {\n" + 
				"\t\tsuper(GameMasterDice.class);\n"
				+ "\t}\n"
				);
		RobotiumTranslator.toWrite.add("\n");
		
		// setUp()
		RobotiumTranslator.toWrite.add( "\t@Override\n" + 
				"\tpublic void setUp() throws Exception {\n" + 
					"\t\tsolo = new Solo(getInstrumentation(), getActivity());\n" + 
				"\t}\n");
		RobotiumTranslator.toWrite.add("\n");
		
		// @Test
		RobotiumTranslator.toWrite.add("\tpublic void testActions() {\n");
		RobotiumTranslator.toWrite.add("\t\tView curView = null;\n");
		
		for(int i = 0; i < stepTestCases.size(); i++){
						
			StepTestCase cur = stepTestCases.get(i);
			
			try {
				functions.writeStep(cur);
			} catch (Exception e) {
				e.printStackTrace();
			}	

		}
		RobotiumTranslator.toWrite.add("\t}\n\n");
	}

	@Override
	public void closeTestMethod() {
		
		// tearDown()
		RobotiumTranslator.toWrite.add("\t@Override\n" + 
		"\tpublic void tearDown() throws Exception {\n" + 
			"\t\tsolo.finishOpenedActivities();\n" + 
		"\t}\n");
		RobotiumTranslator.toWrite.add("\n");
		RobotiumTranslator.toWrite.add("}");
	}

}
