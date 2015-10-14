package tanksnflags.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface InventoryItem {
		
		public int getX();
		
		public int getY();
		
		public void setX(int x);
		
		public void setY(int y);
		
		public BufferedImage getImage();
		
		public String getDirection();
		
		public void draw(Graphics g);
	}
