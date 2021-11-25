package net.tfobz.compression;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CompressionRunnable implements Runnable {
	private BufferedImage image = null;
	private double quality = 0.0;
	private BufferedImage compressedImg = null;
	
	public CompressionRunnable(BufferedImage image, double quality) {
		this.image = image;
		this.quality = quality;
	}
	@Override
	public void run() {
		try {
			System.out.println("compress: "+ quality);
			compressedImg = JPGImageCompress.compressImage(image, quality);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getCompressedImage() {
		while (true) {
			if (compressedImg != null) {
				return compressedImg;
			}
		}
	}

}
