package woodland.Animals;

import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import woodland.Square;
import woodland.Spells.Spell;
import woodland.Game;

/**
 * Represents an animal in the woodland environment.
 * Each animal has attributes like name, life points, spells, and others.
 */
public class Animal {

    private String name;
    protected Map<Spell, Integer> spells = new HashMap<>();
    protected int lifePoints;
    protected String description;
    protected boolean hasSpell;
    private Map<Integer, Animal> animalIdentifier = new HashMap<>();
    private Square square;
    protected Game game;
    protected boolean shielded = false;
    private int zero = 0;
    private int one = 1;
    private int nineteen = 19;
    private int hundred = 100;
    private int ten = 10;

    /**
     * Constructs an Animal instance with a specified name. Sets initial life points
     * to 100
     * and maps the animal to a unique identifier.
     *
     * @param name The name of the animal.
     */
    public Animal(String name) {
        this.name = name;
        lifePoints = hundred;
        animalIdentifier.put(name.hashCode(), this);
    }

    /**
     * Sets the game environment in which this animal operates.
     *
     * @param game The game context to set.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Retrieves an Animal instance based on its unique identifier.
     *
     * @param identifier The unique identifier for the animal.
     * @return Animal instance corresponding to the identifier, or null if not
     *         found.
     */
    public Animal getAnimalByIdentifier(Integer identifier) {
        return animalIdentifier.get(identifier);
    }

    /**
     * Determines if an animal can move to a specified position based on its current
     * position. This method always returns false and should be overridden in
     * subclasses.
     *
     * @param oldRow The animal's current row.
     * @param oldCol The animal's current column.
     * @param newRow The desired new row.
     * @param newCol The desired new column.
     * @return true if the movement is valid, false otherwise.
     */
    public boolean move(int oldRow, int oldCol, int newRow, int newCol) {
        return false;
    }

    /**
     * Validates whether a move from the current position to a new position is
     * allowed within
     * the game's rules and boundaries.
     *
     * @param oldRow The animal's current row.
     * @param oldCol The animal's current column.
     * @param newRow The desired new row.
     * @param newCol The desired new column.
     * @return true if the move is valid, false otherwise.
     */

    public boolean validMove(int oldRow, int oldCol, int newRow, int newCol) {
        if (newRow <= nineteen && newCol <= nineteen) {
            if (game.getSquare(newRow, newCol).hasAnimal()) {
                return false;
            } else if ((newRow < zero && newRow > nineteen) || (newCol > nineteen && newCol < zero)) {
                game.setStatus("The last move was invalid.");
                return false;
            } else {
                return true;
            }
        } else {
            game.setStatus("The last move was invalid.");
            return false;
        }
    }

    /**
     * Retrieves the square on which the animal currently resides.
     *
     * @return The current square of the animal.
     */
    public Square getSquare() {
        return square;
    }

    /**
     * Sets the square where the animal is currently positioned.
     *
     * @param square The square to place the animal on.
     */
    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * Heals the animal, increasing its life points by 10.
     */
    public void heal() {
        lifePoints += ten;
    }

    /**
     * Damages the animal by reducing its life points.
     * 
     * @param attackValue The amount of damage to inflict on the animal.
     */
    public void attacked(int attackValue) {
        lifePoints -= attackValue;
    }

    /**
     * Checks if the animal is still alive based on its life points.
     * 
     * @return true if the animal has life points greater than 0, false otherwise.
     */
    public boolean isAlive() {
        if (lifePoints <= zero) {
            return false;
        }
        return true;
    }

    /**
     * Adds a spell to the animal's collection of spells.
     * If the spell already exists, its count is not increased.
     * 
     * @param spell The spell to be added.
     */
    public void addSpell(Spell spell) {
        if (spells.containsKey(spell)) {
            int value = spells.get(spell);
            spells.put(spell, value + one);
        } else {
            spells.put(spell, one);
        }
    }

