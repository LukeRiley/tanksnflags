package tanksnflags.tokens;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.helpers.*;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.ui.ImageLoader;

public class Wall extends Item {

	private int height = 1;

	public Wall(Vector pos, IsoLogic iL) {
		super(pos, iL);
		vertical = 29;
	}

	@Override
	public void draw(Graphics2D g2, Dir dir) {
		calculateDrawPos(dir);
		for (int i = 0; i < height; i++) {
			g2.drawImage(RED, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
		}
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Item fromInputStream(DataInputStream din) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	private static final Image RED = ImageLoader.loadImage("tileRed.png");

	private static final Image BLUE = ImageLoader.loadImage("tileBlue.png");

}
