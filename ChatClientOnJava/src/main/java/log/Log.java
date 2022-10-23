package log;

import java.io.*;
import java.time.LocalDateTime;

public class Log {
    private static Log INSTANCE = null;

    private Log() {
    }

    public static Log getInstance() {
        if (INSTANCE == null) {
            synchronized (Log.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Log();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void log(String msg) {
        try (FileWriter fileWriter = new FileWriter("src/main/java/files/logs.txt", true)) {
            fileWriter.write("[" + LocalDateTime.now() + "]" +
                    " -> " + msg + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Файл не доступен для записи");
        }
    }
}
