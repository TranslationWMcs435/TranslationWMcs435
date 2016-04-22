package edu.wm.translationengine.uiautomator;

import edu.wm.translationengine.classes.Component;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.AbstractChecker;
import edu.wm.translationengine.trans.StepTestCaseDataPrinter;

public class UiAutomatorChecker extends AbstractChecker {
	Boolean last_id_exists;
	
	public UiAutomatorChecker() {
		// Shrug
		super();
		last_id_exists = true;
	}
	

	/**
	 * UiAutomator does not need to know the package or main activity, but will need the name
	 * if it wants to open the app automatically. It remains a noncritical problem, so it still passes.
	 * NOTE: The name MUST be the same as how the app APPEARS ON THE APP SCREEN. This should not be too hard
	 * to accommodate in the JSON files. Otherwise it doesn't work. Take note of that, now.
	 */
	@Override
	public boolean checkAppData(TestCase tc){
		// Rewrote requirements so we require none of the same package info as other frameworks.
		return true;
	}
	
	/**
	 * Requires: some sort of reasonable description of the button.
	 * The JSON files do not atm include a bunch of these at crucial junctures.
	 */
	@Override
	public boolean checkClick(Component c){
		// Mostly the same as Espresso, but updating with last_id_exists.
		if(c.getType().equals("android.widget.CheckedTextView")){
			if(c.getText() == null){
				return false;
			}
		}
		// Images are impossible to recognize without descriptions.
		else if(c.getType().equals("android.widget.ImageButton")){
			if(c.getDescription() == null || c.getDescription().equals("")){
				return false;
			}
		}
		else if(c.getId().equals("")){
			if(c.getText() == null || c.getText().equals("")){
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
				last_id_exists = false;
				return false;
			}
		}
		last_id_exists = true;
		return true;
	}
	
	@Override
	public boolean checkLongClick(Component c){
		// Mostly the same as Espresso, but updating with last_id_exists.
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
				last_id_exists = false;
				return false;
			}
		}
		last_id_exists = true;
		return true;
	}
	
	@Override
	public boolean checkType(Component c){
		if(c.getId() == null || c.getId().equals("") || c.getId().equals("id/keyboard_view")){
			// The ID field is empty, or is too vague. We need the last thing we clicked on in this case.
			if(!last_id_exists){
				return false;
			}
		}
		if(c.getText() == null){
			// Why are we typing nothing? I don't know. But it doesn't actually break this one.
		}
		return true;
	}
	
	@Override
	public boolean checkSwipe(Component c){
		if (c.getPositionX() == null || c.getPositionY() == null || c.getPositionX().equals("") || c.getPositionY().equals("")){
			// We need some initial positions if we're gonna fake a swipe.
			return false;
		}
		return true;
	}

}
