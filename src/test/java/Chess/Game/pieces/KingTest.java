package Chess.Game.pieces;

import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.COLOR;
import Chess.generated.PositionData;
import Chess.misc.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    Board b;

    @BeforeEach
    void setUp() {
        Logger.testcase = true;

        this.b = new Board();

        List<Chessman> chessman = new ArrayList<>();

        chessman.add(new King(COLOR.BLACK, new Position(1,1).getData(), true));
        chessman.add(new Queen(COLOR.WHITE, new Position(5,1).getData(), true));

        this.b.changeBoard(chessman);
    }

    @AfterEach
    void tearDown() {
        Logger.testcase = false;
    }

    @Test
    void inChessTest(){
        List<Chessman> inChess = b.inChess(COLOR.BLACK);

        assertTrue(inChess.size() > 0);
        Chessman c1 = b.getFigureByPosition(1,1);

        List<Position> nPositions = c1.getMovablePositions(b).stream().map(Position::new).collect(Collectors.toList());
        assertFalse(nPositions.contains(new Position(2,1)));

        try {
            List<Position> positions = c1.getMovableChessPositions(b, inChess).stream().map(Position::new).collect(Collectors.toList());
            System.out.println(positions);
        }catch (StackOverflowError e){
            e.printStackTrace();
        }
    }
}