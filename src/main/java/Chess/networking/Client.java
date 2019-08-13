package Chess.networking;

import Chess.generated.COLOR;

import java.net.Socket;

public class Client {

    private Socket socket;
    private String name;
    private int id;
    private COLOR color;

    public Client(Socket socket, int id, COLOR color, String name) {
        this.socket = socket;
        this.id = id;
        this.color = color;
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
