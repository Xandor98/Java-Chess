package Chess;

import Chess.Game.pieces.Chessman;
import Chess.Server.Server;
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

        Server sv = new Server();
        sv.start();
    }
}
