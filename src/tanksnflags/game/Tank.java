package tanksnflags.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.game.Wall.TILECOLOR;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.ui.ImageLoader;

/**
 * A tank in the game. Assigned a unique id to link it to a player in the game with the same id.
 * @author Haylem
 *
 */
public class Tank extends Item {

	private int uid;
	
	private int vertical = 28;

	public enum TILECOLOR {
		RED, BLUE
	}
	
	TILECOLOR color = TILECOLOR.BLUE;
	
	public Tank(Vector pos, IsoLogic iL, int uid){
		super(pos, iL);
		this.uid=uid;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if (color == TILECOLOR.RED) {
			g2.drawImage(RED, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);

		} else {
			g2.drawImage(BLUE, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);
		}
	}
	
	public TILECOLOR getColor(){
		return color;
	}

	public void setBlue() {
		color = TILECOLOR.BLUE;
	}

	public void setRed() {
		color = TILECOLOR.RED;
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

	private static final Image RED = ImageLoader.loadImage("tileRed.png");

	private static final Image BLUE = ImageLoader.loadImage("tileBlue.png");

	public void move(Vector delta) {
		pos = pos.add(delta);
	}
	
	// Images of tank
}
