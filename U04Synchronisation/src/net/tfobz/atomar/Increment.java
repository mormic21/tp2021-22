package net.tfobz.atomar;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 * Public Klasse Increment
 * inkrementiert eine Variable vom Typ Int 1mio mal
 * extends Thread
 * @author Michael Morandell
 *
 */
public class Increment extends Thread{
	//Objekt vom Typ Int
	Int intObject = null;
	//progressbar der GUI
	JProgressBar bar = null;
	//textfeld der gui
	JTextField text = null;
	
	/**
	 * Increment-Konstruktor
	 * @param intObject, vom Typ Int
	 * @param progressbar, die JProgressbar der GUI
	 * @param textfield, das JTextfield der GUI
	 */
	public Increment(Int intObject, JProgressBar progressbar, JTextField textfield) {
		this.intObject = intObject;
		this.bar = progressbar;
		this.text = textfield;
	}
	
	/**
	 * run-Methode des Threads
	 */
	@Override
	public void run() {
		//1 mio mal wird inkrementiert
		for (int i = 0; i < 1000000; i++) {
			//Objekt-Variable wird erhöht
			intObject.i++;
			//Progressbar wird gesetzt
			bar.setValue(intObject.i);
			//Textfeld wird gesetzt
			text.setText(String.valueOf(intObject.i));
		}
	}
}