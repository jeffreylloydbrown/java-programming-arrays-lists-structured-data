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

        int max = wf.maxNumber();
        System.out.println("maximum number of files any word is in = "+max);

        ArrayList<String> maxWords = wf.wordsInNumFiles(max);
        for (String word : maxWords)
            wf.printFilesIn(word);

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

    @Test
    void maxNumber() {
        WordsInFiles wf = new WordsInFiles();

        // Before words loaded, should get -1 back.
        assertTrue(wf.maxNumber() == -1, "no words loaded is -1");

        // Load
        wf.buildWordFileMap();

        // With our 4 test files, maxNumber() should be 3.
        assertTrue(wf.maxNumber() == 3, "loaded 4 brief files");
    }

    @Test
    void printFilesIn() {
        WordsInFiles wf = new WordsInFiles();
        wf.buildWordFileMap();

        // null and empty don't cause a crash.
        wf.printFilesIn(null);
        wf.printFilesIn("");

        // word not in the file does not crash, prints message.
        System.out.println("expect bccfgh not found");
        wf.printFilesIn("bccfgh");

        // cat appears in 3 files.
        System.out.println("expect cats in brief1, brief3 and brief4");
        wf.printFilesIn("cats");
    }

}