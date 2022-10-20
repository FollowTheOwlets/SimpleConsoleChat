package log_and_pool;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Pool {
    private Map<String, PrintWriter> printWriters;
    private static Pool INSTANCE = null;

    private Pool() {
        this.printWriters = new HashMap<>();
    }

    public static Pool getInstance() {
        if (INSTANCE == null) {
            synchronized (Pool.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Pool();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized int getSize() {
        return this.printWriters.size();
    }

    public synchronized void addWriter(String name, PrintWriter printWriter) {
        this.printWriters.put(name, printWriter);
    }

    public synchronized void deleteWriter(String name) {
        this.printWriters.remove(name);
    }

    public synchronized boolean send(String name, String message) {
        boolean flag = false;
        for (String key : this.printWriters.keySet()) {
            if (!key.equals(name)) {
                flag = true;
                PrintWriter out = this.printWriters.get(key);
                out.println(message);
            }
        }
        return flag;
    }
}
