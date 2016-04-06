package com.evancharlton.mileage.test;

import android.util.Log;
import junit.framework.AssertionFailedError;
import com.robotium.solo.Solo;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import com.evancharlton.mileage.Mileage;

public class TestBasic extends ActivityInstrumentationTestCase2<com.evancharlton.mileage.Mileage> {

	private Solo solo;

	@TargetApi(Build.VERSION_CODES.FROYO)
	public TestBasic() {
		super(GameMasterDice.class);
	}

	@Override
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testActions() {
		View curView = null;

		// com.evancharlton.mileage:id/price
		curView = solo.getView(android.widget.EditText.class, 1);
		solo.clickOnView(curView);

		// com.evancharlton.mileage:id/price
		curView = solo.getView(android.widget.EditText.class, 2);
		solo.enterText(curView, "9999999);

		// com.evancharlton.mileage:id/volume
		curView = solo.getView(android.widget.EditText.class, 2);
		solo.clickOnView(curView);

		// com.evancharlton.mileage:id/volume
		curView = solo.getView(android.widget.EditText.class, 2);
		solo.enterText(curView, "1234567890);

		// com.evancharlton.mileage:id/odometer
		curView = solo.getView(android.widget.EditText.class, 3);
		solo.clickOnView(curView);

		// com.evancharlton.mileage:id/odometer
		curView = solo.getView(android.widget.EditText.class, 3);
		solo.enterText(curView, "3333333);
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}