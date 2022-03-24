package net.tfobz.tunnel.client;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

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
@SuppressWarnings("serial")
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
	private JPanel available;
	private JPanel active;
	private JPanel visitors;
	private JLabel title_label;
	public JLabel guides_label;
	private JLabel visitors_label;
	private JLabel visits_label;
	public JLabel available_visitors_label;
	private JLabel status_label;
	private JButton request_button;
	private JButton finish_button;
	private JTextField visitors_text;
	public JTextArea status_txtarea;
	private JScrollPane status_scrollp;
	//Fonts
	private Font bold_font = new Font("Sans Serif", Font.BOLD, 17);
	private Font default_font = new Font("Sans Serif", 0, 17);
	private Font title_font = new Font("Sans Serif", Font.BOLD, 22);
	//Border
	private Border loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
	//Buttonlistener
	private ButtonListener buttonlistener = new ButtonListener();
	//ListModel fuer auswaehlbare strings in JList
	public DefaultListModel<String> listModel;
	//JList
	public JList<String> visitors_list;
	//Scheduled Executor
	private ScheduledExecutorService schedExecutor;
	
	/**
	 * ClientForm-Konstruktor
	 * @param title, String, the title of the gui window
	 */
	public ClientForm(String title) {
		//GuidesMonitor angelegt
		this.guidesMonitor = new GuidesMonitor(this);
		
		//JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		int height = 595;
		int width = 550;
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2, 
				(Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2, width, height);
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
		available = new JPanel();
		available.setBounds(10, 55, 180, 130);
		available.setBorder(loweredBevelBorder);
		available.setLayout(null);
		this.getContentPane().add(available);
		
		//guides label
		guides_label = new JLabel();
		guides_label.setText("Available guides: 4");
		guides_label.setFont(bold_font);
		guides_label.setBounds(10, 10, 160, 20);
		available.add(guides_label);
		
		//visitors label
		visitors_label = new JLabel();
		visitors_label.setText("Visitors:");
		visitors_label.setFont(bold_font);
		visitors_label.setBounds(10, 40, 66, 20);
		available.add(visitors_label);
		
		//visitor textfield
		visitors_text = new JTextField();
		visitors_text.setFont(default_font);
		visitors_text.setBounds(90, 40, 74, 22);
		available.add(visitors_text);
		
		//request button
		request_button = new JButton();
		request_button.setFont(bold_font);
		request_button.setText("Request visit");
		request_button.setBounds(10, 80, 160, 40);
		request_button.addActionListener(buttonlistener);
		request_button.setMnemonic(KeyEvent.VK_Q);
		available.add(request_button);
		
		//visits panel
		active = new JPanel();
		active.setBounds(10, 200, 180, 250);
		active.setBorder(loweredBevelBorder);
		active.setLayout(null);
		this.getContentPane().add(active);
		
		//visits label
		visits_label = new JLabel();
		visits_label.setFont(bold_font);
		visits_label.setText("Active visits:");
		visits_label.setBounds(10, 10, 160, 20);
		active.add(visits_label);
		
		//ListModel mit auswaehlbaren Strings für JList
		listModel = new DefaultListModel<String>();
		
		//visitors_list
		visitors_list = new JList<String>(listModel);
		visitors_list.setFont(bold_font);
		visitors_list.setBounds(10, 40, 160, 150);
		active.add(visitors_list);
		
		//finish button
		finish_button = new JButton();
		finish_button.setFont(bold_font);
		finish_button.setText("Finish visit");
		finish_button.setBounds(10, 200, 160, 40);
		finish_button.addActionListener(buttonlistener);
		finish_button.setMnemonic(KeyEvent.VK_F);
		active.add(finish_button);
		
		//avaiable visitors label 
		available_visitors_label = new JLabel();
		available_visitors_label.setFont(bold_font);
		available_visitors_label.setText("Available Visitors: 50");
		available_visitors_label.setBounds(20, 460, 180, 20);
		this.getContentPane().add(available_visitors_label);
		
		//JPanel visitors
		visitors = new JPanel();
		visitors.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		visitors.setBounds(10, 490, 180, 60);
		this.getContentPane().add(visitors);
		
		//textlabel
		status_label = new JLabel();
		status_label.setFont(bold_font);
		status_label.setText("Status:");
		status_label.setBounds(205, 27, 80, 20);
		this.getContentPane().add(status_label);
		
		//textarea
		status_txtarea = new JTextArea();
		//automatische newline, wenn text zu lang
		status_txtarea.setLineWrap(true);
		status_txtarea.setWrapStyleWord(false);
		status_txtarea.setEditable(false);
		//scrollt immer ans Ende
		DefaultCaret caret = (DefaultCaret)status_txtarea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//Font
		status_txtarea.setFont(default_font);
		
		//scrollpane
		status_scrollp = new JScrollPane(status_txtarea);
		//vertikale scorllbar, wenn nötig
		status_scrollp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//horinzontale scrollbar nie
		status_scrollp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		status_scrollp.setBounds(205, 55, 328, 495);
		this.getContentPane().add(status_scrollp);
		
		//defaultButton
		this.getRootPane().setDefaultButton(request_button);
	
		//update der Besucheranzahl
		schedExecutor = Executors.newSingleThreadScheduledExecutor();
		Thread t = new ClientThread(0, ClientForm.this, guidesMonitor);
		schedExecutor.scheduleAtFixedRate((Runnable)t, 1, 1, TimeUnit.SECONDS);
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
			//request button
			if (e.getSource().equals(request_button)) {
				try {
					//anzahl wird von Textfeld geholt
					int anzahl = Integer.parseInt(visitors_text.getText());
					//Thread wird erstellt
					Thread t = new ClientThread(anzahl, ClientForm.this, guidesMonitor);
					//Thread der EventQuene übergeben
					t.start();
					//EventQueue.invokeLater((Runnable)t);
					//leeren des Textfeldes
					visitors_text.setText("");
				} catch (Exception err) {
					System.out.println("Ungültige Eingabe!");
				}
				
			}
			//finish button
			if (e.getSource().equals(finish_button)) {
				try {
					//holt selected value
					String [] strings = visitors_list.getSelectedValue().split(" ");
					//anzahl wird geholt
					int anzahl = Integer.parseInt(strings[0]);
					//Thread wird erstellt
					Thread t = new ClientThread(-anzahl, ClientForm.this, guidesMonitor);
					//Thread der EventQuene übergeben
					t.start();
					//EventQueue.invokeLater((Runnable)t);
					//leeren des Textfeldes
					visitors_text.setText("");
				} catch (Exception err) {
					System.out.println("Bitte wählen sie eine aktive visit aus!");
				}
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
		ClientForm entrance1 = new ClientForm("Entrance 1");
		entrance1.setVisible(true);
		ClientForm entrance2 = new ClientForm("Entrance 2");
		entrance2.setVisible(true);
	}
}