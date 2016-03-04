package edu.wm.translationengine.appium;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.github.genium_framework.appium.support.server.AppiumServer;
import com.github.genium_framework.server.ServerArguments;

/**
 * This class programmatically starts and stops an Appium server. 
 */
public class AppiumServerWorker {

	private AppiumServer appiumServer;
	private String appiumDir;
	
	/**
	 * Constructor.
	 * 
	 * @param appiumDir	root Appium directory on the local machine
	 */
	public AppiumServerWorker( String appiumDir ) throws AppiumException {
		
		if( appiumDir == null || appiumDir.equals("") )
			throw new AppiumException("Illegal Appium directory!");
		
		this.appiumDir = appiumDir;
	}
	
	/**
	 * Programmatically starts an Appium server.
	 * @throws InterruptedException 
	 */
	public void startServer() throws InterruptedException {
		
		System.out.println("Starting Appium server...");
		
		ServerArguments serverArguments = new ServerArguments();
		serverArguments.setArgument("--address", "127.0.0.1");
		//serverArguments.setArgument("--chromedriver-port", 4723);
		serverArguments.setArgument("--port", 4723);
		serverArguments.setArgument("--no-reset", true);
		serverArguments.setArgument("--local-timezone", true);

		appiumServer = new AppiumServer(new File(appiumDir), serverArguments);
		appiumServer.startServer();
		Thread.sleep(500);
		System.out.println("Server started!");
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
}