import edu.duke.*;

/** Write a program to transform words from a file into another form,
 *  such as replacing vowels with asterisks.
 *
 * @author Jeff Brown
 * @version 1
 */
public class WordPlay {

    /** Write a method isVowel that has one Char parameter named ch. This method
     *  returns true if ch is a vowel (one of 'a', 'e', 'i', 'o', or 'u' or the
     *  uppercase versions) and false otherwise. You should write a tester method
     *  to see if this method works correctly. For example, isVowel(‘F’) should
     *  return false, and isVowel(‘a’) should return true.
     *
     * @param ch    any character
     * @return true if `ch` is a vowel
     */
    public boolean isVowel (char ch) {
        return "aeiouAEIOU".contains(Character.toString(ch));
    }

    private boolean hasValue (String s) { return s != null && ! s.isEmpty(); }

    /** Write a method replaceVowels that has two parameters, a String named phrase
     *  and a Char named ch. This method should return a String that is the string
     *  phrase with all the vowels (uppercase or lowercase) replaced by ch. For
     *  example, the call replaceVowels(“Hello World”, ‘*’) returns the string
     *  “H*ll* W*rld”. Be sure to call the method isVowel that you wrote and also
     *  test this method.
     *
     * @param phrase    the phrase that will get its vowels replaced
     * @param ch        the character to replace the vowels with
     * @return          the original string but with all vowels replaced by `ch`.
     */
    public String replaceVowels (String phrase, char ch) {
        if (! hasValue(phrase)) return "";
        StringBuilder sb = new StringBuilder(phrase);
        for (int k = 0; k < phrase.length(); k++)
            if (isVowel(sb.charAt(k)))
                sb.setCharAt(k, ch);
        return sb.toString();
    }

    private char toLower (char ch) { return Character.toLowerCase(ch); }
    private char toUpper (char ch) { return Character.toUpperCase(ch); }

    private char replacement (int pos) { return (pos % 2 == 0) ? '*' : '+'; }

    /** Write a method emphasize with two parameters, a String named phrase and
     *  a character named ch. This method should return a String that is the
     *  string phrase but with the Char ch (upper- or lowercase) replaced by
     *
     * ‘*’ if it is in an odd number location in the string (e.g. the first
     * character has an odd number location but an even index, it is at index 0), or
     *
     * ‘+’ if it is in an even number location in the string (e.g. the second
     * character has an even number location but an odd index, it is at index 1).
     *
     * For example, the call emphasize(“dna ctgaaactga”, ‘a’) would return the
     * string “dn* ctg+*+ctg+”, and the call emphasize(“Mary Bella Abracadabra”, ‘a’)
     * would return the string “M+ry Bell+ +br*c*d*br+”. Be sure to test this method.
     *
     * @param phrase    the string being processed.
     * @param ch        the character in `phrase` to replace
     * @return          the original string but with all instances of `ch`
     * replaced with either '*' or '+' as described.
     */
    public String emphasize (String phrase, char ch) {
        if (! hasValue(phrase)) return "";
        StringBuilder sb = new StringBuilder(phrase);
        for (int k = 0; k < phrase.length(); k++)
            if (toLower(sb.charAt(k)) == toLower(ch) ||
                    toUpper(sb.charAt(k)) == toUpper(ch)) {
                sb.setCharAt(k, replacement(k));
            }
        return sb.toString();
    }
}
