package Chess;

import Chess.Game.Board;
import Chess.Game.pieces.Chessman;
import Chess.Game.pieces.Pawn;
import Chess.Server.Server;
import Chess.generated.ChessFigure;
import Chess.generated.MoveMessage;
import Chess.generated.MoveType;
import Chess.generated.PositionData;
import Chess.misc.Logger;
import Chess.misc.Settings;
import org.apache.commons.cli.*;

public class Main {

    private static void parsArgs(String[] args) {
        Options availableOptions = new Options();
        availableOptions.addOption("c", true, "path to property file for configuration");
        availableOptions.addOption("h", false, "displays this help message");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(availableOptions, args);
            if (cmd.hasOption("h")) {
                printCMDHelp(0, availableOptions);
            }
            String configPath = cmd.getOptionValue("c");
            // Wenn mit null aufgerufen, werden Standardwerte benutzt
            Settings.load(configPath);
        } catch (ParseException e) {
            printCMDHelp(1, availableOptions);
        }
    }

    private static void printCMDHelp(int exitCode, Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar maze-server.jar [options]\nAvailable Options:", options);
        System.exit(exitCode);
    }

    public static void main(String[] args) {
        parsArgs(args);

        Board b = new Board();
        b.printBoardToConsole();

        for (Chessman chessman : b.getChessmanList()) {
            Logger.debug(chessman.getPos(), chessman.getMovablePositions(b).toString());
        }

        ChessFigure abc = b.getChessmanList().get(0).getFigure();

        b.makeMove(new MoveMessage(){{
            this.setDestinationPos(new PositionData(){{this.setX(1); this.setY(2);}});
            this.setFigure(abc);
            this.setMoveType(MoveType.NORMAL);
        }});

        b.printBoardToConsole();
    }
}
