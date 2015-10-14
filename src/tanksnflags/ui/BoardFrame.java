package tanksnflags.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tanksnflags.game.Game;
import tanksnflags.game.GameCanvas;
import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.helpers.IsoLogic.Dir;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.MovingItem;
import tanksnflags.tokens.Tank;
import tanksnflags.tokens.Tile;
import tanksnflags.tokens.Wall;

/**
 * This class creates a JFrame that holds all menu bars and window..
 * @author raymondgeuze
 *
 */
public class BoardFrame extends JFrame implements KeyListener{
	private JMenu gameMenu;
	private JMenuBar menuBar;
	private JMenuItem quitGame;
	private JMenu helpMenu;
	private JMenuItem help;
	//private ImageIcon bombIcon = new ImageIcon(BoardCanvas.class.getResource("images/bomb.png"));

	//tank and items
	private Point AXIS_INT = new Point(560, 220);
	private int count = 0;
	private IsoLogic isoLogic = new IsoLogic(Math.toRadians(30), Math.toRadians(330), AXIS_INT.getX(), AXIS_INT.getY());	

 
	public BoardFrame(GameCanvas canvas){
		super("Tanks'n'Flags");
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}