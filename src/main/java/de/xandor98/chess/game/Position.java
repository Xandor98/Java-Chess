package de.xandor98.chess.game;

import java.util.Objects;

public class Position {

    private int x;
    private int y;

    public Position(String s){
        if(s.length() != 2){
            x = -1;
            y = -1;
        }
        this.x = s.charAt(0) - 'a';
        this.y = (8 - Integer.parseInt(String.valueOf(s.charAt(1))));
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(){
        this.x = -1;
        this.y = -1;
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
        return "" + (char)('a' + this.getX()) + (8 - this.getY());
    }
}
