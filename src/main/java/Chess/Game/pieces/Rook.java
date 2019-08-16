package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.generated.PositionData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rook extends Chessman {

    protected Rook(ChessFigure figure) {
        this(figure.getColor(), figure.getPos(), figure.isMoved());
    }

    public Rook(COLOR color, PositionData data, boolean moved){
        super(color, data, ChessFigureType.ROOK, moved);
    }

    @Override
    public List<PositionData> getMovablePositions(Board board) {
        List<Position> positionData = new ArrayList<>();

        Position pos = new Position(this.getPos());

        int X = pos.getX() + 1;
        while(X < board.getWidth()){
            if(board.getFigureByPosition(X, pos.getY()) != null){
                if(!board.getFigureByPosition(X, pos.getY()).getColor().equals(this.getColor())){
                    positionData.add(new Position(X, pos.getY()));
                }
                break;
            }
            positionData.add(new Position(X, pos.getY()));
            X++;
        }

        X = pos.getX() - 1;
        while(X >= 0){
            if(board.getFigureByPosition(X, pos.getY()) != null){
                if(!board.getFigureByPosition(X, pos.getY()).getColor().equals(this.getColor())){
                    positionData.add(new Position(X, pos.getY()));
                }
                break;
            }
            positionData.add(new Position(X, pos.getY()));
            X--;
        }

        int Y = pos.getY() + 1;
        while(Y < board.getHeight()){
            if(board.getFigureByPosition(pos.getX(), Y) != null){
                if(!board.getFigureByPosition(pos.getX(), Y).getColor().equals(this.getColor())){
                    positionData.add(new Position(pos.getX(), Y));
                }
                break;
            }
            positionData.add(new Position(pos.getX(), Y));
            Y++;
        }

        Y = pos.getY() - 1;
        while(Y >= 0){
            if(board.getFigureByPosition(pos.getX(), Y) != null){
                if(!board.getFigureByPosition(pos.getX(), Y).getColor().equals(this.getColor())){
                    positionData.add(new Position(pos.getX(), Y));
                }
                break;
            }
            positionData.add(new Position(pos.getX(), Y));
            Y--;
        }

        return positionData.stream().map(Position::getData).collect(Collectors.toList());
    }
}
