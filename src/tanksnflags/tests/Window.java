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
import java.util.List;

import javax.swing.*;

import tanksnflags.game.Item;
import tanksnflags.game.Tank;
import tanksnflags.game.Tank.TILECOLOR;
import tanksnflags.game.Wall;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;

public class Window extends JFrame implements MouseListener, KeyListener {

	JPanel canvas;
	Dimension canvasSize = new Dimension(700, 700);
	Point AXIS_INT = new Point(40, 500);

	IsoLogic isoLogic = new IsoLogic(Math.toRadians(60), Math.toRadians(60), AXIS_INT.getX(), AXIS_INT.getY());

	List<Wall> walls = new ArrayList<Wall>();
	Item[][] items;
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

	public void renderFromArray(Graphics2D g2) {
		for (int gridX = items[0].length; gridX >= 0; gridX--) {
			int currentX = gridX;
			for (int gridY = 0; gridY < items.length; gridY++) {
				if (currentX < items[0].length) {
					items[currentX][gridY].draw(g2);
					currentX++;
				}
			}
		}

		for (int gridY = 0; gridY < items[0].length; gridY++) {
			int currentY = gridY;
			for (int gridX = 0; gridX < items.length; gridX++) {
				if (currentY < items.length) {
					items[gridX][currentY].draw(g2);
					currentY++;
				}
			}
		}
	}

	private void initializeItems() {
		items = new Item[10][10];
		for (int u = 0; u < items[0].length; u++) {
			for (int v = 0; v < items.length; v++) {
				Wall wall = new Wall(new Vector(u * 46, v * 46), isoLogic);
				items[u][v] = wall;
				walls.add(wall);
			}
		}
	}

	public void drawAxis(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawLine(AXIS_INT.x, AXIS_INT.y, (int) isoLogic.isoToScreen(0, 300).getQ(), (int) isoLogic.isoToScreen(0, 300).getT());
		g2.drawLine(AXIS_INT.x, AXIS_INT.y, (int) isoLogic.isoToScreen(300, 0).getQ(), (int) isoLogic.isoToScreen(300, 0).getT());
		renderFromArray(g2);
		tank.draw(g2);
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
		for (Wall wall : walls) {
			if (wall.contains(iso.getQ(), iso.getT())) {
				if (wall.getColor() == Wall.TILECOLOR.BLUE) {
					wall.setRed();
				} else {
					wall.setBlue();
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
			if(tank.getColor()==TILECOLOR.BLUE){
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
