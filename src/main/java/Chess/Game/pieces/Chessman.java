package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.PositionData;

import java.util.List;

public abstract class Chessman {

    private COLOR color;
    private PositionData pos;

    private boolean moved;

    protected Chessman(ChessFigure figure){
        this(figure.getColor(), figure.getPos(), figure.isMoved());
    }

    protected Chessman(COLOR color, PositionData pos, boolean isMoved){
        this.color = color;
        this.pos = pos;
        this.moved = isMoved;
    }

    public abstract List<PositionData> getMovablePositions(Board board);

    public void Move(PositionData data, Board board){
        //CHECK if move is Valid
        if(getMovablePositions(board).contains(data)){
            this.pos = data;
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

    public boolean isMoved() {
        return moved;
    }
}
