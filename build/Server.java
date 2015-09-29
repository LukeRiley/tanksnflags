package tanksnflags;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	private static int port;
	
	public Server(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Which port would you like to run the server from?");
		if(sc.hasNextInt()){
			this.port = sc.nextInt();
		}	
		sc.close();
	}

	public static void main(String args[]) throws IOException{
		Server s = new Server();
		s.run();
		
	}
	
	public void run() throws IOException{
		System.out.println("Server running");
		String recievedStr = "";
		int recievedInt = 0;
		ServerSocket servSocket = new ServerSocket(9090);
		while(true){
			Socket socket = servSocket.accept();
			Scanner scan = new Scanner(socket.getInputStream());
			if(scan.hasNext()){
				recievedStr = scan.next();
				recievedInt = scan.nextInt();
				System.out.println("Recieved message \"" + recievedStr+ " "+ recievedInt +"\"\n" );
				//number = number*2;
				PrintStream pstream = new PrintStream(socket.getOutputStream());
				pstream.println(recievedInt + " " + recievedStr);
			}
		}
	}
	
}
