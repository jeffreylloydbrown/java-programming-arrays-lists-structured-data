import edu.duke.*;

/** You will start with the Caesar Cipher algorithm you learned
 *  about in the videos, and you will make some enhancements to it,
 *  so that it works with all letters (both uppercase and lowercase)
 *  and to make it a little bit harder to decrypt.
 */
public class CaesarCipher {

    /** Write the method encrypt that has two parameters, a String named
     *  `input` and an int named `key`. This method returns a String that
     *  has been encrypted using the Caesar Cipher algorithm explained in
     *  the videos. Assume that all the alphabetic characters are uppercase
     *  letters.
     *
     * @param input     The string to encrypt
     * @param key       The rotation key to use
     * @return the encrypted version of `input` using `key`.
     */
    public String encrypt (String input, int key) {
        return "TODO";
    }

    /** Write the method encryptTwoKeys that has three parameters, a String
     *  named `input`, and two integers named `key1` and `key2`. This method
     *  returns a String that has been encrypted using the following algorithm.
     *  Parameter `key1` is used to encrypt every other character with the
     *  Caesar Cipher algorithm, starting with the first character, and `key2`
     *  is used to encrypt every other character, starting with the second
     *  character.
     *
     * @param input     The string to encrypt
     * @param key1      The rotation key for the first, third, etc. characters
     * @param key2      The rotation key for the second, fourth, etc. characters
     * @return  the encrypted version of `input` that is harder to crack
     */
    public String encryptTwoKeys (String input, int key1, int key2) {
        return "TODO";
    }

}  // CaesarCipher
