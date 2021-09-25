package net.tfobz.junit5;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

public class CalculatorTest {
	private static Calculator c = null;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		c = new Calculator();
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		c = null;
	}

	@BeforeEach
	public void setUp() throws Exception {
		c.clear();
	}

	@Test
	public void creation() {
		assertEquals(0, c.getResult());
	}

	@Test
	public void add() {
		c.add(2);
		c.add(2);
		assertEquals(4, c.getResult());
	}

	@Test
	public void subtract() {
		c.subtract(2);
		c.subtract(2);
		assertEquals(-4, c.getResult());
	}

	@Test
	public void multiply() {
		c.add(2);
		c.multiply(2);
		assertEquals(4, c.getResult());
	}

	@Test
	public void divide() {
		c.divide(2);
		assertEquals(0, c.getResult());
		c.add(2);
		c.divide(2);
		assertEquals(1, c.getResult());
	}

	@Test
	public void divideByZero() {
		assertThrows(ArithmeticException.class, () -> c.divide(0));
	}

	@Test
	@Disabled
	public void squareRoot() {
		assertTimeoutPreemptively(Duration.ofMillis(500), () -> {
			c.squareRoot(4);
			assertEquals(2, c.getResult());
		});
	}

	@Test
	public void pow() {
		c.pow(5);
		assertEquals(25, c.getResult());
	}
	
	@Test
	public void pythagoras() {
		c.pythagoras(2, 3);
		assertEquals(13, c.getResult());
	}
	
	@Test
	public void testTrue() {
		assertTrue(c.isthree(3));
	}
	
	@Test
	public void testFalse() {
		assertFalse(c.isthree(100));
	}
	
	@Test
	public void testNull() {
		assertNull(c.retNull());
	}
	
	@Test
	public void testNotNull() {
		assertNotNull(c.getMemory());
	}
	
	@Test
	public void testArray() {
		int [] test = {1,8,7,56,45,2,8,2,2,44};
		c.setMemory(test);
		assertArrayEquals(test, c.getMemory());
	}
	
	@Test
	public void testSame() {
		assertSame(c.getMemory(), c.getMemory());
	}
}
