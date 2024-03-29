package de.xandor98.chess.game;

import org.apache.commons.lang3.tuple.Pair;

import de.xandor98.chess.exceptions.WrongMoveException;
import de.xandor98.chess.exceptions.WrongNotationException;
import de.xandor98.chess.game.pieces.Chessman;
import de.xandor98.chess.game.pieces.King;
import de.xandor98.chess.game.pieces.Pawn;
import de.xandor98.chess.generated.COLOR;
import de.xandor98.chess.generated.MoveMessage;
import de.xandor98.chess.generated.WishMessage;
import de.xandor98.chess.misc.Logger;
import de.xandor98.chess.misc.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {

    private List<Chessman> chessmanList;
    private COLOR currentPlayer;

    private Position enPassent;

    private int round;
    private int halfMoveClock;

    private COLOR canMakeWish = null;

    private HashMap<COLOR, Boolean> rochade;

    public Board() throws WrongNotationException {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public Board(Board b){
        this.chessmanList = new ArrayList<>();
        for (Chessman chessman : b.getChessmanList(null)) {
            this.chessmanList.add(Parser.getChessman(chessman.getType(), chessman.getColor(), chessman.getPosition()));
        }
        this.currentPlayer = b.currentPlayer;

        this.enPassent = b.enPassent;
        this.round = b.round;
        this.halfMoveClock = b.halfMoveClock;
        this.rochade = b.rochade;
    }

    public Board(String FEN) throws WrongNotationException {
        if(!Parser.FENTester(FEN)){
            throw new WrongNotationException("FEN Notation is wrong");
        }

        chessmanList = new ArrayList<>();
        String[] FENargs = FEN.split(" ");

        //Current Player
        if(FENargs[1].equals("w")){
            currentPlayer = COLOR.WHITE;
        }else {
            currentPlayer = COLOR.BLACK;
        }

        //Chessborad
        int currentY = 0;
        for(String s : FENargs[0].split("/")){
            int currentX = 0;
            for(char c : s.toCharArray()){
                Pair<COLOR, Chessman.ChessmanType> res;
                if((res = (Parser.parseChessman(c))) != null){
                    Chessman man = Parser.getChessman(res.getRight(), res.getLeft(), new Position(currentX, currentY));
                    chessmanList.add(man);
                    currentX++;
                }else{
                    currentX += Integer.parseInt(String.valueOf(c));
                }
            }
            currentY++;
        }

        //HalfMoveClock and Round Clock
        this.halfMoveClock = Integer.parseInt(FENargs[4]);
        this.round = Integer.parseInt(FENargs[5]);

        //Rochade
        this.rochade = new HashMap<>();
        rochade.put(COLOR.BLACK, true);
        rochade.put(COLOR.WHITE, true);

        for (char c : FENargs[2].toCharArray()) {
            switch (c){
                case 'K':
                case 'Q':
                    rochade.put(COLOR.WHITE, false);
                    break;

                case 'k':
                case 'q':
                    rochade.put(COLOR.BLACK, false);
                    break;
                case '-':
            }
        }
    }

    /**
     * This Method Checks if the current Player is in Check and returns all ChessFigures that causes the Check
     * @return Chess Figures that causes the Check of the King
     */
    public List<Chessman> inCheck(){
        Chessman king = null;

        for (Chessman chessman : chessmanList) {
            if(chessman instanceof King && chessman.getColor() == currentPlayer){
                king = chessman;
                break;
            }
        }

        if(king == null){
            throw  new IllegalStateException("NO KING FOUND");
        }

        List<Chessman> inCheck = new ArrayList<>();
        for (Chessman chessman : getChessmanList(currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK)) {
            if(chessman.getMoves(this).contains(king.getPosition())){
                inCheck.add(chessman);
            }
        }
        return inCheck;
    }

    public void justRemove(Position pos){
        if(this.getChessmanByPosition(pos) != null)
            chessmanList.remove(this.getChessmanByPosition(pos));
    }

    public boolean isWin(){
        List<Chessman> inCheck = inCheck();
        boolean win = true;

        if(inCheck.size() > 0){
            for (Chessman chessman : getChessmanList(currentPlayer)) {
                if(chessman.getCheckMoves(this, inCheck).size() > 0){
                    win = false;
                }
            }
            return win;
        }
        return false;
    }

    public void makeMove(MoveMessage message) throws WrongMoveException {
        
        if(message.getRochade() != null){
            //When the Method returns False the Rochhade was an - in the Notaion of the message
            if (checkRochhade(message)) 
                return;
        }

        Position from = new Position(message.getFrom());
        Position dest = new Position(message.getTo());

        if(!(from.getX() >= 0 && from.getX() < 8)){
            throw new IndexOutOfBoundsException("Position out of range");
        }

        if(!(from.getY() >= 0 && from.getY() < 8)){
            throw new IndexOutOfBoundsException("Position out of range");
        }

        if(!(dest.getX() >= 0 && dest.getX() < 8)){
            throw new IndexOutOfBoundsException("Position out of range");
        }

        if(!(dest.getY() >= 0 && dest.getY() < 8)){
            throw new IndexOutOfBoundsException("Position out of range");
        }

        Chessman men = getChessmanByPosition(from);
        if(men == null){
            throw new IllegalArgumentException();
        }

        List<Position> positions;

        List<Chessman> chessmen = inCheck();

        if(chessmen.size() > 0){
            positions = men.getCheckMoves(this, chessmen);
        }else {
            positions = men.getMoves(this);
        }

        if(men instanceof Pawn){
            if(dest.equals(enPassent)){
                switch (currentPlayer){
                    case WHITE:
                        chessmanList.remove(getChessmanByPosition(new Position(enPassent.getX(), enPassent.getY() - 1)));
                        break;
                    case BLACK:
                        chessmanList.remove(getChessmanByPosition(new Position(enPassent.getX(), enPassent.getY() + 1)));
                        break;
                }
                men.setPosition(enPassent);

                Logger.info(men.getColor(), men.getType(), "does a enPassent on Position", enPassent);

                currentPlayer = currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK;
                round++;

                return;
            }
        }

        if(positions.contains(dest)){
            if(this.getChessmanByPosition(dest) != null){
                Chessman slain = this.getChessmanByPosition(dest);
                chessmanList.remove(slain);
                Logger.info(men.getColor(), men.getType(), "has slain", slain.getColor(), slain.getType());
                halfMoveClock = 0;
            }else if(! (men instanceof Pawn)){
                halfMoveClock++;
            }

            if(men instanceof Pawn){
                halfMoveClock = 0;
                if(Math.abs(dest.getY() - men.getPosition().getY()) == 2){
                    switch (men.getColor()){
                        case BLACK:
                            enPassent = new Position(men.getPosition().getX(), 3);
                        case WHITE:
                            enPassent = new Position(men.getPosition().getX(), 5);
                    }
                }else{
                    enPassent = null;
                }
            }else{
                enPassent = null;
            }

            Logger.info(men.getColor(), men.getType(), "moved from", men.getPosition(), "to", dest);
            if(enPassent != null){
                Logger.info("EnPassant:", enPassent.getX() + "|" + enPassent.getY());
            }
            men.setPosition(dest);
            men.setMoveCounter(men.getMoveCounter() + 1);

            if(men instanceof King){
                this.rochade.put(currentPlayer, true);
            }

        }else{
            throw new WrongMoveException();
        }

        for (Chessman chessman : chessmanList) {
            if(chessman instanceof Pawn) {
                if (chessman.getPosition().getY() == 0 || chessman.getPosition().getY() == 7) {
                    canMakeWish = chessman.getColor();
                    break;
                }
            }
            canMakeWish = null;
        }

        currentPlayer = currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK;
        round++;
    }

    /**
     * This Method checks If the rochade Moves are possible
     * @param message The Move Message from the Client
     * @throws WrongMoveException When the Move is not Possible
     * @return If the Rochade is successfull. When False the Rochade was set with an - in the Notation
     */
    private boolean checkRochhade(MoveMessage message) throws WrongMoveException{
        if(inCheck().size() > 0){
            throw new WrongMoveException();
        }

        if(getChessmanList(currentPlayer).stream().filter(chessman -> chessman.getType().equals(Chessman.ChessmanType.KING))
                .findFirst().orElseThrow(WrongMoveException::new).getMoveCounter() > 0){
            throw new WrongMoveException();
        }

        char rochade = message.getRochade().charAt(0);
        
        switch (rochade){
            case 'K':
                if(currentPlayer != COLOR.WHITE){
                    throw new WrongMoveException();
                }

                if(getChessmanByPosition(new Position(7,7)) == null || getChessmanByPosition(new Position(7,7)).getMoveCounter() > 0 ){
                    throw new WrongMoveException();
                }

                if(getChessmanByPosition(new Position(5, 7)) == null && getChessmanByPosition(new Position(6, 7)) == null){
                    for (Chessman chessman : getChessmanList(currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK)) {
                        if(chessman.getMoves(this).contains(new Position(5,7)) ||
                                chessman.getMoves(this).contains(new Position(6,7))){
                            throw new WrongMoveException();
                        }
                    }
                    Chessman king = getChessmanByPosition(new Position(4,7));
                    king.setPosition(new Position(6,7));
                    king.setMoveCounter(king.getMoveCounter() + 1);

                    Chessman rook = getChessmanByPosition(new Position(7,7));
                    rook.setPosition(new Position(5,7));
                    rook.setMoveCounter(rook.getMoveCounter() + 1);

                    this.rochade.put(currentPlayer, true);
                }else{
                    throw new WrongMoveException();
                }
                break;
            case 'Q':
                if(currentPlayer != COLOR.WHITE){
                    throw new WrongMoveException();
                }

                if(getChessmanByPosition(new Position(0,7)) == null || getChessmanByPosition(new Position(0,7)).getMoveCounter() > 0 ){
                    throw new WrongMoveException();
                }

                if(getChessmanByPosition(new Position(1, 7)) == null && getChessmanByPosition(new Position(2, 7)) == null && getChessmanByPosition(new Position(3, 7)) == null){
                    for (Chessman chessman : getChessmanList(currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK)) {
                        if(chessman.getMoves(this).contains(new Position(1,7)) ||
                                chessman.getMoves(this).contains(new Position(2,7)) ||
                                chessman.getMoves(this).contains(new Position(3,7))){
                            throw new WrongMoveException();
                        }
                    }

                    Chessman king = getChessmanByPosition(new Position(4,7));
                    king.setPosition(new Position(2,7));
                    king.setMoveCounter(king.getMoveCounter() + 1);

                    Chessman rook = getChessmanByPosition(new Position(0,7));
                    rook.setPosition(new Position(3,7));
                    rook.setMoveCounter(rook.getMoveCounter() + 1);

                    this.rochade.put(currentPlayer, true);
                }else{
                    throw new WrongMoveException();
                }
                break;
            case 'k':
                if(currentPlayer != COLOR.BLACK){
                    throw new WrongMoveException();
                }

                if(getChessmanByPosition(new Position(7,0)) == null || getChessmanByPosition(new Position(7,0)).getMoveCounter() > 0 ){
                    throw new WrongMoveException();
                }

                if(getChessmanByPosition(new Position(5, 0)) == null && getChessmanByPosition(new Position(6, 0)) == null){
                    if(getChessmanByPosition(new Position(5, 0)) == null && getChessmanByPosition(new Position(6, 0)) == null){
                        for (Chessman chessman : getChessmanList(currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK)) {
                            if(chessman.getMoves(this).contains(new Position(5,0)) ||
                                    chessman.getMoves(this).contains(new Position(6,0))){
                                throw new WrongMoveException();
                            }
                        }
                        Chessman king = getChessmanByPosition(new Position(4,0));
                        king.setPosition(new Position(6,0));
                        king.setMoveCounter(king.getMoveCounter() + 1);

                        Chessman rook = getChessmanByPosition(new Position(7,0));
                        rook.setPosition(new Position(5,0));
                        rook.setMoveCounter(rook.getMoveCounter() + 1);

                        this.rochade.put(currentPlayer, true);
                    }else{
                        throw new WrongMoveException();
                    }
                }else{
                    throw new WrongMoveException();
                }
                break;
            case 'q':
                if(currentPlayer != COLOR.BLACK){
                    throw new WrongMoveException();
                }

                if(getChessmanByPosition(new Position(0,0)) == null || getChessmanByPosition(new Position(0,0)).getMoveCounter() > 0 ){
                    throw new WrongMoveException();
                }

                if(getChessmanByPosition(new Position(1, 0)) == null && getChessmanByPosition(new Position(2, 0)) == null && getChessmanByPosition(new Position(3, 0)) == null){
                    for (Chessman chessman : getChessmanList(currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK)) {
                        if(chessman.getMoves(this).contains(new Position(1,0)) ||
                                chessman.getMoves(this).contains(new Position(2,0)) ||
                                chessman.getMoves(this).contains(new Position(3,0))){
                            throw new WrongMoveException();
                        }
                    }

                    Chessman king = getChessmanByPosition(new Position(4,0));
                    king.setPosition(new Position(2,0));
                    king.setMoveCounter(king.getMoveCounter() + 1);

                    Chessman rook = getChessmanByPosition(new Position(0,0));
                    rook.setPosition(new Position(3,0));
                    rook.setMoveCounter(rook.getMoveCounter() + 1);

                    this.rochade.put(currentPlayer, true);
                }else{
                    throw new WrongMoveException();
                }
                break;
            default:
                return false;
        }

        return true;
    } 

    public void makeWish(WishMessage message){
        for (Chessman chessman : new ArrayList<>(chessmanList)) {
            if(chessman instanceof Pawn) {
                if (chessman.getPosition().getY() == 0 || chessman.getPosition().getY() == 7) {
                    chessmanList.remove(chessman);
                    Pair<COLOR, Chessman.ChessmanType> tmp = Parser.parseChessman(message.getNewFigure().charAt(0));
                    if (tmp != null) {
                        if(!tmp.getLeft().equals(canMakeWish)){
                            throw new IllegalArgumentException();
                        }
                        chessmanList.add(Parser.getChessman(tmp.getRight(), chessman.getColor(), chessman.getPosition()));
                        canMakeWish = null;
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                }
            }
        }
    }

    public boolean canMakeWish(){
        return canMakeWish != null;
    }

    public String getFEN(){
        StringBuilder FEN = new StringBuilder();

        for(int y = 0; y < 8; y++){
            int leer = 0;
            for (int x = 0; x < 8; x++){
                Chessman man;
                if((man = this.getChessmanByPosition(new Position(x, y))) != null){
                    if(leer != 0){
                        FEN.append(leer);
                        leer = 0;
                    }
                    FEN.append(Parser.parseChessman(man));
                }else{
                    leer++;
                }
            }
            if(leer != 0){
                FEN.append(leer);
            }
            if(y != 7){
                FEN.append("/");
            }
        }

        FEN.append(" ").append(currentPlayer.equals(COLOR.WHITE) ? 'w' : 'b').append(" ");

        String rochade = "";
        if(!this.rochade.get(COLOR.WHITE)){
            try {
                if (getChessmanByPosition(new Position(7, 7)) != null && getChessmanByPosition(new Position(7, 7)).getMoveCounter() == 0) {
                    rochade += "K";
                }
            }catch (NullPointerException ignored){}

            try {
                if (getChessmanByPosition(new Position(7, 7)) != null && getChessmanByPosition(new Position(0, 7)).getMoveCounter() == 0) {
                    rochade += "Q";
                }
            }catch (NullPointerException ignored){}
        }

        if(!this.rochade.get(COLOR.BLACK)){
            try{
                if(getChessmanByPosition(new Position(7,0)) != null && getChessmanByPosition(new Position(7,0)).getMoveCounter() == 0 ){
                    rochade += "k";
                }
            }catch (NullPointerException ignored){}

            try{
                if(getChessmanByPosition(new Position(0,0)) != null && getChessmanByPosition(new Position(0,0)).getMoveCounter() == 0 ){
                    rochade += "q";
                }
            }catch (NullPointerException ignored){}
        }

        if(rochade.equals("")){
            rochade += "-";
        }
        FEN.append(rochade).append(" ");

        FEN.append(enPassent != null? enPassent : "-").append(" ");
        FEN.append(halfMoveClock).append(" ").append(round);

        return FEN.toString();
    }

    public COLOR getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Chessman> getChessmanList(COLOR color){
        if(color == null) return chessmanList;
        return chessmanList.stream().filter(chessman -> chessman.getColor() == color).collect(Collectors.toList());
    }

    public Chessman getChessmanByPosition(Position p){
        Optional<Chessman> opt = chessmanList.stream().filter(chessman -> chessman.getPosition().equals(p)).findFirst();
        return opt.orElse(null);
    }

    public int getHalfMoveClock() {
        return halfMoveClock;
    }

    public void printBoard() {
        StringBuilder s = new StringBuilder();

        for(int y = 0; y < 8; y++){
            s.append("|");
            for(int x = 0; x < 8; x++){
                if(this.getChessmanByPosition(new Position(x, y)) != null){
                    s.append(Parser.parseChessman(this.getChessmanByPosition(new Position(x, y))));
                }else{
                    s.append(" ");
                }
                s.append("|");
            }
            s.append("\n");
        }

        Logger.info(s);
    }
}
