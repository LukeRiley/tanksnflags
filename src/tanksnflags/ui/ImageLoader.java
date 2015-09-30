package tanksnflags.ui;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
	
	/**
	 * Load an image from the file system, using a given filename.
	 * 
	 * @param filename
	 * @return
	 */
	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		java.net.URL imageURL = ImageLoader.class.getResource("images/"
				+ filename);
System.out.println(ImageLoader.class);		
		try {
			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}
}
