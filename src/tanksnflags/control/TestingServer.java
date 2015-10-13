package tanksnflags.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tanksnflags.game.Game;
import tanksnflags.helpers.IsoLogic;

public class TestingServer {

	public static void main(String[] args) {

		int port = 32768;
		runServer(2, port/* ,gameClock, broadcastClock, game */);
	}

	private static void runServer(int nClients,
			int port/* , int gameClock, int broadcastClock, Board game */) {
		//TimerThread clk = new TimerThread(gameClock, game, null);

		System.out.println("TNF SERVER LISTENING ON PORT " + port);
		System.out.println("TNF SERVER AWAITING " + nClients + " CLIENTS");
		Game game = new Game(new IsoLogic(Math.toRadians(30), Math.toRadians(330), 500, 500));
		try {
			Master[] connections = new Master[nClients];

			ServerSocket ss = new ServerSocket(port);
			int playerID = 0;
			while (1 == 1) {
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());
				connections[--nClients] = new Master(playerID, s, game);
				connections[nClients].start();
				playerID++;
				if (nClients == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
				}
			}
		} catch (IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		}
	}

}
