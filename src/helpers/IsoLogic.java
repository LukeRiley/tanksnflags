package helpers;

import java.awt.Point;

/**
 * A class which helps with the isometric math.
 * 
 * @author Haylem
 *
 */
public class IsoLogic {

	// store angle of both isometric axis
	// store length of x/y isometric axis 'size of square sizes'

	private double yAngle;// In radians
	private double xAngle;

	private double yLength;
	private double xLength;

	public IsoLogic(double yAngle, double xAngle, double yLength, double xLength) {
		this.yAngle = yAngle;
		this.xAngle = xAngle;
		this.yLength = yLength;
		this.xLength = xLength;
	}

	/**
	 * Gives the grid position of a point clicked on the screen for the stored
	 * isometric axis.
	 * 
	 * @param x
	 *            - the x position on the screen
	 * @param y
	 *            - the y position on the screen
	 * @param oX
	 *            - the x position of the bottom corner of the iso axis
	 * @param oY
	 *            - the y position of the bottom corner of the iso axis
	 * @return
	 */
	public int[] computeGridPos(int x, int y, int oX, int oY) {
		double[] axisPos = computeAxisPos(x, y, oX, oY);
		return new int[] { (int) (axisPos[0] / (axisPos[0] - axisPos[0] % this.xLength)), (int) (axisPos[0] / (axisPos[0] - axisPos[0] % this.xLength)) };
	}

	/**
	 * Gives the position on the isometric axis in terms of the two axis.
	 * 
	 * @param x
	 *            - The horizontal position on the x/y axis (the screen)
	 * @param y
	 *            - The vertical position on the x/y axis (the screen)
	 * @param oX
	 *            - The x coordinate of the isometric axis origin (the bottom
	 *            corner)
	 * @param oY
	 *            - The y coordinate of the isometric axis origin (the bottom
	 *            corner)
	 * @return an array of doubles containing the coordinates of this x/y point
	 *         on the isometric axis. The first value gives the coordinate on
	 *         the right hand side axis of the isometric axis (furtherest to the
	 *         right) and the second value gives the coordinate on the axis
	 *         furtherest to the left.
	 */
	private double[] computeAxisPos(int x, int y, int oX, int oY) {
		return new double[] { xLength = (oX - x) * Math.sin(xAngle), (oY - y) / Math.sin(yAngle) };
	}

	/**
	 * Gives the component of this point on the U axis. The U axis is the
	 * isometric axis which is CLOSEST to the Y axis of the screen. The x/y
	 * coordinates of the isometric axis origin must be given. The origin is
	 * defined as the bottom corner of our isometric square plane.
	 * 
	 * @param x
	 *            - The x coordinate of the point.
	 * @param y
	 *            - The y coordinate of the point.
	 * @param oX
	 *            - The x coordinate of the isometric axis origin at the time of
	 *            this calculation.
	 * @param oY
	 *            - The y coordinate of the isometric axis origin at the time of
	 *            this calculation.
	 * @return The value of this point along the U axis.
	 */
	public double isometricAxisU(int x, int y, int oX, int oY) {
		return computeAxisPos(x, y, oX, oY)[1];
	}

	/**
	 * Point parameter alternative to the method
	 * {@link #isometricAxisU(int, int, int, int)}.
	 * 
	 * @param p
	 *            - The point in terms of the x/y axis of the screen.
	 * @param o
	 *            - The point of the isometric axis origin on the screen.
	 * @return
	 */
	public double isometricAxisU(Point p, Point o) {
		return computeAxisPos(p.x, p.y, o.x, o.y)[1];
	}

	/**
	 * Gives the component of this point on the V axis. The V axis is the
	 * isometric axis which is FURTHEST to the Y axis of the screen. The x/y
	 * coordinates of the isometric axis origin must be given. The origin is
	 * defined as the bottom corner of our isometric square plane.
	 * 
	 * @param x
	 *            - The x coordinate of the point.
	 * @param y
	 *            - The y coordinate of the point.
	 * @param oX
	 *            - The x coordinate of the isometric axis origin at the time of
	 *            this calculation.
	 * @param oY
	 *            - The y coordinate of the isometric axis origin at the time of
	 *            this calculation.
	 * @return The value of this point along the V axis.
	 */
	public double getV(int x, int y, int oX, int oY) {
		return computeAxisPos(x, y, oX, oY)[0];
	}

	/**
	 * Point parameter alternative to the method
	 * {@link #isometricAxisU(int, int, int, int)}.
	 * 
	 * @param p
	 *            - The point in terms of the x/y axis of the screen.
	 * @param o
	 *            - The point of the isometric axis origin on the screen.
	 * @return
	 */
	public double getV(Point p, Point o) {
		return computeAxisPos(p.x, p.y, o.x, o.y)[0];
	}

	public void setyAngle(double yAngle) {
		this.yAngle = yAngle;
	}

	public void setxAngle(double xAngle) {
		this.xAngle = xAngle;
	}

	public void setyLength(double yLength) {
		this.yLength = yLength;
	}

	public void setxLength(double xLength) {
		this.xLength = xLength;
	}

}
