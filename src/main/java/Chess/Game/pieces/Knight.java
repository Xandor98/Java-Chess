package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.generated.PositionData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Knight extends Chessman {

    protected Knight(ChessFigure figure) {
        this(figure.getColor(), figure.getPos(), figure.isMoved());
    }

    public Knight(COLOR color, PositionData data, boolean moved){
        super(color, data, ChessFigureType.KNIGHT, moved);
    }

    @Override
    public List<PositionData> getMovablePositions(Board board) {
        List<Position> positionData = new ArrayList<>();

        positionData.add(new Position(this.getPos().getX() + 2, this.getPos().getY() + 1));
        positionData.add(new Position(this.getPos().getX() + 2, this.getPos().getY() - 1));
        positionData.add(new Position(this.getPos().getX() - 2, this.getPos().getY() + 1));
        positionData.add(new Position(this.getPos().getX() + 1, this.getPos().getY() + 2));
        positionData.add(new Position(this.getPos().getX() - 1, this.getPos().getY() + 2));
        positionData.add(new Position(this.getPos().getX() - 2, this.getPos().getY() - 1));
        positionData.add(new Position(this.getPos().getX() + 1, this.getPos().getY() - 2));
        positionData.add(new Position(this.getPos().getX() - 1, this.getPos().getY() - 2));

        for(Position pos : new ArrayList<>(positionData)){
            if(board.getFigureByPosition(pos.getData()) != null && board.getFigureByPosition(pos.getData()).getColor().equals(this.getColor())){
                positionData.remove(pos);
            }else if(pos.getX() < 0 || pos.getY() < 0 ||pos.getX() >= board.getWidth() || pos.getY() >= board.getHeight()){
                positionData.remove(pos);
            }
        }

        return positionData.stream().map(Position::getData).collect(Collectors.toList());
    }
}
