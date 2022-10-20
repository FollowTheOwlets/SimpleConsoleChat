package log_and_pool_tests;

import log_and_pool.Pool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;

public class PoolTest {
    Pool pool = Pool.getInstance();

    @Test
    void getInstanceTest() {
        System.out.println("/-- getInstanceTest begin ---");
        Pool checkPool = Pool.getInstance();
        pool = Pool.getInstance();

        Assertions.assertEquals(pool, checkPool);
        System.out.println("--- getInstanceTest complete --/");
    }

    @Test
    void getSizeTest() {
        System.out.println("/-- getSizeTest begin ---");

        pool.deleteWriter("name1");
        pool.deleteWriter("name2");
        pool.deleteWriter("name3");
        pool.addWriter("name1", Mockito.mock(PrintWriter.class));
        pool.addWriter("name2", Mockito.mock(PrintWriter.class));

        int size = pool.getSize();

        Assertions.assertEquals(size ,2);
        System.out.println("--- getSizeTest complete --/");
    }

    @Test
    void addWriterTest() {
        System.out.println("/-- addWriterTest begin ---");

        pool.addWriter("name1", Mockito.mock(PrintWriter.class));
        pool.addWriter("name2", Mockito.mock(PrintWriter.class));

        Assertions.assertEquals(pool.getSize(), 2);
        System.out.println("--- addWriterTest complete --/");
    }

    @Test
    void deleteWriterTest() {
        System.out.println("/-- deleteWriterTest begin ---");

        pool.addWriter("name1", Mockito.mock(PrintWriter.class));
        pool.addWriter("name2", Mockito.mock(PrintWriter.class));
        pool.addWriter("name3", Mockito.mock(PrintWriter.class));
        pool.deleteWriter("name3");

        Assertions.assertEquals(pool.getSize(), 2);
        System.out.println("--- deleteWriterTest complete --/");
    }

    @Test
    void sendFirstTest() {
        System.out.println("/-- sendFirstTest begin ---");

        PrintWriter printWriter = Mockito.mock(PrintWriter.class);
        pool.addWriter("name1", printWriter);
        pool.addWriter("name2", printWriter);

        Assertions.assertTrue(pool.send("name1", "test"));
        System.out.println("--- sendFirstTest complete --/");
    }

    @Test
    void sendSecondTest() {
        System.out.println("/-- sendSecondTest begin ---");

        pool.deleteWriter("name1");
        pool.deleteWriter("name2");
        pool.deleteWriter("name3");

        Assertions.assertFalse(pool.send("name1", "test"));
        System.out.println("--- sendSecondTest complete --/");
    }
}
