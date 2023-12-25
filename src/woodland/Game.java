package woodland;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

import woodland.Animals.Animal;
import woodland.Animals.Badger;
import woodland.Animals.Deer;
import woodland.Animals.Fox;
import woodland.Animals.Owl;
import woodland.Animals.Rabbit;
import woodland.Creatures.ComplicatedCentaur;
import woodland.Creatures.Creature;
import woodland.Creatures.DeceptiveDragon;
import woodland.Creatures.PrecociousPhoenix;
import woodland.Creatures.SassySphinx;
import woodland.Creatures.UnderAppreciatedUnicorn;
import woodland.Spells.Spell;

/**
 * Represents a game board with animals, creatures, and spells. The game
 * provides functionalities
 * for moving animals, attacking creatures, saving and casting spells, among
 * other operations.
 */
public class Game {
    public String status = "";
    protected final int ROW = 20;
    protected final int COL = 20;
    protected Square[][] board;
    protected int turnNumber;
    protected boolean gameOver;
    protected Creature creature;
    protected ArrayList<Animal> animals;
    protected List<Creature> creatures;
    protected List<Spell> spells;
    protected int lastAnimalIndex = 0;
    protected int currentAnimalIndex = 0;
    private boolean hasMoved = false;
    boolean currentAnimalUsedSpell = false;
    boolean nextAnimalUsedSpell = false;
    boolean thisTurn = false;
    boolean nextTurn = false;
    static long seed;
    private int zero = 0;
    private int one = 1;
    private int two = 2;
    private int five = 5;
    private int nineteen = 19;
    private int ten = 10;
    private int thirty6 = 36;
    private int twenty9 = 29;
    private int fourty2 = 42;
    private int twenty1 = 21;
    private int fourteen = 14;

    /**
     * Initializes a new game with a specified random seed. This constructor sets up
     * the game
     * board and populates it with animals and creatures at random locations. It
     * also distributes
     * spells throughout the board.
     *
     * The game board is represented by a 2D array of Square objects. Each square
     * can have either an
     * animal, a creature, or a spell. The game starts with all animals placed at
     * random positions on
     * the bottom row of the board and creatures scattered throughout the rest of
     * the board. Spells
     * are distributed randomly across the board.
     *
     * @param seed The seed for the random number generator. This ensures that the
     *             random placements
     *             and choices are consistent across different runs of the game with
     *             the same seed.
     */
    public Game(long seed) {

        Game.seed = seed;

        board = new Square[ROW][COL];
        gameOver = false;

        for (int i = zero; i < ROW; i++) {
            for (int j = zero; j < COL; j++) {
                board[i][j] = new Square(i, j);
            }
        }

        Random generator = new Random(seed);

        animals = new ArrayList<Animal>();
        animals.add(new Rabbit("Rabbit"));
        animals.add(new Fox("Fox"));
        animals.add(new Deer("Deer"));
        animals.add(new Owl("Owl"));
        animals.add(new Badger("Badger"));

        for (int i = zero; i < five; i++) {
            int randomNumber = generator.nextInt(COL);
            while (board[nineteen][randomNumber].hasAnimal()) {
                randomNumber = generator.nextInt(COL);
            }
            board[nineteen][randomNumber].setAnimal(animals.get(i));
            animals.get(i).setSquare(board[nineteen][randomNumber]);
            board[nineteen][randomNumber].setVisible(true);
            animals.get(i).setGame(this);
        }

        creatures = new ArrayList<Creature>();
        creatures.add(new UnderAppreciatedUnicorn("Under Appreciated Unicorn", fourteen));
        creatures.add(new ComplicatedCentaur("Complicated Centaur", thirty6));
        creatures.add(new DeceptiveDragon("Deceptive Dragon", twenty9));
        creatures.add(new PrecociousPhoenix("Precocious Phoenix", fourty2));
        creatures.add(new SassySphinx("Sassy Sphinx", twenty1));

        for (int i = zero; i < five; i++) {
            int row = generator.nextInt(ROW - two) + one;
            int col = generator.nextInt(COL);
            while (board[row][col].hasCreature()) {
                row = generator.nextInt(ROW - two) + one;
                col = generator.nextInt(COL);
            }
            Square square = board[row][col];
            square.setCreature(creatures.get(i));
            square.setHasCreature(true);
        }

        spells = new ArrayList<Spell>();
        spells.add(Spell.DETECT);
        spells.add(Spell.HEAL);
        spells.add(Spell.SHIELD);
        spells.add(Spell.CONFUSE);
        spells.add(Spell.CHARM);

        for (int i = zero; i < ten; i++) {
            int spellIndex = generator.nextInt(five);
            int row = generator.nextInt(ROW - two) + one;
            int col = generator.nextInt(COL);
            while (board[row][col].hasCreature() 
            || board[row][col].hasSpell()) {
                row = generator.nextInt(ROW - two) + one;
                col = generator.nextInt(COL);
            }
            board[row][col].setSpell(spells.get(spellIndex));
        }
    }

