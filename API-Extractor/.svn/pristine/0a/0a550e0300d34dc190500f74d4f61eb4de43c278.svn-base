package edu.semeru.wm.qextractor.processors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.semeru.wm.CallGraph.Analyzer;

import edu.semeru.wm.CallGraph.Method;
import edu.semeru.wm.qextractor.model.APICallVO;
import edu.semeru.wm.qextractor.model.MethodAPICallVO;
public class APICallsPropagator {

	public void printPaths(HashMap<String, MethodAPICallVO> methodAPICallsMap,
			Analyzer analyzer, String outputFolder) throws IOException{
		ArrayList<ArrayList<Method>> callgraph = null;
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFolder+File.separator+"calls-propagation.csv"));
		writer.write("[APICall]:Call-chain");
		writer.newLine();
		
		
		Set<String> methodKeys = methodAPICallsMap.keySet();
		ArrayList<Method> path = null;
		
		MethodAPICallVO macvo = null;
		StringBuffer callChain = null;
		for(String key: methodKeys){
			
			
			
			if(!key.isEmpty()){ 
				callgraph = analyzer.findCallerListByName(key);
				for(int i = 0; i < callgraph.size(); i++){
					path = callgraph.get(i);
					
					macvo = methodAPICallsMap.get(key);
					
					callChain = new StringBuffer();
					callChain.append("[").append(path.get(0)).append("]");
					
					for(int level = 1; level < path.size(); level++){
						callChain.append("<--[").append(path.get(level)).append("]");
					}
					
					for(APICallVO acVO: macvo.getAPICalls()){
						writer.write("["+acVO.getCall().replace("\n", " ")+"]:");
						writer.write(callChain.toString());
						writer.newLine();
					}
						
					
				}
				
			}
			
		}
		
		writer.close();
		
	}

}
