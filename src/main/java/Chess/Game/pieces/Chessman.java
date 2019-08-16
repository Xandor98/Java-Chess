package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.*;
import Chess.Exceptions.WrongMoveException;
import Chess.misc.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Chessman {

    private COLOR color;
    private PositionData pos;
    private ChessFigureType type;

    private boolean moved;
    boolean enPassantPossible = false;
    private int moves = 0;

    protected Chessman(ChessFigure figure){
        this(figure.getColor(), figure.getPos(), figure.getType(), figure.isMoved());
    }

    protected Chessman(COLOR color, PositionData pos, ChessFigureType type, boolean isMoved){
        this.color = color;
        this.pos = pos;
        this.moved = isMoved;
        this.type = type;
    }

    public abstract List<PositionData> getMovablePositions(Board board);

    public List<PositionData> getMovableChessPositions(Board board, List<Chessman> chessmen){
        List<Position> positionData = getMovablePositions(board).stream().map(Position::new).collect(Collectors.toList());

        List<Position> allChessPositions = new ArrayList<>();
        chessmen.forEach(chessman -> {
            allChessPositions.addAll(chessman.getMovablePositions(board).stream().map(Position::new)
                    .filter(position -> board.getFigureByPosition(position.getData()) == null).collect(Collectors.toList()));
        });

        List<Position> ret = new ArrayList<>();
        if(!this.type.equals(ChessFigureType.KING)) {
            for (Position pos : positionData) {
                if (allChessPositions.contains(pos)) {
                    Board b = board.performFakeMove(new MoveMessage() {{
                        this.setFigure(Chessman.this.getFigure());
                        this.setDestinationPos(pos.getData());
                    }}).getLeft();

                    if (b.inChess(this.color).size() == 0) {
                        ret.add(pos);
                    }
                }
            }
        }else{
            ret.addAll(positionData);
        }

        return ret.stream().map(Position::getData).collect(Collectors.toList());
    }

    public void performMove(PositionData data, Board board) throws WrongMoveException{
        List<Chessman> chessmanList = board.inChess(this.color);

        if(chessmanList.size() > 0){
            Logger.debug("Player", this.color, "is in Chess");
            if(getMovableChessPositions(board, chessmanList).stream().map(Position::new).anyMatch(position -> position.equals(new Position(data)))){
                Logger.info(this.getColor(), this.getType(), "prevented Chess");
                Logger.info(this.getColor(), this.getType(), "moved from", new Position(this.pos), "to", new Position(data));
            }else {
                throw new WrongMoveException();
            }
        }else if(getMovablePositions(board).stream().map(Position::new).anyMatch(position -> position.equals(new Position(data)))){
            Logger.info(this.getColor(), this.getType(), "moved from", new Position(this.pos), "to", new Position(data));
        }else{
            throw new WrongMoveException();
        }

        for (Chessman chessman : new ArrayList<>(board.getChessmanList())) {
            if(new Position(chessman.getPos()).equals(new Position(data)) && chessman.getColor() != this.getColor()){
                board.getChessmanList().remove(chessman);
                Logger.info(this.getColor(), this.getType(), this.color, this.type, "has kicked out", chessman.color, chessman.type);
                break;
            }
        }
        if(this.type.equals(ChessFigureType.PAWN)){
            this.enPassantPossible = !this.moved && Math.abs(data.getY() - this.pos.getY()) == 2;
        }

        if(this.type.equals(ChessFigureType.KING)){
            if(Math.abs(this.pos.getX() - data.getX()) == 2){
                switch (this.color){
                    case BLACK:
                        if(data.getX() < this.pos.getX()){
                            board.getFigureByPosition(0,0).pos = new Position(data.getX() + 1, 0).getData();
                        }else {
                            board.getFigureByPosition(7,0).pos = new Position(data.getX() - 1, 0).getData();
                        }
                        break;
                    case WHITE:
                        if(data.getX() < this.pos.getX()){
                            board.getFigureByPosition(0,7).pos = new Position(data.getX() + 1, 7).getData();
                        }else {
                            board.getFigureByPosition(7,7).pos = new Position(data.getX() - 1, 7).getData();
                        }
                        break;
                }
            }
        }

        this.pos = data;
        moved = true;
        moves++;
        if(moves > 1){
            enPassantPossible = false;
        }
    }

    public static Chessman readFigure(ChessFigure figure){
        switch (figure.getType()){
            case PAWN:
                return new Pawn(figure);
            case KING:
                return new King(figure);
            case QUEEN:
                return new Queen(figure);
            case ROOK:
                return new Rook(figure);
            case BISHOP:
                return new Bishop(figure);
            case KNIGHT:
                return new Knight(figure);
            default:
                return null;
        }
    }

    public COLOR getColor() {
        return color;
    }

    public PositionData getPos() {
        return pos;
    }

    public ChessFigureType getType() {
        return type;
    }

    boolean isMoved() {
        return moved;
    }

    public ChessFigure getFigure(){
        ChessFigure figure = new ChessFigure();
        figure.setType(this.type);
        figure.setPos(this.pos);
        figure.setMoved(this.moved);
        figure.setColor(this.color);
        return figure;
    }

    @Override
    public String toString() {
        return "Chessman{" +
                "color=" + color +
                ", pos=" + new Position(pos) +
                ", type=" + type +
                '}';
    }
}
