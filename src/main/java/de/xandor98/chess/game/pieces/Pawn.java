package de.xandor98.chess.game.pieces;

import java.util.ArrayList;
import java.util.List;

import de.xandor98.chess.game.Board;
import de.xandor98.chess.game.Position;
import de.xandor98.chess.generated.COLOR;

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
                if(current.getY() == 6){
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
                if(b.getChessmanByPosition(new Position(current.getX() + 1, current.getY() + 1)) != null
                        && b.getChessmanByPosition(new Position(current.getX() + 1, current.getY() + 1)).getColor() == COLOR.WHITE){
                    positions.add(new Position(current.getX() + 1, current.getY() + 1));
                }

                if(b.getChessmanByPosition(new Position(current.getX() - 1, current.getY() + 1)) != null
                        && b.getChessmanByPosition(new Position(current.getX() - 1, current.getY() + 1)).getColor() == COLOR.WHITE){
                    positions.add(new Position(current.getX() - 1, current.getY() + 1));
                }
                break;
            case WHITE:
                if(b.getChessmanByPosition(new Position(current.getX() + 1, current.getY() - 1)) != null
                        && b.getChessmanByPosition(new Position(current.getX() + 1, current.getY() - 1)).getColor() == COLOR.BLACK){
                    positions.add(new Position(current.getX() + 1, current.getY() - 1));
                }

                if(b.getChessmanByPosition(new Position(current.getX() - 1, current.getY() - 1)) != null
                        && b.getChessmanByPosition(new Position(current.getX() - 1, current.getY() - 1)).getColor() == COLOR.BLACK){
                    positions.add(new Position(current.getX() - 1, current.getY() - 1));
                }
                break;
        }

        for (Position position : new ArrayList<>(positions)) {
            if(position.getX() < 0 || position.getX() > 7 || position.getY() < 0 || position.getY() > 7){
                positions.remove(position);
            }
        }

        return positions;
    }
}
