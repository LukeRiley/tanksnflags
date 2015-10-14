package tanksnflags.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This class creates a JFrame that holds all menu bars and canvas.
 * Keylistener, so responds to keys for shortcuts to a help pop up and quiting the game
 * @author raymondgeuze
 *
 */
public class BoardFrame extends JFrame implements KeyListener{
	private JMenu gameMenu; //game menu which holds quit menuitem
	private JMenuBar menuBar; //menubar to hold the different menus
	private JMenuItem quitGame; //menu item to quit
	private JMenu helpMenu; //help menu which hold help menuItem
	private JMenuItem help; //menu item for displaying help
	private static ImageIcon tagIcon = new ImageIcon("src/tanksnflags/ui/images/tag.png");
	private GameCanvas canvas; //the canvas everything is drawn to
 
	public BoardFrame(GameCanvas canvas){
		super("Tanks'n'Flags");
		this.canvas = canvas;
		createMenus();
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
				displayHelp();
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
	 * Displays a help pop up exaplining the game
	 */
	public void displayHelp(){
		JOptionPane.showMessageDialog(null, "A simple game of tag, use the directional keys to keep away from the blue"
				+ " block.\nIf you are the blue block you need to hit another players block.\n"
				+ "Use the 9 button to rotate the field.","Help",0,tagIcon);
	}
	
	
	/**
	 * Displays a pop up with some sort of informative message to the user
	 * @param message - the message to display in the pop up
	 */
	public void showPopUp(String message){
		JOptionPane.showMessageDialog(null, message,"Message",0,tagIcon);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_H){
			displayHelp();
		}
		if(e.getKeyCode() == KeyEvent.VK_Q){
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}