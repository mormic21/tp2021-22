package net.tfobz.digitalclock;
/**
 * DigitalClockMain
 * Instannziiert ein GUI-Fenster vom Typ DigitalClock
 * @author Michael Morandell
 *
 */
public class DigitalClockMain {
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		//GUI
		DigitalClock dg = new DigitalClock();
		//set Visible
		dg.setVisible(true);
	}
}