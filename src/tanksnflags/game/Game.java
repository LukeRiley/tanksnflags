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
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.helpers.Vector;
import tanksnflags.tokens.Door;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.MovingItem;
import tanksnflags.tokens.Tank;
import tanksnflags.tokens.Tile;
import tanksnflags.tokens.Wall;

public class Game extends JFrame {

	public enum TILECOLOR {
		RED, BLUE
	}

	List<Item> itemList = new ArrayList<Item>();
	IsoLogic isoLogic;
	List<Tank> tanks = new ArrayList<Tank>();
	int size = 8;

	Dir dir = Dir.EAST;

	public static final int WALL = 1;
	public static final int TANK = 2;
	public static final int TILE = 3;

	public Game(IsoLogic isoLogic) {
		initializeItems();
		this.isoLogic = isoLogic;
	}

	public Tank tank(int uid) {
		for (Tank tank : tanks) {
			if (tank.uid() == uid) {
				return tank;
			}
		}
		return null;
	}

	public synchronized void registerTank(int uid) {
		for (int u = -size / 2; u < size / 2; u++) {
			for (int v = -size / 2; v < size / 2; v++) {
				if (!occupied(u, v)) {
					Tank newTank = new Tank(new Vector(u * 46, v * 46), isoLogic, uid);
					tanks.add(newTank);
					itemList.add(newTank);
					return;
				}
			}
		}
	}

	/**
	 * Returns whether the given coordinates contain a moving item
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	private boolean occupied(int u, int v) {
		for (Item item : itemList) {
			if (item.pos().equals(new Vector(u * 46, v * 46)) && item.vertical() == 29) {
				return true;
			}
		}
		return false;
	}

	public void tick() {
		for (Item item : itemList) {
			if (item instanceof MovingItem) {
				MovingItem mItem = (MovingItem) item;
				mItem.tick();
			}
		}
	}

	private void initializeItems() {
		for (int u = -size / 2; u < size / 2; u++) {
			for (int v = -size / 2; v < size / 2; v++) {

				if (u == -4 || v == -4 || u == 3 || v == 3) {
					Wall wall = new Wall(new Vector(u * 46, v * 46), isoLogic);
					itemList.add(wall);
				}

				Tile tile = new Tile(new Vector(u * 46, v * 46), isoLogic);
				itemList.add(tile);
			}
		}
	}

	public boolean canMoveUp(MovingItem character) {
		if (occupied((int) character.pos().getQ(), (int) character.pos().getT() + 46)) {
			return false;
		}
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character) && itemPos.equalsDelta(new Vector(character.pos().getQ() + 46, character.pos().getT()), 10) && item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public boolean canMoveDown(MovingItem character) {
		if (occupied((int) character.pos().getQ(), (int) character.pos().getT() - 46)) {
			return false;
		}
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character) && itemPos.equalsDelta(new Vector(character.pos().getQ() - 46, character.pos().getT()), 10) && item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public boolean canMoveRight(MovingItem character) {
		if (occupied((int) character.pos().getQ() + 46, (int) character.pos().getT())) {
			return false;
		}
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character) && itemPos.equalsDelta(new Vector(character.pos().getQ(), character.pos().getT() + 46), 10) && item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public boolean canMoveLeft(MovingItem character) {
		if (occupied((int) character.pos().getQ() - 46, (int) character.pos().getT())) {
			return false;
		}
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character) && itemPos.equalsDelta(new Vector(character.pos().getQ(), character.pos().getT() - 46), 10) && item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public synchronized byte[] toByteArray() throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);

		dout.writeInt(itemList.size());
		for (Item item : itemList) {
			item.toOutputStream(dout);
		}
		dout.flush();

		// Finally, return!!
		return bout.toByteArray();
	}

	public synchronized void fromByteArray(byte[] bytes) throws IOException {
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(bin);

		// Third, update characters
		int nItems = din.readInt();
		itemList.clear();
		for (int i = 0; i != nItems; ++i) {
			itemList.add(Item.fromInputStream(din, isoLogic));
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
