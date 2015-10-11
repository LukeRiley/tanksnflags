package tanksnflags.tokens;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.helpers.Vector;

/**
 * Represents an item in the game. This encapsulates anything which is drawable
 * to the screen. Walls, characters, pick up items etc. An important requirement
 * of an item is that it is able to create and write it self from/to the data
 * strem.
 * 
 * @author Haylem
 *
 */
public abstract class Item {

	protected Vector pos;
	protected Vector sPos;

	protected IsoLogic iL;

	protected int vertical = 0;

	public Item(Vector pos, IsoLogic iL) {
		this.pos = pos;
		this.iL = iL;
	}

	public int vertical() {
		return vertical;
	}

	public Vector pos() {
		return pos;
	}

	public void calculateDrawPos(Dir dir) {
		sPos = iL.isoToScreen(this);
		Vector[] vertices = new Vector[4];
		vertices[0] = sPos;
		vertices[1] = iL.isoToScreen(new Vector(pos.getQ() + 46, pos.getT()));
		vertices[2] = iL.isoToScreen(new Vector(pos.getQ() + 46, pos.getT()+46));
		vertices[3] = iL.isoToScreen(new Vector(pos.getQ(), pos.getT() + 46));

		switch (dir) {
		case NORTH:
			sPos = vertices[1];
			break;
		case WEST:
			sPos = vertices[2];
			break;
		case SOUTH:
			sPos = vertices[3];
			break;
		}
	}

	public abstract void draw(Graphics2D g2, Dir dir);

	public abstract void toOutputStream(DataOutputStream dout) throws IOException;

	public abstract Item fromInputStream(DataInputStream din) throws IOException;

}
