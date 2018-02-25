import org.junit.jupiter.api.Test;
import edu.duke.*;
import static org.junit.jupiter.api.Assertions.*;

class TestCaesarCipherTwo {

    @Test
    void reallySimpleTests() {
        // Invalid keys
        CaesarCipherTwo badkeys = new CaesarCipherTwo(-1, 26);
        // That should set both keys to zero, which means no encryption.
        String msg = "Oh what a tangled web we weave";
        assertEquals(msg, badkeys.encrypt(msg));

        // degenerate cases
        CaesarCipherTwo breaker = new CaesarCipherTwo(17, 3);
        assertEquals("", breaker.decrypt(null), "null message");
        assertEquals("", breaker.decrypt(""), "empty message");

        // Remember we need tests where 'e' is the most frequent character.
        String lotsOfEs = "Just a test string with lots of eeeeeeeeeeeeeeeees";
        String encrypted = breaker.encrypt(lotsOfEs);
        assertEquals(lotsOfEs, breaker.decrypt(encrypted), "test case from assignment");
    }

    // As prescribed in the assignment.
    @Test
    void simpleTests() {
        String message = new FileResource("test/data/smallHamlet.txt").asString();
        ps("message", message);
        CaesarCipherTwo cc = new CaesarCipherTwo(17, 3);
        String encrypted = cc.encrypt(message);
        ps("encrypted", encrypted);
        String decrypted = cc.decrypt(encrypted);
        ps("decrypted", decrypted);
        assertEquals(message, decrypted);

        String broken = breakCaesarCipher(encrypted);
        ps("broken", broken);
        assertEquals(message, broken);
    }

    private String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private int ALPHABET_LENGTH = ALPHABET.length();
    private int LETTER_E_POSITION = ALPHABET.indexOf('e');

    // Returns true if the string has actual content.
    private boolean hasValue (String s) { return s != null && s.length() != 0; }

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
        WordLengths w = new WordLengths();
        return w.indexOfMax(values);
    }

    /** Given a string `s`, compute the encryption key assuming `e`
     * is the most frequent letter in `s`.
     *
     * @param s     A piece of text.
     * @return the likely encryption key.  If `s` is empty, return -1.
     */
    private int getKey (String s) {
        return hasValue(s) ? maxIndex(countLetters(s)) : -1;
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

    /** Return every other character in `message` starting at position `start`.
     *
     * For example, `halfOfString("Hello", 0)` returns `"Hlo"`, and
     * `halfOfString("Hello", 1)` returns `"el"`.
     *
     * @param message   The text to process.
     * @param start     The first character in `message` to return.
     * @return every other character in `message`.  If `start` is
     * outside of `message`, returns an empty string.  If `message`
     * is empty, also return an empty string.
     */
    private String halfOfString (String message, int start) {
        if (! hasValue(message)) return "";

        StringBuilder sb = new StringBuilder("");
        int messageLen = message.length();
        for (int k = start; k < messageLen; k += 2) {
            sb.append(message.charAt(k));
        }
        return sb.toString();
    }

    /** Given a string `encrypted` that was encrypted with the two keys
     * algorithm discussed in the previous lesson, attempt to determine
     * the two keys used to encrypt the message, print the two keys, and
     * return the decrypted message using those two keys.
     *
     * @param input     An encrypted message from
     *                      CaesarCipher.encryptTwoKeys().
     * @return the decrypted message.  If `encrypted` is empty, returns
     * an empty string.
     */
    private String breakCaesarCipher (String input) {
        if (! hasValue(input)) return "";

        // Calculate string of every other character in `encrypted`
        String oddChars = halfOfString(input, 0);
        String evenChars = halfOfString(input, 1);
        ps("oddChars", oddChars);
        ps("evenChars", evenChars);

        // Calculate a key used for each of those.
        // If the getKey() question on the practice quiz is wrong,
        // then keyOffset() goes INSIDE getKey() and I have to
        // adjust those tests.
        int key1 = keyOffset(getKey(oddChars));
        int key2 = keyOffset(getKey(evenChars));

        // Decrypt using CaesarCipher with those keys.
        return new CaesarCipherTwo(key1, key2).decrypt(input);
    }

    private void ps(String label, String value) {
        //System.out.println(label+" :'"+value+"'");
    }

}  // TestCaesarCipherTwo