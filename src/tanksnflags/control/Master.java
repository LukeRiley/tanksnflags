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
 * @author huntdani1
 *
 */
public class Master extends Thread {
	private final int playerID; 
	private final Socket socket; // the socket with the slave.
	private final Game game; // Local version of the game which is displayed
	private final int broadcastClock; // Time between the refreshing

	public Master(int playerID, Socket sock, Game game, int broadcastClock) {
		this.playerID = playerID;
		this.socket = sock;
		this.game = game;
		this.broadcastClock = broadcastClock;
	}

	public void run() {
		try {
			DataInputStream iStream = new DataInputStream(socket.getInputStream()); // Create a data input stream for coms with Slave
			DataOutputStream oStream = new DataOutputStream(socket.getOutputStream());//and also and output stream
			oStream.writeInt(playerID); // tell the slave its PlayerID

			boolean exit = false;
			while (!exit) {
				try {
					if (iStream.available() != 0) {
						int btn = iStream.readInt(); // get the button pressed
														// from the user/slave
						switch (btn) { // alter the game based upon the info from the slave
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
						case 5:
							if(game.tank(playerID).getNumKeys()>0){
								game.dropItem(game.tank(playerID));
							}
						}
					}

					byte[] state = game.toByteArray(); // format the game as a byte array
					oStream.writeInt(state.length); // send the length of byte array to the slave
					oStream.write(state); // send the game to the slave
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
