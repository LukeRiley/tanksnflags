package tanksnflags.control;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import tanksnflags.game.Game;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.ui.BoardFrame;
import tanksnflags.ui.GameCanvas;

/**
 * The slave has a local copy of the game which it updates based on the
 * information that it receives. It also has a key listener through which it get
 * input from the user and passes onto it's master.
 * 
 * @author huntdani1 , 300311206 raynerhayl
 *
 */
public final class Slave extends Thread implements KeyListener {
	private Game game; // the game board
	private int playerID; // the players id, matches its master
	private DataInputStream iStream; // input stream from the master
	private DataOutputStream oStream; // output stream to the master
	private final Socket socket;// socket between the slave and master
	private BoardFrame frame; // Frame that holds the game
	private GameCanvas canvas;// canvas that game is drawn on

	/**
	 * Construct a slave which connects to a master via the provided socket.
	 * 
	 * @param sock
	 *            Socket to create a slave for.
	 */
	public Slave(Socket sock) {
		this.socket = sock;
	}

	/**
	 * Run the slave
	 */
	public void run() {
		try {
			this.oStream = new DataOutputStream(socket.getOutputStream());
			this.iStream = new DataInputStream(socket.getInputStream());

			this.playerID = iStream.readInt(); // get the player ID from the
												// master
			System.out.println("Tanks and Flags client! Player: " + this.playerID);
			IsoLogic iL = new IsoLogic(Math.toRadians(30), Math.toRadians(330), 500, 320);
			// create new game, canvas and frame
			game = new Game(iL, playerID);
			canvas = new GameCanvas(game, iL, playerID);
			frame = new BoardFrame(canvas);
			frame.addKeyListener(this);
			boolean exit = false;

			while (!exit) {
				int amount = iStream.readInt(); // get the length of the
												// incoming data array from the
												// master
				byte[] data = new byte[amount]; // create a new array to put
												// game info into
				iStream.readFully(data); // read the output from the socket
				this.game.fromByteArray(data); // update a game from the byte
												// array

				game.tick();
				frame.repaint(); // redraw the updated game

			}
			socket.close();
		} catch (IOException e) {
			System.err.println("IO error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	/**
	 * If one of the game play keys is pressed (up, down, left, right) the slave
	 * sends a number to the master, which updates the game board accordingly
	 */
	public void keyPressed(KeyEvent e) {
		try {
			// what follows is nasty
			int btn = e.getKeyCode();
			if (btn == KeyEvent.VK_UP) {
				switch (canvas.dir()) {
				case EAST:
					oStream.writeInt(1);
					break;
				case NORTH:
					oStream.writeInt(3);
					break;
				case WEST:
					oStream.writeInt(2);
					break;
				case SOUTH:
					oStream.writeInt(4);
					break;
				}
			}

			else if (btn == KeyEvent.VK_LEFT) {
				switch (canvas.dir()) {
				case EAST:
					oStream.writeInt(4);
					break;
				case NORTH:
					oStream.writeInt(1);
					break;
				case WEST:
					oStream.writeInt(3);
					break;
				case SOUTH:
					oStream.writeInt(2);
					break;
				}
			}

			else if (btn == KeyEvent.VK_DOWN) {
				switch (canvas.dir()) {
				case EAST:
					oStream.writeInt(2);
					break;
				case NORTH:
					oStream.writeInt(4);
					break;
				case WEST:
					oStream.writeInt(1);
					break;
				case SOUTH:
					oStream.writeInt(3);
					break;
				}
			}

			else if (btn == KeyEvent.VK_RIGHT) {
				switch (canvas.dir()) {
				case EAST:
					oStream.writeInt(3);
					break;
				case NORTH:
					oStream.writeInt(2);
					break;
				case WEST:
					oStream.writeInt(4);
					break;
				case SOUTH:
					oStream.writeInt(1);
					break;
				}
			} else if (btn == KeyEvent.VK_9) {
				canvas.rotate();
			} else if (btn == KeyEvent.VK_D) {
				oStream.writeInt(5);
			}

			oStream.flush();
		} catch (IOException er) {
			// Do not need to do anything here as there was just an error
			// sending move to the master.
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
