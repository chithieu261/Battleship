package model;

import model.Board.ShotResult;

public class Player {
	PlayerType playerType;
	int wins;
	int losses;
	private Board board;
	private int size;

	public enum PlayerType {
		HUMAN, AI_HARD, AI_NORMAL, AI_EASY, LAN_HOST, LAN_PLAYER2;
	}

	public Player(PlayerType player, int size) {
		this.wins = 0;
		this.losses = 0;
		this.playerType = player;
		this.size = size;
		board = new Board(size);

		// Automatically places ships if player is computer
		if (playerType.equals(PlayerType.AI_HARD)) {
			HardAI ai = new HardAI();
			ai.placeShips(board);
		}
		if (playerType.equals(PlayerType.AI_NORMAL)) {
			NormalAI ai = new NormalAI();
			ai.placeShips(board);
		}
		if (playerType.equals(PlayerType.AI_EASY)) {
			EasyAI ai = new EasyAI();
			ai.placeShips(board);
		}
	}

	public Player() {
		this.wins = 0;
		this.losses = 0;
	}

	public ShotResult takeShot(Player opponent, int x, int y) {
		if (playerType.equals(PlayerType.AI_EASY) || playerType.equals(PlayerType.AI_NORMAL)
				|| playerType.equals(PlayerType.AI_HARD))
			return ShotResult.INVALID;
		return opponent.getBoard().shotAttempt(x, y);
	}

	public ShotResult takeShot(Player opponent) {
		if (playerType == PlayerType.AI_EASY) {
			EasyAI easyAI = new EasyAI();
			return easyAI.shoot(opponent.getBoard());
		} else if (playerType == PlayerType.AI_NORMAL) {
			NormalAI normalAI = new NormalAI();
			return normalAI.shoot(opponent.getBoard());
		} else if (playerType == PlayerType.AI_HARD) {
			HardAI hardAI = new HardAI();
			return hardAI.shoot(opponent.getBoard());
		}
		return ShotResult.INVALID;
	}

	public boolean isAlive() {
		return board.getBoardHealth() != 0;
	}

	public int getBoardSize() {
		return size;
	}

	public PlayerType getPlayerType() {
		return playerType;
	}

	public void addWin() {
		wins++;
	}

	public int getWins() {
		return wins;
	}

	public void addLoss() {
		losses++;
	}

	public int getLosses() {
		return losses;
	}

	public Board getBoard() {
		return board;
	}
}
