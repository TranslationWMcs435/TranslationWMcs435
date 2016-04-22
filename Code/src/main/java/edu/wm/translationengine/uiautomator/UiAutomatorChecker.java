package edu.wm.translationengine.uiautomator;

import edu.wm.translationengine.classes.Component;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.AbstractChecker;

public class UiAutomatorChecker extends AbstractChecker {

	public UiAutomatorChecker() {
		// Shrug
		super();
	}
	

	/**
	 * UiAutomator does not need to know the package or main activity, but will need the name
	 * if it wants to open the app automatically. It remains a noncritical problem, so it still passes.
	 * NOTE: The name MUST be the same as how the app APPEARS ON THE APP SCREEN. This should not be too hard
	 * to accommodate in the JSON files. Otherwise it doesn't work. Take note of that, now.
	 */
	@Override
	public boolean checkAppData(TestCase tc){
		if(tc.getAppName() == null || tc.getAppName().equals("")){
			System.out.println("No app name included. Please start your app before running the test.");
		}
		return true;
	}
	
	/**
	 * Requires: some sort of reasonable description of the button.
	 * The JSON files do not atm include a bunch of these at crucial junctures.
	 */
	@Override
	public boolean checkClick(Component c){
		if(c.getType().equals("android.widget.CheckedTextView")){
			if(c.getText() == null){
				return false;
			}
		}
		else if(c.getType().equals("android.widget.ImageButton")){
			if(c.getDescription() == null || c.getDescription().equals("")){
				return false;
			}
		}
		else if(c.getId().equals("")){
			if(c.getText() == null){
				return false;
			}
		}
		else if(c.getId().length() > 11 && c.getId().substring(0, 11).equals("android:id/")){
			if(c.getText() == null){
				return false;
			}
		}
		else{
			if(c.getId() == null || c.getId().equals("")){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean checkLongClick(Component c){
		if(c.getType().equals("android.widget.CheckedTextView")){
			if(c.getText() == null){
				return false;
			}
		}
		else if(c.getType().equals("android.widget.ImageButton")){
			if(c.getDescription() == null || c.getDescription().equals("")){
				return false;
			}
		}
		else if(c.getId().equals("")){
			if(c.getText() == null){
				return false;
			}
		}
		else if(c.getId().length() > 11 && c.getId().substring(0, 11).equals("android:id/")){
			if(c.getText() == null){
				return false;
			}
		}
		else{
			if(c.getId() == null || c.getId().equals("")){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean checkType(Component c){
		if(c.getText() == null){
			return false;
		}
		return true;
	}

}
