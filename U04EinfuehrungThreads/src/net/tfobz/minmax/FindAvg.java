package net.tfobz.minmax;

/**
 * FindAvg
 * berechnet in einem eigenen Thread den Durchschnitt aller Werte eines Int-Arrays
 * Die Progressbar der GUI wird gesetzt und das Ergebnis am Ende ans Textfeld der GUI ausgegeben
 * erbt von Thread
 * @author Michael Morandell
 *
 */
public class FindAvg extends Thread {
	//Membervariable
	private int [] arr;
	
	/**
	 * FindAvg-Konstruktor
	 * @param arry, Array aus welchem der Durchschnitt berechnet wird
	 */
	public FindAvg(int [] arry) {
		arr = arry;
	}
	
	/**
	 * run-Methode der Thread-Klasse
	 */
	@Override
	public void run() {
		double ret = 0;
		//Addiert alle Zahlen im Array
		for (int index = 0; index < arr.length; index++) {
			FindGUI.progressBars[2].setValue((index/(arr.length/100))+1);
			ret = ret + arr[index];
		}
		//Die Summe wird durch die Anzahl geteilt
		ret = (ret/(double)arr.length);
		//der Int-Wert des Mittelwerts wird an das Textfeld der GUI ausgegeben
		FindGUI.texts[2].setText(String.valueOf((int)ret));
	}
}
