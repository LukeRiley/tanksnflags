package tanksnflags.tokens;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.helpers.*;
import tanksnflags.ui.ImageLoader;

public class Wall extends Item {

	private int height = 1;

	public Wall(Vector pos, IsoLogic iL) {
		super(pos, iL);
		vertical = 29;
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
	}

	@Override
	public Item fromInputStream(DataInputStream din, IsoLogic iL) throws IOException {
		Vector v = new Vector(din.readDouble(), din.readDouble());
		Wall w = new  Wall(v, iL);
		return w;
	}

	private static final Image RED = ImageLoader.loadImage("tile.png");

	private static final Image BLUE = ImageLoader.loadImage("flag.png");

}
