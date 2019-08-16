package Chess.Game;

import Chess.Exceptions.NoSuchFigureException;
import Chess.Exceptions.WrongMoveException;
import Chess.Game.pieces.Chessman;
import Chess.generated.*;
import Chess.misc.Logger;
import Chess.networking.Client;
import Chess.networking.XmlInputStream;
import Chess.networking.XmlOutputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.xml.bind.UnmarshalException;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Game {

    private HashMap<COLOR, Client> player;

    private String password;
    private int gameID;

    public Game(int gameid, String password) {
        player = new HashMap<>();

        this.password = password.isEmpty()? "" : DigestUtils.sha256Hex(password);
        this.gameID = gameid;
    }

    public COLOR getFreeColor(){
        if(player.isEmpty()) return null;
        return player.containsKey(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK;
    }

    public void registerClient(Client client){
        if(player.containsKey(client.getColor())){
            throw new IllegalArgumentException("Farbe existiert bereits");
        }else{
            player.put(client.getColor(), client);
            if(player.containsKey(COLOR.BLACK) && player.containsKey(COLOR.WHITE)){
                Thread t1 = new Thread(this::startGame);
                t1.start();
            }
        }
    }

    public boolean verify(String pass){
        return password.isEmpty() || this.password.equals(DigestUtils.sha256Hex(pass));
    }

    public boolean canJoin(){
        return !player.containsKey(COLOR.BLACK) || !player.containsKey(COLOR.WHITE);
    }

    public boolean isFreeToJoin(){
        return password.isEmpty();
    }

    private void startGame(){
        Board b = new Board();
        Pair<Boolean, COLOR> win;

        COLOR current = COLOR.WHITE;

        do{
            current = current.equals(COLOR.WHITE) ? COLOR.BLACK : COLOR.WHITE;

            Client client = player.get(current);

            while (true) {
                try {
                    Move(client, b);
                    break;
                } catch (IOException | UnmarshalException e) {
                    Logger.error("Something went wring while handling a Move");
                }
            }

            win = b.isWin();
        }while(!win.getLeft());
        for (Client value : player.values()) {
            try {
                sendWinMessage(value, win.getRight(), b);
                sendDisconnectMessage(value);
            } catch (IOException e) {
                Logger.error("Could not send Win to Player " + value.getSocket().getInetAddress());
            }
        }

    }

    private void sendWinMessage(Client client, COLOR winner, Board board) throws IOException {
        XmlOutputStream out = new XmlOutputStream(client.getSocket().getOutputStream());

        ChessMessage message = new ChessMessage();

        WinMessage winMessage = new WinMessage();
        winMessage.setBoard(new BoardData(){{
            this.getFigure().addAll(board.getChessmanList().stream().map(Chessman::getFigure).collect(Collectors.toList()));
            this.setHeight(board.getHeight());
            this.setWidth(board.getWidth());
        }});
        winMessage.setWinner(winner);

        message.setMessageType(MessageType.WIN);
        message.setId(client.getId());
        message.setGameID(this.gameID);

        out.write(message);
    }

    private void sendDisconnectMessage(Client client) throws IOException {
        XmlOutputStream out = new XmlOutputStream(client.getSocket().getOutputStream());

        ChessMessage message = new ChessMessage();
        message.setMessageType(MessageType.DISCONNECT);

        DisconnectMessage disconnectMessage = new DisconnectMessage();
        disconnectMessage.setErrortypeCode(Errortype.NOERROR);
        disconnectMessage.setName("END of game");

        message.setDisconnect(disconnectMessage);
        message.setGameID(this.gameID);
        message.setId(client.getId());

        out.write(message);
    }

    private void Move(Client client, Board board) throws IOException, UnmarshalException {
        XmlOutputStream out = new XmlOutputStream(client.getSocket().getOutputStream());
        XmlInputStream in = new XmlInputStream(client.getSocket().getInputStream());

        ChessMessage message = new ChessMessage();
        message.setMessageType(MessageType.AWAIT_MOVE);

        AwaitMoveMessage moveMessage = new AwaitMoveMessage();
        moveMessage.setBoard(new BoardData(){{
            this.getFigure().addAll(board.getChessmanList().stream().map(Chessman::getFigure).collect(Collectors.toList()));
            this.setHeight(board.getHeight());
            this.setWidth(board.getWidth());
        }});

        message.setAwaitMove(moveMessage);
        message.setId(client.getId());
        message.setGameID(gameID);

        out.write(message);

        ChessMessage resv = in.readChessMessage();
        if(resv.getMessageType().equals(MessageType.MOVE)){
            MoveMessage move = resv.getMove();
            try {
                boolean wish = board.makeMove(move);
                sendAcceptMessage(client, out, Errortype.NOERROR, true);
                if(wish){
                    makeWish(client, board, in, out);
                }
            }catch (NoSuchFigureException e){
                sendAcceptMessage(client, out, Errortype.FIGURE_NOT_FOUND, false);
            }catch (WrongMoveException | IndexOutOfBoundsException e){
                sendAcceptMessage(client, out, Errortype.ILLEGAL_MOVE, false);
            }
        }else{
            sendAcceptMessage(client, out, Errortype.AWAIT_MOVE, false);
            Move(client, board);
        }
    }

    private void makeWish(Client client, Board board, XmlInputStream in, XmlOutputStream out) throws IOException, UnmarshalException {
        ChessMessage wishMessage = new ChessMessage();
        wishMessage.setMessageType(MessageType.MAKE_WISH);

        MakeAWishMessage makeAWishMessage = new MakeAWishMessage();
        makeAWishMessage.setBoard(new BoardData(){{
            this.getFigure().addAll(board.getChessmanList().stream().map(Chessman::getFigure).collect(Collectors.toList()));
            this.setHeight(board.getHeight());
            this.setWidth(board.getWidth());
        }});

        wishMessage.setMakeWish(makeAWishMessage);
        wishMessage.setGameID(gameID);
        wishMessage.setId(client.getId());

        out.write(wishMessage);

        ChessMessage resv = in.readChessMessage();
        if(resv.getMessageType().equals(MessageType.WISH)){
            board.proposeAWish(resv.getWish());

            sendAcceptMessage(client, out, Errortype.NOERROR, true);
        }else{
            sendAcceptMessage(client, out, Errortype.AWAIT_WISH, false);
            makeWish(client, board, in, out);
        }
    }

    private void sendAcceptMessage(Client client, XmlOutputStream out, Errortype errortype, boolean isAccepted) throws IOException {
        ChessMessage fail = new ChessMessage();
        fail.setMessageType(MessageType.ACCEPT);

        AcceptMessage acceptMessage = new AcceptMessage();
        acceptMessage.setErrortypeCode(errortype);
        acceptMessage.setAccept(isAccepted);

        fail.setAccept(acceptMessage);
        fail.setId(client.getId());
        fail.setGameID(gameID);

        out.write(fail);
    }
}
