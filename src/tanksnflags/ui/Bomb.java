package tanksnflags.ui;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A Bomb item in the game, can be picked up by players,
 *  dragged around the canvas and dropped in places
 * @author raymondgeuze
 *
 */
public class Bomb implements InventoryItem{
	
	private BufferedImage image; //the image of this item
	private int xPos; //the x position of this bomb
	private int yPos;
	private String direction; //direction this image is facing
	
	public Bomb(int x,int y,String dir){
		xPos =x;
		yPos = y;
		direction = dir;
		
		try {
		    image = ImageIO.read(new File("src/applicationWindow/images/Bomb.png"));
		    
		} catch (IOException e) {
		}
	}

	@Override
	public int getX() {
	return xPos;
	}

	@Override
	public int getY() {
		return yPos;
	}
	@Override
	public void setX(int x){
		xPos = x;
	}
	
	@Override
	public void setY(int y){
		yPos = y;
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public String getDirection() {
		//string or int? 
		return direction;
	}

	/**
	 * Draws the image to the graphics pane. So draws to the canvas graphics
	 */
	public void draw(Graphics g){
		g.drawImage(image, xPos, yPos, null);
	}
}
