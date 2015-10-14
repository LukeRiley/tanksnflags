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
 * @author Haylem
 *
 */
public class Game extends JFrame {

	public enum TILECOLOR {
		RED, BLUE, GREY
	}

	Map<Integer, List<Item>> rooms = new HashMap<Integer, List<Item>>();
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
	 * Add a tank to the game.
	 * 
	 * @param uid
	 *            the unique id of the game.
	 */
	public synchronized void registerTank(int uid) {
		for (int r : rooms.keySet()) {
			for (int u = -size / 2; u < size / 2; u++) {
				for (int v = -size / 2; v < size / 2; v++) {
					if (!occupied(u, v, r)) {
						Tank newTank = new Tank(new Vector(u * 46, v * 46), uid);
						tanks.add(newTank);
						rooms.get(r).add(newTank);
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

		for (Item item : rooms.get(rID)) {
			if (item.pos().equals(new Vector(u * 46, v * 46)) && item.vertical() == 29) {
				return true;
			}
		}
		return false;
	}

	public void tick() {
		for (int r : rooms.keySet()) {
			for (Item item : rooms.get(r)) {
				if (item instanceof MovingItem) {
					MovingItem mItem = (MovingItem) item;
					mItem.tick();
				}
			}
		}
	}

	private void initializeItems() {
		int nRooms = 1;
		for (int r = 0; r <= nRooms; r++) {
			List<Item> itemList = new ArrayList<Item>();
			for (int u = -size / 2; u < size / 2; u++) {
				for (int v = -size / 2; v < size / 2; v++) {
					if (u == -size / 2 || v == -size / 2 || u == size / 2 - 1
							|| v == size / 2 - 1 && (u != 2 && v != 2)) {
						itemList.add(new Wall(new Vector(u * 46, v * 46)));
					}

					if (u == 2 && v == 3) {
						itemList.add(new Door(new Vector(u * 46, v * 46), new int[] { 0, 1 }));
					}

					itemList.add(new Tile(new Vector(u * 46, v * 46)));
				}
			}
			rooms.put(r, itemList);
		}

		for (int r = 0; r < nRooms; r++) {
			Random rnd = new Random();
			int nKeys = 5;
			List<Item> itemList = rooms.get(r);
			for (int i = 0; i < 5; i++) {
				int u = rnd.nextInt(size / 2);
				int v = rnd.nextInt(size / 2);
				if (!occupied(u, v, r)) {
					itemList.add(new Key(new Vector(u * 46, v * 46)));

				}
			}

		}

	}

	public void removeItem(Item toRemove) {
		for (int key : rooms.keySet()) {
			rooms.get(key).remove(toRemove);
		}
	}

	public boolean canMoveUp(MovingItem character) {
		if (occupied((int) character.pos().getQ(), (int) character.pos().getT() + 46, character.room)) {
			return false;
		}
		for (Item item : rooms.get(character.room)) {
			Vector itemPos = item.pos();
			if (!item.equals(character)
					&& itemPos.equalsDelta(new Vector(character.pos().getQ() + 46, character.pos().getT()), 0)
					&& item.vertical() >= character.vertical()) {
				if (item instanceof Key) {
					removeItem(item);
					return true;
				} else if (item instanceof Door) {
					Door d = (Door) item;
					if(!d.locked){
						rooms.get(character.room).remove(character);
						character.updateRoom(d);
						rooms.get(character.room).add(character);
						return false;
					}
				}
				return false;
			}
		}
		return true;
	}

	public boolean canMoveDown(MovingItem character) {
		if (occupied((int) character.pos().getQ(), (int) character.pos().getT() - 46, character.room)) {
			return false;
		}
		for (Item item : rooms.get(character.room)) {
			Vector itemPos = item.pos();
			if (!item.equals(character)
					&& itemPos.equalsDelta(new Vector(character.pos().getQ() - 46, character.pos().getT()), 0)
					&& item.vertical() >= character.vertical()) {
				if (item instanceof Key) {
					Key key = (Key) item;
					System.out.println(key.keyNo);
					removeItem(item);
					return true;
				} else if (item instanceof Door) {
					Door d = (Door) item;
					if(!d.locked){
						rooms.get(character.room).remove(character);
						character.updateRoom(d);
						rooms.get(character.room).add(character);
						return false;
					}
				}
				return false;
			}
		}
		return true;
	}

	public boolean canMoveRight(MovingItem character) {
		if (occupied((int) character.pos().getQ() + 46, (int) character.pos().getT(), character.room)) {
			return false;
		}
		for (Item item : rooms.get(character.room)) {
			Vector itemPos = item.pos();
			if (!item.equals(character)
					&& itemPos.equalsDelta(new Vector(character.pos().getQ(), character.pos().getT() + 46), 0)
					&& item.vertical() >= character.vertical()) {
				if (item instanceof Key) {
					removeItem(item);
					return true;
				} else if (item instanceof Door) {
					Door d = (Door) item;
					if(!d.locked){
						rooms.get(character.room).remove(character);
						character.updateRoom(d);
						rooms.get(character.room).add(character);
						return false;
					}
				}
				return false;
			}
		}
		return true;
	}

	public boolean canMoveLeft(MovingItem character) {
		if (occupied((int) character.pos().getQ() - 46, (int) character.pos().getT(), character.room)) {
			return false;
		}
		for (Item item : rooms.get(character.room)) {
			Vector itemPos = item.pos();
			if (!item.equals(character)
					&& itemPos.equalsDelta(new Vector(character.pos().getQ(), character.pos().getT() - 46), 0)
					&& item.vertical() >= character.vertical()) {
				if (item instanceof Key) {
					removeItem(item);
					return true;
				} else if (item instanceof Door) {
					Door d = (Door) item;
					if(!d.locked){
						rooms.get(character.room).remove(character);
						character.updateRoom(d);
						rooms.get(character.room).add(character);
						return false;
					}
				}
				return false;
			}
		}
		return true;
	}

	public synchronized byte[] toByteArray() throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);

		dout.writeInt(rooms.size());
		for (Integer roomID : rooms.keySet()) {
			dout.writeInt(roomID);
			dout.writeInt(rooms.get(roomID).size());
			for (Item item : rooms.get(roomID)) {
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
					tanks.add((Tank) item);
				}
				itemList.add(item);
			}
			rooms.put(roomID, itemList);
		}
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
