package edu.semeru.wm.qextractor.helper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class MethodCallVisitor extends ASTVisitor{

	private HashMap<String, HashSet<String>> calls;

	public MethodCallVisitor(){
		calls = new HashMap<String, HashSet<String>>();
	}
	
	public boolean visit(MethodInvocation call){
		
		
		
		ITypeBinding bind = null;
		ITypeBinding parentBind = null;
		
		String className = null;
		
		String methodName = call.getName().getFullyQualifiedName();
		String methodArgs = "";
		Expression temp = null;
		SingleVariableDeclaration temp2 = null;
		
		List args = ((MethodInvocation)call).arguments();
		int calleeArgsSize = args.size();
		
		if(args != null){
			for (Object expr : args) {
				temp = (Expression)expr;
				try{
					methodArgs += temp.resolveTypeBinding().getName()+",";
				}catch(Exception ex){
					methodArgs += "*,";
				}
			}
		}
		
		if(methodArgs.endsWith(",")){
			methodArgs = methodArgs.substring(0, methodArgs.length()-1);
		}
		try{
			bind = ((MethodInvocation)call).getExpression().resolveTypeBinding();
			if(bind.getQualifiedName().startsWith("java")){
				return true;
			}
		}catch(Exception ex){
			bind = null;
		}
		
		
		ASTNode parent = call;
		
		ASTNode parentClass = null;
		
		
		String callerMethodName= null;
		String callerClassName= null;
		String callerArgs = "";
		int callerArgsSize = 0;
		
		while(true){
			parent = parent.getParent();
			if(parent == null){
				return true;
			}
			if(parent instanceof MethodDeclaration){
				callerMethodName = ((MethodDeclaration)parent).getName().getFullyQualifiedName();
				
				args = ((MethodDeclaration)parent).parameters();
				
				if(args != null){
					callerArgsSize = args.size();
					for (Object expr : args) {
						temp2 = (SingleVariableDeclaration)expr;
						try{
							callerArgs += temp2.getType().resolveBinding().getName()+",";
							
						}catch(Exception ex){
							callerArgs += "*,";
						}
					}
					
					if(callerArgs.endsWith(",")){
						callerArgs = callerArgs.substring(0, callerArgs.length()-1);
					}
				}
				break;
			}
		}
		
		
		
		
		
		parentClass = parent;
		
		
		
		while(true){
			parentClass = parentClass.getParent();
			if(parentClass instanceof TypeDeclaration){
				parentBind = ((TypeDeclaration)parentClass).resolveBinding();
				if(parentBind != null){
					callerClassName = parentBind.getQualifiedName();
				}
				else{
					callerClassName = ((TypeDeclaration)parentClass).getName().getFullyQualifiedName();
				}
				break;
			}else if(parentClass instanceof EnumDeclaration){
				parentBind = ((EnumDeclaration)parentClass).resolveBinding();
				if(parentBind != null){
					callerClassName = parentBind.getQualifiedName();
				}
				else{
					callerClassName = ((EnumDeclaration)parentClass).getName().getFullyQualifiedName();
				}
				break;
			}
		}
		
		if(bind == null){
			className = callerClassName;
		}else{
			className = bind.getQualifiedName();
			
		}
		
		String key = callerClassName+"|"+callerMethodName+"|"+callerArgs;
		String value = className+"|"+methodName+"|"+methodArgs;
		
		//System.out.println(key+"-->"+value);
		
		if(!calls.containsKey(key)){
			calls.put(key, new HashSet<String>());
		}
		calls.get(key).add(value);
		return true;
	}

	public HashMap<String, HashSet<String>> getCalls() {
		return calls;
	}

	
	
	
	

}
