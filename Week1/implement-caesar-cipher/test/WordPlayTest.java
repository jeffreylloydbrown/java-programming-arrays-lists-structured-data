import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordPlayTest {
    WordPlay w = new WordPlay();

    @Test
    void isVowel() {
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
        assertTrue(w.replaceVowels("Hello World", '*').equals("H*ll* W*rld"), "case 1");
        assertTrue(w.replaceVowels("b", '*').equals("b"), "case 2");
        assertTrue(w.replaceVowels("", '*').equals(""), "case 3");
        assertTrue(w.replaceVowels(null, '#').equals(""), "case 4");
    }

    @Test
    void emphasize() {
        assertTrue(w.emphasize("dna ctgaaactga", 'a').equals("dn* ctg+*+ctg+"), "case 1");
        assertTrue(w.emphasize("Mary Bella Abracadabra", 'a').equals("M+ry Bell+ +br*c*d*br+"), "case 2");
        assertTrue(w.emphasize("bbb", 'a').equals("bbb"), "case 3");
        assertTrue(w.emphasize("", 'e').equals(""), "case 4");
    }

}