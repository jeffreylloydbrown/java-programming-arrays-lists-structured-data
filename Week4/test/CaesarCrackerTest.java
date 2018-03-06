import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.*;

class CaesarCrackerTest {
    @Test
    void countLetters () {
        CaesarCracker cc = new CaesarCracker();
        assertEquals(1, cc.countLetters("a")[0]);
        assertEquals(2, cc.countLetters("bB")[1]);
        assertEquals(0, cc.countLetters("b")[0]);  // no 'a'
        assertEquals(0, cc.countLetters("*")[0]);
        assertEquals(0, cc.countLetters("")[0]);
        assertEquals(0, cc.countLetters(null)[0]);
    }

    @Test
    void maxIndex () {
        CaesarCracker cc = new CaesarCracker('e');  // for coverage
        assertEquals(1, cc.maxIndex(cc.countLetters("abB")));
        assertEquals(0, cc.maxIndex(cc.countLetters("AaBB")));
        assertEquals(0, cc.maxIndex(cc.countLetters("#")));
        assertEquals(0, cc.maxIndex(cc.countLetters(null)));
    }

    @Test
    void getKey () {
        CaesarCracker cc = new CaesarCracker('e');
        int key = 25;
        CaesarCipher cipher = new CaesarCipher(key);
        assertEquals(key, cc.getKey(cipher.encrypt("a bunch of eeeeeeeeeeeeeeeeeee")));
    }

    @Test
    void decrypt () {
        CaesarCracker cc = new CaesarCracker();
        String titusClear = new FileResource("test/data/titus-small.txt").asString();
        String titusEncrypted = new FileResource("test/data/titus-small_key5.txt").asString();
        assertEquals(titusClear, cc.decrypt(titusEncrypted));

        CaesarCracker portCracker = new CaesarCracker('a');
        String portClear = new FileResource("test/data/oslusiadas.txt").asString();
        String portEncrypted = new FileResource("test/data/oslusiadas_key17.txt").asString();
        assertEquals(portClear, portCracker.decrypt(portEncrypted));
    }

}