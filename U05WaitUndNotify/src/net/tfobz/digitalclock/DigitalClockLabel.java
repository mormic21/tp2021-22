package net.tfobz.digitalclock;
import java.awt.EventQueue;
import java.util.Date;
import javax.swing.JLabel;

/**
 * DigitalClockLabel
 * Realisiert ein Objekt vom Typ Label, welches die sekunden-genaue Zeit darstellt
 * erbt von JLabel
 * implementiert Runnable
 * @author Michael Morandell
 *
 */
@SuppressWarnings("serial")
public class DigitalClockLabel extends JLabel implements Runnable {
	//Membervariable
	private boolean stopped = false;
	
	/**
	 * getStopped
	 * gibt die Membervaribale stopped zurueck
	 * @return stopped, als boolean
	 */
	public boolean getStopped() {
		return stopped;
	}
	
	/**
	 * setStopped
	 * setzt die Membervariable stopped
	 * @param stopped, boolean
	 */
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
	
	/**
	 * run-Methode des Runnable-Interfaces
	 */
	@Override
	public void run() {
		//Endlosschleife
		while (true) {
			//Wenn Uhr nicht gestoppt wurde
			if (!this.stopped) {
				//Aktuelle Zeit wird geholt und in das Label geschrieben
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						setText(new Date().toString().substring(11,19));
					}
				});
			}
			if (this.stopped) {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			//Der Thread schlaeft fuer 100ms
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {;}
		}
	}
}