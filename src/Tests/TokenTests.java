package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import tanksnflags.helpers.Vector;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.MovingItem;
import tanksnflags.tokens.Tank;
import junit.framework.TestCase;

public class TokenTests {
	
	
	//Tank Tests
	@Test
	public void itemTest1(){
		Vector v = new Vector(1, 1);
		Tank t = new Tank(new Vector(1,1), 95);
		assertTrue(t.vertical() == 29);
//		assertTrue(t.)
	}

}
