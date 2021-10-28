package net.tfobz.minmax;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class FindGUI extends JFrame{
	private JButton sbutton = null;
	private JLabel [] labels = new JLabel[3];
	public static JTextField [] texts = new JTextField[3];
	public static JProgressBar [] progressBars = new JProgressBar[3];
	//10 Millionen
	private final int anzahl = 10000000;
	private int [] arr = null;
	
	public FindGUI () {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(650, 250, 600, 200);
		this.setResizable(false);
		this.setTitle("MinMaxAvgSearch");
		this.getContentPane().setLayout(null);
		
		sbutton = new JButton();
		sbutton.setBounds((this.getWidth()-160)/2, 15, 160, 30);
		sbutton.setText("Start searching");
		sbutton.setFont(new Font(null, Font.BOLD, 17));
		sbutton.addActionListener(new StartListener());
		this.getContentPane().add(sbutton);
		
		labels[0] = new JLabel();
		labels[0].setBounds(10, 70, 50, 20);
		labels[0].setText("Min:");
		labels[0].setFont(new Font(null, Font.BOLD, 17));
		labels[0].setHorizontalAlignment(SwingConstants.RIGHT);
		this.getContentPane().add(labels[0]);
		
		labels[1] = new JLabel();
		labels[1].setBounds(10, 100, 50, 20);
		labels[1].setText("Max:");
		labels[1].setFont(new Font(null, Font.BOLD, 17));
		labels[1].setHorizontalAlignment(SwingConstants.RIGHT);
		this.getContentPane().add(labels[1]);
		
		labels[2] = new JLabel();
		labels[2].setBounds(10, 130, 50, 20);
		labels[2].setText("Avg:");
		labels[2].setFont(new Font(null, Font.BOLD, 17));
		labels[2].setHorizontalAlignment(SwingConstants.RIGHT);
		this.getContentPane().add(labels[2]);
		
		texts[0] = new JTextField();
		texts[0].setBounds(70, 70, 100, 20);
		texts[0].setFont(new Font(null, 0, 17));
		texts[0].setEditable(false);
		this.getContentPane().add(texts[0]);
		
		texts[1] = new JTextField();
		texts[1].setBounds(70, 100, 100, 20);
		texts[1].setFont(new Font(null, 0, 17));
		texts[1].setEditable(false);
		this.getContentPane().add(texts[1]);
		
		texts[2] = new JTextField();
		texts[2].setBounds(70, 130, 100, 20);
		texts[2].setFont(new Font(null, 0, 17));
		texts[2].setEditable(false);
		this.getContentPane().add(texts[2]);
		
		progressBars[0] = new JProgressBar(0, 100);
		progressBars[0].setBounds(180, 70, 400, 20);
		progressBars[0].setStringPainted(true);
		this.getContentPane().add(progressBars[0]);
		
		progressBars[1] = new JProgressBar(0, 100);
		progressBars[1].setBounds(180, 100, 400, 20);
		progressBars[1].setStringPainted(true);
		this.getContentPane().add(progressBars[1]);
		
		progressBars[2] = new JProgressBar(0, 100);
		progressBars[2].setBounds(180, 130, 400, 20);
		progressBars[2].setStringPainted(true);
		this.getContentPane().add(progressBars[2]);
	}
	
	private class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			arr = randomIntArray();
			FindMin min = new FindMin(arr);
			FindMax max = new FindMax(arr);
			FindAvg avg = new FindAvg(arr);
			min.start();
			max.start();
			avg.start();
		}
		
	}
	/**
	 * Generates an intArray with length anzahl mit random Elementen von 0 bis Integer.MaxValue-1
	 * @return a random int Array
	 */
	private int[] randomIntArray() {
		int min = 0;
		int max = Integer.MAX_VALUE-1;
		int range = max - min + 1;
		int [] ret = new int[this.anzahl];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int)(Math.random()*(range)+min);
		}
		return ret;
	}
}
