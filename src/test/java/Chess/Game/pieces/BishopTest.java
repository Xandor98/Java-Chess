package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.*;
import Chess.misc.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    Board b;
    int size;

    @BeforeEach
    void setUp() {

        Logger.testcase = true;

        b = new Board();

        ChessFigure fig = new ChessFigure(){{
            this.setPos(new Position(0,0).getData());
            this.setColor(COLOR.BLACK);
            this.setType(ChessFigureType.BISHOP);
            this.setMoved(false);
        }};

        ChessFigure fig2 = new ChessFigure(){{
            this.setPos(new Position(7,0).getData());
            this.setColor(COLOR.BLACK);
            this.setType(ChessFigureType.BISHOP);
            this.setMoved(false);
        }};

        ChessFigure fig3 = new ChessFigure(){{
            this.setPos(new Position(0,7).getData());
            this.setColor(COLOR.WHITE);
            this.setType(ChessFigureType.BISHOP);
            this.setMoved(false);
        }};

        ChessFigure fig4 = new ChessFigure(){{
            this.setPos(new Position(7,7).getData());
            this.setColor(COLOR.WHITE);
            this.setType(ChessFigureType.BISHOP);
            this.setMoved(false);
        }};

        ArrayList<Chessman> chessmen = new ArrayList<>(){{
            this.add(Chessman.readFigure(fig));
            this.add(Chessman.readFigure(fig2));
            this.add(Chessman.readFigure(fig3));
            this.add(Chessman.readFigure(fig4));
        }};

        b.changeBoard(chessmen);
        size = chessmen.size();
    }

    @AfterEach
    void tearDown() {
        Logger.testcase = false;
    }

    @Test
    void PositionTest() {
        Chessman c1 = b.getFigureByPosition(0,0);
        Chessman c2 = b.getFigureByPosition(7,0);
        Chessman c3 = b.getFigureByPosition(0,7);
        Chessman c4 = b.getFigureByPosition(7,7);

        assertEquals(new Position(c1.getPos()), new Position(0,0));
        assertEquals(new Position(c2.getPos()), new Position(7,0));
        assertEquals(new Position(c3.getPos()), new Position(0,7));
        assertEquals(new Position(c4.getPos()), new Position(7,7));
    }

    @Test
    void NormalMoves() {
        assertEquals(7, b.getFigureByPosition(0,0).getMovablePositions(b).size());
        assertEquals(7, b.getFigureByPosition(7,0).getMovablePositions(b).size());
        assertEquals(7, b.getFigureByPosition(0,7).getMovablePositions(b).size());
        assertEquals(7, b.getFigureByPosition(7,7).getMovablePositions(b).size());
    }

    @Test
    void Beating_Move1(){
        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setFigure(b.getFigureByPosition(0,0).getFigure());
                this.setDestinationPos(new Position(7,7).getData());
            }});
        });

        assertEquals(this.size - 1, b.getChessmanList().size());

        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setFigure(b.getFigureByPosition(7,0).getFigure());
                this.setDestinationPos(new Position(0,7).getData());
            }});
        });

        assertEquals(this.size - 2, b.getChessmanList().size());
    }

    @Test
    void Beating_Move2(){
        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setFigure(b.getFigureByPosition(7,7).getFigure());
                this.setDestinationPos(new Position(0,0).getData());
            }});
        });

        assertEquals(this.size - 1, b.getChessmanList().size());

        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setFigure(b.getFigureByPosition(0,7).getFigure());
                this.setDestinationPos(new Position(7,0).getData());
            }});
        });

        assertEquals(this.size - 2, b.getChessmanList().size());
    }
}