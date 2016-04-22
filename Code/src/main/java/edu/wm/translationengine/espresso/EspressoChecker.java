package edu.wm.translationengine.espresso;
import edu.wm.translationengine.classes.Component;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.AbstractChecker;


public class EspressoChecker extends AbstractChecker{
	
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
	
	@Override
	public boolean checkType(Component c){
		if(c.getText() != null){
			return true;
		}
		return false;
	}
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
