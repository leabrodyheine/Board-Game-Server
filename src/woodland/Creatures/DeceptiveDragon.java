package woodland.Creatures;

/**
 * Represents a DeceptiveDragon which is a specialized type of Creature.
 * The DeceptiveDragon is characterized by its ability to deceive others through
 * social engineering tactics.
 */
public class DeceptiveDragon extends Creature {
    private int twenty9 = 29;

    /**
     * Constructor for initializing a DeceptiveDragon.
     * 
     * @param name        The name of the dragon.
     * @param attackValue The attack value of the dragon.
     */
    public DeceptiveDragon(String name, int attackValue) {
        super(name, attackValue);
        setName("Deceptive Dragon");
        setDescription(
                "The DD is a dragon that practices social engineering. The dragon is very good at sending phishing emails pretending to be a prince");
        setShortName("DD");
        setAttackValue(twenty9);
    }
}
