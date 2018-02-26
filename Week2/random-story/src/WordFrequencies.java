import edu.duke.FileResource;
import java.util.ArrayList;

public class WordFrequencies {

    private ArrayList<String> myWords;

    public WordFrequencies () {
        myWords = new ArrayList<String>();
    }

    public void findUnique () {
        FileResource resource = new FileResource("../../shared-data/romeo.txt");

        for (String s : resource.words()) {
            s = s.toLowerCase();
            int index = myWords.indexOf(s);
            if (index == -1) {
                myWords.add(s);
            }
        }
    }

    public void tester() {
        findUnique();
        System.out.println("# unique words: "+myWords.size());
    }
}
