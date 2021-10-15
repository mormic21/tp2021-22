package net.tfobz.primefactor;

public class ThreadPrimeFactorTool extends PrimeFactorTool {
	
	public void printPrimeFactors(int num) {
		new Thread(() -> {
			super.printPrimeFactors(num);
		}).start();
	}
	
}
