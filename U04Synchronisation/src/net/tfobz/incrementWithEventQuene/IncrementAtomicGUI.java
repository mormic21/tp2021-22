package net.tfobz.incrementWithEventQuene;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;

/**
 * IncrementAtomicGUI
 * realisiert eine GUI, welche darstellt ob i++ atomar ist
 * Es ist nicht atomar, da nur 99% erreicht werden und die Zahl der Variable i leicht unter 2mio bleibt
 * VERSION MIT EVENTQUENES
 * extends JFrame
 * @author Michael Morandell
 *
 */
@SuppressWarnings("serial")
public class IncrementAtomicGUI extends JFrame {
	//GUI-Membervariablen
	JButton start = null;
	JProgressBar bar = null;
	JTextField text = null;
	JLabel label = null;
	//font
	private Font bold_font = new Font("Sans Serif", Font.BOLD, 16);
	/**
	 * IncrementAtomicGUI-Konstruktor
	 */
	public IncrementAtomicGUI() {
		//Exit on close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("IncrementAtomic MIT EVENTQUENES");
		// Einstellen der Werte für das Fenster
		int height = 300;
		int width = 528;
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2, 
				(Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2,
						width, height);
		this.setResizable(false);
		//Layoutmgr null
		this.getContentPane().setLayout(null);
		
		//start-button
		start = new JButton();
		start.setText("Start incrementation");
		start.setFont(bold_font);
		start.setBounds(154, 20, 220, 40);
		//ActionListener
		start.addActionListener(new ButtonListener());
		this.getContentPane().add(start);
		
		//progressbar
		bar = new JProgressBar(0, 2000000);
		bar.setStringPainted(true);
		bar.setValue(0);
		bar.setBounds(10, 90, 500, 100);
		this.getContentPane().add(bar);
		
		//label
		label = new JLabel();
		label.setFont(bold_font);
		label.setText("Result:");
		label.setBounds(10, 220, 55, 25);
		this.getContentPane().add(label);
		
		//textfield
		text = new JTextField();
		text.setFont(bold_font);
		text.setEditable(true);
		text.setBounds(80, 215, 400, 35);
		this.getContentPane().add(text);
	}
	
	/**
	 * ButtonListener reagiert auf das Druecken des start-Buttons
	 * @author Michael Morandell
	 *
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * actionPerformed
		 * wenn Button gedrueckt
		 * @param e, ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//neues Int-Objekt
			Int intObject = new Int();
			//Zwei Increment-Threads
			Increment inc1 = new Increment(intObject, bar, text);
			Increment inc2 = new Increment(intObject, bar, text);
			//starten der Threads
			inc1.start();
			inc2.start();


			try {
				inc1.join();
				inc2.join();
				System.out.println(intObject.i);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}