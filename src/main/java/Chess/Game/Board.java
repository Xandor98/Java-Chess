package Chess.Game;

import Chess.Game.pieces.*;
import Chess.generated.BoardData;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.PositionData;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Chessman> chessmanList = new ArrayList<>();

    private int width;
    private int height;

    public Board(){
        this.width = 8;
        this.height = 8;

        initScenario();

    }

    private void initScenario(){
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(1);this.setY(1);}}, false));
        this.chessmanList.add(new Pawn(COLOR.BLACK, new PositionData(){{this.setX(3);this.setY(1);}}, false));

        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(1);this.setY(3);}}, true));
        this.chessmanList.add(new Pawn(COLOR.WHITE, new PositionData(){{this.setX(3);this.setY(4);}}, true));
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
        this.chessmanList.add(new King(COLOR.BLACK, new PositionData(){{this.setX(3); this.setY(0);}}, false));
        this.chessmanList.add(new King(COLOR.WHITE, new PositionData(){{this.setX(3); this.setY(7);}}, false));

        //QUEEN
        this.chessmanList.add(new Queen(COLOR.BLACK, new PositionData(){{this.setX(4); this.setY(0);}}, false));
        this.chessmanList.add(new Queen(COLOR.WHITE, new PositionData(){{this.setX(4); this.setY(7);}}, false));

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
