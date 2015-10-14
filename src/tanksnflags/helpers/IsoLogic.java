package tanksnflags.helpers;

import java.awt.Point;

import tanksnflags.tokens.Item;

/**
 * A class which helps with the isometric math. The axis are labeled u and v.
 * The u axis is the one closest to the y axis of the screen. The v axis is the
 * one furthest from it. The origin is taken as the bottom most point of the
 * isometric plane. There is a txt file named axisRepresentation with a diagram
 * of what the axis are like.
 * 
 * @author Haylem
 *
 */

public class IsoLogic {

	// store angle of both isometric axis
	// store length of x/y isometric axis 'size of square sizes'

	private double vAngle;// In radians
	private double uAngle;

	private double originX;
	private double originY;

	private Vector origin;

	private Vector uVector;// u unit vector in terms of x/y axis
	private Vector vVector;// v unit vector in terms of x/y axis

	boolean swap = true;

	public enum Dir {
		NORTH, SOUTH, EAST, WEST
	}

	public IsoLogic(double uAngle, double vAngle, double originX, double originY) {
		this.uAngle = uAngle;
		this.vAngle = vAngle;
		this.originX = originX;
		this.originY = originY;

		uVector = new Vector(Math.cos(uAngle), -Math.sin(uAngle));
		vVector = new Vector(Math.cos(vAngle), -Math.sin(vAngle));

		origin = new Vector(originX, originY);
		computeUnitVectors();
	}

	/**
	 * Returns left rotated direction of the one given.
	 * @param dir the direction you wish to rotate.
	 * @return the direction that is left of the one given.
	 */
	public Dir rotateLeft(Dir dir) {
		switch (dir) {
		case EAST:
			return Dir.NORTH;
		case NORTH:
			return Dir.WEST;
		case WEST:
			return Dir.SOUTH;
		default:
			return Dir.EAST;
		}
	}

	/**
	 * Change the angle of the axis. The rotation rotates left.
	 */
	public void rotateAxis() {
		if (swap) {
			uAngle += Math.toRadians(120);
			vAngle += Math.toRadians(60);
			swap = false;
		} else {
			uAngle += Math.toRadians(60);
			vAngle +=Math.toRadians(120);
			swap = true;
		}
		computeUnitVectors();
	}

	private void computeUnitVectors() {
		uVector = new Vector(Math.cos(uAngle), -Math.sin(uAngle));
		vVector = new Vector(Math.cos(getvAngle()), -Math.sin(getvAngle()));
	}

	/**
	 * Converts coordinates in the isometric axis into coordinates on the screen
	 * axis, (x/y). Read the doc on the class to see how the isometric axis are
	 * labeled.
	 * 
	 * @param u
	 *            The component along the u unit vector of the isometric axis
	 * @param v
	 *            The component along the v unit vector of the isometric axis
	 * @return an array of double. The first element is the x coordinate and the
	 *         second is the y coordinate.
	 */
	public Vector isoToScreen(double u, double v) {
		Vector screen = origin.add((vVector.scale(v)).add(uVector.scale(u)));
		screen = new Vector(Math.round(screen.getQ()), Math.round(screen.getT()));
		return screen;
	}

	public Vector isoToScreen(Vector v) {
		return isoToScreen(v.getQ(), v.getT());
	}

	public Vector isoToScreen(Item i) {
		return isoToScreen(i.pos());
	}

	/*
	 * This math doesn't work anymore . IGNORE
	 */
	private Vector screenToIso(double x, double y) {
		return new Vector(Math.round(originY + (x - originX) * Math.tan(uAngle / 2) - y), Math.round((x - originX) * Math.tan(uAngle / 2) + y - originY));
	}

	private Vector screenToIso(Vector v) {
		return screenToIso(v.getQ(), v.getT());
	}

	private Vector screenToIso(Item i) {
		return screenToIso(i.pos());
	}

	public double getvAngle() {
		return vAngle;
	}

}
