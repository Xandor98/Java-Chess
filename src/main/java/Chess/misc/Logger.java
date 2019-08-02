package Chess.misc;

import java.util.Arrays;

public class Logger {

    private static final LogLvl DEFAULT = LogLvl.DEBUG;

    public static boolean testcase = false;

    public static void log(LogLvl lvl, Object... msg){
        if(lvl.getLevel() >= DEFAULT.getLevel() && !testcase){
            for (Object o : msg) {
                System.out.print(o.toString() + " ");
            }
            System.out.print("\n");
        }
    }

    public static void debug(Object... msg){
        Logger.log(LogLvl.DEBUG, msg);
    }

    public static void info(Object... msg){
        Logger.log(LogLvl.INFO, msg);
    }
    public static void warning(Object... msg){
        Logger.log(LogLvl.WARNING, msg);
    }
    public static void error(Object... msg){
        Logger.log(LogLvl.ERROR, msg);
    }
    public static void fatal(Object... msg){
        Logger.log(LogLvl.FATAL, msg);
    }

    public enum LogLvl{
        DEBUG(1), INFO(2), WARNING(3), ERROR(4), FATAL(5);

        private int level;

        LogLvl(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }
}
