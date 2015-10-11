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
import tanksnflags.tokens.Item;
import tanksnflags.tokens.MovingItem;
import tanksnflags.tokens.Tank;
import tanksnflags.tokens.Tile;
import tanksnflags.tokens.Wall;

public class Game extends JFrame implements MouseListener, KeyListener {

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
		canvas.addMouseListener(this);
		this.addKeyListener(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void renderCollection(Graphics2D g2) {
		Comparator<Item> comp = new DepthComparator(isoLogic);
		Collections.sort(itemList, comp);
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).pos().getQ() < 0 || itemList.get(i).pos().getT() < 0) {
				System.out.println(itemList.get(i).pos());
			}
			itemList.get(i).draw(g2, dir);
		}
	}

	public void tick() {
		itemList.remove(tank);
		itemList.add(tank);
		tank.tick();
	}

	private void initializeItems() {
		for (int u = -4; u < 4; u++) {
			for (int v = -4; v < 4; v++) {

				if (u == -4 || v == -4 || u == 3 || v == 3) {
					Wall wall = new Wall(new Vector(u * 46, v * 46), isoLogic);
					itemList.add(wall);
				}

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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Vector iso = isoLogic.screenToIso(arg0.getX(), arg0.getY());
		for (Item item : itemList) {
			if (item instanceof Tile) {
				Tile tile = (Tile) item;
				if (tile.contains(iso.getQ(), iso.getT())) {
					if (tile.getColor() == TILECOLOR.BLUE) {
						tile.setRed();
						tile.moveVertical(-10);
					} else {
						tile.setBlue();
						tile.moveVertical(10);
					}
					this.repaint();
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

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

	@Override
	public void keyPressed(KeyEvent e) {
		itemList.remove(tank);// for some reason the tank has to be removed otherwise the sorting doesn't work properly
		if (e.getKeyCode() == (e.VK_UP)) {
			if (canMoveUp(tank))
				tank.moveUp();
		}
		if (e.getKeyCode() == (e.VK_DOWN)) {
			if (canMoveDown(tank))
				tank.moveDown();
		}
		if (e.getKeyCode() == (e.VK_RIGHT)) {
			if (canMoveRight(tank))
				tank.moveRight();
		}
		if (e.getKeyCode() == (e.VK_LEFT)) {
			if (canMoveLeft(tank))
				tank.moveLeft();
		}
		if (e.getKeyCode() == e.VK_9) {
			rotate();
			repaint();
		}
		itemList.add(tank);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}

class DepthComparator implements Comparator<Item> {

	IsoLogic iL;

	public DepthComparator(IsoLogic iL) {
		this.iL = iL;
	}

	@Override
	public int compare(Item i1, Item i2) {
		if (iL.isoToScreen(i1).equals(iL.isoToScreen(i2))) {
			return i1.vertical() - i2.vertical();
		}
		return (int) (iL.isoToScreen(i1).getT() - iL.isoToScreen(i2).getT());
	}

}
