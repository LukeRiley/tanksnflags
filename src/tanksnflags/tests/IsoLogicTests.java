package tanksnflags.tests;

import org.junit.*;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class IsoLogicTests {

	Random rnd = new Random();

	@Test
	public void testAxisConversion() {

		IsoLogic iL = new IsoLogic(Math.toRadians(60), Math.toRadians(60), 0, 500);

		for (int i = 0; i < 1000; i++) {
			int u = rnd.nextInt(10000);
			int v = rnd.nextInt(10000);
			Vector screenV = iL.isoToScreen(u, v);
			Vector isoV = iL.screenToIso(screenV.getQ(), screenV.getT());
			assertEquals(u, isoV.getQ(), 1);
		}

	}

}
