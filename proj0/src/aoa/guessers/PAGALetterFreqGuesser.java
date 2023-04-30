package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.*;


public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        List<String> validWords = keepOnlyWordsThatMatchPattern(words, pattern);
        Map<Character, Integer> frequencyMap = getFreqMapThatMatchesPattern(validWords, guesses);
        return getMostCommonLetter(frequencyMap);
    }

    public boolean containsChar(String s, char search) {
        if (s.length() == 0)
            return false;
        else
            return s.charAt(0) == search || containsChar(s.substring(1), search);
    }
    private List<String> keepOnlyWordsThatMatchPattern(List<String> words, String pattern) {
        List<String> validWords = new ArrayList<>();
        for (String word : words) {
            boolean isValid = true;
            for (int i = 0; i < pattern.length(); i++) {
                if ((pattern.charAt(i) != '-' && pattern.charAt(i) != word.charAt(i)) || ((pattern.charAt(i) == '-') && containsChar(pattern, word.charAt(i))) ) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                validWords.add(word);
            }
        }
        return validWords;
    }

    private Map<Character, Integer> getFreqMapThatMatchesPattern(List<String> validWords, List<Character> guesses) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (String word : validWords) {
            for (char c : word.toCharArray()) {
                if (!guesses.contains(c)) {
                    frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
                }
            }
        }
        return frequencyMap;
    }

    private char getMostCommonLetter(Map<Character, Integer> frequencyMap) {
        char mostCommonLetter = 'a';
        int maxFrequency = 0;
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency || (entry.getValue() == maxFrequency && entry.getKey() < mostCommonLetter)) {
                mostCommonLetter = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return mostCommonLetter;
    }

    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
