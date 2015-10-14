package tanksnflags.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.helpers.Vector;
import tanksnflags.tokens.Door;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.Key;
import tanksnflags.tokens.MovingItem;
import tanksnflags.tokens.Tank;
import tanksnflags.tokens.Tile;
import tanksnflags.tokens.Wall;

/**
 * Game model. Stores all items present in the game and checks whether their
 * movement is valid.
 * 
 * @author 300311206 raynerhayl
 *
 */
public class Game extends JFrame {

	public enum TILECOLOR {
		RED, BLUE, GREY
	}

	private Map<Integer, List<Item>> rooms = new HashMap<Integer, List<Item>>();
	IsoLogic isoLogic;
	List<Tank> tanks = new ArrayList<Tank>(); // for easy access to the tanks in
	int size = 8;
	Dir dir = Dir.EAST;
	public static final int WALL = 1;
	public static final int TANK = 2;
	public static final int TILE = 3;
	public static final int DOOR = 4;
	public static final int KEY = 5;
	public int uid;
	private int numKeys = 0;

	public Game(IsoLogic isoLogic, int uid) {
		this.isoLogic = isoLogic;
		this.uid = uid;
		initializeItems();
	}

	public Tank tank(int uid) {
		for (Tank tank : tanks) {
			// System.out.println("id: " + tank.uid());
			if (tank.uid() == uid) {
				return tank;
			}
		}
		return null;
	}

	/**
	 * Add a tank to the game. Used by the master to add tanks to the remote
	 * game model
	 * 
	 * @param uid
	 *            the unique id of the game.
	 */
	public synchronized void registerTank(int uid) {
		for (int r : getRooms().keySet()) {
			for (int u = -size / 2; u < size / 2; u++) {
				for (int v = -size / 2; v < size / 2; v++) {
					if (!occupied(u, v, r)) {
						Tank newTank = new Tank(new Vector(u * 46, v * 46), uid);
						tanks.add(newTank);
						getRooms().get(r).add(newTank);
						return;
					}
				}
			}
		}
		// should probably throw an exception
	}

	/**
	 * Returns whether the given coordinates contain a moving item
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	private boolean occupied(int u, int v, int rID) {

		for (Item item : getRooms().get(rID)) {
			if (item.pos().equals(new Vector(u * 46, v * 46)) && item.vertical() == 29) {
				return true;
			}
		}
		return false;
	}

	public void tick() {
		for (int r : getRooms().keySet()) {
			for (Item item : getRooms().get(r)) {
				if (item instanceof MovingItem) {
					MovingItem mItem = (MovingItem) item;
					mItem.tick();
				}
			}
		}
	}

	private void initializeItems() {
		int nRooms = 2;
		for (int r = 0; r <= nRooms; r++) {
			List<Item> itemList = new ArrayList<Item>();
			for (int u = -size / 2; u < size / 2; u++) {
				for (int v = -size / 2; v < size / 2; v++) {
					if ((u == -size / 2 || v == -size / 2 || u == size / 2 - 1 || v == size / 2 - 1)
							&& (u == 2 && v == size / 2 - 1) == false) {
						itemList.add(new Wall(new Vector(u * 46, v * 46)));
					}

					if (u == 2 && v == size / 2 - 1) {
						itemList.add(new Door(new Vector(u * 46, v * 46), new int[] { 0, 1 }));
					}

					itemList.add(new Tile(new Vector(u * 46, v * 46)));
				}
			}
			getRooms().put(r, itemList);
		}

		for (int r = 0; r < nRooms; r++) {
			Random rnd = new Random();
			int nKeys = 5;
			List<Item> itemList = getRooms().get(r);
			for (int i = 0; i < 5; i++) {
				int u = rnd.nextInt(size) - size / 2;
				int v = rnd.nextInt(size) - size / 2;
				if (!occupied(u, v, r)) {
					itemList.add(new Key(new Vector(u * 46, v * 46)));
				}
			}

		}

	}

	public void removeItem(Item toRemove) {
		for (int key : getRooms().keySet()) {
			getRooms().get(key).remove(toRemove);
		}
	}

	/**
	 * Draps a key behind the player.
	 */
	public void dropItem(Tank tank) {
		//if a key is available to drop
		if (tank.getNumKeys() > 0) {
			Vector v = tank.pos();
			//create a new key to drop as actual picked up key objects are not stored, just a count
			Key key = new Key(new Vector(v.getQ(), v.getT()));
			//add it to the room to be picked up later
			rooms.get(tank.room).add(key);
			tank.reduceNumKeys();
		}
	}
	/**
	 * if possible will unlock the door
	 * if the door is unlocked then move through to next room and lock behind
	 */
	public void enterDoor(MovingItem character, Door door) {
		//cast as tank so the number of keys can be checked.
		Tank tank = (Tank) character;
		if (tank.getNumKeys() > 0 && door.locked) {
			//if door is locked and a key is available unlock it
			door.unlock();
			tank.reduceNumKeys();
		}
		else if (!door.locked) {
			//re lock door so that another key is needed
			door.lock();
			//remove player from old room
			rooms.get(character.room).remove(character);
			int[] rooms = door.getRooms();
			//change the room number that is stored in the tank/moving item
			int room = character.room;
			if (room == rooms[0]) {
				room = rooms[1];
			} else {
				room = rooms[0];
			}
			character.room = room;
			//place tank in new room in random position
			Random rnd = new Random();
			while (true) {
				List<Item> itemList = getRooms().get(character.room);
				int u = rnd.nextInt(size) - size / 2;
				int v = rnd.nextInt(size) - size / 2;
				if (!occupied(u, v, character.room)) {
					character.setPos(new Vector(u * 46, v * 46));
					//once a position is found then add the tank to the new rooms item list
					this.rooms.get(character.room).add(character);
					return;
				}
			}
		}

	}

