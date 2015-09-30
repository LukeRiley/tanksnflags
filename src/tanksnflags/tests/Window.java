package tanksnflags.tests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import tanksnflags.game.Item;
import tanksnflags.game.Wall;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;

public class Window extends JFrame implements MouseListener {

	JPanel canvas;
	Point AXIS_INT = new Point(500, 900);
	IsoLogic isoLogic = new IsoLogic(Math.toRadians(60), Math.toRadians(60), 0, 500);
	List<Wall> walls = new ArrayList<Wall>();

	public Window() {
		initializeItems();

		canvas = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				drawAxis(g);
			}
		};
		this.setContentPane(canvas);
		this.setSize(new Dimension(1000, 1000));

		canvas.setSize(new Dimension(100, 1000));
		canvas.setVisible(true);

		canvas.addMouseListener(this);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void initializeItems() {
		for (int i = 0; i < 10; i++) {
			walls.add(new Wall(i * 48, 0));
		}
	}

	public void drawAxis(Graphics g) {
		System.out.println(isoLogic.isoToScreen(10, 10));

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.drawLine(0, 500, (int) isoLogic.isoToScreen(0, 300).getQ(), (int) isoLogic.isoToScreen(0, 300).getT());
		g2.drawLine(0, 500, (int) isoLogic.isoToScreen(300, 0).getQ(), (int) isoLogic.isoToScreen(300, 0).getT());

		for (int i = walls.size() - 1; i >= 0; i--) {
			walls.get(i).draw(g2);
		}
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
		System.out.println(isoLogic.screenToIso(arg0.getX(), arg0.getY()));
		Vector iso = isoLogic.screenToIso(arg0.getX(), arg0.getY());
		for(Wall wall: walls){
			if(wall.contains(iso.getQ(), iso.getT())){
				wall.setRed();
				this.repaint();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
