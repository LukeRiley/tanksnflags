package game;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Wall extends Item {

	public Wall(double isoX, double isoY) {
		super(isoX, isoY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Item fromInputStream(DataInputStream din) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
