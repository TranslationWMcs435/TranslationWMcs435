package appium;

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
	 * Commands Appium to tap.
	 * 
	 * @param inputID	the UI element on an Android apk to tap
	 * @return	true if tap was successful, false otherwise
	 */
	public boolean tap(String inputID) {
		
		if(inputID == null || inputID.equals(""))
			return false;
		
		driver.findElement( By.id(inputID) );
		return true;
	}
	
	// TODO: Add more commands in later sprints 
}