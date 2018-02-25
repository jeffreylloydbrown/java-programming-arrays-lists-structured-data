/**
 * You should start by writing the decryption method explained in the lesson
 * that decrypts a message that was encrypted with one key, using statistical
 * letter frequencies of English text. Then you will add code to be able to
 * decrypt a message that was encrypted with two keys, using ideas from the
 * single key decryption method and the encryption with two keys method from
 * the program you wrote in the last lesson.
 *
 * Idea for two keys decrypt method. Recall that in using two keys, key1 and
 * key2, key1 was used to encrypt every other character, starting with the first,
 * of the String, and key2 was used to encrypt every other character, starting
 * with the second. In order to decrypt the encrypted String, it may be easier
 * to split the String into two Strings, one String of all the letters encrypted
 * with key1 and one String of all the letters encrypted with key2. Then use the
 * algorithm from the lesson to determine the key for each String, and then use
 * those keys and the two key encryption method to decrypt the original
 * encrypted message.
 *
 * For example, if the encrypted message was “Qbkm Zgis”, then you would split
 * this String into two Strings: “Qk gs”, representing the characters in the odd
 * number positions and “bmZi” representing the characters in the even number
 * positions. Then you would get the key for each half String and use the two
 * key encryption method to find the message. Note this example is so small it
 * likely won’t find the keys, but it illustrates how to take the Strings apart.
 */
public class CaesarBreaker {

    private String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private int ALPHABET_LENGTH = ALPHABET.length();
    private int LETTER_E_POSITION = ALPHABET.indexOf('e');

    public int[] countLetters (String message) {
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
    public int maxIndex (int[] values) {
        WordLengths w = new WordLengths();
        return w.indexOfMax(values);
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

    public String decrypt (String encrypted) {
        CaesarCipher cc = new CaesarCipher();
        WordLengths w = new WordLengths();
        int[] freqs = countLetters(encrypted);
        int maxIndex = maxIndex(freqs);
        //int dkey = maxIndex - LETTER_E_POSITION;
        // if the maximum index is less than where 'e' is, we have
        // to wrap round to get the the decrypt key.
        //if (maxIndex < LETTER_E_POSITION) {
        //    dkey = ALPHABET_LENGTH - (LETTER_E_POSITION - maxIndex);
        //}
        int dkey = keyOffset(maxIndex);
        return cc.encrypt(encrypted, ALPHABET_LENGTH-dkey);
    }

    // Returns true if the string has actual content.
    private boolean hasValue (String s) { return s != null && s.length() != 0; }

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
    public String halfOfString (String message, int start) {
        if (! hasValue(message)) return "";

        StringBuilder sb = new StringBuilder("");
        int messageLen = message.length();
        for (int k = start; k < messageLen; k += 2) {
            sb.append(message.charAt(k));
        }
        return sb.toString();
    }

    /** Given a string `s`, compute the encryption key assuming `e`
     * is the most frequent letter in `s`.
     *
     * @param s     A piece of text.
     * @return the likely encryption key.  If `s` is empty, return -1.
     */
    public int getKey (String s) {
        return hasValue(s) ? maxIndex(countLetters(s)) : -1;
    }

    /** Given a string `encrypted` that was encrypted with the two keys
     * algorithm discussed in the previous lesson, attempt to determine
     * the two keys used to encrypt the message, print the two keys, and
     * return the decrypted message using those two keys.
     *
     * @param encrypted     An encrypted message from
     *                      CaesarCipher.encryptTwoKeys().
     * @return the decrypted message.  If `encrypted` is empty, returns
     * an empty string.
     */
    public String decryptTwoKeys (String encrypted) {
        if (! hasValue(encrypted)) return "";

        // Calculate string of every other character in `encrypted`
        String oddChars = halfOfString(encrypted, 0);
        String evenChars = halfOfString(encrypted, 1);
        ps("oddChars", oddChars);
        ps("evenChars", evenChars);

        // Calculate a key used for each of those.
        // If the getKey() question on the practice quiz is wrong,
        // then keyOffset() goes INSIDE getKey() and I have to
        // adjust those tests.
        int key1 = keyOffset(getKey(oddChars));
        int key2 = keyOffset(getKey(evenChars));

        // We're told to print the keys found.
        System.out.println("key1 = "+key1+", key2 = "+key2);

        // Decrypt using CaesarCipher with the inverse keys.
        return new CaesarCipher().encryptTwoKeys(encrypted, ALPHABET_LENGTH-key1, ALPHABET_LENGTH-key2);
    }

    private void ps(String label, String value) {
        //System.out.println(label+" :'"+value+"'");
    }

}  // CaesarBreaker
