package woodland.Moveables;

/**
 * Represents the capability to perform long jumps from one position to another.
 * This interface extends the basic Jumpable interface to specify that the jump
 * is a "long" jump.
 */
public interface LongJumpable extends Jumpable {

    /**
     * Attempts to perform a long jump from the specified position to a new
     * position.
     * 
     * @param oldRow The original row from which the long jump starts.
     * @param oldCol The original column from which the long jump starts.
     * @param newRow The target row to jump to.
     * @param newCol The target column to jump to.
     * @return true if the long jump operation was successful, false otherwise.
     */
    boolean jump(int oldRow, int oldCol, int newRow, int newCol);
}
