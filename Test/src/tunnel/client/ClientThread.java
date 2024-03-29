package tunnel.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Jede Anfrage um Start einer Besichtigung oder Beendigung einer solchen muss
 * in einem eigenen Thread durchgef�hrt werden, da insbesondere bei nicht
 * Vorhandensein eines F�hrers oder bei nicht verf�gbarem Besucherkontingent
 * eine solche Anfrage l�ngere Zeit warten und deshalb das ClientFormu blockiert
 * w�rde.<br>
 * Damit der Thread seine Aufgabe durchf�hren kann, muss er einerseits den
 * GuidesMonitor als Referenz enthalten, um die F�hreranforderung zu stellen.
 * Andererseits muss er ClientForm kennen, um die Ausgaben und Anpassungen an
 * der Benutzerschnittstelle vornehmen zu k�nnen.<br>
 * Der Thread erh�lt die Besucheranzahl. Ist diese positiv, so fordert er zuerst
 * beim GuidesMonitor einen F�hrer f�r die Gruppe an. Erh�lt er diese, so wird
 * �ber eine Netzwerkverbindung mit dem Programm ServerMain Verbindung
 * aufgenommen und um die eingegebene Anzahl von Besuchern angefragt.<br>
 * Ist die Besucheranzahl negativ, so bedeutet dies, dass die F�hrung beendet
 * wird, der F�hrer dem GuidesMonitor zur�ck gegeben und beim Server ebenfalls
 * die Besucheranzahl zur�ck gegeben wird.<br>
 * Ist die Besucheranzahl gleich 0, so wird der Thread anweisen beim Server die
 * Anzahl der verf�gbaren Besucher nachzufragen, die noch im Tunnel Platz haben
 */
public class ClientThread extends Thread {
	/**
	 * IP-Adresse des Besucherservers
	 */
	protected static final String HOST = "localhost";
	/**
	 * Port
	 */
	protected static final int PORT = 65500;
	/**
	 * Falls positiv: Anzahl der anzufordernden Besucher die eine Besichtigung
	 * machen m�chten<br>
	 * Falls negativ: Anzahl der Besucher die eine Besichtigung beenden m�chten<br>
	 * Falls 0: Besucherserver muss angewiesen werden, die Anzahl der Besucher
	 * zur�ckzuliefern, welche noch in den Tunnel eingelassen werden k�nnen
	 */
	protected int count = 0;
	/**
	 * Referenz auf das ClientForm. Diese ist notwendig, damit der ClientThread die
	 * Benutzerschnittstelle aktualisieren und z. B. Statusmeldungen dort anzeigen
	 * kann
	 */
	protected ClientForm clientForm = null;
	/**
	 * Referent auf den GuidesMonitor. Diese ist notwendig, dass der ClientThread an
	 * diesem einen F�hrer anfordern bzw. nach Beendigung einer F�hrung den F�hrer
	 * zur�ckgeben kann
	 */
	protected GuidesMonitor guidesMonitor = null;

	/**
	 * Konstruktor dem die Anzahl der Besucher, das ClientForm und der GuidesMonitor
	 * �bergeben wird
	 * 
	 * @param anzahl
	 * @param clientForm
	 * @param guidesMonitor
	 */
	public ClientThread(int anzahl, ClientForm clientForm, GuidesMonitor guidesMonitor) {
		this.clientForm = clientForm;
		this.count = anzahl;
		this.guidesMonitor = guidesMonitor;
	}

