package Chess.Game;

import Chess.generated.PositionData;

import java.util.Objects;

public class Position {

    private int x;
    private int y;

    public Position(PositionData data){
        this.x = data.getX();
        this.y = data.getY();
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(){
        this.x = -1;
        this.y = -1;
    }

    public PositionData getData(){
        int X = this.x;
        int Y = this.y;
        return new PositionData(){{this.setX(X); this.setY(Y);}};
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return getX() == position.getX() &&
                getY() == position.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
