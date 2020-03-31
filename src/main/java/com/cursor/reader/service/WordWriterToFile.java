package com.cursor.reader.service;

import java.io.*;
import java.util.List;

public class WordWriterToFile {
    private FileOutputStream pathForWriting;
    private final List<String> wordsRepo;

    public WordWriterToFile(List<String> wordsRepo, String pathForWriting) {
        this.wordsRepo = wordsRepo;
        tryToCreateFileOutputStream(pathForWriting);
    }

    private void tryToCreateFileOutputStream(String pathForWriting) {
        try {
            this.pathForWriting = new FileOutputStream(pathForWriting);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startWriting() {
        try (PrintStream out = new PrintStream(pathForWriting)) {
            for (String word : wordsRepo) {
                out.println(word);
            }
        }
    }
}