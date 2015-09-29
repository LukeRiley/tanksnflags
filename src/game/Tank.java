package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A tank in the game. Assigned a unique id to link it to a player in the game with the same id.
 * @author Haylem
 *
 */
public class Tank extends Item {

	private int uid;
	
	public Tank(double isoX, double isoY, int uid){
		super(isoX,isoY);
		this.uid=uid;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		BufferedImage johnCena = null;
		try {
		    johnCena = ImageIO.read(new File("johncena1.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		g2.drawImage(johnCena, 200, 200, 200, 200, null);
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item fromInputStream(DataInputStream din) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	// Images of tank
}
