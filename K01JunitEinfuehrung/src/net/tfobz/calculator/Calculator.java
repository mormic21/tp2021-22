package net.tfobz.calculator;

public class Calculator {
	private static int result = 0;

	public int getResult() {
		return result;
	}

	public void clear() {
		result = 0;
	}

	public void add(int n) {
		result = result + n;
	}

	public void subtract(int n) {
		result = result - n;
	}

	public void multiply(int n) {
		result = result *n;
	}

	public void divide(int n) {
		result = result / n;
	}

	public void squareRoot(int n) {
		result = (int)Math.sqrt(n);
	}
	
	public void pow(int n) {
		result = result + (int)Math.pow(n, 2);
	}
	
}
