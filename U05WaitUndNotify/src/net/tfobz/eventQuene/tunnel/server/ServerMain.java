package net.tfobz.eventQuene.tunnel.server;
import java.io.IOException;
import java.net.*;

/**
 * In dieser Konsolenanwendung wird zuerst ein VisitorsMonitor angelegt, und dann 
 * wartet das Programm in einer Endlosschleife auf Clientanfragen. Erreicht ihm 
 * eine solche, so wird diese in einem Thread vom Typ ServerThread abgearbeitet.
 * Dadurch dass jede Anfrage in einem eigenen Thread abgearbeitet wird,
 * können mehrere Anfragen gleichzeitig bearbeitet werden
 */
public class ServerMain {
	/**
	 * Port an welchem der Server arbeitet
	 */
	protected static final int PORT = 65123;
	
	/**
	 * Besuchermonitor wird angelegt, und in einer Endlosschleife wird auf 
	 * Clientanfragen gewartet, welche alle über einzelne ServerThreads abgearbeitet
	 * werden. Dadurch dass jede Anfrage in einem eigenen Thread abgearbeitet wird,
	 * können mehrere Anfragen gleichzeitig bearbeitet werden
	 * @param args
	 */
	public static void main(String[] args) {
		//neuer Visitor-Monitor
		VisitorsMonitor visitorMonitor = new VisitorsMonitor();
		//neuer Socket-Server
		ServerSocket server = null;
		try {
			//server-Socket wird gestartet
			server = new ServerSocket(PORT);
			//Initiale ausgabe
			System.out.println("S E R V E R");
			System.out.println("==============");
			//Ausgabe der besucher
			System.out.println(visitorMonitor.getAvailableVisitors()+" available visitors");
			//endlos-schleife
			while (true) {
				//es wird auf clients gewartet
				Socket client = server.accept();
				//wenn cient accpted, neuer ServerThread wird gestartet
				new ServerThread(client, visitorMonitor).start();
			}
			//IO-Exception
		} catch (IOException e) {
			behandleException(e);
		} finally {
			try { server.close(); } catch (Exception e1) { ; }
		}
	}
	
	/**
	 * Methode zur Exceptionbehandlung
	 * @param e
	 */
	public static void behandleException(Exception e) {
		System.out.println(e.getClass().getName()+" in ClientThread.java | "+e.getMessage());
	}
}