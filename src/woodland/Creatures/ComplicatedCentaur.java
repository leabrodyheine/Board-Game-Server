package woodland.Creatures;

/**
 * Represents a ComplicatedCentaur in the woodland environment.
 * The ComplicatedCentaur is a special type of Creature with unique attributes.
 */
public class ComplicatedCentaur extends Creature {
    private int thirty6 = 36;

    /**
     * Constructs a new ComplicatedCentaur with the specified name and attack value.
     * Initializes its description, attack value, and short name.
     *
     * @param name        The name of the ComplicatedCentaur.
     * @param attackValue The attack value of the ComplicatedCentaur.
     */
    public ComplicatedCentaur(String name, int attackValue) {
        super(name, attackValue);
        setName("Complicated Centaur");
        setDescription(
                "The CC is a centaur that has mixed feelings about its love interest, a horse. The centaur is unsure whether they can love them fully.");
        setAttackValue(thirty6);
        setShortName("CC");
    }
}
