package Chess;

import Chess.Exceptions.WrongNotationException;
import Chess.Game.Board;
import Chess.Game.Position;
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

    public static void main(String[] args) throws WrongNotationException {
        parsArgs(args);

        try {
            Board b = new Board("8/8/8/8/8/3b4/2P5/5K2 w - - 0 1");

            b.printBoard();

            System.out.println(b.inChess());

            System.out.println(b.getChessmanByPosition(new Position(2,6)).getMoves(b));
            System.out.println(b.getChessmanByPosition(new Position(2,6)).getChessMoves(b, b.inChess()));

        } catch (WrongNotationException e) {
            e.printStackTrace();
        }

        //Server sv = new Server();
        //sv.start();
    }
}
