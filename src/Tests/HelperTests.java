package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.tokens.Tank;

/**
 * JUnit tests for the helper classes.
 * 
 * @author huntdani1
 *
 */
public class HelperTests {
	
	//IsoLogic Tests
	@Test
	public void isoLogicTestIsoToScreen(){
		IsoLogic iL = new IsoLogic(Math.toRadians(30), Math.toRadians(330), 500, 500);
		//Vector v = new Vector(0,0);
		//System.out.println(iL.isoToScreen(0,0).getQ()+" "+ iL.isoToScreen(0,0).getT());
		assertTrue(iL.isoToScreen(0,0).getQ() == 500);
		assertTrue(iL.isoToScreen(0,0).getT() == 500);
	}
	
	@Test
	public void isoLogicTestIsoToScreen2(){
		IsoLogic iL = new IsoLogic(Math.toRadians(30), Math.toRadians(330), 500, 500);
		Tank t = new Tank(new Vector(0,0), 2);
		assertTrue(iL.isoToScreen(t).getQ() == 500);
		assertTrue(iL.isoToScreen(t).getT() == 500);
	}
	
	@Test 
	public void isoLogicTestRotateAxis(){
		IsoLogic iL = new IsoLogic(Math.toRadians(30), Math.toRadians(330), 500, 500);
		iL.rotateAxis();
		
	}
	
}
