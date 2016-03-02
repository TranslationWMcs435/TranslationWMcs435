package edu.wm.translationengine.appium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.wm.translationengine.classes.*;
import edu.wm.translationengine.main.*;
import edu.wm.translationengine.trans.*;

public class AppiumTranslator extends GenericTranslator{
	private AppiumFunctions af;
	public AppiumTranslator() throws IOException {
		super();
		af = new AppiumFunctions();
	}
	
	/* setup for the java file being created*/
	public void setupFile(){
		toWrite.add("import edu.wm.translationengine.appium;\n"
				+ "import io.appium.java_client.AppiumDriver;\n"
				+ "import io.appium.java_client.MobileElement;\n"
				+ "import io.appium.java_client.android.AndroidDriver;\n"
				+ "import io.appium.java_client.android.AndroidElement;\n"
				+ "import java.io.File;\n"
				+ "import java.net.URL;\n"
				+ "import java.util.List;\n"
				+ "import org.junit.runner.RunWith;\n"
				+ "import junit.framework.TestSuite;\n"
				+ "import org.junit.After;\n"
				+ "import org.junit.Before;\n"
				+ "import org.junit.Test;\n"
				+ "import org.openqa.selenium.By;\n"
				+ "import org.openqa.selenium.remote.DesiredCapabilities;\n");		
	}
	public void writeToFile(ArrayList<String> al) throws IOException{
		// TODO copy shit
		for(int i = 0; i < al.size(); i++){
			bw.write(al.get(i));
		}
	}
	public void writeToFile() throws IOException {
		// TODO copy shit
		ArrayList<String> al = toWrite;
		for(int i = 0; i < al.size(); i++){
			bw.write(al.get(i));
		}
	}
	
	/* write to the file generating a test case suitable for use with the appium server*/
	public void steps_iterator(TestCase testCase) throws IOException{
		List <StepTestCase> stepTestCases = testCase.getSteps();
		
		
		//stuff before the test stuff
		toWrite.add("public class TestFile{\n");
		
		toWrite.add("\tpublic static String DEFAULT_SERVER = \"http://localhost\";\n"
				+ "\tpublic static String DEFAULT_PORT = \"4723\";\n"
				+ "\tpublic static String DEFAULT_SUFFIX = \"/wd/hub\";\n"
				+ "\tprivate static String DEFAULT_APPIUM_DIR = \"C:\\\\Program Files (x86)\\\\Appium\";\n" //TODO: fine for now, but may need to fix the way this is written to ask user for Appium Directory in future??
				+ "\tpublic static String DEVICE_NAME = \"Google Nexus 4 - 5.1.0 - API 22 - 768x1280\";\n"
				+ "\tpublic static String PLATFORM_NAME = \"Android\";\n"
				+ "\tpublic static String PLATFORM_VERSION = \"5.1\";\n"
				+ "\tpublic static String GMDICE_DIR = System.getProperty(\"user.dir\") + \"\\\\res\\\\apk\\\\gmdice.apk\";\n"
				+ "\tprivate AppiumDriver<MobileElement> driver;\n"
				+ "\tprivate AppiumServerWorker serverWorker;\n" );
		
		//@Before
		toWrite.add("\t\t@Before\n");
		toWrite.add("\t\tpublic void setUp() throws Exception {\n");
		toWrite.add("\t\t\tserverWorker = new AppiumServerWorker(DEFAULT_APPIUM_DIR);\n"
				+ "\t\t\tserverWorker.startServer();\n"
				+ "\t\t\tDesiredCapabilities capability = new DesiredCapabilities();\n"
				+ "\t\t\tcapability.setCapability(\"deviceName\", DEVICE_NAME);\n"
				+ "\t\t\tcapability.setCapability(\"platformName\", PLATFORM_NAME);\n"
				+ "\t\t\tcapability.setCapability(\"platformVersion\", PLATFORM_VERSION);\n"
				+ "\t\t\tFile file = new File(GMDICE_DIR);\n"
				+ "\t\t\tcapability.setCapability(\"app\", file.getAbsolutePath());\n"
				+ "\t\t\tURL serverURL = new URL(DEFAULT_SERVER + \":\" + DEFAULT_PORT + DEFAULT_SUFFIX);\n"
				+ "\t\t\tdriver = new AndroidDriver<MobileElement>(serverURL, capability);\n");
		toWrite.add("\t\t}\n");
		
		//@After
		toWrite.add("\t\t@After\n");
		toWrite.add("\t\tpublic void tearDown() throws Exception {\n");
		toWrite.add("\t\t\tdriver.quit();\n"
				+ "\t\t\tserverWorker.stopServer();\n");
		toWrite.add("\t\t}\n");
		
		//@Test 
		toWrite.add("\t\t@Test\n");
		toWrite.add("\t\tpublic void testAppium() throws InterruptedException{\n");
		//loop through the parsed input to generate the good stuff
		for(int i=0; i < stepTestCases.size(); i++){
			StepTestCase cur = stepTestCases.get(i);
			af.appium_switcher(cur.getAction(), cur);
		}
		toWrite.add("\t\t}\n}");	
	}
	
}
