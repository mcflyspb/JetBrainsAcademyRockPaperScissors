package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String SCISSORS = "scissors";
    private static final String ROCK = "rock";
    private static final String PAPER = "paper";
    private static final String EXIT = "!exit";
    private static final String BYE = "Bye!";
    private static final String RATING = "!rating";
    private static final String LETS_START = "Okay, let's start";
    private static final String INVALID_INPUT = "Invalid input";
    private static final String ENTER_NAME = "Enter your name:";
    private static final String RATING_FILE = "C:\\android\\rating.txt";
    private static int rating = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String[] wordsArray;
        System.out.println(ENTER_NAME);
        String name = scanner.nextLine();
        System.out.printf("Hello, %s\n", name);
        rating = getScore(name);
        String wordsList = scanner.nextLine();
        if (wordsList.length() > 0) {
            wordsArray = wordsList.split(",");
        } else {
            wordsArray = new String[]{SCISSORS, ROCK, PAPER};
        }

        System.out.println(LETS_START);

        while (true) {
            String userInput = scanner.nextLine();

            if (Objects.equals(userInput, EXIT)) {
                System.out.println(BYE);
                return;
            }

            if (Objects.equals(userInput, RATING)) {
                System.out.printf("Your rating: %d\n", rating);
                continue;
            }

            if (!checkUserInput(userInput, wordsArray)) {
                System.out.println(INVALID_INPUT);
                continue;
            }
            String aiMove = getComputerMove(wordsArray);
            checkNewResult(wordsArray,userInput, aiMove);
        }
    }

    private static void checkNewResult(String[] wordsArray, String userInput, String aiInput) {
        if (Objects.equals(userInput, aiInput)) {
            System.out.printf("There is a draw (%s)\n", userInput);
            updateRatingDraw();
            return;
        }
        int userPosition = 0;
        int aiPosition = 0;

        int i = 0;
        for (String word: wordsArray) {
            if (Objects.equals(word, userInput)) {
                userPosition = i;
            }
            if (Objects.equals(word, aiInput)) {
                aiPosition = i;
            }
            i++;
        }

        if (aiPosition < userPosition) {
            aiPosition = aiPosition + wordsArray.length;
        }

        if (aiPosition - userPosition <= wordsArray.length / 2) {
            System.out.printf("Sorry, but the computer chose %s\n", aiInput);
        } else {
            System.out.printf("Well done. The computer chose %s and failed\n", aiInput);
            updateRatingWon();
        }
    }

    private static boolean checkUserInput(String userInput, String[] wordsArray) {
        for (String word: wordsArray) {
            if (Objects.equals(userInput,word)) {
                return true;
            }
        }
        return false;
    }

    private static int getScore(String name) throws FileNotFoundException {
        File file = new File(RATING_FILE);
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNext()) {
            String[] fileLine = fileScanner.nextLine().trim().split(" ");
            if (Objects.equals(fileLine[0], name)) {
                return Integer.parseInt(fileLine[1]);
            }
        }
        return 0;
    }

    private static void updateRatingWon () {
        rating += 100;
    }

    private static void updateRatingDraw () {
        rating += 50;
    }
    private static String getComputerMove(String[] wordsArray) {
        Random random = new Random();
        int rnd = random.nextInt(wordsArray.length);
            return wordsArray[rnd];
    }
}

