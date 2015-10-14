package tanksnflags.control;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import tanksnflags.game.Game;

/**
 * 
 * The master exchanges data with the slave, via a socket. It receives movement
 * instructions from the slave, and sends back the updated Game.
 * 
 * @author Daniel Hunt
 *
 */
public class Master extends Thread {
	private final int playerID;
	private final Socket socket; // the socket with the slave.
	private final Game game;
	private final int broadcastClock; // TODO not sure if this is necessary

	public Master(int playerID, Socket sock, Game game, int broadcastClock) {
		this.playerID = playerID;
		this.socket = sock;
		this.game = game;
		this.broadcastClock = broadcastClock;
	}

	public void run() {
		try {
			DataInputStream iStream = new DataInputStream(socket.getInputStream());
			DataOutputStream oStream = new DataOutputStream(socket.getOutputStream());
			oStream.writeInt(playerID);

			boolean exit = false;
			while (!exit) {
				try {
					if (iStream.available() != 0) {
						int btn = iStream.readInt(); // get the button pressed
														// from the user/slave
						switch (btn) {
						case 1:
							if (game.canMoveUp(game.tank(playerID))) {
								game.tank(playerID).moveUp();
							}
							break;
						case 2:
							if (game.canMoveDown(game.tank(playerID))) {
								game.tank(playerID).moveDown();
							}
							break;
						case 3:
							if (game.canMoveRight(game.tank(playerID))) {
								game.tank(playerID).moveRight();
							}
							break;
						case 4:
							if (game.canMoveLeft(game.tank(playerID))) {
								game.tank(playerID).moveLeft();
							}
							break;
						case 7:// drop an object
							//if (game.tank(playerID).)
						}
						break;
					}

					byte[] state = game.toByteArray();
					oStream.writeInt(state.length);
					oStream.write(state);
					oStream.flush();
					game.tick();
					Thread.sleep(broadcastClock);
				} catch (InterruptedException ex) {
				}
			}
			socket.close();
		} catch (IOException e) {
			System.err.println(playerID + " is disconnected!");
		}
	}

}
