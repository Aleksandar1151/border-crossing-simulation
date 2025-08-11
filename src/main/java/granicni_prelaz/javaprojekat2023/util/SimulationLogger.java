package granicni_prelaz.javaprojekat2023.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SimulationLogger {

    public static void log(Class<?> C, Level level, String msg, Throwable thrown) {
        Logger logger = Logger.getLogger(C.getName());
        try {
            String path = "." + File.separator + "res" + File.separator + "logs" + File.separator;
            Utils.createFolderIfNotExists(path);
            Handler handler = new FileHandler(path + C.getName() + LocalDateTime.now().toLocalTime().toString().replace(':', '_') + ".log");
            logger.addHandler(handler);
            logger.log(level, msg, thrown);
            handler.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void logAsync(Class<?> C, Exception exception) {
        new Thread(() -> SimulationLogger.log(C, Level.SEVERE, exception.getMessage(), exception)).start();
    }
}
