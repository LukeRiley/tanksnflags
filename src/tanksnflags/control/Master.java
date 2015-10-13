package tanksnflags.control;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import tanksnflags.game.Game;
import tanksnflags.helpers.IsoLogic;

/**
 * 
 * The master exchanges data with the slave, via a socket. It receives movement
 * instructions from the slave, and sends back the updated board.
 * 
 * @author Daniel Hunt
 *
 */
public class Master extends Thread {
	private final int playerID;
	private final Socket socket; // the socket with the slave.
	// private final Board game;
	// private final int broadcastClock; // TODO not sure if this is necessary
	private Game game;

	public Master(int playerID,
			Socket sock/* , Board game, int broadcastClock */) {
		this.playerID = playerID;
		this.socket = sock;
		this.game = new Game(new IsoLogic(Math.toRadians(30), Math.toRadians(330), 500, 500));
		// this.game = game;
		// this.broadcastClock = broadcastClock;
	}

	public void run() {
		try {
			DataInputStream iStream = new DataInputStream(socket.getInputStream());
			DataOutputStream oStream = new DataOutputStream(socket.getOutputStream());

			oStream.writeInt(playerID);
			// oStream.writeInt(game.width()); // TODO this is just sending the
			// data so slave can create a
			// oStream.writeInt(game.height());//TODO board/game which is the
			// same as the one in the master.
			// oStream.write(game.wallsToByteArray()); // TODO will need to
			// adjust for our own game

			boolean exit = false;
			while (!exit) {
				try {
					if (iStream.available() != 0) {
						int btn = iStream.readInt(); // get the button pressed
													// from the user/slave
						System.out.println("Button " + btn + " received from " + playerID);

						switch (btn) {
						case 1:
							game.tank().moveUp();
							break;
						case 2:
							game.tank().moveDown();
							break;
						case 3:
							game.tank().moveRight();
							break;
						case 4:
							game.tank().moveLeft();
							break;
						}
						game.tank().tick();

					}
					byte[] state = game.toByteArray();
					oStream.writeInt(state.length);
					oStream.write(state);
					oStream.flush();
					Thread.sleep(500);// broadcastClock);
				} catch (InterruptedException ex) {
				}
			}
			socket.close();
		} catch (IOException e) {
			System.err.println(playerID + " is disconnected!");
			// game.disconnectPlayer(playerID);
		}
	}

}
