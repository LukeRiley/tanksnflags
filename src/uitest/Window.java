package uitest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.*;
import helpers.IsoLogic;

public class Window extends JFrame {

	JPanel canvas;
	Point AXIS_INT = new Point(500, 900);
	IsoLogic isoLogic = new IsoLogic(Math.toRadians(150), Math.toRadians(30), 500, 900);

	public Window() {
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

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void drawAxis(Graphics g) {
		g.drawLine(AXIS_INT.x, AXIS_INT.y, (int) isoLogic.isoToScreen(300, 0)[0], (int) isoLogic.isoToScreen(300, 0)[1]);
		g.setColor(Color.yellow);

		g.drawLine(AXIS_INT.x, AXIS_INT.y, (int) isoLogic.isoToScreen(0, 300)[0], (int) isoLogic.isoToScreen(0, 300)[1]);

	}

	public static void main(String[] args) {
		new Window();
	}

}
