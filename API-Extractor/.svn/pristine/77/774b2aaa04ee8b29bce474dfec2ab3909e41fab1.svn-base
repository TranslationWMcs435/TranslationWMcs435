package edu.semeru.wm.qextractor.processors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.semeru.wm.CallGraph.Analyzer;
import edu.semeru.wm.CallGraph.LeveledCallGraphComponent;
import edu.semeru.wm.CallGraph.Method;


import edu.semeru.wm.qextractor.model.APICallVO;
import edu.semeru.wm.qextractor.model.MethodAPICallVO;

public class APIAnalyzer {

	/**
	 * depth constraint of analysis
	 */
	public static final int LEVELTHRESHOLD = 15;
	
	
	public static void printDetectionList(String systemFolder, String outputFolder, String libs4AstFolder) throws IOException{
		System.out.println("DBScribe is running");
		System.out.println("1. Running JDBCProcessor");
		SourceCodeProcessor processor = new SourceCodeProcessor();
		processor.processFolder(systemFolder, libs4AstFolder);
		System.out.println("--- JDBC processing: DONE");
		//----------------------------------------------------------------------------
		//*Step 2: extract partial graph including only call-chains related to db operations
		// The key is the caller signature, and the value is a set of callees (signatures).
		HashMap<String, HashSet<String>> methodCalls = processor.getMethodCalls();
		//The key is a method signature,and the value is a VO object with all the information
		// of the queries/statements declared in that method.
		HashMap<String, MethodAPICallVO> methodAPICallsMap = processor.getMethodAPICallsMap();
		
		HashSet<String> allMethods = new HashSet<String>();
		allMethods = processor.getAllMethods();
		Iterator<Entry<String, HashSet<String>>> it = methodCalls.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, HashSet<String>> pair = (Map.Entry<String, HashSet<String>>)it.next();
			String caller = pair.getKey(); 
			allMethods.add(caller);
			HashSet<String> hsCallees = pair.getValue();
			for(String cStr : hsCallees){
				allMethods.add(cStr);
			}
		}

		System.out.println("2. Running Callgraph extractor");
		
		//Boyang's part that returns the partial call graph
		LeveledCallGraphComponent lcgComponent = new LeveledCallGraphComponent();
		lcgComponent.analyze(methodCalls, methodAPICallsMap, allMethods);
		Analyzer analyzer = lcgComponent.getAz();
		System.out.println("--- Callgraph extraction: DONE");
		
		printCallsSummary(outputFolder, methodAPICallsMap, analyzer);
		
		
	}


	
	
	
	public static void run(String sourceCodeFolder, String outputFolder, String libs4ASTFolder) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		
		// *Step 1: Process source code to (i) extract couples (caller-->callee), 
		// (ii) locate methods using sql queries/statements, and (iii) parse the sql queries/statements
		System.out.println("APICalls analyser is running");
		System.out.println("1. Running APIProcessor");
		SourceCodeProcessor processor = new SourceCodeProcessor();
		
		processor.processFolder(sourceCodeFolder, libs4ASTFolder);
		
		System.out.println("--- API processing: DONE");
		//----------------------------------------------------------------------------
		
		
		
		//*Step 2: extract partial graph including only call-chains related to db operations
		
		// The key is the caller signature, and the value is a set of callees (signatures).
		HashMap<String, HashSet<String>> methodCalls = processor.getMethodCalls();
		
		//The key is a method signature,and the value is a VO object with all the information
		// of the queries/statements declared in that method.
		HashMap<String, MethodAPICallVO> methodAPICallsMap = processor.getMethodAPICallsMap();
		
		HashSet<String> allMethods = new HashSet<String>();
		System.out.println("number of methods calling at least one method: " + methodCalls.size());

		allMethods = processor.getAllMethods();
		Iterator<Entry<String, HashSet<String>>> it = methodCalls.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, HashSet<String>> pair = (Map.Entry<String, HashSet<String>>)it.next();
			String caller = pair.getKey(); 
			allMethods.add(caller);
			HashSet<String> hsCallees = pair.getValue();
			for(String cStr : hsCallees){
				allMethods.add(cStr);
			}
		}

		System.out.println("2. Running Callgraph extractor");
		
		//Boyang's part that returns the partial call graph
		LeveledCallGraphComponent lcgComponent = new LeveledCallGraphComponent();
		lcgComponent.analyze(methodCalls, methodAPICallsMap, allMethods);
		Analyzer analyzer = lcgComponent.getAz();
		printCallsSummary(outputFolder, methodAPICallsMap, analyzer);
		
		System.out.println("--- Callgraph extraction: DONE");
		
		
		
    	//----------------------------------------------------------------------------
    	// *Step 3: Print paths from partial call graph
    	APICallsPropagator qp = new APICallsPropagator();
    	qp.printPaths(methodAPICallsMap, analyzer, outputFolder);
 	
    	
    	
	}
	
	
	private static void printCallsSummary(String outputFolder,
			HashMap<String, MethodAPICallVO> methodAPICallsMap,
			Analyzer analyzer) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFolder+File.separator+"api-calls.csv"));
		writer.write("Class,Method,Level,[APICall]");
		writer.newLine();
		Set<String> keys = methodAPICallsMap.keySet();
		MethodAPICallVO vo = new MethodAPICallVO();
		ArrayList <ArrayList <Method>> callers = null;
		int depth = 0;
		List<APICallVO> apiCalls = null;
		String methodData[] = null;
		for(String key:keys){
			vo = methodAPICallsMap.get(key);
			callers = analyzer.findCallerListByName(key);
			depth = 0;
			for(ArrayList <Method> path : callers){
				if(path.size() > depth){
					depth = path.size();
				}
			}
			methodData = key.split("\\|");
			apiCalls = vo.getAPICalls();
			for (APICallVO call : apiCalls) {
				writer.write(methodData[0]+","+methodData[1]+","+depth+",["+call.getCall().replace("\n", " ")+"]");
				writer.newLine();	
			}
			
			
		}
		writer.close();
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		String systemFolder =null;
		
		String outputFolder =null;
		String libs4ASTFolder = null;
		
		//String outputFile = "/Users/mariolinares/Documents/academy/SEMERU/Code-tools/Q-Extractor/output/text.html";
		//String sourceCodeFolder = "/Users/mariolinares/Documents/academy/2015/ICSE-Android-test/study/source-code/com.evancharlton.mileage";
		//String libs4ASTFolder = "/Users/mariolinares/Documents/academy/SEMERU/Code-tools/API-Extractor/libs4ast";
		
		if(args[0].equals("-r")){
			systemFolder = args[1];
			outputFolder = args[2];
			libs4ASTFolder = args[3];
		
			APIAnalyzer.run(systemFolder, outputFolder, libs4ASTFolder);
		}
		else if(args[0].equals("-p")){
			systemFolder = args[1];
			outputFolder = args[2];
			libs4ASTFolder = args[3];
			
			APIAnalyzer.printDetectionList(systemFolder, outputFolder, libs4ASTFolder );
		}
			

	}

}
