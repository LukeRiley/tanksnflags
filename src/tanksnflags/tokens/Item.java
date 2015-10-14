package tanksnflags.tokens;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.game.Game;
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
	protected int vertical = 0;
	public Dimension size = new Dimension(46, 46);

	public Item(Vector pos) {
		this.pos = pos;
	}

	public int vertical() {
		return vertical;
	}

	public Vector pos() {
		return pos;
	}

	public void calculateDrawPos(Dir dir, IsoLogic iL) {
		sPos = iL.isoToScreen(this);
		Vector[] vertices = new Vector[4];
		vertices[0] = sPos;
		vertices[1] = iL.isoToScreen(new Vector(pos.getQ() + 46, pos.getT()));
		vertices[2] = iL.isoToScreen(new Vector(pos.getQ() + 46, pos.getT() + 46));
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

	public boolean contains(Item other) {
		Vector oPos = other.pos();
		int delta = 0;
		if (oPos.getQ() - delta >= pos.getQ() + size.getWidth()) {
			return false;
		} else if (oPos.getT() - delta >= pos.getT() + size.getHeight()) {
			return false;
		} else if (oPos.getQ() + other.size.getWidth() + delta <= pos.getQ()) {
			return false;
		} else if (oPos.getT() + other.size.getHeight() + delta <= pos.getT()) {
			return false;
		}
		return true;
	}

	public abstract void draw(Graphics2D g2, Dir dir, IsoLogic iL);

	public abstract void toOutputStream(DataOutputStream dout) throws IOException;

	/**
	 * Create an object from an input stream. The type of item is determined by
	 * the type given in the input stream.
	 * 
	 * @param din
	 *            the input stream being read from.
	 * @param iL
	 *            the isometric logic being used by the display.
	 * @return an item corresponding to that encoded by the input stream.
	 * @throws IOException
	 */
	public static Item fromInputStream(DataInputStream din) throws IOException {
		int type = din.readByte();
		double u = din.readDouble();
		double v = din.readDouble();

		if (type == Game.TANK) {
			return Tank.fromInputStream(u, v, din);
		} else if (type == Game.TILE) {
			return Tile.fromInputStream(u, v, din);
		} else if (type == Game.WALL) {
			return Wall.fromInputStream(u, v, din);
		} else if (type ==Game.DOOR) {
			return Door.fromInputStream(u, v, din);
		} else {
			throw new IOException();
		}

	}

}
