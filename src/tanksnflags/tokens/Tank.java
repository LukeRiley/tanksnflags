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
	private int numKeys=0;

	TILECOLOR color = TILECOLOR.BLUE;

	public Tank(Vector pos, int uid) {
		super(pos);
		this.uid = uid;
		vertical = 29;
	}

	public int uid() {
		return uid;
	}
	
	
	public int getNumKeys(){
		return numKeys;
	}
	
	/**
	 * reduces number of keys held by this player
	 */
	public void reduceNumKeys(){
		numKeys--;
	}
	
	/**
	 * Adds one key to the number held by this player
	 */
	public void addKey(){
		numKeys++;
	}

	@Override
	public void draw(Graphics2D g2, Dir dir, IsoLogic iL) {
		calculateDrawPos(dir, iL);
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
		dout.writeInt(room);
		dout.writeInt(uid);
	}

	public static Tank fromInputStream(double u, double v, DataInputStream din) throws IOException {
		int room = din.readInt();
		int uid = din.readInt();
		Tank newTank = new Tank(new Vector(u, v), uid);
		newTank.room = room;
		switch (din.readInt()) {
		case 1:
			newTank.setBlue();
			break;
		case 2:
			newTank.setRed();
			break;
		case 3:
			newTank.setGrey();
			break;
		}
		return newTank;

	}

	private static final Image RED = ImageLoader.loadImage("tileRed.png");

	private static final Image BLUE = ImageLoader.loadImage("tileBlue.png");

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
