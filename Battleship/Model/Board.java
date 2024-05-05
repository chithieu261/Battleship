package model;

import java.util.ArrayList;
import java.util.List;
//import javafx.beans.property.SimpleObjectProperty;

/**
 * Board.java class that handles all battleship board functions.
 */
public class Board {
	private Object[][] theBoard; // Main board object
	private Object[][] hiddenBoard;
	private int size; // Board size
	private int shipsLeft;
	private int totalBoardHealth; // Aggregate health of all ships on board
	private List<Ship> shipList = new ArrayList<>();
	//private final SimpleObjectProperty<GameResult> gameResult = new SimpleObjectProperty<>(null);

	public enum GameResult {
		WIN, LOSE
	}

	//public SimpleObjectProperty<GameResult> gameResultProperty() {
	//	return gameResult;
	//}

	/**
	 * Shot result enum types.
	 */
	public enum ShotResult {
		HIT, MISS, SUNK, REPEAT_ATTEMPT, GAME_WON, INVALID;
	}

	/**
	 * Board object constructor
	 * 
	 * @param size of board
	 */
	public Board(int size) {
		this.size = size;
		theBoard = new Object[size][size];
		hiddenBoard = new Object[size][size];
		shipsLeft = 0;
		totalBoardHealth = 0;
	}

	/**
	 * Attempts a new shot on the board and returns one of the resulting ShotResult
	 * enum types
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return ShotResult enum
	 */
	public ShotResult shotAttempt(int x, int y) {
		// Checks to make sure shot attempt is valid
		if (x >= theBoard[0].length || y >= theBoard.length || x < 0 || y < 0)
			return ShotResult.INVALID;
		// Checks to see if we've hit a ship
		if (theBoard[y][x] instanceof Ship) {
			// Damages ship and reduces board health
			((Ship) theBoard[y][x]).damage();
			this.totalBoardHealth--;
			if (this.totalBoardHealth == 0) {
				for (int[] location : ((Ship) hiddenBoard[y][x]).locations)
					theBoard[location[1]][location[0]] = ShotResult.SUNK;
				return ShotResult.GAME_WON;
			}
			if (((Ship) theBoard[y][x]).getHealth() == 0) {
				// The ship is now fully sunk so return SUNK
				shipsLeft--;
				for (int[] location : ((Ship) hiddenBoard[y][x]).locations)
					theBoard[location[1]][location[0]] = ShotResult.SUNK;
				return ShotResult.SUNK;
			}
			// The ship did not sink so this is just a hit.
			theBoard[y][x] = ShotResult.HIT;
			return ShotResult.HIT;
		}

		// Handles a miss.
		if (theBoard[y][x] == null) {
			theBoard[y][x] = ShotResult.MISS;
			return ShotResult.MISS;
		}

		// The spot is neither null or a ship at this point so
		// it has to be either a HIT or a MISS, meaning that its a repeat
		return ShotResult.REPEAT_ATTEMPT;

	}

	/**
	 * Adds a given ship to the board.
	 * 
	 * @param ship to be added. The ship object will contain the needed coordinates.
	 * @return true if ship was added, false if ship cannot be added
	 */
	public boolean addShipToBoard(Ship ship) {
		for (int[] location : ship.locations) {
			int x = location[0];
			int y = location[1];
			if (x >= theBoard[0].length || y >= theBoard.length || theBoard[y][x] != null)
				return false;
		}
		for (int[] location : ship.locations) {
			int x = location[0];
			int y = location[1];
			this.totalBoardHealth++;
			theBoard[y][x] = ship;
			hiddenBoard[y][x] = ship;
		}
		shipsLeft++;
		shipList.add(ship);
		return true;
	}

	/**
	 * Returns the dimension of the square board
	 * 
	 * @return int
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns the board.
	 * 
	 * @return Object[][]
	 */
	public Object[][] getBoard() {
		return theBoard;
	}

	/**
	 * Returns the total health of remaining ships on the board.
	 * 
	 * @return int of board health
	 */
	public int getBoardHealth() {
		return totalBoardHealth;
	}

