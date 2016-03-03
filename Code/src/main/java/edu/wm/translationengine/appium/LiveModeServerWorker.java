package edu.wm.translationengine.appium;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.SystemUtils;
import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gargoylesoftware.htmlunit.WebClient;
import com.github.genium_framework.appium.support.server.AppiumServer;
import com.github.genium_framework.server.ServerArguments;

/**
 * This class programmatically starts and stops an Appium server. 
 */
public class LiveModeServerWorker {
	
	public static String DEFAULT_WINDOWS_APPIUM_DIR = "C:\\Program Files (x86)\\Appium";
	public static String DEFAULT_MAC_APPIUM_DIR = "/Applications/Appium.app/Contents/Resources/node_modules/appium/";
	
	// Default server is localhost (127.0.0.1) on port 4723
	public static String DEFAULT_SERVER = "http://localhost:4723/wd/hub";

	public static String DEVICE_NAME = "Android Emulator";
	public static String PLATFORM_NAME = "Android";
	public static String PLATFORM_VERSION = "";
		
	private AppiumServer appiumServer;
	private DesiredCapabilities capability;
	
	protected String appiumDir;
	private String serverURL;
	protected String appDir;
	
	/**
	 * Constructor.
	 * 
	 * @param appiumDir	root Appium directory on the local machine
	 */
	public LiveModeServerWorker( String appiumDir, String appDir ) throws AppiumException {
		
		if( appiumDir == null || appiumDir.equals("") )
			throw new AppiumException("Illegal Appium directory!");
		
		this.appiumDir = appiumDir;
		this.appDir = appDir;
		serverURL = DEFAULT_SERVER;
	}
	
	public LiveModeServerWorker() throws AppiumException {
		
		appiumDir = DEFAULT_WINDOWS_APPIUM_DIR;
		serverURL = DEFAULT_SERVER;
	}
	
	/**
	 * Programmatically starts an Appium server.
	 * @throws InterruptedException 
	 */
	public void startServer() throws InterruptedException {
		
		System.out.println("Starting Appium server...");
		
		capability = new DesiredCapabilities();
		capability.setCapability("deviceName", DEVICE_NAME);
		capability.setCapability("platformName", PLATFORM_NAME);
		capability.setCapability("platformVersion", PLATFORM_VERSION);
		
		capability.setCapability("app", appDir);
		
		ServerArguments serverArguments = new ServerArguments();
		serverArguments.setArgument("--address", "127.0.0.1");
		serverArguments.setArgument("--port", 4723);
		serverArguments.setArgument("--no-reset", true);
		serverArguments.setArgument("--local-timezone", true);

		appiumServer = new AppiumServer(new File(appiumDir), serverArguments);
		appiumServer.startServer();
		Thread.sleep(500);
		
		System.out.println("Server started!");
	}
	
	public void setApp(String appDir) {
		this.appDir = appDir;
	}
	
	public DesiredCapabilities getCapabilities() {
		return capability;
	}
	
	/**
	 * Stop the Appium server.
	 */
	public void stopServer() {
		System.out.println("Stopping Appium server...");
		appiumServer.stopServer();
		System.out.println("Server stopped!");
	}
	
	/**
	 * Change the root Appium directory.
	 * 
	 * @param dir the new Appium directory
	 */
	public void setAppiumDirectory( String dir ) {
		appiumDir = dir;
	}
	
	public String getServerURL() {
		return serverURL;
	}
	
	public static String getOSAppiumDir() {
		String directory = null;
		if (SystemUtils.IS_OS_WINDOWS) {
			directory = LiveModeServerWorker.DEFAULT_WINDOWS_APPIUM_DIR;
		} else if (SystemUtils.IS_OS_MAC) {
			directory = LiveModeServerWorker.DEFAULT_MAC_APPIUM_DIR;
		}
		return directory;
	}
	
	/**
	 * Get Appium server response. 
	 * 
	 * Appium will communicate back to the user once it receives a command.
	 * It will respond if there is anything wrong on it's end. We need to capture
	 * that response and alert the user if there is an error with Appium.
	 * 
	 * @param serverURL
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public int getServerResponseCode() throws ClientProtocolException, IOException {
	
		try {
	      HttpURLConnection.setFollowRedirects(false);
	      HttpURLConnection con =
	         (HttpURLConnection) new URL(serverURL).openConnection();
	      con.setRequestMethod("HEAD");
	      
	      System.out.println("url: " + con.getURL());
	    
	      return con.getResponseCode();
	    }
	    catch (Exception e) {
	       e.printStackTrace();
	       return -1;
	    }
	}
}