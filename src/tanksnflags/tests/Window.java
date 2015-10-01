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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import tanksnflags.game.Floor;
import tanksnflags.game.Item;
import tanksnflags.game.Tank;
import tanksnflags.game.Wall;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;

public class Window extends JFrame implements MouseListener {

	JPanel canvas;
	Dimension canvasSize = new Dimension(700,700);
	Point AXIS_INT = new Point(40, 500);
	
	IsoLogic isoLogic = new IsoLogic(Math.toRadians(60), Math.toRadians(60), AXIS_INT.getX(), AXIS_INT.getY());
	
	List<Wall> walls = new ArrayList<Wall>();
	List<Tank> tanks = new ArrayList<Tank>();
	Item[][] items;
	Item[][] floor;
	

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
		for(Tank t: tanks){
			t.draw(g2);
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
		for (int u = 1; u < items[0].length-1; u++) {
			for (int v = 1; v < items.length-1; v++) {
				Floor floor = new Floor(new Vector(u * 46, v * 46), isoLogic);
				items[u][v] = floor;
			}
		}
		Tank t1 = new Tank(new Vector(3*46,6*46), isoLogic, 1);
		Tank t2 = new Tank(new Vector(7*46,2*46), isoLogic, 2);
		t2.setWest();
		tanks.add(t1);
		tanks.add(t2);
		
	}

	public void drawAxis(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawLine(AXIS_INT.x, AXIS_INT.y, (int) isoLogic.isoToScreen(0, 300).getQ(), (int) isoLogic.isoToScreen(0, 300).getT());
		g2.drawLine(AXIS_INT.x, AXIS_INT.y, (int) isoLogic.isoToScreen(300, 0).getQ(), (int) isoLogic.isoToScreen(300, 0).getT());
		renderFromArray(g2);
	}

	public static void main(String[] args) {
		try {
		    File yourFile = new File("champ.wav");
		    AudioInputStream stream;
		    AudioFormat format;
		    DataLine.Info info;
		    Clip clip;

		    stream = AudioSystem.getAudioInputStream(yourFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		}
		catch (Exception e) {
		    System.out.println(e.getMessage());
		}
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
		tanks.get(1).setPosition(iso);
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
