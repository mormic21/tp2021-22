package net.tfobz.compression;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Realisiert eine GUI für das Komprimieren von Bildern mit nebnlaeufigen
 * Threads erbt von JFrame
 * 
 * @author Michael Morandell
 *
 */
public class CompressionGUI extends JFrame {
	// ImageComponent zum Anzeigen des Bildes
	private ImageComponent imgcomponent = null;
	// Buttons
	private JButton openb = null;
	private JButton compressb = null;
	private JButton saveb = null;
	// Spinner
	private JSpinner spinner = null;
	// ProgressBar
	private JProgressBar bar = null;
	// FileChooser
	private JFileChooser filechooser = null;

	/**
	 * CompressionGUI-Konstruktor realsiert das GUI-Fenster
	 */
	public CompressionGUI() {
		// Exit on Close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Bounds
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 700;
		int height = 500;
		this.setBounds((int) (d.getWidth() - width) / 2, (int) (d.getHeight() - height) / 2, width, height);
		// Titel
		this.setTitle("JPG-Compression");
		this.setResizable(false);
		// Layoutmgr null
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
		// Listener
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
		// Listener
		saveb.addActionListener(new CompressSaveListener());
		this.getContentPane().add(saveb);

		// compress-Button
		compressb = new JButton();
		compressb.setBounds(367, this.getHeight() - 82, 110, 40);
		compressb.setFont(f);
		compressb.setText("Compress");
		// Listener
		compressb.addActionListener(new CompressListener());
		this.getContentPane().add(compressb);

		// progress bar
		bar = new JProgressBar();
		bar.setBounds(487, this.getHeight() - 82, 200, 40);
		bar.setStringPainted(true);
		this.getContentPane().add(bar);
	}

	/**
	 * Openlistener Actionlistener für den Open-Button, welcher eine Datei öffnet
	 * implementiert ActionListener
	 * 
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
			// neuer FileChooser
			filechooser = new JFileChooser();
			filechooser.setFileFilter(new FileNameExtensionFilter("JPG-Dateien", "jpg"));
			// Sobald eine Datei ausgewaehlt und auf Open gedrueckt wurde
			if (filechooser.showOpenDialog(CompressionGUI.this) == JFileChooser.APPROVE_OPTION) {
				// Ausgewaehltes File wird geholt
				File newfile = filechooser.getSelectedFile();
				try {
					// Bild-Datei wird in das ImgComponent gesetzt
					imgcomponent.setImage(newfile);
					// faengt IOException
				} catch (IOException err) {
					err.printStackTrace();
				}
			}
		}
	}

	/**
	 * CompressSaveListener Listener fuer den saveb-Button, welcher mit
	 * nebenlaeufigen Threads das Bild komprimiert und verschiedene Versionen davon
	 * abspeichert implementiert ActionListener
	 * 
	 * @author Michael Morandell
	 *
	 */
	private class CompressSaveListener implements ActionListener {

