package helpers;

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
	 *            the x position on the screen
	 * @param y
	 *            the y position on the screen
	 * @param oX
	 *            the x position of the bottom corner of the iso axis
	 * @param oY
	 *            the y position of the bottom corner of the iso axis
	 * @return
	 */
	public int[] computeGridPos(int x, int y, int oX, int oY) {
		int[] gridPos = new int[2];

		double yLength = (oY - y) / Math.sin(yAngle);
		double xLength = (oX - x) * Math.sin(xAngle);

		gridPos[0] = (int) (xLength / (xLength - xLength % this.xLength));
		gridPos[1] = (int) (yLength / (yLength - yLength % this.yLength));

		return gridPos;
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
