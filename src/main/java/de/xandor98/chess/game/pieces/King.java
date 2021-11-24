package de.xandor98.chess.game.pieces;

import java.util.ArrayList;
import java.util.List;

import de.xandor98.chess.game.Board;
import de.xandor98.chess.game.Position;
import de.xandor98.chess.generated.COLOR;

public class King extends Chessman {

    public King(Position position, COLOR color) {
        super(ChessmanType.KING, position, color);
    }

    @Override
    public List<Position> getMoves(Board b) {
        List<Position> positions = new ArrayList<>();

        Position currPos = this.getPosition();

        positions.add(new Position(currPos.getX() + 1, currPos.getY() - 1));
        positions.add(new Position(currPos.getX() + 1, currPos.getY()));
        positions.add(new Position(currPos.getX() + 1, currPos.getY() + 1));
        positions.add(new Position(currPos.getX(), currPos.getY() + 1));
        positions.add(new Position(currPos.getX() - 1, currPos.getY() + 1));
        positions.add(new Position(currPos.getX() - 1, currPos.getY()));
        positions.add(new Position(currPos.getX() - 1, currPos.getY() - 1));
        positions.add(new Position(currPos.getX(), currPos.getY() - 1));

        for (Position position : new ArrayList<>(positions)) {
            Chessman chessman;
            if((chessman = b.getChessmanByPosition(position)) != null && chessman.getColor() == this.getColor()){
                positions.remove(position);
            }
        }

        for (Chessman chessman : b.getChessmanList(this.getColor().equals(COLOR.WHITE) ? COLOR.BLACK : COLOR.WHITE)) {
            if(chessman instanceof King){

                List<Position> kingPos = new ArrayList<>();

                Position currentPos = chessman.getPosition();

                kingPos.add(new Position(currentPos.getX() + 1, currentPos.getY() - 1));
                kingPos.add(new Position(currentPos.getX() + 1, currentPos.getY()));
                kingPos.add(new Position(currentPos.getX() + 1, currentPos.getY() + 1));
                kingPos.add(new Position(currentPos.getX(), currentPos.getY() + 1));
                kingPos.add(new Position(currentPos.getX() - 1, currentPos.getY() + 1));
                kingPos.add(new Position(currentPos.getX() - 1, currentPos.getY()));
                kingPos.add(new Position(currentPos.getX() - 1, currentPos.getY() - 1));
                kingPos.add(new Position(currentPos.getX(), currentPos.getY() - 1));

                for (Position position : new ArrayList<>(positions)) {
                    if(kingPos.contains(position)){
                        positions.remove(position);
                    }
                }

                continue;
            }
            if(chessman instanceof Pawn){

                List<Position> pawnPos = new ArrayList<>();

                switch (chessman.getColor()){
                    case BLACK:
                        pawnPos.add(new Position(chessman.getPosition().getX() + 1, chessman.getPosition().getY() + 1));
                        pawnPos.add(new Position(chessman.getPosition().getX() - 1, chessman.getPosition().getY() + 1));
                        break;
                    case WHITE:
                        pawnPos.add(new Position(chessman.getPosition().getX() + 1, chessman.getPosition().getY() - 1));
                        pawnPos.add(new Position(chessman.getPosition().getX() - 1, chessman.getPosition().getY() - 1));
                        break;
                }

                for (Position position : new ArrayList<>(positions)) {
                    if(pawnPos.contains(position)){
                        positions.remove(position);
                    }
                }

                continue;
            }
            for (Position position : new ArrayList<>(positions)) {
                if(chessman.getMoves(b).contains(position)){
                    positions.remove(position);
                }
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
