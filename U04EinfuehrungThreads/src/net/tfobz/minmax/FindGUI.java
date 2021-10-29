package net.tfobz.minmax;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

/**
 * FindGUI
 * Realisiert eine GUI, welche die Klassen FindMin, FindMax, FindAvg benutzt.
 * erbt von JFrame
 * @author Michael Morandell
 *
 */
public class FindGUI extends JFrame {
	//Membervariablen fuer die GUI
	private JButton sbutton = null;
	private JLabel [] labels = new JLabel[3];
	public static JTextField [] texts = new JTextField[3];
	public static JProgressBar [] progressBars = new JProgressBar[3];
	//10 Millionen
	private final int anzahl = 10000000;
	private int [] arr = null;
	
	/**
	 * FindGUI-Konstruktor
	 * setzt den Anfangszustand der GUI
	 */
	public FindGUI () {
		//Exit on close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(650, 250, 600, 200);
		this.setResizable(false);
		//Titel
		this.setTitle("MinMaxAvgSearch");
		this.getContentPane().setLayout(null);
		//start-Button
		sbutton = new JButton();
		sbutton.setBounds((this.getWidth()-160)/2, 15, 160, 30);
		sbutton.setText("Start searching");
		sbutton.setFont(new Font(null, Font.BOLD, 17));
		sbutton.addActionListener(new StartListener());
		this.getContentPane().add(sbutton);
		//Min-Label
		labels[0] = new JLabel();
		labels[0].setBounds(10, 70, 50, 20);
		labels[0].setText("Min:");
		labels[0].setFont(new Font(null, Font.BOLD, 17));
		labels[0].setHorizontalAlignment(SwingConstants.RIGHT);
		this.getContentPane().add(labels[0]);
		//Max-Label
		labels[1] = new JLabel();
		labels[1].setBounds(10, 100, 50, 20);
		labels[1].setText("Max:");
		labels[1].setFont(new Font(null, Font.BOLD, 17));
		labels[1].setHorizontalAlignment(SwingConstants.RIGHT);
		this.getContentPane().add(labels[1]);
		//Avg-Label
		labels[2] = new JLabel();
		labels[2].setBounds(10, 130, 50, 20);
		labels[2].setText("Avg:");
		labels[2].setFont(new Font(null, Font.BOLD, 17));
		labels[2].setHorizontalAlignment(SwingConstants.RIGHT);
		this.getContentPane().add(labels[2]);
		//Min-Textfeld
		texts[0] = new JTextField();
		texts[0].setBounds(70, 70, 100, 20);
		texts[0].setFont(new Font(null, 0, 17));
		texts[0].setEditable(false);
		this.getContentPane().add(texts[0]);
		//Max-Textfeld
		texts[1] = new JTextField();
		texts[1].setBounds(70, 100, 100, 20);
		texts[1].setFont(new Font(null, 0, 17));
		texts[1].setEditable(false);
		this.getContentPane().add(texts[1]);
		//Avg-Textfeld
		texts[2] = new JTextField();
		texts[2].setBounds(70, 130, 100, 20);
		texts[2].setFont(new Font(null, 0, 17));
		texts[2].setEditable(false);
		this.getContentPane().add(texts[2]);
		//Min-ProgressBar
		progressBars[0] = new JProgressBar(0, 100);
		progressBars[0].setBounds(180, 70, 400, 20);
		progressBars[0].setStringPainted(true);
		this.getContentPane().add(progressBars[0]);
		//Max-ProgressBar
		progressBars[1] = new JProgressBar(0, 100);
		progressBars[1].setBounds(180, 100, 400, 20);
		progressBars[1].setStringPainted(true);
		this.getContentPane().add(progressBars[1]);
		//Avg-ProgressBar
		progressBars[2] = new JProgressBar(0, 100);
		progressBars[2].setBounds(180, 130, 400, 20);
		progressBars[2].setStringPainted(true);
		this.getContentPane().add(progressBars[2]);
	}
	
	/**
	 * StartListener
	 * reagiert auf das druecken des Buttons
	 * implementiert ActionListener
	 * @author Michael Morandell
	 *
	 */
	private class StartListener implements ActionListener {
		/**
		 * actionPerformed
		 * Erzeugt ein random Int-Array, erzeugt die notwendigen Objekte und startet die jeweiligen Threads, um die Suchlaeufe zu starten
		 * @param e, ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//neues random int-array
			arr = randomIntArray();
			//Objekte
			FindMin min = new FindMin(arr);
			FindMax max = new FindMax(arr);
			FindAvg avg = new FindAvg(arr);
			//Threads werden gestartet
			min.start();
			max.start();
			avg.start();
		}

		/**
		 * Generates an intArray with length anzahl mit random Elementen von 0 bis Integer.MaxValue-1
		 * @return a random int Array
		 */
		private int[] randomIntArray() {
			//minimum des Zufallsbereichs
			int min = 0;
			//maximum des Zufallsbereichs
			int max = Integer.MAX_VALUE-1;
			int range = max - min + 1;
			//neues Array
			int [] ret = new int[anzahl];
			//Fuellung mit random Werten
			for (int i = 0; i < ret.length; i++) {
				ret[i] = (int)(Math.random()*(range)+min);
			}
			//return
			return ret;
		}
	}
}
