package game;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

	private double isoX;
	private double isoY;

	public Item(double isoX, double isoY) {
		this.isoX=isoX;
		this.isoY=isoY;
	}

	public abstract void draw(Graphics2D g2);

	public abstract void toOutputStream(DataOutputStream dout) throws IOException;

	public abstract Item fromInputStream(DataInputStream din) throws IOException;

}
