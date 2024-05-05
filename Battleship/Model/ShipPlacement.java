package model;

import java.util.ArrayList;
import java.util.Random;

public class ShipPlacement {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static ArrayList<int[]> getAllShipPlacements(Board board, int row, int col, int size) {
        ArrayList<int[]> placements = new ArrayList<>();

        // Check for other ships in the specified positions
        if (!isShipPresent(board, row, col, size, Direction.UP)) {
            int[] upPlacement = new int[]{row, col, Direction.UP.ordinal()};
            placements.add(upPlacement);
        }

        if (!isShipPresent(board, row, col, size, Direction.DOWN)) {
            int[] downPlacement = new int[]{row, col, Direction.DOWN.ordinal()};
            placements.add(downPlacement);
        }

        if (!isShipPresent(board, row, col, size, Direction.LEFT)) {
            int[] leftPlacement = new int[]{row, col, Direction.LEFT.ordinal()};
            placements.add(leftPlacement);
        }

        if (!isShipPresent(board, row, col, size, Direction.RIGHT)) {
            int[] rightPlacement = new int[]{row, col, Direction.RIGHT.ordinal()};
            placements.add(rightPlacement);
        }

        return placements;
    }

    private static boolean isShipPresent(Board board, int row, int col, int size, Direction direction) {
        for (int i = 0; i < size; i++) {
            int checkRow = row;
            int checkCol = col;
            switch (direction) {
                case UP:
                    checkRow -= i;
                    break;
                case DOWN:
                    checkRow += i;
                    break;
                case LEFT:
                    checkCol -= i;
                    break;
                case RIGHT:
                    checkCol += i;
                    break;
            }

            if (checkRow < 0 || checkRow >= board.getSize() || checkCol < 0 || checkCol >= board.getSize() ||
                    board.getBoard()[checkRow][checkCol] instanceof Ship) {
                return true; // Ship is present
            }
        }
        return false; // No ship present
    }

    public static void randomlyPlaceRemainingShips(Board board) {
        Random random = new Random();
        int[] shipSizes = {1, 3, 3, 4, 5};

        for (int size : shipSizes) {
            int row, col;
            ArrayList<int[]> placements;
            do {
                row = random.nextInt(board.getSize());
                col = random.nextInt(board.getSize());
                placements = getAllShipPlacements(board, row, col, size);
            } while (placements.isEmpty());

            // Randomly select a valid placement
            int[] selectedPlacement = placements.get(random.nextInt(placements.size()));

            // Create and add the ship to the board
            Ship ship = new Ship(row, col, selectedPlacement[2], size);
            board.addShipToBoard(ship);
        }
    }

    public static void shuffleShips(Board board) {
        Random random = new Random();

        // Remove existing ships from the board
        board.clearBoard();

        // Place ships randomly on the board
        randomlyPlaceRemainingShips(board);
    }
}
