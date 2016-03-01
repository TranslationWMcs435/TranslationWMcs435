package edu.wm.translationengine.appium;

import org.openqa.selenium.By;

import edu.wm.translationengine.classes.*;
import io.appium.java_client.MobileElement;


public class AppiumFunctions {


	public void appium_switcher(String action, StepTestCase s){
		
		if(action.equals("CLICK")){
			//pull component 
			String name = s.getComponent().getText();
			//format string
			String format = "\t\t\tdriver.findElement(By.name(\""+name+"\")).click();\n"
					+ "\t\t\tThread.sleep(250);\n";
			//write string to file
			AppiumTranslator.toWrite.add(format);
		
		}
		if(action.equals("LONG_CLICK")){
			//pull component 
			String name = s.getComponent().getText();
			//format string
			String format = "\t\t\t((MobileElement)driver.findElement(By.name(\""+name+"\"))).tap(1, 800);\n"
					+"\t\t\tThread.sleep(250);\n";
			AppiumTranslator.toWrite.add(format); 			
		}
		//TODO: add typing?
	}	
	
}
