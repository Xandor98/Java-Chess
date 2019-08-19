package Chess.Game;

import Chess.Exceptions.WrongNotationException;
import Chess.Server.Client;
import Chess.generated.COLOR;

public class Game {

    Client c1;
    Client c2;

    public COLOR getFreeColor(){
        return c1 != null ? (c1.getColor().equals(COLOR.WHITE) ? COLOR.BLACK : COLOR.WHITE ): null;
    }

    public void addClient(Client c){
        if(c1 == null){
            c1 = c;
        }else if(c2 == null){
            c2 = c;
            Thread t = new Thread(this::startGame);
            t.start();
        }

    }

    private void startGame(){
        try {
            Board b = new Board();
        } catch (WrongNotationException e) {
            e.printStackTrace();
            System.exit(0);
        }

        while (true){

        }
    }

    public boolean hasStarted() {
        return c1 != null && c2 != null;
    }
}
