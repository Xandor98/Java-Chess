package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;

import java.util.List;
import java.util.Objects;

public abstract class Chessman {

    private Position position;
    private COLOR color;
    private ChessmanType type;

    public Chessman(ChessmanType type, Position position, COLOR color) {
        this.position = position;
        this.color = color;
        this.type = type;
    }

    public abstract List<Position> getMoves(Board b);

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
