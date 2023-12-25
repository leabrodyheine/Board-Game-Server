package woodland.Creatures;

/**
 * Represents a PrecociousPhoenix which is a specialized type of Creature.
 * The PrecociousPhoenix is distinguished by its deep understanding of the
 * universe and its precocious nature.
 */
public class PrecociousPhoenix extends Creature {
    private int fourty2 = 42;

    /**
     * Constructor for initializing a PrecociousPhoenix.
     * 
     * @param name        The name of the phoenix.
     * @param attackValue The attack value of the phoenix.
     */
    public PrecociousPhoenix(String name, int attackValue) {
        super(name, attackValue);
        setName("Precocious Phoenix");
        setDescription(
                "The PP is a phoenix that is very precocious. The phoenix understands the meaning of life and the universe");
        setAttackValue(fourty2);
        setShortName("PP");
    }
}
