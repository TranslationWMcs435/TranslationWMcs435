package edu.wm.translationengine.trans;

import java.util.ArrayList;
import java.util.List;

import edu.wm.translationengine.classes.Component;
import edu.wm.translationengine.classes.StepTestCase;
import edu.wm.translationengine.classes.TestCase;
/**
 * This is the AbstractChecker it is an abstract class that should
 * be extended. Its function is to check that the data pulled from
 * the input JSON file is correct and that all data that is needed 
 * is present and error-free.
 * 
 * @author Nathan Chen
 *
 */
public abstract class AbstractChecker {
	
	
	boolean appDataPass;
	int action_indexer;
	boolean pass;
	ArrayList<StepTestCase> cases_with_errors;
	
	public AbstractChecker(){
		appDataPass = false;
		action_indexer = 0;
		pass = false;
		cases_with_errors = new ArrayList<StepTestCase>();
	}
	
	/**
	 * This is the core of the AbstractChecker it iterates 
	 * through all of the StepTestCases and calls the necessary
	 * checker functions such as checkAppData, checkClick, 
	 * checkLongClick, and checkType. Then after it has iterated
	 * through all the StepTestCases it prints out any StepTestCases
	 * that have errors and then returns a boolean, true if all 
	 * StepTestCases passed the checking else false. 
	 * 
	 * @param tc TestCase parsed from input JSON file
	 * @return boolean true if all StepTestCases passed the checking else false
	 */
	public boolean runCheck(TestCase tc){
		
		appDataPass = checkAppData(tc);
		List<StepTestCase> stcList = tc.getSteps();
		
		for(int i = 0; i < stcList.size(); i++){
			StepTestCase cur = stcList.get(i); //Grab current StepTestCase	
			if(cur.getAction().equals("CLICK")){
				pass = checkClick(cur.getComponent());
				if(pass == false){
					cases_with_errors.add(cur);
				}
			}
			else if(stcList.get(i).getAction().equals("LONG_CLICK")){
				pass = checkLongClick(cur.getComponent());
				if(pass == false){
					cases_with_errors.add(cur);
				}
			}
			else if(stcList.get(i).getAction().equals("TYPE")){
				pass = checkType(cur.getComponent());
				if(pass == false){
					cases_with_errors.add(cur);
				}
			}
			else if(stcList.get(i).getAction().equals("SWIPE_UP") || stcList.get(i).getAction().equals("SWIPE_DOWN")
					|| stcList.get(i).getAction().equals("SWIPE_LEFT") || stcList.get(i).getAction().equals("SWIPE_RIGHT")){
				pass = checkSwipe(cur.getComponent());
				if(pass == false){
					cases_with_errors.add(cur);
				}
			}
			// When request to open app.
			else if(stcList.get(i).getAction().equals("OPEN")){
				pass = checkType(cur.getComponent());
				if(pass == false){
					cases_with_errors.add(cur);
				}
			}
			// When request to open app.
			else if(stcList.get(i).getAction().contains("SWIPE")){
				pass = checkSwipe(cur.getComponent());
				if(pass == false){
					cases_with_errors.add(cur);
				}
			}						
		}
		
		if(cases_with_errors.isEmpty() && appDataPass == true){
			return true;
		}
		else{
			if(!cases_with_errors.isEmpty()){
				System.out.println("StepTestCase errors:");
				StepTestCaseDataPrinter printer = new StepTestCaseDataPrinter();
				for(int j = 0; j < cases_with_errors.size(); j++){
					printer.printData(cases_with_errors.get(j));
				}
			}
			return false;
		}
	}
	
	/**
	 * Checks the app data, makes sure that necessary inputs from
	 * data such as appName, packageName, and mainActivity are
	 * present if they are needed for the execution of the translator.
	 * 
	 * Make sure to add print statements to print out which items have
	 * errors! Prints for these pieces of data need to be put here so
	 * less variables can be used.
	 * 
	 * OVERRIDE THIS METHOD
	 * 
	 * @param tc
	 * @return boolean true if necessary data is present else false
	 */
	public boolean checkAppData(TestCase tc){
		return false;
	}
	
	/**
	 * Checks that the necessary information for clicks are present
	 * 
	 * OVERRIDE THIS METHOD
	 * 
	 * @param c
	 * @return boolean true if necessary data is present else false
	 */
	public boolean checkClick(Component c){
		return false;
	}
	/**
	 * Checks that the necessary information for long clicks are present
	 * 
	 * OVERRIDE THIS METHOD
	 * 
	 * @param c
	 * @return boolean true if necessary data is present else false
	 */
	public boolean checkLongClick(Component c){
		return false;
	}
	/**
	 * Checks that the necessary information for typing are present
	 * 
	 * OVERRIDE THIS METHOD
	 * 
	 * @param c
	 * @return boolean true if necessary data is present else false
	 */
	public boolean checkType(Component c){
		return false;
	}
	/**
	 * Checks that the necessary information for swiping are present
	 * 
	 * OVERRIDE THIS METHOD
	 * 
	 * @param c
	 * @return boolean true if necessary data is present else false
	 */
	public boolean checkSwipe(Component c){
		return false;
	}
	
	
	
}
