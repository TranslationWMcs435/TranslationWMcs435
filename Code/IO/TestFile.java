package [Test_package_name_here]
import static android.support.test.espresso.action.ViewActions.typeText;
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
import com.evancharlton.mileage.Mileage;
import com.evancharlton.mileage.R;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestFile extends TestSuite{
	@Rule
	public ActivityTestRule<Mileage> mActivityRule = new ActivityTestRule<Mileage>(Mileage.class);
	@Test
	public void test(){
			onView(withId(R.id.price)).perform(click());
			onView(withId(R.id.price)).perform(typeText("9999999"));
			onView(withId(R.id.volume)).perform(click());
			onView(withId(R.id.volume)).perform(typeText("1234567890"));
			onView(withId(R.id.odometer)).perform(click());
			onView(withId(R.id.odometer)).perform(typeText("3333333"));
	 }
 }