import edu.duke.*;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class WordsInFiles {

    private HashMap<String, ArrayList<String>> wordLists;

    public WordsInFiles() {
        wordLists = new HashMap<String, ArrayList<String>>();
    }

    private boolean hasValue (String s) { return s != null && ! s.isEmpty(); }

    private void addWordsFromFile (File f) {
        FileResource fr = new FileResource(f);
        String filename = f.getName();
        for (String word : fr.words()) {
            if (wordLists.containsKey(word)) {
                ArrayList<String> fileList = wordLists.get(word);
                // fileList cannot be null.
                assert(fileList != null);
                if (! fileList.contains(filename)) {
                    fileList.add(filename);
                    // we changed it, must save it.
                    wordLists.put(word, fileList);
                }
            } else {
                ArrayList<String> fileList = new ArrayList<String>();
                fileList.add(filename);
                wordLists.put(word, fileList);
            }
        }
    }  // addWordsFromFile

    public void buildWordFileMap() {
        wordLists.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }  // buildWordFileMap

    public int maxNumber() {
        if (wordLists.isEmpty()) return -1;

        // we have stuff, so find the largest number of files and return that.
        // The number is the largest value.size(), where value is the ArrayList<String>
        // in the map.
        int max = 0;
        for (String word : wordLists.keySet()) {
            int size = wordLists.get(word).size();
            if (size > max) max = size;
        }
        return max;
    }

    public ArrayList<String> wordsInNumFiles (int number) {
        ArrayList<String> exactly = new ArrayList<String>();

        // No sense in doing anything for negative or zero requests, cuz
        // there won't be any cases of those.
        if (number > 0) {
            for (String word : wordLists.keySet()) {
                ArrayList<String> fileList = wordLists.get(word);
                if (fileList.size() == number) {
                    exactly.add(word);
                }
            }
        }

        return exactly;
    }

    void showMap() {
        for (String word : wordLists.keySet()) {
            System.out.print(word + ":\t");
            ArrayList<String> fileList = wordLists.get(word);
            for (String filename : fileList) {
                System.out.print(filename+", ");
            }
            System.out.println();
        }
    }  // showMap

}  // WordsInFiles
