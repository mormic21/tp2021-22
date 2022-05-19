/**
 * Slotmachine - Application layer
 */
package net.tfobz.game;
import java.awt.Image;

/**
 * Handles a slot and notifies the observers on an image change.
 * 
 * @author mdw
 *
 */
class Slot implements Runnable
{
	/** The slot delay in milliseconds */
	public static final int SLOT_DELAY = 50;
	
	/** The given images */
	private Image images[];
	/** The actual image */
	private Image actualImage;
	/** Slot timeout in seconds */
	private int timeout;
	
	/**
	 * Sets up a new slot and passes the images and the slot timeout
	 * @param images The given images
	 * @param timeout The slot timeout in seconds
	 */
	public Slot(Image images[], int timeout) {
		this.images = images;
		this.timeout = timeout;
	}

	/**
	 * Runs a thread
	 */
	public void run() {
		// Left timeout in milliseconds
		int leftTimeout = timeout * 1000;
		// Thread loop for image change. Proofs the left time and for interrupt queries 
		while ((!Thread.interrupted()) && (leftTimeout >= SLOT_DELAY)) {
			// Picture change happens here
			synchronized (this) {
				actualImage = images[(int)(Math.random() * images.length)];
			}
			SlotMachine.getInstance().imageUpdated();
			SlotMachine.getInstance().notifyObservers();

			// Slot delay and timeout decrementation
			try {
				Thread.sleep(SLOT_DELAY);
			} catch (InterruptedException e) {
				// Resets the interrupt flag if the interrupt has arrived during "sleep"
				Thread.currentThread().interrupt();
			}
			leftTimeout -= SLOT_DELAY;

		}
	}
	
	/**
	 * Gets back the actual image
	 * @return actual image
	 */
	public Image getActualImage() {
		synchronized (this) {
			return actualImage;
		}
	}
}