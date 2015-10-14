package tanksnflags.control;

import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import tanksnflags.game.Game;

public class Main {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;

	public static void main(String[] args) {

		boolean server = false;
		int nClients = 0;
		String url = null;
		int gameClock = DEFAULT_CLK_PERIOD;
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = 32768;

		// UI STUFF HERE
		String[] possibleValues = { "Create Game", "Enter Game" };
		Object selectedValue = JOptionPane.showInputDialog(null, "Would you like to:", "Game Selection",
				JOptionPane.INFORMATION_MESSAGE, /* bombIcon */null, possibleValues, possibleValues[0]);

		// convert input from string to int
		if (selectedValue == null) {
			System.exit(0); // closes window as they have exited or something
							// like that
		} else if (selectedValue.equals("Create Game")) {
			server = true; // this is the server for the game
			String[] numString = { "One", "Two", "Three" };
			Object selectedString = JOptionPane.showInputDialog(null, "How many people would you like to play against:",
					"How Many Players", JOptionPane.INFORMATION_MESSAGE, /* bombIcon */null, numString, numString[0]);
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
			if (server) {
				Game game = new Game(null, -1);
				runServer(port, nClients, gameClock, broadcastClock, game);
			} else {
				runClient(url, port);
			}
		} catch (IOException ioe) {
			System.out.println("IO err: " + ioe.getMessage());
			ioe.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}

	private static void runClient(String addr, int port) throws IOException {
		Socket s = new Socket(addr, port);
		System.out.println("TNF CLIENT CONNECTED TO " + addr + ": " + port);
		new Slave(s).run();
	}

	private static void runServer(int port, int nClients, int gameClock, int broadcastClock, Game game) {
		// TimerThread clk = new TimerThread(gameClock, game, null);
		int playerId = 0;
		System.out.println("TNF SERVER LISTENING ON PORT " + port);
		System.out.println("TNF SERVER AWAITING " + nClients + " CLIENTS");
		try {
			Master[] connections = new Master[nClients];

			ServerSocket ss = new ServerSocket(port);
			while (1 == 1) {
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());
				game.registerTank(playerId);
				connections[--nClients] = new Master(playerId, s, game, 100);
				connections[nClients].start();
				if (nClients == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");

				}
				System.out.println(playerId);
			}
		} catch (IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		}
	}

}
