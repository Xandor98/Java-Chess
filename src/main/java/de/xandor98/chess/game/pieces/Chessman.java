package de.xandor98.chess.game.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.xandor98.chess.game.Board;
import de.xandor98.chess.game.Position;
import de.xandor98.chess.generated.COLOR;

public abstract class Chessman {

    private Position position;
    private COLOR color;
    private ChessmanType type;

    private int moveCounter = 0;

    public Chessman(ChessmanType type, Position position, COLOR color) {
        this.position = position;
        this.color = color;
        this.type = type;
    }

    public abstract List<Position> getMoves(Board b);

    public List<Position> getCheckMoves(Board b, List<Chessman> checkChessmanList){
        List<Position> positions = getMoves(b);

        List<Position> ret = new ArrayList<>();

        if(this instanceof King){
            for (Position position1 : positions) {
                Board fake = new Board(b);

                if(fake.getChessmanByPosition(position1) != null){
                    fake.getChessmanList(null).remove(fake.getChessmanByPosition(position1));
                }
                fake.getChessmanByPosition(this.position).setPosition(position1);


                if (fake.inCheck().size() == 0) {
                    ret.add(position1);
                }
            }
        }else{
            for (Chessman chessman : checkChessmanList) {
                for (Position pos : positions) {
                    if (chessman.getMoves(b).contains(pos) || chessman.getPosition().equals(pos)) {
                        Board fake = new Board(b);

                        fake.justRemove(pos);
                        fake.getChessmanByPosition(this.position).setPosition(pos);


                        if (fake.inCheck().size() == 0) {
                            ret.add(pos);
                        }
                    }
                }
            }

        }
        return ret;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public ChessmanType getType() {
        return type;
    }

    public void setType(ChessmanType type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chessman chessman = (Chessman) o;
        return Objects.equals(getPosition(), chessman.getPosition()) &&
                getColor() == chessman.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getColor());
    }

    public enum ChessmanType{
        PAWN,KNIGHT,KING,QUEEN,BISHOP,ROOK
    }
}
