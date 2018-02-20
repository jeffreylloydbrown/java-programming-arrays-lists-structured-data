import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordPlayTest {
    @Test
    void isVowel() {
        WordPlay w = new WordPlay();
        assertTrue(w.isVowel('a'));
        assertTrue(w.isVowel('e'));
        assertTrue(w.isVowel('i'));
        assertTrue(w.isVowel('o'));
        assertTrue(w.isVowel('u'));
        assertTrue(w.isVowel('A'));
        assertTrue(w.isVowel('E'));
        assertTrue(w.isVowel('I'));
        assertTrue(w.isVowel('O'));
        assertTrue(w.isVowel('U'));
        assertFalse(w.isVowel('b'));
        assertFalse(w.isVowel('0'));
        assertFalse(w.isVowel('B'));
        assertFalse(w.isVowel('#'));
    }

    @Test
    void replaceVowels() {
    }

    @Test
    void emphasize() {
    }

}