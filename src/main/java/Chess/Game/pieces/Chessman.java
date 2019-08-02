package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.*;
import Chess.Exceptions.WrongMoveException;
import Chess.misc.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class Chessman {

    private COLOR color;
    private PositionData pos;
    private ChessFigureType type;

    private boolean moved;
    protected boolean enPassantPossible = false;
    private int moves = 0;

    protected Chessman(ChessFigure figure){
        this(figure.getColor(), figure.getPos(), figure.getType(), figure.isMoved());
    }

    protected Chessman(COLOR color, PositionData pos, ChessFigureType type, boolean isMoved){
        this.color = color;
        this.pos = pos;
        this.moved = isMoved;
        this.type = type;
    }

    public abstract List<PositionData> getMovablePositions(Board board);

    public void performMove(PositionData data, Board board) throws WrongMoveException{
        if(getMovablePositions(board).stream().map(Position::new).anyMatch(position -> position.equals(new Position(data)))){
            Logger.info("Chessfigure moved from", new Position(this.pos), "to", new Position(data));
            for (Chessman chessman : new ArrayList<>(board.getChessmanList())) {
                if(new Position(chessman.getPos()).equals(new Position(data)) && chessman.getColor() != this.getColor()){
                    board.getChessmanList().remove(chessman);
                    Logger.info("Figure", this.color, this.type, "has kicked out", chessman.color, chessman.type);
                    break;
                }
            }
            if(this.type.equals(ChessFigureType.PAWN)){
                if(!this.moved && Math.abs(data.getY() - this.pos.getY()) == 2){
                    this.enPassantPossible = true;
                }else{
                    this.enPassantPossible = false;
                }
            }
            this.pos = data;
            moved = true;
            moves++;
            if(moves > 1){
                enPassantPossible = false;
            }
        }else{
            throw new WrongMoveException();
        }
    }

    public static Chessman readFigure(ChessFigure figure){
        switch (figure.getType()){
            case PAWN:
                return new Pawn(figure);
            case KING:
                return new King(figure);
            case QUEEN:
                return new Queen(figure);
            case ROOK:
                return new Rook(figure);
            case BISHOP:
                return new Bishop(figure);
            case KNIGHT:
                return new Knight(figure);
            default:
                return null;
        }
    }

    public COLOR getColor() {
        return color;
    }

    public PositionData getPos() {
        return pos;
    }

    public ChessFigureType getType() {
        return type;
    }

    public boolean isMoved() {
        return moved;
    }

    public ChessFigure getFigure(){
        ChessFigure figure = new ChessFigure();
        figure.setType(this.type);
        figure.setPos(this.pos);
        figure.setMoved(this.moved);
        figure.setColor(this.color);
        return figure;
    }
}
