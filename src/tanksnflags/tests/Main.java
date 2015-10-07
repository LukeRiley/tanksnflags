package tanksnflags.tests;

public class Main extends Thread {

	Window window;

	public Main() {
		window = new Window();
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			window.tick();
			window.repaint();
			window.rotate();
			try {
				Thread.sleep(500);
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