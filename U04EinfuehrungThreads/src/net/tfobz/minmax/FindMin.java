package net.tfobz.minmax;

/**
 * FindMin
 * sucht in einem eigenen Thread das Minimum aller Werte eines Int-Arrays
 * Die Progressbar der GUI wird gesetzt und das Ergebnis am Ende ans Textfeld der GUI ausgegeben
 * erbt von Thread
 * @author Michael Morandell
 *
 */
public class FindMin extends Thread {
	//Membervariable
	private int [] arr;
	
	/**
	 * FindMin-Konstruktor
	 * @param arry, Array aus das Minimum gesucht wird
	 */
	public FindMin(int [] arry) {
		arr = arry;
	}
	
	/**
	 * run-Methode der Thread-Klasse
	 */
	@Override
	public void run() {
		int ret = arr[0];
		//Ermittelt die kleinste Zahl und speichert diese in ret
		for (int index = 0; index < arr.length; index++) {
			//setzt die Progress-Bar
			FindGUI.progressBars[0].setValue((index/(arr.length/100))+1);
			if (arr[index] < ret) {
				ret = arr[index];
			}
		}
		//setzt den Wert des Maximums in das Textfeld der GUI
		FindGUI.texts[0].setText(String.valueOf(ret));
	}
}
