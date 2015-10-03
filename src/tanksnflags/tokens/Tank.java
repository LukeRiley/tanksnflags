package tanksnflags.tokens;

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

	private int vertical = 28;

	TILECOLOR color = TILECOLOR.BLUE;

	public Tank(Vector pos,Vector gridPos, IsoLogic iL, int uid) {
		super(pos, iL, gridPos);
		this.uid = uid;
	}

	@Override
	public void draw(Graphics2D g2) {
		if (color == TILECOLOR.RED) {
			g2.drawImage(RED, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);

		} else {
			g2.drawImage(BLUE, (int) iL.isoToScreen(pos.getQ(), pos.getT()).getQ(), (int) iL.isoToScreen(pos.getQ(), pos.getT()).getT() - 23 - vertical, null);
		}
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
		gridPos = new Vector(gridPos.getQ() + 1, gridPos.getT());
		pos = pos.add(new Vector(46, 0));
	}

	public void moveDown() {
		gridPos = new Vector(gridPos.getQ() - 1, gridPos.getT());
		pos = pos.add(new Vector(-46, 0));

	}

	public void moveRight() {
		gridPos = new Vector(gridPos.getQ(), gridPos.getT() + 1);
		pos = pos.add(new Vector(0, 46));

	}

	public void moveLeft() {
		gridPos = new Vector(gridPos.getQ(), gridPos.getT() - 1);
		pos = pos.add(new Vector(0, -46));

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
	
	public Vector getGridPos(){
		return gridPos;
	}

	@Override
	public void setGridPos(Vector v) {
		// TODO Auto-generated method stub
		
	}

	// Images of tank
}
