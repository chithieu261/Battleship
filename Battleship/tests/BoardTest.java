// Kade Dean
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import model.*;

class BoardTest {

	@Test
	void testBoardSize() {
		assertEquals(10, a.board.size(10).build().getSize());
	}

	@Test
	void testShipSize() {
		assertEquals(10, a.ship.size(10).build().getHealth());
	}

	@Test
	void testAddingASmallShip() {
		assertEquals(2,
				a.board.w(10, a.ship.size(2).build(), new int[][] { { 0, 0 }, { 0, 1 } }).build().getBoardHealth());
	}

	@Test
	void testHittingAShipObject() {
		Ship ship = a.ship.size(3).build();
		ship.damage();
		assertEquals(2, ship.getHealth());
	}

	@Test
	void testAddingABigShip() {
		assertEquals(10, a.board.w(10, a.ship.size(10).build(), new int[][] { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 },
				{ 0, 4 }, { 0, 5 }, { 0, 6 }, { 0, 7 }, { 0, 8 }, { 0, 9 } }).build().getBoardHealth());
	}

	@Test
	void testDamagingAShipOnTheBoard() {
		Board board = a.board.w(10, a.ship.size(3).build(), new int[][] { { 0, 0 }, { 0, 1 }, { 0, 2 } }).build();
		assertEquals(3, board.getBoardHealth());
		board.shotAttempt(0, 2);
		assertEquals(2, board.getBoardHealth());
		board.shotAttempt(0, 1);
		assertEquals(1, board.getBoardHealth());
	}

	@Test
	void testHitting() {
		assertEquals(Board.ShotResult.HIT,
				a.board.w(10, a.ship.size(2).build(), new int[][] { { 0, 0 }, { 0, 1 } }).build().shotAttempt(0, 1));
	}

	@Test
	void testMissing() {
		assertEquals(Board.ShotResult.MISS,
				a.board.w(10, a.ship.size(2).build(), new int[][] { { 0, 0 }, { 0, 1 } }).build().shotAttempt(0, 2));
	}

	@Test
	void testRepeatShot() {
		Board board = a.board.w(10, a.ship.size(2).build(), new int[][] { { 0, 0 }, { 0, 1 } }).build();
		board.shotAttempt(0, 2);
		assertEquals(Board.ShotResult.REPEAT_ATTEMPT, board.shotAttempt(0, 2));
	}

	@Test
	void testSinkingAShipOnTheBoard() {
		assertEquals(Board.ShotResult.SUNK, a.board.w(10, a.ship.size(1).build(), new int[][] { { 0, 0 } },
				a.ship.size(3).build(), new int[][] { { 1, 0 }, { 1, 1 }, { 1, 2 } }).build().shotAttempt(0, 0));
	}

	@Test
	void testWinning() {
		assertEquals(Board.ShotResult.GAME_WON,
				a.board.w(10, a.ship.size(1).build(), new int[][] { { 0, 0 } }).build().shotAttempt(0, 0));
	}

	//@Test
	//void testInvalidShot() {
	//	assertEquals(Board.ShotResult.INVALID, a.board.size(10).build().shotAttempt(20, 20));
	//}

	//@Test
	//void testOutOfBoundsShipPlacement() {
	//	assertFalse(a.board.size(10).build().addShipToBoard(a.ship.size(1).build(), new int[][] { { 10, 10 } }));
	//}

	//@Test
	//void testOverlappingShipPlacement() {
	//	Board board = a.board.w(10, a.ship.size(1).build(), new int[][] { { 1, 1 } }).build();
	//	assertFalse(board.addShipToBoard(a.ship.size(1).build(), new int[][] { { 1, 1 } }));
	//}

	@Test
	void testCheckingHealthOfIndividualShipOnBoard() {
		Ship ship = a.ship.size(3).build();
		Board board = a.board.w(10, ship, new int[][] { { 1, 0 }, { 1, 1 }, { 1, 2 } }).build();
		assertEquals(3, ((Ship) board.getBoard()[0][1]).getHealth());
		board.shotAttempt(1, 0);
		assertEquals(2, ((Ship) board.getBoard()[1][1]).getHealth());
		board.shotAttempt(1, 1);
		assertEquals(1, ((Ship) board.getBoard()[2][1]).getHealth());
	}

}

/**
 * Basic object builder class for Ship.java and Board.java
 */
class a {
	// Creates the board builder for testing
	public static BoardBuilder board = new BoardBuilder();

	// Creates the ship buider for testing ship
	public static ShipBuilder ship = new ShipBuilder();

	/**
	 * Basic builder class for board
	 */
	public static class BoardBuilder {
		// size for each ship
		int size;
		// Hashmap for loading all ships into the board
		HashMap<Ship, int[][]> ships = new HashMap<>();

		BoardBuilder() {
			this.size = 10;
		}

		BoardBuilder(int size) {
			this.size = size;
		}

		BoardBuilder(int size, Ship ship, int[][] locations) {
			this.size = size;
			this.ships.put(ship, locations);
		}

		BoardBuilder(int size, Ship ship1, int[][] location1, Ship ship2, int[][] location2) {
			this.size = size;
			this.ships.put(ship1, location1);
			this.ships.put(ship2, location2);
		}

		public BoardBuilder size(int size) {
			return new BoardBuilder(size);
		}

		public BoardBuilder w(int size, Ship ship, int[][] locations) {
			return new BoardBuilder(size, ship, locations);
		}

		public BoardBuilder w(int size, Ship ship1, int[][] location1, Ship ship2, int[][] location2) {
			return new BoardBuilder(size, ship1, location1, ship2, location2);
		}

		public Board build() {
			Board result = new Board(this.size);
			for (Ship ship : this.ships.keySet()) {
				//result.addShipToBoard(ship, this.ships.get(ship));
			}
			return result;
		}
	}

	/**
	 * Basic builder method for testing ship builder
	 */
	public static class ShipBuilder {
		int size;

		ShipBuilder() {
			this.size = 3;
		}

		ShipBuilder(int size) {
			this.size = size;
		}

		public ShipBuilder size(int size) {
			return new ShipBuilder(size);
		}

		public Ship build() {
			Ship result = new Ship(this.size);
			return result;
		}
	}

}