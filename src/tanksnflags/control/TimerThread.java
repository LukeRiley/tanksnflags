package tanksnflags.control;

import tanksnflags.game.Game;
import tanksnflags.game.GameCanvas;

/**
 * The Timer thread is used to create a timing constant which updates the game
 * state. 
 * 
 * @author huntdani1
 *
 */
public class TimerThread extends Thread {
	private final Game game; // the game board
	private final int pause; // the amount of time between pulses
	private final GameCanvas display;

	public TimerThread(Game game, int pause, GameCanvas display) {
		this.game = game;
		this.pause = pause;
		this.display = display;
	}

	public void run() {
		while (true) { // loop for the games entirity
			try {
				Thread.sleep(pause);
				game.tick(); // clockTick() comes from the board class
									
				if (display != null) {
					display.repaint();
				}
			} catch (InterruptedException e) {
				// the thread should never be interrupted so this should never
				// happen
			}
		}
	}
}