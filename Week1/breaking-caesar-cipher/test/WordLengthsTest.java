import org.junit.jupiter.api.Test;
import edu.duke.*;

import static org.junit.jupiter.api.Assertions.*;

class WordLengthsTest {
    WordLengths w = new WordLengths();

    private String check (int actual, int expected) { return expected == actual ? "Pass" : "FAIL"; }

    // Test required in assignment (does not hit the len > counts size case
    @Test
    void countWordLengths31() {
        // See the assignment for why these are here.
        FileResource fr = new FileResource("src/data/smallHamlet.txt");
        int[] counts = new int[31];
        // Expected results
        int[] expected = {0, 0, 2, 3, 2, 1, 1, 1, 2, 0, 0,
                             1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                             0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                          };
        w.countWordLengths(fr, counts);
        System.out.println("countWordLengths31 results");
        for (int k = 0; k < counts.length; k++) {
            System.out.println(k + ":\t" + counts[k] + "\t" + check(counts[k], expected[k]));
        }

        assertArrayEquals(expected, counts);
    }

    // Hits len > counts size case, with other words of length size-1 in test data
    @Test
    void countWordLengths9() {
        // See the assignment for why these are here.
        FileResource fr = new FileResource("src/data/smallHamlet.txt");
        int[] counts = new int[9];
        // Expected results
        int[] expected = {0, 0, 2, 3, 2, 1, 1, 1, 3};
        w.countWordLengths(fr, counts);
        System.out.println("countWordLengths9 results");
        for (int k = 0; k < counts.length; k++) {
            System.out.println(k + ":\t" + counts[k] + "\t" + check(counts[k], expected[k]));
        }

        assertArrayEquals(expected, counts);
    }

    // Hits len > counts size case, but no other word of length size-1 in test data.
    @Test
    void countWordLengths10() {
        // See the assignment for why these are here.
        FileResource fr = new FileResource("src/data/smallHamlet.txt");
        int[] counts = new int[10];
        // Expected results
        int[] expected = {0, 0, 2, 3, 2, 1, 1, 1, 2, 1};
        w.countWordLengths(fr, counts);
        System.out.println("countWordLengths10 results");
        for (int k = 0; k < counts.length; k++) {
            System.out.println(k + ":\t" + counts[k] + "\t" + check(counts[k], expected[k]));
        }

        assertArrayEquals(expected, counts);
    }

    // Test with an empty data file, shouldn't crash and counts are all zero.
    @Test
    void emptyDataFile() {
        FileResource fr = new FileResource("src/data/empty.txt");
        int[] counts = new int[4];
        // Expected results
        int[] expected = {0, 0, 0, 0};

        w.countWordLengths(fr, counts);

        assertArrayEquals(expected, counts);
    }

    //
    @Test
    void indexOfMax() {
        int[] noSize = {};
        assertEquals(-1, w.indexOfMax(noSize));

        int[] one = {2};
        assertEquals(0, w.indexOfMax(one));

        int[] two_1 = {2, 1};
        assertEquals(0, w.indexOfMax(two_1));

        int[] two_2 = {1, 2};
        assertEquals(1, w.indexOfMax(two_2));

        int[] three_1 = {3, 2, 1};
        assertEquals(0, w.indexOfMax(three_1));

        int[] three_2 = {1, 3, 2};
        assertEquals(1, w.indexOfMax(three_2));

        int[] three_3 = {2, 1, 3};
        assertEquals(2, w.indexOfMax(three_3));
    }

    // Test required by assignment
    @Test
    void testCountWordLengths() {
        FileResource empty = new FileResource("src/data/empty.txt");
        int[] counts = new int[12];
        int[] expected = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        w.countWordLengths(empty, counts);
        assertArrayEquals(expected, counts);
        assertEquals(0, w.indexOfMax(counts));

        FileResource fr = new FileResource("src/data/smallHamlet.txt");
        int[] hamletCounts = new int[13];
        int[] hamletExpected = {0, 0, 2, 3, 2, 1, 1, 1, 2, 0, 0, 1, 0};
        w.countWordLengths(fr, hamletCounts);
        assertArrayEquals(hamletExpected, hamletCounts);
        assertEquals(3, w.indexOfMax(hamletCounts));
    }

}