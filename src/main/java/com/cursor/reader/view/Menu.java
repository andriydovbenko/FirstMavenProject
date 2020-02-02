package com.cursor.reader.view;

import com.cursor.reader.execution.Executor;

import java.util.Scanner;

public class Menu {
    private Executor executor = new Executor();
    private Scanner input = new Scanner(System.in);
    private final int autoChoice = 10;

    public void showMenu() {
        tryToInputFirsChoice();
        tryToInputNumberOfPopularWords();
        executor.createServices();
        input.close();
    }

    private void tryToInputFirsChoice() {
        System.out.println("Do you want to add bed words to list searcher?\nPress \"y\" -> yes  or any key-> not");
        String firsChoice = input.next();
        if (firsChoice.equals("y") || firsChoice.equals("Y") || firsChoice.equals("yes")) {
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
            if (inputSwearWordsFromConsole.equals("s") || inputSwearWordsFromConsole.equals("stop")) {
                condition = false;
            } else {
                executor.addSwearWordToUserList(inputSwearWordsFromConsole);
                System.err.println("\"" + inputSwearWordsFromConsole + "\"" + " was added to bad word list");
            }
        }
    }

    private void tryToInputNumberOfPopularWords() {
        System.out.println("How many popular words form text print to console?\nEnter the number:");
        String inputNumberOfWordsFromConsole = input.next();
        try {
            int number = Integer.parseInt(inputNumberOfWordsFromConsole);
            executor.setQuantityOfTheMostPopularWord(number);
        } catch (NumberFormatException e) {
            System.err.println("Wrong input format\nQuantity of popular words = 10");
            executor.setQuantityOfTheMostPopularWord(autoChoice);
        }
    }
}