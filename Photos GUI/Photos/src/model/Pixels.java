
/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package model;


import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Piexls implement Serializable
 * Pixels class converts FX images to Pixels in order to serialize them
 *
 */
public class Pixels implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int width, height;
	private int[][] pixels;
	
	
	/**
	 * Constructor that takes and image parameter to convert image into two dimensional pixel array
	 * @param image - image to extract pixels from
	 */
	
	public Pixels(Image image) {
		//using image class methods
		width = (int)image.getWidth();
		height = (int)image.getHeight();
		
		pixels = new int[width][height];
		
		PixelReader reader = image.getPixelReader();
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				// gets color of the pixel in int format
				pixels[w][h] = reader.getArgb(w, h);
			}
		}
	}

	
	/**
	 * Returns an image made of pixels
	 * @return Image 
	 */
	public Image getImage() {
		WritableImage image = new WritableImage(width, height);
		
		PixelWriter w = image.getPixelWriter();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				w.setArgb(i, j, pixels[i][j]);
			}
		}
		
		return image;
	}
	
	/**
	 * Returns the width of the image
	 * @return int
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of the image
	 * @return int
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns a two dimensional array of Pixels
	 * @return int[][]
	 */
	public int[][] getPixels() {
		return pixels;
	}
	
	
	/**
	 * Checks if one image is the same as the other
	 * @param image - image to be compared to current image
	 * @return boolean
	 */
	public boolean equals(Pixels image) {
		if (width != image.getWidth() || height != image.getHeight()) {
			return false;
		}
		
		for (int cr = 0; cr < width; cr++) {
			for (int cc = 0; cc < height; cc++) {
				if (pixels[cr][cc] != image.getPixels()[cr][cc]) {
					return false;
				}
			}
		}
		
		return true;
	}
}
