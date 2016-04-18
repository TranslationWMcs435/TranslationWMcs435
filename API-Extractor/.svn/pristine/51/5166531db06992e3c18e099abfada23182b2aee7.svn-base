package edu.semeru.wm.CallGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



public class LevelGraph {

	ArrayList <Method> nodes = new  ArrayList <Method>();
	HashMap<Method, Integer> index = new HashMap <Method, Integer>();

	/**
	 * size of current graph
	 */
	private int size = 0;


	/**
	 * record all callees
	 */
	ArrayList <HashSet <Integer>> calleeEdge = new ArrayList <HashSet <Integer>> ();

	ArrayList <HashSet <Integer>> callerEdge = new ArrayList <HashSet <Integer>> ();
	
	//record bottom level note (methods that call SQL directly)
	ArrayList <Method> botLevelNodes = new ArrayList <Method>();





	/**
	 * add a node to the level graph and build a index
	 * @param m
	 */
	public boolean addNode(Method m){
		if(!index.containsKey(m)){
			this.nodes.add(m);
			this.index.put(m, size++);
			this.calleeEdge.add(new HashSet <Integer>());
			this.callerEdge.add(new HashSet <Integer>());
			return true;
		}else{
			return false;
		}
	}

	
	
	public void addBotLevNode(Method m){
		this.botLevelNodes.add(m);
	}




	/**
	 * @param m_caller
	 * @param m_callee
	 * @return true if this set did not already contain the specified element
	 */
	public boolean addCalleeEdge(Method m_caller,  Method m_callee) {
		int colPos = index.get(m_caller);
		int linkedNodePos = index.get(m_callee);
		HashSet <Integer> hs = calleeEdge.get(colPos);
		return hs.add(linkedNodePos);
	}


	
	
	/**
	 * @param m_caller
	 * @param m_callee
	 * @return true if this set did not already contain the specified element
	 */
	public boolean addCallerEdge(Method m_caller,  Method m_callee) {
		int colPos = index.get(m_callee);
		int linkedNodePos = index.get(m_caller);
		HashSet <Integer> hs = callerEdge.get(colPos);
		return hs.add(linkedNodePos);
	}
	
	
	
	/**
	 * return a list of m's callee
	 * @param m
	 * @return
	 */
	public ArrayList<Method> returnCallee(Method m) {
		ArrayList<Method> ret_al = new ArrayList<Method>();
		int colPos = index.get(m);
		HashSet <Integer> hs = calleeEdge.get(colPos);
		for(Integer i : hs){
			ret_al.add(nodes.get(i));
		}
		return ret_al;
		
	}
	
	
	/**
	 * return a list of m's caller
	 * @param m
	 * @return
	 */
	public ArrayList<Method> returnCaller(Method m) {
		ArrayList<Method> ret_al = new ArrayList<Method>();
		int colPos = index.get(m);
		HashSet <Integer> hs = callerEdge.get(colPos);
		for(Integer i : hs){
			ret_al.add(nodes.get(i));
		}
		return ret_al;
		
	}

	



	/**
	 * @param m
	 * @return true if this levelgraph contains a node for the method m.
	 */
	public boolean containsMethod(Method m){
		return index.containsKey(m);
	}




	public void lgPrint(){
		System.out.println("all nodes" );
		for(int i = 0; i < nodes.size(); i++){
			System.out.println("ID " + i + " : " + nodes.get(i).getMethodName());
		}
		System.out.println("calleeEdge : " );
		for(int i = 0; i < calleeEdge.size(); i++){
			System.out.print(i  + " --- ");

			HashSet <Integer> hs = calleeEdge.get(i);
			if(hs.size() == 0){
				System.out.print("SQL access");
			}
			for(Integer ind : hs){
				System.out.print(nodes.get(ind).getMethodName() +" ;  ");
			}
			System.out.println("");
		}
	}


}
