package tanksnflags.tests;

import java.io.File;

import tanksnflags.game.Game;
import tanksnflags.ui.ImageLoader;

public class Main extends Thread {

	Game window;

	public Main() {
		window = new Game();
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			window.tick();
			window.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
