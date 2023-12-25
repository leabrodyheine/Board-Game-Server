package woodland.Animals;

import woodland.Moveables.Flyable;

/**
 * Represents an Owl in the woodland environment.
 * The Owl has unique attributes and behaviors and implements the Flyable
 * interface.
 */
public class Owl extends Animal implements Flyable {
    private int hundred = 100;
    private int one = 1;

    /**
     * Constructs a new Owl with the specified name.
     * Initializes its life points and sets a description.
     * 
     * @param name The name of the Owl.
     */
    public Owl(String name) {
        super(name);
        setLifePoints(hundred);
        setDescription("The owl has wings. The owl has prescription contact lenses but cannot put them on.");
    }

    /**
     * Attempts to move the animal from its current position to a new position by
     * flying.
     * The move is only successful if it is valid based on the game's rules and the
     * animal's
     * ability to fly. If the move is not valid, the game's status message is
     * updated to indicate
     * an invalid move.
     *
     * @param oldRow the row index of the animal's current position.
     * @param oldCol the column index of the animal's current position.
     * @param newRow the row index of the animal's desired new position.
     * @param newCol the column index of the animal's desired new position.
     * @return true if the animal successfully flies to the new position, false
     *         otherwise.
     */
    @Override
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        if (validMove(oldRow, oldCol, newRow, newCol)) {
            if (fly(oldRow, oldCol, newRow, newCol) == true) {
                this.game.moveAnimal(this, oldRow, oldCol, newRow, newCol);
                return true;
            } else {
                game.setStatus("The last move was invalid.");
                return false;
            }
        }
        return false;
    }

    /**
     * Determines if the animal can fly from its current position to a new position.
     * The flying is constrained to straight lines horizontally, vertically, or
     * diagonal
     * by one square. Any other move is not allowed for flying.
     *
     * @param oldRow the row index of the animal's current position.
     * @param oldCol the column index of the animal's current position.
     * @param newRow the row index of the desired new position for flying.
     * @param newCol the column index of the desired new position for flying.
     * @return true if the flying move is valid, false otherwise.
     */

    @Override
    public boolean fly(int oldRow, int oldCol, int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);
        if (oldRow == newRow || newCol == oldCol || (rowDiff == one && colDiff == one)) {
            return true;
        } else {
            return false;
        }
    }
}
