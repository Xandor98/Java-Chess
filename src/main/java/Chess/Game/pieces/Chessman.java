package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.generated.PositionData;
import Chess.Exceptions.WrongMoveException;

import java.util.List;

public abstract class Chessman {

    private COLOR color;
    private PositionData pos;
    private ChessFigureType type;

    private boolean moved;

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

    public void performNormalMove(PositionData data, Board board) throws WrongMoveException{
        System.out.println(data);
        System.out.println(getMovablePositions(board));
        if(getMovablePositions(board).contains(data)){
            this.pos = data;
            moved = true;
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
