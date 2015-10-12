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

	JPanel canvas;
	Dimension canvasSize = new Dimension(700, 700);
	Point AXIS_INT = new Point(500, 450);
	int count = 0;
	IsoLogic isoLogic = new IsoLogic(Math.toRadians(30), Math.toRadians(330), AXIS_INT.getX(), AXIS_INT.getY());

	List<Item> itemList = new ArrayList<Item>();
	Tank tank = new Tank(new Vector(0, 0), isoLogic, 50);

	Dir dir = Dir.EAST;

	public Game() {
		initializeItems();
		canvas = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				renderCollection(g2);
			}
		};
		this.setContentPane(canvas);
		this.setSize(new Dimension(1000, 1000));
		canvas.setBackground(Color.darkGray);
		canvas.setSize(new Dimension(100, 1000));
		canvas.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void renderCollection(Graphics2D g2) {
		Comparator<Item> comp = new DepthComparator(isoLogic);
		Collections.sort(itemList, comp);
		for (int i = 0; i < itemList.size(); i++) {
			itemList.get(i).draw(g2, dir);
			Vector sPos = isoLogic.isoToScreen(itemList.get(i));
			g2.setColor(Color.white);
			/*
			 * if (itemList.get(i) instanceof Tank) {
			 * g2.drawString(String.valueOf(i), (int) sPos.getQ() + 30, (int)
			 * sPos.getT() - 20); } else { g2.drawString(String.valueOf(i),
			 * (int) sPos.getQ() + 30, (int) sPos.getT() + 10); }
			 */
		}
	}

	public Tank tank() {
		return tank;
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
		for (int u = -4; u < 4; u++) {
			for (int v = -4; v < 4; v++) {

				if (u == 2 && v == 2) {
					Door door = new Door(new Vector(u * 46, v * 46), isoLogic, 10);
					itemList.add(door);
				}

				/*
				 * if (u == -4 || v == -4 || u == 3 || v == 3) { Wall wall = new
				 * Wall(new Vector(u * 46, v * 46), isoLogic);
				 * itemList.add(wall); }
				 */

				Tile tile = new Tile(new Vector(u * 46, v * 46), isoLogic);
				itemList.add(tile);
			}
		}
		itemList.add(tank);
	}

	public void readFile(File file) {
		try {
			Scanner scan = new Scanner(file);
			int size = scan.nextInt();
			for (int u = -size / 2; u < size; u++) {
				for (int v = -size / 2; v < size; v++) {
					String token = scan.next();
					switch (token) {
					default:
						itemList.add(new Tile(new Vector(u * 46, v * 46), isoLogic));
						break;
					case "W":
						itemList.add(new Wall(new Vector(u * 46, v * 46), isoLogic));
						break;
					case "T":
						itemList.add(new Tank(new Vector(u * 46, v * 46), isoLogic, 50));
					}
				}
			}

		} catch (IOException e) {
			System.out.println("could not print:" + e);
		}
	}

	public void rotate() {
		System.out.println(tank.pos());
		isoLogic.rotateAxis();
		dir = isoLogic.rotateLeft(dir);
		this.repaint();
	}

	public static void main(String[] args) {
		new Game();
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public boolean canMoveUp(MovingItem character) {
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character) && itemPos.equalsDelta(new Vector(character.pos().getQ() + 46, character.pos().getT()), 10) && item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public boolean canMoveDown(MovingItem character) {
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character) && itemPos.equalsDelta(new Vector(character.pos().getQ() - 46, character.pos().getT()), 10) && item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public boolean canMoveRight(MovingItem character) {
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character) && itemPos.equalsDelta(new Vector(character.pos().getQ(), character.pos().getT() + 46), 10) && item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public boolean canMoveLeft(MovingItem character) {
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character) && itemPos.equalsDelta(new Vector(character.pos().getQ(), character.pos().getT() - 46), 10) && item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
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
