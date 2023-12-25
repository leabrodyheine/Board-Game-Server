package woodland.Animals;

import woodland.Moveables.LongJumpable;

/**
 * Represents a Deer animal in the woodland environment.
 * The Deer has unique attributes and behaviors, and it implements the
 * LongJumpable interface.
 */
public class Deer extends Animal implements LongJumpable {
    private int hundred = 100;
    private int one = 1;
    private int three = 3;
    private int zero = 0;

    /**
     * Constructs a new Deer with the specified name.
     * Initializes its life points and sets a description.
     *
     * @param name The name of the Deer.
     */
    public Deer(String name) {
        super(name);
        setLifePoints(hundred);
        setDescription("The deer has antlers. The deer is recently divorced and is looking for a new partner.");
    }

    /**
     * Implements the jump method from the LongJumpable interface.
     * Allows the Deer to jump and move to a new square on the board.
     * 
     * @param oldRow The starting row of the Deer.
     * @param oldCol The starting column of the Deer.
     * @param newRow The target row for the Deer to move to.
     * @param newCol The target column for the Deer to move to.
     * @return True if the movement is valid based on Deer's jumping behavior,
     *         otherwise false.
     */
    @Override
    public boolean jump(int oldRow, int oldCol, int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);
        return (rowDiff <= three && colDiff == zero) || (rowDiff == zero && colDiff <= three)
                || (rowDiff == one && colDiff == one);
    }

    /**
     * Executes the movement of the animal from its current position to a new
     * position if the move is valid.
     * The move must be valid according to the game rules and specific movement
     * capabilities of the animal,
     * such as jumping in this context. If the new position is not a valid jump or
     * is out of bounds or occupied,
     * the game status is updated to reflect an invalid move.
     *
     * @param oldRow the row index of the animal's current position.
     * @param oldCol the column index of the animal's current position.
     * @param newRow the row index of the animal's desired new position.
     * @param newCol the column index of the animal's desired new position.
     * @return true if the animal successfully moves to the new position, false if
     *         the move is invalid.
     */
    @Override
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        if (validMove(oldRow, oldCol, newRow, newCol)) {
            if (jump(oldRow, oldCol, newRow, newCol)) {
                this.game.moveAnimal(this, oldRow, oldCol, newRow, newCol);
                return true;
            } else {
                game.setStatus("The last move was invalid.");
                return false;
            }
        }
        return false;
    }
}
