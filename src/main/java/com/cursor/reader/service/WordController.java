package com.cursor.reader.service;

import java.util.*;
import java.util.stream.Collectors;

public class WordController {
    private final String symbol = "'";
    private final String emptyPlace = "";
    private final int zeroIntValue = 0;
    private List<String> wordsRepo;
    private List<String> swearWordsList = new ArrayList<>();
    private List<String> shortWordsList = new ArrayList<>();
    private Set<String> exampleSwearWords = new TreeSet<>();
    private Map<String, Integer> diversityOfUniqueWords = new TreeMap<>();
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
            if (word.replace(symbol, emptyPlace).length() < 3) {
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
            diversityOfUniqueWords.put(word, zeroIntValue);
        }
    }

    public int getCountOfSwearWords() {
        return countOfSwearWords;
    }

    public int getCountOfShortWords() {
        return countOfShortWords;
    }
}