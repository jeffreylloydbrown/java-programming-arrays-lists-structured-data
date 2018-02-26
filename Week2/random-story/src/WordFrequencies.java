import edu.duke.FileResource;
import java.util.ArrayList;

/**
 * You will write a program to determine the word that occurs the most often in a file.
 * If more than one word occurs as the most often, then return the first such word
 * found. You should make all words lowercase before counting them. Thus, “This” and
 * “this” will both be counted as the lowercase version of “this”. You should not
 * consider punctuation, so “end” and “end,” will be considered different words. Use
 * the WordFrequencies program in the lesson as a starting point.
 */
public class WordFrequencies {

    private ArrayList<String> myWords;      // holds the words in the file
    private ArrayList<Integer> myFreqs;     // holds the frequency of kth word in myWords

    public WordFrequencies () {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }

    /**
     * Write a void method findUnique that has no parameters. This method should first
     * clear both myWords and myFreqs, using the .clear() method. Then it selects a
     * file and then iterates over every word in the file, putting the unique words
     * found into myWords. For each word in the kth position of myWords, it puts the
     * count of how many times that word occurs from the selected file into the kth
     * position of myFreqs, as was demonstrated in the lesson.
     */
    public void findUnique () {
        myWords.clear();
        myFreqs.clear();

        //FileResource resource = new FileResource("../../shared-data/romeo.txt");
        //FileResource resource = new FileResource("test/data/testwordfreqs.txt");
        FileResource resource = new FileResource();

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
        System.out.println("Number of unique words: "+myWords.size());
        for (int k = 0; k < myWords.size(); k++) {
            System.out.println(myFreqs.get(k)+"\t"+myWords.get(k));
        }
        int maxIndex = findIndexOfMax();
        System.out.println("The word that occurs most often and its count are: "+
                myWords.get(maxIndex)+" "+myFreqs.get(maxIndex));
    }

    /** Find the index location of the largest value in `myFreqs`.
     * If there is a tie, then return the first such value.
     *
     * @return  The index of the largest value.  If `myFreqs` is empty, return -1.
     */
    public int findIndexOfMax() {
        if (myFreqs.size() == 0) return -1;

        int maxIndex = 0;  // location where max occurred
        for (int k = 0; k < myFreqs.size(); k++) {
            // "If there is a tie, return first such location" means use > instead
            // >=.
            if (myFreqs.get(k) > myFreqs.get(maxIndex))
                maxIndex = k;
        }

        return maxIndex;
    }
}
