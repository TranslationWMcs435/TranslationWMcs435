package appium;
import java.io.*;
import classes.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URL;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
public class AppiumTest {
	
	// Default server is localhost (127.0.0.1) on port 4723
	public static String DEFAULT_SERVER = "http://localhost";
	public static String DEFAULT_PORT = "4723";
	public static String DEFAULT_SUFFIX = "/wd/hub";
	private static String DEFAULT_APPIUM_DIR = "C:\\Program Files (x86)\\Appium";
	
	/* 
	 * Android emulator's attributes.
	 * Needed to connect with Appium.
	 * 
	 * Obtain device info by going into "Settings" --> "About phone" on your device/emulator
	 *
	 * (Replace with your own emulator/device info...)
	*/
	public static String DEVICE_NAME = "Google Nexus 4 - 5.1.0 - API 22 - 768x1280";
	public static String PLATFORM_NAME = "Android";
	public static String PLATFORM_VERSION = "5.1";
	
	public static String GMDICE_DIR = System.getProperty("user.dir") + "\\res\\apk\\gmdice.apk";
	//public static String GMDICE_DIR = "gmdice.apk";
	
	public static void main( String [] args ) throws IOException, AppiumException, InterruptedException {
		
		//used to handle user input into the console
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String directory = null;
		
		// Ask user to specify their Appium Directory if not the same as default --> (C:\\Program Files (x86)\\Appium)
		System.out.println("DEFAULT APPIUM DIRECTORY : C:\\Program Files (x86)\\Appium\n"
				+ "Use default directory? ");
		String input = in.readLine();
		
		if(input.matches("[y|Y].*")){
			System.out.println("Using DEFAULT APPIUM DIRECTORY...");
			directory = DEFAULT_APPIUM_DIR;
		}else if(input.matches("[n|N].*")){
			System.out.println("Okay. Enter the absolute path to the Appium directory on your system:");
			directory = in.readLine();
		}else{
			System.out.println("Invalid Choice. Goodbye.");
			System.exit(-1);
		}

		//TODO: Ask user about their google emulator/device options --This seems like it can be ignored since my Physical device is working with mismatched specs right now
		//TODO: prompt user to select path for their apk of choice --further down the road. Stick with gmdice for now
		
		// Start Appium server
		AppiumServerWorker serverWorker = new AppiumServerWorker(directory);
		serverWorker.startServer();
		
		// Minimum # of capabilities to run
		// Remember 'no reset' because you don't want it to install apk every time!!
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("deviceName", DEVICE_NAME);
		capability.setCapability("platformName", PLATFORM_NAME);
		capability.setCapability("platformVersion", PLATFORM_VERSION);
		
		// Specify apk file to test
		File file = new File(GMDICE_DIR);
		capability.setCapability("app", file.getAbsolutePath());
		
		URL serverURL = new URL(DEFAULT_SERVER + ":" + DEFAULT_PORT + DEFAULT_SUFFIX);
		
		// Connect capability to server
		// AppiumDriver is the main class that allows us to interact with Appium, 
		// which then interacts with the mobile device
		AndroidDriver driver = new AndroidDriver(serverURL, capability);
		// Check that driver was able to connect to emulator/device
		//serverWorker.getServerResponse(DEFAULT_SERVER + ":" + DEFAULT_PORT + DEFAULT_SUFFIX);
		
		performTest(driver);
		
		// Quit
		driver.quit();
		serverWorker.stopServer();
		System.exit(0);
	}
	
	/*borrowed the parse method...
	 *TODO: Update to prompt user for JSON file location instead of using the given file
	 *TODO: Clean up code to avoid copy/pasting. Pull a basic version from Main.java and then change parts accordingly here
	**/
	public static TestCase parse() throws IOException {
	    	TestCase list_of_actions = null;
	    	//If there's a need to get rid of hard coding, this is where to do it.
	    	InputStream stream = new FileInputStream(System.getProperty("user.dir") + "\\gmdice_simple.txt");
	        System.out.println(stream);
	        Reader reader = new InputStreamReader(stream, "UTF-8");
	        try{ 
	            Gson gson = new GsonBuilder().create();
	            list_of_actions = gson.fromJson(reader,TestCase.class);
	            System.out.println("Parsing... Succeeds?");
	            return list_of_actions;
	        } catch (Exception e){
	        	System.out.println("Parsing failed. Returning null.");
	            return list_of_actions;
	        }
	}
	
    
	public static void performTest(RemoteWebDriver driver) throws IOException, InterruptedException {
		
		
		/* Obtain tap IDs from parse method and JSON file*/
		TestCase appiumTest = parse();
		int i;
	        for(i=0; i<appiumTest.getSteps().size() ; i++)
	        {
	        	String buttonName = appiumTest.getSteps().get(i).getComponent().getText();
	        	if(appiumTest.getSteps().get(i).getAction().contentEquals("CLICK")){
	        		driver.findElement(By.name(buttonName)).click();
	        		Thread.sleep(500);
	        		System.out.println("clicked button at " + buttonName);
	        	}else if(appiumTest.getSteps().get(i).getAction().contentEquals("LONG_CLICK")){
	        		MobileElement button = (MobileElement)driver.findElement(By.name(buttonName));
	        		button.tap(1, 1000);
				Thread.sleep(500);
				System.out.println("long clicked button at" + buttonName);
	        		
	        	}
	        }
	}
	
	
}
