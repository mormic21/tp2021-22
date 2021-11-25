package net.tfobz.compression;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CompressionThread extends Thread {
	private BufferedImage image = null;
	private double quality = 0.0;
	private BufferedImage compressedImg = null;
	
	public CompressionThread(BufferedImage image, double quality) {
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
				System.out.println("image get");
				return compressedImg;
			}
		}
	}

}
