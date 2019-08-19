package Chess.Game;

import Chess.Exceptions.WrongNotationException;
import Chess.Server.Client;
import Chess.generated.*;
import Chess.misc.Logger;
import Chess.networking.XmlInputStream;
import Chess.networking.XmlOutputStream;

import javax.xml.bind.UnmarshalException;
import java.io.IOException;
import java.util.HashMap;

public class Game {

    HashMap<COLOR, Client> clients = new HashMap<>();

    public COLOR getFreeColor(){
        return clients.containsKey(COLOR.BLACK) ? COLOR.WHITE : (clients.containsKey(COLOR.WHITE)? COLOR.BLACK : null);
    }

    public void addClient(Client c){
        if(clients.values().size() == 0){
            clients.put(c.getColor(), c);
        }else if(clients.values().size() < 2){
            if(!clients.containsKey(c.getColor())){
                clients.put(c.getColor(), c);
                startGame();
            }
        }
    }

    private void startGame(){
        Board b = null;
        try {
            b = new Board();
        } catch (WrongNotationException e) {
            e.printStackTrace();
            System.exit(0);
        }

        COLOR current = null;
        while (!b.isWin()){
            current = b.getCurrentPlayer();

            try {
                makeMove(clients.get(current), b);

                if(b.canMakeWish()){
                    makeWish(clients.get(current), b);
                }

                b.printBoard();
            } catch (IOException | UnmarshalException e) {
                e.printStackTrace();
            }
        }
        if(current != null) {
            COLOR winner = (current.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK);
            for (Client value : clients.values()) {
                try {
                    XmlOutputStream out = new XmlOutputStream(value.getSocket().getOutputStream());

                    final COLOR Fwinner = winner;
                    Board finalB = b;
                    ChessMessage winMessage = new ChessMessage(){{
                        this.setWin(new WinMessage(){{
                            this.setWinner(Fwinner);
                            this.setBoard(new BoardData(){{
                                this.setFEN(finalB.getFEN());
                            }});
                            this.setRemis(false);
                        }});
                       this.setMessageType(MessageType.WIN);
                       this.setColor(value.getColor());
                    }};

                    out.write(winMessage);
                } catch (IOException ignore) {}
            }
        }
        System.exit(0);
    }

    public void makeWish(Client client, Board b) throws IOException, UnmarshalException {
        XmlOutputStream out = new XmlOutputStream(client.getSocket().getOutputStream());
        XmlInputStream in = new XmlInputStream(client.getSocket().getInputStream());

        do{
            out.write(new ChessMessage(){{
                this.setMakeWish(new MakeAWishMessage(){{
                    this.setBoard(new BoardData(){{
                        this.setFEN(b.getFEN());
                    }});
                }});
                this.setMessageType(MessageType.MAKE_WISH);
                this.setColor(client.getColor());
            }});

            ChessMessage message = in.readChessMessage();
            if(message.getMessageType().equals(MessageType.WISH)){
                try {
                    b.makeWish(message.getWish());

                    out.write(createAcceptMessage(client, true, Errortype.NOERROR));
                    break;
                }catch (IllegalArgumentException e){
                    out.write(createAcceptMessage(client, false, Errortype.AWAIT_WISH));
                }
            }else{
                out.write(createAcceptMessage(client, false, Errortype.AWAIT_WISH));
            }

        }while(true);
    }

    public void makeMove(Client client, Board b) throws IOException, UnmarshalException {
        XmlOutputStream out = new XmlOutputStream(client.getSocket().getOutputStream());
        XmlInputStream in = new XmlInputStream(client.getSocket().getInputStream());

        do{
            out.write(new ChessMessage(){{
                this.setAwaitMove(new AwaitMoveMessage(){{
                    this.setBoard(new BoardData(){{
                        this.setFEN(b.getFEN());
                    }});
                }});
                this.setMessageType(MessageType.AWAIT_MOVE);
                this.setColor(client.getColor());
            }});

            ChessMessage message = in.readChessMessage();
            if(message.getMessageType().equals(MessageType.MOVE)){
                try {
                    b.makeMove(message.getMove());
                    out.write(createAcceptMessage(client, true, Errortype.NOERROR));
                    break;
                } catch (Throwable throwable) {
                    out.write(createAcceptMessage(client, false, Errortype.ILLEGAL_MOVE));
                }
            }else{
                out.write(createAcceptMessage(client, false, Errortype.AWAIT_MOVE));
            }
        }while (true);
    }

    public ChessMessage createAcceptMessage(Client c, boolean accept, Errortype errortype){
        ChessMessage message = new ChessMessage();
        message.setColor(c.getColor());
        message.setMessageType(MessageType.ACCEPT);

        AcceptMessage acceptMessage = new AcceptMessage();

        acceptMessage.setAccept(accept);
        acceptMessage.setErrortypeCode(errortype);

        message.setAccept(acceptMessage);

        return message;
    }

    public boolean hasStarted() {
        return clients.values().size() == 2;
    }
}
