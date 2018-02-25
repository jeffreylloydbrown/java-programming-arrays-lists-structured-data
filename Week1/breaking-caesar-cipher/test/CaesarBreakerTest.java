import org.junit.jupiter.api.Test;
import edu.duke.*;
import static org.junit.jupiter.api.Assertions.*;

class CaesarBreakerTest {
    CaesarCipher cipher = new CaesarCipher();
    CaesarBreaker breaker = new CaesarBreaker();

    // For this test to pass, the cases must all have `e` with the highest letter
    // count.  Otherwise, our assumption about frequencies of `e` for decrypting
    // is violated.
    @Test
    void decrypt() {
        String msg = "";
        String result = breaker.decrypt(cipher.encrypt(msg, 14));
        assertEquals(msg, result, "empty message");

        msg = "Just a bunch of eeeeeeeeeeeeeeeeeeeees!";
        result = breaker.decrypt(cipher.encrypt(msg, 14));
        assertEquals(msg, result, msg);

    }

    // Demonstrate violating the assumption means the string does not decrypt.
    @Test
    void decryptNotEnoughEs() {
        String msg = "Oh, my, 27 messes!";  // 3 s, 2 m, 2 e.  Fails.
        String result = breaker.decrypt(cipher.encrypt(msg, 19));
        assertNotEquals(msg, result, msg);
    }

    // Maximum index is before 4 (before where e appears in the alphabet) to
    // force a wrap-around.
    @Test
    void maxIndexBefore4() {
        String msg = "abeeeeeeeeeeeeed";
        String result = breaker.decrypt(cipher.encrypt(msg, 9));
        assertEquals(msg, result);
    }

}