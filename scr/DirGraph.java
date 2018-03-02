package hw5;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

/**
 * <b>DirGraph</b> represents a mutable, directed, labeled graph
 * <p>
 * @specfield nodes : Set<T> // Set of all nodes of type T in graph
 * @specfield outEdges : Set<LabEdge<T, L> // Set of edges for each node in nodes			
 *  					// each edge containing label of type L, destination of type T
 * @author Yash Vig
 *
 */
public class DirGraph<T, L extends Comparable<L>> {
	// Representative Invariant: 
	//	 graph != null
	//	 graph has no null nodes
	// 	 graph can have an edge going from n1 to n2 only if n1 and n2 
	//	 are present in graph
	
	// Abstract Function 
	//	 AF(g) = a directed labeled graph such that
	//	 		{} if graph is empty
	// 			{a = [(e, l1), (f, l2), .. , (g, ln)], b = []} if a is a node
	// 			and has n edges and if b is a node with no edges
	//			edges are described as (d, l) where d is destination node and l is label
	
	
	
	// constant variable used in checkRep
	private final static boolean CHECK = false;
	
	// directed labeled multi-graph
	private final Map<T, Set<LabEdge<T, L>>> graph;
	
	/**
	 * @effects Constructs a new directed graph with no nodes
	 */
	public DirGraph() {
		graph = new HashMap<T, Set<LabEdge<T, L>>>();
		checkRep();
	}
	
	/**
	 * returns true if node is present in the graph
	 * 
	 * @param node The node to be checked if contained in the graph
	 * @requires node != null
	 * @return true if graph contains node, false otherwise
	 */
	public boolean containsNode(T node) {
		checkRep();
		
		if(node == null) {
			throw new IllegalArgumentException("null node cannot be passed as parameter");
		}
		
		return graph.containsKey(node);
	}
	
	/**
	 * adds node node to be added in the graph, it is not present in graph
	 * 
	 * @param node The node to be added in the graph
	 * @modifies nodes 
	 * @effects addes node to nodes if node is not already present in 
	 * nodes
	 * @requires node != null
	 * @return true if node was successfully added in the graph, false otherwise
	 */
	public boolean addNode(T node) {
		checkRep();
		
		if(node == null) {
			throw new IllegalArgumentException("null node cannot be passed as parameter");
		}
		// do not add duplicates, return false
		if(graph.containsKey(node))
			return false;
		graph.put(node, new TreeSet<LabEdge<T, L>>());
		checkRep();
		return true;
	}
	
	/**
	 * adds edge from origin to dest with 
	 * attribute label and does not add duplicate edges
	 * 
	 * @param origin The node which the edge originates from
	 * @param dest The node which the edge lands on
	 * @param label The attribute of the edge
	 * @requires origin, dest, label != null
	 * @modifies outEdges
	 * @effects adds new edge to outEdges if edge is not already in graph
	 * @throws IllegalArgumentException if either origin or dest 
	 * is not in graph
	 * @return true if edge is successfully added, false otherwise
	 */
	public boolean addEdge(T origin, T dest, L label) {
		if(origin == null || dest == null || label == null) {
			throw new IllegalArgumentException("None of the parameters can be null");
		}
		
		if(!containsNode(origin)) {
			throw new IllegalArgumentException("graph does not contain " + origin + " node");
		}
		
		if(!containsNode(dest)) {
			throw new IllegalArgumentException("graph does not contain " + dest + " node");
		}
		checkRep();
		boolean success = graph.get(origin).add(new LabEdge<T, L>(dest, label));
		checkRep();
		return success;
	}
	
	/**
	 * returns a set of nodes
	 * 
	 * @return set of nodes
	 */
	public Set<T> getNodes() {
		checkRep();
		return new TreeSet<T>(graph.keySet());
	}
	
	/**
	 * returns size of graph or number of nodes in it
	 * 
	 * @return number of nodes
	 */
	public int size() {
		checkRep();
		return graph.size();
	}
	
	/**
	 * returns true if graph has no nodes
	 * 
	 * @return true if graph has no nodes
	 */
	public boolean isEmpty() {
		checkRep();
		return size() == 0;
	}
	
