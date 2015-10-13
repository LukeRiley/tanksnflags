package tanksnflags.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import tanksnflags.game.Game.DepthComparator;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.Tank;

public class GameCanvas extends JPanel {

	Game game;
	Point AXIS_INT = new Point(500, 450);
	int count = 0;
	IsoLogic isoLogic;
	//= new IsoLogic(Math.toRadians(30), Math.toRadians(330), AXIS_INT.getX(), AXIS_INT.getY());
	Dir dir = Dir.EAST;

	public GameCanvas(Game game, IsoLogic isoLogic) {
		this.game = game;
		this.isoLogic = isoLogic;
		this.setSize(new Dimension(1000, 1000));
		this.setBackground(Color.darkGray);
		this.setSize(new Dimension(100, 1000));
		this.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (this.game != null) {
			this.renderCollection(g2);
		}
	}

	private void renderCollection(Graphics2D g2) {
		List<Item> itemList = game.itemList;
		Comparator<Item> comp = new DepthComparator(isoLogic);
		Collections.sort(itemList, comp);
		for (int i = 0; i < itemList.size(); i++) {
			itemList.get(i).draw(g2, dir);
			Vector sPos = isoLogic.isoToScreen(itemList.get(i));
			g2.setColor(Color.white);
		}
	}

	public void rotate() {
		isoLogic.rotateAxis();
		dir = isoLogic.rotateLeft(dir);
		this.repaint();
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
