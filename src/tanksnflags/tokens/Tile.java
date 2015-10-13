package tanksnflags.tokens;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.game.Game;
import tanksnflags.game.Game.TILECOLOR;
import tanksnflags.helpers.*;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.ui.ImageLoader;

public class Tile extends Wall {

	private TILECOLOR color = TILECOLOR.BLUE;

	public Tile(Vector pos, IsoLogic iL) {
		super(pos, iL);
		vertical = 0;
		// TODO Auto-generated constructor stub
	}

	public void moveVertical(int delta) {
		vertical += delta;
	}

	@Override
	public void draw(Graphics2D g2, Dir dir) {
		calculateDrawPos(dir);
		if (color == TILECOLOR.RED) {
			g2.drawImage(RED, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
		} else {
			g2.drawImage(BLUE, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
		}

	}


	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeByte(Game.TILE);
		dout.writeDouble(pos.getQ());
		dout.writeDouble(pos.getT());
	}

	public static Tile fromInputStream(double u, double v, DataInputStream din, IsoLogic iL) throws IOException {
		return new Tile(new Vector(u, v), iL);
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
