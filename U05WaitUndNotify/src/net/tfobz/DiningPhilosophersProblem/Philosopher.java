package net.tfobz.DiningPhilosophersProblem;

/**
 * Philosopher
 * realisiert einen Philosopher
 * extends Thread
 * @author Michael Morandell
 *
 */
public class Philosopher extends Thread {
	//Membervariablen
	public static final int MAX_THINK_TIME = 2000;
	public static final int MAX_EAT_TIME = 1000;
	private Fork left, right = null;
	private ForkControl control = null;
	
	/**
	 * Philosopher-Konstruktor
	 * @param name, String
	 * @param left, Fork
	 * @param right, Fork
	 */
	public Philosopher(String name, Fork left, Fork right) {
		setName(name);
		this.left = left;
		this.right = right;
		//neues Fork-Kontroll
		control = new ForkControl(this.left, this.right);
	}
	
	/**
	 * run-Methode
	 * des Threads
	 */
	@Override
	public void run() {
		while (true) {
			//delay 100ms
			try {
				sleep(100);
			} catch (InterruptedException e) {
				;
			}
			//Gabeln werden gegettet
			control.get(this);
			//delay 100ms
			try {
				sleep(100);
			} catch (InterruptedException e) {
				;
			}
			//Gabeln werden geputtet
			control.put(this);
		}
	}
}