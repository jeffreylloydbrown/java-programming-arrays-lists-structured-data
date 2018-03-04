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

    @Test
    public void countVisitsPerIP() {
        LogAnalyzer laShortTest = new LogAnalyzer();
        laShortTest.readFile("test/data/short-test_log");
        HashMap<String, Integer> counts = laShortTest.countVisitsPerIP();
        assertEquals(3, (int) counts.get("152.3.135.44"));
        assertEquals( 2, (int) counts.get("152.3.135.63"));
        assertEquals(1, (int) counts.get("157.55.39.203"));
        assertEquals(1, (int) counts.get("110.76.104.12"));

        //negative cases that throw if we don't use getOrDefault
        assertEquals(0, (int) counts.getOrDefault("99.99.99.99", 0));
        assertEquals(0, (int) counts.getOrDefault("", 0));
        assertEquals(0, (int) counts.getOrDefault(null, 0));
    }

    @Test
    public void mostNumberVisitsByIP() {
        LogAnalyzer laShortTest = new LogAnalyzer();
        laShortTest.readFile("test/data/weblog3-short_log");
        HashMap<String, Integer> counts = laShortTest.countVisitsPerIP();
        assertEquals(3, laShortTest.mostNumberVisitsByIP(counts));

        assertEquals(0, laShortTest.mostNumberVisitsByIP(new HashMap<String, Integer>()));
        assertEquals(0, laShortTest.mostNumberVisitsByIP(null));
    }
}