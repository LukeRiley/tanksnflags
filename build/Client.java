package tanksnflags;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private int playerNum;
	private static int port;
	private static String ip;
	
	public Client(int playerNum){
		this.playerNum = playerNum;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("What is the ip address of the server you would like to connect to?");
		if(sc.hasNext()){
			this.ip = sc.next();
			System.out.println(ip);
		}
		
		System.out.println("What is the port number you want to connect to?");
		if(sc.hasNextInt()){
			this.port = sc.nextInt();
		}
		
		sc.close();
	}
	
	public void run() throws UnknownHostException, IOException{
		Scanner scan = new Scanner(System.in);
		while(true){
			Socket socket = new Socket("127.0.0.1", 9090);
			Scanner sc1 = new Scanner(socket.getInputStream());
		
		
			System.out.println("Enter any string");
			String number = "1";
			//if(scan.hasNext()){
				number = scan.next();
				System.out.println(number);
			PrintStream pstream = new PrintStream(socket.getOutputStream());
			pstream.println(number);
			//}
			

			String temp = sc1.next();
			System.out.println(temp);
		}
	}
	
	

	public static void main(String args[]) throws UnknownHostException, IOException{
		Scanner scan = new Scanner(System.in);
		while(true){
			Socket socket = new Socket("127.0.0.1", 9090);
			Scanner sc1 = new Scanner(socket.getInputStream());
		
		
			System.out.println("Enter any string");
			String number = "1";
			//if(scan.hasNext()){
				number = scan.next();
				System.out.println(number);
			PrintStream pstream = new PrintStream(socket.getOutputStream());
			pstream.println(number);
			//}
			

			String temp = sc1.next();
			System.out.println(temp);
		}
		
	}
}
