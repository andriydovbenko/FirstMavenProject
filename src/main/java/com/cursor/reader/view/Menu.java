package com.cursor.reader.view;

import com.cursor.reader.controller.Executor;

import java.util.Scanner;

public class Menu {
    private final Executor executor = new Executor();
    private final Scanner input = new Scanner(System.in);
    private static final int AUTO_CHOICE = 10;

    public void showMenu() {
        inputFirsChoice();
        inputNumberOfPopularWords();
        executor.createServices();
        input.close();
    }

    private void inputFirsChoice() {
        System.out.println("Do you want to add bed words to list searcher?\nPress \"y\" -> yes  or any key-> not");
        String firsChoice = input.next();
        if (firsChoice.equalsIgnoreCase("y") || firsChoice.equalsIgnoreCase("yes")) {
            System.out.println("Enter 'bad' word. To stop enter: \"stop\" or \"s\"");
            inputSwearWordsFromConsole();
        } else {
            System.out.println("Your choice is NO");
        }
    }

    private void inputSwearWordsFromConsole() {
        String inputSwearWordsFromConsole;
        boolean condition = true;
        while (condition) {
            inputSwearWordsFromConsole = input.next();
            if (inputSwearWordsFromConsole.equalsIgnoreCase("s") ||
                    inputSwearWordsFromConsole.equalsIgnoreCase("stop")) {
                condition = false;
            } else {
                executor.addSwearWordToUserList(inputSwearWordsFromConsole);
                System.err.println("\"" + inputSwearWordsFromConsole + "\"" + " was added to bad word list");
            }
        }
    }

    private void inputNumberOfPopularWords() {
        System.out.println("How many popular words form text print to console?\nEnter the number:");
        String inputNumberOfWordsFromConsole = input.next();
        try {
            int number = Integer.parseInt(inputNumberOfWordsFromConsole);
            executor.setQuantityOfTheMostPopularWord(number);
        } catch (NumberFormatException e) {
            System.err.println("Wrong input format\nQuantity of popular words = 10");
            executor.setQuantityOfTheMostPopularWord(AUTO_CHOICE);
        }
    }
}