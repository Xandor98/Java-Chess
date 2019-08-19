package Chess.Server;

import Chess.generated.COLOR;

import java.net.Socket;

public class Client {

    private COLOR color;
    private Socket socket;
    private String name;

    public Client(COLOR color, Socket socket, String name) {
        this.color = color;
        this.socket = socket;
        this.name = name;
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
}
