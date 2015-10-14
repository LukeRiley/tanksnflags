package tanksnflags.tokens;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.game.Game;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.helpers.Vector;
import tanksnflags.ui.ImageLoader;

public class Door extends Item {

	private int height = 1;
	private Key key;
	public boolean locked = false;
	private int[] rooms = new int[2];

	public Door(Vector pos, int[] rooms) {
		super(pos);
		vertical = 29;
		this.rooms = rooms;
	}

	public boolean unlock(Key k) {
		if (k == key) {
			locked = false;
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics2D g2, Dir dir, IsoLogic iL) {
		Vector sPos = iL.isoToScreen(this);

		for (int i = 0; i < height; i++) {
			if (locked) {
				g2.drawImage(RED, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
			} else {
				g2.drawImage(BLUE, (int) sPos.getQ(), (int) sPos.getT() - 23 - vertical, null);
			}
		}
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.write(Game.DOOR);
		dout.writeDouble(pos.getQ());
		dout.writeDouble(pos.getT());
		dout.writeBoolean(locked);
		dout.writeInt(rooms[0]);
		dout.writeInt(rooms[1]);
	}

	public static Door fromInputStream(double u, double v, DataInputStream din) throws IOException {
		Vector vec = new Vector(u, v);
		boolean l = din.readBoolean();
		int[] rooms = new int[2];
		rooms[0] = din.readInt();
		rooms[1] = din.readInt();
		Door d = new Door(vec, rooms);
		d.locked = l;
		return d;
		// doesnt need to read in the position, passed through as an argument.
	}
	
	public int[] getRooms(){
		return rooms;
	}

	private static final Image RED = ImageLoader.loadImage("tileRed.png");

	private static final Image BLUE = ImageLoader.loadImage("tileBlue.png");

}
