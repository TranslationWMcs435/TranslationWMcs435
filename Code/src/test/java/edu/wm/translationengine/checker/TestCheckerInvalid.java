package edu.wm.translationengine.checker;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

import edu.wm.translationengine.appium.AppiumChecker;
import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.espresso.EspressoChecker;
import edu.wm.translationengine.main.Main;
import edu.wm.translationengine.robotium.RobotiumChecker;
import edu.wm.translationengine.trans.AbstractChecker;
import edu.wm.translationengine.uiautomator.UiAutomatorChecker;

public class TestCheckerInvalid {
	
	@Test
	public void testInvalidGMDice() throws Exception {
		
		String TEST_FAIL = "./IO/CheckerTestResources/simple_fail_checker_invalid.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_FAIL);
		
		// Appium needs app name only
		checker = new AppiumChecker();
		assertTrue(checker.checkAppData(testCase));
		
		// Espresso needs package name and main activity
		checker = new EspressoChecker();
		assertTrue(checker.checkAppData(testCase));
		
		// Robotium needs app name, package name, main activity 
		checker = new RobotiumChecker();
		assertTrue(checker.checkAppData(testCase));
		
		// UIAutomator does not need package name or main activity
		checker = new UiAutomatorChecker();
		assertTrue(checker.checkAppData(testCase));
	}
	
	/**
	 * Tests click action checking.
	 */
	@Test
	public void testClick() throws Exception {
		
		String TEST_FAIL = "./IO/CheckerTestResources/simple_fail_checker_invalid.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_FAIL);
		List<StepTestCase> steps = testCase.getSteps();
		
		Throwable a = null;
		
		try {
			
			// Appium needs only id
			checker = new AppiumChecker();
			assertFalse(checker.checkClick(steps.get(0).getComponent()));
			
			// Espresso needs id and type
			checker = new EspressoChecker();
			assertFalse(checker.checkClick(steps.get(0).getComponent()));
			
			// Robotium needs type and index
			checker = new RobotiumChecker();
			assertFalse(checker.checkClick(steps.get(0).getComponent()));
			
			// UiAutomator needs type and id
			checker = new UiAutomatorChecker();
			assertFalse(checker.checkClick(steps.get(0).getComponent()));
		}
		catch (NullPointerException e) {
			a = e;
		}
		
		assertTrue(a instanceof NullPointerException);
	}
	
	/**
	 * Tests long click checking
	 */
	@Test
	public void testLongClick() throws Exception {
		
		String TEST_FAIL = "./IO/CheckerTestResources/simple_fail_checker_invalid.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_FAIL);
		List<StepTestCase> steps = testCase.getSteps();
		
		Throwable t = null;
		
		try {
			// Appium checks only if key-value pair is null
			checker = new AppiumChecker();
			assertFalse(checker.checkLongClick(steps.get(1).getComponent()));
			
			// Espresso needs id and type
			checker = new EspressoChecker();
			assertFalse(checker.checkLongClick(steps.get(1).getComponent()));
			
			// Robotium needs index and type
			checker = new RobotiumChecker();
			assertFalse(checker.checkLongClick(steps.get(1).getComponent()));
			
			// UiAutomator needs id and type
			checker = new UiAutomatorChecker();
			assertTrue(checker.checkLongClick(steps.get(1).getComponent()));
		}
		catch(NullPointerException e) {
			t = e;
		}
	}

	/**
	 * Tests type checking
	 * 
	 * All frameworks should return true because
	 * all allow empty text input
	 */
	@Test
	public void testType() throws Exception {
		
		String TEST_FAIL = "./IO/CheckerTestResources/simple_fail_checker_invalid.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_FAIL);
		List<StepTestCase> steps = testCase.getSteps();
		
		// Appium needs id, text, and type
		checker = new AppiumChecker();
		assertFalse(checker.checkType(steps.get(2).getComponent()));
				
		// Espresso needs text
		checker = new EspressoChecker();
		assertFalse(checker.checkType(steps.get(2).getComponent()));
		
		// Robotium needs text only
		checker = new RobotiumChecker();
		assertFalse(checker.checkType(steps.get(2).getComponent()));
		
		// UiAutomator needs text only
		checker = new UiAutomatorChecker();
		assertTrue(checker.checkType(steps.get(2).getComponent()));
	}
	
	@Test
	public void testSwipe() throws Exception {
		
		String TEST_FAIL = "./IO/CheckerTestResources/simple_fail_checker_invalid.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_FAIL);
		List<StepTestCase> steps = testCase.getSteps();
		
		Throwable t = null;
		
		try {
			// Appium checker does not have swipe method
			
			// Espresso needs type and id
			checker = new EspressoChecker();
			assertFalse(checker.checkSwipe(steps.get(3).getComponent()));
			
			// Robotium needs positionX and positionY
			checker = new RobotiumChecker();
			assertFalse(checker.checkSwipe(steps.get(3).getComponent()));
			
			// UIAutomator needs positionX and positionY
			checker = new UiAutomatorChecker();
			assertFalse(checker.checkSwipe(steps.get(3).getComponent()));
		}
		catch(NullPointerException e) {
			t = e;
		}
		
		assertTrue(t instanceof NullPointerException);
	}
}

