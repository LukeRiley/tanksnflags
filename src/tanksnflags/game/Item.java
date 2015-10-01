package tanksnflags.game;

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

	protected Vector pos;

	protected IsoLogic iL;

	public Item(Vector pos, IsoLogic iL) {
		this.pos = pos;
		this.iL = iL;
	}

	public abstract void draw(Graphics2D g2);

	public abstract void toOutputStream(DataOutputStream dout) throws IOException;

	public abstract Item fromInputStream(DataInputStream din) throws IOException;

}
