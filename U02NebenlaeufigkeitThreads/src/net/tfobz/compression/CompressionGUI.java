package net.tfobz.compression;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 * Realisiert eine GUI für das Komprimieren von Bildern mit nebnlaeufigen Threads
 * erbt von JFrame
 * @author Michael Morandell
 *
 */
public class CompressionGUI extends JFrame {
	//ImageComponent zum Anzeigen des Bildes
	public ImageComponent imgcomponent = null;
	//Buttons
	private JButton openb = null;
	private JButton compressb = null;
	private JButton saveb = null;
	//Spinner
	public JSpinner spinner = null;
	//ProgressBar
	private JProgressBar bar = null;
	//FileChooser
	public JFileChooser filechooser = null;
	
	/**
	 * CompressionGUI-Konstruktor
	 * realsiert das GUI-Fenster
	 */
	public CompressionGUI() {
		//Exit on Close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Bounds
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 700;
		int height = 500;
		this.setBounds((int) (d.getWidth() - width) / 2, (int) (d.getHeight() - height) / 2, width, height);
		//Titel
		this.setTitle("JPG-Compression");
		this.setResizable(false);
		//Layoutmgr null
		this.getContentPane().setLayout(null);

		// ImageComponent
		imgcomponent = new ImageComponent();
		imgcomponent.setBounds(7, 0, this.getWidth() - 20, this.getHeight() - 90);
		this.getContentPane().add(imgcomponent);

		// Font
		Font f = new Font(null, 0, 17);

		// Open-Button
		openb = new JButton();
		openb.setBounds(7, this.getHeight() - 82, 90, 40);
		openb.setText("Open...");
		openb.setFont(f);
		//Listener
		openb.addActionListener(new OpenListener());
		this.getContentPane().add(openb);

		// JSpinner
		spinner = new JSpinner(new SpinnerNumberModel(0.01, 0.01, 0.1, 0.01));
		spinner.setBounds(107, this.getHeight() - 77, 60, 30);
		spinner.setFont(f);
		this.getContentPane().add(spinner);

		// Compress and Save-Button
		saveb = new JButton();
		saveb.setBounds(177, this.getHeight() - 82, 180, 40);
		saveb.setFont(f);
		saveb.setText("Compress and save");
		//Listener
		saveb.addActionListener(new CompressSaveListener());
		this.getContentPane().add(saveb);

		// compress-Button
		compressb = new JButton();
		compressb.setBounds(367, this.getHeight() - 82, 110, 40);
		compressb.setFont(f);
		compressb.setText("Compress");
		//Listener
		compressb.addActionListener(new CompressListener());
		this.getContentPane().add(compressb);
		
		// progress bar
		bar = new JProgressBar();
		bar.setBounds(487, this.getHeight() - 82, 200, 40);
		bar.setStringPainted(true);
		this.getContentPane().add(bar);
	}
	
	/**
	 * Openlistener
	 * Actionlistener für den Open-Button, welcher eine Datei öffnet
	 * implementiert ActionListener
	 * @author Michael Morandell
	 *
	 */
	private class OpenListener implements ActionListener {
		
		/**
		 * actionPerformed
		 * @param e, ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//neuer FileChooser
			filechooser = new JFileChooser();
			//Sobald eine Datei ausgewaehlt und auf Open gedrueckt wurde
			if (filechooser.showOpenDialog(CompressionGUI.this) == JFileChooser.APPROVE_OPTION) {
				//Ausgewaehltes File wird geholt
				File newfile = filechooser.getSelectedFile();
				try {
					//Bild-Datei wird in das ImgComponent gesetzt
					imgcomponent.setImage(newfile);
					//faengt IOException
				} catch (IOException err) {
					err.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * CompressSaveListener
	 * Listener fuer den saveb-Button, 
	 * welcher mit nebenlaeufigen Threads das Bild komprimiert und verschiedene Versionen davon abspeichert
	 * implementiert ActionListener
	 * @author Michael Morandell
	 *
	 */
	private class CompressSaveListener implements ActionListener {
		
		/**
		 * actionPerformed
		 * @param e, ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//Progressbar auf 0 setzen
			bar.setValue(0);
			double quality = 1.0;
			//Granulitaet wird aus dem JSpinner der GUI geholt
			double granulitaet = Math.round((double) spinner.getValue() * 100.0) / 100.0;
			//Berechnung der benoetigten Threads
			int anzahlThreads = (int) ((1 / granulitaet) + 1);
			bar.setMaximum(anzahlThreads);
			//Thread-Array
			Thread threads[] = new Thread[anzahlThreads];
			//For i in AnzahlThreads
			for (int i = 0; i < anzahlThreads; i++) {
				//runden
				final double q = Math.round(quality * 100.0) / 100.0;
				//neuer Thread
				threads[i] = new Thread() {
					/**
					 * Run-Methode
					 */
					public void run() {
						try {
							//Kompression + Abspeicherung
							JPGImageCompress.compressImage(imgcomponent.getImage(), filechooser.getSelectedFile()
									.toString().substring(0, filechooser.getSelectedFile().toString().length() - 4), q);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				};
				//Qualitaet wird vermindert
				quality -= granulitaet;
			}
			//Alle instanziierte Threads werden gestartet
			for (int i = 0; i < threads.length; i++) {
				threads[i].start();
			}
			//Thread control, welcher kontrolliert wieviele Threads schon fertig komprimiert haben
			Thread control = new Thread() {
				/**
				 * Run-Methode
				 */
				public void run() {
					//Solange true
					while (true) {
						//Anzahl terminierter Threads
						int terminated = 0;
						//Eine halbe Sekunde wird geschlafen
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//Zaehlen wieviele Threads bereits terminated sind
						for (int i = 0; i < threads.length; i++) {
							if (threads[i].getState().equals(State.TERMINATED)) {
								//progressbar wird gesetzt
								bar.setValue(terminated);
								terminated++;
							}
						}
						//Wenn alle Threads terminated sind
						if (terminated >= anzahlThreads) {
							bar.setValue(terminated);
							//Abbruch der Endlos-Schleife
							break;
						}
					}
				}
			};
			//Starten des Kontroll-Threads
			control.start();
		}
	}
	
	private class CompressListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
