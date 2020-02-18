package com.cursor.reader.controller;

import com.cursor.reader.service.WordController;
import com.cursor.reader.service.WordReaderFromFile;
import com.cursor.reader.service.WordWriterToFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Executor {
    private static final String FILE_SWEAR_WORDS_TXT = "file/swear_words.txt";
    private static final String FILE_SHORT_WORDS_TXT = "file/short_words.txt";
    private static final String FILE_DATA_TXT = "file/data.txt";
    private List<String> swearWordsListFromText;
    private final List<String> swearWordsListFromUser = new ArrayList<>();
    private List<String> shortWordsList;
    private Map<String, Integer> sortedByWordFrequency;
    private int countOfAllWords;
    private int countOfShortWords;
    private int countOfSwearWords;
    private int quantityOfTheMostPopularWord;
    private int counterOfEntrySet = 1;
    private WordController controller;

    public void createServices() {
        WordReaderFromFile readerFromFile = new WordReaderFromFile(FILE_DATA_TXT);
        countOfAllWords = readerFromFile.getCountOfReadWords();
        List<String> listOfAllWords = readerFromFile.getWordsRepo();

        controller = new WordController(listOfAllWords);
        addSwearWordsIfExist();
        swearWordsListFromText = controller.getSwearWordList();
        countOfSwearWords = controller.getCountOfSwearWords();

        shortWordsList = controller.getShortWordList();
        countOfShortWords = controller.getCountOfShortWords();
        writeShortWordsToFile();
        writeSwearWordsToFile();

        printQuantitiesOfWrittenWord();

        sortedByWordFrequency = controller.getMapSortedByWordFrequency();
        printMostPopularWords();
    }

    private void printQuantitiesOfWrittenWord() {
        System.out.println("Quantity of all word in text = " + countOfAllWords + "\nQuantity of bad word in text = " +
                controller.getCountOfSwearWords() + "\nQuantity of word which is less than 3 char in text = " +
                controller.getCountOfShortWords() + "\n");
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

    private void writeShortWordsToFile() {
        if (countOfShortWords != 0) {
            new WordWriterToFile(shortWordsList, FILE_SHORT_WORDS_TXT).startWriting();
        } else {
            System.out.println("There are no short words");
        }
    }

    private void writeSwearWordsToFile() {
        if (countOfSwearWords != 0) {
            new WordWriterToFile(swearWordsListFromText, FILE_SWEAR_WORDS_TXT).startWriting();
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