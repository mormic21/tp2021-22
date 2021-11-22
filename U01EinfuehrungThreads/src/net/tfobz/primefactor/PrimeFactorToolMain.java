package net.tfobz.primefactor;

/**
 * Primefactor-Main
 * Startet die Threads zur Primfaktor-Berechnung
 * @author Michael Morandell
 *
 */
public class PrimeFactorToolMain {
	//minimale Nummer
	public static final int MIN_NUM = 10000;
	//maximale Nummer
	public static final int MAX_NUM = 10250;
	
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		//neues Objekt zur Primzahlberechnung
		PrimeFactorTool pt = new ThreadPrimeFactorTool();
		//für jede Zahl wird die Methode aufgerufen
		for (int num = MIN_NUM; num <= MAX_NUM; num++)
			pt.printPrimeFactors(num);
	}
}
