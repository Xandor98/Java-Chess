package Chess.Game;

import Chess.Exceptions.WrongNotationException;
import Chess.Game.pieces.Chessman;
import Chess.generated.COLOR;
import Chess.misc.Parser;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {

    private List<Chessman> chessmanList;
    private COLOR currentPlayer;

    public Board() throws WrongNotationException {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public Board(String FEN) throws WrongNotationException {
        if(!Parser.FENTester(FEN)){
            throw new WrongNotationException("FEN Notation is wrong");
        }

        chessmanList = new ArrayList<>();
        String[] FENargs = FEN.split(" ");

        if(FENargs[1].equals("w")){
            currentPlayer = COLOR.WHITE;
        }else {
            currentPlayer = COLOR.BLACK;
        }

        int currentY = 0;
        for(String s : FENargs[0].split("/")){
            int currentX = 0;
            for(char c : s.toCharArray()){
                Pair<COLOR, Chessman.ChessmanType> res;
                if((res = (Parser.parseChessman(c))) != null){
                    Chessman man = Parser.getChessman(res.getRight(), res.getLeft(), new Position(currentX, currentY));
                    chessmanList.add(man);
                    currentX++;
                }else{
                    currentX += Integer.parseInt(String.valueOf(c));
                }
            }
            currentY++;
        }
    }

    public List<Chessman> getChessmanList(COLOR color){
        if(color == null) return new ArrayList<>(chessmanList);
        return chessmanList.stream().filter(chessman -> chessman.getColor() == color).collect(Collectors.toList());
    }

    public Chessman getChessmanByPosition(Position p){
        Optional<Chessman> opt = chessmanList.stream().filter(chessman -> chessman.getPosition().equals(p)).findFirst();
        return opt.orElse(null);
    }
}
