package edu.semeru.wm.qextractor.model;

import java.util.ArrayList;
import java.util.List;

public class MethodAPICallVO {

	private String name;
	private int nArgs;
	private String key;
	private String signature;
	private List<APICallVO> apiCalls;
	
	
	private List<String> calledMethods;
	
	
	public MethodAPICallVO() {
		apiCalls = new ArrayList<APICallVO>();
		calledMethods = new ArrayList<String>();
		
	}
	
	
	
	public void addAPICall (APICallVO vo){
		apiCalls.add(vo);
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getnArgs() {
		return nArgs;
	}

	public void setnArgs(int nArgs) {
		this.nArgs = nArgs;
	}

	public List<APICallVO> getAPICalls() {
		return apiCalls;
	}

	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public void addCalledMethod(String method){
		this.calledMethods.add(method);
	}

	public List<String> getCalledMethods() {
		return calledMethods;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
	
	public String getAPICallsStr(){
		StringBuffer buff = new StringBuffer();
		buff.append("[");
		for (APICallVO call: apiCalls) {
			buff.append(call.getCall()+"|");
			
		}
		buff.append("]");
		return buff.toString();
	}
	
}
