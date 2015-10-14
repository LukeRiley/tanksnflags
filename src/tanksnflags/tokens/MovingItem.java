package tanksnflags.tokens;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.helpers.Vector;

/**
 * A moving item is one which can move in any direction in the game.
 * @author Haylem
 *
 */
public abstract class MovingItem extends Item {

	Dir dir = null;

	double size = 46;
	// variables for animation
	protected int frames = 1;
	protected int moveIncrement = 46;
	protected int moveRate = moveIncrement / frames;
	int count = 0;
	int state = 1;

	public MovingItem(Vector pos) {
		super(pos);
	}

	public Dir dir() {
		return dir;
	}

	public void moveUp() {
		moveCommand(Dir.NORTH);

	}

	public void moveDown() {
		moveCommand(Dir.SOUTH);

	}

	public void moveRight() {
		moveCommand(Dir.EAST);

	}

	public void moveLeft() {
		moveCommand(Dir.WEST);
	}

	public void moveCommand(Dir dir) {
		if (state == 1) {
			this.dir = dir;
			count = 0;
			state = 0;
		}
	}

	public void tick() {
		if (count == frames - 1) {
			moveRate = moveIncrement - (int) moveRate * frames + moveRate;
		}
		renderTick(moveRate);
		count++;
		if (count == frames) {
			moveRate = moveIncrement / frames;
			dir = null;
			count = 0;
			state = 1;
		}
	}

	/*
	 * The render tick method was used for animation of movement however not
	 * completely working.
	 */
	protected abstract void renderTick(int moveRate);

}
