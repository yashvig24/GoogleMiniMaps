package hw8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * CampusRouteFinderHelper is the view, controller, and the main 
 * of the CampusRouteFinderHelper. This class allows user to 
 * type in some command to call appropriate method from view 
 * to get information from the model.
 * <p>
 * 
 */
public class CampusRouteFinderHelper {
	private static final double EPSILON = 0.00000001;
	private static final double ONEEIGHTHPI = Math.PI / 8;
	private static final double THREEEIGHTHSPI = 3 * ONEEIGHTHPI;
	private static final double FIVEEIGHTHSPI = 5 * ONEEIGHTHPI;
	private static final double SEVENEIGHTHSPI = 7 * ONEEIGHTHPI;
	private static final double NEGONEEIGHTHPI = -1 * ONEEIGHTHPI;
	private static final double NEGTHREEEIGHTHSPI = -1 * THREEEIGHTHSPI;
	private static final double NEGFIVEEIGHTHSPI = -1 * FIVEEIGHTHSPI;
	private static final double NEGSEVENIGHTHSPI = -1 * SEVENEIGHTHSPI;
	
	/**
	 * Lists all buildings' names (both abbreviated name and full name).
	 * 
	 * @param model model of the CampusRouteFinderHelper
	 * @requires model != null
	 */
	public static void getAllBuildings(CampusRouteFinder model) {
		if (model == null)
			throw new IllegalArgumentException("model cannot be null.");
		
		String buildingsNames = "Buildings:\n";
		
		Map<String, String> buildings = model.getBuildings();
		
		// use TreeSet to sort the keys in lexicographic order
		TreeSet<String> buildingsKeys = new TreeSet<String>(buildings.keySet());
		
		for (String short_name : buildingsKeys) {
			String full_name = buildings.get(short_name);
			buildingsNames += "\t" + short_name + ": " + full_name + "\n";
		}
		
		System.out.println(buildingsNames);
	}
	
	/**
	 * Gets the shortest walking route from one building to another building on campus.
	 * 
	 * @requires model, start, end != null and start and end to be buildings' abbreviated names
	 * @param model model of the CampusRouteFinderHelper
	 * @param start starting building's abbreviated name of the walking route
	 * @param end ending building's abbreviated name of the walking route
	 */
	public static void getShortestWalkingRoute(CampusRouteFinder model,
			String start, String end) {
		if (model == null)
			throw new IllegalArgumentException("model cannot be null.");
		
		if (start == null)
			throw new IllegalArgumentException("start cannot be null.");
		
		if (end == null)
			throw new IllegalArgumentException("end cannot be null.");
		
		Coordinates startLocation = model.getLocationOfBuilding(start);
		Coordinates endLocation = model.getLocationOfBuilding(end);


		if (startLocation == null) 
			System.out.println("Unknown building: " + start  + "\n");
		if (endLocation == null) 
			System.out.println("Unknown building: " + end  + "\n");
		else if(startLocation != null && endLocation != null){
			String route = "";
			
			route += "Path from " +  model.getFullNameOfBuilding(start) + " to " +
			
					model.getFullNameOfBuilding(end) + ":\n";
			
			Map<Coordinates, Double> shortest_route = 
					model.findShortestWalkingRoute(startLocation, endLocation);
			
			double current_x = startLocation.getX();
			double current_y = startLocation.getY();
			double current_dist = 0.0;  // store the total distance up to this point
			
			List<Coordinates> coordinates = new ArrayList<Coordinates>(shortest_route.keySet());
			coordinates = coordinates.subList(1, coordinates.size());
			
			for (Coordinates edge : coordinates) {
				double dest_x = edge.getX();
				double dest_y = edge.getY();
				double distance = shortest_route.get(edge).doubleValue();
				// get the angle for next direction
				double theta = Math.atan2(dest_y - current_y, dest_x - current_x);
				String direction = determineDirection(theta);
				
				route += String.format("\tWalk %.0f feet %s to (%.0f, %.0f)\n", 
						               (distance - current_dist), direction, 
						               dest_x, dest_y);
				
				// update the current coordinates in order to compute 
				// theta for the next edge
				current_x = dest_x;
				current_y = dest_y;
				current_dist = distance;
			}
			route += String.format("Total distance: %.0f feet\n", current_dist);
			
			System.out.println(route);
		}
	}
	
	/**
	 * Determine the direction based on the angle theta passed in.
	 * Possible output is N, E, S, W, NE, NW, SE, or SW
	 * 
	 * @param theta angle from the polar coordinates
	 * @return the direction based on the angle theta passed in
	 */
	private static String determineDirection(double theta) {
		if (Math.abs(theta) < EPSILON || Math.abs(theta - ONEEIGHTHPI) < EPSILON || 
			Math.abs(theta - NEGONEEIGHTHPI) < EPSILON || 
			(theta > 0 && theta < ONEEIGHTHPI) || (theta > NEGONEEIGHTHPI && theta < 0)) {
			return "E";
		} else if (theta > ONEEIGHTHPI && theta < THREEEIGHTHSPI) {
			return "SE";
		} else if (theta > NEGTHREEEIGHTHSPI && theta < NEGONEEIGHTHPI) {
			return "NE";
		} else if (Math.abs(theta - THREEEIGHTHSPI) < EPSILON || 
				   Math.abs(theta - FIVEEIGHTHSPI) < EPSILON || 
				   (theta > THREEEIGHTHSPI && theta < FIVEEIGHTHSPI)) {
			return "S";
		} else if (Math.abs(theta - NEGTHREEEIGHTHSPI) < EPSILON || 
				   Math.abs(theta - NEGFIVEEIGHTHSPI) < EPSILON || 
				   (theta > NEGFIVEEIGHTHSPI && theta < NEGTHREEEIGHTHSPI)) {
			return "N";
		} else if (theta > FIVEEIGHTHSPI && theta < SEVENEIGHTHSPI) {
			return "SW";
		} else if (theta > NEGSEVENIGHTHSPI && theta < NEGFIVEEIGHTHSPI) {
			return "NW";
		} else {
			return "W";
		}
	}
	
	/**
	 * Main method allows user to find shortest walking route 
	 * between two buildings and get all the buildings' names 
	 * on campus.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CampusRouteFinder model = 
					new CampusRouteFinder("src/hw8/data/campus_buildings.dat", 
												"src/hw8/data/campus_paths.dat");
			
			String menu = "Menu:\n" + "\t" + "r to find a route\n" +
						  "\t" + "b to see a list of all buildings\n" +
					      "\t" + "q to quit\n";
			
			System.out.println(menu);
			Scanner reader = new Scanner(System.in);
			System.out.print("Enter an option ('m' to see the menu): ");
			
			while (true) {
				String input = reader.nextLine();
				
				// echo empty input lines or lines beginning with # 
				if (input.length() == 0 || input.startsWith("#")) {
					System.out.println(input);
					continue;
				}
				
				if (input.equals("m")) {
					System.out.println(menu);
				} else if (input.equals("b")) {
					getAllBuildings(model);
				} else if (input.equals("r")) {
					System.out.print("Abbreviated name of starting building: ");
					String start = reader.nextLine();
					System.out.print("Abbreviated name of ending building: ");
					String end = reader.nextLine();
					getShortestWalkingRoute(model, start, end);
				} else if (input.equals("q")) {
					reader.close();
					return;
				} else {
					System.out.println("Unknown option\n");
				}
				System.out.print("Enter an option ('m' to see the menu): ");
			}
		} catch (Exception e) {
			System.err.println(e.toString());
	    	e.printStackTrace(System.err);
		}
	}
}
