package Tests;

import org.junit.Test;

import tanksnflags.game.Game;
import tanksnflags.helpers.IsoLogic;
import junit.framework.TestCase;

/**
 * JUnit Tests for the Game class
 *
 * @author huntdani1
 *
 */
public class GameTests {

	@Test
	public void GameTest1(){
		IsoLogic iL = new IsoLogic(Math.toRadians(30), Math.toRadians(330), 500, 500);
		Game g = new Game(iL);
		assertTrue(g.itemList != null);

	}
}