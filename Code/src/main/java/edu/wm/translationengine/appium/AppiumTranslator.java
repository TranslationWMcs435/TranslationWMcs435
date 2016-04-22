package edu.wm.translationengine.appium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.wm.translationengine.classes.*;
import edu.wm.translationengine.main.*;
import edu.wm.translationengine.trans.*;

public class AppiumTranslator extends GenericTranslator{
	private AppiumFunctions af;
	public static String appName;
	public static String packageName; 
	public static String mainActivity;
	public AppiumTranslator() throws IOException {
		super();
		fm = new AppiumFileModifier();
		af = new AppiumFunctions();
	}
	
//	public void writeToFile() throws IOException {
//		// TODO copy shit
//		ArrayList<String> al = toWrite;
//		for(int i = 0; i < al.size(); i++){
//			bw.write(al.get(i));
//		}
//	}
	
	
	/* write to the file generating a test case suitable for use with the appium server*/
	public void steps_iterator(TestCase testCase) throws IOException{
		appName = testCase.getAppName();
		packageName = testCase.getPackageName();
		mainActivity = testCase.getMainActivity().substring(packageName.length() + 1);
		String directory=null;
		//ask user for appium directory on their system to make file more dynamic
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("DEFAULT APPIUM DIRECTORY : C:\\Program Files (x86)\\Appium\n"
				+ "Use default directory? ");
		String input = in.readLine();
		if(input.matches("[y|Y].*")){
			System.out.println("Using DEFAULT APPIUM DIRECTORY...");
			directory = LiveModeServerWorker.getOSAppiumDir();
		}else if(input.matches("[n|N].*")){
			System.out.println("Okay. Enter the absolute path to the Appium directory on your system:");
			directory = in.readLine();
		}else{
			System.out.println("Invalid Choice. Goodbye.");
			System.exit(-1);
		}
		//-----------------------------------
		List <StepTestCase> stepTestCases = testCase.getSteps();

		//loop through the parsed input to generate the good stuff
		//need to pass appName to setupTestMethodHeader. Dunno what to do with second arg.
		fm.setupFileImports();
		fm.setupTestMethodHeader(directory, appName);
		for(int i=0; i < stepTestCases.size(); i++){
			StepTestCase cur = stepTestCases.get(i);
			af.appium_switcher(cur.getAction(), cur);
		}
		fm.closeTestMethod();

	}
	
}
