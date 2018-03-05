
/**
 * Write a description of class LogAnalyzer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;
import sun.rmi.runtime.Log;

public class LogAnalyzer
{
    private ArrayList<LogEntry> records;

    public LogAnalyzer() {
        records = new ArrayList<LogEntry>();
    }

    public void readFile(String filename) {
        FileResource fr = new FileResource(filename);
        for (String rec : fr.lines()) {
            records.add(WebLogParser.parseEntry(rec));
        }
    }

    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }

    private int countUniqueByIP (ArrayList<LogEntry> list) {
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for (LogEntry le : list) {
            String address = le.getIpAddress();
            if (! uniqueIPs.contains(address))
                uniqueIPs.add(address);
        }
        return uniqueIPs.size();
    }

    public int countUniqueIPs() {
        return countUniqueByIP(records);
    }

    public void printAllHigherThanNum (int num) {
        for (LogEntry le : records) {
            if (le.getStatusCode() > num)
                System.out.println(le);
        }
    }

    private String getMMMDD (LogEntry le) {
        return le.getAccessTime().toString().substring(4,10);
    }

    public ArrayList<String> uniqueIPVisitsOnDay (String someday) {
        ArrayList<String> thatDay = new ArrayList<String>();
        if(someday == null || someday.isEmpty()) return thatDay;

        for (LogEntry le : records) {
            String mmm_dd = getMMMDD(le);
            String address = le.getIpAddress();
            if (someday.equals(mmm_dd) && !thatDay.contains(address))
                thatDay.add(address);
        }

        return thatDay;
    }

    public int countUniqueIPsInRange (int low, int high) {
        // make sure low <= high
        if (low > high) {
            int temp = low;
            low = high;
            high = temp;
        }

        // For this to get the correct results, we have to filter by
        // status code first, then by unique address.  The other way around
        // and we miss status codes that should have been counted.

        // First filter by status code.
        ArrayList<LogEntry> byStatus = new ArrayList<LogEntry>();
        for (LogEntry le : records) {
            if (low <= le.getStatusCode() && le.getStatusCode() <= high)
                byStatus.add(le);
        }

        // Now use the helper we created for countUniqueIPs, but on our array.
        return countUniqueByIP(byStatus);
    }

    public HashMap<String, Integer> countVisitsPerIP () {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (LogEntry le : records) {
            String address = le.getIpAddress();
            counts.put(address, counts.getOrDefault(address, 0) + 1);
        }
        return counts;
    }

    public int mostNumberVisitsByIP (HashMap<String, Integer> counts) {
        if (counts == null || counts.isEmpty()) return 0;

        int most = 0;
        for (String address : counts.keySet()) {
            int count = counts.get(address);
            if (count > most) most = count;
        }
        return most;
    }

    public ArrayList<String> iPsMostVisits (HashMap<String, Integer> counts) {
        ArrayList<String> results = new ArrayList<String>();
        // now check parameters, if they aren't valid results is empty so can return it.
        if (counts == null || counts.isEmpty()) return results;

        // What do we look for?  The maximum visits, so get that.
        int maxVisits = mostNumberVisitsByIP(counts);

        // Now we can search counts for value == maxVisits & put key into results.
        // Geez I miss filter().  And .max() for that matter.
        for (String key : counts.keySet()) {
            if (counts.get(key) == maxVisits) results.add(key);
        }

        return results;
    }  // iPsMostVisits

    public HashMap<String, ArrayList<String>> iPsForDays () {
        HashMap<String, ArrayList<String>> forDays = new HashMap<String, ArrayList<String>>();
        for (LogEntry le : records) {
            String mmm_dd = getMMMDD(le);
            // ArrayList.add() returns a success/fail code instead of itself, so we cannot
            // chain calls thru it.
            //forDays.put(mmm_dd, forDays.getOrDefault(mmm_dd, new ArrayList<String>()).add(le.getIpAddress()));
            ArrayList<String> today = forDays.getOrDefault(mmm_dd, new ArrayList<String>());
            today.add(le.getIpAddress());
            forDays.put(mmm_dd, today);
        }
        return forDays;
    }

    public String dayWithMostIPVisits (HashMap<String, ArrayList<String>> byDays) {
        if (byDays == null || byDays.isEmpty()) return "";

        String most = "";
        for (String day : byDays.keySet()) {
            if (byDays.get(day).size() > byDays.getOrDefault(most, new ArrayList<String>()).size()) {
                most = day;
            }
        }

        return most;
    }

    public ArrayList<String> iPsWithMostVisitsOnDay (HashMap<String, ArrayList<String>> byDays, String mmm_dd) {
        ArrayList<String> results = new ArrayList<String>();
        // validate parameters
        // If any problems, results is empty so we can return it.
        if (byDays == null || byDays.isEmpty()) return results;
        if (mmm_dd == null || mmm_dd.isEmpty()) return results;

        // Need just the list of addresses for the 1 target day.
        // And the most hits on that day to know what to search for.
        // Unfortunately countVisitsByIP() only works with LogEntry objects.
        // So build a similar result here, and then use mostNumberVisitsByIP() to
        // get the visit number to look for.
        ArrayList<String> theDay = byDays.getOrDefault(mmm_dd, new ArrayList<String>());
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (String address : theDay) {
            counts.put(address, counts.getOrDefault(address, 0) + 1);
        }
        int most = mostNumberVisitsByIP(counts);

        // OK, filter `counts` for value == `most`, put that into `results`.
        // results = counts.filterByValue(most).keySet();  Sigh.
        for (String key : counts.keySet()) {
            if (counts.get(key) == most)
                results.add(key);
        }

        return results;
    } // iPsWithMostVisitsOnDay

}  // LogAnalyzer
