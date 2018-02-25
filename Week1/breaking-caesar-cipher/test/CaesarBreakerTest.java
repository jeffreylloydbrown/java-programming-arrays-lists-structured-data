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

        result = breaker.decrypt(cipher.encrypt(null, 14));
        assertEquals("", result, "used null message");

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

    @Test
    void halfOfString() {
        // degenerate cases
        assertEquals("", breaker.halfOfString(null, 1), "null message");
        assertEquals("", breaker.halfOfString("", 1), "empty message");

        // case from the assignment
        String msg = "Qbkm Zgis";
        String expected_0 = "Qk gs";
        String expected_1 = "bmZi";
        assertEquals(expected_0, breaker.halfOfString(msg, 0), "starting at 0");
        assertEquals(expected_1, breaker.halfOfString(msg, 1), "starting at 1");

        // simplest real message.
        msg = "a";
        assertEquals("a", breaker.halfOfString(msg, 0), "\"a\" starting at 0");
        assertEquals("", breaker.halfOfString(msg, 1), "\"a\" starting at 1");
    }

    @Test
    void getKey() {

    }

    @Test
    void decryptTwoKeys() {

    }

}