import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaesarCipherTest {
    @Test
    void encryptDecryptLetter () {
        CaesarCipher cc = new CaesarCipher(14);
        assertEquals('d', cc.decryptLetter(cc.encryptLetter('d')));
        assertEquals('Q', cc.decryptLetter(cc.encryptLetter('Q')));
        assertEquals('#', cc.encryptLetter('#'));
        assertEquals('$', cc.decryptLetter('$'));
    }

    @Test
    void encryptDecryptString () {
        CaesarCipher cc = new CaesarCipher(12);
        assertEquals("Hello", cc.decrypt(cc.encrypt("Hello")));
        assertEquals("A.B$cD", cc.decrypt(cc.encrypt("A.B$cD")));
        assertEquals("!#!$", cc.encrypt("!#!$"));
        assertEquals("!#!$", cc.decrypt("!#!$"));
        assertEquals("", cc.encrypt(""));
        assertEquals("", cc.decrypt(""));
        assertEquals("", cc.encrypt(null));
        assertEquals("", cc.decrypt(null));
    }

    @Test
    void showString () {
        assertEquals("19", new CaesarCipher(19).toString());
    }

}