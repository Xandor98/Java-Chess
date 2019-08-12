package Chess.Game.pieces;

import Chess.Exceptions.WrongMoveException;
import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.generated.MoveMessage;
import Chess.misc.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    Board b;
    int size;

    @BeforeEach
    void setUp() {
        Logger.testcase = true;
        b = new Board();

        ChessFigure fig = new ChessFigure(){{
            this.setPos(new Position(4,4).getData());
            this.setColor(COLOR.WHITE);
            this.setType(ChessFigureType.KNIGHT);
            this.setMoved(false);
        }};

        ChessFigure fig2 = new ChessFigure(){{
            this.setPos(new Position(1,0).getData());
            this.setColor(COLOR.BLACK);
            this.setType(ChessFigureType.KNIGHT);
            this.setMoved(false);
        }};

        ChessFigure fig3 = new ChessFigure(){{
            this.setPos(new Position(2,3).getData());
            this.setColor(COLOR.BLACK);
            this.setType(ChessFigureType.PAWN);
            this.setMoved(false);
        }};

        ArrayList<Chessman> chessmen = new ArrayList<>(){{
            this.add(Chessman.readFigure(fig));
            this.add(Chessman.readFigure(fig2));
            this.add(Chessman.readFigure(fig3));
        }};

        b.changeBoard(chessmen);
        size = chessmen.size();
    }

    @AfterEach
    void tearDown() {
        Logger.testcase = false;
    }

    @Test
    void PositionTest(){
        Chessman c1 = b.getFigureByPosition(4,4);
        Chessman c2 = b.getFigureByPosition(1,0);
        Chessman c3 = b.getFigureByPosition(2,3);

        assertEquals(new Position(c1.getPos()), new Position(4,4));
        assertEquals(new Position(c2.getPos()), new Position(1,0));
        assertEquals(new Position(c3.getPos()), new Position(2,3));
    }

    @Test
    void NormalMoves(){
        assertEquals(8, b.getFigureByPosition(4,4).getMovablePositions(b).size());

        List<Position> positions = b.getFigureByPosition(4,4).getMovablePositions(b).stream().map(Position::new).collect(Collectors.toList());

        assertTrue(positions.contains(new Position(6,5)));
        assertTrue(positions.contains(new Position(6,3)));
        assertTrue(positions.contains(new Position(2,5)));
        assertTrue(positions.contains(new Position(2,3)));
        assertTrue(positions.contains(new Position(5,6)));
        assertTrue(positions.contains(new Position(3,6)));
        assertTrue(positions.contains(new Position(5,2)));
        assertTrue(positions.contains(new Position(3,2)));

        assertThrows(WrongMoveException.class, () -> {
            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(new Position(2,3).getData());
                this.setFigure(b.getFigureByPosition(1,0).getFigure());
            }});
        });
    }

}