package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Chessman {

    public Bishop(Position position, COLOR color) {
        super(ChessmanType.BISHOP, position, color);
    }

    @Override
    public List<Position> getMoves(Board b) {
        List<Position> positions = new ArrayList<>();

        int x = this.getPosition().getX() + 1;
        int y = this.getPosition().getY() + 1;

        //RIGHT LOWER
        while(x < 8 && y < 8){
            Chessman man;
            if((man = b.getChessmanByPosition(new Position(x, y))) != null) {
                if (man.getColor() != this.getColor()) {
                    positions.add(new Position(x, y));
                }
                break;
            }
            positions.add(new Position(x,y));

            x++;
            y++;
        }

        x = this.getPosition().getX() + 1;
        y = this.getPosition().getY() - 1;

        //RIGHT UPPER
        while(x < 8 && y >= 0){
            Chessman man;
            if((man = b.getChessmanByPosition(new Position(x, y))) != null) {
                if (man.getColor() != this.getColor()) {
                    positions.add(new Position(x, y));
                }
                break;
            }
            positions.add(new Position(x,y));

            x++;
            y--;
        }

        x = this.getPosition().getX() - 1;
        y = this.getPosition().getY() + 1;

        //Left LOWER
        while(x >= 0 && y < 8){
            Chessman man;
            if((man = b.getChessmanByPosition(new Position(x, y))) != null) {
                if (man.getColor() != this.getColor()) {
                    positions.add(new Position(x, y));
                }
                break;
            }
            positions.add(new Position(x,y));

            x--;
            y++;
        }

        x = this.getPosition().getX() - 1;
        y = this.getPosition().getY() - 1;

        //LEFT UPPER
        while(x >= 0 && y >= 0){
            Chessman man;
            if((man = b.getChessmanByPosition(new Position(x, y))) != null) {
                if (man.getColor() != this.getColor()) {
                    positions.add(new Position(x, y));
                }
                break;
            }
            positions.add(new Position(x,y));

            x--;
            y--;
        }

        for (Position position : new ArrayList<>(positions)) {
            if(position.getX() < 0 || position.getX() > 7 || position.getY() < 0 || position.getY() > 7){
                positions.remove(position);
            }
        }

        return positions;
    }
}
