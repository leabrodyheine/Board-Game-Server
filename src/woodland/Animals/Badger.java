package woodland.Animals;

import woodland.Moveables.Digable;

/**
 * Represents a Badger, a specific type of animal in the woodland environment.
 * A Badger is characterized by its ability to dig. It implements the
 * {@code Digable} interface
 * indicating it has digging behaviors. This class extends the {@code Animal}
 * class, inheriting its
 * basic attributes and methods.
 */
public class Badger extends Animal implements Digable {
    private int hundred = 100;
    private int one = 1;
    private int two = 2;
    private int zero = 0;

    /**
     * Constructs a new Badger with the specified name. The Badger's life points are
     * initialized to 100 and a specific description is set.
     * 
     * @param name The name of the Badger.
     */
    public Badger(String name) {
        super(name);
        setLifePoints(hundred);
        setDescription(
                "The badger has a black and white face. The badger is a often mistaken for a very small panda. The badger wears a t-shirt that says “I am not a panda” to combat this. ");
    }

    /**
     * Overrides the {@code creatureInPath} method from {@code Animal}. Since
     * Badgers have unique
     * movement behavior, this method is not applicable and thus always returns
     * false, indicating no creature
     * is considered to be in path for a Badger.
     * 
     * @param oldRow The starting row position.
     * @param oldCol The starting column position.
     * @param newRow The destination row position.
     * @param newCol The destination column position.
     * @return Always returns false for Badger as it does not consider creatures in
     *         its path.
     */
    @Override
    public boolean creatureInPath(int oldRow, int oldCol, int newRow, int newCol) {
        return false;
    }

    /**
     * Implements the dig method from the {@code Digable} interface. Determines if
     * the Badger
     * can dig from its current position to the new position. The dig is successful
     * if the movement
     * is either two squares diagonally, two squares vertically/horizontally, or one
     * square in any
     * direction.
     * 
     * @param oldRow The starting row position.
     * @param oldCol The starting column position.
     * @param newRow The destination row position.
     * @param newCol The destination column position.
     * @return true if the Badger can dig to the new position, false otherwise.
     */
    @Override
    public boolean dig(int oldRow, int oldCol, int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);
        return ((rowDiff == two && colDiff == two) || (rowDiff == zero && colDiff == two)
                || (rowDiff == two && colDiff == zero)
                || (rowDiff <= one && colDiff <= one));
    }

    /**
     * Attempts to move the badger from its current position to a new position.
     * The move is valid if it adheres to the badger's movement rules and the new
     * position is within the game's bounds
     * and not already occupied by another creature. The badger has the ability to
     * dig, allowing it to move in a unique pattern.
     * If the move is invalid, the game status is updated accordingly.
     *
     * @param oldRow The row index of the badger's current position.
     * @param oldCol The column index of the badger's current position.
     * @param newRow The row index of the badger's intended new position.
     * @param newCol The column index of the badger's intended new position.
     * @return true if the move is executed successfully, false if the move is
     *         invalid.
     */
    @Override
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        if (validMove(oldRow, oldCol, newRow, newCol)) {
            if (dig(oldRow, oldCol, newRow, newCol)) {
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
