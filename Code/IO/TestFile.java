package [Test_package_name_here]

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class TestBackPress extends UiAutomatorTestCase {
	public void uiautomator_test() throws UiObjectNotFoundException{
		new UiObject(new UiSelector().resourceId("de.duenndns.gmdice:id/die0")).click();
		new UiObject(new UiSelector().resourceId("de.duenndns.gmdice:id/die1")).click();
		new UiObject(new UiSelector().resourceId("de.duenndns.gmdice:id/die2")).click();
		new UiObject(new UiSelector().resourceId("de.duenndns.gmdice:id/die3")).click();
		new UiObject(new UiSelector().resourceId("de.duenndns.gmdice:id/more")).longClick();
		new UiObject(new UiSelector().resourceId("de.duenndns.gmdice:id/text1")).click();
		new UiObject(new UiSelector().resourceId("android:id/button2")).click();
		new UiObject(new UiSelector().resourceId("de.duenndns.gmdice:id/more")).click();
		new UiObject(new UiSelector().resourceId("de.duenndns.gmdice:id/text1")).click();
	}
