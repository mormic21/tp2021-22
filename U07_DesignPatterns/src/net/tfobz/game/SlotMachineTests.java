/**
 * Slotmachine - Application layer
 */
package net.tfobz.game;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The slotmachine unit tester
 * 
 * @author mdw
 * 
 */
public class SlotMachineTests
{
	/**
	 * Test observer<br>
	 * - Number of observer calls
	 */
	private class TestObserver implements Observer {
		private int obsCalls;
		
		public void update(Observable o, Object arg) {
			// Increment this on each observer call
			++obsCalls;
		}
	}
	@Test public void slotTimeout() {
		Image anImage = Toolkit.getDefaultToolkit().getImage("images/1.jpg");
		Slot myslot = new Slot(new Image[] { anImage }, 4);
		long startTime = System.currentTimeMillis();
		myslot.run();
		long stopTime = System.currentTimeMillis();
		// Time test
		assertTrue((stopTime - startTime) / 1000 == 4);
	}
	
	@Test public void slotThread() {
		Image anImage = Toolkit.getDefaultToolkit().getImage("images/1.jpg");
		Slot myslot = new Slot(new Image[] { anImage }, 4);
		Thread slotThread = new Thread(myslot);
		slotThread.start();
		// Now let the thread stop and test if it really has been stopped.
		// Attention: The test doesn't succeed if it doesn't terminate!
		slotThread.interrupt();
		while (slotThread.isAlive())
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
	}
	
	@Test public void slotMachineBasicTest() {
		Image anImage = Toolkit.getDefaultToolkit().getImage("images/1.jpg");
		// TODO: Singleton call missing
		SlotMachine slotMachine = SlotMachine.getInstance();
		// Test images set up
		try {
			slotMachine.setImages(new Image[] { anImage });
			assertFalse(true);
		} catch (IllegalArgumentException e) { }
		Image images[] = {
			Toolkit.getDefaultToolkit().getImage("images/1.jpg"),
			Toolkit.getDefaultToolkit().getImage("images/2.jpg"),
			Toolkit.getDefaultToolkit().getImage("images/3.jpg"),
			Toolkit.getDefaultToolkit().getImage("images/4.jpg"),
			Toolkit.getDefaultToolkit().getImage("images/5.jpg"),
		};
		slotMachine.setImages(images);
		assertTrue(slotMachine.getImages().equals(images));
		// Tests if "stop" doesn't fail before start
		slotMachine.stop();
		// Small observer test
		// Attention: The test doesn't succeed if it doesn't terminate!
		TestObserver testObserver = new TestObserver();
		slotMachine.addObserver(testObserver);
		slotMachine.start();
		while (testObserver.obsCalls == 0)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
		slotMachine.stop();
		// The points should have been changed
		while (slotMachine.getPoints() == 250)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
	}
}
