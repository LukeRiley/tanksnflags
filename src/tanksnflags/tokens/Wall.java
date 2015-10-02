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

	private int vertical = 0;

	private int height = 1;

	public Dimension size = new Dimension(48, 48);

	public Wall(Vector pos, IsoLogic iL) {
		super(pos, iL);
		// TODO Auto-generated constructor stub
	}

	public void moveVertical(int delta) {
		vertical += delta;
	}

	@Override
	public void draw(Graphics2D g2) {
		for (int i = 0; i < height; i++) {
			g2.drawImage(RED, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 52 - vertical- i*100, null);
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
