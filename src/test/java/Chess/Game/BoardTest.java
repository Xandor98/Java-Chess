package Chess.Game;

import static org.junit.jupiter.api.Assertions.*;

import Chess.Game.pieces.Chessman;
import Chess.generated.ChessFigureType;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void Board_Init(){
        Board b = new Board();

        for (int i = 0; i < b.getWidth(); i++){
            Chessman cB = b.getFigureByPosition(i, 1);
            Chessman cW = b.getFigureByPosition(i,6);

            assertEquals(cB.getType(), ChessFigureType.PAWN);
            assertEquals(cW.getType(), ChessFigureType.PAWN);
        }

        assertEquals(b.getFigureByPosition(0, 0).getType(), ChessFigureType.ROOK);
        assertEquals(b.getFigureByPosition(1, 0).getType(), ChessFigureType.KNIGHT);
        assertEquals(b.getFigureByPosition(2, 0).getType(), ChessFigureType.BISHOP);
        assertEquals(b.getFigureByPosition(3, 0).getType(), ChessFigureType.QUEEN);
        assertEquals(b.getFigureByPosition(4, 0).getType(), ChessFigureType.KING);
        assertEquals(b.getFigureByPosition(5, 0).getType(), ChessFigureType.BISHOP);
        assertEquals(b.getFigureByPosition(6, 0).getType(), ChessFigureType.KNIGHT);
        assertEquals(b.getFigureByPosition(7, 0).getType(), ChessFigureType.ROOK);

        assertEquals(b.getFigureByPosition(0, 7).getType(), ChessFigureType.ROOK);
        assertEquals(b.getFigureByPosition(1, 7).getType(), ChessFigureType.KNIGHT);
        assertEquals(b.getFigureByPosition(2, 7).getType(), ChessFigureType.BISHOP);
        assertEquals(b.getFigureByPosition(3, 7).getType(), ChessFigureType.QUEEN);
        assertEquals(b.getFigureByPosition(4, 7).getType(), ChessFigureType.KING);
        assertEquals(b.getFigureByPosition(5, 7).getType(), ChessFigureType.BISHOP);
        assertEquals(b.getFigureByPosition(6, 7).getType(), ChessFigureType.KNIGHT);
        assertEquals(b.getFigureByPosition(7, 7).getType(), ChessFigureType.ROOK);
    }

}