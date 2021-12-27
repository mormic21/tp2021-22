package net.tfobz.simpleRssReader;
import java.awt.Font;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

/**
 * AddUrlGUI
 * realisiert die Eingabe einer Url, welche dann in die Liste in SimpleRssReader gesetzt wird
 * erbt von JDialog
 * @author Michael Morandell
 */
@SuppressWarnings("serial")
public class AddUrlGUI extends JDialog {
	//Membervariablen
	private JLabel label = null;
	private JTextField text = null;
	private JButton button = null;
	
	/**
	 * AddUrlGUI-Konstruktor
	 * @param owner, JFrame
	 */
	public AddUrlGUI(JFrame owner) {
		//super-Konstruktor
		super(owner);
		//hide on close
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int height = 200;
		int width = 550;
		this.setBounds(owner.getX() + ((owner.getWidth() - width) / 2),
				owner.getY() + ((owner.getHeight() - height) / 2), width, height);
		this.setTitle("Add URL");
		this.setResizable(false);
		//modaler Dialog
		this.setModal(true);
		
		// Ausschalten des Layoutmgr
		this.getContentPane().setLayout(null);
		
		//JLablel
		label = new JLabel();
		label.setBounds(20, (this.getHeight()/2)-30, 50, 20);
		label.setText("URL:");
		label.setFont(SimpleRssReader.defaultFont_Bold);
		this.getContentPane().add(label);
		
		//jTextfield
		text = new JTextField();
		text.setBounds(70, (this.getHeight()/2)-35, 380, 30);
		text.setFont(new Font("Sans Serif", 0, 17));
		this.getContentPane().add(text);
		
		//JButton "add"
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
	 * reagiert auf den Add-Button 
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
			try {
				//Text wird aus dem Textfeld geholt
				String link = text.getText();
				//wenn kein https:// oder http:// vorhanden ist, wird das hinzugefuegt
				if (!link.contains("http://") && !link.contains("https://")) {
					link = "http://"+link;
				}
				//URL-Objekt wird mit eingegebener URL-Adresse gebaut
				URL url = new URL(link);
				//Link wird versucht zu oeffnen. Wenn nicht oeffenbar, wird eine Exception geworfen und eine Fehlermelung angezigt
				url.openStream();
				//url wird zur Arraylist in SimpleRssReader hinzugefuegt
				SimpleRssReader.urls.add(url);
				//Ausgabe der Link-Liste an Konsole
				System.out.println("URL-List:");
				for (int i = 0; i < SimpleRssReader.urls.size(); i++) {
					System.out.println(i + ": "+SimpleRssReader.urls.get(i).toString());
				}
				//Dialog wird geschlossen
				setVisible(false);
				//bei Fehler -> Fehlermeldung an Benutzer
			} catch (Exception err) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(AddUrlGUI.this, "Url kann nicht geladen werden!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}