package tanksnflags.tokens;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import tanksnflags.game.Game;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.helpers.Vector;
import tanksnflags.ui.ImageLoader;

public class Key extends Item {
	private int height = 1;
	public int keyNo;

	public Key(Vector pos) {
		super(pos);
		vertical = 29;
		//key would generate a special keyNo to match a door
		Random r = new Random();
		keyNo = r.nextInt(100);
	}

	public Key(Vector pos, int kn) {
		super(pos);
		vertical = 29;
		keyNo = kn;
	}

	@Override
	public void draw(Graphics2D g2, Dir dir, IsoLogic iL) {
		calculateDrawPos(dir, iL);
		// for (int i = 0; i < height; i++) {
		g2.drawImage(RED, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
		// }
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeByte(Game.KEY);
		dout.writeDouble(pos.getQ());
		dout.writeDouble(pos.getT());
		dout.writeInt(keyNo);
	}

	public static Key fromInputStream(double u, double v, DataInputStream din) throws IOException {
		int kn = din.readInt();
		Key k = new Key(new Vector(u, v), 5);
		return k;
	}

	private static final Image RED = ImageLoader.loadImage("keyYellow.png");

	private static final Image BLUE = ImageLoader.loadImage("tileBlue.png");

}