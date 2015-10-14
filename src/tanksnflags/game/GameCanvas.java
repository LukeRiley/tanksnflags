package tanksnflags.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import tanksnflags.game.Game.DepthComparator;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.Tank;

public class GameCanvas extends JPanel {

	private Game game;
	private int count = 0;
	private IsoLogic isoLogic;
	private Dir dir = Dir.EAST;
	private BufferedImage backgroundImage;


	public GameCanvas(Game game, IsoLogic isoLogic) {
		this.game = game;
		this.isoLogic = isoLogic;
		this.setLayout(null);
		try {
			backgroundImage = ImageIO.read(new File("src/tanksnflags/ui/images/background.png"));		    
		} catch (IOException e) {
		}
		this.setVisible(true);
	}
	
	/**
	 * Gets the preferred size of the canvas
	 * @return The preferred size of the canvas
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000,650);
	}

	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(backgroundImage, 0, 0, null);
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
			itemList.get(i).draw(g2, dir, isoLogic);
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
