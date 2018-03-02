package hw8.test;

import static org.junit.Assert.*;

import hw5.DirGraph;
import hw8.CampusDataParser;
import hw8.Coordinates;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases to test 
 * the implementation of the CampusDataParser class.
 * 
 */
public class CampusDataParserTest {
	private static final int TIMEOUT = 2000;
	private Map<String, String> buildingNames;
	private Map<String, String> buildingNamesOpp;
	private Map<String, Coordinates> buildingLocs;
	private DirGraph<Coordinates, Double> campusPaths;
	
	@Before
	public void setUp() throws Exception {
		buildingNames = new HashMap<String, String>();
		buildingNamesOpp = new HashMap<String, String>();
		buildingLocs = new HashMap<String, Coordinates>();
		campusPaths = new DirGraph<Coordinates, Double>();
	}

	@Test(timeout = TIMEOUT)
	public void testParseEmptyBuildingData() throws Exception {
		CampusDataParser.parseBuildingData("src/hw8/data/empty_buildings.dat", buildingNames, buildingNamesOpp, buildingLocs);
		assertEquals(new HashMap<String, String>(), buildingNames);
		assertEquals(new HashMap<String, Coordinates>(), buildingLocs);
	}
	
	@Test(timeout = TIMEOUT)
	public void testParseBuildingDataTwoBuildings() throws Exception {
		CampusDataParser.parseBuildingData("src/hw8/data/two_buildings.dat", buildingNames, buildingNamesOpp, buildingLocs);
		Map<String, String> bn = new HashMap<String, String>();
		Map<String, Coordinates> bl = new HashMap<String, Coordinates>();
		bn.put("AA", "Apartment A");
		bn.put("AB", "Apartment B");
		bl.put("AA", new Coordinates(0, 0));
		bl.put("AB", new Coordinates(3, 4));
		assertEquals(bn, buildingNames);
		assertEquals(bl, buildingLocs);
	}
	
	@Test(timeout = TIMEOUT)
	public void testBuildCampusPathsTwoPaths() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw8/data/two_paths.dat", campusPaths);
		DirGraph<Coordinates, Double> cp = new DirGraph<Coordinates, Double>();
		Coordinates aa = new Coordinates(0, 0);
		Coordinates ab = new Coordinates(3, 4);
		cp.addNode(aa);
		cp.addNode(ab);
		cp.addEdge(aa, ab, 5.0);
		cp.addEdge(ab, aa, 5.0);
		assertEquals(cp.entrySet(), campusPaths.entrySet());
	}
	
	// tokens not separated by tab character
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testParseBadFormatedBuildingData() throws Exception {
		CampusDataParser.parseBuildingData("src/hw8/data/bad_buildings.dat", buildingNames, buildingNamesOpp, buildingLocs);
	}
	
	// bad formated on indented line
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testBuildBadFormatedPaths() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw8/data/bad_paths.dat", campusPaths);
	}
	
	// indented line comes before non-indented line
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testBuildBadFormatedPaths2() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw8/data/bad_paths2.dat", campusPaths);
	}
	
	@Test(timeout = TIMEOUT)
	public void testParseSportsBuildingData() throws Exception {
		CampusDataParser.parseBuildingData("src/hw8/data/city_buildings.dat", buildingNames, buildingNamesOpp, buildingLocs);
		Map<String, String> bn = new HashMap<String, String>();
		Map<String, Coordinates> bl = new HashMap<String, Coordinates>();
		bn.put("NYC", "Newyork");
		bn.put("SEA", "Seattle");
		bn.put("SAN", "SanFran");
		bn.put("BOS", "Boston");
		bn.put("HOU", "Houston");
		bn.put("LAS", "Lasvegas");
		bl.put("NYC", new Coordinates(3, 0));
		bl.put("SEA", new Coordinates(0, 4));
		bl.put("SAN", new Coordinates(4, 7));
		bl.put("BOS", new Coordinates(7, 3));
		bl.put("HOU", new Coordinates(10, 10));
		bl.put("LAS", new Coordinates(15, 15));
		assertEquals(bn, buildingNames);
		assertEquals(bl, buildingLocs);
	}
	
	@Test(timeout = TIMEOUT)
	public void testBuildSportsPaths() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw8/data/city_paths.dat", campusPaths);
		DirGraph<Coordinates, Double> cp = new DirGraph<Coordinates, Double>();
		Coordinates nyc = new Coordinates(3, 0);
		Coordinates sea = new Coordinates(0, 4);
		Coordinates san = new Coordinates(4, 7);
		Coordinates bos = new Coordinates(7, 3);
		Coordinates hou = new Coordinates(10, 10);
		Coordinates las = new Coordinates(15, 15);
		cp.addNode(nyc);
		cp.addNode(sea);
		cp.addNode(san);
		cp.addNode(bos);
		cp.addNode(hou);
		cp.addNode(las);
		cp.addEdge(nyc, sea, 1.0);
		cp.addEdge(nyc, san, 4.0);
		cp.addEdge(nyc, bos, 3.0);
		cp.addEdge(sea, nyc, 1.0);
		cp.addEdge(sea, san, 2.0);
		cp.addEdge(sea, bos, 3.0);
		cp.addEdge(san, nyc, 4.0);
		cp.addEdge(san, sea, 2.0);
		cp.addEdge(san, bos, 3.0);
		cp.addEdge(bos, nyc, 3.0);
		cp.addEdge(bos, sea, 3.0);
		cp.addEdge(bos, san, 3.0);
		cp.addEdge(hou, las, 5.0);
		cp.addEdge(las, hou, 5.0);
		assertEquals(cp.entrySet(), campusPaths.entrySet());
	}
}
