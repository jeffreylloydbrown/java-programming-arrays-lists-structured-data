import org.junit.jupiter.api.Test;
import edu.duke.*;
import static org.junit.jupiter.api.Assertions.*;

class TestCaesarCipher {

    @Test
    public void simpleTests () {
        String message = new FileResource("test/data/wordsLotsOfEs.txt").asString();
        CaesarCipher cipher = new CaesarCipher(18);
        ps("message", message);
        String encrypted = cipher.encrypt(message);
        ps("encrypted", encrypted);
        String decrypted = cipher.decrypt(encrypted);
        ps("decrypted", decrypted);
        assertEquals(message, decrypted);
        System.out.println("now breaking it...");
        String broken = breakCaesarCipher(encrypted);
        ps("broke encrypt", broken);
        assertEquals(message, broken);
    }

    private void qps (String label, String value) {
        System.out.println(label+" '"+value+"'");
    }

    @Test
    void finalQuiz () {
        String q1msg = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        qps("Q1.", new CaesarCipher(15).encrypt(q1msg));

        WordLengths w = new WordLengths();
        FileResource errors = new FileResource("test/data/errors.txt");
        int[] counts = new int[200];
        w.countWordLengths(errors, counts);
        qps("Q4.", ""+w.indexOfMax(counts));

        FileResource manywords = new FileResource("test/data/manywords.txt");
        int[] manyCounts = new int[200];
        w.countWordLengths(manywords, manyCounts);
        qps("Q5.", ""+w.indexOfMax(manyCounts));
    }

    private String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private int ALPHABET_LENGTH = ALPHABET.length();
    private int LETTER_E_POSITION = ALPHABET.indexOf('e');

    private int[] countLetters (String message) {
        int[] counts = new int[ALPHABET_LENGTH];
        for (int k = 0; k < message.length(); k++) {
            char ch = Character.toLowerCase(message.charAt(k));
            int index = ALPHABET.indexOf(ch);
            if (index != -1) counts[index] += 1;
        }
        return counts;
    }

    /** Find the index of the largest value in `values`.
     * @param values    the array of integers to check.
     * @return  the index with the largest value, or -1 if `values`
     * has length 0.
     */
    private int maxIndex (int[] values) {
        if (values.length == 0)
            return -1;

        int maxIdx = 0;
        for (int k = 0; k < values.length; k++)
            if (values[k] > values[maxIdx])
                maxIdx = k;
        return maxIdx;
    }

    private void ps (String label, String value) {
        //System.out.println(label+": '"+value+"'");
    }

    // keyOffset() applies the 'e' assumption to the passed in value,
    // which is supposed to be the most frequent letter seen in a piece
    // of text.
    private int keyOffset(int value) {
        int key = value - LETTER_E_POSITION;
        // This is so much more clear, but I'm going to follow
        // the lesson code.
        //
        // if (key < 0)
        //    key += ALPHABET_LENGTH;
        if (value < LETTER_E_POSITION)
            key = ALPHABET_LENGTH - (LETTER_E_POSITION - value);
        return key;
    }

    private String breakCaesarCipher (String input) {
        int[] freqs = countLetters(input);
        int maxIndex = maxIndex(freqs);
        int dkey = keyOffset(maxIndex);
        CaesarCipher cc = new CaesarCipher(dkey);
        return cc.decrypt(input);
    }

}  // TestCaesarCipher