package hw8.test;

import static org.junit.Assert.*;
import hw8.Coordinates;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases to test 
 * the implementation of the Coordinates class.
 */
public class CoordinatesTest {
	private static final int TIMEOUT = 2000;
	public static final double EPSILON = 0.00000001;
	private Coordinates coordinates;
	
	@Before
	public void setUp() throws Exception {
		coordinates = new Coordinates(1.23, 4.56);
	}

	@Test(timeout = TIMEOUT)
	public void testGetX() {
		assertTrue(Math.abs(coordinates.getX() - 1.23) < EPSILON);
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetY() {
		assertTrue(Math.abs(coordinates.getY() - 4.56) < EPSILON);
	}
	
	@Test(timeout = TIMEOUT)
	public void testToString() {
		assertTrue(coordinates.toString().equals("1.23, 4.56"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testEqualsWithNull() {
		assertFalse(coordinates.equals(null));
	}
	
	@Test(timeout = TIMEOUT)
	public void testEqualsWithNonCoordinatesObject() {
		assertFalse(coordinates.equals("1.23, 4.56"));
	}

	@Test(timeout = TIMEOUT)
	public void testEqualsWithSameCoordinates() {
		assertTrue(coordinates.equals(new Coordinates(1.23, 4.56)));
	}
	
	@Test(timeout = TIMEOUT)
	public void testHashCode() {
		assertEquals((new Coordinates(1.23, 4.56)).hashCode(), coordinates.hashCode());
	}
}