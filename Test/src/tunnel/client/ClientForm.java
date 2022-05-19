package tunnel.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.*;



/**
 * Diese Klasse erstellt die Benutzerschnittstelle und den GuidesMonitor zur
 * Verwaltung der Gruppenführer pro Eingang. Sie enthält auch die
 * Ereignisbehandlungsmethoden für die beiden Knöpfe. In diesen Methoden werden
 * die Objekte vom Typ ClientThread zur Behandlung der Clientanfragen angelegt
 * und die Threads gestartet.<br>
 * <br>
 * <b>Ereignisbehandlungsmethode Besichtigung anfordern</b><br>
 * Diese Methode kontrolliert zuerst, ob eine Besucherzahl ins Textfeld
 * eingegeben wurde und konvertiert den Inhalt in eine Zahl. Diese Zahl darf
 * nicht größer sein als das maximale Fassungsvermögen des Tunnels. Dann wird
 * der ClientThread gestartet, dem diese Besucheranzahl und die Referenzen auf
 * das ClientForm sowie auf den GuidesMonitor übergeben werden.<br>
 * <br>
 * <b>Ereignisbehandlungsmethode Besichtigung beenden</b><br>
 * Zuerst wird kontrolliert ob es überhaupt Aktive Besichtigungen gibt, welche
 * von diesem Eingang aus den Tunnel betreten haben. Sind solche vorhanden, dann
 * wird kontrolliert, ob eine aktive Besichtigung ausgewählt wurde. Ist dies der
 * Fall so wird aus dem Text des ausgewählten JList-Eintrages die Anzahl der
 * Besucher ermittelt und in eine Zahl konvertiert. Dann wird der ClientThred
 * gestartet, dem diese negative (!) Anzahl und Referenzen auf ClientForm und
 * GuidesMonitor übergeben werden
 */
public class ClientForm extends JFrame {

	/**
	 * Monitor durch welchen am Eingang ein Führer reserviert werden kann
	 */
	private GuidesMonitor guidesMonitor = null;
	/**
	 * Modell zur Verwaltung der Inhalte der JList
	 */
	protected DefaultListModel<String> mActiveVisits = new DefaultListModel<String>();

	/**
	 * Legt das Formular an und macht es sichtbar. Beim Anlegen des Forumulas wird
	 * auch der GuidesMonitor angelegt. Nachdem das Formular angelegt wurde, werden
	 * in Abständen von einer Sekunde Serveranfragen geschickt zur Ermittlung der
	 * verfügbaren Besucher, d. h. der Server antwortet und liefert die Anzahl je
	 * Besucheranzahl zurück die noch in den Tunnel eingelassen werden kann
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ClientForm f = new ClientForm(1);
		f.setVisible(true);
		ClientForm k = new ClientForm(2);
		k.setVisible(true);
	}

	/** 
	 */
	private JTextField visitor;
	private JButton rvisit;
	private JButton fvisit;
	private JLabel title;
	public JLabel aguides;
	private JLabel avisitors;
	private JLabel actvisits;
	private JLabel status;
	public JLabel availvisitors;
	private JScrollPane scrollvisit;

	public JList pzwei;
	private JPanel eins;
	private JPanel zwei;
	private JPanel drei;
	public JTextArea editor;

	private final ScheduledExecutorService exec;
	
	public ClientForm(int nummer) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(10, 10, 400, 500);
		this.setLayout(null);
		this.setTitle("Entrance "+nummer);
		this.setResizable(false);

		this.guidesMonitor = new GuidesMonitor(this);

		title = new JLabel();
		title.setBounds(10, 10, 100, 40);
		title.setText("Entrance "+nummer);
		title.setFont(new Font(null, Font.BOLD, 17));
		this.getContentPane().add(title);

		eins = new JPanel();
		TitledBorder tBorder = new TitledBorder(new LineBorder(Color.black, 1), "");
		eins.setBorder(tBorder);
		eins.setBounds(5, 50, 150, 100);

		aguides = new JLabel();
		aguides.setBounds(10, 55, 120, 20);
		if (guidesMonitor != null) {
			aguides.setText("Available guides: " + 4);
		}
		eins.add(aguides);
		this.getContentPane().add(aguides);

		avisitors = new JLabel();
		avisitors.setBounds(10, 80, 100, 20);
		avisitors.setText("Visitors: ");
		eins.add(avisitors);
		this.getContentPane().add(avisitors);

		visitor = new JTextField();
		visitor.setBounds(120, 80, 20, 20);
		eins.add(visitor);
		this.getContentPane().add(visitor);

		//der knopf um einen neuen besuch zu starten
		rvisit = new JButton();
		rvisit.setBounds(10, 110, 130, 30);
		rvisit.setText("Request visit");
		rvisit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (visitor.getText() != null) {

						new ClientThread(Integer.parseInt(visitor.getText()), ClientForm.this, guidesMonitor).start();
						visitor.setText("");

					}
				} catch (NumberFormatException ex) {
					visitor.setText("");
				}

			}
		});
		eins.add(rvisit);
		this.getContentPane().add(rvisit);

		this.getContentPane().add(eins);

		zwei = new JPanel();
		zwei.setBorder(tBorder);
		zwei.setBounds(5, 160, 150, 200);

		actvisits = new JLabel();
		actvisits.setBounds(10, 160, 100, 20);
		actvisits.setText("Active visits: ");
		zwei.add(actvisits);
		this.getContentPane().add(actvisits);

		pzwei = new JList(mActiveVisits);
		pzwei.setBounds(10, 185, 140, 130);
		this.getContentPane().add(pzwei);

		//der knopf um besucher freizugeben
		fvisit = new JButton();
		fvisit.setBounds(10, 325, 130, 30);
		fvisit.setText("Finish visit");
		fvisit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int zahl = Integer.parseInt(mActiveVisits.get(pzwei.getSelectedIndex()).split(" ")[0]);
				new ClientThread(-zahl, ClientForm.this, guidesMonitor).start();
				mActiveVisits.remove(pzwei.getSelectedIndex());
			}
		});
		zwei.add(fvisit);
		this.getContentPane().add(fvisit);

		this.getContentPane().add(zwei);

		availvisitors = new JLabel();
		availvisitors.setBounds(20, 370, 150, 20);
		availvisitors.setText("Available Visitors: " + 50);
		this.getContentPane().add(availvisitors);

		drei = new JPanel();
		drei.setBorder(tBorder);
		drei.setBounds(5, 400, 150, 50);
		this.getContentPane().add(drei);

		status = new JLabel();
		status.setBounds(170, 30, 100, 10);
		status.setText("Status:");
		this.getContentPane().add(status);

		
		//gibt neuigkeiten aus
		editor = new JTextArea();
		editor.setEditable(false);
//		if (mActiveVisits != null && !mActiveVisits.isEmpty()) {
//			for (int i = 0; i < mActiveVisits.size(); i++) {
//				editor.setText(editor.getText() + mActiveVisits.get(i));
//			}
//
//		}
		editor.setBounds(170, 45, 215, 415);
		this.getContentPane().add(editor);
		
		exec = Executors.newSingleThreadScheduledExecutor();
		Thread t = new ClientThread(0, ClientForm.this, guidesMonitor);
		exec.scheduleAtFixedRate((Runnable)t, 1, 1, TimeUnit.SECONDS);

	}
}