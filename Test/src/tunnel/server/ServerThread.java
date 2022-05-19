package tunnel.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Der Thread liest vom Socket die Anzahl, und dabei werden die drei Fälle –
 * größer 0, kleiner 0 oder gleich 0 – unterschieden und entsprechen am
 * VisitorsMonitor die Anfragen gestellt. Das Ergebnis wird an den Client zurück
 * geschickt. der ServerThread erhält den Socket des Clients und eine Referenz
 * auf VisitorsMonitor
 */
public class ServerThread extends Thread {
	/**
	 * Der Clientsocket von welchem die Besucheranzahl gelesen werden kann
	 */
	protected Socket client = null;
	/**
	 * VisitorsMonitor an dem die Anfrage nach Besuchern bzw. die Rückgabe der
	 * Besucher nach Beendigung einer Besichtigung gestellt werden kann
	 */
	protected VisitorsMonitor visitorsMonitor = null;

	/**
	 * Konstruktor erhält den Clientsocket und den VisitorsMonitor als Referenz. Als
	 * Threadname wird die IP-Adresse des Clients gesetzt. Die IP-Adresse kann über
	 * den Clientsocket durch die Methode getInetAdress() erfragt werden
	 * 
	 * @param client
	 * @param visitorsMonitor
	 */
	public ServerThread(Socket client, VisitorsMonitor visitorsMonitor) {
		this.client = client;
		this.setName(client.getInetAddress().toString());
		this.visitorsMonitor = visitorsMonitor;
	}

	/**
	 * Diese Methode liest zuerst vom Clientsocket die Anzahl. Je nach dem welche
	 * Werte in anzahl stehen, werden folgende Aufgaben erledigt:<br>
	 * <br>
	 * <b>anzahl == 0</b><br>
	 * Es wird die Anzahl der am VisitorsMonitor momentan verfügbaren Benutzer
	 * abgefragt und an den Client zurück geschickt<br>
	 * <br>
	 * <b>anzahl > 0</b><br>
	 * Es werden am VisitorsMonitor die Benutzer angefordert<br>
	 * <br>
	 * <b>anzahl < 0</b><br>
	 * Es werden dem VisitorsMonitor die Anzahl an Benutzer zurück gegeben
	 */
	public void run() {
		String line = null;
		BufferedReader in;
		PrintStream out;
		try {
			out = new PrintStream(client.getOutputStream());

			try {
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));

				while (line == null) {

					line = in.readLine();

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int anzahl = Integer.parseInt(line);
			if (anzahl > 0) {
				System.out.println(client.getInetAddress()+" requests "+anzahl+" visitors");
				if (visitorsMonitor.getAvailableVisitors() >= anzahl) {
					visitorsMonitor.request(anzahl);
					out.println("Visit with " + anzahl + " visitors enter the tunner");
					System.out.println(client.getInetAddress()+" receives "+anzahl+" visitors. "+visitorsMonitor.getAvailableVisitors()+" visitors available");
				} else {
					out.println("Too many visitors");
				}
			} else if (anzahl < 0) {
				visitorsMonitor.release(anzahl);
				out.println("Visit with " + -anzahl + " visitors finished");
				System.out.println(client.getInetAddress()+" releases "+-anzahl+" visitors");
			} else if (anzahl == 0) {
				out.println(visitorsMonitor.getAvailableVisitors());
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
