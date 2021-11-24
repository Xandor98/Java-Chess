package de.xandor98.chess.misc;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import de.xandor98.chess.game.Position;
import de.xandor98.chess.game.pieces.*;
import de.xandor98.chess.generated.COLOR;

import java.util.regex.Pattern;

public class Parser {

    private static Pattern FENregex = Pattern.compile("(([rnbqkbnrpRNBQKBNRP]|[1-8])+/)(([rnbqkbnrpRNBQKBNRP]|[1-8])+/)(([rnbqkbnrpRNBQKBNRP]|[1-8])+/)(([rnbqkbnrpRNBQKBNRP]|[1-8])+/)(([rnbqkbnrpRNBQKBNRP]|[1-8])+/)(([rnbqkbnrpRNBQKBNRP]|[1-8])+/)(([rnbqkbnrpRNBQKBNRP]|[1-8])+/)(([rnbqkbnrpRNBQKBNRP]|[1-8])+) [wb] ([KQkq]*|-) (-|([abcdefgh][36])) [0-9]+ [0-9]+");

    public static boolean FENTester(String FEN){
        return FENregex.matcher(FEN).matches();
    }

    public static Pair<COLOR, Chessman.ChessmanType> parseChessman(char c){
        switch (c){
            case 'p':
                return new ImmutablePair<>(COLOR.BLACK, Chessman.ChessmanType.PAWN);
            case 'r':
                return new ImmutablePair<>(COLOR.BLACK, Chessman.ChessmanType.ROOK);
            case 'n':
                return new ImmutablePair<>(COLOR.BLACK, Chessman.ChessmanType.KNIGHT);
            case 'b':
                return new ImmutablePair<>(COLOR.BLACK, Chessman.ChessmanType.BISHOP);
            case 'q':
                return new ImmutablePair<>(COLOR.BLACK, Chessman.ChessmanType.QUEEN);
            case 'k':
                return new ImmutablePair<>(COLOR.BLACK, Chessman.ChessmanType.KING);
            case 'P':
                return new ImmutablePair<>(COLOR.WHITE, Chessman.ChessmanType.PAWN);
            case 'R':
                return new ImmutablePair<>(COLOR.WHITE, Chessman.ChessmanType.ROOK);
            case 'N':
                return new ImmutablePair<>(COLOR.WHITE, Chessman.ChessmanType.KNIGHT);
            case 'B':
                return new ImmutablePair<>(COLOR.WHITE, Chessman.ChessmanType.BISHOP);
            case 'Q':
                return new ImmutablePair<>(COLOR.WHITE, Chessman.ChessmanType.QUEEN);
            case 'K':
                return new ImmutablePair<>(COLOR.WHITE, Chessman.ChessmanType.KING);
            default:
                return null;
        }
    }

    public static char parseChessman(Chessman chessman){
        switch (chessman.getType()){
            case PAWN:
                return chessman.getColor().equals(COLOR.BLACK) ? 'p' : 'P';
            case KNIGHT:
                return chessman.getColor().equals(COLOR.BLACK) ? 'n' : 'N';
            case KING:
                return chessman.getColor().equals(COLOR.BLACK) ? 'k' : 'K';
            case QUEEN:
                return chessman.getColor().equals(COLOR.BLACK) ? 'q' : 'Q';
            case BISHOP:
                return chessman.getColor().equals(COLOR.BLACK) ? 'b' : 'B';
            case ROOK:
                return chessman.getColor().equals(COLOR.BLACK) ? 'r' : 'R';
        }
        return '-';
    }

    public static Chessman getChessman(Chessman.ChessmanType type, COLOR color, Position position){
        switch (type){
            case PAWN:
                return new Pawn(position, color);
            case KNIGHT:
                return new Knight(position, color);
            case KING:
                return new King(position, color);
            case QUEEN:
                return new Queen(position, color);
            case BISHOP:
                return new Bishop(position, color);
            case ROOK:
                return new Rook(position, color);
            default:
                return null;
        }
    }
}
