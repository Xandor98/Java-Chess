package Chess.Game;

import Chess.Exceptions.WrongNotationException;
import Chess.Game.pieces.Chessman;
import Chess.generated.COLOR;
import Chess.misc.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board b;

    @BeforeEach
    void setUp() {
        try {
            b = new Board();
        } catch (WrongNotationException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void StandardBoard() {
        assertNotNull(b);

        List<Chessman> list = b.getChessmanList(null);

        assertNotNull(list);
        assertNotEquals(0, list.size());
    }

    @Test
    void pawnTest(){
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(0,1)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(1,1)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(2,1)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(3,1)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(4,1)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(5,1)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(6,1)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(7,1)).getType());

        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(0,1)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(1,1)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(2,1)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(3,1)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(4,1)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(5,1)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(6,1)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(7,1)).getColor());

        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(0,6)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(1,6)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(2,6)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(3,6)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(4,6)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(5,6)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(6,6)).getType());
        assertEquals(Chessman.ChessmanType.PAWN, b.getChessmanByPosition(new Position(7,6)).getType());

        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(0,6)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(1,6)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(2,6)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(3,6)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(4,6)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(5,6)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(6,6)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(7,6)).getColor());
    }

    @Test
    void backLineTest(){
        assertEquals(Chessman.ChessmanType.ROOK  , b.getChessmanByPosition(new Position(0,0)).getType());
        assertEquals(Chessman.ChessmanType.KNIGHT, b.getChessmanByPosition(new Position(1,0)).getType());
        assertEquals(Chessman.ChessmanType.BISHOP, b.getChessmanByPosition(new Position(2,0)).getType());
        assertEquals(Chessman.ChessmanType.QUEEN , b.getChessmanByPosition(new Position(3,0)).getType());
        assertEquals(Chessman.ChessmanType.KING  , b.getChessmanByPosition(new Position(4,0)).getType());
        assertEquals(Chessman.ChessmanType.BISHOP, b.getChessmanByPosition(new Position(5,0)).getType());
        assertEquals(Chessman.ChessmanType.KNIGHT, b.getChessmanByPosition(new Position(6,0)).getType());
        assertEquals(Chessman.ChessmanType.ROOK  , b.getChessmanByPosition(new Position(7,0)).getType());

        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(0,0)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(1,0)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(2,0)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(3,0)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(4,0)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(5,0)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(6,0)).getColor());
        assertEquals(COLOR.BLACK, b.getChessmanByPosition(new Position(7,0)).getColor());

        assertEquals(Chessman.ChessmanType.ROOK  , b.getChessmanByPosition(new Position(0,7)).getType());
        assertEquals(Chessman.ChessmanType.KNIGHT, b.getChessmanByPosition(new Position(1,7)).getType());
        assertEquals(Chessman.ChessmanType.BISHOP, b.getChessmanByPosition(new Position(2,7)).getType());
        assertEquals(Chessman.ChessmanType.QUEEN , b.getChessmanByPosition(new Position(3,7)).getType());
        assertEquals(Chessman.ChessmanType.KING  , b.getChessmanByPosition(new Position(4,7)).getType());
        assertEquals(Chessman.ChessmanType.BISHOP, b.getChessmanByPosition(new Position(5,7)).getType());
        assertEquals(Chessman.ChessmanType.KNIGHT, b.getChessmanByPosition(new Position(6,7)).getType());
        assertEquals(Chessman.ChessmanType.ROOK  , b.getChessmanByPosition(new Position(7,7)).getType());

        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(0,7)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(1,7)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(2,7)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(3,7)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(4,7)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(5,7)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(6,7)).getColor());
        assertEquals(COLOR.WHITE, b.getChessmanByPosition(new Position(7,7)).getColor());
    }

    @Test
    void test(){
        try {
            Board b = new Board("8/8/8/8/8/3b4/2P5/5K2 w - - 0 1");

            b.printBoard();

            System.out.println(b.inChess());

            System.out.println(b.getChessmanByPosition(new Position(2,6)).getMoves(b));
            System.out.println(b.getChessmanByPosition(new Position(2,6)).getChessMoves(b, b.inChess()));

        } catch (WrongNotationException e) {
            e.printStackTrace();
        }
    }
}