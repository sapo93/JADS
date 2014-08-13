package DataStructures;

import java.util.ArrayList;

import Exceptions.AutomatonNotReadyException;
import Exceptions.GraphNotReadyException;

public abstract class Graph<T> {

	/** Nodes List */
	protected ArrayList<Node<T>> nodes;
	
	/** Archs List */
	protected ArrayList<Arch<T>> archs;
	
	/** Graph's Name */
	protected String name;
	
	
	
	/**
	 * Constructor That Creates An Empty Graph
	 * 
	 * @param name Graph Name
	 */
	public Graph(String name) {
		
		this.nodes = new ArrayList<Node<T>> ();
		
		this.archs = new ArrayList<Arch<T>> ();
		
		this.name = name;
		
		
	}

	
	
	/**
	 * Constructor That Creates The Graph
	 * 
	 * @param Nodes Nodes List
	 * @param Archs Archs List
	 * @param name Graph Name
	 * @throws GraphNotReadyException Graph Is Not Correctly Initialised
	 */
	public Graph (ArrayList<Node<T>> Nodes, ArrayList<Arch<T>> Archs, String name) throws GraphNotReadyException {
		
		this.nodes = Nodes;
		
		this.checkNodeDuplicates(this.nodes);
		
		this.archs = Archs;
		
		this.name = name;
		
		this.checkGraphReady(false);
		
	}
	
	
	
	/**
	 * Method To Add A Node To The Graph
	 * 
	 * @param Node Node To Add
	 * @throws GraphNotReadyException Graph Is Not Correctly Initialised
	 */
	public void addNode (Node<T> Node) throws GraphNotReadyException {
		
		this.checkGraphReady (false);

		if (!this.nodes.contains(Node)) {
			
			this.nodes.add(Node);
		
		}	
			
	}

	
	
	/**
	 * Method To Add An Arch To The Graph
	 * 
	 * @param Arch Arch To Add
	 * @throws GraphNotReadyException Graph Is Not Correctly Initialised
	 */
	public abstract void addArch (Arch<T> Arch) throws GraphNotReadyException;
		
	
	
	/**
	 * Check If The Node List Has Duplicates
	 * 
	 * @param Nodes Nodes List
	 * @throws GraphNotReadyException Duplicate Node In The Nodes List
	 */
	protected void checkNodeDuplicates (ArrayList<Node<T>> Nodes) throws GraphNotReadyException {
		
		if (Nodes != null) {
			
			Node<T> tmpNode;
			
			int duplicateNodeCounter = 0;
		
			for (int i = 0; i < Nodes.size(); i++) {
			
				duplicateNodeCounter = 0;
				
				tmpNode = Nodes.get(i);
				
				for (int j = 0; j < Nodes.size(); j++) {
					
					if (tmpNode.equals(Nodes.get(j))) {
						
						duplicateNodeCounter++;
						
					}
					
				}
				
				if (duplicateNodeCounter > 1) {
					
					throw new GraphNotReadyException ("Node " + tmpNode.toString() 
													+ " has " + duplicateNodeCounter 
													+ "entries in the nodeList!!!");
					
				}
			
			}
			
		}
		
	}

	
	
	/**
	 * Checks If The Arch Is Correct
	 * 
	 * @param arch Arch To Check
	 * @throws GraphNotReadyException Graph No Correctly Initialised
	 * 
	 */
	protected abstract boolean checkArch (Arch<T> arch) throws GraphNotReadyException;
	
	
	
	/**
	 * Checks If The Every Arch Is Correct
	 * By Calling checkArch On Every Single Arch
	 * And checkArchDuplicates To See If There Are Doubles
	 *  
	 * @throws AutomatonNotReadyException Wrong Archs
	 */
	protected void checkArchsList () throws GraphNotReadyException {
		
		for (int i = 0; i < this.archs.size(); i++) {
			
			if (!this.checkArch(this.archs.get(i))) {
				
				throw new GraphNotReadyException ("Wrong Archs!!!");
				
			}
			
		}
		
		this.checkArchDuplicates();
		
	}

	
	
