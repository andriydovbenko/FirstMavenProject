package com.cursor.reader.service;

import java.util.*;
import java.util.stream.Collectors;

public class WordController {
    private static final String SYMBOL = "'";
    private static final String EMPTY_PLACE = "";
    private static final int ZERO_INT_VALUE = 0;
    private final List<String> wordsRepo;
    private final List<String> swearWordsList = new ArrayList<>();
    private final List<String> shortWordsList = new ArrayList<>();
    private final Set<String> exampleSwearWords = new TreeSet<>();
    private final Map<String, Integer> diversityOfUniqueWords = new TreeMap<>();
    private int countOfSwearWords;
    private int countOfShortWords;

    public WordController(List<String> wordsRepo) {
        this.wordsRepo = wordsRepo;
    }

    public void addSwearWordToChecker(String swearWord) {
        this.exampleSwearWords.add(swearWord);
    }

    public List<String> getSwearWordList() {
        if (!exampleSwearWords.isEmpty()) {
            checkOutEveryWord();
        }
        return swearWordsList;
    }

    private void checkOutEveryWord() {
        for (String word : wordsRepo) {
            for (String example : exampleSwearWords) {
                if (word.equals(example)) {
                    swearWordsList.add(word);
                    countOfSwearWords++;
                }
            }
        }
    }

    public List<String> getShortWordList() {
        for (String word : wordsRepo) {
            if (word.replace(SYMBOL, EMPTY_PLACE).length() < 3) {
                shortWordsList.add(word);
                countOfShortWords++;
            }
        }
        return shortWordsList;
    }

    public Map<String, Integer> getMapSortedByWordFrequency() {
        createWordMapWithValueZero();
        countOfEachWord();
        return sortByValue(diversityOfUniqueWords);
    }

    private Map<String, Integer> sortByValue(final Map<String, Integer> wordCounts) {
        return wordCounts.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private void countOfEachWord() {
        for (String word : wordsRepo) {
            Integer value = diversityOfUniqueWords.get(word);
            value++;
            diversityOfUniqueWords.put(word, value);
        }
    }

    private void createWordMapWithValueZero() {
        for (String word : wordsRepo) {
            diversityOfUniqueWords.put(word, ZERO_INT_VALUE);
        }
    }

    public int getCountOfSwearWords() {
        return countOfSwearWords;
    }

    public int getCountOfShortWords() {
        return countOfShortWords;
    }
}