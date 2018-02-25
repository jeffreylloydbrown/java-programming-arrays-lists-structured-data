/**
 * 
 */
public class CaesarCipherTwo {

    private String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private int ALPHABET_LENGTH = ALPHABET.length();
    private int LETTER_E_POSITION = ALPHABET.indexOf('e');

    private String alphabet;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int mainKey1;
    private int mainKey2;

    public CaesarCipherTwo (int key1, int key2) {
        alphabet = ALPHABET.toUpperCase() + ALPHABET.toLowerCase();
        mainKey1 = validateKey(key1);
        mainKey2 = validateKey(key2);
        shiftedAlphabet1 = shiftAlphabet(mainKey1).toUpperCase() +
                shiftAlphabet(mainKey1).toLowerCase();
        shiftedAlphabet2 = shiftAlphabet(mainKey2).toUpperCase() +
                shiftAlphabet(mainKey2).toLowerCase();
    }

    // Make sure key is in the range 0..ALPHABET_LENGTH-1.  It it is not,
    // set it to zero.  No idea what happens in Java if constructors
    // throw exceptions.
    private int validateKey (int key) {
        return (key < 0 || key > ALPHABET_LENGTH-1) ? 0 : key;
    }

    // Encapsulate the key shift since it will get done in several places.
    private String shiftAlphabet(int key) {
        return ALPHABET.substring(key) + ALPHABET.substring(0, key);
    }

    // Returns true if the string has actual content.
    private boolean hasValue (String s) { return s != null && s.length() != 0; }

    /** Encrypts `input` and returns it.
     * @param input     The cleartext to be encrypted.
     * @return the encrypted version of `input`.  If `input` is an empty string,
     * returns an empty string.
     */
    public String encrypt (String input) {
        // parameter checks.  If input has nothing, or both keys
        // are zero, we have no work to do.
        if (!hasValue(input)) return "";
        if (mainKey1 == 0 && mainKey2 == 0) return input;

        // Start with a StringBuilder we can update below.
        StringBuilder encrypted = new StringBuilder(input);

        // Walk the input string and transform each letter that exists in
        // our alphabet
        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = alphabet.indexOf(currChar);
            if (idx != -1) {
                // Which alphabet do I use?  even i is key1, odd i is key2
                String shiftedAlphabet = (i % 2 == 0) ? shiftedAlphabet1: shiftedAlphabet2;
                char newChar = shiftedAlphabet.charAt(idx);
                encrypted.setCharAt(i, newChar);
            }
        }

        return encrypted.toString();
    }

    /** Decrypt a message that this object encrypted.  If another object
     *  encrypted the message, it won't decrypt correctly unless that other object
     *  used the same keys.
     *
     *  This method does not attempt to determine keys (that is, it doesn't break
     *  the encryption).
     *
     * @param input     Encrypted text returned from this object's `encrypt` method
     * @return          Clear text version of `input`.  If `input` is empty, returns
     * an empty string.
     */
    public String decrypt (String input) {
        return new CaesarCipherTwo(ALPHABET_LENGTH-mainKey1, ALPHABET_LENGTH-mainKey2).encrypt(input);
    }

}  // CaesarBreaker
