package woodland.Spells;

/**
 * Represents various types of spells in a game with their respective names and
 * descriptions.
 * Each spell has a unique effect that can be utilized within the game.
 */
public enum Spell {
    /**
     * The detect spell allows the animal to detect the mythical creatures on the
     * adjacent squares.
     */
    DETECT("Detect", "The detect spell allows the animal to detect the mythical creatures on the adjacent squares."),
    /**
     * The heal spell allows the animal to heal 10 life points.
     */
    HEAL("Heal", "The heal spell allows the animal to heal 10 life points."),
    /**
     * The shield spell allows the animal to block a mythical creature attack for
     * that turn
     */
    SHIELD("Shield", "The shield spell allows the animal to block a mythical creature attack for that turn"),
    /**
     * The confuse spell allows the animal to confuse a mythical creature on a
     * square adjacen to the animal but not the square the animal is occupying.
     * The mythical creature will not attack an animal for the next turn.
     */
    CONFUSE("Confuse",
            "The confuse spell allows the animal to confuse a mythical creature on a square adjacent to the animal but not the square the animal is occupying. The mythical creature will not attack any animal for the next turn."),
    /**
     * The charm spell allows the animal to charm a mythical creature on a square
     * adjacent to the animal but not the square the animal is occupying. The
     * mythical creature will not attack the charming animal for the next three
     * turns
     */
    CHARM("Charm",
            "The charm spell allows the animal to charm a mythical creature on a square adjacent to the animal but not the square the animal is occupying. The mythical creature will not attack the charming animal for the next three turns");

    private final String name;
    private final String description;

    /**
     * Constructs a spell with the given name and description.
     *
     * @param name        The name of the spell.
     * @param description A brief description of the spell's effect.
     */
    Spell(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the name of the spell.
     *
     * @return The name of the spell.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the spell.
     *
     * @return The description of what the spell does.
     */
    public String getDescription() {
        return description;
    }
}
