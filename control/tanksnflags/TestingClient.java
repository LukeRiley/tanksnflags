package tanksnflags;
import java.io.IOException;
import java.net.Socket;

public class TestingClient {
	private static int port = 32768;
	private static String url = "192.168.0.22";
	
	public static void main(String[] args){
		try {
			runClient(url, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	private static void runClient(String addr, int port)throws IOException{
		System.out.println("starting client");
		Socket s = new Socket(addr, port);
		System.out.println("TNF CLIENT CONNECTED TO " + addr +": "+ port );
		new Slave(s).run();
	}
}

