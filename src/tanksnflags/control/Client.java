package tanksnflags.control;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * The client sends and recieves strings to and from the server. The strings it
 * sends are based on user input, such as movements, and the strings it recieves
 * are based on updates to the game, which it then parses and updates the local
 * copy of the board.
 */
public class Client {
	private static int playerNum = 65;
	private static int port;// The server port for the connection
	private static String ip;// The servers IP address.
	//private Game/Board game; // Local copy of the game which gets updated based of strings from the server

	public Client(int playerNum) {
		this.playerNum = playerNum;
		Scanner sc = new Scanner(System.in);

		System.out.println("What is the ip address of the server you would like to connect to?");
		if (sc.hasNext()) {
			this.ip = sc.next();
			System.out.println(ip);
		}

		System.out.println("What is the port number you want to connect to?");
		if (sc.hasNextInt()) {
			this.port = sc.nextInt();
		}

		sc.close();
	}

	public static void main(String args[]) throws UnknownHostException, IOException {
		Scanner scan = new Scanner(System.in);
		String number = "";

		while (true) {
			Socket socket = new Socket("127.0.0.1", 9090);
			Scanner sc1 = new Scanner(socket.getInputStream());

			System.out.println("Enter any string");

			number = scan.next();
			PrintStream pstream = new PrintStream(socket.getOutputStream());
			pstream.println(number + " " + playerNum);

			int recievedInt = sc1.nextInt();
			String recievedStr = sc1.next();
			System.out.println(recievedInt + " " + recievedStr);
		}

	}
}
