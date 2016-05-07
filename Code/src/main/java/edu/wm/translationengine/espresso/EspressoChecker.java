package edu.wm.translationengine.espresso;

import edu.wm.translationengine.classes.Component;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.AbstractChecker;

/**
 * This is the EspressoChecker class, it extends AbstractChecker. It is 
 * run before the translating is done to make sure that the JSON file 
 * given as input has enough information to generate Espresso code that
 * works.
 * 
 * @author Nathan Chen
 *
 */
public class EspressoChecker extends AbstractChecker{
	
	/**
	 * Checks that the TestCase that is being analyzed has enough information
	 * about the app under test to build a working Espresso test case.
	 * 
	 * @param tc TestCase that is being analyzed
	 */
	@Override
	public boolean checkAppData(TestCase tc){
		int checksum = 0;
		if(tc.getPackageName() != null && !tc.getPackageName().equals("")){
			checksum++;
		}
		else{
			System.out.println("Package name is missing.");
		}
		if(tc.getMainActivity() != null && !tc.getMainActivity().equals("")){
			checksum++;
		}
		else{
			System.out.println("Main activity is missing.");
		}
		if(checksum == 2){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks that the given click component has enough information
	 * to generate a working Espresso click command.
	 * 
	 * @param c Component that is being checked currently
	 */
	@Override
	public boolean checkClick(Component c){
		if(c.getType().equals("android.widget.CheckedTextView")){
			if(c.getText() != null){
				return true;
			}
		}
		else if(c.getType().equals("android.widget.ImageButton")){
			if(c.getDescription() != null && !c.getDescription().equals("")){
				return true;
			}
		}
		else if(c.getId().equals("")){
			if(c.getText() != null && !c.getText().equals("")){
				return true;
			}
		}
		else if(c.getId().length() > 11 && c.getId().substring(0, 11).equals("android:id/")){
			if(c.getText() != null){
				return true;
			}
		}
		else{
			if(c.getId() != null && !c.getId().equals("")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks that the given long click component has enough information
	 * to generate a working Espresso long click command.
	 * 
	 * @param c Component that is being checked currently
	 */
	@Override
	public boolean checkLongClick(Component c){
		if(c.getType().equals("android.widget.CheckedTextView")){
			if(c.getText() != null){
				return true;
			}
		}
		else if(c.getType().equals("android.widget.ImageButton")){
			if(c.getDescription() != null && !c.getDescription().equals("")){
				return true;
			}
		}
		else if(c.getId().equals("")){
			if(c.getText() != null && !c.getText().equals("")){
				return true;
			}
		}
		else if(c.getId().length() > 11 && c.getId().substring(0, 11).equals("android:id/")){
			if(c.getText() != null){
				return true;
			}
		}
		else{
			if(c.getId() != null && !c.getId().equals("")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks that the given type component has enough information
	 * to generate a working Espresso type command.
	 * 
	 * @param c Component that is being checked currently
	 */
	@Override
	public boolean checkType(Component c){
		if(c.getText() != null){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks that the given swipe component has enough information
	 * to generate a working Espresso swipe command.
	 * 
	 * @param c Component that is being checked currently
	 */
	@Override
	public boolean checkSwipe(Component c){
		if(c.getType().equals("android.widget.CheckedTextView")){
			if(c.getText() != null){
				return true;
			}
		}
		else{
			if(c.getId() != null && !c.getId().equals("")){
				return true;
			}
		}
		return false;
	}
}
