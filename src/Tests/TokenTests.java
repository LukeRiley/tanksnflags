package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import tanksnflags.helpers.Vector;
import tanksnflags.tokens.Door;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.Key;
import tanksnflags.tokens.MovingItem;
import tanksnflags.tokens.Tank;
import tanksnflags.tokens.Tile;
import tanksnflags.tokens.Wall;
import junit.framework.TestCase;
/**
 * Test for the Token Package
 * 
 * @author huntdani1
 *
 */
public class TokenTests {
	
	
	//Tank Tests
	@Test
	public void tankTestVertical(){
		Tank t = new Tank(new Vector(1,1), 95);
		assertTrue(t.vertical() == 29);
	}
	
	@Test
	public void tankTestUID(){
		Tank t = new Tank(new Vector(1,1), 95);
		assertTrue(t.uid() == 95);
	}
	
	//Tile tests
	@Test
	public void tileTestVertical(){
		Tile t = new Tile(new Vector(1,1));
		assertTrue(t.vertical() == 0);
	}
	
	@Test
	public void tileTestMove(){
		Tile t = new Tile(new Vector(1,1));
		t.moveVertical(3);
		assertTrue(t.vertical() == 3);
	}
	
	//Wall test
	@Test
	public void wallTestVertical(){
		Wall w = new Wall(new Vector(1,1));
		assertTrue(w.vertical() == 29);
	}
	
	//Item tests
	@Test
	public void itemTestContainsTrue(){
		Tank t1 = new Tank(new Vector(1,1), 0);
		Tank t2 = new Tank(new Vector(1,1), 1);
		assertTrue(t1.contains(t2));
	}
	
	@Test
	public void itemTestContainsFalse(){
		Tank t1 = new Tank(new Vector(47,47), 0);
		Tank t2 = new Tank(new Vector(1,1), 1);
		assertFalse(t1.contains(t2));
	}
	
	
}
