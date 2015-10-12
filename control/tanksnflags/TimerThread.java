package tanksnflags;


/**
 * The Timer thread is used to create a timing constant which updates the game state
 * 
 * @author Daniel Hunt
 *
 */
public class TimerThread extends Thread{
	private final Board board; // the game board 
	private final int pause; // the amount of time between pulses
	private final BoardFrame display; //TODO ?? not sure if we will need this
	
	public TimerThread(Board board, int pause, BoardFrame display){
		this.board = board;
		this.pause = pause;
		this.display = display;
	}
	
	public void run(){
		while(true){ //loop for the games entirity 
			try{
				Thread.sleep(pause);
				board.clockTick(); // clockTick() comes from the board class dont know if necessary?? TODO
				if(display != null){
					display.repaint();
				}
			}catch(InterruptedException e){
				//the thread should never be interrupted so this should never happen
			}
		}
	}
}