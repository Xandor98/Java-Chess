package de.xandor98.chess.game;

import javax.xml.bind.UnmarshalException;

import de.xandor98.chess.exceptions.WrongMoveException;
import de.xandor98.chess.exceptions.WrongNotationException;
import de.xandor98.chess.server.Client;
import de.xandor98.chess.generated.*;
import de.xandor98.chess.misc.Settings;
import de.xandor98.chess.networking.XmlInputStream;
import de.xandor98.chess.networking.XmlOutputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.*;

public class Game {

    HashMap<COLOR, Client> clients = new HashMap<>();

    public COLOR getFreeColor(){
        return clients.containsKey(COLOR.BLACK) ? COLOR.WHITE : (clients.containsKey(COLOR.WHITE)? COLOR.BLACK : null);
    }

    public void addClient(Client c){
        if (!clients.containsKey(c.getColor()))
            clients.put(c.getColor(), c);

        if(clients.values().size() == 2)
            startGame();
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

                if (b.getHalfMoveClock() == 50){
                    break;
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
                            this.setWinner((finalB.getHalfMoveClock() == 50) ? null : Fwinner);
                            this.setBoard(new BoardData(){{
                                this.setFEN(finalB.getFEN());
                            }});
                            this.setRemis(finalB.getHalfMoveClock() == 50);
                        }});
                       this.setMessageType(MessageType.WIN);
                       this.setColor(value.getColor());
                       this.setPlayerID(value.getPlayerID().toString());
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
                this.setPlayerID(client.getPlayerID().toString());
            }});

            ChessMessage message = in.readChessMessage();
            if (!message.getPlayerID().equals(client.getPlayerID().toString())){
                out.write(createAcceptMessage(client, false, Errortype.WRONG_PLAYERID));
            }else if(message.getMessageType().equals(MessageType.WISH)){
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

    public void makeMove(Client client, Board b) throws IOException {
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
                this.setPlayerID(client.getPlayerID().toString());
            }});

            FutureTask<ChessMessage> messageFutureTask = new FutureTask<>(in::readChessMessage);
            messageFutureTask.run();
            ChessMessage message = null;
            try {
                message = messageFutureTask.get(Settings.TIMEOUT_TIME, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }

            if(message == null){
                out.write(createAcceptMessage(client, false, Errortype.TIMEOUT));
                sendDissconect(client);
            }else if (!message.getPlayerID().equals(client.getPlayerID().toString())){
                out.write(createAcceptMessage(client, false, Errortype.WRONG_PLAYERID));
            }else if(message.getMessageType().equals(MessageType.MOVE)){
                try {
                    b.makeMove(message.getMove());
                    out.write(createAcceptMessage(client, true, Errortype.NOERROR));
                    break;
                } catch (WrongMoveException wrongMove) {
                    out.write(createAcceptMessage(client, false, Errortype.ILLEGAL_MOVE));
                }
            }else{
                out.write(createAcceptMessage(client, false, Errortype.AWAIT_MOVE));
            }
        }while (true);
    }

    private void sendDissconect(Client failure){
        for (Client value : clients.values()) {
            try {
                XmlOutputStream out = new XmlOutputStream(value.getSocket().getOutputStream());
                out.write(new ChessMessage(){{
                    this.setDisconnect(new DisconnectMessage(){{
                        this.setName(failure.getName() + " has not respond");
                        this.setErrortypeCode(Errortype.ERROR);
                    }});
                    this.setColor(value.getColor());
                    this.setMessageType(MessageType.DISCONNECT);
                    this.setPlayerID(value.getPlayerID().toString());
                }});
            } catch (IOException ignore) {
            }
        }
        System.exit(0);
    }

    private ChessMessage createAcceptMessage(Client c, boolean accept, Errortype errortype){
        ChessMessage message = new ChessMessage();
        message.setColor(c.getColor());
        message.setMessageType(MessageType.ACCEPT);
        message.setPlayerID(c.getPlayerID().toString());

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