	/**
	 * checks if a player has an empty space above to move to
	 * will pick up any keys on the newly moved to tile
	 * will attempt to move through any doors above
	 */
	public boolean canMoveUp(Tank character) {

		if (occupied((int) character.pos().getQ(), (int) character.pos().getT() + 46, character.room)) {
			return false;
		}
		return checkItem(character, new Vector(character.pos().getQ() + 46, character.pos().getT()));

	}

	
	/**
	 * checks if a player has an empty space below to move to
	 * will pick up any keys on the newly moved to tile
	 * will attempt to below through any doors above
	 */
	public boolean canMoveDown(Tank character) {
		if (occupied((int) character.pos().getQ(), (int) character.pos().getT() - 46, character.room)) {
			return false;
		}
		return checkItem(character, new Vector(character.pos().getQ() - 46, character.pos().getT()));


	}


	/**
	 * checks if a player has an empty space right to move to
	 * will pick up any keys on the newly moved to tile
	 * will attempt to move through any doors right
	 */
	public boolean canMoveRight(Tank character) {
		Tank tank = (Tank) character;
		if (occupied((int) character.pos().getQ() + 46, (int) character.pos().getT(), character.room)) {
			return false;
		}
		return checkItem(character, new Vector((int) character.pos().getQ(), (int) character.pos().getT() + 46));
	}

	/**
	 * checks if a player has an empty space left to move to
	 * will pick up any keys on the newly moved to tile
	 * will attempt to move through any doors left
	 */
	public boolean canMoveLeft(Tank character) {
		if (occupied((int) character.pos().getQ() - 46, (int) character.pos().getT(), character.room)) {
			return false;
		}
		return checkItem(character, new Vector((int) character.pos().getQ(), (int) character.pos().getT() - 46));
	}

	public boolean checkItem(Tank character, Vector move) {
		for (Item item : getRooms().get(character.room)) {
			Vector itemPos = item.pos();
			if (!item.equals(character) && itemPos.equalsDelta(move, 0) && item.vertical() >= character.vertical()) {
				if (item instanceof Key) {
					removeItem(item);
					character.addKey();
					return true;
				} else if (item instanceof Door) {
					Door d = (Door) item;
					enterDoor(character, d);
					return false;

				}
				return false;
			}
		}
		return true;
	}

	public synchronized byte[] toByteArray() throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);

		dout.writeInt(getRooms().size());
		for (Integer roomID : getRooms().keySet()) {
			dout.writeInt(roomID);
			dout.writeInt(getRooms().get(roomID).size());
			for (Item item : getRooms().get(roomID)) {
				item.toOutputStream(dout);
			}
		}
		dout.flush();
		return bout.toByteArray();
	}

	public synchronized void fromByteArray(byte[] bytes) throws IOException {
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(bin);

		int nRooms = din.readInt();
		rooms.clear();
		tanks.clear();
		for (int r = 0; r < nRooms; r++) {
			int roomID = din.readInt();
			int nItems = din.readInt();
			List<Item> itemList = new ArrayList<Item>();
			for (int i = 0; i < nItems; i++) {
				Item item = Item.fromInputStream(din);
				if (item instanceof Tank) {
					Tank nTank = (Tank) item;
					tanks.add(nTank);
					if (nTank.uid() == this.uid) {
						nTank.setRed();
					} else {
						nTank.setGrey();
					}
				}
				itemList.add(item);
			}
			getRooms().put(roomID, itemList);
		}
	}

	public Map<Integer, List<Item>> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Integer, List<Item>> rooms) {
		this.rooms = rooms;
	}

	class DepthComparator implements Comparator<Item> {

		IsoLogic iL;

		public DepthComparator(IsoLogic iL) {
			this.iL = iL;
		}

		@Override
		public int compare(Item i1, Item i2) {

			if (i1.contains(i2)) {
				return i1.vertical() - i2.vertical();
			}

			return (int) (iL.isoToScreen(i1).getT() - iL.isoToScreen(i2).getT());
		}
	}
}
