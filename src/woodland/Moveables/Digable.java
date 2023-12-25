package woodland.Moveables;

/**
 * Represents entities that have the capability to dig from one position to
 * another.
 */
public interface Digable {

  /**
   * Attempts to dig from the specified position to a new position.
   *
   * @param oldRow The original row from which digging starts.
   * @param oldCol The original column from which digging starts.
   * @param newRow The target row to dig to.
   * @param newCol The target column to dig to.
   * @return true if the dig operation was successful, false otherwise.
   */
  boolean dig(int oldRow, int oldCol, int newRow, int newCol);
}
