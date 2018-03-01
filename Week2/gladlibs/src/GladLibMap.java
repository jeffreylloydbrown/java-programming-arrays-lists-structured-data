import edu.duke.*;
import java.util.*;

public class GladLibMap {

	private HashMap<String, ArrayList<String>> myMap;

	//private ArrayList<String> usedWords;
	private HashMap<String, ArrayList<String>> usedWords;
	
	private Random myRandom;
	
	private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
	private static String dataSourceDirectory = "src/data";

	private static String[] categories = {"adjective", "noun", "color",
			"country", "name", "animal",
			"timeframe", "verb", "fruit"};
	
	public GladLibMap(){
		myMap = new HashMap<String, ArrayList<String>>();
		initializeFromSource(dataSourceDirectory);
		usedWords = new HashMap<String, ArrayList<String>>();
		myRandom = new Random();
	}
	
	public GladLibMap(String source){
		myMap = new HashMap<String, ArrayList<String>>();
		initializeFromSource(source);
		usedWords = new HashMap<String, ArrayList<String>>();
		myRandom = new Random();
	}
	
	private void initializeFromSource(String source) {
		for (String category : categories) {
			myMap.put(category, readIt(source+"/"+category+".txt"));
		}
	}
	
	private String randomFrom(ArrayList<String> source){
		int index = myRandom.nextInt(source.size());
		return source.get(index);
	}
	
	private String getSubstitute(String label) {
		if (label.equals("number")){
			return ""+myRandom.nextInt(50)+5;
		}
		if (myMap.containsKey(label)) {
			return randomFrom(myMap.get(label));
		}
		return "**UNKNOWN**";
	}
	
	private String processWord(String w){
		int first = w.indexOf("<");
		int last = w.indexOf(">",first);
		if (first == -1 || last == -1){
			return w;
		}
		String prefix = w.substring(0,first);
		String suffix = w.substring(last+1);
		String category = w.substring(first+1, last);
		String sub = getSubstitute(category);
		// If category does not already exist, we need
		// to add the category, and then add the substitute
		// to it.  No chance of a repeat in this case.
		if (! usedWords.containsKey(category)) {
			ArrayList<String> words = new ArrayList<String>();
			words.add(sub);
			usedWords.put(category, words);
		} else {
			// We have a previously seen category, so make sure we
			// haven't seen sub before.  If we have, make up to 20
			// attempts to get a different word.  If we run out at
			// that point, we will allow the duplicate.
			int attempts = 1;
			ArrayList<String> words = usedWords.get(category);
			while (attempts <= 20) {
				if (words.contains(sub)) {
					// sub already used, attempt to get a replacement.
					sub = getSubstitute(category);
					attempts += 1;
				} else {
					// not found, remember it and exit loop.
					words.add(sub);
					break;
				}
			}
			// Now save the updated word list for the category.
			usedWords.put(category, words);
		}
		return prefix+sub+suffix;
	}
	
	private void printOut(String s, int lineWidth){
		int charsWritten = 0;
		for(String w : s.split("\\s+")){
			if (charsWritten + w.length() > lineWidth){
				System.out.println();
				charsWritten = 0;
			}
			System.out.print(w+" ");
			charsWritten += w.length() + 1;
		}
	}
	
	private String fromTemplate(String source){
		String story = "";
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		return story;
	}
	
	private ArrayList<String> readIt(String source){
		ArrayList<String> list = new ArrayList<String>();
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		return list;
	}

	private int totalUsedWords() {
		int total = 0;
		for (String category : usedWords.keySet()) {
			total += usedWords.get(category).size();
		}
		return total;
	}
	
	public void makeStory(){
		usedWords.clear();
	    System.out.println();
		//String story = fromTemplate("src/data/madtemplate2.txt");
		String story = fromTemplate("src/data/madtemplate.txt");
		printOut(story, 60);
		System.out.println("\n\nReplaced "+totalUsedWords()+" words.");
		System.out.println("There were "+totalWordsInMap()+" words to choose from in "+
				myMap.keySet().size()+" categories (not including 'number' which isn't mapped).");
		System.out.println("There were "+totalWordsConsidered()+" words considered in "+
				usedWords.keySet().size()+" categories.");
	}

	public int totalWordsInMap() {
		int total = 0;
		for (String category : myMap.keySet()) {
			total += myMap.get(category).size();
		}
		return total;
	}

	// I think this is supposed to count like total words in map, but only for
	// the categories used in this gladlib.  It could mean counting words used
	// in the categories used, but that doesn't make sense now that I think about
	// it.  A used word would only be "used" if a category contained it.  If the
	// quiz looks for "how many of category <noun> got used", then I have to
	// change this code.
	public int totalWordsConsidered() {
		int total = 0;
		for (String category : usedWords.keySet()) {
			total += usedWords.get(category).size();
		}
		return total;
	}
	


}
