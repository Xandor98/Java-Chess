package de.xandor98.chess.server;

import java.net.Socket;
import java.util.UUID;

import de.xandor98.chess.generated.COLOR;

public class Client {

    private COLOR color;
    private Socket socket;
    private String name;
    private UUID playerID;

    public Client(COLOR color, Socket socket, String name, UUID playerID) {
        this.color = color;
        this.socket = socket;
        this.name = name;
        this.playerID = playerID;
    }

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public void setPlayerID(UUID playerID) {
        this.playerID = playerID;
    }
}
