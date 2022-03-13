package net.tfobz.DiningPhilosophersProblem;

/**
 * ForkControl
 * löst das DiningPhilosophersProblem
 * @author Michael Morandell
 *
 */
public class ForkControl {
	//Membervariablen
	Fork left = null;
	Fork right = null;
	
	/**
	 * ForkControl-Konstruktor
	 * @param left, Fork
	 * @param right, Fork
	 */
	public ForkControl(Fork left, Fork right) {
		this.left = left;
		this.right = right;
	}
	
	/**
	 * get-Methode
	 * @param p, Philosopher
	 * synchronized
	 */
	public synchronized void get(Philosopher p) {
		//Wenn linke gabel verfügbar ist, wird sie geholt
		if (left.available) {
			left.get(p);
		}
		//wenn auch rechte gabel verfügbar, wird sie geholt
		if (right.available) {
			right.get(p);
		}
		//ansonsten wird linke wieder abgelegt
		else {
			left.put(p);
		}
	}
	
	/**
	 * put-Methode
	 * @param p, Philosopher
	 * sychronized
	 */
	public synchronized void put(Philosopher p) {
		//Wenn Gabeln vorhanden
		if (left.owner != null && right.owner != null) {
			//Wenn Gabeln diesen Philosopher gehören
			if (left.owner.equals(p) && right.owner.equals(p)) {
				//Gabeln werden geputtet
				left.put(p);
				right.put(p);
			}
		}
	}
}