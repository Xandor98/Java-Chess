package Chess.Game;

import Chess.Exceptions.NoSuchFigureException;
import Chess.Exceptions.WrongMoveException;
import Chess.Game.pieces.*;
import Chess.generated.*;
import Chess.misc.Logger;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    private List<Chessman> chessmanList = new ArrayList<>();

    private int width;
    private int height;

    public Board(){
        this.width = 8;
        this.height = 8;

        initDefault();

    }

    public void changeBoard(List<Chessman> figures){
        this.chessmanList.clear();
        this.chessmanList.addAll(figures);
    }

    private void initDefault(){
        //PAWNS
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(0); this.setY(1);}}, false));
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(1); this.setY(1);}}, false));
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(2); this.setY(1);}}, false));
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(3); this.setY(1);}}, false));
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(4); this.setY(1);}}, false));
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(5); this.setY(1);}}, false));
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(6); this.setY(1);}}, false));
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(7); this.setY(1);}}, false));

        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(0); this.setY(6);}}, false));
        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(1); this.setY(6);}}, false));
        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(2); this.setY(6);}}, false));
        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(3); this.setY(6);}}, false));
        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(4); this.setY(6);}}, false));
        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(5); this.setY(6);}}, false));
        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(6); this.setY(6);}}, false));
        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(7); this.setY(6);}}, false));

        //KING
        this.chessmanList.add(new King(COLOR.BLACK, new PositionData(){{this.setX(4); this.setY(0);}}, false));
        this.chessmanList.add(new King(COLOR.WHITE, new PositionData(){{this.setX(4); this.setY(7);}}, false));

        //QUEEN
        this.chessmanList.add(new Queen(COLOR.BLACK, new PositionData(){{this.setX(3); this.setY(0);}}, false));
        this.chessmanList.add(new Queen(COLOR.WHITE, new PositionData(){{this.setX(3); this.setY(7);}}, false));

        //ROOKs
        this.chessmanList.add(new Rook(COLOR.BLACK, new PositionData(){{this.setX(0); this.setY(0);}}, false));
        this.chessmanList.add(new Rook(COLOR.BLACK, new PositionData(){{this.setX(7); this.setY(0);}}, false));

        this.chessmanList.add(new Rook(COLOR.WHITE, new PositionData(){{this.setX(0); this.setY(7);}}, false));
        this.chessmanList.add(new Rook(COLOR.WHITE, new PositionData(){{this.setX(7); this.setY(7);}}, false));

        //BISHOPS
        this.chessmanList.add(new Bishop(COLOR.BLACK, new PositionData(){{this.setX(2); this.setY(0);}}, false));
        this.chessmanList.add(new Bishop(COLOR.BLACK, new PositionData(){{this.setX(5); this.setY(0);}}, false));

        this.chessmanList.add(new Bishop(COLOR.WHITE, new PositionData(){{this.setX(2); this.setY(7);}}, false));
        this.chessmanList.add(new Bishop(COLOR.WHITE, new PositionData(){{this.setX(5); this.setY(7);}}, false));

        //KNIGHT
        this.chessmanList.add(new Knight(COLOR.BLACK, new PositionData(){{this.setX(1); this.setY(0);}}, false));
        this.chessmanList.add(new Knight(COLOR.BLACK, new PositionData(){{this.setX(6); this.setY(0);}}, false));

        this.chessmanList.add(new Knight(COLOR.WHITE, new PositionData(){{this.setX(1); this.setY(7);}}, false));
        this.chessmanList.add(new Knight(COLOR.WHITE, new PositionData(){{this.setX(6); this.setY(7);}}, false));
    }

    public Board(BoardData data){
        for (ChessFigure chessFigure : data.getFigure()) {
            Chessman man = Chessman.readFigure(chessFigure);
            if (man != null)
                chessmanList.add(man);
        }

        this.width = data.getWidth();
        this.height = data.getHeight();
    }

    public List<Chessman> getChessmanList() {
        return chessmanList;
    }

    /**
     *
     * @param message The {@link MoveMessage} that represent the current move to make
     * @return If it is possible to propose a wish
     * @throws NoSuchFigureException When the given Figure is not on the Board
     * @throws WrongMoveException When you cant move to the destination Port
     * @throws IndexOutOfBoundsException When the Figure or Destination out of the Field
     */
    public boolean makeMove(MoveMessage message) throws NoSuchFigureException, WrongMoveException, IndexOutOfBoundsException{
        PositionData chessFigure = message.getFigure().getPos();
        PositionData destinationPos = message.getDestinationPos();

        if(chessFigure.getX() < 0 || chessFigure.getY() < 0){
            throw new IndexOutOfBoundsException("Chess Figure Position is Below 0");
        }

        if(chessFigure.getX() >= this.width || chessFigure.getY() >= this.height){
            throw new IndexOutOfBoundsException("Chess Figure Position is Greater than given Width and Height");
        }

        if(destinationPos.getX() < 0 || destinationPos.getY() < 0){
            throw new IndexOutOfBoundsException("Destination Position is Below 0");
        }

        if(destinationPos.getX() >= this.width || destinationPos.getY() >= this.height){
            throw new IndexOutOfBoundsException("Destination Position is Greater than given Width and Height");
        }

        Chessman currentFigure = this.getFigureByPosition(chessFigure);

        currentFigure.performMove(destinationPos, this);

        return chessmanList.stream().anyMatch(chessman -> chessman.getType().equals(ChessFigureType.PAWN) && (chessman.getPos().getY() == 0 || chessman.getPos().getY() == this.getWidth() - 1));
    }

    public void proposeAWish(WishMessage wishMessage){
        if(chessmanList.stream().noneMatch(chessman -> chessman.getType().equals(ChessFigureType.PAWN) && (chessman.getPos().getY() == 0 || chessman.getPos().getY() == this.getWidth() - 1))){
            throw new NoSuchFigureException();
        }

        Chessman man = chessmanList.stream().filter(chessman -> chessman.getType().equals(ChessFigureType.PAWN) && (chessman.getPos().getY() == 0 || chessman.getPos().getY() == this.getWidth() - 1)).collect(Collectors.toList()).get(0);

        chessmanList.remove(man);
        ChessFigure nFig = new ChessFigure(){{
            this.setMoved(true);
            this.setType(wishMessage.getNewFigure());
            this.setColor(man.getColor());
            this.setPos(man.getPos());
        }};

        chessmanList.add(Chessman.readFigure(nFig));
    }

    public boolean inChess(COLOR color){
        Chessman king = chessmanList.stream().filter(chessman -> chessman.getColor() == color && chessman.getType().equals(ChessFigureType.KING)).collect(Collectors.toList()).get(0);

        return chessmanList.stream().filter(chessman -> chessman.getColor() == (color.equals(COLOR.WHITE) ? COLOR.BLACK : COLOR.WHITE))
                .anyMatch(chessman -> chessman.getMovablePositions(this).stream().map(Position::new).anyMatch(position -> position.equals(new Position(king.getPos()))));
    }

    public Chessman getFigureByPosition(PositionData data) {
        List<Chessman> collection = this.getChessmanList().stream().filter(chessman -> chessman.getPos().getX() == data.getX() && chessman.getPos().getY() == data.getY()).collect(Collectors.toList());

        if(collection.isEmpty()){
            return null;
        }

        return collection.get(0);
    }

    public Chessman getFigureByPosition(int X, int Y) {
        return getFigureByPosition(new PositionData(){{
            this.setX(X);
            this.setY(Y);
        }});
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void printBoardToConsole(){
        for (int y = 0; y < this.height; y++) {
            if(y != 0){
                for(int i = 0; i < this.width; i++) {
                    System.out.print("–––");
                }
                System.out.print("–\n");
            }
            System.out.print("|");
            for(int x = 0; x < this.width; x++){
                Chessman chessman = null;
                for (Chessman man : chessmanList) {
                    if(man.getPos().getX() == x && man.getPos().getY() == y){
                        chessman = man;
                        break;
                    }
                }
                if(chessman == null){
                    System.out.print("  ");
                }else {
                    System.out.print(chessman.getColor().equals(COLOR.BLACK) ? "B" : "W");

                    if(chessman instanceof Pawn){
                        System.out.print("p");
                    }

                    if(chessman instanceof Rook){
                        System.out.print("r");
                    }

                    if(chessman instanceof Knight){
                        System.out.print("k");
                    }

                    if(chessman instanceof Queen){
                        System.out.print("Q");
                    }

                    if(chessman instanceof King){
                        System.out.print("K");
                    }

                    if(chessman instanceof Bishop){
                        System.out.print("b");
                    }
                }
                System.out.print("|");
            }
            System.out.print("\n");
        }
    }

}
