package [Test_package_name_here]
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.support.test.runner.AndroidJUnitRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.TestSuite;
import de.duenndns.gmdice.GameMasterDice;
import de.duenndns.gmdice.R;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestFile extends TestSuite{
	@Rule
	public ActivityTestRule<GameMasterDice> mActivityRule = new ActivityTestRule<GameMasterDice>(GameMasterDice.class);
	@Test
	public void test(){
			onView(withId(R.id.die0)).perform(click());
			onView(withId(R.id.die1)).perform(click());
			onView(withId(R.id.die2)).perform(click());
			onView(withId(R.id.die3)).perform(click());
			onView(withId(R.id.more)).perform(longClick());
			onData(allOf(is("More..."))).perform(click());
			onView(withText("Cancel")).perform(click());
			onView(withId(R.id.more)).perform(click());
			onData(allOf(is("1d2"))).perform(click());
	 }
 }