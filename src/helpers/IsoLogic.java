package helpers;

import java.awt.Point;

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

	public IsoLogic(double uAngle, double vAngle, double originX, double originY) {
		this.uAngle = uAngle;
		this.vAngle = vAngle;
		this.originX = originX;
		this.originY = originY;
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
	public double[] isoToScreen(double u, double v) {
		return new double[] { Math.cos(uAngle) * (u) + Math.cos(vAngle)*(v) + originX,originY - Math.sin(uAngle) * u - Math.sin(vAngle) * v };
	}

}
