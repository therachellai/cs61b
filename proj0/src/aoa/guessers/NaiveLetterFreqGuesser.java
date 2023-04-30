package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.*;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // TODO: Fill in this method.
        Map<Character, Integer> frequencyMap = new HashMap<>();
        //iterating through all the words
        for (String word : words) {
            //iterating through each character of the word
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                //putting the character and its count in the frequency map
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }
        return frequencyMap;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // TODO: Fill in this method.
        Map<Character, Integer> frequencyMap = getFrequencyMap();
        TreeMap<Integer, List<Character>> sortedMap = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            //adding only the characters that haven't been guessed
            if (!guesses.contains(entry.getKey())) {
                //checking whether letter of entry is already in sortedMap, if so have the letters be set
                //to the original list, else create new list
                List<Character> letters = sortedMap.getOrDefault(entry.getValue(), new ArrayList<>());
                letters.add(entry.getKey());
                sortedMap.put(entry.getValue(), letters);
            }
        }
        if (sortedMap.isEmpty()) {
            return '?';
        } else {
            //getting the top letters in the sorted map
            List<Character> topLetters = sortedMap.firstEntry().getValue();
            //sorting the letters in alphabetical order
            Collections.sort(topLetters);
            return topLetters.get(0);
        }
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
