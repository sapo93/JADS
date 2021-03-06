package Automata;

import java.util.ArrayList;

import DataStructures.Arch;
import DataStructures.Node;
import Exceptions.AutomatonNotReadyException;
import Exceptions.GraphNotReadyException;


public class NDFA<T extends Comparable<T>> extends LambdaNDFA<T> {

	
	/**
	 * Constructor That Create An Empty NDFA
	 * 
	 * @param name NDFA's Name
	 */
	public NDFA (String name) {
		
		super(name);
	
	}

	
	/** 
	 * Constructor That Creates The NDFA
	 * 
	 * @param Nodes Nodes List
	 * @param Archs Archs List
	 * @param Start Start State
	 * @param FinalNodes FinalNodes List
	 * @param Name NDFA's Name
	 * @throws GraphNotReadyException Graph Is Not Correctly Initialised
	 * @throws AutomatonNotReadyException DFA Not Correctly Initialised
	 */
	public NDFA (ArrayList<Node<T>> Nodes, ArrayList<Arch<T>> Archs, Node<T> Start, ArrayList<Node<T>> FinalNodes, String Name) throws GraphNotReadyException, AutomatonNotReadyException {
		
		super(Nodes, Archs, Start, FinalNodes, Name);
		
		this.checkArchsList();
		
	}

	
	/**
	 * Checks If The Arch Are Correct:
	 * 1) Starting And Ending Nodes Are In nodeList
	 * 2) No Lambda Transition 
	 * 
	 * @param arch Arch To Check
	 * @throws GraphNotReadyException @throws GraphNotReadyException Graph No Correctly Initialised	 * 
	 */
	@Override
	protected boolean checkArch (Arch<T> arch) throws GraphNotReadyException {
		
		System.out.println ("checkArch NDFA: " + arch.toString());
		
		boolean returnValue = true;
		
		if (super.checkArch (arch)) { // Look Point 1
		
			ArrayList<Arch<T>> archList = new ArrayList<Arch<T>>(); // New ArrayList
		
			archList = this.archsWithLabel(""); // Look For Arch With Lambda Transition
			
			if (archList != null && archList.size() > 0) { // No Lambda Transition Admitted
				
				returnValue = false;
				
			}
			
		} else {
			
			returnValue = false;
			
		}
		
		System.out.println ("		Returns: " + returnValue);
		
		return returnValue;	
		
		
	}
	
	
	
	/**
	 * Transiction Function 
	 * 
	 * @param a Symbol To Check
	 * @param currentState DFA Current State
	 * @return The Arriving Node If There Is An Arch Labelled a From currentState, Null Otherwise
	 * @throws AutomatonNotReadyException DFA Not Correctly Initialised
	 */
	private ArrayList<Node<T>> transitionFunction (String a, Node<T> currentState) throws AutomatonNotReadyException {
		
		this.checkAutomatonReady(true, true, true); // Check If The DFA Is Ready
		 
		ArrayList<Node<T>> finalNodes = new ArrayList<Node<T>>(); // Return Value
		
		// Takes Only The Archs With currentState As Initial Node
		ArrayList<Arch<T>> archsList = this.archsFromNode(currentState);
		 
		for (int j = 0; j < archsList.size(); j++) {
						
			// Check If The Arch Has a As Label
			if (archsList.get(j).getLabel().equals(a)) {
									
				finalNodes.add(archsList.get(j).getB());				
							
			}
			
		}
		
		if (finalNodes.size() < 1) {
			
			finalNodes = null;
			
		}
		
		return finalNodes;
					
	}
					
		
	
	/**
	 * Transiction Function Extended To String, To Check If The
	 * Passed String Is Accepted By The NDFA
	 * 
	 * @param word Word To Check
	 * @return True If The Word Belongs To The DFA's Language, False Otherwise
	 * @throws AutomatonNotReadyException DFA Not Correctly Initialised
	 */
	@Override	
	public boolean transitionFunctionExtended (String word) throws AutomatonNotReadyException {
		
		this.checkAutomatonReady(true, true, true); // Check If The DFA Is Ready
		 
		boolean accepted = false;
		
		if (word != null) { // Null Word Cannot Be Accepted
		
			accepted = this.recursiveTransictionFunctionExtended(word, this.start);
			
		}
		
		return accepted;
		
		
	}
	
	
	
	/**
	 * Recursive Transiction Function To Check If The Passed String Is Accepted
	 * By Calling Itself On Every State Generate By The TransictionFunction, 
	 * Reducing Step By Step The Word
	 * 
	 * @param word Word To Check
	 * @return True If The Word Belongs To The DFA's Language, False Otherwise
	 * @throws AutomatonNotReadyException DFA Not Correctly Initialised
	 */
	private boolean recursiveTransictionFunctionExtended (String word, Node<T> currentState) throws AutomatonNotReadyException {
		
		boolean accepted = false; // Return Value
		
		if ( word != null) { // Still Character To Compute
			
			boolean TMPaccepted = false; // Recursive Function Return Value
				
			ArrayList<Node<T>> nextStates = new ArrayList<Node<T>>(); // Next State In The Computation
					
			nextStates = this.transitionFunction(word.substring(0, 1), currentState); // Calculate The Next State
					
			if (nextStates != null) { // There Is An Arch, Go On With The Computation
				
				String nextWord = new String();
						
				if (word.length() > 1) { // Recursive Step
					
					nextWord = word.substring(1);
					
				} else { // // Word Ended, Pass null As Word
					
					nextWord = null;
				}
					
				for (int i = 0; i < nextStates.size(); i++) {
					
					TMPaccepted = this.recursiveTransictionFunctionExtended(nextWord, nextStates.get(i)); // Calculate Result From nextStatus(i)
					
					accepted = accepted || TMPaccepted; 
					
					//System.out.println ("[i=" + i + "] Word: " + word + " NextState: " + nextStates.get(i).toString() + " | TMPaccepted: " + TMPaccepted + " | accepted: " + accepted);
						
				}
				
			} else { // No New State, Refuse The Word
				
				accepted = false;
				
			}
						
		} else { // Finished Computing, Check If Final State
					
			//System.out.println ("Word: NULL | State: " + currentState);
				
			if (this.finalNodes.contains(currentState)) { // If In Final State, Accept
								
				accepted = true;
							
			} else { // Not In Final State
								
				accepted = false;						
							
			}
								
		}
	
		return accepted;		
		
	}
	
	
	
	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		try {
			
			this.checkAutomatonReady (true, true, true);
		
		} catch (AutomatonNotReadyException e) {
			
			e.printStackTrace();
		
		}
		
		String toString = "DFA";
		
		toString += "\n\tName: " + this.name;
		
		toString += "\n\n\tNodes:";
		
		for (int i = 0; i < this.nodes.size(); i++) {
			
			toString +=  "\n\t\t" + this.nodes.get(i);
			
		}
		
		toString += "\n\n\tArchs:";
		
		for (int i = 0; i < this.archs.size(); i++) {
			
			toString +=  "\n\t\t" + this.archs.get(i);
			
		}
		
		toString += "\n\n\t Start Node: " + start.toString();
		
		toString += "\n\n\tFinal Nodes:";
		
		for (int i = 0; i < this.finalNodes.size(); i++) {
			
			toString +=  "\n\t\t" + this.finalNodes.get(i).getValue();
			
		}
		
		return toString;
		
	}

	
	
}
