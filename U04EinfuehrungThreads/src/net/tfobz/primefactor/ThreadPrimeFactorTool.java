package net.tfobz.primefactor;

/**
 * ThreadPrimeFactorTool
 * erstellt bei jedem Aufruf einen neuen Thread
 * erbt von PrimeFactorTool
 * @author Michael Morandell
 *
 */
public class ThreadPrimeFactorTool extends PrimeFactorTool {
	/**
	 * printPrimeFactors
	 * @param num, die Zahl als Integer
	 * @Ovveride
	 */
	@Override
	public void printPrimeFactors(int num) {
		//neuer Thread wird erstellt
		new Thread(() -> {
			//aufruf der Methode der Parent-Klass
			super.printPrimeFactors(num);
			//starten des Threads
		}).start();
	}
	
}
