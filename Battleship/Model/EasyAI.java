package model;

import java.util.Random;

import model.Board.ShotResult;

public class EasyAI implements AIStrategy {

	private static Random generator;

	public EasyAI() {
		generator = new Random();
	}

	public boolean placeShips(Board game) {
		Random generator = new Random();
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

	public ShotResult shoot(Board game) {
		ShotResult temp = ShotResult.REPEAT_ATTEMPT;
		while (temp == ShotResult.REPEAT_ATTEMPT) {
			int row = generator.nextInt(game.getSize());
			int col = generator.nextInt(game.getSize());
			temp = game.shotAttempt(row, col);
		}
		return temp;
	}
}