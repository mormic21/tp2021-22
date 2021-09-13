package net.tfobz.calculator;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class CalculatorTest {
	private static Calculator c = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		c = new Calculator();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		c = null;
	}

	@Before
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
	@Ignore
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

	@Test(expected = ArithmeticException.class)
	public void divideByZero() {
		c.divide(0);
	}

	@Test(timeout = 100)
	public void squareRoot() {
		c.squareRoot(4);
		assertEquals(2, c.getResult());
	}
}
