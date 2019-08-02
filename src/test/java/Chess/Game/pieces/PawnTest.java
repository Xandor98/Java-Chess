package Chess.Game.pieces;

import Chess.Exceptions.WrongMoveException;
import Chess.Game.Board;
import Chess.Game.Position;
import Chess.generated.*;
import Chess.misc.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    Board b;
    int size;

    @AfterEach
    void tearDown() {
        Logger.testcase = false;
    }

    @BeforeEach
    void setUp() {
        Logger.testcase = true;

        this.b = new Board();

        ChessFigure fig = new ChessFigure(){{
            this.setColor(COLOR.BLACK);
            this.setPos(new PositionData(){{this.setX(1); this.setY(1);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(false);
        }};

        ChessFigure fig2 = new ChessFigure(){{
            this.setColor(COLOR.BLACK);
            this.setPos(new PositionData(){{this.setX(3); this.setY(1);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(false);
        }};

        ChessFigure fig3 = new ChessFigure(){{
            this.setColor(COLOR.WHITE);
            this.setPos(new PositionData(){{this.setX(2); this.setY(2);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(true);
        }};

        ChessFigure fig4 = new ChessFigure(){{
            this.setColor(COLOR.WHITE);
            this.setPos(new PositionData(){{this.setX(0); this.setY(2);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(true);
        }};

        ChessFigure fig5 = new ChessFigure(){{
            this.setColor(COLOR.BLACK);
            this.setPos(new PositionData(){{this.setX(4); this.setY(2);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(true);
        }};

        ChessFigure fig6 = new ChessFigure(){{
            this.setColor(COLOR.WHITE);
            this.setPos(new PositionData(){{this.setX(4); this.setY(3);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(true);
        }};

        ChessFigure fig7 = new ChessFigure(){{
            this.setColor(COLOR.WHITE);
            this.setPos(new PositionData(){{this.setX(7); this.setY(6);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(false);
        }};

        ChessFigure fig8 = new ChessFigure(){{
            this.setColor(COLOR.BLACK);
            this.setPos(new PositionData(){{this.setX(6); this.setY(4);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(true);
        }};

        ChessFigure fig9 = new ChessFigure(){{
            this.setColor(COLOR.WHITE);
            this.setPos(new PositionData(){{this.setX(1); this.setY(6);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(false);
        }};

        ChessFigure fig10 = new ChessFigure(){{
            this.setColor(COLOR.BLACK);
            this.setPos(new PositionData(){{this.setX(2); this.setY(5);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(true);
        }};

        ChessFigure fig11 = new ChessFigure(){{
            this.setColor(COLOR.BLACK);
            this.setPos(new PositionData(){{this.setX(0); this.setY(5);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(true);
        }};

        ChessFigure fig12 = new ChessFigure(){{
            this.setColor(COLOR.BLACK);
            this.setPos(new PositionData(){{this.setX(5); this.setY(6);}});
            this.setType(ChessFigureType.PAWN);
            this.setMoved(true);
        }};

        b.changeBoard(new ArrayList<>(){{
            this.add(Chessman.readFigure(fig));
            this.add(Chessman.readFigure(fig2));
            this.add(Chessman.readFigure(fig3));
            this.add(Chessman.readFigure(fig4));
            this.add(Chessman.readFigure(fig5));
            this.add(Chessman.readFigure(fig6));
            this.add(Chessman.readFigure(fig7));
            this.add(Chessman.readFigure(fig8));
            this.add(Chessman.readFigure(fig9));
            this.add(Chessman.readFigure(fig10));
            this.add(Chessman.readFigure(fig11));
            this.add(Chessman.readFigure(fig12));
        }});

        size = b.getChessmanList().size();
    }

    @Test
    void Position_Test(){
        Chessman c1 = b.getChessmanList().get(0);
        Chessman c2 = b.getChessmanList().get(1);
        Chessman c3 = b.getChessmanList().get(2);
        Chessman c4 = b.getChessmanList().get(3);
        Chessman c5 = b.getChessmanList().get(4);
        Chessman c6 = b.getChessmanList().get(5);
        Chessman c7 = b.getChessmanList().get(6);
        Chessman c8 = b.getChessmanList().get(7);
        Chessman c9 = b.getChessmanList().get(8);
        Chessman c10 = b.getChessmanList().get(9);
        Chessman c11 = b.getChessmanList().get(10);
        Chessman c12 = b.getChessmanList().get(11);

        assertEquals(new Position(c1.getPos()), new Position(1,1));
        assertEquals(new Position(c2.getPos()), new Position(3,1));
        assertEquals(new Position(c3.getPos()), new Position(2,2));
        assertEquals(new Position(c4.getPos()), new Position(0,2));
        assertEquals(new Position(c5.getPos()), new Position(4,2));
        assertEquals(new Position(c6.getPos()), new Position(4,3));
        assertEquals(new Position(c7.getPos()), new Position(7,6));
        assertEquals(new Position(c8.getPos()), new Position(6,4));
        assertEquals(new Position(c9.getPos()), new Position(1,6));
        assertEquals(new Position(c10.getPos()), new Position(2,5));
        assertEquals(new Position(c11.getPos()), new Position(0,5));
        assertEquals(new Position(c12.getPos()), new Position(5,6));
    }

    /**
     * This Test is vor a Single Move and a Double Move of an Pawn
     */
    @Test
    void Normal_moves(){
        Chessman c1 = b.getChessmanList().get(0);
        Chessman c2 = b.getChessmanList().get(1);

        Chessman finalC = c2;
        Chessman finalC1 = c1;
        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(new PositionData(){{this.setX(1); this.setY(2);}});
                this.setFigure(finalC1.getFigure());
            }});

            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(new PositionData(){{this.setX(3); this.setY(3);}});
                this.setFigure(finalC.getFigure());
            }});
        });

        c1 = b.getChessmanList().get(0);
        c2 = b.getChessmanList().get(1);

        assertEquals(new Position(c1.getPos()), new Position(1,2));
        assertEquals(new Position(c2.getPos()), new Position(3,3));
    }

    @Test
    void Beating_Move1(){
        Chessman c1 = b.getChessmanList().get(0);
        Chessman c2 = b.getChessmanList().get(2);

        Chessman finalC = c1;
        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(c2.getPos());
                this.setFigure(finalC.getFigure());
            }});
        });

        c1 = b.getChessmanList().get(0);

        assertEquals(b.getChessmanList().size(), this.size - 1);
        assertEquals(new Position(c1.getPos()), new Position(c2.getPos()));


        Chessman c3 = b.getFigureByPosition(1,6);
        Chessman c4 = b.getFigureByPosition(2,5);

        Chessman finalC1 = c3;
        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(c4.getPos());
                this.setFigure(finalC1.getFigure());
            }});
        });

        c3 = b.getFigureByPosition(2, 5);

        assertEquals(b.getChessmanList().size(), this.size - 2);
        assertEquals(new Position(c3.getPos()), new Position(c4.getPos()));
    }

    @Test
    void Beating_Move2(){
        Chessman c1 = b.getChessmanList().get(0);
        Chessman c2 = b.getChessmanList().get(3);

        Chessman finalC = c1;
        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(c2.getPos());
                this.setFigure(finalC.getFigure());
            }});
        });

        c1 = b.getChessmanList().get(0);

        assertEquals(b.getChessmanList().size(), this.size - 1);
        assertEquals(new Position(c1.getPos()), new Position(c2.getPos()));



        Chessman c3 = b.getFigureByPosition(1, 6);
        Chessman c4 = b.getFigureByPosition(0, 5);

        Chessman finalC1 = c3;
        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(c4.getPos());
                this.setFigure(finalC1.getFigure());
            }});
        });

        c3 = b.getFigureByPosition(0, 5);

        assertEquals(b.getChessmanList().size(), this.size - 2);
        assertEquals(new Position(c3.getPos()), new Position(c4.getPos()));
    }

    @Test
    void False_Beating_Move(){
        Chessman c1 = b.getFigureByPosition(3,1);
        Chessman c2 = b.getFigureByPosition(4,2);

        assertThrows(WrongMoveException.class, () -> {
           b.makeMove(new MoveMessage(){{
              this.setDestinationPos(c2.getPos());
              this.setFigure(c1.getFigure());
           }});
        });
    }

    @Test
    void En_Passant(){

        Chessman c1 = b.getFigureByPosition(7,6);
        Chessman c2 = b.getFigureByPosition(6,4);

        Chessman c3 = b.getFigureByPosition(3, 1);
        Chessman c4 = b.getFigureByPosition(4, 3);

        assertDoesNotThrow(() -> {

            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(new Position(3,3).getData());
                this.setFigure(c3.getFigure());
            }});

            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(new Position(3,2).getData());
                this.setFigure(c4.getFigure());
            }});

        });

        assertDoesNotThrow(() -> {
            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(new Position(7,4).getData());
                this.setFigure(c1.getFigure());
            }});

            b.makeMove(new MoveMessage(){{
                this.setDestinationPos(new Position(7,5).getData());
                this.setFigure(c2.getFigure());
            }});
        });
    }

    @Test
    void Wish_Move(){
        Chessman c1 = b.getFigureByPosition(5,6);

        assertDoesNotThrow(() -> {
            assertTrue(b.makeMove(new MoveMessage(){{
                this.setFigure(c1.getFigure());
                this.setDestinationPos(new Position(5,7).getData());
            }}));

            b.proposeAWish(new WishMessage(){{
                this.setNewFigure(ChessFigureType.QUEEN);
            }});
        });

        assertEquals(b.getFigureByPosition(5,7).getType(), ChessFigureType.QUEEN);
    }
}