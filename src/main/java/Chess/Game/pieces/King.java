package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.generated.PositionData;

import java.util.ArrayList;
import java.util.List;

public class King extends Chessman {

    protected King(ChessFigure figure) {
        this(figure.getColor(), figure.getPos(), figure.isMoved());
    }

    public King(COLOR color, PositionData data, boolean moved){
        super(color, data, ChessFigureType.KING, moved);
    }

    @Override
    public List<PositionData> getMovablePositions(Board board) {
        //TODO: Casteling Move
        List<PositionData> positionData = new ArrayList<>();

        positionData.add(new Position(this.getPos().getX() + 1, this.getPos().getY() + 1).getData());
        positionData.add(new Position(this.getPos().getX() + 1, this.getPos().getY()).getData());
        positionData.add(new Position(this.getPos().getX() + 1, this.getPos().getY() - 1).getData());
        positionData.add(new Position(this.getPos().getX() - 1, this.getPos().getY() + 1).getData());
        positionData.add(new Position(this.getPos().getX() - 1, this.getPos().getY()).getData());
        positionData.add(new Position(this.getPos().getX() - 1, this.getPos().getY() - 1).getData());
        positionData.add(new Position(this.getPos().getX(), this.getPos().getY() + 1).getData());
        positionData.add(new Position(this.getPos().getX(), this.getPos().getY() - 1).getData());

        board.getChessmanList().stream().filter(chessman -> chessman.getColor() == this.getColor()).forEach(chessman -> {
            for (PositionData positionDatum : new ArrayList<>(positionData)) {
                if(new Position(positionDatum).equals(new Position(chessman.getPos()))){
                    positionData.remove(positionDatum);
                }
            }
        });

        board.getChessmanList().stream().filter(chessman -> chessman.getColor() == this.getColor()).forEach(chessman -> {
            new ArrayList<>(positionData).stream().map(Position::new).forEach(position -> {
                if(chessman.getMovablePositions(board).stream().map(Position::new).anyMatch(position1 -> position1.equals(position))){
                    positionData.remove(position.getData());
                }
            });
        });

        if(!this.isMoved()){
            Chessman c1 = null;
            Chessman c2 = null;
            switch (this.getColor()){
                case BLACK:
                    c1 = board.getFigureByPosition(0,0);
                    c2 = board.getFigureByPosition(7,0);
                    if(c1 != null && !c1.isMoved() && c1.getType().equals(ChessFigureType.ROOK)){
                        boolean canMove = true;
                        for (int x = 1; x < this.getPos().getX(); x++){
                            if(board.getFigureByPosition(x, 0) != null){
                                canMove = false;
                                break;
                            }
                        }
                        if(canMove){
                            positionData.add(new Position(this.getPos().getX() - 2, 0).getData());
                        }
                    }

                    if(c2 != null && !c2.isMoved() && c2.getType().equals(ChessFigureType.ROOK)){
                        boolean canMove = true;
                        for (int x = this.getPos().getX() + 1; x < board.getWidth(); x++){
                            if(board.getFigureByPosition(x, 0) != null){
                                canMove = false;
                                break;
                            }
                        }
                        if(canMove){
                            positionData.add(new Position(this.getPos().getX() + 2, 0).getData());
                        }
                    }
                    break;
                case WHITE:
                    c1 = board.getFigureByPosition(0,7);
                    c2 = board.getFigureByPosition(7,7);
                    if(c1 != null && !c1.isMoved() && c1.getType().equals(ChessFigureType.ROOK)){
                        boolean canMove = true;
                        for (int x = 1; x < this.getPos().getX(); x++){
                            if(board.getFigureByPosition(x, 7) != null){
                                canMove = false;
                                break;
                            }
                        }
                        if(canMove){
                            positionData.add(new Position(this.getPos().getX() - 2, 7).getData());
                        }
                    }

                    if(c2 != null && !c2.isMoved() && c2.getType().equals(ChessFigureType.ROOK)){
                        boolean canMove = true;
                        for (int x = this.getPos().getX() + 1; x < board.getWidth(); x++){
                            if(board.getFigureByPosition(x, 7) != null){
                                canMove = false;
                                break;
                            }
                        }
                        if(canMove){
                            positionData.add(new Position(this.getPos().getX() + 2, 7).getData());
                        }
                    }
                    break;
            }
        }

        return positionData;
    }
}
