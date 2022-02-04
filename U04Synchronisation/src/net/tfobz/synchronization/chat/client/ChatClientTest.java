package net.tfobz.synchronization.chat.client;
import java.io.IOException;
import java.net.Socket;

/**
 * ChatClientTest
 * realisiert ein Testprogramm, welches das korrekte Verhalten des Chat-Servers verifiziert.
 * Dabei werden mit Threads, 50 parallele Clients erstellt, die 50 Mal eine Antwort senden und sich dann ausloggen
 * @author Michael Morandell
 *
 */
public class ChatClientTest {
	//Membervariablen
	public static final int PORT = 65534;
	public static final String HOST = "localhost";
	private static ChatClientTestThread[] threads = new ChatClientTestThread[50];
	
	/**
	 * Main-Methode
	 * @param args
	 */
	public static void main(String[] args) {
		Socket client = null;
		try {
			//Fuer Laenge des Arrays werden neue Threads erstellt
			for (int i = 0; i < threads.length; i++) {
				//neuer Client
				client = new Socket(HOST, PORT);
				//neuer Name wird im Thread gesetzt
				String name = "Thread_Nr." + i;
				threads[i] = new ChatClientTestThread(client);
				threads[i].setName(name);
				//starten der Threads
				threads[i].start();
			}
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}