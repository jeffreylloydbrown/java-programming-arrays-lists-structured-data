import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        // verify parameters
        if (message == null || message.isEmpty()) return "";
        if (whichSlice < 0 || totalSlices < 1) return message;

        // Build up the slice then return it.
        StringBuilder sb = new StringBuilder();
        for (int k = whichSlice; k < message.length(); k += totalSlices)
            sb.append(message.charAt(k));
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        // verify parameters.  This really wants to throw, but we don't know that
        // yet.  So return an array of 1 int, set to -1.
        int[] error = {-1};
        if (encrypted == null || encrypted.isEmpty()) return error;
        if (klength < 1) return error;

        // For each spot in the key, create a slice from the encrypted text,
        // and crack it to get the key it used.  Put that key into the key array.
        int[] key = new int[klength];
        CaesarCracker cracker = new CaesarCracker(mostCommon);
        for (int slice = 0; slice < key.length; slice++) {
            String thisSlice = sliceString(encrypted, slice, klength);
            key[slice] = cracker.getKey(thisSlice);
        }
        return key;
    }

    public void breakVigenere () {
        String encrypted = new FileResource().asString();
        int[] key = tryKeyLength(encrypted, 5, 'e');
        VigenereCipher cipher = new VigenereCipher(key);
        System.out.println(cipher.decrypt(encrypted));
    }

}
