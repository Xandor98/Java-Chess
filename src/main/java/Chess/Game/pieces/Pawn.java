package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Chessman {

    public Pawn(Position position, COLOR color) {
        super(ChessmanType.PAWN, position, color);
    }

    @Override
    public List<Position> getMoves(Board b) {
        List<Position> positions = new ArrayList<>();

        Position current = this.getPosition();

        switch (this.getColor()){
            case BLACK:
                positions.add(new Position(current.getX(), current.getY() + 1));
                if(current.getY() == 1){
                    positions.add(new Position(current.getX(), current.getY() + 2));
                }
                break;
            case WHITE:
                positions.add(new Position(current.getX(), current.getY() - 1));
                if(current.getY() == 7){
                    positions.add(new Position(current.getX(), current.getY() - 2));
                }
                break;
        }

        for (Position position : new ArrayList<>(positions)) {
            if(b.getChessmanByPosition(position) != null){
                positions.remove(position);
            }
        }

        switch (this.getColor()){
            case BLACK:
                if(b.getChessmanByPosition(new Position(current.getX() + 1, current.getY() + 1)) != null){
                    positions.add(new Position(current.getX() + 1, current.getY() + 1));
                }

                if(b.getChessmanByPosition(new Position(current.getX() - 1, current.getY() + 1)) != null){
                    positions.add(new Position(current.getX() + 1, current.getY() + 1));
                }
                break;
            case WHITE:
                if(b.getChessmanByPosition(new Position(current.getX() + 1, current.getY() - 1)) != null){
                    positions.add(new Position(current.getX() + 1, current.getY() + 1));
                }

                if(b.getChessmanByPosition(new Position(current.getX() - 1, current.getY() - 1)) != null){
                    positions.add(new Position(current.getX() + 1, current.getY() - 1));
                }
                break;
        }

        return positions;
    }
}
