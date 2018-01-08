package prospector.mechconstruct.util;

import prospector.mechconstruct.mod.MechConstruct;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    private static Logger logger = LogManager.getLogger(MechConstruct.MOD_ID);

    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    public static void error(String message) {
        logger.log(Level.ERROR, message);
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }
}
