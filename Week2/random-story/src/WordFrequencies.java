import edu.duke.FileResource;
import java.util.ArrayList;

public class WordFrequencies {

    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;

    public WordFrequencies () {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }

    public void findUnique () {
        FileResource resource = new FileResource("../../shared-data/romeo.txt");

        for (String s : resource.words()) {
            s = s.toLowerCase();
            int index = myWords.indexOf(s);
            if (index == -1) {
                // word not found, remember it and set the count
                // for the word to 1.
                myWords.add(s);
                myFreqs.add(1);
            } else {
                // we've seen this word before, and `index` is
                // its location.  So bump that location in `myFreqs`.
                int value = myFreqs.get(index);
                myFreqs.set(index, value + 1);
            }
        }
    }

    public void tester() {
        findUnique();
        System.out.println("# unique words: "+myWords.size());
        for (int k = 0; k < myWords.size(); k++) {
            System.out.println(myFreqs.get(k)+"\t"+myWords.get(k));
        }
    }
}
