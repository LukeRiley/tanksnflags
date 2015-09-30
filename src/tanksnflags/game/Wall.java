package tanksnflags.game;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.helpers.*;
import tanksnflags.ui.ImageLoader;

public class Wall extends Item {

	IsoLogic iL = new IsoLogic(Math.toRadians(60), Math.toRadians(60), 0, 500);

	public enum TILECOLOR {
		RED, BLUE
	}

	public Dimension size = new Dimension(48, 48);

	private TILECOLOR color = TILECOLOR.BLUE;

	public Wall(double isoX, double isoY) {
		super(isoX, isoY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics2D g2) {
		if (color == TILECOLOR.RED) {
			g2.drawImage(RED, (int) iL.isoToScreen(isoX, isoY).getQ(), (int) iL.isoToScreen(isoX, isoY).getT(), null);

		} else {
			g2.drawImage(BLUE, (int) iL.isoToScreen(isoX, isoY).getQ(), (int) iL.isoToScreen(isoX, isoY).getT() - 23, null);
		}
	}

	public boolean contains(double x, double y) {
		if (x < isoX || x > isoX + size.getWidth()) {
			return false;
		}

		if (y < isoY || y > isoY + size.getHeight()) {
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

	private static final Image RED = ImageLoader.loadImage("tileRed.png");

	private static final Image BLUE = ImageLoader.loadImage("tileBlue.png");

}
