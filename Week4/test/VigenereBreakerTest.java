import edu.duke.FileResource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class VigenereBreakerTest {
    @Test
    void sliceString () {
        VigenereBreaker vb = new VigenereBreaker();
        String letters = "abcdefghijklm";
        assertEquals("adgjm", vb.sliceString(letters, 0, 3));
        assertEquals("behk", vb.sliceString(letters,1,3));
        assertEquals("cfil", vb.sliceString(letters, 2, 3));
        assertEquals("aeim", vb.sliceString(letters,0,4));
        assertEquals("bfj", vb.sliceString(letters, 1, 4));
        assertEquals("cgk", vb.sliceString(letters,2,4));
        assertEquals("dhl", vb.sliceString(letters,3,4));
        assertEquals("afk", vb.sliceString(letters,0,5));
        assertEquals("bgl", vb.sliceString(letters,1,5));
        assertEquals("chm", vb.sliceString(letters,2,5));
        assertEquals("di", vb.sliceString(letters,3,5));
        assertEquals("ej", vb.sliceString(letters, 4,5));

        assertEquals("", vb.sliceString("", 0,3));
        assertEquals("", vb.sliceString(null, 0, 3));
        assertEquals(letters, vb.sliceString(letters, -1, 5));
        assertEquals(letters, vb.sliceString(letters,0, -1));
    }

    private int[] stringToKey (String keyText) {
        int[] key = new int[keyText.length()];
        for (int k = 0; k < key.length; k++)
            key[k] = keyText.charAt(k) - 'a';
        return key;
    }

    @Test
    void tryKeyLength () {
        int[] error = {-1};
        VigenereBreaker vb = new VigenereBreaker();
        assertArrayEquals(error, vb.tryKeyLength("", 2, 'f'));
        assertArrayEquals(error, vb.tryKeyLength(null, 2, 'f'));
        assertArrayEquals(error, vb.tryKeyLength("asrrd", 0, 'q'));

        String encrypted = new FileResource("test/data/athens_keyflute.txt").asString();
        int[] key = stringToKey("flute");
        assertArrayEquals(key, vb.tryKeyLength(encrypted, key.length, 'e'));
    }

    @Test
    void readDictionary() {
        VigenereBreaker vb = new VigenereBreaker();
        // Checking dictionaries that start and end with no special characters.  English ends with
        // accented e words, so can't use that.  I don't want to figure out special characters.
        HashSet<String> italian = vb.readDictionary(new FileResource("src/dictionaries/Italian"));
        assertTrue(italian.contains("achille"));        // first word in file
        assertTrue(italian.contains("zuzzurellone"));   // last word in file

        HashSet<String> dutch = vb.readDictionary(new FileResource("src/dictionaries/Dutch"));
        assertTrue(dutch.contains("a"));                // first word in file
        assertTrue(dutch.contains("keesje"));           // last word in file
    }

    @Test
    void countWords () {
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> english = vb.readDictionary(new FileResource("src/dictionaries/English"));
        assertEquals(1, vb.countWords("a", english));
        assertEquals(2, vb.countWords("COUNT WordS", english));
        assertEquals(0, vb.countWords("#@@#$ $#@ @@", english));
        assertEquals(16 , vb.countWords("Now is the time for all good people to come to the aid of their country", english));

        assertEquals(0, vb.countWords("", english));
        assertEquals(0, vb.countWords(null, english));
        assertEquals(0, vb.countWords("valid words", new HashSet<String>()));
        assertEquals(0, vb.countWords("valid words", null));
    }

    @Test
    void breakForLanguage() {
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> english = vb.readDictionary(new FileResource("src/dictionaries/English"));

        // Decrypt athens_keyflute.txt, compare it to its original.  They must match.
        String encrypted = new FileResource("test/data/athens_keyflute.txt").asString();
        String clearText = new FileResource("test/data/athens.txt").asString();
        String decrypted = vb.breakForLanguage(encrypted, english);
        assertEquals(clearText, decrypted);

        // degenerate cases to confirm null safety
        assertEquals(0, vb.countWords("", english));
        assertEquals(0, vb.countWords(null, english));
        assertEquals(0, vb.countWords(encrypted, new HashSet<String>()));
        assertEquals(0, vb.countWords(encrypted, null));
    }

    @Test
    void breakVigenere () {
        System.out.println("Select test/data/athens_keyflute.txt for this");
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }

    @Test
    void practiceQuizKnownLangKnownLength() {
        VigenereBreaker vb = new VigenereBreaker();
        String encrypted = new FileResource("test/data/secretmessage1.txt").asString();
        int[] key = vb.tryKeyLength(encrypted, 4, 'e');
        System.out.println("Q1.  "+ Arrays.toString(key));
        VigenereCipher vc = new VigenereCipher(key);
        System.out.println("Q2 answer is the first line shown here");
        System.out.println(vc.decrypt(encrypted).substring(0, 240));
    }

    @Test
    void practiceQuizKnownLangUnknownLength() {
        VigenereBreaker vb = new VigenereBreaker();
        String encrypted = new FileResource("test/data/secretmessage2.txt").asString();
        HashSet<String> english = vb.readDictionary(new FileResource("src/dictionaries/English"));
        //String decrypted = vb.breakForLanguage(encrypted, english);

        // Need to remember the decrypt with the most words in the dictionary.
        String bestDecrypt = "";
        int bestCount = 0;
        int bestKeyLength = 0;

        // Try various key lengths, see which gives most words in dictionary.
        for (int klength = 1; klength < 100; klength++) {
            int[] key = vb.tryKeyLength(encrypted, klength, 'e');
            String decrypt = new VigenereCipher(key).decrypt(encrypted);
            int count = vb.countWords(decrypt, english);
            if (count > bestCount) {
                bestCount = count;
                bestDecrypt = decrypt;
                bestKeyLength = klength;
            }
        }

        System.out.println("Q1. "+bestKeyLength);
        System.out.println("Q2. "+bestCount);
        System.out.println("Q3.  Answer is the first line of the following");
        System.out.println(bestDecrypt.substring(0, 120));
        int[] key38 = vb.tryKeyLength(encrypted, 38, 'e');
        String decrypt38 = new VigenereCipher(key38).decrypt(encrypted);
        System.out.println("Q4. "+vb.countWords(decrypt38, english));
    }

}