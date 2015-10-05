package tanksnflags.tokens;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;

public abstract class MovingItem extends Item {

	public MovingItem(Vector pos, IsoLogic iL ) {
		super(pos, iL);
	}

	public abstract void moveUp();

	public abstract void moveDown();

	public abstract void moveRight();

	public abstract void moveLeft();

	@Override
	public abstract void draw(Graphics2D g2);

	@Override
	public abstract void toOutputStream(DataOutputStream dout) throws IOException;

	@Override
	public abstract Item fromInputStream(DataInputStream din) throws IOException;

}
