/**
 * Slotmachine - Application layer
 */
package net.tfobz.game;
import java.awt.Image;
import java.util.Observable;

/**
 * This class defines the interface between the application and presentation
 * layer. It notifies it observers on image and points changes.
 * 
 * @author mdw
 *
 */
//TODO: Make this class observable
public class SlotMachine extends Observable {	

	/** The slots */
	private Slot slots[] = new Slot[3];
	/** The slots threads */
	private Thread slotThreads[] = new Thread[3];
	/** The slot controller thread */
	private Thread slotControllerThread;
	
	/** The images */
	private Image images[];
	/** The game points */
	private int points;
	
	/**
	 * This controls the slots for finalization, then it calculates the points and
	 * informs the observer about it.
	 */
	private class SlotController implements Runnable {
		
		/**
		 * run
		 */
		public void run() {
			try {
				slotThreads[0].join();
			} catch (InterruptedException e) { }
			try {
				slotThreads[1].join();
			} catch (InterruptedException e) { }
			try {
				slotThreads[2].join();
			} catch (InterruptedException e) { }
			
			synchronized (SlotMachine.this) {
				// All finished, so calculate the points
				if ((slots[0].getActualImage().equals(slots[1].getActualImage()))
						&& (slots[1].getActualImage().equals(slots[2].getActualImage())))
					// All images equal
					points += 30;
				else
					if ((slots[0].getActualImage().equals(slots[1].getActualImage()))
							|| (slots[1].getActualImage().equals(slots[2].getActualImage()))
							|| (slots[2].getActualImage().equals(slots[0].getActualImage())))
						// Two images equal
						points += 20;
					else
						// Nothing equal
						points -= 10;
			}
			setChanged();
			notifyObservers();
		}
	}	
	
	// TODO: Implement the singleton pattern
	
	/**
	 * SlotMachineHolder
	 * @author Michael Morandell
	 *
	 */
	private static class SlotMachineHolder {
		private final static SlotMachine INSTANCE = new SlotMachine();
	}
	
	/**
	 * SlotMachine
	 * @return
	 */
	public static SlotMachine getInstance() {
			return SlotMachineHolder.INSTANCE;
	}
	
	/**
	 * Sets up the images (a minimum of 5)
	 * @param images Images
	 */
	synchronized public void setImages(Image images[]) {
		if (images.length < 5)
			throw new IllegalArgumentException(
					"SlotMachine setImages: At least five images needed!");
		this.images = images;
	}
	
	/**
	 * Gets back all images
	 * @return Images
	 */
	synchronized public Image[] getImages() {
		return images;
	}
	
	/**
	 * Starts the slot machine. Note: The "setImages" call has to be invoked
	 * before this one.
	 */
	public void start() {
		if (images == null)
			throw new IllegalArgumentException("SlotMachine start: No images set!");
		// Stops an eventual previous game
		stop();
		// Creates the slots with timeouts between [20..30] seconds
		slots[0] = new Slot(images, 20 + (int)(Math.random() * 10));
		slots[1] = new Slot(images, 20 + (int)(Math.random() * 10));
		slots[2] = new Slot(images, 20 + (int)(Math.random() * 10));
		// Sets initial credit when points are smaller equal 0 or greater equal 500
		if ((points <= 0) || (points >= 500))
			points = 250;
		// Starts the slots
		slotThreads[0] = new Thread(slots[0]);
		slotThreads[1] = new Thread(slots[1]);
		slotThreads[2] = new Thread(slots[2]);
		slotThreads[0].start();
		slotThreads[1].start();
		slotThreads[2].start();
		// Invokes slot controller thread
		slotControllerThread = new Thread(new SlotController());
		slotControllerThread.start();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Stops the slot machine through the slot controller
	 */
	public void stop() {
		if ((slots[0] != null) && (slots[1] != null) && (slots[2] != null)) {
			slotThreads[0].interrupt();
			slotThreads[1].interrupt();
			slotThreads[2].interrupt();
			// Waits until the slot controller thread has been stopped
			try {
				slotControllerThread.join();
			} catch (InterruptedException e) { }
			setChanged();
			notifyObservers();
		}

	}
	
	/**
	 * Gets the actual images from the three slots (0..2). Should be invoked after
	 * an observer notify event (pull).
	 * @return The three images
	 */
	public Image[] getActualImages() {
		Image res[] = new Image[3];
		res[0] = slots[0].getActualImage();
		res[1] = slots[1].getActualImage();
		res[2] = slots[2].getActualImage();
		return res;
	}
	
	/**
	 * Gets back the game points
	 * @return Game points
	 */
	synchronized public int getPoints() {
		return points;
	}
	
	/**
	 * setChanged
	 */
	public void imageUpdated() {
		setChanged();
	}
}
