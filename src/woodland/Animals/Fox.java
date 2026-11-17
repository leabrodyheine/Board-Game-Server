package woodland.Animals;

import woodland.Moveables.LongJumpable;

/**
 * Represents a Fox animal in the woodland environment.
 * The Fox has unique attributes and behaviors, and it implements the
 * LongJumpable interface.
 */
public class Fox extends Animal implements LongJumpable {
    private static final int INITIAL_LIFE_POINTS = 100;
    private static final int MAX_JUMP_DISTANCE = 3;

    /**
     * Constructs a new Fox with the specified name.
     * Initializes its life points and sets a description.
     * 
     * @param name The name of the Fox.
     */
    public Fox(String name) {
        super(name);
        setLifePoints(INITIAL_LIFE_POINTS);
        setDescription("The fox has a bushy tail. The fox really enjoys looking at butterflies in the sunlight.");
    }

    /**
     * Implements the jump method from the LongJumpable interface.
     * Allows the Fox to jump and move to a new square on the board.
     * 
     * @param oldRow The starting row of the Fox.
     * @param oldCol The starting column of the Fox.
     * @param newRow The target row for the Fox to move to.
     * @param newCol The target column for the Fox to move to.
     * @return True if the movement is valid based on Fox's jumping behavior,
     *         otherwise false.
     */
    @Override
    public boolean jump(int oldRow, int oldCol, int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);
        return (rowDiff <= MAX_JUMP_DISTANCE && colDiff == 0)
                || (rowDiff == 0 && colDiff <= MAX_JUMP_DISTANCE);
    }

    @Override
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        if (validMove(oldRow, oldCol, newRow, newCol)) {
            if (animalInPath(oldRow, oldCol, newRow, newCol)) {
                game.setStatus("The last move was invalid.");
                return false;
            } else if (jump(oldRow, oldCol, newRow, newCol)) {
                this.game.moveAnimal(this, oldRow, oldCol, newRow, newCol);
                return true;
            } else {
                game.setStatus("The last move was invalid.");
                return false;
            }
        } return false;
    }
}
