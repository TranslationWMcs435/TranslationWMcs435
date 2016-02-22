package de.duenndns.gmdice.test;

import static org.junit.Assert.*;



import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.support.test.runner.AndroidJUnitRunner;

import de.duenndns.gmdice.GameMasterDice;
import de.duenndns.gmdice.R;
import junit.framework.TestSuite;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class JsonTest extends TestSuite{
    @Rule
    public ActivityTestRule<GameMasterDice> mActivityRule = new ActivityTestRule<GameMasterDice>(
    		GameMasterDice.class);
	@Test
	public void test() {
		onView(withId(R.id.die0)).perform(click());
		onView(withId(R.id.die1)).perform(click());
		onView(withId(R.id.die2)).perform(click());
		onView(withId(R.id.die3)).perform(click());
//		onView(withId(R.id.more)).perform(longClick());
		//onView(withId(R.id.text1)).perform(click());
		//onView(withId(R.id.button2)).perform(click());
		onView(withId(R.id.more)).perform(click());
		//onView(withId(R.id.text1)).perform(click());
	}

}
