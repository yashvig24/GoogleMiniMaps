package hw8;

import hw5.DirGraph;
import hw5.LabEdge;
import hw7.MarvelPaths2;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CampusRouteFinder represents the model of route-finding tool.
 * <p>
 * 
 * @specfield buildingNames : Map<String, String>  
 * 							   // short names mapped to full names of buildings
 * @specfield buildingLocs : Map<String, Coordinates>
 * 								   // short names mapped to location of building
 * @specfield campusPaths : DirGraph<Coordinates, Double>
 * 							 // a graph of all the possible paths with the associated distance on campus 
 */
public class CampusRouteFinder {
	// Rep invariant:
		//     buildingNames, buildingLocs, campusPaths != null
		//     Every key, value of buildingNames are not null.
		//     Every key, value of buildingLoc are not null.
		
		// Abstract function: 
		//     AF(this) = a CampusRouteFindingModel m such that
		//		   m.buildingNames = this.buildingNames
		//		   m.buildingLocs = this.buildingLocs
		//		   m.campusPaths = this.campusPaths
		
		// constant variable for checkRep
		private static final boolean CHECK = false;

		// a graph representing campus paths
		private DirGraph<Coordinates, Double> campusPaths;
				
		// a map that maps building's short name to full name
		private Map<String, String> buildingNames;
		
		// a map that maps building's full name to short name
		private Map<String, String> buildingNamesOpp;
		
		// a map that maps building's short name to its location
		private Map<String, Coordinates> buildingLocs;
		
		/**
		 * Constructs a campus graph.
		 * 
		 * @requires Files are well-formed.	each line of building passed in 
		 * 			 should contain exactly four tokens separated by a 
		 * 			 tab character, or else starting with a # symbol indicates 
		 * 			 a comment line. paths passed in should start with 
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
		 * @param buildings file which contains data of campus buildings
		 * @param paths file which contains data of campus paths
		 * @throws Exception if the format of the files does not match the 
		 * 		   expected format
		 */
		public CampusRouteFinder(String buildings, String paths) throws Exception {
			if (buildings == null && paths == null)
				throw new IllegalArgumentException("buildings and paths files passed in " +
						"cannot be null.");
			if (buildings == null)
				throw new IllegalArgumentException("buildings file passed in cannot be null");
			if (paths == null)
				throw new IllegalArgumentException("paths file passed in cannot be null");
			
			// a map thats maps building's abbreviated name to its full name
			buildingNames = new HashMap<String, String>();
			
			// a map thats maps building's full name to its abbreviated name
			buildingNamesOpp = new HashMap<String, String>();
			
			// a map thats maps building's abbreviated name to its location
			buildingLocs = new HashMap<String, Coordinates>();
			
			// a graph to hold all the paths with the distance associated with them
			campusPaths = new DirGraph<Coordinates, Double>();
			
			CampusDataParser.parseBuildingData(buildings, buildingNames, buildingNamesOpp, buildingLocs);
			CampusDataParser.buildCampusPaths(paths, campusPaths);
			checkRep();
		}
		
		/**
		 * Returns a map that contains buildings short name 
		 * associated with its full name.
		 * 
		 * @return a map contains buildings short name associated with 
		 * 		   its full name
		 */
		public Map<String, String> getBuildings() {
			checkRep();
			return new HashMap<String, String> (buildingNames);
		}
		
		/**
		 * Returns the full name of the specified building.
		 * 
		 * @require buildingName != null and building is one of 
		 * 			the buildings on campus
		 * @param buildingName abbreviated name of a building on campus
		 * @return the full name of the specified building
		 */
		public String getFullNameOfBuilding(String buildingName) {
			checkRep();
			if (buildingName == null)
				throw new IllegalArgumentException("building name cannot " +
						"be null: " + buildingName);
			
			if (!buildingNames.containsKey(buildingName))
				throw new IllegalArgumentException("specified building name " +
						"is not one of the building on campus: " + buildingName);
			
			checkRep();
			return buildingNames.get(buildingName);
		}
		
		/**
		 * Returns the abbreviated name of the specified building.
		 * 
		 * @require buildingName != null and building is one of 
		 * 			the buildings on campus
		 * @param buildingName full name of a building on campus
		 * @return the abbreviated name of the specified building
		 */
		public String getAbbreviatedNameOfBuilding(String buildingName) {
			checkRep();
			if (buildingName == null)
				throw new IllegalArgumentException("building name cannot " +
						"be null: " + buildingName);
			
			if (!buildingNamesOpp.containsKey(buildingName))
				throw new IllegalArgumentException("specified building name " +
						"is not one of the building on campus: " + buildingName);
			
			checkRep();
			return buildingNamesOpp.get(buildingName);
		}
		
		/**
		 * Returns the location of the specified building.
		 * 
		 * @require building != null
		 * @param buildingName the name of a building on campus
		 * @return the location of the specified building or null 
		 * 		   if buildingName is not one of the building's 
		 * 		   name on campus
		 */
		public /*@Nullable*/ Coordinates getLocationOfBuilding(String buildingName) {
			checkRep();
			if (buildingName == null)
				throw new IllegalArgumentException("building name cannot " +
						"be null: " + buildingName);
			
			checkRep();
			return buildingLocs.get(buildingName);
		}
		
		/**
		 * Finds the shortest walking route from one point to another point on campus.
		 * 
		 * @param start starting point of the walking route
		 * @param end end point of the walking route
		 * @requires start, end != null, and start and end are nodes in campusPaths
		 * @return the shortest walking route from start to end, or null if 
		 * 		   no path exists from start to end
		 */
		public /*@Nullable*/ Map<Coordinates, Double> findShortestWalkingRoute(
				Coordinates start, Coordinates end) {
			List<LabEdge<Coordinates, Double>> route = 
					MarvelPaths2.minimumCostPath(campusPaths, start, end);
			
			// return null if no path was found
			if (route == null)
				return null;
			
			Map<Coordinates, Double> route_map = new LinkedHashMap<Coordinates, Double>();
			
			// store the shortest walking route to LinkedHashMap
			for (LabEdge<Coordinates, Double> path : route)
				route_map.put(path.getDest(), path.getLabel());
			
			return route_map;
		}
		
		/**
		 * Checks if representation invariant holds.
		 */
		private void checkRep() {
			if (CHECK) {
				if (buildingNames == null)
					throw new RuntimeException("map of building's names is null");
				
				if (buildingLocs == null)
					throw new RuntimeException("map of building's locations is null");
				
				if (campusPaths == null)
					throw new RuntimeException("graph of campus paths is null");
				
				Set<String> names = buildingNames.keySet();
				for (String name : names) {
					if (name == null) 
						throw new RuntimeException("abbreviated name of the building cannot be null.");
					
					if (buildingNames.get(name) == null)
						throw new RuntimeException("full name of the building cannot be null.");
				}
				
				Set<String> names2 = buildingNamesOpp.keySet();
				for (String name2 : names2) {
					if (name2 == null) 
						throw new RuntimeException("full name of the building cannot be null.");
					
					if (buildingNamesOpp.get(name2) == null)
						throw new RuntimeException("abbreviated name of the building cannot be null.");
				}
				
				Set<String> locations = buildingLocs.keySet();
				for (String name : locations) {
					if (name == null)
						throw new RuntimeException("abbreviated name of the building cannot be null.");
					
					if (buildingLocs.get(name) == null)
						throw new RuntimeException("location of the building cannot be null.");
				}
			}
		}
}
