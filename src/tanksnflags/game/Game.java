package tanksnflags.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tanksnflags.helpers.IsoLogic;
import tanksnflags.helpers.Vector;
import tanksnflags.tokens.Item;
import tanksnflags.tokens.Tank;
import tanksnflags.tokens.Wall;

public class Game {

	private IsoLogic isoLogic;
	private AList[][] itemGrid;// array representation of items in game

	private int size = 10;// size of itemGrid

	public Game(IsoLogic isoLogic) {
		itemGrid = new AList[size][size];
	}
	
	

}

/*
 * Used as a 'bucket' for the itemGrid
 */

class AList implements Iterable<Item> {
	public List<Item> list;

	public AList(List<Item> l) {
		list = l;
	}

	public void add(Item toAdd) {
		list.add(toAdd);
	}

	public Item get(int index) {
		return list.get(index);
	}

	@Override
	public Iterator<Item> iterator() {
		return list.iterator();
	}
}
