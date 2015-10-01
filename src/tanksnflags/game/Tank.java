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
	private int vertical = 0;
	
	public enum DIRECTION {
		NORTH, EAST, SOUTH, WEST
	}
	
	private DIRECTION dir = DIRECTION.NORTH;
	
	public Tank(Vector pos, IsoLogic iL, int uid){
		super(pos, iL);
		this.uid=uid;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if (dir == DIRECTION.NORTH) {
			
			g2.drawImage(NORTH, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);
		} else if (dir == DIRECTION.EAST) {
			g2.drawImage(EAST, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);
		} else if (dir == DIRECTION.SOUTH) {
			g2.drawImage(SOUTH, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);
		}  else {
			g2.drawImage(WEST, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);
		}
	}
	
	public void setWest(){
		dir = DIRECTION.WEST;
	}
	
	public void setPosition(Vector pos){
		super.pos = pos;
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
	private static final Image NORTH = ImageLoader.loadImage("rsz_jcn.png");

	private static final Image EAST = ImageLoader.loadImage("JCE.png");
	
	private static final Image SOUTH = ImageLoader.loadImage("JCS.png");

	private static final Image WEST = ImageLoader.loadImage("rsz_jcw.png");


}
