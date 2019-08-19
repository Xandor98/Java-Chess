package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Chessman {

    public Rook(Position position, COLOR color) {
        super(ChessmanType.ROOK, position, color);
    }

    @Override
    public List<Position> getMoves(Board b) {
        List<Position> positions = new ArrayList<>();

        Position currPos = this.getPosition();

        int x = currPos.getX() + 1;
        int y = currPos.getY();

        //RIGHT
        while(x < 8){
            Chessman man;
            if((man = b.getChessmanByPosition(new Position(x,y))) != null){
                if(man.getColor() != this.getColor()){
                    positions.add(new Position(x,y));
                }
                break;
            }
            positions.add(new Position(x,y));
            x++;
        }

        x = currPos.getX() - 1;
        //LEFT
        while(x >= 0){
            Chessman man;
            if((man = b.getChessmanByPosition(new Position(x,y))) != null){
                if(man.getColor() != this.getColor()){
                    positions.add(new Position(x,y));
                }
                break;
            }
            positions.add(new Position(x,y));
            x--;
        }

        x = currPos.getX();
        y = currPos.getY() + 1;
        //TOP
        while(y < 8){
            Chessman man;
            if((man = b.getChessmanByPosition(new Position(x,y))) != null){
                if(man.getColor() != this.getColor()){
                    positions.add(new Position(x,y));
                }
                break;
            }
            positions.add(new Position(x,y));
            y++;
        }

        x = currPos.getX();
        y = currPos.getY() - 1;
        //RIGHT
        while(y >= 0){
            Chessman man;
            if((man = b.getChessmanByPosition(new Position(x,y))) != null){
                if(man.getColor() != this.getColor()){
                    positions.add(new Position(x,y));
                }
                break;
            }
            positions.add(new Position(x,y));
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
