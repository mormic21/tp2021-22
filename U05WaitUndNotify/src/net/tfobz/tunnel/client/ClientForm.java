package net.tfobz.tunnel.client;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Diese Klasse erstellt die Benutzerschnittstelle und den GuidesMonitor zur 
 * Verwaltung der Gruppenführer pro Eingang. Sie enthält auch die 
 * Ereignisbehandlungsmethoden für die beiden Knöpfe. In diesen Methoden werden 
 * die Objekte vom Typ ClientThread zur Behandlung der Clientanfragen angelegt 
 * und die Threads gestartet.<br><br>
 * <b>Ereignisbehandlungsmethode Besichtigung anfordern</b><br>
 * Diese Methode kontrolliert zuerst, ob eine Besucherzahl ins Textfeld
 * eingegeben wurde und konvertiert den Inhalt in eine Zahl. Diese Zahl
 * darf nicht größer sein als das maximale Fassungsvermögen des Tunnels.
 * Dann wird der ClientThread gestartet, dem diese Besucheranzahl und
 * die Referenzen auf das ClientForm sowie auf den GuidesMonitor
 * übergeben werden.<br><br>
 * <b>Ereignisbehandlungsmethode Besichtigung beenden</b><br>
 * Zuerst wird kontrolliert ob es überhaupt Aktive Besichtigungen gibt,
 * welche von diesem Eingang aus den Tunnel betreten haben. Sind solche
 * vorhanden, dann wird kontrolliert, ob eine aktive Besichtigung 
 * ausgewählt wurde. Ist dies der Fall so wird aus dem Text des ausgewählten
 * JList-Eintrages die Anzahl der Besucher ermittelt und in eine Zahl
 * konvertiert. Dann wird der ClientThred gestartet, dem diese negative (!)
 * Anzahl und Referenzen auf ClientForm und GuidesMonitor 
 * übergeben werden
 */
public class ClientForm extends JFrame {

	/**
	 * Monitor durch welchen am Eingang ein Führer reserviert werden kann
	 */
	private GuidesMonitor guidesMonitor = null;
	/**
	 * Modell zur Verwaltung der Inhalte der JList
	 */
	protected DefaultListModel<String> mActiveVisits = null;
	
	//JFrame-Variables
	private JPanel avaiable;
	private JPanel active;
	private JPanel visitors;
	private JLabel title_label;
	private JLabel guides_label;
	private JLabel visitors_label;
	private JLabel visits_label;
	private JLabel avaible_visitors_label;
	private JLabel status_label;
	private JButton request_button;
	private JButton finish_button;
	private JTextField visitors_text;
	private JTextArea visits_txtarea;
	private JTextArea status_txtarea;
	private JScrollPane status_scrollp;
	//Fonts
	private Font bold_font = new Font("Sans Serif", Font.BOLD, 17);
	private Font default_font = new Font("Sans Serif", 0, 17);
	private Font title_font = new Font("Sans Serif", Font.BOLD, 22);
	//Border
	private Border loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
	//Buttonlistener
	ButtonListener buttonlistener = new ButtonListener();
	
	/**
	 * ClientForm-Konstruktor
	 */
	public ClientForm() {
		//GuidesMonitor angelegt
		this.guidesMonitor = new GuidesMonitor(this);
		
		//JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Entrance 1");
		int height = 600;
		int width = 550;
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2, 
				(Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2,
						width, height);
		this.setResizable(false);
		//Layoutmgr null
		this.getContentPane().setLayout(null);
		
		//Titel
		title_label = new JLabel();
		title_label.setFont(title_font);
		title_label.setText(this.getTitle());
		title_label.setBounds(10, 10, 120, 25);
		this.getContentPane().add(title_label);
	  
		//avaiable panel
		avaiable = new JPanel();
		avaiable.setBounds(10, 55, 180, 130);
		avaiable.setBorder(loweredBevelBorder);
		avaiable.setLayout(null);
		this.getContentPane().add(avaiable);
		
		//guides label
		guides_label = new JLabel();
		guides_label.setText("Avaiable guides: 2");
		guides_label.setFont(bold_font);
		guides_label.setBounds(10, 10, 160, 20);
		avaiable.add(guides_label);
		
		//visitors label
		visitors_label = new JLabel();
		visitors_label.setText("Visitors:");
		visitors_label.setFont(bold_font);
		visitors_label.setBounds(10, 40, 66, 20);
		avaiable.add(visitors_label);
		
		//visitor textfield
		visitors_text = new JTextField();
		visitors_text.setFont(default_font);
		visitors_text.setBounds(90, 40, 74, 22);
		avaiable.add(visitors_text);
		
		//request button
		request_button = new JButton();
		request_button.setFont(bold_font);
		request_button.setText("Request visit");
		request_button.setBounds(10, 80, 160, 40);
		request_button.addActionListener(buttonlistener);
		avaiable.add(request_button);
		
		//visits panel
		active = new JPanel();
		active.setBounds(10, 200, 180, 200);
		active.setBorder(loweredBevelBorder);
		active.setLayout(null);
		this.getContentPane().add(active);
		
		//defaultButton
		this.getRootPane().setDefaultButton(request_button);
	}
	
	/**
	 * private class ButtonListener
	 * reagiert auf das betaetigen der buttons
	 * implements ActionListener
	 * @author Michael Morandell
	 *
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * actionPerformed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(request_button)) {
				System.out.println("request_button");
			}
			if (e.getSource().equals(finish_button)) {
				System.out.println("finish_button");
			}
			
		}
		
	}

	/**
	 * Legt das Formular an und macht es sichtbar. Beim Anlegen des Forumulas
	 * wird auch der GuidesMonitor angelegt. Nachdem das Formular angelegt wurde,
	 * werden in Abständen von einer Sekunde Serveranfragen geschickt zur 
	 * Ermittlung der verfügbaren Besucher, d. h. der Server antwortet und
	 * liefert die Anzahl je Besucheranzahl zurück die noch in den Tunnel 
	 * eingelassen werden kann
	 * @param args
	 */
	public static void main(String[] args) {
		ClientForm gui = new ClientForm();
		gui.setVisible(true);
	}
	
}