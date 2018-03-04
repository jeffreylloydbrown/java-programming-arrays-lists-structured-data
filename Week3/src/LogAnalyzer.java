
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

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
     
}  // LogAnalyzer
