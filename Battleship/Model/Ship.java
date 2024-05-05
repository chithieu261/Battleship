//Danilo Malo-Molina
package model;

import java.util.ArrayList;

/**
 * Ship.java class which represents a single ship to be placed on a board
 * object.
 */
public class Ship {
	private int health;
	public ArrayList<int[]> locations;
	private int startingSize;
	private int[] start;
	private int[] end;
	private int verticality;

	/**
	 * Ship object constructor
	 * 
	 * @param size total size of ship
	 */
	public Ship(int size) {
		health = size;
		startingSize = size;
		locations = new ArrayList<int[]>();
	}

	/**
	 * Ship object constructor
	 * 
	 * @param row       root coordinate for ship to be placed
	 * @param col       root coordinate for ship to be placed
	 * @param direction for ship to go
	 * @param size      how far for ship to expand
	 */
	public Ship(int row, int col, int direction, int size) {
		locations = new ArrayList<int[]>();
		verticality = direction;
		if (direction == 0) {
			for (int i = 0; i < size; i++) {
				if (i == 0) {
					start = new int[] { row + i, col };
				} else if (i == size - 1) {
					end = new int[] { row + i, col };
				}
				locations.add(new int[] { row + i, col });
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (i == 0) {
					start = new int[] { row, col + i };
				} else if (i == size - 1) {
					end = new int[] { row, col + i };
				}
				locations.add(new int[] { row, col + i });
			}
		}
		health = locations.size();
	}

	/**
	 * gets the ship's current health
	 * 
	 * @return the ship's current health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Damages a ship
	 */
	public void damage() {
		health -= 1;
	}

	/**
	 * gets the ship's starting size
	 * 
	 * @return the ship's starting size
	 */
	public int getSize() {
		return startingSize;
	}

	public boolean isVertical() {
		return verticality == 1;
	}

	public boolean isStart(int y, int x) {
		return x == start[0] && y == start[1];
	}

	public boolean isEnd(int y, int x) {
		return x == end[0] && y == end[1];
	}
}
