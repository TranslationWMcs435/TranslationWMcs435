package edu.semeru.wm.CallGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.semeru.wm.qextractor.model.MethodAPICallVO;



public class LeveledCallGraphComponent {
	private MethodManager mm = new MethodManager();

	private Analyzer az = new Analyzer();


	/**
	 * @return the mm
	 */
	public MethodManager getMm() {
		return mm;
	}


	/**
	 * @return the az
	 */
	public Analyzer getAz() {
		return az;
	}



	/**
	 * Save all method to MM
	 * @param allMethods
	 */
	private void saveAllMethodToMM(HashSet<String> allMethods){
		for(String str: allMethods){
			mm.addMethod(str);	
		}
	}



	/**
	 * update method Calls info
	 * @param methodCalls
	 */
	private void updateMethodCallToMM(HashMap<String, HashSet<String>> methodCalls){
		Iterator<Entry<String, HashSet<String>>> it = methodCalls.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, HashSet<String>> pair = (Map.Entry<String, HashSet<String>>)it.next();
			String caller = pair.getKey(); 
			HashSet<String> hsCallees = pair.getValue();
			for(String cStr : hsCallees){
				//System.out.println(caller + " --> "+ cStr);
				this.mm.addCallee(caller, cStr);
			}
			//it.remove(); 
		}
	}



	/**
	 * update DB usage info to methodManager
	 * @param methodQueriesMap
	 */
	public void updateDateDBusageToMM(HashMap<String, MethodAPICallVO> methodQueriesMap){

		Iterator<Entry<String, MethodAPICallVO>> it = methodQueriesMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, MethodAPICallVO> pair = (Map.Entry<String, MethodAPICallVO>)it.next();
			String methodFullName = pair.getKey(); 
			MethodAPICallVO methodQuery = pair.getValue();
			this.mm.updateDBUsage(methodFullName, methodQuery);
			//System.out.println("full name : " + methodFullName + "methodQuery " + methodQuery.toString());
		}
	}




	/**
	 * run DBScribe
	 */
	public void analyze(HashMap<String, HashSet<String>> methodCalls, HashMap<String, MethodAPICallVO> methodQueriesMap, HashSet<String> allMethods){

		//1. store all method into methodManager
		saveAllMethodToMM(allMethods);


		//2. update method Calls info
		updateMethodCallToMM(methodCalls);


		//3. update DB usage info to methodManager
		updateDateDBusageToMM(methodQueriesMap);

		//for debug
		//this.mm.printAllMethod();



		//4. build the level graph
		az.buildLevelGraph(this.mm);
		
		//for testing
		//az.printLevelMap();
		//calleeAndcallerTest();


	}


	public void calleeAndcallerTest(){
		//testing
		System.out.println("\n*********** calleeAndcallerTest ********************* ");
		
		HashSet<Method> hs =  mm.getAllMethods();
		System.out.println("num of all Methods  : "  + hs.size());

		System.out.println("=========== ");
		
		for(Method m : hs){
			ArrayList <ArrayList <Method>> pathsToDB = az.findCalleeListToDB(m);
			System.out.println("callee list of " + m.getMethodName());
			for(ArrayList <Method> curPath : pathsToDB){
				for(Method mInPath : curPath){
					System.out.print(mInPath.getMethodName() + "-->" );
				}
				System.out.println("\n");
			}
		}
		
	
		System.out.println("\n=========== ");
		for(Method m : hs){
			ArrayList <ArrayList <Method>> pathsFromM = az.findCallerList(m);
			System.out.println("caller list of " + m.getMethodName());
			for(ArrayList <Method> curPath : pathsFromM){
				for(Method mInPath : curPath){
					System.out.print(mInPath.getMethodName() + "<--" );
				}
				System.out.println("");
			}
		}
	}
}
