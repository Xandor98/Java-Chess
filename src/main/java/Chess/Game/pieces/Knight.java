package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.generated.PositionData;

import java.util.List;

public class Knight extends Chessman {

    protected Knight(ChessFigure figure) {
        this(figure.getColor(), figure.getPos(), figure.isMoved());
    }

    public Knight(COLOR color, PositionData data, boolean moved){
        super(color, data, ChessFigureType.KNIGHT, moved);
    }

    @Override
    public List<PositionData> getMovablePositions(Board board) {
        return null;
    }
}