	/**
	 * In dieser Methode wird die eigentliche Arbeit des Threads erledigt. Ausgehend
	 * vom Wert der Variable count wird folgendes erledigt:<br>
	 * <br>
	 * <b>count > 0: Eine neue Besichtigung soll durchgef�hrt werden</b><br>
	 * In einem ersten Schritt wird am GuidesMonitor ein F�hrer angefordert. War
	 * dies erfolgreich, so wird in einem zweiten Schritt eine Socket-Verbindung mit
	 * dem Server aufgebaut. Konnte die Verbindung nicht aufgebaut werden, so wird
	 * der F�hrer wieder zur�ck gegeben. Bei aufrechter Verbindung wird die Anzahl
	 * �bermittelt. Dann wartet der Thread auf die Antwort des Servers. Da der
	 * Thread neben anderen Threads eigenst�ndig wartet, werden alle anderen
	 * Aktivit�ten nicht blockiert. Nachdem die Antwort des Servers da ist, muss
	 * auch noch die JList mit einem neuen Eintrag erg�nzt werden.Bei diesem Vorgang
	 * m�ssen �nderungen und Ausgaben von Statusmeldungen am ClientFormular
	 * erfolgen<br>
	 * <br>
	 * <b>count < 0: Eine Besichtigung soll beendet werden</b><br>
	 * Zuerst wird der F�hrer dem GuidesMonitor zur�ck gegeben. Dann wird eine
	 * Verbindung zum Server aufgebaut und diesem die Anzahl �bergeben. Dann wird
	 * auf die Antwort des Servers gewartet und danach die JListeintr�ge
	 * aktualisiert. W�hrend dieses Vorganges werden die Inhalte von ClienForm
	 * angepasst<br>
	 * <br>
	 * <b>count == 0: Eine Anfrage an den Server soll ermitteln, wie viele Besucher
	 * noch im Tunnel Platz finden</b><br>
	 * Der Thread erstellt eine Socketverbindung mit dem Server, und schickt diesem
	 * eine 0. Der Server - falls aktiv - antwortet mit der aktuellen Besucheranzahl
	 * die noch in den Tunnel einglassen werden d�rfen. Diese Anzahl wird im
	 * ClientForm ausgegeben
	 */
	public void run() {
		if (count > 0) {

			clientForm.editor.append("Guide reguested...\n");
			if (guidesMonitor.getAvailableGuides() > 0) {
				Socket client = null;

				// verbindet sich mit dem Server
				try {
					client = new Socket("localhost", 65500);

					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					PrintStream out = new PrintStream(client.getOutputStream());
					BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

					out.println(this.count);
					if(count > 0) {
						clientForm.editor.append("Visit with "+count+" visitors requested...\n");
					}
					

					while (true) {
						System.out.println("IN THE WHILE LOOP");
						String line = in.readLine();
						if (line == null) {
							break;
						} else {
							if(!line.equals("Too many visitors")) {
								guidesMonitor.request();
								clientForm.mActiveVisits.addElement(count+" visitors");
							}
							clientForm.editor.append(line+"\n");
							System.out.println(line);
						}

					}
					// schlie�t den Client
					try {
						this.sleep(500);
						client.close();
					} catch (Exception e1) {
						;
					}
				} catch (IOException e) {
					guidesMonitor.release();
					e.printStackTrace();
				}

			}else {
				clientForm.editor.append("No guide available\n");
			}

		} else if (count < 0) {
			guidesMonitor.release();
			Socket client = null;

			// verbindet sich mit dem Server
			try {
				client = new Socket("127.0.0.1", 65500);

				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintStream out = new PrintStream(client.getOutputStream());
				BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

				out.println(this.count);

				while (true) {
					System.out.println("In while loop");
					String line = in.readLine();
					if (line == null) {
						break;
					} else {
						clientForm.editor.append(line+"\n");
						System.out.println(line);
					}

				}
				// schlie�t den Client
				try {
					this.sleep(500);
					client.close();
				} catch (Exception e1) {
					;
				}
			} catch (IOException e) {
				guidesMonitor.release();
				e.printStackTrace();
			}
		} else if (count == 0) {
			System.out.println("Here");
			Socket client = null;
			// verbindet sich mit dem Server
			try {
				client = new Socket("127.0.0.1", 65500);

				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintStream out = new PrintStream(client.getOutputStream());
				BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("[count]: "+this.count);
				out.println(this.count);
				String line = in.readLine();
				System.out.println("[Line] "+line);
				String labeltext = clientForm.availvisitors.getText();
				String []texte = labeltext.split(" ");
				String newtext = texte[0]+" "+texte[1]+" "+line;
				clientForm.availvisitors.setText(newtext);

//				while (true) {
//					System.out.println("in while loop");
//					System.out.println("reading...");
//					String line = in.readLine();
//					System.out.println("reading ok");
//					if (line == null) {
//						break;
//					} else {
//						System.out.println("[Line] "+line);
//						String labeltext = clientForm.availvisitors.getText();
//						String []texte = labeltext.split(" ");
//						String newtext = texte[0]+" "+texte[1]+" "+line;
//						clientForm.availvisitors.setText(newtext);
//						
//						//clientForm.availvisitors.setText(clientForm.availvisitors.getText().split(":")[0]+": "+line);
//					}
//
//				}
				System.out.println("Exited the While Loop");
				// schlie�t den Client
				try {
					System.out.println("Sleeping");
					this.sleep(500);
					client.close();
				} catch (Exception e1) {
					System.out.println("Error on Sleep");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Methode zur Behandlung der Netzwerkexceptions
	 * 
	 * @param e
	 */
	public void behandleException(Exception e) {
	}
}