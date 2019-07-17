package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.generated.PositionData;

import java.util.List;

public class Rook extends Chessman {

    protected Rook(ChessFigure figure) {
        this(figure.getColor(), figure.getPos(), figure.isMoved());
    }

    public Rook(COLOR color, PositionData data, boolean moved){
        super(color, data, ChessFigureType.ROOK, moved);
    }

    @Override
    public List<PositionData> getMovablePositions(Board board) {
        return null;
    }
}
