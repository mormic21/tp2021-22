package net.tfobz.testprogramm;

public class MyThreadStateAnalyser extends Thread {
	@Override
	public void run() {
		while (Hauptprogramm.my.getState() != State.TERMINATED) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				;
			}
			System.out.println(Hauptprogramm.my.getState());
		}
	}
}
