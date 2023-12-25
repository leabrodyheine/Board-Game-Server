package woodland;

import woodland.Animals.Animal;
import woodland.Creatures.Creature;
import woodland.Spells.Spell;

/**
 * Represents a square on the game board.
 * Each square can have various attributes like visibility, presence of an
 * animal, creature, or spell.
 */
public class Square {
    protected int row;
    protected int col;
    protected boolean visible;
    protected boolean hasAnimal;
    protected boolean hasCreature;
    protected boolean hasSpell;
    protected Spell spell;
    protected Creature creature;
    protected Animal animal;

    /**
     * Constructs a square with the given row and column coordinates.
     * Initializes the square without any animal or creature.
     * 
     * @param row The row position of the square.
     * @param col The column position of the square.
     */
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        this.animal = null;
        this.creature = null;
    }

    /**
     * Returns the row position of the square.
     * 
     * @return The row position.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column position of the square.
     *
     * @return The column position.
     */
    public int getCol() {
        return col;
    }

    /**
     * Checks if the square is visible.
     * 
     * @return true if the square is visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Sets the square to be visible.
     */
    public void reveal() {
        visible = true;
    }

    /**
     * Checks if the square has a creature.
     * 
     * @return true if the square has a creature, false otherwise.
     */
    public boolean hasCreature() {
        return hasCreature;
    }

    public void setHasCreature(Boolean hasCreature) {
        this.hasCreature = hasCreature;
    }

    /**
     * Checks if the square has an animal.
     * 
     * @return true if the square has an animal, false otherwise.
     */
    public boolean hasAnimal() {
        return hasAnimal;
    }

    /**
     * Checks if the square has a spell.
     * 
     * @return true if the square has a spell, false otherwise.
     */
    public boolean hasSpell() {
        return hasSpell;
    }

    /**
     * Sets the given animal to the square.
     * Marks the square as having an animal.
     * 
     * @param animal The animal to be set to the square.
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;
        hasAnimal = true;
    }

    /**
     * Removes the animal from the square.
     * Marks the square as not having an animal.
     */
    public void removeAnimal() {
        animal = null;
        hasAnimal = false;
    }

    /**
     * Removes the spell from the square.
     * Marks the square as not having a spell.
     */
    public void removeSpell() {
        spell = null;
        hasSpell = false;
    }

    /**
     * Sets the given creature to the square.
     * Marks the square as having a creature.
     *
     * @param creature The creature to be set to the square.
     */
    public void setCreature(Creature creature) {
        this.creature = creature;
        hasCreature = true;
    }

    /**
     * Returns the animal currently present on the square.
     * 
     * @return The animal on the square. Returns null if no animal is present.
     */
    public Animal getAnimal() {
        return animal;
    }

    /**
     * Returns the creature currently present on the square.
     * 
     * @return The creature on the square. Returns null if no creature is present.
     */
    public Creature getCreature() {
        return creature;
    }

    /**
     * Returns the spell currently present on the square.
     * 
     * @return The spell on the square. Returns null if no spell is present.
     */
    public Spell getSpell() {
        return spell;
    }

    /**
     * Sets the given spell to the square.
     * Marks the square as having a spell.
     * 
     * @param spell The spell to be set to the square.
     */
    public void setSpell(Spell spell) {
        this.spell = spell;
        hasSpell = true;
    }
}
