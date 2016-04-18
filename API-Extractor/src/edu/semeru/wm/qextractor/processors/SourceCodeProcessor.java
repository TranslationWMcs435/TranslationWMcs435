package edu.semeru.wm.qextractor.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import edu.semeru.wm.qextractor.helper.ASTHelper;

import edu.semeru.wm.qextractor.model.MethodAPICallVO;

import edu.semeru.wm.qextractor.model.APICallVO;


public class SourceCodeProcessor {
	
	
	private HashSet<String> apiCalls;
	
	private HashMap<String, HashSet<String>> methodCalls;
	private HashSet<String> allMethods;
	private HashSet<String> targetedApis;
	

	private HashMap<String, MethodAPICallVO> methodAPICallsMap;
	
	public void processFolder(String folderPath, String binariesFolder){
		methodCalls = new HashMap<String, HashSet<String>>();
		allMethods = new HashSet<String>();
		methodAPICallsMap = new HashMap<String, MethodAPICallVO>();
		Collection<File> files = FileUtils.listFiles(new File(folderPath), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File file : files) {
			if(file.getName().endsWith(".java")){
				processFile(file.getAbsolutePath(), folderPath, binariesFolder);
			}
		}
		
	}
	
	private void processFile(String filePath, String projectPath, String binariesFolder){
		BufferedReader reader = null;
		StringBuffer source = new StringBuffer();
		List<String> lines = new ArrayList<String>();
		String unitName = filePath.substring(filePath.lastIndexOf(File.separator)+1).replace(".java", "");
		Block block = null;
		List<Statement> statements = null;
		
		String pckName = null;
		String className = null;
		int methodDeclarationArgs = 0;
		String methodArgsType = null;
		String strStatement = null;
		HashSet<String> tables = null;
		List<Column> columns = null;
		MethodAPICallVO methodAPICallVO = null;
		APICallVO apiCallVO = null;
		List tempArray = null;
		String methodSignature = null;
		
		targetedApis = new HashSet<String>();
		targetedApis.add("android.hardware.SensorManager");
		targetedApis.add("android.hardware.Sensor");
		targetedApis.add("android.hardware.SensorEventListener");
		targetedApis.add("android.hardware.SensorEvent");
		targetedApis.add("android.net.ConnectivityManager");
		targetedApis.add("android.location.LocationManager");
		targetedApis.add("android.media.MediaRecorder");
		targetedApis.add("android.net.wifi.WifiManager");
		targetedApis.add("java.net.HttpURLConnection");
		targetedApis.add("java.net.HttpsURLConnection");
		targetedApis.add("org.apache.http.client.HttpClient");
		targetedApis.add("android.net.http.AndroidHttpClient");
		targetedApis.add("org.apache.http.impl.client.DefaultHttpClient");
		
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = null;
			while((line = reader.readLine()) != null){
				lines.add(line);
				source.append(line).append("\n");
			}
			reader.close();
			
			CompilationUnit cu = ASTHelper.getASTAndBindings(source.toString(), projectPath,binariesFolder, unitName);
			
			IProblem[] problems = cu.getProblems();

			for (IProblem problem : problems) {
				System.err.println(problem.toString());
			}
			if(cu.getPackage() != null){
				pckName = cu.getPackage().getName().toString();
			}
			else{
				pckName = "";
			}
			List<MethodDeclaration> methods = ASTHelper.getMethodDeclarationsFromCU(cu);
			
			methodCalls.putAll(ASTHelper.getMethodCallsFromCU(cu));
			for (MethodDeclaration methodDeclaration : methods) {
				methodArgsType = "";
				methodAPICallVO = new MethodAPICallVO();
				//Javadoc comment = methodDeclaration.getJavadoc();
			    //commentStr = "";
				//if(comment != null){
				//	commentStr = comment.toString().replace("/**", "").replace("*/", "").replace("*", " ").replace("\n", " ").trim();
				//}
				
				methodDeclarationArgs = 0;
				tempArray = methodDeclaration.parameters();
				if(tempArray != null){
					methodDeclarationArgs = tempArray.size();
					for(Object arg: tempArray){
						try{
							methodArgsType += ((SingleVariableDeclaration)arg).getType().resolveBinding().getQualifiedName()+",";
						}catch(Exception ex){
							methodArgsType += "*,";	
						}
					}
					if(!methodArgsType.isEmpty()){
						methodArgsType = methodArgsType.substring(0,methodArgsType.length()-1);
					}
					
				}
				if(methodDeclaration.getParent() instanceof TypeDeclaration){
					className = ((TypeDeclaration)methodDeclaration.getParent()).getName().toString();
				}else if(methodDeclaration.getParent() instanceof EnumDeclaration){
					className = ((EnumDeclaration)methodDeclaration.getParent()).getName().toString();
				}
				
				methodSignature = pckName+"."+className+"|"+methodDeclaration.getName().getFullyQualifiedName()+"("+methodArgsType+")";
				
				
				methodAPICallVO.setSignature(methodSignature);
				methodAPICallVO.setKey(pckName+"."+className+"|"+methodDeclaration.getName().getFullyQualifiedName()+"|"+methodArgsType);
				methodAPICallVO.setName(methodDeclaration.getName().getFullyQualifiedName());
				methodAPICallVO.setnArgs(methodDeclarationArgs);
				
				allMethods.add(methodAPICallVO.getKey());
				//TODO: AnonymousClassDeclaration
				
				apiCalls = new HashSet<String>();
				
				block = methodDeclaration.getBody();
				if(block != null){
					statements = block.statements();
					
					//Filter statements
					for (Statement stmt : statements) {
						getStatements(stmt);
					}
					
					for(String apiCall: apiCalls){
						columns = new ArrayList<Column>();
						apiCallVO = new APICallVO();
						apiCallVO.setCall(apiCall);
						
						
						methodAPICallVO.addAPICall(apiCallVO);
						
						
						
						//This is the output on console. Just for debugging purposes
						//System.out.println(
						//methodAPICallVO.getSignature()+"|"+
						//apiCall.replace("\n", " "));
						
						
						
						
					}
					if(!apiCalls.isEmpty()){
						methodAPICallsMap.put(methodAPICallVO.getKey(), methodAPICallVO);
					}
					
				}
				
				
				
			}
			
			
		} catch (FileNotFoundException e) {
			Logger.getLogger(SourceCodeProcessor.class.getName()).severe(
					" File not found " + filePath);


		} catch (IOException e) {
			Logger.getLogger(SourceCodeProcessor.class.getName()).severe(
					" Error reading/writing file" + filePath);
		}

		
	}
	
	

	private  List<Statement> getStatements(Statement stmt){
		List<Statement> statements = new ArrayList<Statement>();
		List<Statement> recursiveStmts = new ArrayList<Statement>();
		ITypeBinding bind = null;
		Type type = null;
		String typeStr =null;
		String methodName = null;
		String varName = null;
		Expression exprTemp = null;
		Expression exprTemp2 = null;
		List fragments = null;
		List arguments = null;
		String string2Add = null;
		
		if(stmt == null){
			return statements;
		}
		
		if(stmt instanceof EnhancedForStatement){
			statements.addAll(getStatements( ((EnhancedForStatement) stmt).getBody()));	
		}else if (stmt instanceof SwitchStatement){
			recursiveStmts = ((SwitchStatement) stmt).statements();
			for(Statement stm2: recursiveStmts){
				statements.addAll(getStatements(stm2));
			}
			
		}
		else if(stmt instanceof ForStatement){
			statements.addAll(getStatements( ((ForStatement) stmt).getBody()));	
		}else if(stmt instanceof DoStatement){
			statements.addAll(getStatements( ((DoStatement) stmt).getBody()));	
		}else if(stmt instanceof IfStatement){
			statements.addAll(getStatements( ((IfStatement) stmt).getThenStatement()));
			statements.addAll(getStatements( ((IfStatement) stmt).getElseStatement()));
		}else if(stmt instanceof TryStatement){
			statements.addAll(getStatements( ((TryStatement) stmt).getBody()));	
		}else if(stmt instanceof WhileStatement){
			statements.addAll(getStatements( ((WhileStatement) stmt).getBody()));	
		}else if(stmt instanceof Block){
			recursiveStmts = ((Block)stmt).statements();
			for(Statement blockStmt: recursiveStmts){
				statements.addAll(getStatements( blockStmt));
			}
		
		}else if( stmt instanceof VariableDeclarationStatement){
			
			type = ((VariableDeclarationStatement)stmt).getType();
		    typeStr = type.toString();
		    fragments = ((VariableDeclarationStatement)stmt).fragments();
			exprTemp = ((VariableDeclarationFragment)fragments.get(0)).getInitializer();
		
			varName = ((VariableDeclarationFragment)fragments.get(0)).getName().toString();
			
			if(exprTemp instanceof MethodInvocation){
				methodName =  ((MethodInvocation)exprTemp).getName().toString();
				try{
					bind = ((MethodInvocation)exprTemp).getExpression().resolveTypeBinding();
					
					
				}catch(Exception ex){
					bind = null;
				}
				if(bind !=null){	
					if( isValidBindType(bind)){
						extractAPICallLiteral((MethodInvocation)exprTemp);
						statements.add(stmt);
						//System.out.println(stmt);
					}
					
				}
			}
			
			
			
		}else if( stmt instanceof ExpressionStatement){
			exprTemp = ((ExpressionStatement)stmt).getExpression();
			
			
			if(exprTemp instanceof MethodInvocation){
				exprTemp2 = ((MethodInvocation)exprTemp).getExpression();
				
				if(exprTemp2 != null && exprTemp2.resolveTypeBinding()!=null){
					bind = exprTemp2.resolveTypeBinding();
					
					typeStr = bind.getName();
					methodName = ((MethodInvocation)exprTemp).getName().toString();
					
					
					if(isValidBindType(bind)){
						extractAPICallLiteral((MethodInvocation)exprTemp);
						statements.add(stmt);
						
						//System.out.println(stmt);
					}
					
				}
				
				
			}else if(exprTemp instanceof Assignment){
				
				
				
					exprTemp2 = ((Assignment)exprTemp).getRightHandSide();
					if(exprTemp2 instanceof MethodInvocation){
						try{
							methodName = ((MethodInvocation)exprTemp2).getName().toString();
							bind = ((MethodInvocation)exprTemp2).getExpression().resolveTypeBinding();
							
						}catch(Exception e){
							bind = null;
						}
						if(bind != null){
							typeStr = bind.getName();
							
							if(isValidBindType(bind)){
								extractAPICallLiteral((MethodInvocation)exprTemp2);
								statements.add(stmt);
								//System.out.println(stmt);
							}
							
							
						}
						
						
					}
					
				
			}	
			
		}
		
		
		return statements;
	}

	private boolean isValidBindType(ITypeBinding bind) {
		return (bind.getPackage()!= null &&  targetedApis.contains(bind.getBinaryName()))
				|| (bind.getSuperclass() != null && bind.getSuperclass().getPackage() != null &&
						targetedApis.contains(bind.getSuperclass().getBinaryName()));
	}

	private void extractAPICallLiteral(MethodInvocation expression) {
		apiCalls.add(expression.toString());
	}
	
	
	
	public HashMap<String, MethodAPICallVO> getMethodAPICallsMap() {
		return methodAPICallsMap;
	}

	
	
	
	
	

	public HashSet<String> getAllMethods() {
		return allMethods;
	}

	public HashMap<String, HashSet<String>> getMethodCalls() {
		return methodCalls;
	}

	public static void main(String[] args){
		SourceCodeProcessor processor = new SourceCodeProcessor();
		//processor.processFile("/Users/mariolinares/Documents/academy/SEMERU/Code-tools/Q-Extractor/examples4test/BugReportDAO.java");
		processor.processFolder("/Users/mariolinares/Documents/academy/2015/ICSE-Android-test/study/source-code/com.evancharlton.mileage","/Users/mariolinares/Documents/academy/SEMERU/Code-tools/API-Extractor/libs4ast");
		
		
		
	}

}
