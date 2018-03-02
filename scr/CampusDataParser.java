package hw8;

import hw5.DirGraph;

import java.io.*;
import java.util.*;

/**
 * Parser utility to load the campus buildings and paths datasets.
 * 
 */
public class CampusDataParser {
	
	/**
	 * Read campus buildings dataset.
	 * Each line of the input contains building's abbreviated name, 
	 * full name, and x and y coordinate of location of 
	 * the building's entrance, each separated by a tab character
	 * 
	 * @requires file is well-formed: each line contains exactly four
     *           tokens separated by a tab character
     *           or else starting with a # symbol to indicate a comment line.
	 * @param buildings file which contains data of campus buildings
	 * @param buildingNames a map of building's names that maps building's 
	 * 		  short name to full name (empty initially)
	 * @param buildingNamesOpp a map of building's names that maps building's 
	 * 		  full name to its short name (empty initially)
	 * @param buildingLocs a map of building's locations that maps 
	 * 		  building's abbreviated name to its location (empty initially)
	 * @throws Exception if the format of the file does not match the 
	 * 		   expected format
	 */
	public static void parseBuildingData(String buildings, 
			Map<String, String> buildingNames, Map<String, String> buildingNamesOpp, 
			Map<String, Coordinates> buildingLocs) throws Exception {
		
		BufferedReader reader = null;
	    try {
	    	reader = new BufferedReader(new FileReader(buildings));

	    	// Construct a map of buildings' names (map abbreviated name to full name) 
	    	// and a map of buildings' locations (map abbreviated name to 
	    	// location of the building).
	    	String inputLine = reader.readLine();
	    	while (inputLine != null) {
	    		// Ignore comment lines.
	    		if (!inputLine.startsWith("#")) {
	    			// Parse the data, stripping out quotation marks and throwing
		    		// an exception for malformed lines.
		    		inputLine = inputLine.replace("\"", "");
		    		String[] tokens = inputLine.split("\t");
		    		if (tokens.length != 4) {
		    			throw new Exception("file not well-formed: " + inputLine);
		    		}

		    		String short_name = tokens[0];
		    		String full_name = tokens[1];
		    		double x = Double.parseDouble(tokens[2]);
		    		double y = Double.parseDouble(tokens[3]);
		    		
		    		// map building's abbreviated name to its location
		    		buildingLocs.put(short_name, new Coordinates(x, y));
		    		
		    		// map building's abbreviated name to its full name
		    		buildingNames.put(short_name, full_name);
		    		
		    		// map building's full name to its abbreviated name
		    		buildingNamesOpp.put(full_name, short_name);
	    		}
	    		inputLine = reader.readLine();
	    	}
	    } catch (IOException e) {
	    	System.err.println(e.toString());
	    	e.printStackTrace(System.err);
	    } finally {
	    	if (reader != null) {
	    		reader.close();
	    	}
	    }
	}
	
	/**
	 * Read campus paths dataset and construct a campus paths.
	 * 
	 * @requires File is well-formed. File passed in should start with 
	 * 			 a non-indented line (if file is not empty) with 
	 * 			 coordinates of a point, followed by lines of 
	 * 			 coordinates and the distance between the coordinates of 
	 * 			 point in non-indented line and	coordinates of point in 
	 * 			 this line. Format of non-indented lines should be 
	 * 			 x coordinate of the point followed by a comma and then 
	 * 			 the y coordinate of the point (e.g. x,y). Format of 
	 * 			 indented lines should be like non-indented lines 
	 * 			 and followed by a colon and the distance between those 
	 * 			 two points.
	 * @param pathsfile file which contains data of campus paths
	 * @param campusPaths a graph that contains campus paths (empty initially)
	 * @throws Exception if the format of the file does not match the 
	 * 		   expected format
	 */
	public static void buildCampusPaths(String pathsfile, 
			DirGraph<Coordinates, Double> campusPaths) throws Exception {
		
		BufferedReader reader = null;
	    try {
	    	reader = new BufferedReader(new FileReader(pathsfile));
	    	
	    	String inputLine = reader.readLine();
	    	Coordinates location = null;
	    	while (inputLine != null) {
	    		
	    		// Ignore comment lines.
	    		if (!inputLine.startsWith("#")) {
	    			
		    		inputLine = inputLine.replace("\"", "");
		    		
		    		inputLine = inputLine.replace("\t", "");
		    		// split coordinate and distance from indented lines
		    		String[] tokens = inputLine.split(": ");
		    		
		    		String[] tokens2 = tokens[0].split(",");
	    			double x = Double.parseDouble(tokens2[0]);
	    			double y = Double.parseDouble(tokens2[1]);
	    			Coordinates coordinates = new Coordinates(x, y);
	    			
		    		// token.length == 1 or 2 means the line is non-indented
		    		// else means the file is not well-formed
		    		if (tokens.length == 1) {
		    			// add the coordinates to the graph if it
		    			// is not already in the graph
		    			if (!campusPaths.containsNode(coordinates))
		    				campusPaths.addNode(coordinates);
		    			
		    			location = coordinates;
		    		} else if (tokens.length == 2) {
		    			if (location == null)
		    				throw new Exception("File is not well-formed. " +
		    						"Non-indented line should come before " +
		    						"indented line.");

		    			// add the coordinates to the graph is it
		    			// is not already in the graph
		    			if (!campusPaths.containsNode(coordinates))
		    				campusPaths.addNode(coordinates);
		    			
		    			double dist = Double.parseDouble(tokens[1]);
		    			campusPaths.addEdge(location, coordinates, dist);
		    		} else {
		    			throw new Exception("File is not well-formed. " + inputLine);
		    		}
	    		}
	    		
	    		inputLine = reader.readLine();
	    		
	    	}
	    } catch (IOException e) {
	    	System.err.println(e.toString());
	    	e.printStackTrace(System.err);
	    } finally {
	    	if (reader != null) {
	    		reader.close();
	    	}
	    }
	}
}