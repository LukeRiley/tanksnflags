package tanksnflags.tests;

import org.junit.*;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IsoLogicTests {

	@Test
	public void testAxisConversion() {

		IsoLogic iL = new IsoLogic(Math.toRadians(60), Math.toRadians(60), 0, 500);
		
/*		Vector screenVOne = iL.isoToScreen(1, 0);
		System.out.println(screenVOne);
		Vector isoScreenV = new Vector(screenVOne.getQ() - 500, screenVOne.getT() - 900);
		System.out.println(isoScreenV.abs());*/

		int u = 30;
		int v = 26;
		Vector screenV = iL.isoToScreen(u, v);
		Vector isoV = iL.screenToIso(screenV.getQ(), screenV.getT());
		//System.out.println(screenV);
		System.out.println(isoV);
	}

}
