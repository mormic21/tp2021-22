package net.tfobz.eventQuene.tunnel.server;

/**
 * Diese Klasse verwaltet die verf�gbaren Besucher, welche eingelassen werden 
 * k�nnen
 */
public class VisitorsMonitor {
	/**
	 * Maximalanzahl der im Tunnel vorhanden Besucher
	 */
	protected final int MAX_VISITORS = 50;
	/**
	 * Anzahl der Besucher die in den Tunnel noch eingelassen werden k�nnen
	 */
	protected int availableVisitors = MAX_VISITORS;
	
	/**
	 * Fordert count Besucher an und gibt Statusmeldungen an der Serverkonsole
	 * aus
	 * @param count
	 */
	public synchronized void request(int count) {
		//Statusmeldung an die Serverkonsole
		System.out.println(Thread.currentThread().getName()+" requests "+getVisitorString(count));
		//solange so viele Benutzer nicht verfuegbar sind, wird gewartet
		while (availableVisitors-count < 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!(availableVisitors-count < 0)) {
			availableVisitors = availableVisitors - count;
			//Statusmeldung an die Serverkonsole
			System.out.println(Thread.currentThread().getName()+" receives "+getVisitorString(count)+". "+getVisitorString(getAvailableVisitors())+" available");
		}	
	}
	
	/**
	 * Gibt count Besucher an den VisitorsMonitor zur�ck und gibt Statusmeldungen
	 * an der Serverkonsole aus
	 * @param count
	 */
	public synchronized void release(int count) {
		if (!(availableVisitors-count > 50)) {
			availableVisitors = availableVisitors - count;
			//Statusmeldung an Serverkonsole
			System.out.println(Thread.currentThread().getName()+" releases "+getVisitorString(count)+". "+getVisitorString(getAvailableVisitors())+" available");
			notifyAll();
		}
	}
	
	/**
	 * Liefert die Anzahl der momentan noch verf�gbaren Besucher zur�ck, die in den
	 * Tunnel eingelassen werden k�nnen
	 * @return Anzahl der noch in den Tunnel einlassbaren Besucher
	 */
	public synchronized int getAvailableVisitors() {
		return this.availableVisitors;
	}
	
	/**
	 * getVisitor String
	 * gibt Einzahl oder Mehrzahl zurueck
	 * @param numberOfVisitors
	 * @return "visitor" or "visitors"
	 */
	private String getVisitorString(int numberOfVisitors) {
		numberOfVisitors = Math.abs(numberOfVisitors);
		String ret = String.valueOf(numberOfVisitors) + " visitor";
		if (numberOfVisitors != 1) {
			ret = ret + "s";
		}
		return ret;
	}
}