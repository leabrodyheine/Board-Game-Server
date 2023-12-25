import woodland.GameServer;

/**
 * The main class responsible for initializing and starting the GameServer.
 * This class reads command line arguments to get the desired port number
 * and seed for initializing the server.
 */
public class GameServerMain {
    /**
     * The main method used to start the GameServer.
     * 
     * @param args Command line arguments where:
     *             args[0] is the port number on which the server should run.
     *             args[1] is the seed used for generating game-specific data.
     */
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        long seed = Long.parseLong(args[1]);

        GameServer server = new GameServer(port, seed);
        server.runServer(port);
    }
}
