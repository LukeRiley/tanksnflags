package tanksnflags.tokens;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import tanksnflags.helpers.IsoLogic;
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

	public Dimension size = new Dimension(46, 46);

	public enum Dir {
		EAST, SOUTH, WEST, NORTH
	}

	protected Vector pos;

	protected IsoLogic iL;

	protected int vertical = 0;

	protected Dir dir;

	public Item(Vector pos, IsoLogic iL, Dir dir) {
		this.pos = pos;
		this.iL = iL;
		this.dir = dir;
	}

	public int vertical() {
		return vertical;
	}

	public Vector pos() {
		return pos;
	}

	public Dir dir() {
		return dir;
	}

	protected Vector getDrawPos(Vector sPos) {
		System.out.println(sPos);
		switch (dir) {
		case EAST:
			return sPos;
		case SOUTH:
			return sPos.add(new Vector(-20, 20));
		case WEST:
			return sPos.add(new Vector(-40, 0));
		default:
			return sPos.add(new Vector(-20, -20));
		}
	}

	public Dir rightOf(Dir dir) {
		switch (dir) {
		case EAST:
			return Dir.SOUTH;
		case SOUTH:
			return Dir.WEST;
		case WEST:
			return Dir.NORTH;
		default:
			return Dir.EAST;
		}
	}

	public Dir leftOf(Dir dir) {
		switch (dir) {
		case EAST:
			return Dir.NORTH;
		case SOUTH:
			return Dir.EAST;
		case WEST:
			return Dir.SOUTH;
		default:
			return Dir.WEST;
		}
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public abstract void draw(Graphics2D g2);

	public abstract void toOutputStream(DataOutputStream dout) throws IOException;

	public abstract Item fromInputStream(DataInputStream din) throws IOException;

}
