package tanksnflags.tokens;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;

public abstract class MovingItem extends Item {

	String dir = "null";
	double size = 46;
	int frames = 10;
	double moveRate = 40 / frames;
	int count = 0;
	int state = 0;

	public MovingItem(Vector pos, IsoLogic iL, Dir dir) {
		super(pos, iL, dir);
	}

	public void moveUp() {
		moveCommand("up");

	}

	public void moveDown() {
		moveCommand("down");

	}

	public void moveRight() {
		moveCommand("right");

	}

	public void moveLeft() {
		moveCommand("left");
	}

	public void moveCommand(String dir) {
		if (state == 1) {
			this.dir = dir;
			count = 0;
			state = 0;
		}
	}

	public void tick() {
		if (count == frames - 1) {
			moveRate = moveRate + size - moveRate * frames;
		}
		switch (dir) {
		case "up":
			pos = pos.add(new Vector(moveRate, 0));
			break;
		case "down":
			pos = pos.add(new Vector(-moveRate, 0));
			break;
		case "left":
			pos = pos.add(new Vector(0, -moveRate));
			break;
		case "right":
			pos = pos.add(new Vector(0, moveRate));
			break;
		}
		count++;
		if (count == frames) {
			moveRate = 40 / frames;
			dir = "null";
			count = 0;
			state = 1;
		}
	}

	@Override
	public abstract void draw(Graphics2D g2);

	@Override
	public abstract void toOutputStream(DataOutputStream dout) throws IOException;

	@Override
	public abstract Item fromInputStream(DataInputStream din) throws IOException;

}
