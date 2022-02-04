package net.tfobz.incrementWithEventQuene;
import java.awt.EventQueue;

import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 * Public Klasse Increment
 * inkrementiert eine Variable vom Typ Int 1mio mal
 * VERSION MIT EVENTQUENES
 * extends Thread
 * @author Michael Morandell
 *
 */
public class Increment extends Thread {
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
			//System.out.println(intObject.i);
			//Nach 100.000 erhöhungen
			if (i % 100000 == 0) {
				//ProgressBar wird gesetzt
				EventQueue.invokeLater(
						new Runnable() {
							public void run() {
								//Progressbar wird gesetzt
								bar.setValue(intObject.i + 100000);
							}
						}
				);
				//JTextfield wird gesetzt
				EventQueue.invokeLater(
						new Runnable() {
							public void run() {
								//Textfeld wird gesetzt
								text.setText(String.valueOf(intObject.i));
							}
						}
				);
			}
		}
	}
}