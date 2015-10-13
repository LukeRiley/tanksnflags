package tanksnflags.control;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import tanksnflags.game.Game;

/**
 * 
 * The master exchanges data with the slave, via a socket. It receives movement instructions from 
 * the slave, and sends back the updated Game.
 * 
 * @author Daniel Hunt
 *
 */
public class Master extends Thread{
	private final int playerID;
	private final Socket socket; // the socket with the slave.
	private final Game game;
	private final int broadcastClock; // TODO not sure if this is necessary
	
	
	public Master(int playerID, Socket sock, Game game, int broadcastClock){
		this.playerID = playerID;
		this.socket = sock;
		this.game = game;
		this.broadcastClock = broadcastClock;
	}
	
	public void run(){
		try{
			DataInputStream iStream = new DataInputStream(socket.getInputStream());
			DataOutputStream oStream = new DataOutputStream(socket.getOutputStream());
			
			oStream.writeInt(playerID);
			
			boolean exit = false;
			while(!exit){
				try{
					if(iStream.available() != 0){
						int btn = iStream.readInt(); // get the button pressed from the user/slave
						switch(btn){
						case 1:
							game.tank(playerID).moveUp();
							break;
						case 2:
							game.tank(playerID).moveDown();
							break;
						case 3: 
							game.tank(playerID).moveRight();
							break;
						case 4:
							game.tank(playerID).moveLeft();
							break;
						}
					}

					byte[] state = game.toByteArray();
					oStream.writeInt(state.length);
					oStream.write(state);
					oStream.flush();
					Thread.sleep(broadcastClock);
				}catch (InterruptedException ex){}
			}
			socket.close();
		}catch(IOException e){
			System.err.println(playerID + " is disconnected!");
		}
	}
	
}
