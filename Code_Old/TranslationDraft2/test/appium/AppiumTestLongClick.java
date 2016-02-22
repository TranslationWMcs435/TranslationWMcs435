package appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppiumTestLongClick {

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
	*/
	public static String DEVICE_NAME = "Google Nexus 4 - 5.1.0 - API 22 - 768x1280";
	public static String PLATFORM_NAME = "Android";
	public static String PLATFORM_VERSION = "5.1";
	
	public static String GMDICE_DIR = System.getProperty("user.dir") + "\\res\\apk\\gmdice.apk";
	
	private AppiumDriver<AndroidElement> driver;
	private AppiumServerWorker serverWorker;
	
	@Before
	public void setUp() throws Exception {
		
		// Start Appium server
		serverWorker = new AppiumServerWorker(DEFAULT_APPIUM_DIR);
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
		driver = new AndroidDriver<AndroidElement>(serverURL, capability);
	}

	@After
	public void tearDown() throws Exception {
		
		driver.quit();
		serverWorker.stopServer();
	}
	
	@Test
	public void testTap() {
		
		/* Element IDs obtained from uiautomatorviewer */
		
		// Long tap first button
		System.out.println("Long tapping id/die0");
		MobileElement button0 = (MobileElement)driver.findElement(By.id("de.duenndns.gmdice:id/die0"));
		button0.tap(1, 1000);
		
		System.out.println("Pressing cancel...");
		MobileElement cancel1 = (MobileElement)driver.findElement(By.id("android:id/button2"));
		cancel1.click();
		
		System.out.println("Long tapping id/die1");
		MobileElement button1 = (MobileElement)driver.findElement(By.id("de.duenndns.gmdice:id/die1"));
		button1.tap(1, 1000);
		
		System.out.println("Pressing cancel...");
		MobileElement cancel2 = (MobileElement)driver.findElement(By.id("android:id/button2"));
		cancel2.click();
		
		System.out.println("Long tapping id/die2");
		MobileElement button2 = (MobileElement)driver.findElement(By.id("de.duenndns.gmdice:id/die2"));
		button2.tap(1, 1000);
		
		System.out.println("Pressing cancel...");
		MobileElement cancel3 = (MobileElement)driver.findElement(By.id("android:id/button2"));
		cancel3.click();
		
		System.out.println("Long tapping id/die3");
		MobileElement button3 = (MobileElement)driver.findElement(By.id("de.duenndns.gmdice:id/die3"));
		button3.tap(1, 1000);
	}
}