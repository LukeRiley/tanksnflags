package tanksnflags.tokens;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.helpers.Vector;

public class Door extends MovingItem {

	private int KEYID;
	private Wall wall;
	private int STATE = 0;

	public Door(Vector pos, IsoLogic iL, int KEYID) {
		super(pos, iL);
		this.KEYID = KEYID;
		vertical = 29;
		moveIncrement = 29;
		wall = new Wall(pos, iL);
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		// TODO Auto-generated method stub

	}

	public Item fromInputStream(DataInputStream din) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void renderTick(int moveRate) {
/*		System.out.println(moveRate);
		if (STATE == 0) {
			wall.vertical = wall.vertical + moveRate;
		} else if (STATE == 1) {
			wall.vertical = wall.vertical - moveRate;
		}*/
	}

	@Override
	public void draw(Graphics2D g2, Dir dir) {
		wall.draw(g2, dir);
	}

}
