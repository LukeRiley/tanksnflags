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

	public enum TILECOLOR {
		RED, BLUE
	}

	public Dimension size = new Dimension(48, 48);

	private TILECOLOR color = TILECOLOR.BLUE;

	public Wall(Vector pos, IsoLogic iL) {
		super(pos, iL);
		// TODO Auto-generated constructor stub
	}

	public void moveVertical(int delta) {
		vertical += delta;
	}

	@Override
	public void draw(Graphics2D g2) {
		if (color == TILECOLOR.RED) {
			g2.drawImage(RED, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);

		} else {
			g2.drawImage(BLUE, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);
		}
	}

	public boolean contains(double x, double y) {
		if (x < pos.getQ() || x > pos.getQ() + size.getWidth()) {
			return false;
		}

		if (y < pos.getT() || y > pos.getT() + size.getHeight()) {
			return false;
		}

		return true;
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

	public void setBlue() {
		color = TILECOLOR.BLUE;
	}

	public void setRed() {
		color = TILECOLOR.RED;
	}

	public TILECOLOR getColor() {
		return color;
	}

	private static final Image RED = ImageLoader.loadImage("tileRed.png");

	private static final Image BLUE = ImageLoader.loadImage("tileBlue.png");

}
