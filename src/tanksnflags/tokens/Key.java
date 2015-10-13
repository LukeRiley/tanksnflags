package tanksnflags.tokens;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.ui.ImageLoader;

public class Key extends Item {
	private int height = 1;
	public int keyNo;

	public Key(Vector pos, IsoLogic iL) {
		super(pos, iL);
		vertical = 29;
		Random r = new Random();
		keyNo = r.nextInt(100);
	}
	
	public Key(Vector pos, IsoLogic iL, int kn) {
		super(pos, iL);
		vertical = 29;
		keyNo = kn;
	}

	@Override
	public void draw(Graphics2D g2) {
		Vector sPos = iL.isoToScreen(this);

		for (int i = 0; i < height; i++) {
			g2.drawImage(RED, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
		}
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeDouble(iL.screenToIso(pos).getQ());
		dout.writeDouble(iL.screenToIso(pos).getT());
		dout.writeInt(keyNo);
	}

	@Override
	public Item fromInputStream(DataInputStream din, IsoLogic iL) throws IOException {
		Vector v = new Vector(din.readDouble(), din.readDouble());
		int kn = din.readInt();
		Key k = new  Key(v, iL, kn);
		return k;
	}

	private static final Image RED = ImageLoader.loadImage("tile.png");

	private static final Image BLUE = ImageLoader.loadImage("flag.png");

}