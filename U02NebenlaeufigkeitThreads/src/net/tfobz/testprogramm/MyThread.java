package net.tfobz.testprogramm;

/**
 * Klasse MyThread
 * realisiert einen Thread, welcher solange er nicht interrupted wird, abwechselnd
 * 1 Sekunde wartet und danach eine Berechnung durchführt
 * erbt von Thread
 * @author Michael Morandell
 *
 */
public class MyThread extends Thread {
	
	/**
	 * run-Methode des Threads
	 */
	@Override
	public void run() {
		//Solange Thread nicht interrupted wurde
		while (!isInterrupted()) {
			try {
				//sleep
				sleep(1000);
				//bei Interrupt
			} catch (InterruptedException e) {
				interrupt();
			}
			//Berechnung
			double a = Math.cos(Math.PI * Math.E);
			for (int i = 0; i < 10000000; i++) {
				a = Math.pow(a, i) + Math.PI;
			}
		}
	}
}
