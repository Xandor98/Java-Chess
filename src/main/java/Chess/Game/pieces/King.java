package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.generated.PositionData;

import java.util.ArrayList;
import java.util.List;

public class King extends Chessman {

    protected King(ChessFigure figure) {
        this(figure.getColor(), figure.getPos(), figure.isMoved());
    }

    public King(COLOR color, PositionData data, boolean moved){
        super(color, data, ChessFigureType.KING, moved);
    }

    @Override
    public List<PositionData> getMovablePositions(Board board) {
        //TODO: Casteling Move
        List<PositionData> positionData = new ArrayList<>();

        positionData.add(new Position(this.getPos().getX() + 1, this.getPos().getY()).getData());
        positionData.add(new Position(this.getPos().getX() + 1, this.getPos().getY()).getData());
        positionData.add(new Position(this.getPos().getX() + 1, this.getPos().getY()).getData());
        positionData.add(new Position(this.getPos().getX(), this.getPos().getY()).getData());
        positionData.add(new Position(this.getPos().getX(), this.getPos().getY()).getData());
        positionData.add(new Position(this.getPos().getX(), this.getPos().getY()).getData());
        positionData.add(new Position(this.getPos().getX(), this.getPos().getY()).getData());
        positionData.add(new Position(this.getPos().getX(), this.getPos().getY()).getData());

        return null;
    }
}
