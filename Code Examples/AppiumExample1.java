import java.io.File;
import java.io.IOException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This example shows how to perform simple 
 * automated taps using Appium.
 * 
 * Needs the following (.jar) dependencies:
 * 1) Selenium (http://docs.seleniumhq.org/download/)
 * 2) Appium java-client (https://search.maven.org/#search|ga|1|g%3Aio.appium%20a%3Ajava-client)
 * 3) Appium-Support (https://search.maven.org/#search|ga|1|g%3A%22com.github.genium-framework%22%20AND%20a%3A%22Appium-Support%22)
 */
public class AppiumExample1 {
	
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
	
	public static String GMDICE_DIR = System.getProperty("user.dir") + "\\apk\\gmdice.apk";
	
	public static void main( String [] args ) throws IOException {
		
		// Start Appium server
		AppiumServerWorker serverWorker = new AppiumServerWorker(DEFAULT_APPIUM_DIR);
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
		AppiumDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(serverURL, capability);
		
		// Check that driver was able to connect to emulator/device
		//serverWorker.getServerResponse(DEFAULT_SERVER + ":" + DEFAULT_PORT + DEFAULT_SUFFIX);
		
		performTaps(driver);
		
		// Quit
		driver.quit();
		serverWorker.stopServer();
		System.exit(0);
	}
	
	public static void performTaps(RemoteWebDriver driver) {
		
		/* Element IDs obtained from uiautomatorviewer */
		
		// Tap first button
		driver.findElement(By.id("de.duenndns.gmdice:id/die0")).click();
		// Tap second button
		driver.findElement(By.id("de.duenndns.gmdice:id/die1")).click();
		// Tap third button
		driver.findElement(By.id("de.duenndns.gmdice:id/die2")).click();
		// Tap fourth button
		driver.findElement(By.id("de.duenndns.gmdice:id/die3")).click();
		// Tap ellipsis
		driver.findElement(By.id("de.duenndns.gmdice:id/more")).click();
		// Tap first 1d2 dice
		driver.findElement(By.id("android:id/text1")).click();
	}
}