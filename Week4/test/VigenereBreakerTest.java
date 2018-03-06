import edu.duke.FileResource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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

}