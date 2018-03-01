import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class WordsInFilesTest {

    String[] filenames = {"brief1.txt", "brief2.txt", "brief3.txt", "brief4.txt"};

    @Test
    void tester() {
        WordsInFiles wf = new WordsInFiles();
        wf.buildWordFileMap();

        System.out.println("maximum number of files any word is in = "+wf.maxNumber());

        wf.showMap();
    }

    @Test
    void wordsInNumFiles() {
        WordsInFiles wf = new WordsInFiles();
        wf.buildWordFileMap();

        ArrayList<String> neg = wf.wordsInNumFiles(-1);
        assertTrue(neg.size() == 0, "negative number");
        ArrayList<String> zero = wf.wordsInNumFiles(0);
        assertTrue(zero.size() == 0, "zero case");
        ArrayList<String> three = wf.wordsInNumFiles(3);
        assertTrue(three.size() == 2, "three case");
        assertTrue(three.contains("cats") && three.contains("and"), "three content");
        ArrayList<String> two = wf.wordsInNumFiles(2);
        assertTrue(two.size() == 3, "two case");
        assertTrue(two.contains("love") && two.contains("are") && two.contains("dogs"), "two content");
    }

}