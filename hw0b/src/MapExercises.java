import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {

        Map<Character, Integer> L = new TreeMap<>();
        int i = 1;
        for (char c = 'a'; c <= 'z'; c++){
            L.put(c, i);
            i += 1;
        }
        return L;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {

        Map<Integer, Integer> L = new TreeMap<>();
        for (Integer i : nums) {
            L.put(i, i*i);
        }
        return L;

    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> L = new TreeMap<>();
        for (String i : words) {
            int count_i = 0;
            for (String j : words) {
                if (i == j) {
                    count_i += 1;
                }
            }
            L.put(i, count_i);
        }
        return L;
    }
}
