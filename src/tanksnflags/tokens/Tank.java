package tanksnflags.tokens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.tests.Window;
import tanksnflags.tests.Window.TILECOLOR;
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
	
	public int uid(){
		return uid;
	}

	@Override
	public void draw(Graphics2D g2) {
		Vector sPos = iL.isoToScreen(this);
		if (color == TILECOLOR.RED) {
			g2.drawImage(RED, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);

		} else {
			g2.drawImage(BLUE, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
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

	public void moveUp() {
		pos = new Vector(pos.getQ() + 46, pos.getT());
	}

	public void moveDown() {
		pos = new Vector(pos.getQ() - 46, pos.getT());

	}

	public void moveRight() {
		pos = new Vector(pos.getQ(), pos.getT() + 46);

	}

	public void moveLeft() {
		pos = new Vector(pos.getQ(), pos.getT() - 46);

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

	public void move(Vector delta) {
		pos = pos.add(delta);
	}
}
