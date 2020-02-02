package com.cursor.reader.execution;

import com.cursor.reader.service.WordController;
import com.cursor.reader.service.WordReaderFromFile;
import com.cursor.reader.service.WordWriterToFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Executor {
    private final String pathForWritingSwearWords = "file/swear_words.txt";
    private final String pathForWritingShortWords = "file/short_words.txt";
    private final String pathForReading = "file/data.txt";
    private List<String> listOfAllWords;
    private List<String> swearWordsListFromText;
    private List<String> swearWordsListFromUser = new ArrayList<>();
    private List<String> shortWordsList;
    private Map<String, Integer> sortedByWordFrequency;
    private int countOfAllWords;
    private int countOfShortWords;
    private int countOfSwearWords;
    private int quantityOfTheMostPopularWord;
    private int counterOfEntrySet = 1;
    private WordController controller;

    public void createServices() {
        WordReaderFromFile readerFromFile = new WordReaderFromFile(pathForReading);
        countOfAllWords = readerFromFile.getCountOfReadWords();
        listOfAllWords = readerFromFile.getWordsRepo();

        controller = new WordController(listOfAllWords);
        addSwearWordsIfExist();
        swearWordsListFromText = controller.getSwearWordList();
        countOfSwearWords = controller.getCountOfSwearWords();

        shortWordsList = controller.getShortWordList();
        countOfShortWords = controller.getCountOfShortWords();
        tryToWriteShortWordsToFile();
        tryToWriteSwearWordsToFile();

        printQuantitiesOfWrittenWord();

        sortedByWordFrequency = controller.getMapSortedByWordFrequency();
        printMostPopularWords();
    }

    private void printQuantitiesOfWrittenWord() {
        System.out.println("Quantity of all word in text = " + countOfAllWords);
        System.out.println("Quantity of bad word in text = " + controller.getCountOfSwearWords());
        System.out.println("Quantity of word which is less than 3 char in text = "
                + controller.getCountOfShortWords() + "\n");
    }

    private void addSwearWordsIfExist() {
        if (!swearWordsListFromUser.isEmpty()) {
            for (String word : swearWordsListFromUser) {
                controller.addSwearWordToChecker(word);
            }
        }
    }

    private void printMostPopularWords() {
        for (Map.Entry<String, Integer> entry : sortedByWordFrequency.entrySet()) {
            if (counterOfEntrySet <= quantityOfTheMostPopularWord) {
                System.out.println(counterOfEntrySet + ". Word: " + "'" + entry.getKey() + "'" +
                        " meets in the text: " + entry.getValue() + " times");
                counterOfEntrySet++;
            }
        }
    }

    private void tryToWriteShortWordsToFile() {
        if (countOfShortWords != 0) {
            new WordWriterToFile(shortWordsList, pathForWritingShortWords).startWriting();
        } else {
            System.out.println("There are no short words");
        }
    }

    private void tryToWriteSwearWordsToFile() {
        if (countOfSwearWords != 0) {
            new WordWriterToFile(swearWordsListFromText, pathForWritingSwearWords).startWriting();
        } else {
            System.out.println("There are no bad words");
        }
    }

    public void setQuantityOfTheMostPopularWord(int quantityOfTheMostPopularWord) {
        this.quantityOfTheMostPopularWord = quantityOfTheMostPopularWord;
    }

    public void addSwearWordToUserList(String swearWord) {
        swearWordsListFromUser.add(swearWord);
    }
}