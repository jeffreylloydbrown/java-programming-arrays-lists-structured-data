import org.junit.jupiter.api.Test;
import edu.duke.*;
import static org.junit.jupiter.api.Assertions.*;

class CaesarCipherTest {

    CaesarCipher cc = new CaesarCipher();

    @Test
    void encrypt() {
        String encrypted, reEncrypted;

        encrypted = cc.encrypt(null, 23);
        assertTrue(encrypted.equals(""), "null case");
        encrypted = cc.encrypt("", 23);
        assertTrue(encrypted.equals(""), "empty string case");

        String message = "SHOULD NOT CHANGE";
        encrypted = cc.encrypt(message, 0);
        assertTrue(encrypted.equals(message), "case where key 0 is unencrypted");

        int key = 17;
        encrypted = cc.encrypt(message.toUpperCase(), key);
        reEncrypted = cc.encrypt(encrypted, 26-key);
        assertTrue(message.equals(reEncrypted), "symmetric encrypt gives original message");

        encrypted = cc.encrypt("FIRST LEGION ATTACK EAST FLANK!", 23);
        assertTrue(encrypted.equals("CFOPQ IBDFLK XQQXZH BXPQ CIXKH!"), "case 1 from Assignment");

        encrypted = cc.encrypt("First Legion", 23);
        assertTrue(encrypted.equals("Cfopq Ibdflk"), "case 2 from Assignment");
        encrypted = cc.encrypt("First Legion", 17);
        assertTrue(encrypted.equals("Wzijk Cvxzfe"), "case 3 from Assignment");
    }

    @Test
    void encryptTwoKeys() {
        String encrypted, reEncrypted;

        encrypted = cc.encryptTwoKeys(null, 23, 17);
        assertTrue(encrypted.equals(""), "null case");
        encrypted = cc.encryptTwoKeys("", 23, 17);
        assertTrue(encrypted.equals(""), "empty string case");

        encrypted = cc.encryptTwoKeys("First Legion", 23, 17);
        assertTrue(encrypted.equals("Czojq Ivdzle"), "case from Assignment");

        String message = "Should Be Unchanged";
        encrypted = cc.encryptTwoKeys(message, 0, 0);
        assertTrue(encrypted.equals(message), "case that no rotation is unencrypted");

        encrypted = cc.encryptTwoKeys(message, 23, 17);
        reEncrypted = cc.encryptTwoKeys(encrypted, 26-23, 26-17);
        assertTrue(message.equals(reEncrypted), "case of symmetric double encryption -> not encrypted");
    }

    @Test
    void testCaesar () {
        String encrypted;
        FileResource fr = new FileResource("simple-message.txt");
        String message = fr.asString();
        System.out.println("message: '" + message + "'");
        System.out.println("key is 0, should match message");
        encrypted = cc.encrypt(message, 0);
        System.out.println("  "+encrypted);
        assertTrue(encrypted.equals(message), "case of 0 rotation so unencrypted");

        int key = 12;
        encrypted = cc.encrypt(message, key);
        String reEncrypted = cc.encrypt(encrypted, 26-key);
        assertTrue(message.equals(reEncrypted), "symmetric encrypt -> not encrypted");

    }

}