		/**
		 * actionPerformed
		 * 
		 * @param e,
		 *            ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//Wenn Bild gesetzt wurde
			if (imgcomponent.getImage() != null) {
				// Progressbar auf 0 setzen
				bar.setValue(0);
				// Alle Knoepfe deaktivieren
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						spinner.setEnabled(false);
						openb.setEnabled(false);
						compressb.setEnabled(false);
						saveb.setEnabled(false);

					}
				});
				double quality = 1.0;
				// Granulitaet wird aus dem JSpinner der GUI geholt
				double granulitaet = Math.round((double) spinner.getValue() * 100.0) / 100.0;
				// Berechnung der benoetigten Threads
				int anzahlThreads = (int) ((1 / granulitaet) + 1);
				bar.setMaximum(anzahlThreads);
				// Thread-Array
				Thread threads[] = new Thread[anzahlThreads];
				// For i in AnzahlThreads
				for (int i = 0; i < anzahlThreads; i++) {
					// runden
					final double q = Math.round(quality * 100.0) / 100.0;
					// neuer Thread
					threads[i] = new Thread() {
						/**
						 * Run-Methode
						 */
						public void run() {
							try {
								// Kompression + Abspeicherung
								JPGImageCompress.compressImage(imgcomponent.getImage(), filechooser.getSelectedFile()
										.toString().substring(0, filechooser.getSelectedFile().toString().length() - 4), q);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					};
					// Qualitaet wird vermindert
					quality -= granulitaet;
				}
				// Alle instanziierte Threads werden gestartet
				for (int i = 0; i < threads.length; i++) {
					threads[i].start();
				}
				// Thread control, welcher kontrolliert wieviele Threads schon fertig
				// komprimiert haben
				Thread control = new Thread() {
					/**
					 * Run-Methode
					 */
					public void run() {
						// Solange true
						while (true) {
							// Anzahl terminierter Threads
							int terminated = 0;
							// Eine halbe Sekunde wird geschlafen
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							// Zaehlen wieviele Threads bereits terminated sind
							for (int i = 0; i < threads.length; i++) {
								if (threads[i].getState().equals(State.TERMINATED)) {
									// progressbar wird gesetzt
									bar.setValue(terminated);
									terminated++;
								}
							}
							// Wenn alle Threads terminated sind
							if (terminated >= anzahlThreads) {
								bar.setValue(terminated);
								//Alle Knoepfe aktivieren
								spinner.setEnabled(true);
								openb.setEnabled(true);
								compressb.setEnabled(true);
								saveb.setEnabled(true);
								break;
							}
						}
					}
				};
				// Starten des Kontroll-Threads
				control.start();
			}
			else {
				//Fehlermeldung, wenn kein Bild vorhanden
				JOptionPane.showMessageDialog(CompressionGUI.this, "Bitte öffnen Sie zuerst ein Bild!");
			}
			
		}
	}

	/**
	 * CompressListener
	 * Listener fuer compressb
	 * Komprimiert das Bild schrittweise und gibt alle 300ms ein neues Bild an die GUI aus
	 * Die ProgressBar zeigt an, wieviele Bilder bereits angezeigt wurden, 
	 * im Verhaeltnis zu der Gesamtanzahl der Bilder
	 * @author Michael Morandell
	 *
	 */
	private class CompressListener implements ActionListener {
		/**
		 * action Performed
		 * @param e, ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//neuer Thread, welcher die GUI updatet
			new Thread() {
				/**
				 * run-Methode des Threads
				 */
				@Override
				public void run() {
					//Wenn Bild gesetzt wurde
					if (imgcomponent.getImage() != null) {
						//Buttons werden disabled
						spinner.setEnabled(false);
						openb.setEnabled(false);
						compressb.setEnabled(false);
						saveb.setEnabled(false);
						//Progress-Bar wird auf den Startzustand -> 0 gesetzt
						bar.setValue(0);
						double quality = 1.0;
						// Granulitaet wird aus dem JSpinner der GUI geholt
						double granulitaet = Math.round((double) spinner.getValue() * 100.0) / 100.0;
						// Berechnung der Anzahl der Bilder
						int anzahlImg = (int) ((1 / granulitaet) + 1);
						//Anzahl der Bilder wird als Maximum der ProgressBar gesetzt
						bar.setMaximum(anzahlImg);
						int offset0;
						int offset1;
						if (anzahlImg % 2 == 0) {
							offset1 = 1;
							offset0 = 0;
						}
						else {
							offset0 = 1;
							offset1 = 0;
						}
						//CompressionThread-Array, welches 2 CompressionThreads enthaelt
						CompressionThread [] threads = new CompressionThread[2];
						//aktuell, gesetztes Bild wird aus GUI geholt
						BufferedImage aktImage = imgcomponent.getImage();
						int i = -1;
						//Solange bis alle Bilder komprimiert und angezeigt wurden
						while(i < anzahlImg) {
							//beim Start der Schleife
							if (i == -1) {
								//Bild mit Qualitaet 1 wird in den Thread 0 geladen und komprimiert
								quality = Math.round(quality * 100.0) / 100.0;
								threads[0] = new CompressionThread(aktImage, quality);
								threads[0].start();
								// Qualitaet wird vermindert
								quality -= granulitaet;
								//Bild mit verminderter Qualitaet wird in den Thread 1 geladen und komprimiert
								quality = Math.round(quality * 100.0) / 100.0;
								threads[1] = new CompressionThread(aktImage, quality);
								threads[1].start();
								i = 0;
							}
							//nach dem ersten Schleifen-Durchlauf
							else {
								if (i % 2 == 0) {
									// Qualitaet wird vermindert
									quality -= granulitaet;
									try {
										//warten, dass Thread 1 sich beendet
										threads[1].join();
										//komprimiertes Bild wird an GUI ausgegeben
										imgcomponent.setImage(threads[1].getCompressedImage());
										i++;
										quality = Math.round(quality * 100.0) / 100.0;
										//Bild mit verminderter Qualitaet wird in den Thread 1 geladen und komprimiert
										if (i < anzahlImg-offset1) {
											threads[1] = new CompressionThread(aktImage, quality);
											threads[1].start();
										}
									} catch (InterruptedException e1) {
										e1.printStackTrace();
									}
								}
								else {
									quality -= granulitaet;
									try {
										//warten, dass Thread 0 sich beendet
										threads[0].join();
										//komprimiertes Bild wird an GUI ausgegeben
										imgcomponent.setImage(threads[0].getCompressedImage());
										i++;
										quality = Math.round(quality * 100.0) / 100.0;
										//Bild mit verminderter Qualitaet wird in den Thread 0 geladen und komprimiert
										if (i < anzahlImg-offset0) {
											threads[0] = new CompressionThread(aktImage, quality);
											threads[0].start();
										}
									} catch (InterruptedException e1) {
										e1.printStackTrace();
									}
								}
							}
							//setzen der ProgressBar
							bar.setValue(i);
							//Es wird fuer 300ms gewartet
							try {
								Thread.sleep(300);
							} catch (InterruptedException err) {
								err.printStackTrace();
							}
						}
						//Buttons werden am Ende wieder enabled
						spinner.setEnabled(true);
						openb.setEnabled(true);
						compressb.setEnabled(true);
						saveb.setEnabled(true);					
					}
					else {
						//Fehlermeldung, wenn kein Bild vorhanden
						JOptionPane.showMessageDialog(CompressionGUI.this, "Bitte öffnen Sie zuerst ein Bild!");
					}
				}
				//Thread wird gestartet
			}.start();
		}
	}
}

