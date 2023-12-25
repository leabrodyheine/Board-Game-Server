package woodland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import woodland.Animals.Animal;
import woodland.Spells.Spell;

/**
 * Represents a game server that allows communication with clients to interact
 * with the game.
 * It listens for client connections, processes client requests, and responds
 * accordingly.
 */
public class GameServer {
    private Game game;
    private long seed;
    private String turnType = "move";
    private int zero = 0;
    private int one = 1;
    private int five = 5;
    private int twohundred = 200;
    private int fourhundred = 400;
    private int fivehundred = 500;

    /**
     * Initializes a game server with the specified port and seed for the game.
     * 
     * @param port The port number the server listens on.
     * @param seed The seed used to initialize the game.
     */
    public GameServer(int port, long seed) {
        this.game = new Game(seed);
        this.seed = seed;
    }

    /**
     * Starts a server to listen for incoming connections on the given port.
     * For each connection, it processes the client's request as long as the game is
     * not over.
     * 
     * @param port The port number on which the server will listen for connections.
     */
    public void runServer(int port) {
        try (ServerSocket ss = new ServerSocket(port)) {
            while (!game.gameOver()) {
                Socket conn = ss.accept();
                clientRequest(conn);
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the type of turn that is currently active in the game.
     * This method is presumably used to control the flow of the game based on the
     * turn type.
     * 
     * @param string The description of the current turn type.
     */
    public void setTurnType(String string) {
        this.turnType = string;
    }

    /**
     * Parses a move action from a JSON string and performs the move if it's valid.
     * The method verifies that the specified animal can make the move based on the
     * game's current state.
     * If the move is valid, the method updates the game state accordingly and may
     * change the turn type to 'spell'.
     *
     * @param jsonString  The JSON string representing the move action to be parsed.
     * @param animalIndex The index of the animal that is supposed to move.
     * @return true if the move was successfully parsed and executed; false
     *         otherwise.
     */
    public boolean parseMoveAction(String jsonString, int animalIndex) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonString))) {
            JsonObject jsonObject = jsonReader.readObject();

            JsonObject toSquare = jsonObject.getJsonObject("toSquare");
            int row = toSquare.getInt("row");
            int col = toSquare.getInt("col");

            String action = jsonObject.getString("action");

            Animal specAnimal = game.getAnimals().get(animalIndex);
            Square currentSquare = specAnimal.getSquare();
            int currrentRow = currentSquare.getRow();
            int currentCol = currentSquare.getCol();

            String currentAnimal = this.game.getCurrentAnimalName();

            if (specAnimal.getName().equals(currentAnimal) && turnType.equals("move")
                    && specAnimal.move(currrentRow, currentCol, row, col)) {
                game.setStatus("The last move was successful.");
                game.thisTurn = true;
                if (action.equals("move")) {
                    setTurnType("spell");
                }
                return true;
            } else if (specAnimal.getName()
                    .equals(game.getAnimals().get((game.currentAnimalIndex + one) % five).getName())
                    && specAnimal.move(currrentRow, currentCol, row, col)) {
                specAnimal.move(currrentRow, currentCol, row, col);
                game.setStatus("The last move was successful.");
                game.nextTurn = true;
                this.game.attackAnimal();
                game.updateCurrentAnimalIndex();
                if (action.equals("move")) {
                    setTurnType("spell");
                }
                return true;
            } else if (!specAnimal.getName().equals(currentAnimal)
                    || !currentAnimal
                            .equals(game.getAnimals().get((game.currentAnimalIndex + one) % five).getName().trim())) {
                game.setStatus("The last move was invalid.");
                return false;
            } else if (!specAnimal.move(currrentRow, currentCol, row, col)) {
                game.setStatus("The last move was invalid.");
                return false;
            } else {
                game.setStatus("The last move was invalid.");
                return false;
            }
        }
    }

    /**
     * Parses a spell action from the provided JSON string and executes the spell if
     * the action is valid.
     * The method checks if the specified animal can cast the spell and updates the
     * game state based on the result.
     * If a spell action is performed, the turn type is set to 'move', indicating
     * the next action should be a move.
     *
     * @param jsonString  The JSON string containing the spell action details.
     * @param animalIndex The index of the animal that is casting the spell.
     * @return true if the spell action was successfully parsed and executed; false
     *         otherwise.
     */
    public boolean parseSpellAction(String jsonString, int animalIndex) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonString))) {
            JsonObject jsonObject = jsonReader.readObject();

            String action = jsonObject.getString("action");
            String spell = jsonObject.getString("spell");
            if (action.equals("spell")) {
                setTurnType("move");
            }

            Animal specAnimal = game.getAnimals().get(animalIndex);
            String currentAnimal = jsonObject.getString("animal");
            if (!specAnimal.getName().equals(currentAnimal)) {
                game.setStatus("The last move was invalid.");
                return false;
            }

            for (Spell spellObject : specAnimal.getSpells().keySet()) {
                if (spellObject.getName().equals(spell)) {
                    this.game.castSpell(specAnimal, spellObject);
                    game.setStatus("The last spell was successful.");
                    game.currentAnimalUsedSpell = false;
                    game.nextAnimalUsedSpell = false;
                    this.game.attackAnimal();
                } else {
                    this.game.attackAnimal();
                }
            }
            return true;
        }
    }

    /**
     * Handles client requests received through the specified socket. It processes
     * different HTTP request
     * types such as GET, POST, and OPTIONS, and performs actions accordingly. It
     * handles actions like
     * fetching the game state, updating the game state with move and spell actions,
     * and resetting the game.
     *
     * @param clientSocket The socket connected to the client.
     */
    public void clientRequest(Socket clientSocket) {
        PrintWriter out = null;
        try {
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String line = in.readLine();
            int contentLength = zero;
            String body = "";

            if (line == "" || line == null) {
                return;
            }
            String[] first_line = line.split(" ");

            // read header
            while (!line.isEmpty()) {
                if (line.startsWith("Content-Length: ")) {
                    contentLength = Integer.parseInt(line.substring("Content-Length: ".length()));
                } else if (line.isEmpty()) {
                    contentLength = zero;
                    break;
                }
                line = in.readLine();
            }

            if (contentLength > -1) {
                char[] bodyChars = new char[contentLength];
                in.read(bodyChars, zero, contentLength);
                body = new String(bodyChars);
            }

            if (first_line[zero].equals("OPTIONS")) {
                String headers = stringHeaders(out, twohundred, zero);
                out.print(headers);
                return;

            } else if (first_line[zero].equals("GET")) {
                if (first_line[one].equals("/")) {
                    String responseBody = "{\"status\": \"ok\"}";
                    String headers = stringHeaders(out, twohundred, responseBody.length());
                    out.print(headers + responseBody);

                } else if (first_line[one].equals("/game") || first_line[one].equals("/game/debug")) {
                    String responseBody = getGameStateAsJson().toString();
                    String headers = stringHeaders(out, twohundred, responseBody.length());
                    out.print(headers + responseBody);

                }

            } else if (first_line[zero].equals("POST")) {
                if (first_line[one].equals("/game")) {
                    try (JsonReader jsonReader = Json.createReader(new StringReader(body))) {
                        JsonObject jsonObject = jsonReader.readObject();
                        String actionType = jsonObject.getString("action");
                        int animalIndex = getAnimalIndex(body);

                        if (actionType.equals("move")) {
                            boolean parseMove = parseMoveAction(body, animalIndex);
                            if (!parseMove) {
                                String responseBody = getGameStateAsJson().toString();
                                String headers = stringHeaders(out, twohundred, responseBody.length());
                                out.print(headers + responseBody);
                                out.flush();
                                return;
                            } else if (parseMove) {
                                String responseBody = getGameStateAsJson().toString();
                                String headers = stringHeaders(out, twohundred, responseBody.length());
                                out.print(headers + responseBody);
                                out.flush();
                            }

                        } else if (actionType.equals("spell")) {
                            parseSpellAction(body, animalIndex);
                            String responseBody = getGameStateAsJson().toString();
                            String headers = stringHeaders(out, twohundred, responseBody.length());
                            out.print(headers + responseBody);
                            out.flush();
                        }
                    }
                } else if (first_line[one].equals("/reset")) {
                    this.game = new Game(seed);
                    turnType = "move";
                    String responseBody = getGameStateAsJson().toString();
                    String headers = stringHeaders(out, twohundred, responseBody.length());
                    out.print(headers + responseBody);
                    out.flush();
                }
            }
            out.flush();

        } catch (SocketException e) {
            System.err.println("Socket error: " + e.getMessage());
            sendError(out, fourhundred, "Socket error");
        } catch (JsonException e) {
            System.err.println("JSON parsing error: " + e.getMessage());
            sendError(out, fourhundred, "JSON parsing error");
        } catch (NumberFormatException e) {
            System.err.println("Error parsing Content-Length header: " + e.getMessage());
            sendError(out, fourhundred, "Error parsing Content-Length");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Unexpected request format: " + e.getMessage());
            sendError(out, fourhundred, "Unexpected request format");
        } catch (IOException e) {
            e.printStackTrace();
            sendError(out, fivehundred, "Internal Server Error");
        } catch (Exception e) {
            System.err.println("General exception: " + e.getMessage());
            e.printStackTrace();
            sendError(out, fivehundred, "Internal Server Error");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Determines the index of the animal from the JSON string provided. The
     * animal's index is
     * essential for processing game actions related to that specific animal.
     *
     * @param JSONString The JSON string that contains the animal information.
     * @return The index of the animal if found; -1 if the animal does not exist.
     */
    public int getAnimalIndex(String JSONString) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(JSONString))) {
            JsonObject jsonObject = jsonReader.readObject();

            String animal = jsonObject.getString("animal");

            for (int i = zero; i < five; i++) {
                Animal specAnimal = game.animals.get(i);
                if (specAnimal.getName().equals(animal)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Sends an error response to the client with the specified status code and
     * error message.
     * This method formats the response in JSON.
     *
     * @param out          The PrintWriter object to send the error message to the
     *                     client.
     * @param statusCode   The HTTP status code representing the type of error.
     * @param errorMessage The error message to be sent to the client.
     */
    private void sendError(PrintWriter out, int statusCode, String errorMessage) {
        String jsonResponse = String.format("{\"error\": \"%s\"}", errorMessage);
        stringHeaders(out, statusCode, jsonResponse.length());
        out.print(jsonResponse);
    }

    /**
     * Creates a JSON object representing the current state of the game. This
     * includes the game board,
     * whether the game is over, the current and next animal turns, the status of
     * the game, and the
     * current turn type (move or spell).
     *
     * @return A JsonObject representing the current game state.
     */
    public JsonObject getGameStateAsJson() {
        JsonObjectBuilder gameStateJsonBuilder = Json.createObjectBuilder();

        String currentAnimalName = game.getCurrentAnimalName();
        String nextAnimalName = game.getNextAnimalName();

        gameStateJsonBuilder.add("board", game.toJson())
                .add("gameOver", game.gameOver())
                .add("currentAnimalTurn", currentAnimalName)
                .add("nextAnimalTurn", nextAnimalName)
                .add("status", this.game.status)
                .add("currentAnimalTurnType", turnType);
        return gameStateJsonBuilder.build();
    }

    /**
     * Sends HTTP headers with the given status code to the client.
     * 
     * @param out           The PrintWriter to send data to the client.
     * @param statusCode    The HTTP status code to be sent.
     * @param contentlength the length of the content of the response
     * @return a string of the header
     */
    public String stringHeaders(PrintWriter out, int statusCode, int contentLength) {
        String response = "HTTP/1.1 " + statusCode + "\r\n"
                + "Access-Control-Allow-Origin: *\r\n"
                + "Access-Control-Allow-Methods: *\r\n"
                + "Access-Control-Allow-Headers: *\r\n"
                + "Access-Control-Max-Age: 86400\r\n"
                + "Content-Type: application/json\r\n"
                + "Content-Length: " + contentLength + "\r\n"
                + "\r\n";
        return response;
    }
}
