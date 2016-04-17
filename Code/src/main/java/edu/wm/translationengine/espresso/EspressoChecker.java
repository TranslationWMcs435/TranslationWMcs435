package edu.wm.translationengine.espresso;
import edu.wm.translationengine.classes.Component;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.trans.AbstractChecker;


public class EspressoChecker extends AbstractChecker{
	
	@Override
	public boolean checkAppData(TestCase tc){
		if(tc.getPackageName() == null || tc.getPackageName().equals("")){
			return false;
		}
		if(tc.getMainActivity() == null || tc.getMainActivity().equals("")){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean checkClick(Component c){
		if(c.getType().equals("android.widget.CheckedTextView")){
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
		if(c.getId() == null || c.getId().equals("")){
			return false;
		}
		if(c.getText() == null){
			return false;
		}
		return true;
	}
}
