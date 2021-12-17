package net.tfobz.simpleRssReader;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;

import javax.swing.*;

public class AddUrlGUI extends JDialog {
	private JLabel label = null;
	private JTextField text = null;
	private JButton button = null;
	
	public AddUrlGUI(JFrame owner) {
		super(owner);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int height = 200;
		int width = 550;
		this.setBounds(owner.getX() + ((owner.getWidth() - width) / 2),
				owner.getY() + ((owner.getHeight() - height) / 2), width, height);
		this.setTitle("Add URL");
		this.setResizable(false);
		this.setModal(true);
		
		// Ausschalten des Layoutmgr
		this.getContentPane().setLayout(null);
		
		label = new JLabel();
		label.setBounds(20, (this.getHeight()/2)-30, 50, 20);
		label.setText("URL:");
		label.setFont(SimpleRssReader.defaultFont_Bold);
		this.getContentPane().add(label);
		
		text = new JTextField();
		text.setBounds(70, (this.getHeight()/2)-35, 380, 30);
		text.setFont(new Font("Sans Serif", 0, 17));
		this.getContentPane().add(text);
		
		button = new JButton();
		button.setBounds(455,(this.getHeight()/2)-35, 80, 30);
		button.setFont(SimpleRssReader.defaultFont_Bold);
		button.setText("Add");
		button.addActionListener(new ButtonListener());
		this.getContentPane().add(button);
		
		//default-Button
		this.getRootPane().setDefaultButton(button);
	}
	
	/**
	 * ButtonListener
	 * @author Michael Morandell
	 *
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * actionPerformed
		 * @param e, ActionEvent
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				String link = text.getText();
				if (!link.contains("http://") && !link.contains("https://")) {
					link = "http://"+link;
				}
				URL url = new URL(link);
				InputStream stream = url.openStream();
				SimpleRssReader.urls.add(url);
				System.out.println("URL-List:");
				for (int i = 0; i < SimpleRssReader.urls.size(); i++) {
					System.out.println(i + ": "+SimpleRssReader.urls.get(i).toString());
				}
				setVisible(false);
			} catch (Exception err) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(AddUrlGUI.this, "Url kann nicht geladen werden!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
