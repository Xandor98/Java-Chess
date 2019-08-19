package Chess.Game;

import Chess.Exceptions.WrongMoveException;
import Chess.Exceptions.WrongNotationException;
import Chess.Game.pieces.Chessman;
import Chess.Game.pieces.King;
import Chess.Game.pieces.Pawn;
import Chess.Game.pieces.Rook;
import Chess.generated.COLOR;
import Chess.generated.MoveMessage;
import Chess.misc.Parser;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Board {

    private List<Chessman> chessmanList;
    private COLOR currentPlayer;

    private Position enPassent;

    private int round;
    private int halfMoveClock;

    private HashMap<COLOR, Boolean> rochade;

    public Board() throws WrongNotationException {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public Board(Board b){
        this.chessmanList = new ArrayList<>(b.chessmanList);
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

        if(FENargs[1].equals("w")){
            currentPlayer = COLOR.WHITE;
        }else {
            currentPlayer = COLOR.BLACK;
        }

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

        this.halfMoveClock = Integer.parseInt(FENargs[4]);
        this.round = Integer.parseInt(FENargs[5]);

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

    public List<Chessman> inChess(){
        Chessman king = chessmanList.stream().filter(chessman -> chessman.getType().equals(Chessman.ChessmanType.KING)
                && chessman.getColor().equals(currentPlayer)).findAny().orElse(null);

        if(king == null){
            throw  new IllegalStateException("NO KING FOUND");
        }

        List<Chessman> inChess = new ArrayList<>();
        for (Chessman chessman : getChessmanList(currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK)) {
            if(chessman.getMoves(this).contains(king.getPosition())){
                inChess.add(chessman);
            }
        }
        return inChess;
    }

    public boolean isWin(){
        List<Chessman> inChess = inChess();
        if(inChess.size() > 0){
            for (Chessman chessman : getChessmanList(currentPlayer)) {
                if(chessman.getChessMoves(this, inChess).size() > 0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void makeMove(MoveMessage message) throws Throwable {
        if(message.getRochade() != null){
            if(inChess().size() > 0){
                throw new WrongMoveException();
            }

            if(getChessmanList(currentPlayer).stream().filter(chessman -> chessman.getType().equals(Chessman.ChessmanType.KING))
                    .findFirst().orElseThrow((Supplier<Throwable>) WrongMoveException::new).getMoves() > 0){
                throw new WrongMoveException();
            }

            char rochade = message.getRochade().charAt(0);


            switch (rochade){
                case 'K':
                    if(currentPlayer != COLOR.WHITE){
                        throw new WrongMoveException();
                    }

                    if(getChessmanByPosition(new Position(7,7)) == null || getChessmanByPosition(new Position(7,7)).getMoves() > 0 ){
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
                        king.setMoves(king.getMoves() + 1);

                        Chessman rook = getChessmanByPosition(new Position(7,7));
                        rook.setPosition(new Position(5,7));
                        rook.setMoves(rook.getMoves() + 1);

                        this.rochade.put(currentPlayer, true);
                    }else{
                        throw new WrongMoveException();
                    }
                    break;
                case 'Q':
                    if(currentPlayer != COLOR.WHITE){
                        throw new WrongMoveException();
                    }

                    if(getChessmanByPosition(new Position(0,7)) == null || getChessmanByPosition(new Position(0,7)).getMoves() > 0 ){
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
                        king.setMoves(king.getMoves() + 1);

                        Chessman rook = getChessmanByPosition(new Position(0,7));
                        rook.setPosition(new Position(3,7));
                        rook.setMoves(rook.getMoves() + 1);

                        this.rochade.put(currentPlayer, true);
                    }else{
                        throw new WrongMoveException();
                    }
                    break;
                case 'k':
                    if(currentPlayer != COLOR.BLACK){
                        throw new WrongMoveException();
                    }

                    if(getChessmanByPosition(new Position(7,0)) == null || getChessmanByPosition(new Position(7,0)).getMoves() > 0 ){
                        throw new WrongMoveException();
                    }

                    if(getChessmanByPosition(new Position(5, 0)) == null && getChessmanByPosition(new Position(6, 0)) == null){
                        if(currentPlayer != COLOR.WHITE){
                            throw new WrongMoveException();
                        }

                        if(getChessmanByPosition(new Position(5, 0)) == null && getChessmanByPosition(new Position(6, 0)) == null){
                            for (Chessman chessman : getChessmanList(currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK)) {
                                if(chessman.getMoves(this).contains(new Position(5,0)) ||
                                        chessman.getMoves(this).contains(new Position(6,0))){
                                    throw new WrongMoveException();
                                }
                            }
                            Chessman king = getChessmanByPosition(new Position(4,0));
                            king.setPosition(new Position(6,0));
                            king.setMoves(king.getMoves() + 1);

                            Chessman rook = getChessmanByPosition(new Position(7,0));
                            rook.setPosition(new Position(5,0));
                            rook.setMoves(rook.getMoves() + 1);

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

                    if(getChessmanByPosition(new Position(0,0)) == null || getChessmanByPosition(new Position(0,0)).getMoves() > 0 ){
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
                        king.setMoves(king.getMoves() + 1);

                        Chessman rook = getChessmanByPosition(new Position(0,0));
                        rook.setPosition(new Position(3,0));
                        rook.setMoves(rook.getMoves() + 1);

                        this.rochade.put(currentPlayer, true);
                    }else{
                        throw new WrongMoveException();
                    }
                    break;
            }
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

        if(inChess().size() > 0){
            positions = men.getChessMoves(this, inChess());
        }else {
            positions = men.getMoves(this);
        }

        if(positions.contains(dest)){
            if(this.getChessmanByPosition(dest) != null){
                chessmanList.remove(this.getChessmanByPosition(dest));
                halfMoveClock = 0;
            }else if(! (men instanceof Pawn)){
                halfMoveClock++;
            }

            if(men instanceof Pawn){
                halfMoveClock = 0;
                if(Math.abs(dest.getY() - men.getPosition().getY()) == 2){
                    switch (men.getColor()){
                        case BLACK:
                            enPassent = dest.add(0, -1);
                        case WHITE:
                            enPassent = dest.add(0, 1);
                    }
                }else{
                    enPassent = null;
                }
            }else{
                enPassent = null;
            }

            men.setPosition(dest);
            men.setMoves(men.getMoves() + 1);

            if(men instanceof King){
                this.rochade.put(currentPlayer, true);
            }

        }else{
            throw new WrongMoveException();
        }

        currentPlayer = currentPlayer.equals(COLOR.BLACK) ? COLOR.WHITE : COLOR.BLACK;
        round++;
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
            if(getChessmanByPosition(new Position(7,7)) != null && getChessmanByPosition(new Position(7,07)).getMoves() == 0 ){
                rochade += "K";
            }
            if(getChessmanByPosition(new Position(7,7)) != null && getChessmanByPosition(new Position(0,7)).getMoves() == 0 ){
                rochade += "Q";
            }
        }

        if(!this.rochade.get(COLOR.BLACK)){
            if(getChessmanByPosition(new Position(7,0)) != null && getChessmanByPosition(new Position(7,0)).getMoves() == 0 ){
                rochade += "k";
            }
            if(getChessmanByPosition(new Position(7,0)) != null && getChessmanByPosition(new Position(0,0)).getMoves() == 0 ){
                rochade += "q";
            }
        }

        if(rochade.equals("")){
            rochade += "-";
        }
        FEN.append(rochade).append(" ");

        FEN.append(enPassent != null? enPassent : "-").append(" ");
        FEN.append(halfMoveClock).append(" ").append(round);

        return FEN.toString();
    }

    public List<Chessman> getChessmanList(COLOR color){
        if(color == null) return new ArrayList<>(chessmanList);
        return chessmanList.stream().filter(chessman -> chessman.getColor() == color).collect(Collectors.toList());
    }

    public Chessman getChessmanByPosition(Position p){
        Optional<Chessman> opt = chessmanList.stream().filter(chessman -> chessman.getPosition().equals(p)).findFirst();
        return opt.orElse(null);
    }
}
