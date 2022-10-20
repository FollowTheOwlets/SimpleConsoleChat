import log.Log;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageGetter extends Thread {
    private final BufferedReader in;
    private final Log log;

    public MessageGetter(BufferedReader in) {
        log = Log.getInstance();
        this.in = in;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                if (in.ready()) {
                    String message = in.readLine();
                    System.out.println(message);
                    log.log(message);
                }
            } catch (IOException e) {
                System.err.println("Соединение потеряно");
                this.interrupt();
            }
        }
    }
}
