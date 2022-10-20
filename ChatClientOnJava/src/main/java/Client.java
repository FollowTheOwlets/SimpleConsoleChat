import log.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static Scanner scan = new Scanner(System.in);
    static String message = "";
    static String name = null;
    static MessageGetter messageGetter = null;
    static int port;
    static String host;
    static Log log;

    public static void main(String[] args) {
        loadSettings();
        log = Log.getInstance();
        try (Socket clientSocket1 = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket1.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()))) {
            System.out.println(in.readLine());
            name = scan.nextLine();
            if (!name.equals("/exit")) {
                messageGetter = new MessageGetter(in);
                messageGetter.start();
                out.println(name);
            } else {
                message = "/exit";
            }
            while (!message.equals("/exit") && !messageGetter.isInterrupted()) {
                message = scan.nextLine();
                out.println(message);
                String messageStr = String.format("[ %s ]: %s", name, message);
                log.log(messageStr);
            }
            if (messageGetter != null) messageGetter.interrupt();
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
