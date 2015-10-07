package tanksnflags.helpers;

public class Vector {

	private double q;
	private double t;

	public Vector(double q, double t) {
		super();
		this.q = q;
		this.t = t;
	}

	public Vector perp() {
		return new Vector(-t, q);
	}

	public Vector subtract(Vector v) {
		return new Vector(v.getQ() - q, v.getT() - t);
	}

	public Vector add(Vector v) {
		return new Vector(v.getQ() + q, v.getT() + t);
	}

	public double dot(Vector v) {
		return v.getQ() * q + v.getT() * t;
	}

	public Vector scale(double s) {
		return new Vector(q * s, t * s);
	}

	public double abs() {
		return Math.sqrt(q * q + t * t);
	}

	public double getQ() {
		return q;
	}

	public double getT() {
		return t;
	}

	public void setQ(double q) {
		this.q = q;
	}

	public void setT(double t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "Vector [q=" + q + ", t=" + t + "]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(q);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(t);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		if (Double.doubleToLongBits(q) != Double.doubleToLongBits(other.q))
			return false;
		if (Double.doubleToLongBits(t) != Double.doubleToLongBits(other.t))
			return false;
		return true;
	}

	public boolean equalsDelta(Object obj, double delta) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		if (other.q <q-delta || other.q > q+delta)
			return false;
		if (other.t < t-delta || other.t >t+delta)
			return false;
		return true;
	}

}