    /**
     * Returns the game board.
     * 
     * @return The current game board as a 2D array of Squares.
     */
    public Square[][] getBoard() {
        return board;
    }

    /**
     * Fetches the square at the specified row and column.
     * 
     * @param row The row of the desired square.
     * @param col The column of the desired square.
     * @return The square at the given row and column.
     */
    public Square getSquare(int row, int col) {
        return board[row][col];
    }

    /**
     * Sets the current status message for the game. This message is typically used
     * to communicate the result
     * of the last action taken, such as an invalid move or the outcome of a game
     * event.
     *
     * @param string The message that describes the current status of the game.
     */
    public void setStatus(String string) {
        this.status = string;
    }

    /**
     * Moves the specified animal from one position to another on the board.
     *
     * @param animal The animal to move.
     * @param oldRow The original row of the animal.
     * @param oldCol The original column of the animal.
     * @param newRow The new row to move the animal to.
     * @param newCol The new column to move the animal to.
     */
    public void moveAnimal(Animal animal, int oldRow, int oldCol, int newRow, int newCol) {
        Square destination = board[newRow][newCol];
        if (board[newRow][newCol].hasCreature()) {
            setStatus("The last move was sucessful");
            hasMoved = true;
            thisTurn = true;
            nextTurn = false;
        } else if (animal.creatureInPath(oldRow, oldCol, newRow, newCol)) {
            setStatus("The last move was interrupted by a creature.");
            hasMoved = true;
            thisTurn = true;
            nextTurn = false;
            destination = getNextSquareWithCreature(oldRow, oldCol, newRow, newCol);
        } else if (board[newRow][newCol].hasSpell()) {
            hasMoved = true;
            thisTurn = true;
            nextTurn = false;
            saveSpell(animal, board[newRow][newCol].getSpell());
            setStatus("The last move was successful.");
        } else {
            hasMoved = true;
            thisTurn = true;
            nextTurn = false;
            setStatus("The last move was successful.");
        }
        board[oldRow][oldCol].removeAnimal();
        destination.setAnimal(animal);
        destination.setVisible(true);
        animal.setSquare(destination);
    }

    /**
     * Searches for and returns the next {@link Square} containing a creature when
     * moving from an old
     * position to a new position. The method searches along a straight line in
     * either horizontal or vertical
     * direction based on the coordinates provided.
     *
     * @param oldRow the row index of the starting square
     * @param oldCol the column index of the starting square
     * @param newRow the row index of the ending square
     * @param newCol the column index of the ending square
     * @return the next square containing a creature along the path, or {@code null}
     *         if no such square exists
     */
    public Square getNextSquareWithCreature(int oldRow, int oldCol, int newRow, int newCol) {
        if (oldCol != newCol) {
            int colStep = (oldCol < newCol) ? one : -one;

            for (int col = oldCol + colStep; (colStep > zero) ? col <= newCol : col >= newCol; col += colStep) {
                if (this.getSquare(newRow, col).hasCreature()) {
                    return board[newRow][col];
                }
            }
        } else {
            for (int row = oldRow + one; row <= newRow; row++) {
                if (this.getSquare(row, newCol).hasCreature()) {
                    return board[row][newCol];
                }
            }
        }
        return null;
    }

    /**
     * Simulates an attack on the animal by a creature.
     */
    public void attackAnimal() {
        Square currentAnimalSquare = animals.get(currentAnimalIndex).getSquare();
        if (currentAnimalSquare.hasCreature()) {
            if (animals.get(currentAnimalIndex).getSquare().getCreature().isCharmed(animals.get(currentAnimalIndex)) 
            || animals.get(currentAnimalIndex).getSquare().getCreature().isConfused() 
            || animals.get(currentAnimalIndex).getSquare().getCreature()
                            .isShieldAnimal(animals.get(currentAnimalIndex))) {
                return;
            } else {
                animals.get(currentAnimalIndex)
                        .attacked(animals.get(currentAnimalIndex).getSquare().getCreature().getAttackValue());
            }
        }
        if (!animals.get(currentAnimalIndex).isAlive()) {
            gameOver = true;
        }
    }

    /**
     * Saves a spell for the given animal.
     * 
     * @param animal The animal saving the spell.
     * @param spell  The spell to save.
     */
    public void saveSpell(Animal animal, Spell spell) {
        animal.addSpell(spell);
    }

