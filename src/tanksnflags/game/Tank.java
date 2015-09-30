package tanksnflags.game;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
		// TODO Auto-generated method stub
		
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
