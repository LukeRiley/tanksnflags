package tanksnflags.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.Tank;
import tanksnflags.tokens.Wall;

public class Game {

	private IsoLogic isoLogic;

	private Wall[][] walls;
	private List<Wall> wallList = new ArrayList<Wall>();
	private List<Tank> tanks;

	private int size = 10;

	public Game(IsoLogic isoLogic) {
		initializeWalls();
	}

	private void initializeWalls() {
		walls = new Wall[10][10];
		for (int u = 0; u < walls[0].length; u++) {
			for (int v = 0; v < walls.length; v++) {
				Wall wall = new Wall(new Vector(u * 46, v * 46), isoLogic);
				walls[u][v] = wall;
				wallList.add(wall);
			}
		}
	}

	public void renderFromArray(Graphics2D g2) {
		for (int gridX = walls[0].length; gridX >= 0; gridX--) {
			int currentX = gridX;
			for (int gridY = 0; gridY < walls.length; gridY++) {
				if (currentX < walls[0].length) {
					walls[currentX][gridY].draw(g2);
					currentX++;
				}
			}
		}

		for (int gridY = 0; gridY < walls[0].length; gridY++) {
			int currentY = gridY;
			for (int gridX = 0; gridX < walls.length; gridX++) {
				if (currentY < walls.length) {
					walls[gridX][currentY].draw(g2);
					currentY++;
				}
			}
		}
	}

}
