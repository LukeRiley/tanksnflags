package tanksnflags.control;

import tanksnflags.game.*;

/**
 * The Timer thread is used to create a timing constant which updates the game
 * state
 * 
 * @author Daniel Hunt
 *
 */
public class TimerThread extends Thread {
	private final Game Game; // the game Game 
	private final int pause; // the amount of time between pulses

	public TimerThread(Game Game, int pause) {
		this.Game = Game;
		this.pause = pause;
	}

	public void run() {
		while (true) { //loop for the games entirity 
			try {
				Thread.sleep(pause);
				Game.tick(); // clockTick() comes from the Game class dont know if necessary?? TODO
				Game.repaint();
			} catch (InterruptedException e) {
				//the thread should never be interrupted so this should never happen
			}
		}
	}
}