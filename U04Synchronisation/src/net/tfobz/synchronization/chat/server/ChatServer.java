package net.tfobz.synchronization.chat.server;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ChatServer
 * realisiert einen Chat-Server
 * @author Michael Morandell
 *
 */
public class ChatServer {
	//Port des Servers
	public static final int PORT = 65534;
	//Verbindungen zu Clients
	protected static ArrayList<PrintStream> outputStreams =
		new ArrayList();
	
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(PORT);
			System.out.println("Chat server started");
			while (true) {
				Socket client = server.accept();
				try {
					new ChatServerThread(client).start();
				} catch (IOException e) {
					System.out.println(e.getClass().getName() + ": " + e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			try { server.close(); } catch (Exception e1) { ; }
		}
	}
}