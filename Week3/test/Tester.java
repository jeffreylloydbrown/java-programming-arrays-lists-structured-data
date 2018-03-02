import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class Tester {

    @Test
    public void testLogEntry() {
        Date date = new Date();
        LogEntry le = new LogEntry("1.2.3.4", date, "example request", 200, 500);
        assertEquals("1.2.3.4", le.getIpAddress());
        assertEquals(date, le.getAccessTime());
        assertEquals("example request", le.getRequest());
        assertEquals(200, le.getStatusCode());
        assertEquals(500, le.getBytesReturned());
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }

    @Test
    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/short-test_log");
        la.printAll();
    }
}