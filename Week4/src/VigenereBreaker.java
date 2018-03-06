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

        //
        int[] key = new int[klength];
        //WRITE YOUR CODE HERE
        return key;
    }

    public void breakVigenere () {
        //WRITE YOUR CODE HERE
    }
    
}
