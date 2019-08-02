package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.generated.PositionData;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Chessman {

    protected Pawn(ChessFigure figure) {
        this(figure.getColor(), figure.getPos(), figure.isMoved());
    }

    public Pawn(COLOR color, PositionData data, boolean moved){
        super(color, data, ChessFigureType.PAWN, moved);
    }

    @Override
    public List<PositionData> getMovablePositions(Board board) {
        List<PositionData> positionData = new ArrayList<>();

        PositionData currPos = this.getPos();

        switch (this.getColor()){
            case BLACK:
                positionData.add(new PositionData(){{
                    this.setX(currPos.getX());
                    this.setY(currPos.getY() + 1);
                }});

                if(!this.isMoved()){
                    positionData.add(new PositionData(){{
                        this.setX(currPos.getX());
                        this.setY(currPos.getY() + 2);
                    }});
                }
                break;
            case WHITE:
                positionData.add(new PositionData(){{
                    this.setX(currPos.getX());
                    this.setY(currPos.getY() - 1);
                }});

                if(!this.isMoved()){
                    positionData.add(new PositionData(){{
                        this.setX(currPos.getX());
                        this.setY(currPos.getY() - 2);
                    }});
                }
                break;
        }

        for (Chessman chessman : board.getChessmanList()) {
            for (PositionData positionDatum : new ArrayList<>(positionData)) {
                if(chessman.getPos().getX() == positionDatum.getX() && chessman.getPos().getY() == positionDatum.getY()){
                    positionData.remove(positionDatum);
                }
            }
            int x1 = this.getPos().getX() + 1;
            int x2 = this.getPos().getX() - 1;
            switch (this.getColor()){
                case BLACK:
                    if(chessman.getColor() != this.getColor()) {
                        if (new Position(x1, this.getPos().getY() + 1).equals(new Position(chessman.getPos()))) {
                            positionData.add(chessman.getPos());
                        }
                        if (new Position(x2, this.getPos().getY() + 1).equals(new Position(chessman.getPos()))) {
                            positionData.add(chessman.getPos());
                        }
                    }
                    break;
                case WHITE:
                    if(chessman.getColor() != this.getColor()) {
                        if (new Position(x1, this.getPos().getY() - 1).equals(new Position(chessman.getPos()))) {
                            positionData.add(chessman.getPos());
                        }
                        if (new Position(x2, this.getPos().getY() - 1).equals(new Position(chessman.getPos()))) {
                            positionData.add(chessman.getPos());
                        }
                    }
                    break;
            }
            if(chessman.getType().equals(ChessFigureType.PAWN)){
                switch (this.getColor()){
                    case BLACK:
                        if(new Position(chessman.getPos()).equals(new Position(x1, this.getPos().getY())) && chessman.enPassantPossible){
                            positionData.add(new Position(x1, this.getPos().getY() + 1).getData());
                        }
                        if(new Position(chessman.getPos()).equals(new Position(x2, this.getPos().getY()))&& chessman.enPassantPossible){
                            positionData.add(new Position(x2, this.getPos().getY() + 1).getData());
                        }
                        break;
                    case WHITE:
                        if(new Position(chessman.getPos()).equals(new Position(x1, this.getPos().getY())) && chessman.enPassantPossible){
                            positionData.add(new Position(x1, this.getPos().getY() - 1).getData());
                        }
                        if(new Position(chessman.getPos()).equals(new Position(x2, this.getPos().getY()))&& chessman.enPassantPossible){
                            positionData.add(new Position(x2, this.getPos().getY() - 1).getData());
                        }
                        break;
                }
            }
        }

        return positionData;
    }
}
