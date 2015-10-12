package tanksnflags.tests;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import tanksnflags.game.Game;
import tanksnflags.ui.ImageLoader;

public class Main extends Thread {

	Game window;

	public Main() {
		window = new Game();
		window.addKeyListener(new MainKeyAdapter());
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			window.tick();
			window.repaint();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}

	class MainKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == (e.VK_UP)) {
				if (window.canMoveUp(window.tank()))
					window.tank().moveUp();
			}
			if (e.getKeyCode() == (e.VK_DOWN)) {
				if (window.canMoveDown(window.tank()))
					window.tank().moveDown();
			}
			if (e.getKeyCode() == (e.VK_RIGHT)) {
				if (window.canMoveRight(window.tank()))
					window.tank().moveRight();
			}
			if (e.getKeyCode() == (e.VK_LEFT)) {
				if (window.canMoveLeft(window.tank()))
					window.tank().moveLeft();
			}
			if (e.getKeyCode() == e.VK_9) {
				window.rotate();
				window.repaint();
			}
		}
	}

}
