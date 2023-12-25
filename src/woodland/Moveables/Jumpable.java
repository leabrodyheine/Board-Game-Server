package woodland.Moveables;

/**
 * Represents entities that have the capability to jump from one position to
 * another.
 */
public interface Jumpable {

    /**
     * Attempts to jump from the specified position to a new position.
     *
     * @param oldRow The original row from which jumping starts.
     * @param oldCol The original column from which jumping starts.
     * @param newRow The target row to jump to.
     * @param newCol The target column to jump to.
     * @return true if the jump operation was successful, false otherwise.
     */
    boolean jump(int oldRow, int oldCol, int newRow, int newCol);
}
