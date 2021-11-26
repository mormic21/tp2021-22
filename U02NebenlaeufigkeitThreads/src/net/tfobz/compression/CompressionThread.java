package net.tfobz.compression;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * CompressionThread
 * realisiert einen Thread, welcher das uebergebene Bild,
 * in der uebergebenen Qualitaet komprimiert.
 * erbt von Thread
 * @author Michael Morandell
 *
 */
public class CompressionThread extends Thread {
	//Membervariablen
	private BufferedImage image = null;
	private double quality = 0.0;
	private BufferedImage compressedImg = null;
	
	/**
	 * CompressionThread-Konstruktor
	 * @param image, das zu komprimierende Bild
	 * @param quality, die Qualitaet, in welcher das Bild komprimiert wird
	 */
	public CompressionThread(BufferedImage image, double quality) {
		//setzen der Membervariablen
		this.image = image;
		this.quality = quality;
	}
	
	/**
	 * run-Methode des Threads
	 */
	@Override
	public void run() {
		try {
			//Bild wird komprimiert und in die Membervariable compressedImg gespeichert
			compressedImg = JPGImageCompress.compressImage(image, quality);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getCompressedImage
	 * gibt das komprimierte Bild zurueck, welches in der Membervariable compressedImg liegt
	 * @return
	 */
	public BufferedImage getCompressedImage() {
		return compressedImg;
	}

}
