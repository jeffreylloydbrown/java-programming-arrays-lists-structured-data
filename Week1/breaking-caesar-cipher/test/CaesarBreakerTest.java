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
        // degenerate cases
        assertEquals(-1, breaker.getKey(null), "null message is -1 for key");
        assertEquals(-1, breaker.getKey(""), "empty message is -1 for key");

        // simplest real message.
        assertEquals(4, breaker.getKey("e"), "\"e\" returns 4");
        assertEquals(16, breaker.getKey("q"), "\"q\" returns 16");

        // longer messages.
        assertEquals( 0, breaker.getKey("aaabbccc"), "\"aaabbccc\" returns 0");
        assertEquals(2, breaker.getKey("abbccc"), "\"abbccc\" returns 2");
    }

    @Test
    void decryptTwoKeys() {
        // degenerate cases
        assertEquals("", breaker.decryptTwoKeys(null), "null message");
        assertEquals("", breaker.decryptTwoKeys(""), "empty message");

        String lotsOfEs = "Just a test string with lots of eeeeeeeeeeeeeeeees";
        String encrypted = cipher.encryptTwoKeys(lotsOfEs, 23, 2);
        assertEquals(lotsOfEs, breaker.decryptTwoKeys(encrypted), "test case from assignment");
    }

    @Test
    void practiceQuizAnswers() {
        String msg = "Top ncmy qkff vi vguv vbg ycpx";
        System.out.println("question 8: '"+cipher.encryptTwoKeys(msg, 26-2, 26-20)+"'");

        String encrypted = "Akag tjw Xibhr awoa aoee xakex znxag xwko";
        System.out.println("question 9: '"+breaker.decryptTwoKeys(encrypted)+"'");

        FileResource question10 = new FileResource("src/data/mysteryTwoKeysPractice.txt");
        encrypted = question10.asString();
        System.out.println("question 10 is first 5 words below, question 11 are the following keys:");
        msg = breaker.decryptTwoKeys(encrypted);
        // Only need the first five words, don't care to parse them out.
        System.out.println("   '"+msg.substring(0, 80)+"'");
    }

}