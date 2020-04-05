import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    private static final String LOG_FOLDER_PATH = "./logs";
    private static final String LOG_FILE_NAME = "client%u.log";

    public static final Logger log = Logger.getLogger("log");

    public static void configureLoggingToFile() {
        Locale.setDefault(Locale.ENGLISH);
        File logFolder = new File(LOG_FOLDER_PATH);
        if (!logFolder.exists()) logFolder.mkdir();
        try {
            FileHandler fileHandler = new FileHandler(LOG_FOLDER_PATH + "/" + LOG_FILE_NAME);
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);
        }
        catch (IOException ignored) { }
    }

}
