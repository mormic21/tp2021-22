package net.tfobz.synchronization.chat.client;

import java.io.PrintStream;

public class ChatClientTestThread extends Thread {
	//Membervariablen
	PrintStream out = null;
	
	public ChatClientTestThread(PrintStream out) {
		this.out = out;
	}
	
	@Override
	public void run() {
		out.println(this.getName());
		for (int j = 0; j < 10; j++) {
			out.println("Lorem Ipsum");
			try {
				sleep(350);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.close();
	}
}
