package net.tfobz.synchronization.serializedcounter;

/**
 * SerializedCounterTest
 * Testprogramm für die Klasse CounterIncrementThread
 * @author Michael Morandell
 *
 */
public class SerializedCounterTest {
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		//Neuer SerializedCounter
		SerializedCounter counter = new SerializedCounter();
		//Threads werden erstellt
		CounterIncrementThread thread1 = new CounterIncrementThread(counter);
		CounterIncrementThread thread2 = new CounterIncrementThread(counter);
		//Threads werden gestartet
		thread1.start();
		thread2.start();
		//Reset des Counters
		counter.resetCounter();
	}
}