package edu.wm.translationengine.appium;

import java.io.IOException;

import edu.wm.translationengine.trans.FileModifier;

public class AppiumFileModifier extends FileModifier{
	
	public AppiumFileModifier(){}
	
	public void setupFileImports() throws IOException {
		AppiumTranslator.toWrite.add("import edu.wm.translationengine.appium.*;\n"
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

	public void setupTestMethodHeader(String packageName, String mainActivity) {
		// TODO Auto-generated method stub
		//stuff before the test stuff
		AppiumTranslator.toWrite.add("public class TestFile{\n");
		
		AppiumTranslator.toWrite.add("\tpublic static String DEFAULT_SERVER = \"http://localhost\";\n"
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
		AppiumTranslator.toWrite.add("\t\t@Before\n");
		AppiumTranslator.toWrite.add("\t\tpublic void setUp() throws Exception {\n");
		AppiumTranslator.toWrite.add("\t\t\tserverWorker = new AppiumServerWorker(DEFAULT_APPIUM_DIR);\n"
				+ "\t\t\tserverWorker.startServer();\n"
				+ "\t\t\tDesiredCapabilities capability = new DesiredCapabilities();\n"
				+ "\t\t\tcapability.setCapability(\"deviceName\", DEVICE_NAME);\n"
				+ "\t\t\tcapability.setCapability(\"platformName\", PLATFORM_NAME);\n"
				+ "\t\t\tcapability.setCapability(\"platformVersion\", PLATFORM_VERSION);\n"
				+ "\t\t\tFile file = new File(GMDICE_DIR);\n"
				+ "\t\t\tcapability.setCapability(\"app\", file.getAbsolutePath());\n"
				+ "\t\t\tURL serverURL = new URL(DEFAULT_SERVER + \":\" + DEFAULT_PORT + DEFAULT_SUFFIX);\n"
				+ "\t\t\tdriver = new AndroidDriver<MobileElement>(serverURL, capability);\n");
		AppiumTranslator.toWrite.add("\t\t}\n");
		
		//@After
		AppiumTranslator.toWrite.add("\t\t@After\n");
		AppiumTranslator.toWrite.add("\t\tpublic void tearDown() throws Exception {\n");
		AppiumTranslator.toWrite.add("\t\t\tdriver.quit();\n"
				+ "\t\t\tserverWorker.stopServer();\n");
		AppiumTranslator.toWrite.add("\t\t}\n");
		
		//@Test 
		AppiumTranslator.toWrite.add("\t\t@Test\n");
		AppiumTranslator.toWrite.add("\t\tpublic void testAppium() throws InterruptedException{\n");
		
		
	}
	
	public void closeTestMethod() {
		AppiumTranslator.toWrite.add("\t\t}\n}");
	}
	

}
