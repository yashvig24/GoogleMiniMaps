package hw8.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import hw8.CampusRouteFinder;
import hw8.Coordinates;

import org.junit.Test;

/**
 * This class contains a set of test cases to test 
 * the implementation of the CampusRouteFinder class.
 */
public class CampusRouteFinderTest {
	private static final int TIMEOUT = 2000;
	private static final String filepath = "src/hw8/data/";
	
	@Test(timeout = TIMEOUT)
	public void testConstructModelSuccessWithNoException() throws Exception {
		new CampusRouteFinder(filepath + "city_buildings.dat", 
									filepath + "city_paths.dat");
	}
	
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testConstructModelThrowsExceptionBadBuildingsData() throws Exception {
		new CampusRouteFinder(filepath + "bad_buildings.dat", 
									filepath + "city_paths.dat");
	}
	
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testConstructModelThrowsExceptionBadPathsData() throws Exception {
		new CampusRouteFinder(filepath + "city_buildings.dat", 
									filepath + "bad_paths.dat");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructModelWithBothArgumentsNull() throws Exception {
		new CampusRouteFinder(null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructModelWithPathsNull() throws Exception {
		new CampusRouteFinder(filepath + "city_buildings.dat", null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructModelWithBuldingsNull() throws Exception {
		new CampusRouteFinder(null, filepath + "city_paths.dat");
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetBuildingsEmptyBuildings() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		assertEquals(new HashMap<String, String>(), model.getBuildings());
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetFullNameOfBuildingWithNullArgument() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		model.getFullNameOfBuilding(null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetAbbreviatedNameOfBuildingWithNullArgument() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		model.getAbbreviatedNameOfBuilding(null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetFullNameOfBuildingWithNonExistingBuilding() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		model.getFullNameOfBuilding("NYC");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetAbbreviatedNameOfBuildingWithNonExistingBuilding() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		model.getAbbreviatedNameOfBuilding("Newyork");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetLocationOfBuildingWithNullArgument() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		model.getLocationOfBuilding(null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingWithNonExistingBuilding() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		assertTrue(model.getLocationOfBuilding("NYC") == null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithBothArgumentNull() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		model.findShortestWalkingRoute(null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithStartingLocationNull() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		model.findShortestWalkingRoute(null, new Coordinates(0, 0));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithEndingLocationNull() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "empty_buildings.dat", filepath + "empty_paths.dat");
		
		model.findShortestWalkingRoute(new Coordinates(0, 0), null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetBuildingsTwoBuildings() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		Map<String, String> bn = new HashMap<String, String>();
		bn.put("AA", "Apartment A");
		bn.put("AB", "Apartment B");
		assertEquals(bn, model.getBuildings());
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetFullNameOfBuildingTwoBuildingsWithNonExistingBuilding() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		model.getFullNameOfBuilding("NYC");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetAbbreviatedNameOfBuildingTwoBuildingsWithNonExistingBuilding() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		model.getAbbreviatedNameOfBuilding("Newyork");
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetFullNameOfBuildingTwoBuildingsWithAA() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		assertEquals("Apartment A", model.getFullNameOfBuilding("AA"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetAbbreviatedNameOfBuildingTwoBuildingsWithApartmentA() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		assertEquals("AA", model.getAbbreviatedNameOfBuilding("Apartment A"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetFullNameOfBuildingTwoBuildingsWithAB() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		assertEquals("Apartment B", model.getFullNameOfBuilding("AB"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetAbbreviatedNameOfBuildingTwoBuildingsWithApartmentB() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		assertEquals("AB", model.getAbbreviatedNameOfBuilding("Apartment B"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingTwoBuildingsWithNonExistingBuilding() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		assertTrue(model.getLocationOfBuilding("NYC") == null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingTwoBuildingsWithAA() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		assertEquals(new Coordinates(0, 0), model.getLocationOfBuilding("AA"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingTwoBuildingsWithAB() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		assertEquals(new Coordinates(3, 4), model.getLocationOfBuilding("AB"));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithBothNonExistingLocations() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		model.findShortestWalkingRoute(new Coordinates(3, 0), new Coordinates(0, 4));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithNonExistingStartingLocation() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		model.findShortestWalkingRoute(new Coordinates(3, 0), new Coordinates(3, 4));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithNonExistingEndingLocation() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		model.findShortestWalkingRoute(new Coordinates(0, 0), new Coordinates(0, 4));
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteWithBothExistingLocations() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		Coordinates aa = new Coordinates(0, 0);
		Coordinates ab = new Coordinates(3, 4);
		Map<Coordinates, Double> route = new LinkedHashMap<Coordinates, Double>();
		route.put(aa, 0.0);
		route.put(ab, 5.0);
		assertEquals(route, model.findShortestWalkingRoute(aa, ab));
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteWithBothExistingLocationsReverseOrder() throws Exception {
		CampusRouteFinder model = 
				new CampusRouteFinder(filepath + "two_buildings.dat", filepath + "two_paths.dat");
		
		Coordinates aa = new Coordinates(0, 0);
		Coordinates ab = new Coordinates(3, 4);
		Map<Coordinates, Double> route = new LinkedHashMap<Coordinates, Double>();
		route.put(ab, 0.0);
		route.put(aa, 5.0);
		assertEquals(route, model.findShortestWalkingRoute(ab, aa));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetCityBuildings() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		Map<String, String> bn = new HashMap<String, String>();
		bn.put("NYC", "Newyork");
		bn.put("SEA", "Seattle");
		bn.put("SAN", "SanFran");
		bn.put("BOS", "Boston");
		bn.put("HOU", "Houston");
		bn.put("LAS", "Lasvegas");
		assertEquals(bn, model.getBuildings());
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetFullNameOfBuildingCityBuildingsWithNYC() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		assertEquals("Newyork", model.getFullNameOfBuilding("NYC"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetAbbreviatedNameOfBuildingCityBuildingsWithNYC() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		assertEquals("NYC", model.getAbbreviatedNameOfBuilding("Newyork"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingCityBuildingsWithNYC() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		assertEquals(new Coordinates(3, 0), model.getLocationOfBuilding("NYC"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingCityBuildingsWithSEA() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		assertEquals(new Coordinates(0, 4), model.getLocationOfBuilding("SEA"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingCityBuildingsWithSAN() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		assertEquals(new Coordinates(4, 7), model.getLocationOfBuilding("SAN"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingCityBuildingsWithBOS() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		assertEquals(new Coordinates(7, 3), model.getLocationOfBuilding("BOS"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingCityBuildingsWithHOU() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		assertEquals(new Coordinates(10, 10), model.getLocationOfBuilding("HOU"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingCityBuildingsWithLAS() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		assertEquals(new Coordinates(15, 15), model.getLocationOfBuilding("LAS"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromNYCToHOU() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		Coordinates nyc = model.getLocationOfBuilding("NYC");
		Coordinates hou = model.getLocationOfBuilding("HOU");
		assertTrue(model.findShortestWalkingRoute(nyc, hou) == null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromHOUToNYC() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		Coordinates hou = model.getLocationOfBuilding("HOU");
		Coordinates nyc = model.getLocationOfBuilding("NYC");
		assertTrue(model.findShortestWalkingRoute(hou, nyc) == null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromNYCToSAN() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		Coordinates nyc = model.getLocationOfBuilding("NYC");
		Coordinates san = model.getLocationOfBuilding("SAN");
		Coordinates sea = model.getLocationOfBuilding("SEA");
		Map<Coordinates, Double> route = new LinkedHashMap<Coordinates, Double>();
		route.put(nyc, 0.0);
		route.put(sea, 1.0);
		route.put(san, 3.0);
		assertEquals(route, model.findShortestWalkingRoute(nyc, san));
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromBOSToSEA() throws Exception {
		CampusRouteFinder model =
				new CampusRouteFinder(filepath + "city_buildings.dat", filepath + "city_paths.dat");
		
		Coordinates bos = model.getLocationOfBuilding("BOS");
		Coordinates sea = model.getLocationOfBuilding("SEA");
		Map<Coordinates, Double> route = new LinkedHashMap<Coordinates, Double>();
		route.put(bos, 0.0);
		route.put(sea, 3.0);
		assertEquals(route, model.findShortestWalkingRoute(bos, sea));
	}
}
