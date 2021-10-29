package net.tfobz.digitalclock;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

/**
 * DigitalClock
 * Realisiert eine Benutzerschnitstelle, welche über ein Objekt vom Typ DigitalClockLabel die aktuelle Zeit
 * anzeigt und die Moeglichkeit gibt, die Zeit anzuhalten bzw. fortzusetzen
 * erbt von JFrame
 * @author Michael Morandell
 *
 */
public class DigitalClock extends JFrame {
	//Membervariablen
	DigitalClockLabel dgl = null;
	JButton stopButton = null;
	JButton continueButton = null;
	
	/**
	 * DigitalClock-Konstruktor
	 * setzt den Startzustand der GUI
	 */
	public DigitalClock() {
		//Exit on close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds((1920/2)-275, (1080/2)-135, 550, 230);
		//Titel
		this.setTitle("DigitalClock");
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		//DigitalClockLabel
		dgl = new DigitalClockLabel();
		dgl.setBounds((this.getWidth()/2)-210, 10, 450, 100);
		dgl.setFont(new Font(null, Font.BOLD, 100));
		//neuer Thread
		Thread t = new Thread(dgl);
		//Thread wird gestartet
		t.start();
		this.getContentPane().add(dgl);
		//Instanziierung ButtonListener
		ButtonListener blistener = new ButtonListener();
		//Stop-Button
		stopButton = new JButton("Stop");
		stopButton.setBounds(140, 140, 100, 40);
		stopButton.setFont(new Font(null, Font.BOLD, 17));
		stopButton.addActionListener(blistener);
		this.getContentPane().add(stopButton);
		//Continue-Button
		continueButton = new JButton("Continue");
		continueButton.setBounds(260, 140, 160, 40);
		continueButton.setFont(new Font(null, Font.BOLD, 17));
		continueButton.addActionListener(blistener);
		this.getContentPane().add(continueButton);
	}
	
	/**
	 * ButtonListener
	 * Reagiert auf die Betaetigung des Stop- oder Continue-Buttons
	 * implementiert ActionListener
	 * @author Michael Morandell
	 *
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * actionPerformed
		 * @param e, Action Event
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//Wenn Stop-Button gedrueckt wurde
			if (e.getSource().equals(stopButton)) {
				//Stop-Variable des Labels wird auf true gesetzt
				dgl.setStopped(true);
			}
			//Continue-Button wurde gedrueckt
			if (e.getSource().equals(continueButton)) {
				//Stop-Variable des Labels wird auf false gesetzt
				dgl.setStopped(false);
			}
		}
	}
}
