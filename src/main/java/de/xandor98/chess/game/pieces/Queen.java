package de.xandor98.chess.game.pieces;

import java.util.ArrayList;
import java.util.List;

import de.xandor98.chess.game.Board;
import de.xandor98.chess.game.Position;
import de.xandor98.chess.generated.COLOR;

public class Queen extends Chessman {

    public Queen(Position position, COLOR color) {
        super(ChessmanType.QUEEN, position, color);
    }

    @Override
    public List<Position> getMoves(Board b) {
        List<Position> positions = new ArrayList<>();

        //BISHOP MOVES
        {
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
        }

        //ROOK MOVES
        {
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
        }

        for (Position position : new ArrayList<>(positions)) {
            if(position.getX() < 0 || position.getX() > 7 || position.getY() < 0 || position.getY() > 7){
                positions.remove(position);
            }
        }

        return positions;
    }
}
