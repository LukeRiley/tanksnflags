package tanksnflags.tokens;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.ui.ImageLoader;

public class Door extends Item {

	private int height = 1;
	private Key key;
	private boolean locked = true;

	public Door(Vector pos, IsoLogic iL, Key k) {
		super(pos, iL);
		vertical = 29;
		key = k;
	}

	public String unlock(Key k) {
		if (k == key) {
			locked = false;
			return "Door unlocked";
		}
		return "The key doesn't fit";
	}

	@Override
	public void draw(Graphics2D g2) {
		Vector sPos = iL.isoToScreen(this);

		for (int i = 0; i < height; i++) {
			if (locked) {
				g2.drawImage(RED, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
			} else {
				g2.drawImage(BLUE, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
			}
		}
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeDouble(iL.screenToIso(pos).getQ());
		dout.writeDouble(iL.screenToIso(pos).getT());
		dout.writeBoolean(locked);
		key.toOutputStream(dout);
	}

	@Override
	public Item fromInputStream(DataInputStream din, IsoLogic iL) throws IOException {
		Vector v = new Vector(din.readDouble(), din.readDouble());
		boolean l = din.readBoolean();
		Key k = new Key(v, iL);
		k = (Key) k.fromInputStream(din, iL);
		Door d = new Door(v, iL, k);
		return d;
	}

	private static final Image RED = ImageLoader.loadImage("tile.png");

	private static final Image BLUE = ImageLoader.loadImage("flag.png");

}
