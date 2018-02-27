import java.util.HashMap;

public class CodonCount {

    private HashMap<String, Integer> counts;

    public CodonCount() {
        counts = new HashMap<String, Integer>();
    }

    // Returns true if `s` has any actual value.
    private boolean hasValue (String s) { return s != null && ! s.isEmpty(); }

    // Returns true if `start` and `dna` are valid, false otherwise.
    private boolean validated (int start, String dna) {
        if (start != 0 && start != 1 && start != 2){
            System.out.println("buildCodonMap: invalid `start` value " + start +
                    ", must be 0, 1 or 2");
            return false;
        }
        if (! hasValue(dna)) {
            System.out.println("buildCodonMap: invalid empty `dna` value");
            return false;
        }
        if (start >= dna.length()) {
            System.out.println("buildCodonMap:  `dna` does not have enough characters");
            return false;
        }

        return true;
    }

    public void buildCodonMap (int start, String dna) {
        // Verify parameters.
        if (! validated(start, dna)) return;

        // Make sure counts starts cleared out.
        counts.clear();

        // Make sure `dna` doesn't have leading or trailing whitespace, and uppercase it.
        dna = dna.trim().toUpperCase();

        // Pluck off codons and add them to the map.
        while (start + 3 <= dna.length()) {
            String codon = dna.substring(start, start+3);
            // Might not have a full codon at the end, so check for that and ignore.
            if (3 == codon.length()) {
                counts.put(codon, counts.getOrDefault(codon, 0) + 1);
            }
            start += 3;
        }
    }  // buildCodonCount()

    public String getMostCommonCodon() {
        String max = "";
        for (String codon : counts.keySet()) {
            if (counts.get(codon) > counts.getOrDefault(max, -1))
                max = codon;
        }
        return max;
    }

    public void printCodonCounts (int start, int end) {
        // Make sure start <= end.
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        System.out.println("Counts of codons between "+start+" and "+end+" inclusive are:");
        for (String codon : counts.keySet()) {
            int count = counts.get(codon);
            if (start <= count && count <= end) {
                System.out.println(codon+":\t"+count);
            }
        }
        System.out.println();
    }  // printCodonCounts()

    public void runFrame (int frame, String dna) {
        if (! validated(frame, dna)) return;

        buildCodonMap(frame, dna);

        System.out.println("Reading frame starting with "+frame+
            " results in "+counts.size()+" unique codons");
        String codon = getMostCommonCodon();
        System.out.println("  and most common codon is "+codon+
            " with count "+counts.get(codon));
        printCodonCounts(1,5);
    }

}  // CodonCount
