package tunnel.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * In dieser Konsolenanwendung wird zuerst ein VisitorsMonitor angelegt, und dann 
 * wartet das Programm in einer Endlosschleife auf Clientanfragen. Erreicht ihm 
 * eine solche, so wird diese in einem Thread vom Typ ServerThread abgearbeitet.
 * Dadurch dass jede Anfrage in einem eigenen Thread abgearbeitet wird,
 * können mehrere Anfragen gleichzeitig bearbeitet werden
 */
public class ServerMain 
{
	/**
	 * Port an welchem der Server arbeitet
	 */
	protected static final int PORT = 65500;
	
	/**
	 * Besuchermonitor wird angelegt, und in einer Endlosschleife wird auf 
	 * Clientanfragen gewartet, welche alle über einzelne ServerThreads abgearbeitet
	 * werden. Dadurch dass jede Anfrage in einem eigenen Thread abgearbeitet wird,
	 * können mehrere Anfragen gleichzeitig bearbeitet werden
	 * @param args
	 */
	public static void main(String[] args) {
		VisitorsMonitor v = new VisitorsMonitor();
		ServerSocket server = null;
		try {
			server = new ServerSocket(PORT);
			System.out.println("S E R V E R");
			System.out.println("===========");
			while (true) {
				Socket client = server.accept();
				new ServerThread(client, v).start();
				for(int i = 0; i < 10000000; i++) {
				}
			}
		} catch (IOException e) {
		//	System.out.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			try { server.close(); } catch (Exception e1) { ; }
		}
	}
	
	/**
	 * Methode zur Exceptionbehandlung
	 * @param e
	 */
	public static void behandleException(Exception e) {
	}
}
