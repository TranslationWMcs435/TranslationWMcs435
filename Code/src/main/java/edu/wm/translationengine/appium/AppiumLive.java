package edu.wm.translationengine.appium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/*starts an appium server and runs through a JSON parse, executing commands in 
 * real time when start() is called.
 */

public class AppiumLive {
	
	public static String GMDICE_DIR = System.getProperty("user.dir") + "\\res\\apk\\gmdice.apk";	
	private LiveModeServerWorker serverWorker;
	private RemoteWebDriver driver;
	
	public void start(TestCase testCase) throws IOException, AppiumException, InterruptedException {
		
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
		
		// Start Appium server
		serverWorker = new LiveModeServerWorker(directory, GMDICE_DIR);
		serverWorker.startServer();
		
		driver = new AndroidDriver<WebElement>(new URL(serverWorker.getServerURL()), serverWorker.getCapabilities());
		performLiveTest(driver, testCase);
		
		// Quit
		driver.quit();
		serverWorker.stopServer();
		System.exit(0);
	}
	
    
	public static void performLiveTest(RemoteWebDriver driver, TestCase testCase) throws IOException, InterruptedException {
		
		List <StepTestCase> stepTestCases = testCase.getSteps();
		int i;
	        for(i=0; i<stepTestCases.size() ; i++)
	        {
	        	StepTestCase cur = stepTestCases.get(i);
	        	String buttonName = cur.getComponent().getText();
	        	if(cur.getAction().contentEquals("CLICK")){
	        		driver.findElement(By.name(buttonName)).click();
	        		Thread.sleep(500);
	        		System.out.println("clicked button at " + buttonName);
	        	}else if(cur.getAction().contentEquals("LONG_CLICK")){
	        		MobileElement button = (MobileElement)driver.findElement(By.name(buttonName));
	        		button.tap(1, 1000);
				Thread.sleep(500);
				System.out.println("long clicked button at" + buttonName);
	        	}
	        	//need an example JSON file with typing so I can properly implement typing actions
	        }
	}
	

}
