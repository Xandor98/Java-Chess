package Chess.Game;

import Chess.generated.COLOR;
import Chess.networking.Client;
import org.apache.commons.codec.digest.DigestUtils;

public class Game {

    private Client player1;
    private Client player2;

    private String password;

    public Game(String password) {
        this.player1 = null;
        this.player2 = null;

        this.password = password.isEmpty()? "" : DigestUtils.sha256Hex(password);
    }

    public COLOR getFreeColor(){
        if(player1 == null) return null;
        return player1.getColor().equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK;
    }

    public void addClient(Client client){
        if(this.player1 == null){
            this.player1 = client;
        }else if(this.player2 == null){
            if(client.getColor() == null){
                client.setColor(this.player1.getColor().equals(COLOR.WHITE) ? COLOR.BLACK : COLOR.WHITE);
            }
            if(client.getColor() != this.player1.getColor()){
                this.player2 = client;
                Thread t1 = new Thread(this::startGame);
                t1.start();
            }else{
                throw new IllegalArgumentException("Clients should not be the same Color");
            }
        }else{
            throw new IllegalArgumentException("All ready full");
        }

    }

    public boolean verify(String pass){
        return password.isEmpty() || this.password.equals(DigestUtils.sha256Hex(pass));
    }

    public boolean isFreeToJoin(){
        return password.isEmpty();
    }

    private void startGame(){

    }
}
