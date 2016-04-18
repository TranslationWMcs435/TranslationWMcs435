package edu.semeru.wm.CallGraph;

import java.util.ArrayList;
import java.util.HashSet;

import edu.semeru.wm.qextractor.model.MethodAPICallVO;

/**
 * Method entity to represent a method
 * @author Boyang
 *
 */
public class Method {
	
	
	private String className = "";
	private String methodName = "";
	//private String methodSpecifier = "";
	private String methodArgs = "";
	private boolean hasDBusage = false;  // has DBusage in Current method
	
	
	//we use MethodQueryVO record DBInfo 
	private MethodAPICallVO dbInfo = new MethodAPICallVO();
	
	
	
	
	
	private HashSet<String> funcCalls = new HashSet <String>(); 
	
	
	/**
	 * class constructor. create a method belong to classBelong
	 */
	public Method(String cName, String mName,  String args){
		this.className = cName;
		this.methodName = mName;
		this.methodArgs = args;
	}
	
	
	
	
	public Method() {
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
	
	/**
	 * return class name
	 * @return
	 */
	public String getClassName(){
		return this.className;
	}
	

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	

	
//	/**
//	 * @return the methodSpecifier
//	 */
//	public String getMethodSpecifier() {
//		return methodSpecifier;
//	}
//	

	/**
	 * return number of args
	 * @return
	 */
	public String getMethodrgs(){
		return this.methodArgs;
	}
	
	

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	
	
//	/**
//	 * @param methodSpecifier the methodSpecifier to set
//	 */
//	public void setMethodSpecifier(String methodSpecifier) {
//		this.methodSpecifier = methodSpecifier;
//	}
//	
	
	
	public void setMethodArgs(String methodArgs){
		this.methodArgs = methodArgs;
	}
	
	
	
	/**
	 * setter boolean DBusage 
	 * @param b
	 */
	public void setHasDBusage(boolean b) {
		this.hasDBusage = b;
	}
	
	
	
	/**
	 * getter boolean DBusage
	 * @return
	 */
	public boolean getHasDBusage() {
		return hasDBusage;
	}
	
	
	
	/**
	 * temporarily record the info
	 * @param MethodAPICallVO mvo
	 */
	public void setDBusageInfo(MethodAPICallVO mvo) {
		this.dbInfo = mvo; 
	}
	
	
	
	/**
	 * return the database info
	 */
	public MethodAPICallVO getDBusageInfo() {
		return this.dbInfo; 
	}
	
	
	
	/**
	 * add function call cs in this method
	 * @param cs
	 */
	public void addfuncCall(String fc){
		this.funcCalls.add(fc);
	}
	
	
	
	public HashSet <String> getfuncCalls(){
		return this.funcCalls;
	}
	



	

	
	public String toString(){
		String str_ret = "";
		str_ret += this.className+ "." + this.methodName +"("+methodArgs+")";
		
		return str_ret;
		
	}
	
	

}
