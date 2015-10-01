package applicationWindow;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This class creates a JFrame that holds all menu bars and window..
 * @author raymondgeuze
 *
 */
public class BoardFrame extends JFrame implements KeyListener{
	private BoardCanvas canvas;
	private JMenu gameMenu;
	private JMenuBar menuBar;
	private JMenuItem quitGame;
	private JMenu helpMenu;
	private JMenuItem help;
	private ImageIcon bombIcon = new ImageIcon(BoardCanvas.class.getResource("images/bomb.png"));

 
	public BoardFrame(){
		super("Tanks'n'Flags");
		askJoinOrCreate();
		createMenus();
		canvas = new BoardCanvas(this); // create canvas
		setBounds(300,0,0,0);
		setLayout(new BorderLayout()); // use border layout
		add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(true);
		setVisible(true); 
		
		//add listeners
		addKeyListener(this);
	}
	
	public void createMenus(){
		menuBar = new JMenuBar();
		
		helpMenu = new JMenu("Help");
		help = new JMenuItem("Help            H");
		helpMenu.add(help);
		menuBar.add(helpMenu);
		
		//add listener to help menu
		help.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {//this stuff may need be in canvas for JPanel stuff
				JOptionPane.showMessageDialog(null, "Give some sort of game discription");
			}
		});
		
		gameMenu = new JMenu("Game");
		quitGame = new JMenuItem("Quit Game       Q");
		gameMenu.add(quitGame);
		menuBar.add(gameMenu);

		//Responds to click. Closes window down (quits game)
		quitGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {//this stuff may need be in canvas for JPanel stuff
				System.exit(0); // closes window
			}
		});
		
		// put the menubar on the frame
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Asks the user if they would like to create a new game (on server) or join an existing game.
	 * They return value will be sent to the server and used to determine whether this client starts
	 * a new game or joins and existing game with other clients.
	 * @return r>0 && r<4 means create game against r number of people
	 * 			0 if join game
	 */
	public int askJoinOrCreate(){
		String[] possibleValues = { "Create Game", "Enter Game"};
		Object selectedValue = JOptionPane.showInputDialog(null,
				"Would you like to:", "Game Selection",
				JOptionPane.INFORMATION_MESSAGE, bombIcon,
				possibleValues, possibleValues[0]);

		//convert input from string to int
		if(selectedValue==null){
			System.exit(0); // closes window as they have exited or something like that
		}
		else if(selectedValue.equals("Create Game")){
			String[] numString = { "One", "Two", "Three"};
			 Object selectedString = JOptionPane.showInputDialog(null,
					"How many people would you like to play against:", "How Many Players",
					JOptionPane.INFORMATION_MESSAGE, bombIcon,
					numString, numString[0]);
			 switch((String) selectedString){
			 case ("One"):return 1;
			 case ("Two"):return 2;
			 case ("Three"):return 3;
			 }
		}
		return 0;	
	}
	
	/**
	 * Displays a pop up wih some sort of informative message to the user
	 * @param message - the message to display in the pop up
	 */
	public void showPopUp(String message){
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 72){ //shortcut to help
			helpMenu.setSelected(true);
			help.doClick();
		}
		else if(e.getKeyCode() == 81){
			gameMenu.setSelected(true); //to make it show blue first
			quitGame.doClick(); //shortcut is Q to quit game
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}