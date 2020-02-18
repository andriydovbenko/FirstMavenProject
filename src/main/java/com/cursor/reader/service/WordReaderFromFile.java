package com.cursor.reader.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordReaderFromFile {
    private static final String WORDS_INDICATOR = " ";
    private static final String UNCHECKED_SYMBOLS = "[,)(!]";
    private static final String EMPTY_PLACE = "";
    private final String pathForReadingFile;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private String line;
    private final List<String> wordsRepo;
    private int countOfReadWords;

    public WordReaderFromFile(String pathForReadingFile){
        this.pathForReadingFile = pathForReadingFile;
        this.wordsRepo = new ArrayList<>();
        startReading();
    }

    private void startReading() {
        tryToReadFromFile();
        bufferedReader = new BufferedReader(fileReader);
        tryToReadLine();
        processingLine();
        tryToCloseBufferedReader();
    }

    private void tryToReadFromFile() {
        try {
            fileReader = new FileReader(pathForReadingFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void tryToReadLine() {
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processingLine() {
        while (line != null) {
            if (!line.isEmpty()) {
                String[] words = line.split(WORDS_INDICATOR);
                addWordsToListAndCountIt(words);
            }
            tryToReadLine();
        }
    }

    private void addWordsToListAndCountIt(String[] words) {
        for (String word : words) {
            String formattedWord = word.replaceAll(UNCHECKED_SYMBOLS, EMPTY_PLACE);
            wordsRepo.add(formattedWord);
            countOfReadWords++;
        }
    }

    private void tryToCloseBufferedReader() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCountOfReadWords() {
        return countOfReadWords;
    }

    public List<String> getWordsRepo() {
        return wordsRepo;
    }
}