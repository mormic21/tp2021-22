package net.tfobz.junit5;

public class Calculator {
	private static int result = 0;
	private int [] memory = new int[10];

	public int getResult() {
		return result;
	}

	public void clear() {
		result = 0;
		memory = null;
		memory = new int[10];
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
	
	public void pythagoras(int a, int b) {
		pow(a);
		memory[0] = result;
		result = 0;
		pow(b);
		memory[1] = result;
		result = 0;
		add(memory[0]);
		add(memory[1]);
	}
	
	public boolean isthree(int n) {
		boolean ret = false;
		if (n == 3) {
			return true;
		}
		return ret;
	}
	
	public String retNull() {
		String ret = null;
		return ret;
	}
	
	public int[] getMemory() {
		return this.memory;
	}
	
	public void setMemory(int[] array) {
		memory = null;
		memory = new int[10];
		for (int i = 0; i < array.length; i++) {
			memory[i] = array[i];
		}
	}
	
	
}
