package edu.wm.translationengine.appium;

import java.util.List;

import org.openqa.selenium.By;

import edu.wm.translationengine.classes.*;
import edu.wm.translationengine.trans.GenericFunctions;
import io.appium.java_client.MobileElement;


public class AppiumFunctions extends GenericFunctions{


	public void appium_switcher(String action, StepTestCase s){
		
		//pull necessary components up here
		String buttonID, xpath;
		String buttonString = "\t\t\tbutton=null;\n";
		AppiumTranslator.toWrite.add(buttonString);		
    	//for some reason x and y buttons arent doing much in the way of being helpful
    	//x = Integer.parseInt(s.getComponent().getPositionX());  X and Y useless for now, hopefully can be used to help with swipes in the future
    	//y = Integer.parseInt(s.getComponent().getPositionY());
    	String type = s.getComponent().getType();
    	String text = s.getComponent().getText();
    	String index = s.getComponent().getIndex();
    	buttonID = s.getComponent().getId();
    	
    	if(type.equals("android.widget.CheckedTextView"))//check text view may rotate the index around, so xpath will not include index for this type of element
    		xpath ="//*[@class='"+type+ "' and @text='"+text+"' and @resource-id ='"+buttonID+"']";
    	else
    		xpath ="//*[@class='"+type+ "' and @text='"+text+"' and @resource-id ='"+buttonID+"' and @index='"+ index+ "']";
    	
    	//System.out.println("button ID is: "+ buttonID);
    	//System.out.println("xpath search thingy configured as : " + xpath);
    	
    	if(buttonID.contains("android:id/")){
     		//android ids are pretty fucking generic so here we create a list of possible buttons that the JSON could be referring to given the created xpath
    		String format ="\t\t\ta = (List<MobileElement>) driver.findElements(By.xpath(\""+xpath+"\"));\n"+
           	"\t\t\tfor(j=0; j <a.size(); j++){\n"+
        		"\t\t\t\tbutton = a.get(j);\n"+	   
        	"\t\t\t}\n";
    		AppiumTranslator.toWrite.add(format);
        	
    	}else{
        	//check for special button ids which would not be identifiable on screen
        	if((buttonID!= null) && (buttonID.equals("id/back"))){
        		String format = "\t\t\tdriver.navigate().back();\n";
        		//write string to file
    			AppiumTranslator.toWrite.add(format);
        		return;
        	}
        	//in any case were we dont have special IDs or android IDs, we can find by ID and be good
        	String format = "\t\t\tbutton = (MobileElement)driver.findElement(By.id(\""+buttonID+"\"));\n";
        	AppiumTranslator.toWrite.add(format);
    	}
		
		
		if(action.equals("CLICK")){
			//format string
			String format = "\t\t\tbutton.tap(1, 25);\n";
			//write string to file
			AppiumTranslator.toWrite.add(format);
		
		}
		if(action.equals("LONG_CLICK")){
			//format string
			String format = "\t\t\tbutton.tap(1, 1000);\n";
			AppiumTranslator.toWrite.add(format); 			
		}
		
		if(action.equals("TYPE")){
			//NOTE: this will most likely need to be refactored once we decide whats up with the text field
			//format string
			String format = "\t\t\tbutton.clear();\n"+
	        		"\t\t\tbutton.sendKeys(\""+text+"\");\n";
			AppiumTranslator.toWrite.add(format);
			
		}
		//would add swipes here, but cannot be implemented with the given JSON files... To get an idea of how they would look, go to AppiumLive.java
		//declare sleep and write to the file
		String sleep = "\t\t\tThread.sleep(750);\n";
		AppiumTranslator.toWrite.add(sleep);
	}	
	
}
