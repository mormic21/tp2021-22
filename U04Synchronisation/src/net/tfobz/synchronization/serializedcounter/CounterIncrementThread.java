package net.tfobz.synchronization.serializedcounter;

/**
 * CounterIncrementThread
 * realisiert einen Thread, der den SerializedCounter 1000 mal inkrementiert
 * extend Thread
 * @author Michael Morandell
 *
 */
public class CounterIncrementThread extends Thread {
	//Objekt vom Typ SerializiedCounter
	SerializedCounter counter = null;
	
	/**
	 * CounterIncrementThread-Konstruktor
	 * @param serializedcounter, Objekt vom Typ SerializedCounter
	 */
	public CounterIncrementThread(SerializedCounter serializedcounter) {
		this.counter = serializedcounter;
	}
	
	/**
	 * run-Methode des Threads
	 */
	@Override
	public void run() {
		//1000mal erhöhen
		for(int i = 0; i < 1000; i++) {
			//synchronisation, damit korrekt und kontinuirlich gezählt wird
			synchronized (counter) {
				//Ausgabe der Werte
				System.out.println("Old value: "+counter.getValue());
				counter.getIncrementedValue();
				System.out.println("New value: "+counter.getValue());
			}
		}
	}
}