	/**
	 * Check If The Archs List Has Duplicates
	 * 
	 * @throws GraphNotReadyException Duplicate Arch In The Archs List
	 */
	protected void checkArchDuplicates () throws GraphNotReadyException {
		
		if (this.archs != null) {
			
			Arch<T> tmpArch;
			
			int duplicateArchCounter = 0;
		
			for (int i = 0; i < this.archs.size(); i++) {
				
				duplicateArchCounter = 0;
				
				tmpArch = this.archs.get(i);
				
				for (int j = 0; j < this.archs.size(); j++) {
					
					if (tmpArch.equals(this.archs.get(j))) {
						
						duplicateArchCounter++;
						
					}
					
				}
				
				if (duplicateArchCounter != 1) {
					
					throw new GraphNotReadyException ("Arch " + tmpArch.toString() 
													+ " has " + duplicateArchCounter 
													+ "entries in the archList!!!");
					
				}
			
			}
			
		}
		
	}
	
	
	
	/**
	 * Check If The Graph Correctly Initialised
	 * 
	 * @param checkZero Flag Used To Check If The Graph Is NonEmpty
	 * @throws GraphNotReadyException Graph Is Not Correctly Initialised
	 */
	protected void checkGraphReady(boolean checkZero) throws GraphNotReadyException {
		
		if (this.nodes == null) {
			
			throw new GraphNotReadyException ("Nodes Null");
			
		} else {
			
			this.checkNodeDuplicates(this.nodes);
			
		}
		
		if (this.archs == null) {
			
			throw new GraphNotReadyException ("Archs Null");
			
		}
		
		if (checkZero) {
			
			if (this.nodes.size() == 0) {
				
				throw new GraphNotReadyException ("No Nodes In The Graph");
				
			}
			
			if ( this.archs.size() == 0) {
				
				throw new GraphNotReadyException ("No Archs In The Graph");
				
			} else {
				
				this.checkArchsList();
				
			}
			
		}
		
	}
	
	
	
	/**
	 * Finds All The Archs With Label label
	 * 
	 * @param label Label To Look For
	 * @return The Arraylist Containing All The Arch Wich Match The Lablel, Null If No Arch Found
	 * @throws GraphNotReadyException 
	 */
	public abstract ArrayList<Arch<T>> archsWithLabel (String label) throws GraphNotReadyException;
	
	
	
	/**
	 * Finds All The Archs Between Two Nodes
	 * 
	 * @param node1 First Node
	 * @param node2 Second Node
	 * @return The Arraylist Containing All The Arch, Null If No Arch Between The Two Nodes
	 */
	public abstract ArrayList<Arch<T>> archsBetweenNodes (Node<T> node1, Node<T> node2);
	
	
	
	/**
	 * Method To Find All The Archs Starting From node
	 * 
	 * @param node Starting Node
	 * @return The Archs List, Null If Empty
	 */
	public abstract ArrayList<Arch<T>> archsFromNode (Node<T> node);
	
	
	
	/**
	 * Method To Find All The Archs Ending At node
	 * 
	 * @param node End Node Node
	 * @return The Archs List, Null If Empty
	 */
	public abstract ArrayList<Arch<T>> archsToNode (Node<T> node);
	
	
	
	/**
	 * Method To Find If The Two Node Are Neighbor
	 * (Exists At Least An Arch BeetWen Them)
	 * 
	 * @param node1 First Node
	 * @param node2 Second Node
	 * @return True If The Nodes Are Neighborhood, False Otherwise
	 * 
	 */
	public boolean neighbor (Node<T> node1, Node<T> node2) {
		
		boolean result = false;
		
		if (this.archsBetweenNodes(node1, node2) != null) {
			
			result = true;
			
		}
				
		return result;
		
	}

	
	
