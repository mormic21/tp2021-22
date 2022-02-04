package net.tfobz.synchronization.chat.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * ChatServerThread
 * realisiert Threads, welche durch die Klasse ChatServer.java verwendet werden
 * erbt von Thread
 * @author Michael Morandell
 *
 */
public class ChatServerThread extends Thread {
	//Membervariablen
	private Socket client = null;
	private BufferedReader in = null;
	private PrintStream out = null;
	
	/**
	 * ChatServerThread-Konstruktor
	 * @param client
	 * @throws IOException
	 */
	public ChatServerThread(Socket client) throws IOException {
		this.client = client;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintStream(client.getOutputStream());
	}
	
	/**
	 * run-Methode
	 * des Threads
	 */
	@Override
	public void run() {
		try {
			String name = null;
			//Synchronisierter Bereich fuer die Output-Streams
			synchronized (ChatServer.outputStreams) {
				ChatServer.outputStreams.add(out);
				name = in.readLine();
				System.out.println(name + " signed in. " + ChatServer.outputStreams.size() + " users");
				for (PrintStream outs: ChatServer.outputStreams)
					outs.println(name + " signed in");
			}
			//Endlos-Schleife
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				//Ausgabe der Nachricht an Konsole
				System.out.println(name + ": " + line);
				//Synchronisierter Bereich fuer die Output-Streams
				synchronized (ChatServer.outputStreams) {
					for (PrintStream outs: ChatServer.outputStreams)
						outs.println(name + ": " + line);
				}
				
			}
			//Synchronisierter Bereich fuer die Output-Streams
			synchronized (ChatServer.outputStreams) {
				ChatServer.outputStreams.remove(out);
				System.out.println(name + " signed out. " + ChatServer.outputStreams.size() + " users");
				for (PrintStream outs: ChatServer.outputStreams)
					outs.println(name + " signed out");
			}
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			//Synchronisierter Bereich fuer die Output-Streams
			synchronized (ChatServer.outputStreams) {
				if (out != null)
					ChatServer.outputStreams.remove(out);
			}
		} finally {
			try { client.close(); } catch (Exception e1) { ; }
		}
	}
}