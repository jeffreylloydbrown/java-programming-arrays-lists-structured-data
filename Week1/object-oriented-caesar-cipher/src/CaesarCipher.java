
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipher {
    private String baseAlphabet = "abcdefghijklmnopqrstuvwxyz";
    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;
    
    public CaesarCipher (int key) {
        alphabet = baseAlphabet.toUpperCase() + baseAlphabet.toLowerCase();
        mainKey = key;
        shiftedAlphabet = shiftAlphabet(key).toUpperCase() + 
            shiftAlphabet(key).toLowerCase();
    }
    
    private boolean hasValue (String s) {
        return s != null && ! s.isEmpty();
    }
    
    // Encapsulate the key shift since it will get done in several places.
    private String shiftAlphabet(int key) {
        return baseAlphabet.substring(key) + baseAlphabet.substring(0, key);
    }
    
    public String encrypt (String input) {
        
        // parameter checks.  input has something in it,
        // and key isn't zero (which would mean "don't rotate the letters"
        if (! hasValue(input)) return "";
        if (mainKey == 0) return input;

        // Start with a StringBuilder we can update below.
        StringBuilder encrypted = new StringBuilder(input);

        // Walk the input string and transform each letter that exists in
        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = alphabet.indexOf(currChar);
            if (idx != -1) {
                char newChar = shiftedAlphabet.charAt(idx);
                encrypted.setCharAt(i, newChar);
            }
        }

        return encrypted.toString();
    }
    
    public String decrypt (String input) {
        CaesarCipher cc = new CaesarCipher(baseAlphabet.length()-mainKey);
        return cc.encrypt(input);
    }
        
}
