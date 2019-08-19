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

        positions.add(this.getPosition().add(2, 1));
        positions.add(this.getPosition().add(2, -1));
        positions.add(this.getPosition().add(-2, 1));
        positions.add(this.getPosition().add(-2, -1));
        positions.add(this.getPosition().add(1, 2));
        positions.add(this.getPosition().add(-1, 2));
        positions.add(this.getPosition().add(1, -2));
        positions.add(this.getPosition().add(-1, -2));

        for (Position position : new ArrayList<>(positions)) {
            Chessman chessman;
            if((chessman = b.getChessmanByPosition(position)) != null && chessman.getColor() == this.getColor()){
                positions.remove(position);
            }
        }

        return positions;
    }
}
