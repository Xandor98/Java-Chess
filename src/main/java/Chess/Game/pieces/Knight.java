package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Chessman {

    public Knight(Position position, COLOR color) {
        super(ChessmanType.KNIGHT, position, color);
    }

    @Override
    public List<Position> getMoves(Board b) {
        List<Position> positions = new ArrayList<>();

        Position currPos = this.getPosition();

        positions.add(new Position(currPos.getX() + 2, currPos.getY() + 1));
        positions.add(new Position(currPos.getX() + 2, currPos.getY() - 1));
        positions.add(new Position(currPos.getX() - 2, currPos.getY() + 1));
        positions.add(new Position(currPos.getX() - 2, currPos.getY() - 1));
        positions.add(new Position(currPos.getX() + 1, currPos.getY() + 2));
        positions.add(new Position(currPos.getX() - 1, currPos.getY() + 2));
        positions.add(new Position(currPos.getX() + 1, currPos.getY() - 2));
        positions.add(new Position(currPos.getX() - 1, currPos.getY() - 2));

        for (Position position : new ArrayList<>(positions)) {
            Chessman chessman;
            if((chessman = b.getChessmanByPosition(position)) != null && chessman.getColor() == this.getColor()){
                positions.remove(position);
            }
        }

        for (Position position : new ArrayList<>(positions)) {
            if(position.getX() < 0 || position.getX() > 7 || position.getY() < 0 || position.getY() > 7){
                positions.remove(position);
            }
        }

        return positions;
    }
}
