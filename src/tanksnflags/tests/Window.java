package tanksnflags.tests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.Tank;
import tanksnflags.tokens.Tile;
import tanksnflags.tokens.Wall;

public class Window extends JFrame implements MouseListener, KeyListener {

	public enum TILECOLOR {
		RED, BLUE
	}

	JPanel canvas;
	Dimension canvasSize = new Dimension(700, 700);
	Point AXIS_INT = new Point(40, 500);

	IsoLogic isoLogic = new IsoLogic(Math.toRadians(60), Math.toRadians(60), AXIS_INT.getX(), AXIS_INT.getY());

	List<Tile> tiles = new ArrayList<Tile>();
	List<Wall> walls = new ArrayList<Wall>();
	AList[][] items;
	Tank tank = new Tank(new Vector(0, 0), isoLogic, 50);

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

	public <T> void renderFromArray(Graphics2D g2, Item c) {
		for (int gridX = items[0].length; gridX >= 0; gridX--) {
			int currentX = gridX;
			for (int gridY = 0; gridY < items.length; gridY++) {
				if (currentX < items[0].length) {
					for (Item item : items[currentX][gridY]) {
						if (item.getClass().equals(c.getClass())) {
							item.draw(g2);
						}
					}
					currentX++;
				}
			}
		}

		for (int gridY = 0; gridY < items[0].length; gridY++) {
			int currentY = gridY;
			for (int gridX = 0; gridX < items.length; gridX++) {
				if (currentY < items.length) {
					for (Item item : items[gridX][currentY]) {
						if (item.getClass().equals(c.getClass())) {
							item.draw(g2);
						}
					}
					currentY++;
				}
			}
		}
	}

	private void initializeItems() {
		items = new AList[10][10];
		for (int u = 0; u < items[0].length; u++) {
			for (int v = 0; v < items.length; v++) {
				List<Item> bucket = new ArrayList<Item>();
				if (u == items[0].length - 1 || v == items.length - 1) {
					Wall wall = new Wall(new Vector(u * 46, v * 46), isoLogic);
					bucket.add(wall);
					walls.add(wall);

				}
				Tile tile = new Tile(new Vector(u * 46, v * 46), isoLogic);
				bucket.add(tile);
				tiles.add(tile);
				items[u][v] = new AList(bucket);

			}
		}
		items[4][1].add(tank);
	}

	public void drawAxis(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawLine(AXIS_INT.x, AXIS_INT.y, (int) isoLogic.isoToScreen(0, 300).getQ(), (int) isoLogic.isoToScreen(0, 300).getT());
		g2.drawLine(AXIS_INT.x, AXIS_INT.y, (int) isoLogic.isoToScreen(300, 0).getQ(), (int) isoLogic.isoToScreen(300, 0).getT());
		renderFromArray(g2, new Tile(new Vector(0, 0), null));
		renderFromArray(g2, tank);
		renderFromArray(g2, new Wall(new Vector(0, 0), null));

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
				} else {
					Tile.setBlue();
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

	@Override
	public void keyPressed(KeyEvent e) {
		Vector move = new Vector(0, 0);
		if (e.getKeyCode() == (e.VK_UP)) {
			tank.move(new Vector(46, 0));
		}
		if (e.getKeyCode() == (e.VK_DOWN)) {
			tank.move(new Vector(-46, 0));
		}
		if (e.getKeyCode() == (e.VK_RIGHT)) {
			tank.move(new Vector(0, 46));
		}
		if (e.getKeyCode() == (e.VK_LEFT)) {
			tank.move(new Vector(0, -46));
		}

		if (e.getKeyCode() == (e.VK_SPACE)) {
			if (tank.getColor() == TILECOLOR.BLUE) {
				tank.setRed();
			} else {
				tank.setBlue();
			}
		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}

class AList implements Iterable<Item> {
	public List<Item> list;

	public AList(List<Item> l) {
		list = l;
	}

	public void add(Item toAdd) {
		list.add(toAdd);
	}

	public Item get(int index) {
		return list.get(index);
	}

	@Override
	public Iterator<Item> iterator() {
		return list.iterator();
	}
}
