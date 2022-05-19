package net.tfobz.nr_6d.berechneGesamtdauer;
/**
 * 
 * @author Michael Morandell
 *
 */
public class Anleitung {
	public int anzahlSchritte = 50;
	public Schritt schritte[] = new Schritt[anzahlSchritte];
	
	/**
	 * Anleitung
	 */
	public Anleitung() {
		for (int i = 0; i < schritte.length; i++) {
			schritte[i] = new Schritt();
		}
	}
	
	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		Anleitung anleitung = new Anleitung();
		System.out.println(anleitung.gesch�tzteDauer());
	}
	
	/**
	 * gesch�tzte Dauer
	 * @return
	 */
	public int gesch�tzteDauer() {
		int ret = 0;
		for (int i = 0; i < schritte.length; i++) {
			for (int j = 0; j < this.schritte[i].wiederholungen; j++) {
				ret =+ this.schritte[i].gesch�tztedauer;
			}
		}
		return ret;
	}
}