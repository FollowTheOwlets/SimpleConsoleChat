package log_tests;

import log.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LogTest {
    Log log;
    @Test
    void getInstanceTest(){
        System.out.println("/-- getInstanceTest begin ---");
        Log checkLog = Log.getInstance();
        log = Log.getInstance();

        Assertions.assertEquals(checkLog,log);
        System.out.println("--- getInstanceTest complete --/");
    }
}
