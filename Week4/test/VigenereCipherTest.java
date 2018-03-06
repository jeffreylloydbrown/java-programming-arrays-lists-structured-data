import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.*;

class VigenereCipherTest {
    int[] ROME = {17, 14, 12, 4};

    @Test
    void encrypt () {
        VigenereCipher vc = new VigenereCipher(ROME);
        String encryptedStart = "Tcmp-pxety mj nikhqv htee mrfhtii tyv";
        String clearText = new FileResource("test/data/titus-small.txt").asString();
        String encrypted = vc.encrypt(clearText);
        assertEquals(encryptedStart, encrypted.substring(0, encryptedStart.length()));
        assertEquals("", vc.encrypt(""));
        assertEquals("", vc.encrypt(null));
        assertEquals("%$", vc.encrypt("%$"));
    }

    @Test
    void decrypt () {
        VigenereCipher vc = new VigenereCipher(ROME);
        String clearText = new FileResource("test/data/titus-small.txt").asString();
        assertEquals(clearText, vc.decrypt(vc.encrypt(clearText)));
        assertEquals("", vc.decrypt(vc.encrypt("")));
        assertEquals("", vc.decrypt(vc.encrypt(null)));
        assertEquals("%$", vc.decrypt("%$"));
    }

    @Test
    void showString () {
        VigenereCipher vc = new VigenereCipher(ROME);
        assertEquals("[17, 14, 12, 4]", vc.toString());
    }

}