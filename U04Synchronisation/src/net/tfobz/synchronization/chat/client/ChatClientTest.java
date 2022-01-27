package net.tfobz.synchronization.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatClientTest {
	public static final int PORT = 65535;
	public static final String HOST = "localhost";
	private static ChatClientTestThread[] threads = new ChatClientTestThread[50];

	public static void main(String[] args) {
		Socket client = null;
		try {
			for (int i = 0; i < threads.length; i++) {
				client = new Socket(HOST, PORT);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				new ChatClientThread(in).start();
				String name = "Thread_Nr" + i;
				PrintStream out = new PrintStream(client.getOutputStream());
				threads[i] = new ChatClientTestThread(out);
				threads[i].setName(name);
				threads[i].start();
			}
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
//		} finally {
//			try {
//				client.close();
//			} catch (Exception e1) {
//				;
//			}
//		}
//		for (int i = 0; i < threads.length; i++) {
//			
//		}
//		for (Thread th : threads) {
//			th.start();
//		}

		// Socket client = null;
		// try {
		// client = new Socket(HOST, PORT);
		// BufferedReader in =
		// new BufferedReader( new InputStreamReader(client.getInputStream()));
		// new ChatClientThread(in).start();
		// for (int i = 0; i < threads.length; i++) {
		// String name = "Thread_"+i;
		// PrintStream out = new PrintStream(client.getOutputStream());
		// threads[i] = new ChatClientTestThread(out);
		// threads[i].setName(name);
		// threads[i].start();
	}
	// for (Thread th : threads) {
	// th.start();
	// }

	// Socket client = null;
	// try {
	// client = new Socket(args[1], PORT);
	//
	// for(int i = 0; i < threads.length; i++) {
	// BufferedReader in =
	// new BufferedReader( new InputStreamReader(client.getInputStream()));
	// PrintStream out = new PrintStream(client.getOutputStream());
	// out.println(i);
	// threads[i] = new ChatClientThread(in);
	// threads[i].start();
	// for (int j = 0; j < 10; j++) {
	// out.println("Lorem Ipsum");
	// }
	// }

	// // sending the name of the client to the server
	// out.println(args[0]);
	//
	// new ChatClientThread(in).start();
	//
	// while (true) {
	// String line = consoleIn.readLine();
	// if (line == null)
	// // pressed [Ctrl]+Z to sign out
	// break;
	// out.println(line);
	// }

}
