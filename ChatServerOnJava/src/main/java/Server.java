import log_and_pool.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static int port;
    static String host;

    public static void main(String[] args) {
        loadSettings();
        Log.getInstance().log("Сервер запустился!");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new User().setClientSocket(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // load settings;
    static public void loadSettings() {
        try (BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/files/settings.json")))) {
            StringBuilder stringBuilder = new StringBuilder();
            while (bis.ready()) {
                stringBuilder.append(bis.readLine());
            }
            JSONParser jsonParser = new JSONParser();
            JSONObject object = (JSONObject) jsonParser.parse(stringBuilder.toString());
            port = Integer.parseInt(object.get("port").toString());
            host = (String) object.get("host");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.printf("Загружены данные \nport: %d\nhost: %s\n\n", port, host);
    }
}
