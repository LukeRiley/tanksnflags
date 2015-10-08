package tanksnflags.tests;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.MovingItem;
import tanksnflags.tokens.Tank;
import tanksnflags.tokens.Tile;
import tanksnflags.tokens.Wall;

public class Window extends JFrame implements MouseListener, KeyListener {

	public enum TILECOLOR {
		RED, BLUE
	}

	JPanel canvas;
	Dimension canvasSize = new Dimension(700, 700);
	Point AXIS_INT = new Point(500, 450);
	int count = 0;
	IsoLogic isoLogic = new IsoLogic(Math.toRadians(30), Math.toRadians(330), AXIS_INT.getX(), AXIS_INT.getY());

	List<Tile> tiles = new ArrayList<Tile>();
	List<Wall> walls = new ArrayList<Wall>();
	List<Item> itemList = new ArrayList<Item>();
	Tank tank = new Tank(new Vector(0, 0), isoLogic, 50, Item.Dir.EAST);

	public Window() {
		initializeItems();
		canvas = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				drawAxis(g);
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
			itemList.get(i).draw(g2);
		}
	}

	public void tick() {
		/*
		 * itemList.remove(tank); itemList.add(tank); tank.tick();
		 */
	}

	private void initializeItems() {
		for (int u = 0; u < 8; u++) {
			for (int v = 0; v < 8; v++) {

				/*
				 * if (u == 10 - 1 || v == 10 - 1 || u == 0 || v == 0) { Wall
				 * wall = new Wall(new Vector(u * 46 - 230, v * 46 - 230),
				 * isoLogic); walls.add(wall); itemList.add(wall); }
				 */
/*
				Tile tile = new Tile(new Vector(u * 46, v * 46), isoLogic, Item.Dir.EAST);
				tiles.add(tile);
				itemList.add(tile);*/
			}
		}
		itemList.add(tank);
	}

	public void rotate() {
		System.out.println(tank.pos());
		isoLogic.rotateAxis();
		for (Item item : itemList) {
			item.setDir(item.leftOf(item.dir()));
		}
		this.repaint();
	}

	public void drawAxis(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderCollection(g2);
		g2.drawLine((int) isoLogic.isoToScreen(new Vector(0, 0)).getQ(),
				(int) isoLogic.isoToScreen(new Vector(0, 0)).getT(),
				(int) isoLogic.isoToScreen(new Vector(500, 0)).getQ(),
				(int) isoLogic.isoToScreen(new Vector(500, 0)).getT());
		g2.setColor(Color.yellow);
		g2.drawLine((int) isoLogic.isoToScreen(new Vector(0, 0)).getQ(),
				(int) isoLogic.isoToScreen(new Vector(0, 0)).getT(),
				(int) isoLogic.isoToScreen(new Vector(0, 500)).getQ(),
				(int) isoLogic.isoToScreen(new Vector(0, 500)).getT());

	}

	public static void main(String[] args) {
		new Window();
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
		for (Tile Tile : tiles) {
			if (Tile.contains(iso.getQ(), iso.getT())) {
				if (Tile.getColor() == TILECOLOR.BLUE) {
					Tile.setRed();
					Tile.moveVertical(-10);
				} else {
					Tile.setBlue();
					Tile.moveVertical(10);
				}
				this.repaint();
				break;
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
			if (!item.equals(character)
					&& itemPos.equalsDelta(new Vector(character.pos().getQ() + 46, character.pos().getT()), 10)
					&& item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public boolean canMoveDown(MovingItem character) {
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character)
					&& itemPos.equalsDelta(new Vector(character.pos().getQ() - 46, character.pos().getT()), 10)
					&& item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public boolean canMoveRight(MovingItem character) {
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character)
					&& itemPos.equalsDelta(new Vector(character.pos().getQ(), character.pos().getT() + 46), 10)
					&& item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	public boolean canMoveLeft(MovingItem character) {
		for (Item item : itemList) {
			Vector itemPos = item.pos();
			if (!item.equals(character)
					&& itemPos.equalsDelta(new Vector(character.pos().getQ(), character.pos().getT() - 46), 10)
					&& item.vertical() >= character.vertical()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		itemList.remove(tank);// for some reason the tank has to be removed
								// otherwise the sorting doesn't work properly
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
