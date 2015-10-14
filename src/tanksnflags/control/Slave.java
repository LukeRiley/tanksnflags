package tanksnflags.control;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import tanksnflags.game.Game;
import tanksnflags.game.GameCanvas;
import tanksnflags.helpers.IsoLogic;

/**
 * The slave has a local copy of the game which it updates based on the
 * information that it receives. It also has a key listener TODO AND A MOUSE
 * LISTENER???? through which it get input from the user and passes onto it's
 * master.
 * 
 * @author Daniel Hunt
 *
 */
public final class Slave extends Thread implements KeyListener {
	private Game game; // the game board
	private int playerID; // the players id, matches its master
	private int totalSent; // TODO is this needed??
	private DataInputStream iStream;
	private DataOutputStream oStream;
	private final Socket socket;
	JFrame frame = new JFrame();
	GameCanvas canvas;

	/**
	 * Construct a slave which connects to a master via the provided socket.
	 * 
	 * @param sock
	 *            Socket to create a slave for.
	 */
	public Slave(Socket sock) {
		IsoLogic iL = new IsoLogic(Math.toRadians(30), Math.toRadians(330), 500, 500);
		game = new Game(iL);
		canvas = new GameCanvas(game, iL);
		frame.setSize(new Dimension(500, 500));
		frame.setVisible(true);
		frame.setContentPane(canvas);
		this.socket = sock;
	}

	public void run() {
		try {
			this.oStream = new DataOutputStream(socket.getOutputStream());
			this.iStream = new DataInputStream(socket.getInputStream());

			this.playerID = iStream.readInt();
			// TODO DO WE NEED THIS?? varies based on the setup of the game
			int width = iStream.readInt();
			int height = iStream.readInt();
			int bitWidth = iStream.readInt();
			int bitSize = iStream.readInt();
			byte[] wallBytes = new byte[bitSize];
			iStream.read(wallBytes);
			System.out.println("Tanks and Flags client! Player: " + this.playerID);

			boolean exit = false;
			long totalReceived = 0;

			while (!exit) {
				int amount = iStream.readInt();
				byte[] data = new byte[amount];
				iStream.readFully(data);
				this.game.fromByteArray(data); // TODO game needs a from byte
												// array
				frame.repaint();
				totalReceived += amount;

				// TODO PACMAN PRINTS OUT SOME USEFUL INFO ABOUT DATA
				// TRANSFERRED HERE
				// USING THE RATE METHOD
			}
			socket.close();
		} catch (IOException e) {
			System.err.println("IO error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	public void keyPressed(KeyEvent e) { // TODO DROPPING BOMBS?? AND OTHER
											// THINGS
		try {
			int btn = e.getKeyCode();
			if (btn == KeyEvent.VK_UP) {
				oStream.writeInt(1);
				totalSent += 4;
			}

			else if (btn == KeyEvent.VK_LEFT || btn == KeyEvent.VK_KP_LEFT) {
				oStream.writeInt(4);
				totalSent += 4;
			}

			else if (btn == KeyEvent.VK_DOWN) {
				oStream.writeInt(2);
				totalSent += 4;
			}

			else if (btn == KeyEvent.VK_RIGHT || btn == KeyEvent.VK_KP_RIGHT) {
				oStream.writeInt(3);
				totalSent += 4;
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
