import edu.duke.*;

/**
 * Write a program to figure out the most common word length of words
 * from a file. To solve this problem you will need to keep track of
 * how many words from a file are of each possible length. You should
 * group all words of length 30 or more together, and you should not
 * count basic punctuation that are the first or last characters of a
 * group of characters.
 */
public class WordLengths {

    /** Read the words in `resource` and count the number of words of each
     *  length.  Return those counts in `counts`.
     *
     *  For example, count[1] being 12 means there were 12 words of length
     *  1.  count[9] being 4 means there were 4 words of length 9, and
     *  count[10] being 0 means there were no 10-character words seen.
     *
     *  If a word has a non-letter as the first or last character, that
     *  character should not be counted as part of the word length. For
     *  example, the word And, would be considered of length 3 (the comma
     *  is not counted), the word “blue-jeans” would be considered of
     *  length 10 (the double quotes are not counted, but the hyphen is).
     *  Note that we will miscount some words, such as “Hello,” which will
     *  be counted as 6 since we don’t count the double quotes but will
     *  count the comma, but that is OK as there should not be many words
     *  in that category.
     *
     *  For any words equal to or larger than the last index of the `counts`
     *  array, count them as the largest size represented in the counts array.
     *
     * @param resource  The resource representing the file with the data.
     * @param counts    Holds how many words have been seen of the length
     *                  matching the index into `counts`.
     * @return `counts` with the updated word counts.
     */
    public void countWordLengths (FileResource resource, int[] counts) {
        int lastIndex = counts.length - 1;
        for (String word : resource.words()) {
            // Apparently there is a defect in FileResource.words(): using an
            // empty file will return a "word" of zero length, so we have to
            // test for that.  In my opinion, it should return nothing, so the
            // foreach loop does not execute at all.  Not checking for this
            // means an exception below on word.charAt(0).
            if (word.isEmpty()) break;

            // Since we are tracking length of word instead of the actual
            // words, we don't need to adjust the string itself if the
            // first or last characters are not letters.
            int wordLen = word.length();    // used to avoid multiple calls to length()
            int len = wordLen;
            if (! Character.isLetter(word.charAt(0))) len -= 1;
            if (! Character.isLetter(word.charAt(wordLen-1))) len -= 1;

            // Now figure out where to put `len`.  Remember if len > last spot in
            // `count`, we bump the final spot in `counts`.  It is also possible
            // that `len` is < 0, if the word is small and there are no letters in it!
            if (len > lastIndex)
                counts[lastIndex]++;
            else if (len >= 0)
                counts[len]++;
        }
    }

    /** @return the index position of the largest element in `values`.
     * @param values    the data to examine for the largest element
     */
    public int indexOfMax (int[] values) {
        if (values.length == 0)
            return -1;

        int maxIdx = 0;
        for (int k = 0; k < values.length; k++)
            if (values[k] > values[maxIdx])
                maxIdx = k;
        return maxIdx;
    }
}
