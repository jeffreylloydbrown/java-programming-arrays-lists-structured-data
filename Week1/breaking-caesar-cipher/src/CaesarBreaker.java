public class CaesarBreaker {

    private String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public int[] countLetters (String message) {
        int[] counts = new int[ALPHABET.length()];
        for (int k = 0; k < message.length(); k++) {
            char ch = Character.toLowerCase(message.charAt(k));
            int index = ALPHABET.indexOf(ch);
            if (index != -1) counts[index] += 1;
        }
        return counts;
    }

    public int maxIndex (int[] freqs) {
        int maxIdx = 0;
        for (int k = 0; k < freqs.length; k++) {
            if (freqs[k] > freqs[maxIdx]) {
                maxIdx = k;
            }
        }
        return maxIdx;
    }

    public String decrypt (String encrypted) {
        CaesarCipher cc = new CaesarCipher();
        int[] freqs = countLetters(encrypted);
        int maxIndex = maxIndex(freqs);
        int LETTER_E_POSITION = ALPHABET.indexOf('e');
        int dkey = maxIndex - LETTER_E_POSITION;
        // if the maximum index is less than where 'e' is, we have
        // to wrap round to get the the decrypt key.
        if (maxIndex < LETTER_E_POSITION) {
            dkey = ALPHABET.length() - (LETTER_E_POSITION - maxIndex);
        }
        return cc.encrypt(encrypted, ALPHABET.length()-dkey);
    }
}