    /**
     * Casts the specified spell by the given animal. The effect of the spell is
     * determined by the type of spell
     * being cast. This method also tracks which animal cast the spell and sets the
     * status accordingly.
     * 
     * 
     * Spell effects include adding shields to creatures, charming creatures,
     * confusing creatures, revealing
     * squares, and healing the animal casting the spell.
     *
     * @param animal the animal casting the spell
     * @param spell  the spell to be cast
     */
    public void castSpell(Animal animal, Spell spell) {
        Square currentSquare = animal.getSquare();
        int currentRow = currentSquare.row;
        int currentCol = currentSquare.col;
        if (animals.get(currentAnimalIndex).equals(animal)) {
            currentAnimalUsedSpell = true;
        } else if (animals.get(currentAnimalIndex + one).equals(animal)) {
            nextAnimalUsedSpell = true;
        }

        switch (spell) {
            case SHIELD:
                if (animal.getSquare().hasCreature()) {
                    Creature creature = animal.getSquare().getCreature();
                    creature.addShieldAnimal(animal);
                }
                setStatus("The last spell was successful.");
                break;

            case CHARM:
                for (int i = currentRow - one; i <= currentRow + one; i++) {
                    for (int j = currentCol - one; j <= currentCol + one; j++) {
                        if (i != currentRow && j != currentCol) {
                            if (board[i][j].hasCreature()) {
                                board[i][j].getCreature().addCharmAnimal(animal);
                            }
                        }
                    }
                }
                animal.updateSpell(Spell.CHARM);
                setStatus("The last spell was successful.");
                break;

            case CONFUSE:
                for (int i = currentRow - one; i <= currentRow + one; i++) {
                    for (int j = currentCol - one; j <= currentCol + one; j++) {
                        if (i != currentRow && j != currentCol) {
                            if (board[i][j].hasCreature()) {
                                board[i][j].getCreature().setConfused(true);
                                board[i][j].getCreature().setConfusedAnimal(animal);
                            }
                        }
                    }
                }
                animal.updateSpell(Spell.CONFUSE);
                setStatus("The last spell was successful.");
                break;

            case DETECT:
                for (int i = currentRow - one; i <= currentRow + one; i++) {
                    for (int j = currentCol - one; j <= currentCol + one; j++) {
                        int variableI = Math.min((Math.max(i, zero)), nineteen);
                        int variableJ = Math.min((Math.max(j, zero)), nineteen);
                        board[variableI][variableJ].reveal();
                    }
                }
                animal.updateSpell(Spell.DETECT);
                setStatus("The last spell was successful.");
                break;

            case HEAL:
                animal.heal();
                animal.updateSpell(Spell.HEAL);
                setStatus("The last spell was successful.");
                break;

            default:
                if (hasMoved == true && currentAnimalUsedSpell == true) {
                    updateCurrentAnimalIndex();
                } else if (hasMoved == false && nextTurn == true) {
                    updateCurrentAnimalIndex();
                }
                break;
        }
    }

    /**
     * Checks if the game is over.
     * 
     * @return true if the game is over, false otherwise.
     */
    public boolean gameOver() {
        return gameOver;
    }

    /**
     * Retrieves the list of animals in the game.
     * 
     * @return A list of animals.
     */
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    /**
     * Retrieves the name of the current animal in turn.
     *
     * @return the name of the current animal.
     */
    public String getCurrentAnimalName() {
        return animals.get(currentAnimalIndex).getName();
    }

    /**
     * Retrieves the name of the next animal in the turn sequence.
     *
     * @return the name of the next animal.
     */
    public String getNextAnimalName() {
        return animals.get((currentAnimalIndex + one) % five).getName();
    }

    /**
     * Updates the index to point to the next animal in turn. This method takes into
     * account
     * various conditions like whether the current animal has used a spell, or if it
     * is time
     * for the next turn, to decide which animal should be next.
     */
    public void updateCurrentAnimalIndex() {
        lastAnimalIndex = currentAnimalIndex;
        currentAnimalIndex = (currentAnimalIndex + one) % five;

        if (animals.get((lastAnimalIndex + one) % five).getName().equals(animals.get(currentAnimalIndex).getName())) {
            currentAnimalIndex = (currentAnimalIndex) % five;
        } else if (nextTurn) {
            currentAnimalIndex = (currentAnimalIndex + one) % five;
        } else if (currentAnimalUsedSpell) {
            currentAnimalIndex = (currentAnimalIndex) % five;
        } else if (nextAnimalUsedSpell) {
            currentAnimalIndex = (currentAnimalIndex + one) % five;
        }
        hasMoved = false;
    }

    /**
     * Converts the current state of the game board into a JSON array. The JSON
     * array
     * represents the rows and columns of the board with nested arrays. Only visible
     * squares on the board are included. If a square is visible and contains an
     * animal
     * or a creature, their JSON representation is added to the array.
     * 
     * @return A JSON array representing the rows and columns of the game board and
     *         including visible animals and creatures.
     */
    public JsonArray toJson() {
        JsonArrayBuilder boardRow = Json.createArrayBuilder();
        for (int i = zero; i < ROW; i++) {
            JsonArrayBuilder boardCol = Json.createArrayBuilder();
            for (int j = zero; j < COL; j++) {
                JsonArrayBuilder boardContents = Json.createArrayBuilder();
                if (getSquare(i, j).isVisible()) {
                    if (getSquare(i, j).getAnimal() != null) {
                        boardContents.add(getSquare(i, j).getAnimal().toJson());
                    }
                    if (getSquare(i, j).getCreature() != null) {
                        boardContents.add(getSquare(i, j).getCreature().toJson());
                    }
                }
                boardCol.add(boardContents);
            }
            boardRow.add(boardCol);
        }
        return boardRow.build();
    }
}
