package net.tfobz.testprogramm;

public class MyThreadTerminator extends Thread {
	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			;
		}
		Hauptprogramm.my.interrupt();
	}
}
