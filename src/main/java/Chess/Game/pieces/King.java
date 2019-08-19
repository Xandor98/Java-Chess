package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.AcceptMessage;
import Chess.generated.COLOR;

import java.util.ArrayList;
import java.util.List;

public class King extends Chessman {

    public King(Position position, COLOR color) {
        super(ChessmanType.KING, position, color);
    }

    @Override
    public List<Position> getMoves(Board b) {
        List<Position> positions = new ArrayList<>();

        positions.add(this.getPosition().add(1,-1));
        positions.add(this.getPosition().add(1,0));
        positions.add(this.getPosition().add(1,1));
        positions.add(this.getPosition().add(0,1));
        positions.add(this.getPosition().add(-1,1));
        positions.add(this.getPosition().add(-1,0));
        positions.add(this.getPosition().add(-1,-1));
        positions.add(this.getPosition().add(0,-1));

        for (Position position : new ArrayList<>(positions)) {
            Chessman chessman;
            if((chessman = b.getChessmanByPosition(position)) != null && chessman.getColor() == this.getColor()){
                positions.remove(position);
            }
        }

        for (Chessman chessman : b.getChessmanList(this.getColor().equals(COLOR.WHITE) ? COLOR.BLACK : COLOR.WHITE)) {
            if(chessman instanceof King){

                List<Position> kingPos = new ArrayList<>();

                kingPos.add(chessman.getPosition().add(1,-1));
                kingPos.add(chessman.getPosition().add(1,0));
                kingPos.add(chessman.getPosition().add(1,1));
                kingPos.add(chessman.getPosition().add(0,1));
                kingPos.add(chessman.getPosition().add(-1,1));
                kingPos.add(chessman.getPosition().add(-1,0));
                kingPos.add(chessman.getPosition().add(-1,-1));
                kingPos.add(chessman.getPosition().add(0,-1));

                for (Position position : new ArrayList<>(positions)) {
                    if(kingPos.contains(position)){
                        positions.remove(position);
                    }
                }

                continue;
            }
            for (Position position : new ArrayList<>(positions)) {
                if(chessman.getMoves(b).contains(position)){
                    positions.remove(position);
                }
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
