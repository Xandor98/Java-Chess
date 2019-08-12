package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;
import Chess.generated.ChessFigure;
import Chess.generated.ChessFigureType;
import Chess.misc.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    Board b;
    int size;

    @BeforeEach
    void setUp() {
        Logger.testcase = true;
        b = new Board();

        ChessFigure fig = new ChessFigure(){{
            this.setPos(new Position(4,4).getData());
            this.setColor(COLOR.BLACK);
            this.setType(ChessFigureType.QUEEN);
            this.setMoved(false);
        }};

        ArrayList<Chessman> chessmen = new ArrayList<>(){{
            this.add(Chessman.readFigure(fig));
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

        assertEquals(new Position(c1.getPos()), new Position(4,4));
        b.printBoardToConsole();
    }

    @Test
    void NormalMoveTest(){
        assertEquals(7*4 - 1, b.getFigureByPosition(4,4).getMovablePositions(b).size());

        List<Position> positions = b.getFigureByPosition(4,4).getMovablePositions(b).stream().map(Position::new).collect(Collectors.toList());

        assertTrue(positions.contains(new Position(0,0)));
        assertTrue(positions.contains(new Position(1,1)));
        assertTrue(positions.contains(new Position(2,2)));
        assertTrue(positions.contains(new Position(3,3)));
        assertTrue(positions.contains(new Position(5,5)));
        assertTrue(positions.contains(new Position(6,6)));
        assertTrue(positions.contains(new Position(7,7)));

        assertTrue(positions.contains(new Position(5,4)));
        assertTrue(positions.contains(new Position(6,4)));
        assertTrue(positions.contains(new Position(7,4)));

        assertTrue(positions.contains(new Position(3,4)));
        assertTrue(positions.contains(new Position(2,4)));
        assertTrue(positions.contains(new Position(1,4)));
        assertTrue(positions.contains(new Position(0,4)));

        assertTrue(positions.contains(new Position(4,3)));
        assertTrue(positions.contains(new Position(4,2)));
        assertTrue(positions.contains(new Position(4,1)));
        assertTrue(positions.contains(new Position(4,0)));

        assertTrue(positions.contains(new Position(4,5)));
        assertTrue(positions.contains(new Position(4,6)));
        assertTrue(positions.contains(new Position(4,7)));

        assertTrue(positions.contains(new Position(5,3)));
        assertTrue(positions.contains(new Position(6,2)));
        assertTrue(positions.contains(new Position(7,1)));

        assertTrue(positions.contains(new Position(3,5)));
        assertTrue(positions.contains(new Position(2,6)));
        assertTrue(positions.contains(new Position(1,7)));
    }
}