/**
 * Slot machine - Presentation layer
 */
package net.tfobz.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class consists of the visual components for the slot machine.
 * 
 * @author mdw
 * 
 */
public class SlotMachineView extends JFrame
{
	private static final long serialVersionUID = -5210290866971834513L;
	
	/** Button "Start" */
	private JButton buttonStart;
	/** Button "Stop" */
	private JButton buttonStop;
	/** Label "Points" */
	private JLabel labelPoints;
	/** Label for slot 1 */
	private JLabel slot1;
	/** Label for slot 2 */
	private JLabel slot2;
	/** Label for slot 3 */
	private JLabel slot3;
	
	/**
	 * This constructor sets up the slot machine view
	 */
	private SlotMachineView() {
		// Higher window part = Slots
		JPanel slots = new JPanel(new GridLayout(1,3));
		slot1 = new JLabel();
		slot2 = new JLabel();
		slot3 = new JLabel();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		setImages(
			getToolkit().getImage("images/1.jpg"),
			getToolkit().getImage("images/1.jpg"),
			getToolkit().getImage("images/1.jpg")
		);
		
		// Lower window part
		JPanel lowerPart = new JPanel(new GridLayout(1, 2));
		// Buttons
		JPanel panel = new JPanel(new GridLayout(2,1));
		buttonStart = new JButton("Start");
		buttonStart.setName("start");
		buttonStop = new JButton("Stop");
		buttonStop.setName("stop");
		panel.add(buttonStart);
		panel.add(buttonStop);
		lowerPart.add(panel);
		// Points
		panel = new JPanel(new FlowLayout());
		JLabel label = new JLabel("Points: ");
		labelPoints = new JLabel();
		panel.add(label);
		panel.add(labelPoints);
		lowerPart.add(panel);
		
		// Unify higher and lower part in the content pane
		getContentPane().setLayout(new GridLayout(2,1));	
		getContentPane().add(slots);
		getContentPane().add(lowerPart);
		
		// Frame properties
		setTitle("Slot machine");
		setResizable(false);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// TODO: Implement the SlotMachineView pattern
	private static class SlotMachineViewHolder {
		private final static SlotMachineView INSTANCE = new SlotMachineView();
	}
	
	public static SlotMachineView getInstance() {
		 return SlotMachineViewHolder.INSTANCE;
	}
	
	/**
	 * Gets the "Start" button
	 * @return Button
	 */
	public JButton getButtonStart() {
		return buttonStart;
	}
	
	/**
	 * Gets the "Stop" button
	 * @return Button
	 */
	public JButton getButtonStop() {
		return buttonStop;
	}
	
	/**
	 * Gets the "points" label
	 * @return Label
	 */
	public JLabel getLabelPoints() {
		return labelPoints;
	}
	
	/**
	 * Sets new images for the slots
	 * @param image1 Image for slot 1
	 * @param image2 Image for slot 2
	 * @param image3 Image for slot 3
	 */
	public void setImages(Image image1, Image image2, Image image3) {
		// Set the new images
		if (image1 != null)
			slot1.setIcon(new ImageIcon(image1));
		if (image2 != null)
			slot2.setIcon(new ImageIcon(image2));
		if (image3 != null)
			slot3.setIcon(new ImageIcon(image3));
	}
}
