package model;

import model.Board.ShotResult;

/**
 * Basic AIStrategy interface.
 */
public interface AIStrategy {

	/**
	 * Shot result template function.
	 * 
	 * @param game board object
	 * @return ShotResult type
	 */
	public ShotResult shoot(Board game);

	/**
	 * AI Automatic ship placing function
	 * 
	 * @param game board object
	 * @return boolean
	 */
	public boolean placeShips(Board game);

}
