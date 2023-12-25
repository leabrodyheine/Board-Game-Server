package woodland.Moveables;

/**
 * Represents entities that have the capability to perform short jumps from one
 * position to another.
 * This interface extends the basic Jumpable interface to specify that the jump
 * is a "short" jump.
 */
public interface ShortJumpable extends Jumpable {

    /**
     * Attempts to perform a short jump from the specified position to a new
     * position.
     *
     * @param oldRow The original row from which the short jump starts.
     * @param oldCol The original column from which the short jump starts.
     * @param newRow The target row to jump to.
     * @param newCol The target column to jump to.
     * @return true if the short jump operation was successful, false otherwise.
     */
    boolean jump(int oldRow, int oldCol, int newRow, int newCol);
}
