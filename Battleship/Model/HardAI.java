package model;

import java.util.Random;
import model.Board.ShotResult;

public class HardAI implements AIStrategy {

	private static Random generator;
	private int last;

	public HardAI() {
		generator = new Random();
		last = 0;
	}

	public boolean placeShips(Board game) {
		int[] shipSizes = new int[] { 2, 3, 3, 4, 5 };
		for (int size : shipSizes) {
			boolean placed = false;
			while (!placed) {
				int row = generator.nextInt(game.getSize());
				int col = generator.nextInt(game.getSize());
				int direction = generator.nextInt(2);
				Ship temp = new Ship(row, col, direction, size);
				placed = game.addShipToBoard(temp);
			}
		}
		return false;
	}

	/**
	 * Given the opponents board, it takes a shot using a smarter strategy
	 * 
	 * @param game
	 * @return
	 */
	public ShotResult shoot(Board game) {
		int size = game.getSize();
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (game.getHiddenCoord(x, y) != null) {
					if (game.getHiddenCoord(x, y).equals(ShotResult.HIT)) {
						if (game.getHiddenCoord(x + 1, y) == null)
							return game.shotAttempt(x + 1, y);
						if (game.getHiddenCoord(x - 1, y) == null)
							return game.shotAttempt(x - 1, y);
						if (game.getHiddenCoord(x, y + 1) == null)
							return game.shotAttempt(x, y + 1);
						if (game.getHiddenCoord(x, y - 1) == null)
							return game.shotAttempt(x, y - 1);
					}
				}
			}
		}

		// Makes a random shot attempt because it has no idea where to shoot
		ShotResult temp = ShotResult.REPEAT_ATTEMPT;
		while (temp == ShotResult.REPEAT_ATTEMPT) {
			temp = game.shotAttempt(last / 10, (last % 10) + ((last / 10) % 2));
			//System.out.println("(" + last / 10 + ", " + ((last % 10) + ((last / 10) % 2)) + ")");
			last += 2;
		}
		return temp;
	}
}