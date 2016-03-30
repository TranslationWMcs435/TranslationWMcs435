package edu.wm.translationengine.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
public class AppiumTestType {

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
		//revamped to play with mileage
		public static String MILEAGE_DIR = System.getProperty("user.dir") + "\\res\\apk\\com.evancharlton.mileage_3110.apk";
		
		private AppiumDriver<MobileElement> driver;
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
			
			//DesiredCapabilities capability = DesiredCapabilities.android();
			
			// Specify apk file to test
			File file = new File(MILEAGE_DIR);
			capability.setCapability("app", file.getAbsolutePath());
			
			URL serverURL = new URL(DEFAULT_SERVER + ":" + DEFAULT_PORT + DEFAULT_SUFFIX);
			
			// Connect capability to server
			// AppiumDriver is the main class that allows us to interact with Appium, 
			// which then interacts with the mobile device
			driver = new AndroidDriver<MobileElement>(serverURL, capability);
		}

		@After
		public void tearDown() throws Exception {
			
			driver.quit();
			serverWorker.stopServer();
		}
		
		@Test
		public void testType() throws InterruptedException{
			
			/* Element IDs obtained from uiautomatorviewer */
			// Type to Price per Gallons
			driver.findElement(By.name("Price per Gallons")).sendKeys("1.75");
			//Type to Gallons
			driver.findElement(By.name("Gallons")).sendKeys("10");
			//Type to Odometer
			driver.findElement(By.name("Odometer")).sendKeys("123456");
			//Type to Comment
			driver.findElement(By.name("Comment")).sendKeys("My car exploded halfway through fueling!");
			// Tap "Save Filup"
			driver.findElement(By.name("Save Fillup")).tap(1,50);
			Thread.sleep(300);
			//Tap by id
			driver.findElement(By.id("android:id/text1")).tap(1,50);
			Thread.sleep(5000);
			
		}

}
