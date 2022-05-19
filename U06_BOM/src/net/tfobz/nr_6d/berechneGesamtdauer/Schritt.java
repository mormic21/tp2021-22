package net.tfobz.nr_6d.berechneGesamtdauer;
/**
 * 
 * @author Michael Morandell
 *
 */
public class Schritt {
	public int wiederholungen;
	public int geschätztedauer;
	//Kardinalität 0..3
	public int anzahlUnterschritte = 3;
	public Unterschritt unterschritte[] = new Unterschritt[anzahlUnterschritte];
	
	/**
	 * Schritt
	 */
	public Schritt() {
//		for (int i = 0; i < unterschritte.length; i++) {
//			unterschritte[i] = new Unterschritt();
//		}
	}
}
