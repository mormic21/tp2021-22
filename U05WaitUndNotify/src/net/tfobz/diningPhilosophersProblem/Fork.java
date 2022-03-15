package net.tfobz.diningPhilosophersProblem;

/**
 * Fork
 * realisiert eine Gabel
 * @author Michael Morandell
 *
 */
public class Fork {
	//Membervariabeln
	private String name = null;
	public boolean available = true;
	public Philosopher owner = null;
	
	/**
	 * Fork-Konstruktor
	 * @param name, String
	 */
	public Fork(String name) {
		this.name = name;
	}
	
	/**
	 * get-Methode
	 * @param p, Philosopher
	 * synchronized
	 */
	public synchronized void get(Philosopher p) {
		//wenn nicht verfügbar, wird gewartet
		while (!available) {
			try {
				wait();
			} catch (InterruptedException e) {
				;
			}
		}
		available = false;
		owner = p;
		//ausgabe
		System.out.println(p.getName() + " gets " + name);
	}
	
	/**
	 * put-Methode
	 * @param p, Philosopher
	 * synchronized
	 */
	public synchronized void put(Philosopher p) {
		available = true;
		owner = null;
		//ausgabe
		System.out.println(p.getName() + " puts " + name);
		notifyAll();
	}
}