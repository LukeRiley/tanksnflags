package tanksnflags.tokens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.game.Game;
import tanksnflags.game.Game.TILECOLOR;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.ui.ImageLoader;

/**
 * A tank in the game. Assigned a unique id to link it to a player in the game
 * with the same id.
 * 
 * @author Haylem
 *
 */
public class Tank extends MovingItem {

	private int uid;

	TILECOLOR color = TILECOLOR.BLUE;

	public Tank(Vector pos, IsoLogic iL, int uid) {
		super(pos, iL);
		this.uid = uid;
		vertical = 29;
	}

	public int uid() {
		return uid;
	}

	@Override
	public void draw(Graphics2D g2, Dir dir) {
		calculateDrawPos(dir);
		if (color == TILECOLOR.RED) {
			g2.drawImage(RED, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);

		} else if (color == TILECOLOR.BLUE) {
			g2.drawImage(BLUE, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
		} else {
			g2.drawImage(GREY, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
		}
		g2.setColor(Color.white);
	}

	public TILECOLOR getColor() {
		return color;
	}

	public void setBlue() {
		color = TILECOLOR.BLUE;
	}

	public void setRed() {
		color = TILECOLOR.RED;
	}

	public void setGrey() {
		color = TILECOLOR.GREY;
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeByte(Game.TANK);
		dout.writeDouble(pos.getQ());
		dout.writeDouble(pos.getT());
		dout.writeInt(uid);
	}

	public static Tank fromInputStream(double u, double v, DataInputStream din, IsoLogic iL) throws IOException {
		int uid = din.readInt();
		return new Tank(new Vector(u, v), iL, uid);
	}

	private static final Image RED = ImageLoader.loadImage("tileRed.png");

	private static final Image BLUE = ImageLoader.loadImage("tileRed.png");

	private static final Image GREY = ImageLoader.loadImage("tileGrey.png");

	@Override
	protected void renderTick(int moveRate) {
		if (dir == null) {
			return;
		}
		switch (dir) {
		case NORTH:
			pos = pos.add(new Vector(moveRate, 0));
			break;
		case SOUTH:
			pos = pos.add(new Vector(-moveRate, 0));
			break;
		case WEST:
			pos = pos.add(new Vector(0, -moveRate));
			break;
		case EAST:
			pos = pos.add(new Vector(0, moveRate));
			break;
		}
	}
}
