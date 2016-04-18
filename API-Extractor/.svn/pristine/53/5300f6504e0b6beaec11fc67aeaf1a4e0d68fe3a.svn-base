package edu.semeru.wm.CallGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.semeru.wm.qextractor.model.MethodAPICallVO;


/**
 * store and manage all methods information
 * @author Boyang
 *
 */
public class MethodManager {
	
	/**
	 * Key is a method string key (package + class + method + numOfPara)
	 * Value is a method instance
	 * This is an index
	 */
	private HashMap <String, Method> map   =  new HashMap <String, Method> ();
	
	
	/**
	 * store all methods
	 */
	private HashSet<Method> allMethod = new  HashSet<Method>();
	
	
	
	/**
	 * return a method based on the given key - String
	 * key is the full name of the method
	 * @param key
	 * @return
	 */
	public Method getMethodByFullName(String key){
		 Method returnMethod =  map.get(key);
		 if(returnMethod!= null){
			 return returnMethod;
		 }else{
			 return new Method();
		 }
	}
	
	

	
	
	/**
	 * add method m into the list which stores all methods
	 * @param m
	 */
	public void addMethod(String m){
		Method methodList = map.get(m);
		if(methodList == null){
			//not exists
			String [] splitm= m.split("\\|");
			String className = splitm[0];
			
			
			String methodName = splitm[1];
			String args = "";
			if (splitm.length > 2){
				args = splitm[2];
			}
			Method newMethod = new Method(className, methodName, args);
			//update key
			this.map.put(m, newMethod);
			
			this.allMethod.add(newMethod);
		}
		
		
	}
	
	
	/**
	 * update caller instance for it's callee information
	 * @param caller
	 * @param callee
	 */
	public void addCallee(String caller, String callee){
		Method m = map.get(caller);
			m.addfuncCall(callee);
		
	}
	
	
	
	/**
	 * 
	 */
	public void updateDBUsage(String key, MethodAPICallVO mvo){
		Method m = map.get(key);
		m.setHasDBusage(true);
		m.setDBusageInfo(mvo);
		
	}
	
//	/**
//	 * import databases usage info to the methods
//	 * @param du
//	 */
//	public void ImportDBUsage(DatabaseUsage du){
//
//		for(DatabaseMethod dm : du.methodUsageList){
//			MethodKey mk = new MethodKey (dm.getMethodName(), dm.getNumPara());
//			ArrayList<Method> al = LeveledCallGraphComponent.mm.getMethodList(mk);
//			for(Method m: al){
//				Class curClass = m.getClassBelong();
//				if(curClass.getClassName().equals(dm.getClassName()) && 
//						curClass.getPackageName().equals(dm.getPackageName())){
//					m.setHasDBusage(true);
//					m.setDBusageInfo(dm.getDbInfo());
//				}
//			}
//		}
//	}
//	
	
	public HashSet<Method> getAllMethods(){
		return this.allMethod;
	}
	
	
	
	public void printAllMethod(){
		for(Method m : this.allMethod){
			System.out.println(m);
		}
		
	}
	

}
