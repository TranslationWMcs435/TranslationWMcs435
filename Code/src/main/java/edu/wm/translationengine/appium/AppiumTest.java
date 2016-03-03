package edu.wm.translationengine.appium;

import edu.wm.translationengine.classes.*;

import java.io.*;
import java.net.URL;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.appium.java_client.*;
import io.appium.java_client.android.*;

public class AppiumTest {
	
	public static String GMDICE_DIR = System.getProperty("user.dir") + "\\res\\apk\\gmdice.apk";
	//public static String GMDICE_DIR = "gmdice.apk";
	
	private LiveModeServerWorker serverWorker;
	private RemoteWebDriver driver;
	
	public void start() throws IOException, AppiumException, InterruptedException {
		
		//used to handle user input into the console
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String directory = null;
		
		// Ask user to specify their Appium Directory if not the same as default --> (C:\\Program Files (x86)\\Appium)
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
		
		//TODO: Ask user about their google emulator/device options --This seems like it can be ignored since my Physical device is working with mismatched specs right now
		//TODO: prompt user to select path for their apk of choice --further down the road. Stick with gmdice for now
		
		// Start Appium server
		serverWorker = new LiveModeServerWorker(directory, GMDICE_DIR);
		serverWorker.startServer();
		
		driver = new AndroidDriver<WebElement>(new URL(serverWorker.getServerURL()), serverWorker.getCapabilities());
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