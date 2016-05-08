package edu.wm.translationengine.checker;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wm.translationengine.appium.AppiumChecker;
import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.espresso.EspressoChecker;
import edu.wm.translationengine.main.Main;
import edu.wm.translationengine.robotium.RobotiumChecker;
import edu.wm.translationengine.trans.AbstractChecker;
import edu.wm.translationengine.uiautomator.UiAutomatorChecker;

public class TestCheckerValid {
	
	@Test
	public void testInvalidGMDice() throws Exception {
		
		String TEST_GMDICE_PASS = "./IO/CheckerTestResources/simple_pass_checker.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_GMDICE_PASS);
		
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
	
	@Test
	public void testClick() throws Exception {
		
		String TEST_GMDICE_PASS = "./IO/CheckerTestResources/simple_pass_checker.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_GMDICE_PASS);
		List<StepTestCase> steps = testCase.getSteps();
		
		// Appium needs only id
		checker = new AppiumChecker();
		assertTrue(checker.checkClick(steps.get(0).getComponent()));
		
		// Espresso needs id and type
		checker = new EspressoChecker();
		assertTrue(checker.checkClick(steps.get(0).getComponent()));
		
		// Robotium needs type and index
		checker = new RobotiumChecker();
		assertTrue(checker.checkClick(steps.get(0).getComponent()));
		
		// UiAutomator needs type and id
		checker = new UiAutomatorChecker();
		assertTrue(checker.checkClick(steps.get(0).getComponent()));
	}
	
	@Test
	public void testLongClick() throws Exception {
		
		String TEST_GMDICE_PASS = "./IO/CheckerTestResources/simple_pass_checker.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_GMDICE_PASS);
		List<StepTestCase> steps = testCase.getSteps();
		
		// Appium needs id and type
		checker = new AppiumChecker();
		assertTrue(checker.checkLongClick(steps.get(1).getComponent()));
		
		// Espresso needs id and type
		checker = new EspressoChecker();
		assertTrue(checker.checkLongClick(steps.get(1).getComponent()));
		
		// Robotium needs index and type
		checker = new RobotiumChecker();
		assertTrue(checker.checkLongClick(steps.get(1).getComponent()));
		
		// UiAutomator needs id and type
		checker = new UiAutomatorChecker();
		assertTrue(checker.checkLongClick(steps.get(1).getComponent()));
	}

	@Test
	public void testType() throws Exception {
		
		String TEST_GMDICE_PASS = "./IO/CheckerTestResources/simple_pass_checker.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_GMDICE_PASS);
		List<StepTestCase> steps = testCase.getSteps();
		
		// Appium needs id, text, and type
		checker = new AppiumChecker();
		assertTrue(checker.checkType(steps.get(2).getComponent()));
				
		// Espresso needs text
		checker = new EspressoChecker();
		assertTrue(checker.checkType(steps.get(2).getComponent()));
		
		// Robotium needs text only
		checker = new RobotiumChecker();
		assertTrue(checker.checkType(steps.get(2).getComponent()));
		
		// UiAutomator needs text only
		checker = new UiAutomatorChecker();
		assertTrue(checker.checkType(steps.get(2).getComponent()));
	}
	
	@Test
	public void testSwipe() throws Exception {
		
		String TEST_GMDICE_PASS = "./IO/CheckerTestResources/simple_pass_checker.txt";
		TestCase testCase;
		AbstractChecker checker;
		
		testCase = Main.parse(TEST_GMDICE_PASS);
		List<StepTestCase> steps = testCase.getSteps();
		
		// Appium checker does not have swipe method
		
		
		// Espresso needs type and id
		checker = new EspressoChecker();
		assertTrue(checker.checkSwipe(steps.get(3).getComponent()));
		
		// Robotium needs positionX and positionY
		checker = new RobotiumChecker();
		assertTrue(checker.checkSwipe(steps.get(3).getComponent()));
		
		// UIAutomator needs positionX and positionY
		checker = new UiAutomatorChecker();
		assertTrue(checker.checkSwipe(steps.get(3).getComponent()));
	}
}

