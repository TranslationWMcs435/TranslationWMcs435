package edu.wm.translationengine.appium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.apache.commons.collections.functors.IfClosure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/*starts an appium server and runs through a JSON parse, executing commands in 
 * real time when start() is called.
 */

public class AppiumLive {
	public static String appName;
	public static String APP_DIR;	
	private LiveModeServerWorker serverWorker;
	private RemoteWebDriver driver;
	
	public void start(TestCase testCase) throws IOException, AppiumException, InterruptedException {
		//get app name and set directory
		if(testCase.getAppName() == null)
			System.out.println("\nERROR: App Name is null\n");//of all things, this better be given, and it better match up with a name in the resources directory
		else
			appName = testCase.getAppName();
		APP_DIR = System.getProperty("user.dir") + "\\res\\apk\\"+appName+".apk";
		
		//used to handle user input into the console
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String directory = null;
		System.out.println("Using apk located at:  "+ APP_DIR+"\n");
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
		serverWorker = new LiveModeServerWorker(directory, APP_DIR);
		serverWorker.startServer();
		
		driver = new AndroidDriver<WebElement>(new URL(serverWorker.getServerURL()), serverWorker.getCapabilities());
		performLiveTest(driver, testCase);
		
		// Quit
		driver.quit();
		serverWorker.stopServer();
		System.exit(0);
	}
	
    
	public static void performLiveTest(RemoteWebDriver d, TestCase testCase) throws IOException, InterruptedException {
		
		AppiumDriver<?> driver = (AppiumDriver<?>) d;
		List <StepTestCase> stepTestCases = testCase.getSteps();
		int i;
	        for(i=0; i<stepTestCases.size() ; i++)
	        {
	        	Thread.sleep(750);
	        	StepTestCase cur = stepTestCases.get(i);
	        	String buttonID=null; String xpath;
	        	int x; int y;
	        	MobileElement button=null;
	        	//for some reason x and y buttons arent doing much in the way of being helpful
	        	x = Integer.parseInt(cur.getComponent().getPositionX());
	        	y = Integer.parseInt(cur.getComponent().getPositionY());
	        	String type = cur.getComponent().getType();
	        	String text = cur.getComponent().getText();
	        	String index = cur.getComponent().getIndex();
	        	buttonID = cur.getComponent().getId();
	        	
	        	if(type.equals("android.widget.CheckedTextView"))//check text view may rotate the index around, so xpath will not include index for this type of element
	        		xpath ="//*[@class='"+type+ "' and @text='"+text+"' and @resource-id ='"+buttonID+"']";
	        	else
	        		xpath ="//*[@class='"+type+ "' and @text='"+text+"' and @resource-id ='"+buttonID+"' and @index='"+ index+ "']";
	        	
	        	System.out.println("xpath search thingy configured as : " + xpath);
	        	
	         	
	         	if(buttonID.contains("android:id/")){
	         		//android ids are pretty fucking generic so here we create a list of possible buttons that the JSON could be referring to given the created xpath
	        		@SuppressWarnings("unchecked")
					List<MobileElement> a = (List<MobileElement>) driver.findElements(By.xpath(xpath));
		        	int j;
		           	for(j=0; j <a.size(); j++){
		        		System.out.println("pulled a button");
		        		button = a.get(j);	   
		        	}
		        	
	        	}else{
		        	//check for special button ids which would not be identifiable on screen
		        	if((buttonID!= null) && (buttonID.equals("id/back"))){
		        		driver.navigate().back();
		        		continue;
		        	}
		        	//in any case were we dont have special IDs or android IDs, we can find by ID and be good
		        	button = (MobileElement)driver.findElement(By.id(buttonID));
	        	}
	        	
	        	
	        	//execute a command based on the JSON parse. Prefferably using IDs, but will use x and y values if necessary
	        	if(cur.getAction().contentEquals("CLICK")){
	        		button.tap(1, 50);
	        		System.out.println("clicked button");
	        	}else if(cur.getAction().contentEquals("LONG_CLICK")){
	       			button.tap(1, 1000);
	        		System.out.println("long clicked button");
	        	}else if(cur.getAction().contentEquals("TYPE")){
	        		button.clear();
	        		button.sendKeys(cur.getComponent().getText());
	        		System.out.println("Typed text at " + buttonID);
	        	}else if(cur.getAction().contentEquals("SWIPE-DOWN-LEFT")){
	        		//cod which follows below is used for swipes. Swipes arent working due to x and y coordinates in JSON file.
	    
	        		int startx = Integer.parseInt(cur.getComponent().getPositionX());
	        		int starty = Integer.parseInt(cur.getComponent().getPositionY());
	        		//for now end points will be a 100px or -100px difference between the starts and ends on x and y. Automatically set to 0 if the addition sends the position out of boudns 
	        		int endx = startx-100;
	        		if(endx < 0) endx = 0;
	        		int endy = starty-100;
	        		if(endy < 0) endy = 0;
	        		driver.swipe(startx, starty, endx, endy, 500);
	        		System.out.println("Swiped performed");
	        	}else if(cur.getAction().contentEquals("SWIPE-DOWN-RIGHT")){
	        		int startx = Integer.parseInt(cur.getComponent().getPositionX());
	        		int starty = Integer.parseInt(cur.getComponent().getPositionY());
	        		//for now end points will be a 100px or -100px difference between the starts and ends on x and y. Automatically set to 0 if the addition sends the position out of boudns 
	        		int endx = startx+100;
	        		if(endx < 0) endx = 0;
	        		int endy = starty-100;
	        		if(endy < 0) endy = 0;
	        		driver.swipe(startx, starty, endx, endy, 500);
	        		System.out.println("Swiped performed");
	        	}else if(cur.getAction().contentEquals("SWIPE-UP-RIGHT")){
	        		int startx = Integer.parseInt(cur.getComponent().getPositionX());
	        		int starty = Integer.parseInt(cur.getComponent().getPositionY());
	        		//for now end points will be a 100px or -100px difference between the starts and ends on x and y. Automatically set to 0 if the addition sends the position out of boudns 
	        		int endx = startx+100;
	        		if(endx < 0) endx = 0;
	        		int endy = starty+100;
	        		if(endy < 0) endy = 0;
	        		driver.swipe(startx, starty, endx, endy, 500);
	        		System.out.println("Swiped performed");
	        	}else if(cur.getAction().contentEquals("SWIPE-UP-LEFT")){
	        		int startx = Integer.parseInt(cur.getComponent().getPositionX());
	        		int starty = Integer.parseInt(cur.getComponent().getPositionY());
	        		//for now end points will be a 100px or -100px difference between the starts and ends on x and y. Automatically set to 0 if the addition sends the position out of boudns 
	        		int endx = startx-100;
	        		if(endx < 0) endx = 0;
	        		int endy = starty+100;
	        		if(endy < 0) endy = 0;
	        		driver.swipe(startx, starty, endx, endy, 500);
	        		System.out.println("Swiped performed");
	        	}else if(cur.getAction().contentEquals("SWIPE-RIGHT")){
	        		int startx = Integer.parseInt(cur.getComponent().getPositionX());
	        		int starty = Integer.parseInt(cur.getComponent().getPositionY());
	        		//for now end points will be a 100px or -100px difference between the starts and ends on x and y. Automatically set to 0 if the addition sends the position out of boudns 
	        		int endx = startx+100;
	        		if(endx < 0) endx = 0;
	        		int endy = starty;
	        		if(endy < 0) endy = 0;
	        		driver.swipe(startx, starty, endx, endy, 500);
	        		System.out.println("Swiped performed");
	        	}else if(cur.getAction().contentEquals("SWIPE-LEFT")){
	        		int startx = Integer.parseInt(cur.getComponent().getPositionX());
	        		int starty = Integer.parseInt(cur.getComponent().getPositionY());
	        		//for now end points will be a 100px or -100px difference between the starts and ends on x and y. Automatically set to 0 if the addition sends the position out of boudns 
	        		int endx = startx-100;
	        		if(endx < 0) endx = 0;
	        		int endy = starty;
	        		if(endy < 0) endy = 0;
	        		driver.swipe(startx, starty, endx, endy, 500);
	        		System.out.println("Swiped performed");
	        	}else if(cur.getAction().contentEquals("SWIPE-DOWN")){
	        		int startx = Integer.parseInt(cur.getComponent().getPositionX());
	        		int starty = Integer.parseInt(cur.getComponent().getPositionY());
	        		//for now end points will be a 100px or -100px difference between the starts and ends on x and y. Automatically set to 0 if the addition sends the position out of boudns 
	        		int endx = startx;
	        		if(endx < 0) endx = 0;
	        		int endy = starty-100;
	        		if(endy < 0) endy = 0;
	        		driver.swipe(startx, starty, endx, endy, 500);
	        		System.out.println("Swiped performed");
	        	}else if(cur.getAction().contentEquals("SWIPE-UP")){
	        		int startx = Integer.parseInt(cur.getComponent().getPositionX());
	        		int starty = Integer.parseInt(cur.getComponent().getPositionY());
	        		//for now end points will be a 100px or -100px difference between the starts and ends on x and y. Automatically set to 0 if the addition sends the position out of boudns 
	        		int endx = startx;
	        		if(endx < 0) endx = 0;
	        		int endy = starty+100;
	        		if(endy < 0) endy = 0;
	        		driver.swipe(startx, starty, endx, endy, 500);
	        		System.out.println("Swiped performed");
	        	}
	        	
	        }
	}
	

}
