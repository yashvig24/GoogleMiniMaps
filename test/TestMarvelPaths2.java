package hw7.test;

import hw5.DirGraph;
import hw7.MarvelPaths2;

import org.junit.Before;
import org.junit.Test;
public class TestMarvelPaths2 {
	private static final int TIMEOUT = 2000;
	private DirGraph<String, Double> g;

	@Before
	public void setUp() throws Exception {
		g = MarvelPaths2.makeWeightedGraph("src/hw7/data/flights.tsv");
	}
	
	@Test(timeout = TIMEOUT, expected = NullPointerException.class)
	public void testNullInputt() throws Exception {
		MarvelPaths2.makeWeightedGraph(null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testMinCostPathNullInput() {
		MarvelPaths2.minimumCostPath(null, "a", "b");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testMinCOstPathNullNode1() {
		MarvelPaths2.minimumCostPath(g, null, "b");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testMinCOstPathNullNode2() {
		MarvelPaths2.minimumCostPath(g, "a", null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testMinCOstPathAbsentNodes() {
		MarvelPaths2.minimumCostPath(g, "a", "b");
	}
}
