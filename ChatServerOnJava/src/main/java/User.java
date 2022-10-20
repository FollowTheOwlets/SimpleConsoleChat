import log_and_pool.Log;
import log_and_pool.Pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class User extends Thread {
    private Socket clientSocket;
    private Log log;
    private Pool pool;

    public User setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.log = Log.getInstance();
        this.pool = Pool.getInstance();
        return this;
    }

    @Override
    public void run() {
        String message = "";
        String name;
        boolean connect = true;
        try (PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()))) {

            out.println("Write your name:");
            name = in.readLine();

            // Проверка на преждевременный выход и подключение
            if (!name.equals("/exit")) {
                System.out.println("New connection accepted : Hello! " + name);
                this.pool.addWriter(name, out);
                this.setName(name);
                this.log.log(String.format("New connection accepted: %s", name));
            }

            // Общение, логирование, отключение при бездействии
            long time = 0;
            while (!message.equals("/exit") && connect) {
                long newTime = System.currentTimeMillis();
                if (time != 0) {
                    if (newTime - time > 300_000) {
                        connect = false;
                    }
                }
                if (in.ready()) {
                    message = in.readLine();
                    if (!message.equals("/exit")) {
                        time = newTime;
                        System.out.printf("[%s:%s][%s]: %s%n", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), name, message);
                        String messageStr = String.format("[ %s ]: %s", name, message);
                        log.log(messageStr);
                        this.pool.send(name, messageStr);
                    }
                }
            }

            // Выход, отключение соединения, удаление из пулла получателей, прекращение потока
            System.out.println(name + "  out of chat. Bye!");
            this.log.log(String.format("Connection kill: %s", name));
            this.clientSocket.close();
            this.pool.deleteWriter(name);
            this.interrupt();
        } catch (IOException e) {
            System.err.println("Ошибка при считывании или передаче информации в потоке" + this.getName());
            this.interrupt();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != User.class) {
            return false;
        }
        User user = (User) o;

        return clientSocket.equals(user.clientSocket);
    }
}
