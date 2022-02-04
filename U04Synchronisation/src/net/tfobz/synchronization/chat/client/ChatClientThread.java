package net.tfobz.synchronization.chat.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

/**
 * ChatClientThread
 * stellt einen Thread fuer die Klasse ChatClient.java zur Verfuegung
 * erbt von Thread
 *
 */
public class ChatClientThread extends Thread {
	//Membervariable
	private BufferedReader in = null;
	
	/**
	 * ChatClientThread-Konstruktor
	 * @param in
	 */
	public ChatClientThread(BufferedReader in) {
		this.in = in;
	}
	
	/**
	 * run-Methode
	 * des Threads
	 */
	@Override
	public void run() {
		try {
			while (true) {
				String line = in.readLine();
				System.out.println(line);
			}
		} catch (SocketException e) {
			System.out.println("Connection to ChatServer lost, ignore exception");
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}