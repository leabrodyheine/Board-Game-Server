# Woodland Strategy Game Server

A completed Java server for a turn-based strategy board game. The server creates a deterministic 20×20 woodland from a supplied random seed, accepts client commands over a socket, updates the game state, and responds with JSON.

## Game model

Players guide animals with different movement abilities across a board populated by stationary mythical creatures and collectible spells. The implementation includes:

- Five animal types with movement-specific behavior
- Creatures with distinct attack values
- Healing and information-revealing spells
- Seeded board generation
- JSON request/response handling for a remote client

## Build and run

Prerequisite: a JDK and the included `javax.json-1.0.jar`.

```bash
cd src
javac -cp "javax.json-1.0.jar" GameServerMain.java woodland/**/*.java
java -cp ".:javax.json-1.0.jar" GameServerMain 8080 42
```

The two arguments are the listening port and board seed. On Windows, replace the classpath separator `:` with `;`.

A compatible course client was published at <https://stacs5001.github.io/p2-client/>; availability and access are controlled by the course maintainers.

## Project status

This repository contains the completed server-side assignment. The separate browser client and the original game specification are not maintained here.