	/**
	 * Returns the number of remaining ships on the board.
	 * 
	 * @return int of ships left
	 */
	public int getShipsLeft() {
		return shipsLeft;
	}

	public List<Ship> getShipList() {
		return shipList;
	}

	/**
	 * This returns an obfuscated object of a board at x, y
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return object at location on board
	 */
	public Object getHiddenCoord(int x, int y) {
		// basic error checking
		if ((x > size - 1 || x < 0) || (y > size - 1 || y < 0))
			return ShotResult.INVALID;
		if (theBoard[y][x] instanceof Ship)
			return null;
		return theBoard[y][x];
	}

	/**
	 * This toString includes the ships in the final string output, which should be
	 * only visible to the current player.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return String representation of the coordinate.
	 */
	public String playerToStringCoord(int x, int y) {
		if (theBoard[y][x] == Board.ShotResult.HIT) {
			if (((Ship) hiddenBoard[y][x]).isVertical()) {
				if (((Ship) hiddenBoard[y][x]).isStart(y, x)) {
					return "HVA";
				} else if (((Ship) hiddenBoard[y][x]).isEnd(y, x)) {
					return "HVC";
				}
				return "HVB";
			} else if (!((Ship) hiddenBoard[y][x]).isVertical()) {
				if (((Ship) hiddenBoard[y][x]).isStart(y, x)) {
					return "HHA";
				} else if (((Ship) hiddenBoard[y][x]).isEnd(y, x)) {
					return "HHC";
				}
				return "HHB";
			}
		} else if (theBoard[y][x] == Board.ShotResult.MISS) {
			return "M";
		} else if (theBoard[y][x] == Board.ShotResult.SUNK) {
			if (((Ship) hiddenBoard[y][x]).isVertical()) {
				if (((Ship) hiddenBoard[y][x]).isStart(y, x)) {
					return "SVA";
				} else if (((Ship) hiddenBoard[y][x]).isEnd(y, x)) {
					return "SVC";
				}
				return "SVB";

			} else if (!((Ship) hiddenBoard[y][x]).isVertical()) {
				if (((Ship) hiddenBoard[y][x]).isStart(y, x)) {
					return "SHA";
				} else if (((Ship) hiddenBoard[y][x]).isEnd(y, x)) {
					return "SHC";
				}
				return "SHB";
			}
		} else if (theBoard[y][x] instanceof Ship) {
			if (((Ship) hiddenBoard[y][x]).isVertical()) {
				if (((Ship) hiddenBoard[y][x]).isStart(y, x)) {
					return "VA";
				} else if (((Ship) hiddenBoard[y][x]).isEnd(y, x)) {
					return "VC";
				}
				return "VB";
			} else if (!((Ship) hiddenBoard[y][x]).isVertical()) {
				if (((Ship) hiddenBoard[y][x]).isStart(y, x)) {
					return "HA";
				} else if (((Ship) hiddenBoard[y][x]).isEnd(y, x)) {
					return "HC";
				}
				return "HB";
			}
		}
		return "_";

	}

	/**
	 * This toString includes the ships in the final string output, which should be
	 * only visible to the current player.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return String representation of the coordinate.
	 */
	public String toStringCoord(int x, int y) {
		if (theBoard[y][x] == Board.ShotResult.HIT) {
			return "H";
		} else if (theBoard[y][x] == Board.ShotResult.MISS) {
			return "M";
		} else if (theBoard[y][x] == Board.ShotResult.SUNK) {
			return "Sunk";
		} else if (theBoard[y][x] instanceof Ship) {
			return "S";
		}
		return "_";
	}

	public int[] calculateTotalHitsAndMisses(Board playerBoard) {
		int[] result = new int[2];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (playerBoard.theBoard[i][j] == ShotResult.HIT) {
					result[0]++;
				} else if (playerBoard.theBoard[i][j] == ShotResult.MISS) {
					result[1]++;
				}
			}
		}
		return result;
	}

	public void customizeBoardSize(int newSize) {
		this.size = newSize;
		this.theBoard = new Object[newSize][newSize];
		clearBoard();
	}

	public void clearBoard() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				theBoard[i][j] = null;
		this.totalBoardHealth = 0;
	}
}
