package edu.semeru.wm.qextractor.model;

import java.util.ArrayList;
import java.util.List;

public class APICallVO {

	private String call;
	private String method;
	private String className;
	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public boolean equals(Object obj) {
		APICallVO vo = (APICallVO)obj;
		if( getCall().equals(vo.getCall())){
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		
		return call.hashCode();
	}
	
	
	 
	
	
	

}
