package woodland.Moveables;

/**
 * Represents entities that have the capability to fly from one position to
 * another.
 */
public interface Flyable {
    /**
     * Attempts to fly from the specified position to a new position.
     * 
     * @param oldRow The original row from which flying starts.
     * @param oldCol The original column from which flying starts.
     * @param newRow The target row to fly to.
     * @param newCol The target column to fly to.
     * @return true if the fly operation was successful, false otherwise.
     */
    boolean fly(int oldRow, int oldCol, int newRow, int newCol);
}
