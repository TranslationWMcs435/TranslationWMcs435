package edu.semeru.wm.CallGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.semeru.wm.qextractor.processors.APIAnalyzer;


public class Analyzer {

	//To Do 1: need a level tracker  <Method, depth> and use depth to bounded loop
	//keep track the highest one

	/**
	 * 
	 */
	LevelGraph lg = new LevelGraph(); 

	
	/**
	 * record levels
	 */
	HashMap <Method, Integer> levelMap = new HashMap <Method, Integer>();

	
	//mm storage
	MethodManager mm;

	//for return callee and caller paths. 
	ArrayList <ArrayList <Method>> calleeList;
	ArrayList <ArrayList <Method>> callerList;


	/**
	 * constructor 
	 */
	public Analyzer(){
	}



	/**
	 * build the level graph based on existing info
	 */
	public void buildLevelGraph(MethodManager mm){
		this.mm = mm;
		//the leaf nodes
		HashSet<Method>  allMethods = mm.getAllMethods();
		for(Method m: allMethods){
			if(m.getHasDBusage() == true){
				lg.addNode(m);
				lg.addBotLevNode(m);
				levelMap.put(m, 0);
			}
		}

		boolean hasChange = true;
		while(hasChange){
			hasChange = false;
			for(Method m: allMethods){
				HashSet<String> curCallees = m.getfuncCalls();
				for(String callee: curCallees){
					Method calleeM = mm.getMethodByFullName(callee);

					if(!lg.containsMethod(calleeM)){
						continue;
					}else{
						boolean addNew = lg.addNode(m);
						//if it's a new node, we set its level to 0
						if(addNew == true){
							levelMap.put(m, 0);
						}

						int calleeLvl = levelMap.get(calleeM);
						int curLvl = levelMap.get(m);
						curLvl = Math.max(curLvl, calleeLvl+1);
						if(curLvl > APIAnalyzer.LEVELTHRESHOLD){
							return;
						}
						levelMap.put(m, curLvl);
						boolean localChange1 = lg.addCalleeEdge(m, calleeM);
						boolean localChange2 = lg.addCallerEdge(m, calleeM);
						if(localChange1 || localChange2){
							hasChange = true;
						}

					}
				}
			}
			//lg.lgPrint();
			System.out.println("*************");
		}
		//lg.lgPrint();

	}


	



	/**
	 * given m return the paths from m to the db access
	 * @param m
	 * @return
	 */
	public ArrayList <ArrayList <Method>> findCalleeListToDB(Method m){
		calleeList = new ArrayList <ArrayList <Method>>();
		if(!lg.containsMethod(m)){
			return calleeList;
		}
		//System.out.println("pos 1");
		//System.out.println(m.getMethodName());
		ArrayList <Method> path = new ArrayList <Method>();
		findCalleeListToDBHelper(m, new ArrayList <Method>(path), 0);
		//print the list
		//System.out.println("callee list of " + m.getMethodName());
		//for(ArrayList <Method> al : calleeList){
		//	for(Method met : al){
		//		System.out.print(met.getMethodName() +  "----");
		//	}
		//	System.out.println("");
		//}
		//end---
		return calleeList;
	}
	
	
	/**
	 * given method full name,  return the paths from m to the db access
	 * @param m
	 * @return
	 */
	public ArrayList <ArrayList <Method>> findCalleeListToDBByName(String m){
		Method currentM = this.mm.getMethodByFullName(m);
		return findCalleeListToDB(currentM);
	}


	/**
	 * Recursive function call
	 * @param m
	 * @param path
	 * @param level
	 */
	private void findCalleeListToDBHelper(Method m, ArrayList <Method> path, int level){
		if(m == null){
			return;
		}
		if(level > APIAnalyzer.LEVELTHRESHOLD){
			return;
		}
		path.add(m);

		if(m.getHasDBusage() == true)
		{
			calleeList.add(path);
			return;
		}
		else
		{
			ArrayList <Method> curCalleeList = lg.returnCallee(m);
			for(Method calleeM : curCalleeList){
				findCalleeListToDBHelper(calleeM, new ArrayList <Method>(path), level + 1);
			}	    
		}   
	}






	/**
	 * given m return the paths from m to top level callers (bottom up)
	 * @param m
	 * @return
	 */
	public ArrayList <ArrayList <Method>> findCallerList(Method m){
		callerList = new ArrayList <ArrayList <Method>>();
		if(!lg.containsMethod(m)){
			return callerList;
		}
		//System.out.println(m.getMethodName());
		ArrayList <Method> path = new ArrayList <Method>();
		findCallerListToDBHelper(m, new ArrayList <Method>(path), 0);
		//print the list
		//		System.out.println("caller list of " + m.getMethodName());
		//		for(ArrayList <Method> al : callerList){
		//			for(Method met : al){
		//				System.out.print(met.getMethodName() +  "----");
		//			}
		//			System.out.println("");
		//		}
		//		//end---
		return callerList;
	}

	
	/**
	 * given method full name,  return the paths from m to its top level callers.
	 * @param m
	 * @return
	 */
	public ArrayList <ArrayList <Method>> findCallerListByName(String m){
		Method currentM = this.mm.getMethodByFullName(m);
		return findCallerList(currentM);
	}

	
	
	
	/**
	 * Recursive function call
	 * @param m
	 * @param path
	 * @param level
	 */
	private void findCallerListToDBHelper(Method m, ArrayList <Method> path, int level){
		if(m == null){
			return;
		}
		if(level > APIAnalyzer.LEVELTHRESHOLD){
			return;
		}
		path.add(m);

		ArrayList <Method> curCallerList = lg.returnCaller(m);
		//System.out.println("caller list size :" +curCallerList.size());
		if(curCallerList.size() == 0)
		{
			callerList.add(path);
			return;
		}
		else
		{
			for(Method callerM : curCallerList){
				findCallerListToDBHelper(callerM, new ArrayList <Method>(path), level + 1);
			}	    
		}   
	}






	public HashMap<String, Integer> getLevelMapWithStringKey(){
		HashMap <String, Integer> hm = new HashMap<String, Integer> ();  // level to numOfethod
		for(Map.Entry<Method, Integer> entry : this.levelMap.entrySet()){
			String cName = entry.getKey().getClassName();
			String mName = entry.getKey().getMethodName();
			int level = entry.getValue();
			
			hm.put(cName+"."+ mName, level);
		}
		
		return hm;
	}



	public void printLevelMap(){
		HashMap <Integer, Integer> hm = new HashMap<Integer, Integer> ();  // level to numOfethod
		for(Map.Entry<Method, Integer> entry : this.levelMap.entrySet()){
			String cName = entry.getKey().getClassName();
			String mName = entry.getKey().getMethodName();
			int level = entry.getValue();
			//print

			System.out.println(cName+"."+ mName + "  /   " + level);

			Integer val =  hm.get(level);
			if(val == null){
				hm.put(level, 1);
			}else{
				val++;
				hm.put(level, val);
			}

		}

		Iterator<Entry<Integer, Integer>> it = hm.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> pair = (Map.Entry<Integer, Integer>)it.next();
			System.out.println(  "highest level  " + pair.getKey() + "  ---  " + " Number of methods  " + pair.getValue());
			//it.remove(); // avoids a ConcurrentModificationException
		}

		System.out.println("number of method related with DB : " + levelMap.size());

	}

}
