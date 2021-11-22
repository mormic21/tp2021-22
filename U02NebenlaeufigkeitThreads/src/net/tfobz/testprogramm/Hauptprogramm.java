package net.tfobz.testprogramm;

public class Hauptprogramm {
	
	public static MyThread my = new MyThread();
	
	public static void main(String[] args) {
		MyThreadStateAnalyser mtsa = new MyThreadStateAnalyser();
		MyThreadTerminator mtt = new MyThreadTerminator();
		
		Thread analyser = new Thread(mtsa);
		Thread terminator = new Thread(mtt);
		
		analyser.start();
		terminator.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			;
		}
		
		my.start();
	}
}
