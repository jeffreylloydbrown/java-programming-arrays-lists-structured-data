import org.junit.jupiter.api.Test;

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

    @Test
    void tryKeyLength () {
        int[] error = {-1};
        VigenereBreaker vb = new VigenereBreaker();
        assertArrayEquals(error, vb.tryKeyLength("", 2, 'f'));
        assertArrayEquals(error, vb.tryKeyLength(null, 2, 'f'));
        assertArrayEquals(error, vb.tryKeyLength("asrrd", 0, 'q'));
    }

    @Test
    void breakVigenere () {
    }

}