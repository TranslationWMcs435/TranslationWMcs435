package edu.wm.translationengine.appium;

import io.appium.java_client.MobileElement;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *	This class commands Appium to perform actions 
 *	on Android UI components with a specific ID.
 */
public class ActionCommands {

	private RemoteWebDriver driver; 
	
	public ActionCommands( RemoteWebDriver driver ) {
		this.driver = driver;
	}
	
	/**
	 * Commands Appium to click.
	 * 
	 * @param inputID	the UI element on an Android apk to tap
	 * @return	true if tap was successful, false otherwise
	 * @throws InterruptedException 
	 * @throws AppiumException 
	 */
	public boolean click(String inputID) throws InterruptedException, AppiumException {
		
		if(inputID == null || inputID.equals(""))
			throw new AppiumException("Invalid parameter passed to commands.");
		
		driver.findElement( By.id(inputID) ).click();
		Thread.sleep(500);
		
		return true;
	}
	
	/**
	 * Commands Appium to long tap.
	 * 
	 * @param el element to long tap
	 * @return	true if long tap was successful, false otherwise
	 * @throws InterruptedException 
	 * @throws AppiumException 
	 */
	public boolean longTap(MobileElement el, int numFingers, int duration) throws InterruptedException, AppiumException {
		
		if(el == null)
			throw new AppiumException("Invalid parameter passed to commands.");
		
		el.tap(numFingers, duration);
		Thread.sleep(500);
		
		return true;
	}
	
	// TODO: Add more commands in later sprints 
}