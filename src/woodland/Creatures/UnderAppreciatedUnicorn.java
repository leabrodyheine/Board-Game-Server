package woodland.Creatures;

/**
 * The UnderAppreciatedUnicorn class represents a special type of creature:
 * a Unicorn that is often overlooked and undervalued in the woodland ecosystem.
 * This particular kind of unicorn faces challenges with its identity as it's
 * frequently mistaken for just another horse with a horn.
 * This class extends the Creature class and inherits its attributes and
 * behaviors.
 */
public class UnderAppreciatedUnicorn extends Creature {
    private int fourteen = 14;

    /**
     * Constructs a new instance of the UnderAppreciatedUnicorn with a specified
     * name and attack value.
     * 
     * @param name        The name of the UnderAppreciatedUnicorn.
     * @param attackValue The attack value of the UnderAppreciatedUnicorn.
     */
    public UnderAppreciatedUnicorn(String name, int attackValue) {
        super(name, attackValue);
        setName("Under-appreciated Unicorn");
        setDescription(
                "The UU is a unicorn that is under-appreciated by the other mythical creatures because it is often mistaken for a horse with a horn");
        setAttackValue(fourteen);
        setShortName("UU");
    }
}
