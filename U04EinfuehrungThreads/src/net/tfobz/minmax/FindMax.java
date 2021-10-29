package net.tfobz.minmax;

/**
 * FindMax
 * sucht in einem eigenen Thread das Maximum aller Werte eines Int-Arrays
 * Die Progressbar der GUI wird gesetzt und das Ergebnis am Ende ans Textfeld der GUI ausgegeben
 * erbt von Thread
 * @author Michael Morandell
 *
 */
public class FindMax extends Thread {
	//Membervariable
	private int [] arr;
	
	/**
	 * FindMax-Konstruktor
	 * @param arry, Array aus das Maximum gesucht wird
	 */
	public FindMax(int [] arry) {
		arr = arry;
	}
	
	/**
	 * run-Methode der Thread-Klasse
	 */
	@Override
	public void run() {
		int ret = arr[0];
		//Ermittelt die groesste Zahl und speichert diese in ret
		for (int index = 0; index < arr.length; index++) {
			//setzt die Progress-Bar
			FindGUI.progressBars[1].setValue((index/(arr.length/100))+1);
			if (arr[index] > ret) {
				ret = arr[index];
			}
		}
		//setzt den Wert des Maximums in das Textfeld der GUI
		FindGUI.texts[1].setText(String.valueOf(ret));
	}
}
