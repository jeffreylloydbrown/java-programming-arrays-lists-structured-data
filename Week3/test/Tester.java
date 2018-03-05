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
    public void countUniqueIPs() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/short-test_log");
        assertEquals(4, la.countUniqueIPs());
    }

    @Test
    public void printAllHigherThanNum() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/short-test_log");
        System.out.println("Should see no output with 404");
        la.printAllHigherThanNum(404);
        System.out.println("End of call with 404");
        System.out.println("Should see 1 line with 302");
        la.printAllHigherThanNum(302);
        System.out.println("End of call with 302");
        System.out.println("Should see 3 lines with 301");
        la.printAllHigherThanNum(301);
        System.out.println("End of call with 301");
    }

    @Test
    public void uniqueIPVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/weblog-short_log");
        assertEquals(2, la.uniqueIPVisitsOnDay("Sep 14").size());
        assertEquals(3, la.uniqueIPVisitsOnDay("Sep 30").size());
        // Valid date not in the file
        assertEquals(0, la.uniqueIPVisitsOnDay("Jan 31").size());
        // Invalid dates
        assertEquals(0, la.uniqueIPVisitsOnDay("Juk 99").size());
        assertEquals(0, la.uniqueIPVisitsOnDay("").size());
        assertEquals(0, la.uniqueIPVisitsOnDay(null).size());
    }

    @Test
    void countUniqueIPsInRange() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/short-test_log");
        assertEquals(4, la.countUniqueIPsInRange(200, 299));
        assertEquals(2, la.countUniqueIPsInRange(399,300));
        assertEquals(0, la.countUniqueIPsInRange(-1, -1));
        assertEquals(0, la.countUniqueIPsInRange(90,90));
        assertEquals(0, la.countUniqueIPsInRange(0, 0));
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

    @Test
    public void uniqueIPPracticeQuiz() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/weblog1_log");
        System.out.println("Q2 is the set of unique status codes shown here");
        la.printAllHigherThanNum(400);

        System.out.println("Q3.  "+ la.uniqueIPVisitsOnDay("Mar 17").size());
        System.out.println("Q4.  "+ la.countUniqueIPsInRange(200, 299));
    }

    @Test
    public void practiceQuiz() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/weblog1_log");
        System.out.println("Q1. "+la.mostNumberVisitsByIP(la.countVisitsPerIP()));

        ArrayList<String> q2 = la.iPsMostVisits(la.countVisitsPerIP());
        System.out.println("Q2. "+q2);

        System.out.println("Q3. "+la.dayWithMostIPVisits(la.iPsForDays()));

        System.out.println("Q4. "+la.iPsWithMostVisitsOnDay(la.iPsForDays(), "Mar 17"));
    }

    @Test
    public void finalQuiz() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("test/data/weblog2_log");
        System.out.println("Q4.  "+la.countUniqueIPs());
        System.out.println("Q5.  "+la.uniqueIPVisitsOnDay("Sep 27").size());
        System.out.println("Q6.  "+la.countUniqueIPsInRange(200, 299));
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        System.out.println("Q7.  "+la.mostNumberVisitsByIP(counts));
        System.out.println("Q8.  "+la.iPsMostVisits(counts));
        HashMap<String, ArrayList<String>> days = la.iPsForDays();
        System.out.println("Q9.  "+la.dayWithMostIPVisits(days));
        System.out.println("Q10. "+la.iPsWithMostVisitsOnDay(days, "Sep 29"));
    }
}