package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.*;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        List<String> validWords = keepOnlyWordsThatMatchPattern(words, pattern);
        Map<Character, Integer> frequencyMap = getFreqMapThatMatchesPattern(validWords, guesses);
        return getMostCommonLetter(frequencyMap);
    }

    private List<String> keepOnlyWordsThatMatchPattern(List<String> words, String pattern) {
        List<String> validWords = new ArrayList<>();
        for (String word : words) {
            boolean isValid = true;
            //iterate through whole list of words, checking if they are valid
            for (int i = 0; i < pattern.length(); i++) {
                //As long as not the same character in the word, it has to be - to be valid
                if (pattern.charAt(i) != '-' && pattern.charAt(i) != word.charAt(i)) {
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
        //get frequency map like in naive guesser that includes the pattern
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
        //initialize
        char mostCommonLetter = 'a';
        int maxFrequency = 0;
        //iterated through all the entries in the frequencyMap; if frequency is higher than the currently stored max
        //or it's the same frequency but higher
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency || (entry.getValue() == maxFrequency && entry.getKey() < mostCommonLetter)) {
                mostCommonLetter = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return mostCommonLetter;
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}