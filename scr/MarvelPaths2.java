package hw7;

import java.util.*;
import hw5.*;
/**
 * This class contains a method to build graph using data 
 * from specified file, and a method to find the 
 * minimum-cost path from one node to another node.
 * 
 * @author Yash Vig
 */
public class MarvelPaths2 {
	/**
	 * Reads the input dataset and returns a graph of social network
	 * Each line of the input file contains a character name and a comic
	 * book the character appeared in, separated by a tab character
	 * A connection (always two-way) between two characters indicates that they both were in the same
	 * comic book.
	 * 
	 * @requires filename is a valid file path
	 * @param filename the file that will be read
	 * @return graph of the social network between characters.
	 * @throws MalformedDataException if the file is not well-formed:
	 *          each line contains exactly two tokens separated by a tab,
	 *          or else starting with a # symbol to indicate a comment line.
	 */
	public static DirGraph<String, Double> makeWeightedGraph(String filename) throws Exception {
		DirGraph<String, Double> network = new DirGraph<String, Double>();
		
		Map<String, HashMap<String, Integer>> charCount = new HashMap<String, HashMap<String, Integer>>();
		MarvelParser2.parseData(filename, charCount);
		// adds all nodes to the graph
		for (String character : charCount.keySet()) {
			network.addNode(character);
		}
		
		// add weighted edges of nodes to the graph, 
		// where the weight of the edge between two characters 
		// is the inverse of how many comic books two 
		// characters are in together
		for (String char1 : charCount.keySet()) {
			HashMap<String, Integer> count = charCount.get(char1);
			for (String char2 : count.keySet()) {
				int num = count.get(char2);
				network.addEdge(char1, char2, 1.0 / num);
				network.addEdge(char1, char2, 1.0 / num);
			}
		}
		
		return network;
	}
	
	/**
	 * Finds the minimum-cost path from one character to another character.
	 * 
	 * @param graph the graph used to find shortest path from origin to dest
	 * @param origin a character as origin
	 * @param dest a character as destination
	 * @requires graph != null && start != null && end != null
	 * @return the minimum-cost path from start to end, or null if 
	 * no path exists from start to end
	 * @throws IllegalArgumentException if either start or end is 
	 * not in the graph
	 */
	public static <T> /*@Nullable*/ List<LabEdge<T, Double>> minimumCostPath(
			DirGraph<T, Double> graph, T origin, T dest) {
		if (graph == null)
			throw new IllegalArgumentException("graph cannot be null.");
		
		if (origin == null || dest == null)
			throw new IllegalArgumentException("start and end cannot be null.");
		
		if (!(graph.containsNode(origin)))
			throw new IllegalArgumentException("Characters " + origin + 
					"is not in the graph.");
		
		if (!(graph.containsNode(dest)))
			throw new IllegalArgumentException("Characters " + dest + 
					"is not in the graph.");
		
		// Each element in active is a path from start to a given node.
		// A path's "priority" in the queue is the total cost of that path.
		// Create the priority queue with specified comparator 
		// to order paths by their total costs.
		PriorityQueue<ArrayList<LabEdge<T, Double>>> active = 
				new PriorityQueue<ArrayList<LabEdge<T, Double>>>(20, 
						new Comparator<ArrayList<LabEdge<T, Double>>>() {
							public int compare(ArrayList<LabEdge<T, Double>> path1, 
											   ArrayList<LabEdge<T, Double>> path2) {
								LabEdge<T, Double> char1 = path1.get(path1.size() - 1);
								LabEdge<T, Double> char2 = path2.get(path2.size() - 1);
								if (!(char1.getLabel().equals(char2.getLabel())))
									return char1.getLabel().compareTo(char2.getLabel());
								
								return path1.size() - path2.size();
							}
						});
		
		// permVisited contains nodes for which we know the minimum-cost path from start
		Set<T> permVisited = new HashSet<T>();
		
		// the path from start to itself has zero cost since it contains no edge,
		// so add the edge with zero cost to active
		ArrayList<LabEdge<T, Double>> start = new ArrayList<LabEdge<T, Double>>();
		start.add(new LabEdge<T, Double>(origin, 0.0));
		active.add(start);
		
		while (!active.isEmpty()) {
			// minPath is the lowest-cost path in active and 
			// is the minimum-cost path for some node
			ArrayList<LabEdge<T, Double>> minPath = active.poll();
			
			// get the latest updated shortest path from origin to current node
			LabEdge<T, Double> endOfMinPath = minPath.get(minPath.size() - 1);
			T minDest = endOfMinPath.getDest();
			double minCost = endOfMinPath.getLabel();
			
			// return minPath if the destination of minPath 
			// is equal to end passed in by client
			if (minDest.equals(dest))
				return minPath;
			
			// if the minimum-cost path from start to minDest is already visited, 
			// skip this one and examine the next one in active
			if (permVisited.contains(minDest))
				continue;
			
			// If we don't know the minimum-cost path from start to child,
            // examine the path we've just found
			Set<LabEdge<T, Double>> children = graph.getOutwardEdgesOf(minDest);
			for (LabEdge<T, Double> edge : children) {
				if (!permVisited.contains(edge.getDest())) {
					double newCost = minCost + edge.getLabel();
					ArrayList<LabEdge<T, Double>> newPath = 
							new ArrayList<LabEdge<T, Double>>(minPath); 
					newPath.add(new LabEdge<T, Double>(edge.getDest(), newCost));
					active.add(newPath);
				}
			}
			
			permVisited.add(minDest);
		}
		
		// no path exists from start to end
		return null;
	}
}
