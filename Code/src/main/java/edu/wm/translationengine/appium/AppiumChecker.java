package edu.wm.translationengine.appium;

import edu.wm.translationengine.classes.Component;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.AbstractChecker;

public class AppiumChecker extends AbstractChecker {
	
	//class used to check for the presence of the necessary parameters to do an Appium test
	//will primarily rely on id values, but in the event we have no id, or conflicting/general values, ALL other necessary information must be provided to create a very specific xpath
	//PLEASE try to create JSON files which conform to UIAutomator viewer screen grabs
	@Override
	public boolean checkAppData(TestCase tc){
		if(tc.getAppName() == null || tc.getAppName().equals("")){
			return false;
		}
		//check that a file named appName.apk exists in the resources folder
		return true;
		
	}
	@Override
	public boolean checkClick(Component c){
		//if id is null we cannot do anything. 
		if(c.getId() == null)
			return false;
		//if id is a tricky id, all the other parameters better be explicit
		//class text and index are musts unless we are dealing with a checkedText widget which may rotate indices 
		else if(c.getId().contains("android:id/") || c.getId().equals("") ){
			if(c.getType() == null)
				return  false;
			if(c.getText() == null)
					return false;
			if(!c.getType().equals("android.widget.CheckedTextView")){
				if(c.getIndex() == null)
					return false;
			}
		}
		//in any other case where we arent dealing with tricky ids, we can go ahead and use the id
		return true;
	}
	@Override 
	public boolean checkLongClick(Component c){
		//if id is null we cannot do anything. 
		if(c.getId() == null)
			return false;
		//if id is a tricky id, all the other parameters better be explicit
		//class text and index are musts unless we are dealing with a checkedText widget which may rotate indices 
		else if(c.getId().contains("android:id/") || c.getId().equals("") ){
			if(c.getType() == null)
				return  false;
			if(c.getText() == null)
					return false;
			if(!c.getType().equals("android.widget.CheckedTextView")){
				if(c.getIndex() == null)
					return false;
			}
		}
		//in any other case where we arent dealing with tricky ids, we can go ahead and use the id
		return true;
		
	}
	@Override
	public boolean checkType(Component c){
		//TODO: this will need to be redone. Im pretty sure our group is using the text parameter wrong. UIAutomator uses text to refer to text on buttons on the screen. We should have a different parameter for text that we wish to type in
		
		
		//if id is null we cannot do anything. 
		if(c.getId() == null)
			return false;
		//if id is a tricky id, all the other parameters better be explicit
		//class text and index are musts unless we are dealing with a checkedText widget which may rotate indices 
		else if(c.getId().contains("android:id/") || c.getId().equals("") ){
			if(c.getType() == null)
				return  false;
			if(c.getText() == null)
					return false;
			if(!c.getType().equals("android.widget.CheckedTextView")){
				if(c.getIndex() == null)
					return false;
			}
		}
		//in any other case where we arent dealing with tricky ids, we can go ahead and use the id
		return true;
	}

}

