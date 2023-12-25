package woodland.Creatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import woodland.Animals.Animal;

/**
 * Represents a Creature in the woodland environment.
 * Creatures have various attributes, including their ability to charm animals,
 * use animals as shields, and their confusion state.
 */
public class Creature {
    protected String name;
    protected int attackValue;
    protected Map<Animal, Integer> charmAnimal;
    protected boolean confused;
    protected String shortName;
    protected String description;
    protected List<Animal> shieldAnimal;
    protected Animal confusedByAnimal;
    private int zero = 0;
    private int one = 1;
    private int three = 3;

    /**
     * Constructs a new Creature with the given name and attack value.
     * Initializes charm and shield collections, and sets the confusion state to
     * false.
     * 
     * @param name        The name of the Creature.
     * @param attackValue The attack value of the Creature.
     */
    public Creature(String name, int attackValue) {
        this.name = name;
        this.attackValue = attackValue;
        this.confused = false;
        this.charmAnimal = new HashMap<>();
        this.shieldAnimal = new ArrayList<>();
    }

    /**
     * Adds the specified animal to the creature's shield.
     * 
     * @param animal The animal to be added to the shield.
     */
    public void addShieldAnimal(Animal animal) {
        shieldAnimal.add(animal);
    }

    /**
     * Adds an animal to the charm list with a default duration.
     * 
     * @param animal Animal to be charmed.
     */
    public void addCharmAnimal(Animal animal) {
        charmAnimal.put(animal, three);
    }

    /**
     * Sets the confusion state of the creature.
     * 
     * @param confused The confusion state to set.
     */
    public void setConfused(boolean confused) {
        this.confused = confused;
    }

    /**
     * Checks if a given animal is charmed by the creature.
     * 
     * @param animal Animal to check.
     * @return True if charmed, false otherwise.
     */
    public boolean isCharmed(Animal animal) {
        return charmAnimal.containsKey(animal) && charmAnimal.get(animal) > zero;
    }

    /**
     * Checks the confusion state of the creature.
     * 
     * @return True if confused, false otherwise.
     */
    public boolean isConfused() {
        return confused;
    }

    /**
     * Checks if a given animal is used as a shield by the creature.
     * 
     * @param animal Animal to check.
     * @return True if used as shield, false otherwise.
     */
    public boolean isShieldAnimal(Animal animal) {
        return shieldAnimal.contains(animal);
    }

    /**
     * Updates the charm duration of a charmed animal.
     * 
     * @param animal Charmed animal whose charm duration needs to be updated.
     */
    public void updateCharmAnimal(Animal animal) {
        if (charmAnimal.containsKey(animal)) {
            int turnsLeft = charmAnimal.get(animal) - one;
            if (turnsLeft <= zero) {
                charmAnimal.remove(animal);
            } else {
                charmAnimal.put(animal, turnsLeft);
            }
        }
    }

    /**
     * Removes an animal from the shield list.
     * 
     * @param animal Animal to be removed from the shield.
     */
    public void updateShieldAnimal(Animal animal) {
        shieldAnimal.remove(animal);
    }

    /**
     * Updates the confusion status of this animal. If the specified animal is the
     * one
     * that caused confusion to this animal, it clears the confusion status.
     *
     * @param animal The animal that may have caused confusion to this animal.
     */
    public void updateConfused(Animal animal) {
        if (animal == confusedByAnimal) {
            setConfused(false);
        }
    }

    /**
     * Sets the animal that has caused confusion to this animal.
     * Storing this reference allows to later check if the confusion should be
     * maintained or cleared.
     *
     * @param animal The animal causing confusion to this animal.
     */
    public void setConfusedAnimal(Animal animal) {
        confusedByAnimal = animal;
    }

    /**
     * Sets the description of the creature.
     * 
     * @param description A descriptive text about the creature.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the description of the creature.
     * 
     * @return A string description of the creature.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the attack value of creatures.
     * 
     * @return The attack value.
     */
    public int getAttackValue() {
        return attackValue;
    }

    /**
     * Sets the attack value for creatures.
     * 
     * @param attackValue The new attack value to set.
     */
    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    /**
     * Sets the short name of the creature.
     * 
     * @param shortName The short name representation of the creature.
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Sets the name of this animal.
     *
     * @param name The new name to be assigned to the animal.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of this animal.
     *
     * @return The current name of the animal.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the short name representation of the creature.
     * 
     * @return The short name of the creature.
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Converts the state of this creature into a JSON object representation.
     * The JSON object includes properties such as name, type, short name,
     * description,
     * attack value, and confused state. It also includes a nested JSON object
     * representing
     * charmed animals and their respective states.
     *
     * @return A JsonObject representing the creature's current state.
     */
    public JsonObject toJson() {
        JsonObjectBuilder creatureJsonBuilder = Json.createObjectBuilder();
        creatureJsonBuilder.add("name", getName())
                .add("type", "Creature")
                .add("shortName", getShortName())
                .add("description", getDescription())
                .add("attack", attackValue)
                .add("confused", this.confused);

        JsonObjectBuilder charmAnimalsBuilder = Json.createObjectBuilder();
        for (Animal animal : this.charmAnimal.keySet()) {
            charmAnimalsBuilder.add(animal.getName(), this.charmAnimal.get(animal));
        }
        creatureJsonBuilder.add("charm", charmAnimalsBuilder.build());

        return creatureJsonBuilder.build();
    }
}
