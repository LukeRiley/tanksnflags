package applicationWindow;

//import BoardCanvas;

import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import Model.Bomb;
import Model.Item;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BoardCanvas extends JPanel implements MouseMotionListener,MouseListener{

	int width;
	int height;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //for getting screen size

	private boolean pressed;
	private int originalX;
	private int originalY;
	private boolean correctItem; //flags whether the selected item has hovered over correct item in game
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private Item selected;
	private BoardFrame frame; //the frame that holds this canvas

	//All the Items
	private Bomb bomb1;
	private Bomb bomb2;

	//images
	private BufferedImage backgroundImage;
	private int xPosInventory = 60; //the x postition items should be placed at in the inventory box
	private int yPosInventory = 500; //the y position items should be place at
	private int maxXposInventory = 240; //max x pos the items can be placed
	private int sizePlusSpace = 60; //amount of space to next spot to place each item
	private ArrayList<Item> itemsInPlay = new ArrayList<Item>();

	/**
	 * creates a canvas
	 * @param frame - the frame which holds this canvas
	 */
	public BoardCanvas(BoardFrame frame){
		this.frame = frame;
		setLayout(null);
		this.width = screenSize.width; //easy to access screen size
		this.height = screenSize.height;
		createInventory();

		//add listeners
		addMouseMotionListener(this);
		addMouseListener(this);

		try {
			backgroundImage = ImageIO.read(new File("src/applicationWindow/images/background.png"));		    
		} catch (IOException e) {
		}

		//just for testing
		JButton button = new JButton("Add Bomb Test");
		button.setBounds(0,0,100,100);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				addToInventory(new Bomb(xPosInventory,yPosInventory,"north"));
				repaint();
			}
		});
		add(button);
	}

	/**
	 * Creates each item in the game and adds them to the JPanel and the inventory arraylist
	 */
	public void createInventory(){
		//first item
//		bomb1 = new Bomb(60,500,"north");
//		bomb2 = new Bomb(120,500,"north");
//		inventory.add(bomb1);
//		inventory.add(bomb2);
	}

	/**
	 * Takes an item and adds it to the inventory. Does the positioning in this method
	 */
	public void addToInventory(Item item){
		if(inventory.size()<8){//checks if any room left in the inventory
			inventory.add(item);
			if(xPosInventory<=maxXposInventory){
				item.setX(xPosInventory); //sets x to the spot the next item should fit
				xPosInventory+=sizePlusSpace; //moves accross space of one item
				//item.setY(yPosInventory);
			}else{
				xPosInventory = 60; //sets item back to left side of inventory box
				item.setX(xPosInventory);
				xPosInventory+=sizePlusSpace;
				yPosInventory+=sizePlusSpace;
			}
			item.setY(yPosInventory);
		}
		else{
			frame.showPopUp("Sorry your inventory is full, drag an item onto the "
					+ "playing field to drop it!");
		}
	}

	/**
	 * Removes the item from the array list of inventory items,moves the last item in the arraylist to the
	 * the item was removed from. rearranges arraylist.
	 * @param item - item to be removed
	 */
	public void removeFromInventory(Item item,int oldX,int oldY){
		int indexOfItem = inventory.indexOf(item);
		Item it;
		int moveToX;
		
		for(int i=indexOfItem+1;i<inventory.size();i++){ //move each item back one position
			it = inventory.get(i);
			moveToX = it.getX()-sizePlusSpace;
			if(moveToX<60 && it!=inventory.get(0)){
				moveToX=maxXposInventory;
				it.setY(500);
			}
			it.setX(moveToX);
		}
		if(inventory.size()>0){ //as long as not the last item move it
		xPosInventory-=sizePlusSpace;
		}
		if(xPosInventory<60){ //move it up a line if its last one on its line
			xPosInventory = maxXposInventory;
			yPosInventory = 500;
		}
		inventory.remove(item);
		itemsInPlay.add(item);



		//		Item lastInArray = inventory.get(inventory.size()-1);
		//		if(item!=lastInArray){
		//			lastInArray.setX(oldX);
		//			lastInArray.setY(oldY);
		//			inventory.set(inventory.indexOf(item),lastInArray); //moves last item to position in array of removed item
		//		}
		//		xPosInventory-=sizePlusSpace;
		//		inventory.remove(item);
		//		itemsInPlay.add(item);
	}


	@Override
	public void paint(Graphics g){
		g.drawImage(backgroundImage, 0, 0, null);
		for(Item i:inventory){
			i.draw(g);
		}
		for(Item i:itemsInPlay){
			i.draw(g);
		}
	}

	/**
	 * Gets the preferred size of the canvas
	 * @return The preferred size of the canvas
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width/2,height-150);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * If the click is on an item, that i item is selected and moved around the
	 *  canvas with the drag method.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		for(Item i: inventory){
			if(e.getX() > i.getX() && e.getX() < i.getX()+50){
				if(e.getY() > i.getY() && e.getY() < i.getY()+50){
					pressed = true;
					originalX = i.getX();
					originalY = i.getY();
					selected = i;
				}
			}	
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!correctItem && pressed){
			selected.setX(e.getX()-25); // was setting to originalX. should be if cant put in taht spot
			selected.setY(e.getY()-25);
			removeFromInventory(selected,originalX,originalY);
			repaint();
		}
		pressed = false;
		correctItem = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Moves the item that has been clicked on around the canvas.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressed){
			selected.setX(e.getX()-(25));
			selected.setY(e.getY()-(25));
			repaint();
		}
		//do comparison, somehow show if is correct or not
		//set flag for if correct
		//when released, if correct then move to that spot? if not then move back to original
		//correctItem
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//	@Override
	//	public void paint(Graphics g){
	//		g.setColor(Color.BLACK);
	//		g.fillRect(0, 0, getWidth(), getHeight());
	//	}
	//	
	//	@Override
	//	protected void paintComponent(Graphics g){
	//		super.paintComponent(g);
	//	}
}