    /**
     * Updates the count of a specific spell for the animal.
     * If the spell doesn't exist in the collection, it's added with a count of 1.
     * If it exists, its count is incremented.
     * 
     * @param spell The spell to be updated.
     */
    public void updateSpell(Spell spell) {
        if (spells.containsKey(spell)) {
            int value = spells.get(spell);
            spells.put(spell, value - one);
            if (value - one == zero) {
                spells.remove(spell);
            }
        }
    }

    /**
     * Checks if the animal has any spells.
     * 
     * @return true if the animal has spells, false otherwise.
     */
    public boolean hasSpell() {
        if (hasSpell == true) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves a collection of the animal's spells along with their quantities.
     * 
     * @return A map of spells and their respective counts.
     */
    public Map<Spell, Integer> getSpells() {
        return spells;
    }

    /**
     * Returns the description of the animal.
     * 
     * @return The description of the animal.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for the animal.
     * 
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the name of the animal.
     * 
     * @return The name of the animal.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the life points for the animal.
     * 
     * @param lifePoints The number of life points to set.
     */
    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    /**
     * Checks for the presence of other animals on the path to a new position.
     * The method checks linearly from the old position to the new one on the grid.
     *
     * @param oldRow The starting row.
     * @param oldCol The starting column.
     * @param newRow The destination row.
     * @param newCol The destination column.
     * @return true if there is an animal on the path, false otherwise.
     */
    protected boolean animalInPath(int oldRow, int oldCol, int newRow, int newCol) {
        if (oldCol != newCol) {
            for (int col = oldCol + one; col <= newCol; col++) {
                if (this.game.getSquare(newRow, col).hasAnimal()) {
                    return true;
                }
            }
        } else {
            for (int row = oldRow + one; row <= newRow; row++) {
                if (this.game.getSquare(row, newCol).hasAnimal()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a creature in the path between the old position and the
     * new position.
     * A creature in the path would prevent an animal from moving from its current
     * square to the target square.
     * This method assumes a straight line movement either horizontally or
     * vertically.
     * 
     * @param oldRow The starting row position of the animal.
     * @param oldCol The starting column position of the animal.
     * @param newRow The target row position of the animal.
     * @param newCol The target column position of the animal.
     * @return true if there is at least one creature in the path, false otherwise.
     */
    public boolean creatureInPath(int oldRow, int oldCol, int newRow, int newCol) {
        if (oldCol != newCol) {
            for (int col = oldCol + one; col <= newCol; col++) {
                if (this.game.getSquare(newRow, col).hasCreature()) {
                    return true;
                }
            }
        } else {
            for (int row = oldRow + one; row <= newRow; row++) {
                if (this.game.getSquare(row, newCol).hasCreature()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets the shielded status of the animal. When shielded, the animal might be
     * immune to certain attacks or effects.
     * 
     * @param shielded A boolean value representing the shielded status to set for
     *                 the animal.
     */

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

    /**
     * Converts the animal's attributes to a JSON object representation.
     * 
     * @return JsonObject representing the animal.
     */
    public JsonObject toJson() {
        JsonObjectBuilder animalJsonBuilder = Json.createObjectBuilder();

        animalJsonBuilder.add("name", this.name)
                .add("type", "Animal")
                .add("description", getDescription())
                .add("life", this.lifePoints);

        JsonArrayBuilder spellsArrayBuilder = Json.createArrayBuilder();
        for (Map.Entry<Spell, Integer> entry : spells.entrySet()) {
            if (spells != null) {
                JsonObjectBuilder spellBuilder = Json.createObjectBuilder();
                spellBuilder.add("name", entry.getKey().getName());
                spellBuilder.add("description", entry.getKey().getDescription());
                spellBuilder.add("amount", entry.getValue());
                spellsArrayBuilder.add(spellBuilder);
            }
        }
        animalJsonBuilder.add("spells", spellsArrayBuilder);

        return animalJsonBuilder.build();
    }
}