	/**
	 * Method To Find All Of The Neighbors Of A Node In The Graph
	 * 
	 * @param node Node Of Whom Is To Be Found The Neighborhood
	 * @return Arraylist Containing All The Neighors, Null If None Or If The node Isn't Present In The Graph
	 */
	public ArrayList<Node<T>> neighborhood (Node<T> node) {
		
		ArrayList<Node<T>> neighborhood = new ArrayList<Node<T>>(); // Return Value
		
		if (node != null && this.nodes.contains(node)) { // node Mustn't Be Null And Contained In The Node List
			
			for (int i = 0; i < this.nodes.size(); i++) { // Look At Every Node
				
				if (this.neighbor(node, this.nodes.get(i))) { // The Nodes In The Pair Are Neighbor
							
					if (!neighborhood.contains(this.nodes.get(i))) { // No Duplicate Nodes
								
						neighborhood.add(this.nodes.get(i)); // Add The Node To The Neighborhood
								
					}					
					
				}
				
			}
			
		} else {
			
			neighborhood = null;
			
		}
		
		return neighborhood;
		
		
	}
	
	
	
	/**
	 * Check If The Graph Is Connected
	 * 
	 * @return True If The Graph Is Connected, False Otherwise
	 * @throws GraphNotReadyException Graph Not Correctly Initialised
	 */
	public boolean isConnected () throws GraphNotReadyException {
		
		boolean returnValue = true; // Return Value
		
		this.checkGraphReady(true); // Check If The Graph Is Ready
		
		Node<T> nodeTMP; // Temporary Node
		
		for (int i = 0; (i < this.nodes.size() && returnValue); i++) { // Loop Over All Nodes
			
			nodeTMP = this.nodes.get(i); // Temporary Node Assignament
			
			for (int j = 0; (i < this.nodes.size() && returnValue); j++) { // Loop Over All The Others Nodes
				
				if (!nodeTMP.equals(this.nodes.get(j))) { // Don't Analise The Temporary Node
					
					if (!this.neighbor(nodeTMP, this.nodes.get(j))) { // Check If Neighbor With The Generic Node
						
						returnValue = false; // No Neighbor, The Graph Isn't Connected
						
					}
					
				}
				
			}			
						
		}
		
		return returnValue;
		
		
	}

	
	
	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		try {
			
			this.checkGraphReady (true);
		
		} catch (GraphNotReadyException e) {
			
			e.printStackTrace();
	
		}
		
		String toString = "Graph";
		
		toString += "\n\tName: " + this.name;
		
		toString += "\n\n\tNodes:";
		
		for (int i = 0; i < this.nodes.size(); i++) {
			
			toString +=  "\n\t\t" + this.nodes.get(i);
			
		}
		
		toString += "\n\n\tArchs:";
		
		for (int i = 0; i < this.archs.size(); i++) {
			
			toString +=  "\n\t\t" + this.archs.get(i);
			
		}
		
		toString += "\n\n\tisConnected: ";
		
		try {
			
			toString += this.isConnected();
			
		} catch (GraphNotReadyException e) {
			
			e.printStackTrace();
			
		}
		
		toString += "\n\n\tNeighbors: ";
		
		for (int i = 0; i < this.nodes.size(); i++) {
			
			toString +=  "\n\t\t - " + this.nodes.get(i).getValue();
			
			ArrayList<Node<T>> n = new ArrayList<Node<T>> ();
			
			n = this.neighborhood(this.nodes.get(i));			
			
			if (n.size() > 0) {
				
				toString += ": " + n.size() + " Neighbors :)";
				
				for (int j = 0; j < n.size(); j++) {
					
					toString += "\n\t\t\t" + n.get(j).toString();
					
				}
				
			} else {
				
				toString += ": None :(";
				
			}			
			
		}
		
		return toString;
		
	}
	
	
}
