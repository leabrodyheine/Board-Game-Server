package woodland.Animals;

import woodland.Moveables.ShortJumpable;

/**
 * Represents a Rabbit in the woodland environment.
 * The Rabbit has specific attributes and behaviors and implements the
 * ShortJumpable interface.
 */
public class Rabbit extends Animal implements ShortJumpable {
    private int hundred = 100;
    private int two = 2;
    private int zero = 0;
    private int one = 1;

    /**
     * Constructs a new Rabbit with the specified name.
     * Initializes its life points and sets a description.
     * 
     * @param name The name of the Rabbit.
     */
    public Rabbit(String name) {
        super(name);
        setLifePoints(hundred);
        setDescription("The rabbit has fluffy ears and tail. The rabbit really likes to eat grass.");
    }

    /**
     * Determines if the jump from the start position to the end position is valid.
     * The jump is considered valid if it is either a vertical or horizontal jump
     * within a distance of two squares, or a diagonal jump of exactly one square.
     *
     * @param oldRow The row index of the starting position.
     * @param oldCol The column index of the starting position.
     * @param newRow The row index of the ending position.
     * @param newCol The column index of the ending position.
     * @return true if the jump is valid, false otherwise.
     */
    @Override
    public boolean jump(int oldRow, int oldCol, int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);
        return (rowDiff <= two && colDiff == zero) || (rowDiff == zero && colDiff <= two)
                || (rowDiff == one && colDiff == one);
    }

    /**
     * Attempts to move the animal from the start position to the end position.
     * The move is valid only if it is within the bounds defined by the validMove
     * method,
     * there is no other animal in the path, and the move adheres to the jumping
     * rules.
     * If the move is not valid, the game's status is updated with an appropriate
     * message.
     *
     * @param oldRow The row index of the starting position.
     * @param oldCol The column index of the starting position.
     * @param newRow The row index of the ending position.
     * @param newCol The column index of the ending position.
     * @return true if the animal moves successfully, false otherwise.
     */
    @Override
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        if (validMove(oldRow, oldCol, newRow, newCol)) {
            if (animalInPath(oldRow, oldCol, newRow, newCol)) {
                return false;
            } else if (jump(oldRow, oldCol, newRow, newCol)) {
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
