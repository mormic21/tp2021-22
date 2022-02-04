package net.tfobz.synchronization.chat.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * ChatClientTestThread
 * realisiert Threads, welche durch die Klasse ChatClientTest genutzt werden, um den ChatServer zu testen
 * erbt von Thread
 * @author Michael Morandell
 *
 */
public class ChatClientTestThread extends Thread {
	//Membervariablen
	Socket client = null;
	PrintStream out = null;
	BufferedReader in = null;
	
	/**
	 * ChatClientTestThread-Konstruktor
	 * @param client
	 */
	public ChatClientTestThread(Socket client) {
		this.client = client;
		try {
			//PrintStream wird geholt
			this.out = new PrintStream(client.getOutputStream());
			this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//wenn IOException
		} catch (IOException e) {
			System.out.println("IOException in Konstruktor: "+e.getMessage());
		}
	}
	
	/**
	 * run-Methode
	 * des Threads
	 */
	@Override
	public void run() {
		//Name wird an den Server gesendet
		out.println(this.getName());
		//50 Mal wird 100ms gewartet und dann eine "Lorem Ipsum"-Nachricht mit einer Nummer abgesendet
		for (int j = 0; j < 50; j++) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			out.println("Lorem Ipsum Nr: "+j);
			out.flush();
		}
		//Es wird 1s gewartet, bis sich der Klient ausloggt
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			//Client wird geschlossen
			try {
				client.close();
			} catch (Exception e1) {;}
		}
	}
}