package woodland.Creatures;

/**
 * The SassySphinx class represents a unique kind of creature - a Sphinx that is
 * characterized by its sassiness. It is well known for its ability to respond
 * with sarcasm.
 * This class extends the Creature class and inherits its properties and
 * methods.
 */
public class SassySphinx extends Creature {
    private int twenty1 = 21;

    /**
     * Constructs a new instance of SassySphinx with the provided name and attack
     * value.
     * 
     * @param name        The name of the SassySphinx.
     * @param attackValue The attack value of the SassySphinx.
     */
    public SassySphinx(String name, int attackValue) {
        super(name, attackValue);
        setName("Sassy Sphinx");
        setDescription(
                "The SS is a sphinx that is very sassy. The sphinx is very good at giving sarcastic answers to questions");
        setAttackValue(twenty1);
        setShortName("SS");
    }
}