	/**
	 * returns a set of outgoing edges from node
	 * 
	 * @param node The node of which the outgoing edges are returned
	 * @return set of outgoing edges from node
	 * @requires n != null
	 * @throws IllegalArgumentException if node is not in nodes
	 */
	public Set<LabEdge<T, L>> getOutwardEdgesOf(T node) {
		checkRep();
		if(node == null) {
			throw new IllegalArgumentException("null node cannot be passed as parameter");
		}
		if(!graph.containsKey(node)) {
			throw new IllegalArgumentException("graph does not contain " + node + " node");
		}
		Set<LabEdge<T, L>> outwardEdges = new TreeSet<LabEdge<T, L>>();
		for (LabEdge<T, L> edge : graph.get(node)) {
			outwardEdges.add(edge); // add the destination of edge to children
		}
		checkRep();
		return outwardEdges;
	}
	
	/**
	 * returns a set nodes that are connected to the node
	 * 
	 * @param node The node of which the children are returned
	 * @return set of children from node
	 * @requires node != null
	 * @throws IllegalArgumentException if node is not in graph
	 */
	public Set<T> getChildrenOf(T node) {
		checkRep();
		if(node == null) {
			throw new IllegalArgumentException("null node cannot be passed as parameter");
		}
		if(!graph.containsKey(node)) {
			throw new IllegalArgumentException("graph does not contain " + node + " node");
		}
		Set<T> children = new TreeSet<T>();
		for (LabEdge<T, L> edge : getOutwardEdgesOf(node)) {
			children.add(edge.getDest()); // add the destination of edge to children
		}
		checkRep();
		return children;
	}
	
	/**
	 * returns a set of edges between node1 and node2
	 * 
	 * @param node1 The origin node
	 * @param node2 The destination node
	 * @requires node1, node2 != null
	 * @throws IllegalArgumentException if node1 or node2 is 
	 * not in nodes
	 * @return set of edges going from node1 to node2
	 */
	public Set<LabEdge<T, L>> getEdgesBetween(T node1, T node2) {
		checkRep();
		if(node1 == null) {
			throw new IllegalArgumentException("parameter " + node1 + " cannot be null");
		}
		if(node2 == null) {
			throw new IllegalArgumentException("parameter " + node2 + " cannot be null");
		}
		if(!graph.containsKey(node1)) {
			throw new IllegalArgumentException("parameter " + node1 + " not in graph");
		}
		if(!graph.containsKey(node2)) {
			throw new IllegalArgumentException("parameter " + node2 + "not in graph");
		}
		Set<LabEdge<T, L>> edgesBetween = new HashSet<LabEdge<T, L>>();
		for(LabEdge<T, L> edge : graph.get(node1)) {
			if(edge.getDest().equals(node2)) {
				edgesBetween.add(new LabEdge<T, L>(edge.getDest(), edge.getLabel()));
			}
		}
		checkRep();
		return edgesBetween;
	}
	
	/**
	 * returns the smallest edge between node1 and node2
	 * 
	 * @param node1 The origin node
	 * @param node2 The destination node
	 * @requires node1, node2 != null
	 * @throws IllegalArgumentException if node1 or node2 is 
	 * not in nodes
	 * @throws NoSuchElementException if there are no edges between node1 and node2
	 * @return smallest edge going from node1 to node2
	 */
	public LabEdge<T, L> getShortestEdgeBetween(T node1, T node2) {
		checkRep();
		Set<LabEdge<T, L>> edgesBetween = getEdgesBetween(node1, node2);
		if(edgesBetween.isEmpty()) {
			throw new NoSuchElementException();
		}
		LabEdge<T, L> min = Collections.min(edgesBetween);
		checkRep();
		return new LabEdge<T, L>(node2, min.getLabel());
	}
	
	/**
	 * Returns string representation of the graph.
	 * 
	 * @return string representation of the graph
	 */
	public String toString() {
		checkRep();
		return graph.toString();
	}
	
	/**
	 * Returns a set view of the graph.
	 * 
	 * @return a set view of the graph
	 */
	public Set<Entry<T, Set<LabEdge<T, L>>>> entrySet() {
		checkRep();
		return Collections.unmodifiableSet(graph.entrySet());
	}
	
	/**
	 * Checks if representation invariant holds (if any)
	 */
	private void checkRep() throws RuntimeException {
		if(CHECK) {
			if(graph == null) {
				throw new RuntimeException("graph cannot be null");
			}
			for(T node : graph.keySet()) {
				if(node == null) {
					throw new RuntimeException("node cannot be null");
				}
				
				for(LabEdge<T, L> edge : graph.get(node)) {
					if(edge == null) {
						throw new RuntimeException("edge cannot be null");
					}
					if(!graph.containsKey(edge.getDest())) {
						throw new RuntimeException("graph must contain destination of edge");
					}
				}
			}
		}
	}
}
