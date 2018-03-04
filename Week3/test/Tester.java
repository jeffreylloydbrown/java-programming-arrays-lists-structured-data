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
        // Calling countVisitsPerIP() before loading a file shouldn't crash, should
        // return empty hashmap
        assertTrue(laShortTest.countVisitsPerIP().isEmpty());
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

    @Test
    public void iPsMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/weblog3-short_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        ArrayList<String> ipAddresses = la.iPsMostVisits(counts);
        assertEquals(2, ipAddresses.size());
        assertTrue(ipAddresses.contains("61.15.121.171"));
        assertTrue(ipAddresses.contains("84.133.195.161"));

        // degenerate cases don't crash, return empty lists
        assertTrue(la.iPsMostVisits(new HashMap<String, Integer>()).isEmpty());
        assertTrue(la.iPsMostVisits(null).isEmpty());
    }

    @Test
    public void iPsForDays() {
        LogAnalyzer la = new LogAnalyzer();
        // shouldn't crash if log file not loaded, get empty map back.
        assertTrue(la.iPsForDays().isEmpty());

        la.readFile("test/data/weblog3-short_log");
        HashMap<String, ArrayList<String>> byDates = la.iPsForDays();
        assertEquals(1, byDates.get("Sep 14").size());
        assertEquals(4, byDates.get("Sep 21").size());
        assertEquals(5, byDates.get("Sep 30").size());

        // Dates that don't exist will throw exception if we don't use getOrDefault.
        assertEquals(0, byDates.getOrDefault("Juk 92", new ArrayList<String>()).size());
    }

    @Test
    public void dayWithMostIPVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/weblog3-short_log");
        assertEquals("Sep 30", la.dayWithMostIPVisits(la.iPsForDays()));

        // degenerate cases
        assertTrue(la.dayWithMostIPVisits(new HashMap<String, ArrayList<String>>()).isEmpty());
        assertTrue(la.dayWithMostIPVisits(null).isEmpty());
    }

    @Test
    public void iPsWithMostVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/weblog3-short_log");
        HashMap<String, ArrayList<String>> byDays = la.iPsForDays();
        ArrayList<String> most = la.iPsWithMostVisitsOnDay(byDays, "Sep 30");
        assertEquals(2, most.size());
        assertTrue(most.contains("61.15.121.171"));
        assertTrue(most.contains("177.4.40.87"));

        // Date not found should return empty list.  So should invalid date.
        assertTrue(la.iPsWithMostVisitsOnDay(byDays, "Jan 22").isEmpty());
        assertTrue(la.iPsWithMostVisitsOnDay(byDays, "Juk 99").isEmpty());

        // Degenerate cases
        assertTrue(la.iPsWithMostVisitsOnDay(byDays, "").isEmpty());
        assertTrue(la.iPsWithMostVisitsOnDay(byDays, null).isEmpty());
        assertTrue(la.iPsWithMostVisitsOnDay(new HashMap<String, ArrayList<String>>(), "Sep 30").isEmpty());
        assertTrue(la.iPsWithMostVisitsOnDay(null, "Sep 30").isEmpty());
    }
}