package tanksnflags.control;

import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import tanksnflags.game.Game;

/**
 * The Main class sets up the game and lets the player choose whether they want
 * to start a new game or join an existing game.
 * 
 * @author huntdani1
 *
 */
public class Main {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 10;
	private static ImageIcon tagIcon = new ImageIcon("src/tanksnflags/ui/images/tag.png");

	public static void main(String[] args) {

		boolean server = false; // Whether is a client or not
		int nClients = 0; // Number of clients
		String url = null; // IP address of server
		int gameClock = DEFAULT_CLK_PERIOD;
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = 32768; // Port to connect to on Server

		// UI STUFF HERE done by Ray
		String[] possibleValues = { "Create Game", "Enter Game" };
		Object selectedValue = JOptionPane.showInputDialog(null, "Would you like to:", "Game Selection",
				JOptionPane.INFORMATION_MESSAGE, tagIcon, possibleValues, possibleValues[0]);

		// convert input from string to int
		if (selectedValue == null) {
			System.exit(0); // closes window as they have exited or something
							// like that
		} else if (selectedValue.equals("Create Game")) {
			server = true; // this is the server for the game
			String[] numString = { "One", "Two", "Three" };
			Object selectedString = JOptionPane.showInputDialog(null, "How many people would you like to play against:",
					"How Many Players", JOptionPane.INFORMATION_MESSAGE, tagIcon, numString, numString[0]);
			switch ((String) selectedString) {
			case ("One"):
				nClients = 1;
				break;
			case ("Two"):
				nClients = 2;
				break;
			case ("Three"):
				nClients = 3;
				break;
			}
		} else if (selectedValue.equals("Enter Game")) {
			url = JOptionPane.showInputDialog("URL/IP Address:");
			if (url.length() == 0) {
				url = null;
			}
		}

		try {
			if (server) { // if we are starting a new game
				Game game = new Game(null, -1); // create a new game
				runServer(port, nClients, gameClock, broadcastClock, game);
			} else { // if we are joining an existing game
				runClient(url, port);
			}
		} catch (IOException ioe) { // if fails
			System.out.println("IO err: " + ioe.getMessage());
			ioe.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}

	/**
	 * Creates a socket to the server, then creates and runs a new slave.
	 * 
	 * @param addr
	 *            The IP address of the Server
	 * @param port
	 *            The port number of the server
	 * @throws IOException
	 */
	private static void runClient(String addr, int port) throws IOException {
		Socket s = new Socket(addr, port);
		System.out.println("TNF CLIENT CONNECTED TO " + addr + ": " + port);
		new Slave(s).run();
	}

	/**
	 * Creates a server, and then an array of masters for each of the players.
	 * 
	 * @param port
	 *            Port number of the server
	 * @param nClients
	 *            The number of players expected
	 * @param gameClock
	 * @param broadcastClock
	 *            The time between broadcasts
	 * @param game
	 *            The game that is being played
	 */
	private static void runServer(int port, int nClients, int gameClock, int broadcastClock, Game game) {
		// TimerThread clk = new TimerThread(gameClock, game, null);
		int playerId = 0;
		System.out.println("TNF SERVER LISTENING ON PORT " + port);
		System.out.println("TNF SERVER AWAITING " + nClients + " CLIENTS");
		try {
			Master[] connections = new Master[nClients]; // Create an array of
															// masters, one for
															// each slave

			ServerSocket ss = new ServerSocket(port);
			while (1 == 1) {
				Socket s = ss.accept(); // accept connections from new players
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());
				game.registerTank(playerId); // create a new tank
				connections[--nClients] = new Master(playerId, s, game, broadcastClock); // create
																							// a
																							// new
																							// master
																							// for
																							// the
																							// player
				connections[nClients].start(); // start the master
				if (nClients == 0) { // when the expected number of players have
										// joined
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");

				}
				playerId++;
				System.out.println(playerId);
			}
		} catch (IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		}
	}

}
