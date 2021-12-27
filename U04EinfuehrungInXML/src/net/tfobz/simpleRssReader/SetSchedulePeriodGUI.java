package net.tfobz.simpleRssReader;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

/**
 * SetSchedulePeriodGUI
 * realisiert eine GUI zur Eingabe der Schedule-Period
 * erbt von JDialog
 * @author Michael Morandell
 *
 */
@SuppressWarnings("serial")
public class SetSchedulePeriodGUI extends JDialog{
	//Membervariablen
	private JLabel label = null;
	private JSpinner spinner = null;
	private JButton button = null;
	private int schedulePeriod = 0;
	private boolean dialogApproved = false;
	
	/**
	 * SetSchedulePeriodGUI-Konstrukor
	 * @param owner, JFrame
	 */
	public SetSchedulePeriodGUI(JFrame owner) {
		//super-Konstruktor
		super(owner);
		//hide on close
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int height = 200;
		int width = 550;
		this.setBounds(owner.getX() + ((owner.getWidth() - width) / 2),
				owner.getY() + ((owner.getHeight() - height) / 2), width, height);
		this.setTitle("Set schedule period");
		this.setResizable(false);
		//modaler dialog
		this.setModal(true);
		
		// Ausschalten des Layoutmgr
		this.getContentPane().setLayout(null);
		
		//JLabel
		label = new JLabel();
		label.setBounds(20, (this.getHeight()/2)-30, 250, 20);
		label.setText("Schedule Period in Sekunden: ");
		label.setFont(SimpleRssReader.defaultFont_Bold);
		this.getContentPane().add(label);
		
		//neues Spinner-Model
		SpinnerNumberModel model = new SpinnerNumberModel(10, 5, 10000, 1);
		//JSpinner
		spinner = new JSpinner(model);
		spinner.setBounds(280, (this.getHeight()/2)-35, 100, 30);
		spinner.setFont(new Font("Sans Serif", 0, 17));
		this.getContentPane().add(spinner);
		
		//OK-Button
		button = new JButton();
		button.setBounds(415,(this.getHeight()/2)-35, 80, 30);
		button.setFont(SimpleRssReader.defaultFont_Bold);
		button.setText("OK");
		button.addActionListener(new ButtonListener());
		this.getContentPane().add(button);
		
		//default-Button
		this.getRootPane().setDefaultButton(button);
	}
	
	/**
	 * getSchedulePeriod
	 * @return the schedulePeriod
	 */
	public int getSchedulePeriod() {
		return schedulePeriod;
	}

	/**
	 * isDialogApproved
	 * @return the dialogApproved
	 */
	public boolean isDialogApproved() {
		return dialogApproved;
	}

	/**
	 * ButtonListener
	 * reagiert auf das druecken des OK-Buttons
	 * implementiert ActionListener
	 * @author Michael Morandell
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * actionPerformed
		 * @param e, ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//Integer wird geholt und gesetzt
			schedulePeriod = (int) spinner.getValue();
			//dieser Dialog wurde bestaetigt
			dialogApproved = true;
			setVisible(false);
		}
	}
}
